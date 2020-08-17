$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#mytab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	// 生成用户数据
	$('#mytab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findMTPageList.action",// 请求后台url
		height : tableHeight(),// 高度调整
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
		pageList : [ 50, 100, 200 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [ {
			title : '通道',
			field : 'channelName',
			formatter : changeContent,
			width : 90
		}, {
			title : '标记',
			field : 'sendFlag',
			width : 60
		}, {
			title : '通道消息ID',
			field : 'channelMsgId',
			width : 60,
			formatter : changeContent
		}, {
			title : '账号提交',
			field : 'logTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			},
			width : 70
		}, {
			title : '平台提交',
			field : 'submitTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			},
			width : 70
		}, {
			title : '回执时间',
			field : 'reportTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			},
			width : 70
		}, {
			title : '账号',
			field : 'appName',
			width : 40,
			formatter : function(value, row, index) {
				return "<a href='javascript:;' onclick=\"findCompany('"+row.appId+ "')\">"+ value + "</a>";
			}
		}, {
			title : '号码',
			field : 'destNumber',
			width : 90
		}, {
			title : '扩展号',
			field : 'extSrc',
			width : 50
		}, {
			title : '回执',
			field : 'reportStatus',
			formatter : function(value, row, index) {
				if (value == 'DELIVRD') {
					return value;
				} else if (value == null) {
					return "";
				}
				return "<font color='red'>" + value + "</font>";
			},
			width : 50
		}, {
			title : '省份',
			field : 'province',
			width : 40
		}, {
			title : '内容',
			field : 'content',
			align : 'center',
			valign : 'middle',
			formatter : actionFormatter
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
		locale : 'zh-CN'// 中文支持,
	})

	$("#search_btn").click(function() {
		var searchDate = new Date($("#logDate").val());
		searchDate.setHours(0);
		searchDate.setMinutes(0);
		searchDate.setSeconds(0);
		var beginDate = new Date("2019-03-01");
		beginDate.setHours(0);
		beginDate.setMinutes(0);
		beginDate.setSeconds(0);
		
		if(searchDate.getTime() <=  new Date().getTime() && searchDate.getTime() >= beginDate.getTime()){
			$('#mytab').bootstrapTable('refreshOptions', {
				pageNumber : 1
			});
		}else{
			$('.popup_de .show_msg').text('查询日期超过范围！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	$("#search_back").click(function() {
		$("#logDate").val(currentTime()),// 日志日期
		$("#appId").val(''),// 账户
		$("#destNumber").val(''),// 号码
		$("#channelMsgId").val(''),// 通道消息ID
		$("#reportStatus").val(''),// 回执
		$("#sendFlag").val(''),// 标识
		$("#sign").val(''),// 内容
		$("#channelId").val(''),// 通道ID
		$("#province").val(''),// 省份
		$("#submitStatus").val(''),// 提交状态
		$("#reportStatus1").val(''),// 回执状态
		$("#startTime").val(''),// 开始时间
		$("#endTime").val(''),// 结束时间
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
})

function tableHeight() {
	return $(window).height() - 120;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		logDate : $("#logDate").val(),// 日志日期
		appId : $("#appId").val(),// 账户
		destNumber : $("#destNumber").val(),// 号码
		channelMsgId : $("#channelMsgId").val(),// 通道消息ID
		reportStatus : $("#reportStatus").val(),// 回执
		sendFlag : $("#sendFlag").val(),// 标识
		sign : $("#sign").val(),// 签名
		channelId : $("#channelId").val(),// 通道ID
		province : $("#province").val(),// 省份
		submitStatus : $("#submitStatus").val(),// 提交状态
		reportStatus1 : $("#reportStatus1").val(),// 回执状态
		startTime : $("#startTime").val(),// 开始时间
		endTime : $("#endTime").val(),// 结束时间
	}
}
function changeDateFormat(cellval) {
	var dateVal = cellval + "";
	if (cellval != null) {
		var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(
				")/", ""), 10));
		var hours = date.getHours() < 10 ? "0" + date.getHours() : date
				.getHours();
		var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
				.getMinutes();
		var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
				.getSeconds();

		return hours + ":" + minutes + ":" + seconds;
	}
	return "";
}
function changeDateFormatBeYmdhms(cellval) {
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

function changeContent(value) {
	var content = value;
	if(content == '' || content == null || content == undefined){
		return content;
	}
	if (content.length > 10) {
		return content.substring(0, 10) + "···";
	}
	return content;
}
function actionFormatter(value, row, index) {
	var content = value;
	if(content == '' || content == null || content == undefined){
		return "<a href='javascript:;' onclick=\"findDetails('"+row.linkId+ "')\" title='详情'>正在加载中……</a>";
	}
	if (content.length > 70) {
		return "<a href='javascript:;' onclick=\"findDetails('"+row.linkId+ "')\" title='详情'>"+content.substring(0, 70) + "···</a>";
	}
	return "<a href='javascript:;' onclick=\"findDetails('"+row.linkId+ "')\" title='详情'>"+ content + "</a>";;
}
function findDetails(value) {
	var linkId = value;
	var logDate = $("#logDate").val();// 日志日期
	$.ajax({
			url : "findMTByLinkId.action",
			data : {
				linkId : linkId,
				logDate : logDate
			},
			dataType : "json",
			type : "post",
			success : function(result) {
				$("#Details_app_name").html(result.appName);
				$("#Details_app_id").html(result.appId);
				$("#Details_dest_number").html(result.destNumber);
				$("#Details_province").html(result.province);
				$("#Details_city").html(result.city);
				$("#Details_provider").html(result.provider);
				$("#Details_log_time").html(
						changeDateFormatBeYmdhms(result.logTime));
				$("#Details_send_flag").html(result.sendFlag);
				$("#Details_src_number").html(result.srcNumber);
				$("#Details_ext_src").html(result.extSrc);
				$("#Details_channel_name").html(result.channelName);
				$("#Details_channel_id").html(result.channelId);
				$("#Details_channel_msg_id").html(result.channelMsgId);
				$("#Details_lm_total").html(result.lmTotal);
				$("#Details_message_class").html(result.messageClass);
				$("#Details_product_id").html(result.productId);
				$("#Details_submit_time").html(
						changeDateFormatBeYmdhms(result.submitTime));
				$("#Details_submit_status").html(result.submitStatus);
				$("#Details_report_time").html(
						changeDateFormatBeYmdhms(result.reportTime));
				$("#Details_report_status").html(result.reportStatus);
				$("#Details_link_id").html(result.linkId);
				$("#Details_logic_path").html(result.logicPath);
				$("#Details_content").html(result.content);
				$("#Details_message_id").html(result.messageId);
				$("#Details_unique_id").html(result.uniqueId);
				$("#Details_batch_id").html(result.batchId);
			}
		});
	$("#Details").modal();
}
function findCompany(appId){
	$.ajax({
		url:"findCompanyDetails.action",
		data:{appId:appId},
		dataType : "json",
		type : "post",
		success:function(obj){
			$("#Details_company_key").html(obj.companyKey);
			$("#Details_company_name").html(obj.companyName);
			$("#company").modal();
		}
	});
}
$(function() {
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$('#logDate').datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd',// 显示格式
		minView : "month",// 设置只显示到月份
		initialDate : new Date(),
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	});
	document.getElementById("logDate").value = currentTime();

	$("#startTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('changeDate', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var startTime = event.date;
		$('#endTime').datetimepicker('setStartDate', startTime);
		$('#endTime').val("");
	});
	$("#endTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('changeDate', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var endTime = event.date;
		$('#startTime').datetimepicker('setEndDate', endTime);
	});
})
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
	return mydate;
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