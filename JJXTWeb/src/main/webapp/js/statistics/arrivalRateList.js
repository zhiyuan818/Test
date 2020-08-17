$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#mytab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	$('#mytab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "",
		height : tableHeight(),
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : false,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [ {
			title : '时间(h)',
			field : 'hour',
		}, {
			title : '发送量',
			field : 'sendTotal',
		}, {
			title : '到达（成功）量',
			field : 'reportTotal',
		}, {
			title : '到达（成功）率',
			field : 'reportRate',
		}, {
			title : '0~10s回状态量',
			field : 'five',
			visible : false
		}, {
			title : '0~10s回状态率',
			field : 'fiveRate',
			formatter : splicingFive
		}, {
			title : '11~20s回状态量',
			field : 'ten',
			visible : false
		}, {
			title : '11~20s回状态率',
			field : 'tenRate',
			formatter : splicingTen
		}, {
			title : '21~30s回状态量',
			field : 'fifteen',
			visible : false
		}, {
			title : '21~30s回状态率',
			field : 'fifteenRate',
			formatter : splicingFifteen
		}, {
			title : '31~60s回状态量',
			field : 'twenty',
			visible : false
		}, {
			title : '31~60s回状态率',
			field : 'twentyRate',
			formatter : splicingTwenty
		}, {
			title : '超60s回状态量',
			field : 'more',
			visible : false
		}, {
			title : '超60s回状态率',
			field : 'moreRate',
			formatter : splicingMore
		}, ],
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
		if($("#channelId").val()==("") && $("#appId").val()==("")){
			$('.popup_de .show_msg').text('请选择账户或者通道！');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			
		}else{
			$('#mytab').bootstrapTable('refreshOptions', {url:'findArrivalRate.action'});
		}
		
	});
	
	$('#logDate').datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd',// 显示格式
		minView : "month",// 设置只显示到月份
		todayHighlight : true,// 高亮显示当前日期
		initialDate : new Date(),
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	});
	document.getElementById("logDate").value = currentTime();
	
	//弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function(){
		$('.popup_de').removeClass('bbox');
	})

});


function splicingFive(value, row, index) {
	var result = "";
	if(value != null){
		result = row.five + " (" + value + ")";
	}
	return result;
}

function splicingTen(value, row, index) {
	var result = "";
	if(value != null){
		result = row.ten + " (" + value + ")";
	}
	return result;
}

function splicingFifteen(value, row, index) {
	var result = "";
	if(value != null){
		result = row.fifteen + " (" + value + ")";
	}
	return result;
}

function splicingTwenty(value, row, index) {
	var result = "";
	if(value != null){
		result = row.twenty + " (" + value + ")";
	}
	return result;
}

function splicingMore(value, row, index) {
	var result = "";
	if(value != null){
		result = row.more + " (" + value + ")";
	}
	return result;
}


function tableHeight() {
	return $(window).height() - 80;
}


function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		channelId : $("#channelId").val(),
		logDate : $("#logDate").val(),
		appId : $("#appId").val(),
		rateButton : $("input[name='rateButton']:checked").val()

	}
}


function currentTime() {
	var today = new Date();
	var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-"
			+ today.getDate();
	// 对日期格式进行处理
	var date = new Date(nowdate);
	var mon = date.getMonth() + 1;
	var day = date.getDate();
	var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-"
			+ (day < 10 ? "0" + day : day);
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

