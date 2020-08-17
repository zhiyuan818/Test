$(function() {
	$("#accSubmit").click(function (){
		var accId=$("#accId").val();
		$("#oldAccId").val(accId);
		loadAccStatistic();
	});
	$("#chaSubmit").click(function (){
		var channelId=$("#channelId").val();
		$("#oldChannelId").val(channelId);
		loadChaStatistic();
	});
	setInterval(function(){
		loadAccStatistic();
	},1000*30);
	setInterval(function(){
		loadChaStatistic();
	},1000*30);
	setInterval(function(){
		loadCount();
	},1000*10);
	
})

function loadCount(){
	$.ajax({
		url:"findCountStatistic.action",
		type:"post",
		success:function(data){
			$("#api_submit").html(data.api_submit);
			$("#gw_submit").html(data.gw_submit);
			$("#api_report_succ").html(data.api_report_succ);
			$("#api_report_fail").html(data.api_report_fail);
			$("#gw_report_succ").html(data.gw_report_succ);
			$("#gw_report_fail").html(data.gw_report_fail);
			$("#api_mo").html(data.api_mo);
			$("#gw_mo").html(data.gw_mo);
		},
		dataType:"json"
	});
}
function loadAccStatistic(){
	var accId=$("#oldAccId").val();
	if(accId == null || accId == '' || accId == undefined){
//		alert("账号ID不能为空");
	}else{
		$.ajax({
			url:"findAccStatisticView.action",
			data:{
				accId:accId
			},
			type:"post",
			success:function(result){
				if (result) {
                    //将返回的category和series对象赋值给options对象内的category和series
                    //因为xAxis是一个数组 这里需要是xAxis[i]的形式
						option1.title.text = accId+"账号数据统计，到达率："+result.arrive+"%，成功率："+result.succ+"%";
                        option1.xAxis.data = result.data.category;
                        option1.series = result.data.series;
//                        option1.legend.data = result.data.legend;
                        psLineChar1.hideLoading();
                        psLineChar1.setOption(option1);
                        
                        option3.title.text = accId+"账号前30分钟数据统计";
                        option3.xAxis.data = result.datas.category;
                        option3.series = result.datas.series;
                        option3.legend.data = result.datas.legend;
                        psLineChar3.hideLoading();
                        psLineChar3.setOption(option3,true);
                }
			},
			dataType:"json"
		});
	}
}

function loadChaStatistic(){
	var channelId=$("#oldChannelId").val();
	if(channelId == null || channelId == '' || channelId == undefined){
//		alert("通道ID不能为空");
	}else{
		$.ajax({
			url:"findChannelStatisticView.action",
			data:{
				channelId:channelId
			},
			type:"post",
			success:function(result){
				if (result) {
					//将返回的category和series对象赋值给options对象内的category和series
					//因为xAxis是一个数组 这里需要是xAxis[i]的形式
					option2.title.text = channelId+"通道数据统计，到达率："+result.arrive+"%，成功率："+result.succ+"%";
					option2.xAxis.data = result.data.category;
					option2.series = result.data.series;
//                  option.legend.data = result.legend;
					psLineChar2.hideLoading();
					psLineChar2.setOption(option2);
					
					option4.title.text = channelId+"通道前30分钟数据统计";
					option4.xAxis.data = result.datas.category;
                    option4.series = result.datas.series;
                    option4.legend.data = result.datas.legend;
                    psLineChar4.hideLoading();
                    psLineChar4.setOption(option4,true);
				}
			},
			dataType:"json"
		});
	}
}
var psLineChar1 = echarts.init(document.getElementById('psLine1'));
var psLineChar2 = echarts.init(document.getElementById('psLine2'));
var psLineChar3 = echarts.init(document.getElementById('psLine3'));
var psLineChar4 = echarts.init(document.getElementById('psLine4'));

option1 = {
		title: {
		   text: ''
		},
		legend: {
	        data:[]
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    xAxis: {
	        type: 'category',
	        data: []
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [{
	        data: [],
	        type: 'bar',
	    }]
	};
option2 = {
		title: {
	        text: ''
		},
		legend: {
	        data:[]
	    },
		tooltip : {
			trigger: 'axis',
			axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		xAxis: {
			type: 'category',
			data: []
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			data: [],
			type: 'bar',
			itemStyle : { normal: {label : {show: true}}}
		}]
};
option3 = {
	    title: {
	        text: ''
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
option4 = {
		title: {
			text: ''
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

psLineChar1.setOption(option1);
psLineChar3.setOption(option3);
psLineChar2.setOption(option2);
psLineChar4.setOption(option4);

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