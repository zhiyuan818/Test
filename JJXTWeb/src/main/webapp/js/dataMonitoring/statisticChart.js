$(function() {
	$("#class").change(function(){
		loadData();
	});
	setInterval(function(){
		findDataChart();
	},1000*30);
	$("#data").change(function(){
		var data=$("#data").val();
		if(data!=null){
			if(data.length>1){
				$("#div3")[0].style.display='block';
				$("#type option:first").prop("selected", 'selected'); 
			}else{
				$("#div3")[0].style.display='none';
				$("#type option:first").prop("selected", 'selected'); 
			}
		}
		$("#type").selectpicker("refresh");
	});
	
	$("#btn_sub").click(function(){
		var cla=$("#class").val();
		var type=$("#type").val();
		var data=$("#data").val();
		var category=$("#category").val();
		var selectTime=$("#selectTime").val();
		if(cla !="count"){
			if(data==null || data.length==0){
				if(cla =="acc"){
					$('.popup_de .show_msg').text('至少选择一个账号');
				}else{
					$('.popup_de .show_msg').text('至少选择一个通道');
				}
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}
			if(selectTime == null || selectTime == '' || selectTime == undefined){
				$('.popup_de .show_msg').text('请选择时间');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}else if(isNaN(selectTime)){
				console.log(selectTime);
				console.log(isNaN(selectTime));
				$('.popup_de .show_msg').text('请选择数字');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}else if(Number(selectTime) <0 ){
				$('.popup_de .show_msg').text('请选择大于0的数字');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}else if(Number(selectTime) >120 ){
				$('.popup_de .show_msg').text('查询最大为120分钟');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}
		}
		$("#oldClass").val(cla);
		$("#oldData").val(data);
		$("#oldType").val(type);
		$("#oldCategory").val(category);
		$("#oldSelectTime").val(selectTime);
		findDataChart();
	});
	
	
	$("#btn_clo").click(function(){
		$("#class option:first").prop("selected", 'selected');
		$("#type option:first").prop("selected", 'selected');
		$("#data").val('');
		$("#category").val('');
		$("#selectTime").val(30);
		$("select").selectpicker("refresh");
		loadData();
	});
})
function findDataChart(){
	var cla=$("#oldClass").val();
	var type=$("#oldType").val();
	var data=$("#oldData").val();
	var category=$("#oldCategory").val();
	var selectTime=$("#oldSelectTime").val();
	$.ajax({
		url:"findDataChart.action",
		type:"post",
		traditional: true,
		data:{
			cla:cla,
			type:type,
			data:data,
			category:category,
			selectTime:selectTime
		},
		success:function(result){
			var title="";
			var subTitle="";
			var sumMap=result.sumMap;
			if("acc"==cla){
				var arrive=toPercent(Number(sumMap.apiReportSucc)+Number(sumMap.apiReportFail),Number(sumMap.apiSubmit));
				var succ=toPercent(Number(sumMap.apiReportSucc),Number(sumMap.apiSubmit));
				subTitle="账号提交："+sumMap.apiSubmit+"，平台提交："+sumMap.gwSubmit+",账号状态成功："+sumMap.apiReportSucc+",账号状态失败："+sumMap.apiReportFail+",账号上行："+sumMap.apiMo+",到达率："+arrive+",成功率："+succ;
			}else if("chan"==cla){
				var arrive=toPercent(Number(sumMap.gwReportSucc)+Number(sumMap.gwReportFail),Number(sumMap.gwSubmit));
				var succ=toPercent(Number(sumMap.gwReportSucc),Number(sumMap.gwSubmit));
				subTitle="账号提交："+sumMap.apiSubmit+"，平台提交："+sumMap.gwSubmit+",平台状态成功："+sumMap.gwReportSucc+",平台状态失败："+sumMap.gwReportFail+",平台上行："+sumMap.gwMo+",到达率："+arrive+",成功率："+succ;
			}else if("count"==cla){
				var apiarrive=toPercent(Number(sumMap.apiReportSucc)+Number(sumMap.apiReportFail),Number(sumMap.apiSubmit));
				var apisucc=toPercent(Number(sumMap.apiReportSucc),Number(sumMap.apiSubmit));
				var gwarrive=toPercent(Number(sumMap.gwReportSucc)+Number(sumMap.gwReportFail),Number(sumMap.gwSubmit));
				var gwsucc=toPercent(Number(sumMap.gwReportSucc),Number(sumMap.gwSubmit));
				subTitle="账号[提交："+sumMap.apiSubmit+",成功："+sumMap.apiReportSucc+",失败："+sumMap.apiReportFail+",上行："+sumMap.apiMo+",到达："+apiarrive+",成功："+apisucc+"]，平台[提交："+sumMap.gwSubmit+",成功："+sumMap.gwReportSucc+",失败："+sumMap.gwReportFail+",上行："+sumMap.gwMo+",到达："+gwarrive+",成功："+gwsucc+"]";
			}
			if(data!=null || data.length>0){
				title+=data;
			}
			if(cla=='acc'){
				title+="账号";
			}else if(cla=='chan'){
				title+="通道"
			}else{
				title+="平台汇总";
			}
			option.title.text=title;
			option.title.subtext=subTitle;
			option.xAxis.data = result.datas.category;
            option.series = result.datas.series;
            option.legend.data = result.datas.legend;
            psLineChar.hideLoading();
            psLineChar.setOption(option,true);
		},
		dataType:"json"
	});
}

var psLineChar = echarts.init(document.getElementById('psLine'));
option = {
	    title: {
	        text: '',
	        subtextStyle:{'fontSize':'12','color':'#000','fontWeight':'1000'},
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: []
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [
	       
	    ]
	};
psLineChar.setOption(option);
function load(){
	$("#oldSelectTime").val(30);
	var category=["api_submit", "gw_submit", "api_report_succ", "api_report_fail", "gw_report_succ", "gw_report_fail", "api_mo", "gw_mo"];
	$("#oldCategory").val(category);
	$("#oldClass").val("count");
	$("#oldType").val("group");
	loadData();
	findDataChart();
}
function loadData(){
	var cla=$("#class").val();
	if(cla == null || cla == '' || cla == undefined){
		alert("error");
	}else{
		$.ajax({
			url:"findLoadData.action",
			data:{
				cla:cla
			},
			type:"post",
			success:function(result){
				$("#data").empty();
				for(var i in result){
					$("#data").append("<option value='"+result[i].id+"'>"+result[i].id+":"+result[i].name+"</option>");
				}
				$("#data").selectpicker("refresh");
			},
			dataType:"json"
		})
	}
	$("#category").empty();
	if(cla == "acc"){
		$("#category").append("<option value='api_submit'>账号提交</option>");
		$("#category").append("<option value='gw_submit'>平台提交</option>");
		$("#category").append("<option value='api_report_succ'>账号状态成功</option>");
		$("#category").append("<option value='api_report_fail'>账号状态失败</option>");
		$("#category").append("<option value='api_mo'>账号上行</option>");
	}else if(cla == "chan"){
		$("#category").append("<option value='api_submit'>账号提交</option>");
		$("#category").append("<option value='gw_submit'>平台提交</option>");
		$("#category").append("<option value='gw_report_succ'>平台状态成功</option>");
		$("#category").append("<option value='gw_report_fail'>平台状态失败</option>");
		$("#category").append("<option value='gw_mo'>平台上行</option>");
	}else {
		$("#category").append("<option value='api_submit'>账号提交</option>");
		$("#category").append("<option value='gw_submit'>平台提交</option>");
		$("#category").append("<option value='api_report_succ'>账号状态成功</option>");
		$("#category").append("<option value='api_report_fail'>账号状态失败</option>");
		$("#category").append("<option value='gw_report_succ'>平台状态成功</option>");
		$("#category").append("<option value='gw_report_fail'>平台状态失败</option>");
		$("#category").append("<option value='api_mo'>账号上行</option>");
		$("#category").append("<option value='gw_mo'>平台上行</option>");
		$("#div3")[0].style.display='none';
		$("#type option:first").prop("selected", 'selected');
	}
	$("#category").selectpicker("refresh");
}
function toPercent(num, total) {
	if(total==0){
		return "00.00%";
	}

    return (Math.round(num / total * 10000) / 100.00 + "%");// 小数点后两位百分比
   
}

$(document).ajaxComplete(function(event, xhr, settings) {
	// 从http头信息取出 在filter定义的sessionstatus，判断是否是 timeout
	if (xhr.getResponseHeader("sessionstatus") == "timeout") {
		// 从http头信息取出登录的url ＝ loginPath
		if (xhr.getResponseHeader("loginPath")) {
			alert("会话过期，请重新登陆!");
			window.location.replace(xhr.getResponseHeader("loginPath"));
		} else {
			alert("请求超时请重新登陆 !");
		}
	}
});