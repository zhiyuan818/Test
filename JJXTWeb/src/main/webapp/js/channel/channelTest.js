$(function() {
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$("#content").keyup(function() {
		var size = $(this).val().length;
		var sum = 0;
		if (size > 70) {
			sum = Math.ceil(size / 67);
		} else {
			sum = 1;
		}
		$("#help").html("短信内容 已输入字符" + size + " 个，按 " + sum + "条计费");
	});
	validatorSubmit();
	$("#channel_test").click(function() {
		var destNumber = $("#destNumber").val();
		var channelId = $("#channelId").val();
		var extNumber = $("#extNumber").val();
		var content = $("#content").val();
		$('#addForm').bootstrapValidator('validate');
		if ($("#addForm").data('bootstrapValidator').isValid()) {
		$.ajax({
			url : "channelTestSubmit.action",
			type : "post",
			data : {
				channelId : channelId,
				extNumber : extNumber,
				destNumber : getSplitString(destNumber),
				content : content
			},
			success : function(obj) {
				obj=obj.status;
				if(obj==0){
					$('.popup_de .show_msg').text('提交成功');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2001'){
					$('.popup_de .show_msg').text('帐户余额不足');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2002'){
					$('.popup_de .show_msg').text('帐户已被禁用');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2003'){
					$('.popup_de .show_msg').text('帐户不在服务时间');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2010'){
					$('.popup_de .show_msg').text('帐户查询操作过于频繁');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2101'){
					$('.popup_de .show_msg').text('产品未配置');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2102'){
					$('.popup_de .show_msg').text('产品已暂停');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2103'){
					$('.popup_de .show_msg').text('不在产品服务时间');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2104'){
					$('.popup_de .show_msg').text('产品不是一种短信或彩信');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2201'){
					$('.popup_de .show_msg').text('找不到通道');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2202'){
					$('.popup_de .show_msg').text('通道不在服务中');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2301'){
					$('.popup_de .show_msg').text('缺少目的手机号码');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2302'){
					$('.popup_de .show_msg').text('目的手机号码无效');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2303'){
					$('.popup_de .show_msg').text('http包超长');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2401'){
					$('.popup_de .show_msg').text('扩展号码无效');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2402'){
					$('.popup_de .show_msg').text('错误时间格式');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2501'){
					$('.popup_de .show_msg').text('缺少内容');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2502'){
					$('.popup_de .show_msg').text('内容超出限制');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2503'){
					$('.popup_de .show_msg').text('内容中缺少签名');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2504'){
					$('.popup_de .show_msg').text('由于关键字而被拒绝');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2505'){
					$('.popup_de .show_msg').text('目的地数量与个性化消息的内容数量不匹配');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else if(obj=='-2999'){
					$('.popup_de .show_msg').text('未知');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else{
					$('.popup_de .show_msg').text('连接失败');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}
			},
			error : function() {
				console.log("channel test error");
			},
			dataType:'json'
		});
		}else{
			console.log("asd");
		}
	});
})
function validatorSubmit(){
		$("#addForm").bootstrapValidator({
	        feedbackIcons: {//根据验证结果显示的各种图标  
	            valid: 'glyphicon glyphicon-ok',  
	            invalid: 'glyphicon glyphicon-remove',  
	            validating: 'glyphicon glyphicon-refresh'  
	        },  
	        fields: {
	        	channelId:{
	        		validators: {
	        			notEmpty : {
							message : '通道不能为空'
						}
	        		}
	        	},
	        	content:{
	        		validators: {
	        			notEmpty : {
	        				message : '内容不能为空'
	        			}
	        		}
	        	},
	        	extNumber:{
	        		validators:{
						stringLength : {
							max : 12,
							message : '扩展号最长为12位'
						},
						regexp : {
							regexp : /^[0-9]*$/,
							message : '必须是数字'
						}
	        		}
	        	},
	        	destNumber: {
	        		validators: {
            		   callback:{
            			   message:'必须为手机号码! 并且数量不超过100个',
                           callback:function(value, validator,$field){
                        	   var arr = value.split(",");
                        	   var num=0;
                        	   for (var i = 0; i < arr.length; i++) {
                        		   var arr1 = arr[i].split(/\s+/);
                        		   for (var j = 0; j < arr1.length; j++) {
                        			   num++;
                        			   if (jQuery.trim(arr1[j]) == "" || !checkMobile(jQuery.trim(arr1[j]))) {
                        				   return false; 
                        			   }
                        		   }
                        	   }
                        	   if(num >100){
                        		   return false;
                        	   }
                        	   return true;
                           }
            		   },
			        	notEmpty : {
							message : '手机号不能为空'
						}
	        		}
	        	}
	        }
	        
		}); 
	}
function checkMobile(s){  
    var length = s.length;  
//    if(length == 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|)+\d{8})$/.test(s) )   
    if(length == 11 && /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/.test(s)){
    	return true; 
    }
    if(/^(([+]|[0]{2})[0-9]*)$/.test(s)){
    	return true; 
    }
	return false;   
}  
function getSplitString(str) {
	var arr = str.split(",");

	var resources = "";
	for (var i = 0; i < arr.length; i++) {
		var arr1 = arr[i].split(/\s+/);

		for (var j = 0; j < arr1.length; j++) {
			if (jQuery.trim(arr1[j]) != "") {
				resources += jQuery.trim(arr1[j]) + ",";
			}
		}
	}
	resources = resources.substr(0, resources.length - 1);
	return resources;
}