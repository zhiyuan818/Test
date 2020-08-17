$(function() {

	$("#btn_searchMT").click(
			function() {
				var logDate = $("#logDate").val();
				var appId = $("#appId").val();
				var provider = $("#provider").val();
				var beginTime = $("#beginTime").val();
				var endTime = $("#endTime").val();
				var sendFlag = $("#sendFlag").val();
				var uniqueId = $("#uniqueId").val();
				var channelMsgId = $("#channelMsgId").val();
				var channelId = $("#channelId").val();
				var destNumber = $("#destNumber").val();
				var sourceSegment = $("#sourceSegment").val();
				var content = $("#content").val();
				if (logic(logDate, appId, provider, beginTime, endTime,
						sendFlag, uniqueId, channelMsgId, channelId,
						destNumber, sourceSegment, content)) {
					$.post("searchMTNumber.action", {
						logDate : logDate,
						appId : appId,
						provider : provider,
						beginTime : beginTime,
						endTime : endTime,
						sendFlag : sendFlag,
						uniqueId : uniqueId,
						channelMsgId : channelMsgId,
						channelId : channelId,
						destNumber : destNumber,
						sourceSegment : sourceSegment,
						content : content
					}, function(obj) {
						$('.popup_de .show_msg').text('符合重推条件的条数：' + obj);
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$("#number").attr("max", obj);
						$("#number").val(obj);
						$("#srcNumber").val(obj);
					}, "json"

					);
				} else {
					console.log("error");
					return false;
				}

			});
	$("#btn_searchMO")
			.click(
					function() {
						var logDate = $("#logDate").val();
						var appId = $("#appId").val();
						var provider = $("#provider").val();
						var beginTime = $("#beginTime").val();
						var endTime = $("#endTime").val();
						var sendFlag = $("#sendFlag").val();
						var uniqueId = $("#uniqueId").val();
						var channelMsgId = $("#channelMsgId").val();
						var channelId = $("#channelId").val();
						var destNumber = $("#destNumber").val();
						var sourceSegment = $("#sourceSegment").val();
						var content = $("#content").val();
						if (isNotExist(logDate)) {
							$('.popup_de .show_msg').text('日期不能为空！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						if (!((isNotExist(beginTime) && isNotExist(endTime)) || (!isNotExist(beginTime) && !isNotExist(endTime)))) {
							$('.popup_de .show_msg').text('开始时间、结束时间必须同时填写！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						if (!isNotExist(destNumber)) {
							if (isNotExist(appId)) {
								$('.popup_de .show_msg').text('按手机号重推需填写账户');
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
										})
								return false;
							}
						}
						$.post("searchMONumber.action", {
							logDate : logDate,
							appId : appId,
							provider : provider,
							beginTime : beginTime,
							endTime : endTime,
							sendFlag : sendFlag,
							uniqueId : uniqueId,
							channelMsgId : channelMsgId,
							channelId : channelId,
							destNumber : destNumber,
							sourceSegment : sourceSegment,
							content : content
						}, function(obj) {
							$('.popup_de .show_msg').text('符合重推条件的条数：' + obj);
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$("#number").attr("max", obj);
							$("#number").val(obj);
							$("#srcNumber").val(obj);
						}, "text"

						);

					});
	$("#btn_rep")
			.click(
					function() {
						var logDate = $("#logDate").val();
						var appId = $("#appId").val();
						var provider = $("#provider").val();
						var beginTime = $("#beginTime").val();
						var endTime = $("#endTime").val();
						var sendFlag = $("#sendFlag").val();
						var uniqueId = $("#uniqueId").val();
						var channelMsgId = $("#channelMsgId").val();
						var channelId = $("#channelId").val();
						var destNumber = $("#destNumber").val();
						var sourceSegment = $("#sourceSegment").val();
						var content = $("#content").val();
						var destSendFlag = $("#destSendFlag").val();
						var reportStatus = $("#reportStatus").val();
						var seconds = $("#seconds").val();
						var number = $("#number").val();
						var srcNumber = $("#srcNumber").val();
						if (logic(logDate, appId, provider, beginTime, endTime,
								sendFlag, uniqueId, channelMsgId, channelId,
								destNumber, sourceSegment, content)) {
							if (!((isNotExist(destSendFlag)
									&& isNotExist(reportStatus) && isNotExist(seconds)) || (!isNotExist(destSendFlag)
									&& !isNotExist(reportStatus) && !isNotExist(seconds)))) {
								$('.popup_de .show_msg').text(
										'状态报告,目标标记,秒数需要同时存在');
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
										})
								return false;
							}
							if (isNotExist(number)) {
								('.popup_de .show_msg').text('数量不能为空');
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
										})
								return false;
							}
							if (number <= 0 || number > srcNumber) {
								$('.popup_de .show_msg').text(
										'数量大小在0~' + srcNumber + "之间");
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
										})
								return false;
							}
							$.post("pushRepState.action", {
								logDate : logDate,
								appId : appId,
								provider : provider,
								beginTime : beginTime,
								endTime : endTime,
								sendFlag : sendFlag,
								uniqueId : uniqueId,
								channelMsgId : channelMsgId,
								channelId : channelId,
								destNumber : destNumber,
								sourceSegment : sourceSegment,
								content : content,
								destSendFlag : destSendFlag,
								reportStatus : reportStatus,
								seconds : seconds,
								number : number
							}, function(obj) {
								if (obj.result == '0') {
									$('.popup_de .show_msg').text('重推状态报告成功!');
									$('.popup_de .btn_cancel').css('display',
											'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one(
											'click',
											function() {
												$('.popup_de').removeClass(
														'bbox');
												location.reload();
											})
								} else {
									$('.popup_de .show_msg').text('重推状态报告失败!');
									$('.popup_de .btn_cancel').css('display',
											'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one(
											'click',
											function() {
												$('.popup_de').removeClass(
														'bbox');
												location.reload();
											})
								}
							}, "json"

							);
						} else {
							console.log("rep error");
						}
					});
	$("#btn_mo")
			.click(
					function() {
						var logDate = $("#logDate").val();
						var appId = $("#appId").val();
						var provider = $("#provider").val();
						var beginTime = $("#beginTime").val();
						var endTime = $("#endTime").val();
						var sendFlag = $("#sendFlag").val();
						var uniqueId = $("#uniqueId").val();
						var channelMsgId = $("#channelMsgId").val();
						var channelId = $("#channelId").val();
						var destNumber = $("#destNumber").val();
						var sourceSegment = $("#sourceSegment").val();
						var content = $("#content").val();
						var destSendFlag = $("#destSendFlag").val();
						var reportStatus = $("#reportStatus").val();
						var seconds = $("#seconds").val();
						var number = $("#number").val();
						var srcNumber = $("#srcNumber").val();
						if (isNotExist(logDate)) {
							$('.popup_de .show_msg').text('日期不能为空！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						if (!((isNotExist(beginTime) && isNotExist(endTime)) || (!isNotExist(beginTime) && !isNotExist(endTime)))) {
							$('.popup_de .show_msg').text('开始时间、结束时间必须同时填写！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						if (!isNotExist(destNumber)) {
							if (isNotExist(appId)) {
								$('.popup_de .show_msg').text('按手机号重推需填写账户');
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
										})
								return false;
							}
						}
						if (!((isNotExist(destSendFlag)
								&& isNotExist(reportStatus) && isNotExist(seconds)) || (!isNotExist(destSendFlag)
								&& !isNotExist(reportStatus) && !isNotExist(seconds)))) {
							$('.popup_de .show_msg').text('状态报告,目标标记,秒数需要同时存在');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						if (isNotExist(number)) {
							('.popup_de .show_msg').text('数量不能为空');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						if (number <= 0 || number > srcNumber) {
							$('.popup_de .show_msg').text(
									'数量大小在0~' + srcNumber + "之间");
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							return false;
						}
						$.post("putMo.action", {
							logDate : logDate,
							appId : appId,
							beginTime : beginTime,
							endTime : endTime,
							channelMsgId : channelMsgId,
							channelId : channelId,
							destNumber : destNumber,
							sourceSegment : sourceSegment,
							content : content,
							number : number
						}, function(obj) {
							if (obj.result == '0') {
								$('.popup_de .show_msg').text('重推上行成功!');
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
											location.reload();
										})
							} else {
								$('.popup_de .show_msg').text('重推上行失败!');
								$('.popup_de .btn_cancel').css('display',
										'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',
										function() {
											$('.popup_de').removeClass('bbox');
											location.reload();
										})
							}
						}, "json"

						);
					});
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

	$("#beginTime").datetimepicker({
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
		$('#beginTime').datetimepicker('setEndDate', endTime);
	});
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
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
	return nowdate;
}
function logic(logDate, appId, provider, beginTime, endTime, sendFlag,
		uniqueId, channelMsgId, channelId, destNumber, sourceSegment, content) {
	if (isNotExist(logDate)) {
		$('.popup_de .show_msg').text('日期不能为空！');
		$('.popup_de .btn_cancel').css('display', 'none');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
		return false;
	}
//	if (isNotExist(appId) && isNotExist(uniqueId) && isNotExist(content)) {
//		$('.popup_de .show_msg').text('账户,内容,内容ID至少有一个不为空');
//		$('.popup_de .btn_cancel').css('display', 'none');
//		$('.popup_de').addClass('bbox');
//		$('.popup_de .btn_submit').one('click', function() {
//			$('.popup_de').removeClass('bbox');
//		})
//		return false;
//	}
	if (!((isNotExist(beginTime) && isNotExist(endTime)) || (!isNotExist(beginTime) && !isNotExist(endTime)))) {
		$('.popup_de .show_msg').text('开始时间、结束时间必须同时填写！');
		$('.popup_de .btn_cancel').css('display', 'none');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
		return false;
	}
	if (!isNotExist(destNumber)) {
		if (isNotExist(appId)) {
			$('.popup_de .show_msg').text('按手机号重推需填写账户');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return false;
		}
	}
	return true;
}
function isNotExist(value) {
	if (value == "" || value == null || value == undefined) {
		return true;
	}
	return false;
}