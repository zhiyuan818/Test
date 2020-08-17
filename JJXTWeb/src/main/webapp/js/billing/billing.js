$(function (){
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$('.btn .btn-default').click(function() {
		$('.btn').removeClass('bbox');
	})
	
	
	$("#btn_billing").click(function(){
		var companyId=$("#companyId").val();
		var appId=$("#appId").val();
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var settlement=$("#settlement").val();
		var selectType=$("input[name='selectType']:checked").val();
		if(companyId == '' && appId == '' ){
			$('.popup_de .show_msg').text('请选择客户或账户');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		if(startTime == '' || endTime == '' ){
			$('.popup_de .show_msg').text('请选择开始时间结束时间');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		//出账时间跨度不得超过一年
		if((new Date(endTime).getTime() - new Date(startTime).getTime()) > 366*(1000*24*60*60)){
			$('.popup_de .show_msg').text('出账时间跨度不得超过一年');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		location.href="billing.action?companyId="+companyId+"&appId="+appId+"&startTime="+encodeURI(startTime)+"&endTime="+encodeURI(endTime)+"&settlement="+settlement+"&selectType="+selectType;
	});
	 $("#startTime").datetimepicker({
			language : 'zh-CN',// 显示中文
			format : 'yyyy-mm-dd',// 显示格式
			initialDate : new Date(),
			minView : 2,
			autoclose : true,// 选中自动关闭
			todayBtn : true,// 显示今日按钮
			locale : moment.locale('zh-cn')
		}).on('hide', function(event) {
			event.preventDefault();
			event.stopPropagation();
			var startTime = event.date;
			$('#endTime').datetimepicker('setStartDate', startTime);
			$('#endTime').val("");
		});
		$("#endTime").datetimepicker({
			language : 'zh-CN',// 显示中文
			format : 'yyyy-mm-dd',// 显示格式
			initialDate : new Date(),
			minView : 2,
			autoclose : true,// 选中自动关闭
			todayBtn : true,// 显示今日按钮
			locale : moment.locale('zh-cn')
		}).on('hide', function(event) {
			event.preventDefault();
			event.stopPropagation();
			var endTime = event.date;
			$('#startTime').datetimepicker('setEndDate', endTime);
		});
})
//按钮是否通过
function passed(button) {
	flag = false;
	var buttons = $("#buttons").val();
	if (buttons == "" || buttons == null || buttons == undefined) {
		return flag;
	}
	var menubutton = buttons.split(",");
	for ( var i in menubutton) {
		if (menubutton[i] == button) {
			flag = true;
		}
	}
	return flag;
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