$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#motab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	$('#motab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findMOPageList.action",
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
		toolbar:'#toolbar',//指定工作栏
		columns : [ {
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'ID',
			field : 'id',
			visible : false

		}, {
			title : '通道',
			field : 'channelName',
			sortable : true
		}, {
			title : '账户',
			field : 'appName',
		}, {
			title : '源号码',
			field : 'srcNumber',
		}, {
			title : '归属地',
			field : 'province',
			formatter : splicingLocation

		}, {
			title : '城市',
			field : 'city',
			visible : false

		}, {
			title : '目的号码',
			field : 'destNumber',
		}, {
			title : '时间',
			field : 'receiveTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}, {
			title : '内容',
			field : 'content',
			formatter : changeContent
		}, {
			title : '模块路径',
			field : 'logicPath'
		}, {
			title : '操作',
			field : 'blackFlag',
			width : 20,
			align : 'center',
			valign : 'middle',
			formatter : yesOrNoBlack
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
		$('#motab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#logDate").val(currentTime()),// 日志日期
		$("#appId").val(''),// 账户名
		$("#srcNumber").val(''),// 源号码
		$("#content").val(''),// 内容
		$("#channelId").val(''),// 通道id
		$("#startTime").val(''),// 开始时间
		$("#endTime").val(''),// 结束时间
		$("#destNumber").val(''),// 目的号码
		$("#city").val(''),// 地区
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
		$('#motab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
			sort : '',
			order : 'asc'
		});

	});

})

function splicingLocation(value, row, index) {
	var location = "";
	location = value + " " + row.city;
	return location;
}

function changeContent(value, row, index) {
	var content = value;
	if (content != null && content.length > 10) {
		var result = "<a href='javascript:;' data-toggle='modal'  data-target='#Details' onclick=\"findDetails('" + value + "')\" title='详情'>" + content.substring(0, 10) + "..." + "</a>";
		return result;
	}
	return content;
}

function findDetails(row) {
	$("#Details_content").html(row);
}

function tableHeight() {
	return $(window).height() - 110;
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		logDate : $("#logDate").val(),// 日志日期
		appId : $("#appId").val(),// 账户名
		srcNumber : $("#srcNumber").val(),// 源号码
		content : $("#content").val(),// 内容
		channelId : $("#channelId").val(),// 通道id
		startTime : $("#startTime").val(),// 开始时间
		endTime : $("#endTime").val(),// 结束时间
		destNumber : $("#destNumber").val(),// 目的号码
		city : $("#city").val()
	// 地区
	}
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


function addToBlack(id) {
	mark = true;
	if (passed('加黑')) {
		$('.popup_de .show_msg').text('确定要添加到二级黑吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				var logDate = $("#logDate").val();
				$.post("findMOById.action", {
					'id' : id,
					logDate : logDate
				}, function() {
					$('#motab').bootstrapTable('refresh', {
						url : 'findMOPageList.action'
					});
				}, "json");
				$('.popup_de').removeClass('bbox');
				mark = false;
			}
		})

	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de .btn_cancel').css('display', 'none');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}

}

function yesOrNoBlack(value, row, index) {
	var result = "";
	if (value == 'yes') {
		result += "<button class='btn btn-primary btn-xs' id='over_black' disabled='disabled' title='已黑'>已黑</button>";

	} else {
		var id = row.id;
		result += "<button class='btn btn-primary btn-xs' id='add_black' onclick=\'addToBlack("
				+ row.id + ")'\ title='点击加黑'>加黑</button>";

	}
	return result;
}

$(function() {
	$('#btn_black').click(function() {
		mark = true;
		if (passed('批量加黑')) {
			var dataArr = $('#motab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要添加到二级黑吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				if (mark) {
					var logDate = $("#logDate").val();
					var ID = [];
					var i = 0;
					for (i; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						$.post("addToBlackLevel2Batch.action",
								{ids : ID,logDate : logDate},
								function(data) {
									if (data.result == 1) {
										$('.popup_de .show_msg').text('添加二级黑成功！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
										$('#motab').bootstrapTable('refresh',{url : 'findMOPageList.action'});
									} else {
										$('.popup_de .show_msg').text('添加失败！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
										$('#motab').bootstrapTable('refresh',{url : 'findMOPageList.action'});
									}
								})
							}
							mark = false;
						}
					})
				} else {
					$('.popup_de .show_msg').text('没有权限，请联系管理员！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}
			})
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 批量加黑按钮的出现与消失
	$('.table').change(function() {
		var dataArr = $('#motab .selected');
		if (dataArr.length >= 1) {
			$('#btn_black').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_black').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_black').css('display', 'none');
			}, 400);
		}
	});
	
	$("[name='btSelectAll']").click(function(){
		var dataArr = $('#motab .selected');
		if (dataArr.length >= 1) {
			$('#btn_black').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_black').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_black').css('display', 'none');
			}, 400);
		}
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
