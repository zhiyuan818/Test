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
		url : "findChaSattistics.action",
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
		columns : [{
			title : '日期',
			field : 'logDate',
			formatter : function(value, row, index) {
				return changeLogDateFormat(value)
			}
		}, {
			title : '通道名称',
			field : 'channelId',
			formatter : splicingLocation
		}, {
			title : '供应商',
			field : 'supplierKey'
		}, {
			title : '商务',
			field : 'head'
		},{
			title : '账号id',
			field : 'appId',
		}, {
			title : '提交总数',
			field : 'sendTotal',
		}, {
			title : '提交计费总数',
			field : 'sendTotalCharge',
		}, {
			title : '提交成功',
			field : 'sendSuccessCharge',
		}, {
			title : '提交失败',
			field : 'sendFailCharge',
		}, {
			title : '状态成功',
			field : 'reportDelivrdCharge',
			formatter : changeToBlue
		}, {
			title : '状态失败',
			field : 'reportUndelivCharge',
			formatter : changeToBlue
			
		}, {
			title : '状态未知',
			field : 'reportUnknownCharge',
			formatter : changeToBlue
		}, {
			title : '驳回',
			field : 'rejectCharge',
			formatter : changeToRed
		}, {
			title : '黑名单',
			field : 'reportBlackCharge',
			formatter : changeToRed
		}, {
			title : '成功率',
			field : 'delivrdRate',
			formatter : changeColor
			
		}, {
			title : '时间',
			field : 'updateTime',
			formatter : changeDateFormat
		},],
		onLoadSuccess: function (data) {
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
		    if ($("input[name='statisticType']:checked").val() == 'no'){
		    	$('#mytab').bootstrapTable('hideColumn','logDate');
		    }else{
		    	$('#mytab').bootstrapTable('showColumn','logDate');
		    }
			if($("input[name='statisticType']:checked").val() == 'yes1'){
				$('#mytab').bootstrapTable('showColumn','appId');
			}else {
				$('#mytab').bootstrapTable('hideColumn','appId');
			}
		    var row = data.rows;
		    for(var i=0;i<row.length;i++){
		    	if(!passed('显示优享通道') && row[i].channelId == '347'){
		    		row.splice(i,1);
		    	}
		    }
		    $('#mytab').bootstrapTable('load',data);
		},
		locale : 'zh-CN',// 中文支持
	})
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#startTime").val(currentTime()),
    	$("#endTime").val(currentTime()),
		$("#channelId").val(''),// 通道id
		$("#supplierId").val(''),
		$("#head").val(''),
		$("[name='statisticType']").first().prop("checked",true);
    	$("[name='statisticType']").first().parent().addClass("active");
    	$("[name='statisticType']").first().parent().next().removeClass("active");
    	$("[name='statisticType']").last().parent().removeClass("active");
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
			sort : '',
			order : 'asc'
		});

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
	
//	$('#logDate').datetimepicker({
//		language : 'zh-CN',// 显示中文
//		format : 'yyyy-mm-dd',// 显示格式
//		minView : "month",// 设置只显示到月份
//		initialDate : new Date(),
//		autoclose : true,// 选中自动关闭
//		todayBtn : true,// 显示今日按钮
//		locale : moment.locale('zh-cn')
//	});
//	document.getElementById("logDate").value = currentTime();

})



function splicingLocation(value, row, index) {
	if(value == null && row.channelName != null){
		return row.channelName;
	}
	var name = "";
	name = value + ":" + row.channelName;
	return name;
}


function changeColor(value, row, index) {
	var result = "";
	if(value != null){
		
		var percentage = value.replace("%","");
		result += Number(percentage)<Number(90)?"<font color='#ff0000'>"+value+"</font>":value;
	}
		
	return result;
}


function changeToBlue(value, row, index) {
	var result = "";
	if(value != null){
		
		result += "<font color='#6B8E23'>"+value+"</font>";
	}
	return result;
}

function changeToRed(value, row, index) {
	var result = "";
	if(value != null){
		
		result += "<font color='#cc3300'>"+value+"</font>";
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
		startTime : $("#startTime").val(),// 开始时间
		endTime : $("#endTime").val(),// 结束时间
		supplierId : $("#supplierId").val(),
		head : $("#head").val(),
		statisticType : $("input[name='statisticType']:checked").val(),

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
function changeLogDateFormat(cellval) {
	var dateVal = cellval + "";
	if (cellval != null) {
		var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(
				")/", ""), 10));
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1)
				: date.getMonth() + 1;
		var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date
				.getDate();

		return date.getFullYear() + "-" + month + "-" + currentDate;
	}
	return "";
}

function changeDateFormat(cellval) {
	var dateVal = cellval + "";
	if (cellval != null) {
		var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(
				")/", ""), 10));
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1)
				: date.getMonth() + 1;
		var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date
				.getDate();

		var hours = date.getHours() < 10 ? "0" + date.getHours() : date
				.getHours();
		var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
				.getMinutes();
		var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
				.getSeconds();

		return date.getFullYear() + "-" + month + "-" + currentDate + " "
				+ hours + ":" + minutes + ":" + seconds;
	}
	return "";
}

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

