$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#appcomplaintab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	$('#appcomplaintab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findAllList.action",
		height : tableHeight(),
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : true,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		pageSize : 20,// 单页记录数
		pageList : [ 20, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : '客户简称',
			field : 'companyName',
		}, {
			title : '账户名称',
			field : 'appName',
		}, {
			title : '提交总数',
			field : 'submitCount',
		}, {
			title : '成功总数',
			field : 'succeedCount',
		}, {
			title : '投诉条数',
			field : 'complainCount',
		}, {
			title : '投诉比(百万)',
			field : 'complainRate',
		}],
		onLoadSuccess: function () {
			var sUserAgent = navigator.userAgent.toLowerCase();
		    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
		    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
		    var bIsAndroid = sUserAgent.match(/android/i) == "android";
		    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
		    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
		    	$(".bootstrap-table").attr("style","margin-left: 43px;");
		    	$(".panel-default").attr("style","margin-left: 43px;");
		    }                
		},
		locale : 'zh-CN',// 中文支持
	})
	$("#search_btn").click(function() {
			
			$('#appcomplaintab').bootstrapTable('refreshOptions', {
				pageNumber : 1,
				url : "findListByName.action"
				});
	});
		
	$("#search_back").click(function() {
		$("#companyName").val(''),
		$("#logDate").val(currentTime()),
		$("select.selectpicker").each(function(){
    		$(this).selectpicker('val',$(this).find('option:first').val());    //重置bootstrap-select显示
    		$(this).find("option").attr("selected", false);                    //重置原生select的值
    		$(this).find("option:first").attr("selected", true);
    	});
		$('#appcomplaintab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
			sort : '',
			order : 'asc'
		});

	});
	
	$('#logDate').datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm',// 显示格式
		minView : "year",// 设置只显示到年份
		startView : "year",
		todayHighlight : true,// 高亮显示当前日期
		initialDate : new Date(),
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	});

	document.getElementById("logDate").value = currentTime();

})


function tableHeight() {
	return $(window).height() - 80;
}


function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		companyName : $("#companyName").val(),
		logDate : $("#logDate").val()

	}
}


function currentTime() {
	var today = new Date();
	var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1);
	// 对日期格式进行处理
	var date = new Date(nowdate);
	var mon = date.getMonth() + 1;
	var day = date.getDate();
	var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon);
	return nowdate;
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


