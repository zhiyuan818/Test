$(function() {
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$("#whiteBtn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('白名单为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#accountTr").prop("style","display:none");
			$("#searchType").val("white");
			$("#searchTab").modal();
		}
	});
	$("#black1Btn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('投诉黑名单为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#accountTr").prop("style","display:none");
			$("#searchType").val("black1");
			$("#searchTab").modal();
		}
	});
	$("#black2Btn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('回复黑名单为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#searchType").val("black2");
			$("#accountTr").prop("style","");
			$("#searchTab").modal();
		}
	});
	$("#black3Btn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('普通黑名单为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#accountTr").prop("style","display:none");
			$("#searchType").val("black3");
			$("#searchTab").modal();
		}
	});
	$("#black5Btn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('关键黑名单为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#accountTr").prop("style","display:none");
			$("#searchType").val("black5");
			$("#searchTab").modal();
		}
	});
	$("#shuntBtn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('实号库为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#accountTr").prop("style","display:none");
			$("#searchType").val("shunt");
			$("#searchTab").modal();
		}
	});
	$("#numPortabilityBtn").click(function() {
		var sum = $(this).next().val();
		if (sum == 0) {
			$('.popup_de .show_msg').text('携号转网数据为空！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		} else {
			$("#accountTr").prop("style","display:none");
			$("#searchType").val("numPortability");
			$("#searchTab").modal();
		}
	});

	$("#btn_priSub").click(
			function() {
				var phone = $("#phone").val();
				var searchType = $("#searchType").val();
				var appId = $("#search_appId").val();
				if(searchType == "black2" && (appId == ""||appId == null)){
					$('.popup_de .show_msg').text('账号不能为空！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					return ;
				}
				$.post("queryExist.action", {
					phone : phone,
					searchType : searchType,
					appId:appId
				}, function(obj) {
					var b2list = obj.b2list;
					var result = obj.result;
					var npResult = obj.npResult;
					var prividerResult = obj.prividerResult;
					$("#phone").val('');
					if (result) {
						if(npResult == null || npResult == ''){
						if (b2list.length > 0) {
							$("#infoList").html("");
							for ( var i in b2list) {
								$("#infoList").append(
										"<tr><td>" + b2list[i].appId + ":"
												+ b2list[i].appName
												+ "</td><td>"
												+ b2list[i].phoneNumber
												+ "</td></tr>");
							}
							$("#Black2List").modal();
						}else {
							$('.popup_de .show_msg').text('手机号存在！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						}
						}else{
							$("#npList").html("");
							$("#npList").append(
									"<tr><td>" + phone
											+ "</td><td>"
											+ prividerResult
											+ "</td><td>"
											+ npResult
											+ "</td></tr>");
						
						$("#NumPortabilityList").modal();
						}
					} else {
						$('.popup_de .show_msg').text('未找到该手机号！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
				}, "json");
			});
	$("#btn_close").click(function() {
		$("#phone").val('');
		$("#accountTr").prop("style","display:none");
	});
	$(".cleanAll").click(function() {
		var channelId = $(this).attr('data');
		mark = true;
		if (passed('全部清理')) {
			$('.popup_de .show_msg').text('确定要清理该通道优先级?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de .btn_submit').one('click', function() {
				if (mark) {
					$.post("cleanSubmit.action", {
						channelId : channelId
					}, function(data) {
						if (data.result == 0) {
							$('.popup_de .show_msg').text('清理成功！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						} else {
							$('.popup_de .show_msg').text('清理失败！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						}
						location.reload();
					});
					mark = false;
				}
			})
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
})
// 按钮是否通过
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