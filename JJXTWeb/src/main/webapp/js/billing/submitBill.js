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
	
	$('#billDate').datetimepicker({
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
	
	
	
	// 点击菜单页面提交按钮
	$('#btn_billing').click(function() {

		
		mark=true;
		// 点击保存时触发表单验证
		$('#billForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#billForm").data('bootstrapValidator').isValid()) {
				var _info = $('#billForm').serialize();
				data = decodeURIComponent(_info, true);
				console.log(data);
				if(mark){
					$.post("submitBill.action", data, function(data) {
						if (data.result == 0) {
							$('.popup_de .show_msg').text('账单提交成功');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})

						} else if(data.result == 1) {
							$('.popup_de .show_msg').text('提交失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						} else if(data.result == 2) {
							$('.popup_de .show_msg').text('账户验证失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						} else if(data.result == 3) {
							$('.popup_de .show_msg').text('缺少参数');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						} else if(data.result == 4) {
							$('.popup_de .show_msg').text('提交超时');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						}
						$("#billForm")[0].reset();
						$("#tableBody").modal('hide');
						$("#billForm").data('bootstrapValidator').destroy();
						$('#billForm').data('bootstrapValidator', null);
						sumbitValidator();
						$("#appName").selectpicker('refresh');
					})
					mark=false;
				}
		}
	});
	
	sumbitValidator();
//	$("#btn_billing").click(function(){
//		var companyId=$("#companyId").val();
//		var appName=$("#appName").val();
//		var billDate=$("#billDate").val();
//		var successCount=$("#successCount").val();
//		var unknownCount=$("#unknownCount").val();
//		var count=$("#count").val();
//		var cost=$("#cost").val();
//		
//		sumbitValidator();
//		
//
//	
//		location.href="submitBill.action?companyId="+companyId+"&appName="+appName+"&billDate="+encodeURI(billDate)+"&successCount="+successCount+"&unknownCount="+unknownCount
//		+"&count="+count+"&cost="+cost;
//	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})


function sumbitValidator() {
	$('#billForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appName : {
				validators : {
					notEmpty : {
						message : '账户不能为空'
					},
					callback:{
	         			   message:'账单提交暂不支持该账户',
	                        callback:function(value, validator,$field){
	                     	   var companyId = $('#companyId').val();
	                     	   var appName = $('#appName').val();
	                     	   
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'validatorBillAccount.action',
	                     				data:{
	                     					companyId:companyId,
	                     					appName:appName
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     					console.log(flag);
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   console.log(flag);
	                     		   return flag;
	                     	   
	                        }
						}
				}
			},
			successCount : {
				validators : {
					notEmpty : {
						message : '状态成功总数不能为空'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '必须是数字'
					}
				}
			},
			unknownCount : {
				validators : {
					notEmpty : {
						message : '未知总数不能为空'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '必须是数字'
					}
				}
			},
			count : {
				validators : {
					notEmpty : {
						message : '实际计费条数不能为空'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '必须是数字'
					}
				}
			},
			cost : {
				validators : {
					notEmpty : {
						message : '费用不能为空'
					},
					regexp : {
						regexp : /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
						message : '最多保留两位小数'
					}
				}
			}
			
		}
	});
}


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