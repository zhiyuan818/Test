phoneStatusArr=new Array();
function phoneStatus(phoneNumber,status,appId)
{
	this.phoneNumber=phoneNumber;
	this.status=status;
	this.appId=appId;
}
$(document).ready(function() { 
	//验证手机号
	validatorPhone("#searchForm");
	$("#btn_searchPhone").click(function() {
		$("#searchForm").bootstrapValidator('validate');//提交验证  
        if ($("#searchForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码  
        	//查询后页面显示
    		loadData("#myTab");  
        }  
    });
	
	$("#btn_searchblack2").click(function(){
		$("#searchForm").bootstrapValidator('validate');//提交验证  
        if ($("#searchForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码  
        	var appId = $("#search_appId").val();
        	console.log(appId);
        	if(appId == null || appId == ''){
        		$('.popup_de .show_msg').text('请选择账号!');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
        		return ;
        	}
        	loadData("#myTab");
        }  
	});
	
	function validatorPhone(formId){
		$(formId).bootstrapValidator({ 
//			live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证  
//	        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的  
//	        submitButtons: '#btn_searchPhone',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面  
//	        message: '验证失败',//好像从来没出现过  
	        feedbackIcons: {//根据验证结果显示的各种图标  
	            valid: 'glyphicon glyphicon-ok',  
	            invalid: 'glyphicon glyphicon-remove',  
	            validating: 'glyphicon glyphicon-refresh'  
	        },  
	        fields: {
	        	phoneNumbers: {
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
            		   }
	        
	        		}
	        	}
	        }
	        
		}); 
	}
	
	function loadData(tableId){
		 $(tableId).bootstrapTable('destroy'); 
         $(tableId).bootstrapTable({
         	method: 'post',
         	contentType: "application/x-www-form-urlencoded",
         	url : "searchPhone.action",// 请求后台url
          	dataType:"json",//返回的数据格式
          	cache:false,//是否缓存 默认是true
          	striped: true, //是否显示行间隔色
          	pagination:false,//是否分页
          	queryParams:queryParams,
          	sortable: true, //是否启用排序
             sortOrder: "asc",//排序方式
          	uniqueId: "ID",  //每一行的唯一标识，一般为主键列
          	showRefresh:true,//刷新按钮
          	showColumns:false,//是否显示所有的列
          	clickToSelect: false,//是否启用点击选中行
          	toolbarAlign:'right',
          	buttonsAlign:'right',//按钮对齐方式
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
     			title : '账户名称',
     			field : 'appName',
     			width: 200
     		},  {
     			title : '账户ID',
     			field : 'appId',
     			width: 200
     		}, {
     			title : '手机号码',
     			field : 'phoneNumber',
     			width: 200
     		}, {
     			title : '状态',
     			field : 'status',
     			width: 200,
     			formatter : function(value, row, index) {
         				if (value == 'white') {
         					return "白名单";
         				} else if (value == 'black1') {
         					return "投诉黑名单";
         				} else if(value == 'black2') {
         					return "回复黑名单";
         				}else if(value == 'black3') {
         					return "普通黑名单";
         				}else if(value == 'black5') {
         					return "关键黑名单";
         				}else if(value == 'shunt') {
         					return "实号库";
         				}else if(value == 'empty') {
         					return "无状态";
         				}
         			}
     		}, {
     			title : '操作',
     			field : 'id',
     			width : 200,
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
     		locale : 'zh-CN',// 中文支持
     	})
	}

	
});

$(document).ready(function() { 
	addValidator();
	$("#add_saveBtn").click(function() {
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			var addPhone = $("#addPhone").val();
			var addLevel = $("#addLevel").val();
			$.ajax({
				url : "addPhone.action",
				type : "post",
				async : false,
				data : {
					addPhone : addPhone,
					addLevel : addLevel
				},
				success : function(obj) {
					$("#addBody").modal("hide");
					if (obj.result > 0) {
						$('.popup_de .show_msg').text('新增成功！');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}else if(obj.result == 0){
						$('.popup_de .show_msg').text('手机号码已存在!');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}else{
						$('.popup_de .show_msg').text('新增失败');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}
					$("#add_saveBtn").attr("disabled",false);
				},
				error : function() {
					console.log("error");
				}
			});
		}
	})
	
	$('.add_backBtn').click(function() {
		$("#addForm")[0].reset();
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#add_saveBtn").attr("disabled", false);
		$("#addBody").modal("hide");
	});
	
	//新增回复黑
	addBlack2Validator();
	$("#addBlack2_saveBtn").click(function() {
		$('#addBlack2Form').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addBlack2Form").data('bootstrapValidator').isValid()) {
			var addBlack2Phone = $("#addBlack2Phone").val();
			var addBlack2AppId = $("#addBlack2AppId").val();
			$.ajax({
				url : "addBlack2Phone.action",
				type : "post",
				async : false,
				data : {
					addBlack2Phone : addBlack2Phone,
					addBlack2AppId : addBlack2AppId
				},
				success : function(obj) {
					$("#addBlack2Body").modal("hide");
					if (obj.result > 0) {
						$('.popup_de .show_msg').text('新增二级黑成功！');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}else if(obj.result == 0){
						$('.popup_de .show_msg').text('手机号码已存在!');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}else if(obj.result == -2){
						$('.popup_de .show_msg').text('账户ID不能为空!');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}else{
						$('.popup_de .show_msg').text('新增二级黑失败');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#myTab').bootstrapTable('refresh', {
								url : 'searchPhone.action'
							});
						})
					}
					$("#addBlack2_saveBtn").attr("disabled",false);
				},
				error : function() {
					console.log("error");
				}
			});
		}
	})
		
	$('.addBlack2_backBtn').click(function() {
		$("#addBlack2Form")[0].reset();
		$("#addBlack2Form").data('bootstrapValidator').destroy();
		$('#addBlack2Form').data('bootstrapValidator', null);
		addBlack2Validator();
		$("#addBlack2_saveBtn").attr("disabled", false);
		$("#addBlack2Body").modal("hide");
	});
	
});

$(document).ready(function() { 
	// 批量加黑按钮的出现与消失
	$('.table').change(
			function() {
				var dataArr = $('#myTab .selected');
				if (dataArr.length >= 1) {
					$('#btn_del').css('display', 'block').removeClass(
							'fadeOutRight').addClass('animated fadeInRight');
					$('#btn_addBlack1').css('display', 'block').removeClass(
							'fadeOutRight').addClass('animated fadeInRight');
					$('#btn_addWhite').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
				} else {
					$('#btn_del').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_del').css('display', 'none');
					}, 400);
					$('#btn_addBlack1').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_del').css('display', 'none');
					}, 400);
					$('#btn_addWhite').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_del').css('display', 'none');
					}, 400);
				}
			});
	$("[name='btSelectAll']").click(function(){
		var dataArr = $('#myTab .selected');
		if (dataArr.length >= 1) {
			$('#btn_del').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
			$('#btn_addBlack1').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
			$('#btn_addWhite').css('display', 'block').removeClass(
			'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_del').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del').css('display', 'none');
			}, 400);
			$('#btn_addBlack1').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del').css('display', 'none');
			}, 400);
			$('#btn_addWhite').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del').css('display', 'none');
			}, 400);
		}
	});
	// 批量删除
	$('#btn_del').click(function() {
		mark=true;
		if (passed('删除')) {
			var dataArr = $('#myTab').bootstrapTable('getSelections');
			if(checkEmpty(dataArr)){
				$('.popup_de .show_msg').text('确定要删除吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				$('.popup_de .btn_submit').one('click', function() {
					var array = new Array();
					var i = 0;
					for (i; i < dataArr.length; i++) {
						phoneStatusArr.push(new phoneStatus(dataArr[i].phoneNumber,dataArr[i].status,dataArr[i].appId));
////						array[i]=new Array();
////						array[i][0] = dataArr[i].phoneNumber;
////						array[i][1] = dataArr[i].status;
////						array[i][2] = dataArr[i].appId;
					}
					if (phoneStatusArr.length > 0) {
						if(mark){
							$.ajax({
								url : "delBatch.action",
								dataType : "json",
								type : "post",
								async : false,
								data : {
									//							array : array
									'phoneStatusArr' : JSON.stringify(phoneStatusArr)
								},
								success:function (data){
									
									//					$.post("delBatch.action", {
									//						totals : array
									//					}, function(data) {
									if (data.result >=0) {
										$('.popup_de .show_msg').text('删除成功！');
										$('.popup_de .btn_cancel').css('display', 'none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click', function() {
											$('#btn_del').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_del').css('display', 'none');
											}, 400);
											$('.popup_de').removeClass('bbox');
										})
										$('#myTab').bootstrapTable('refresh', {
											url : 'searchPhone.action'
										});
									} else {
										$('.popup_de .show_msg').text('删除出错！');
										$('.popup_de .btn_cancel').css('display', 'none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click', function() {
											$("#edit_saveBtn").attr("disabled", false);
											$('.popup_de').removeClass('bbox');
										})
										$('#myTab').bootstrapTable('refresh', {
											url : 'searchPhone.action'
										});
									}
									//					}, "json")
								}
							});
							mark=false;
						}
					}
	
				})

			}else{
				$('.popup_de .show_msg').text('无状态数据不能进行删除操作');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click',function(){
					$('.popup_de').removeClass('bbox');
				})
			}
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}

	})
	
	
	 
	// 批量添加投诉黑
	$('#btn_addBlack1').click(function() {
		mark=true;
		if (passed('批量新增投诉黑')) {
			var dataArr = $('#myTab').bootstrapTable('getSelections');
			if(checkWhite(dataArr)){
				$('.popup_de .show_msg').text('确定要批量新增投诉黑吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				$('.popup_de .btn_submit').one('click', function() {
					var array = new Array();
					var i = 0;
					for (i; i < dataArr.length; i++) {
						phoneStatusArr.push(new phoneStatus(dataArr[i].phoneNumber,dataArr[i].status,dataArr[i].appId));
					}
					if (phoneStatusArr.length > 0) {
						if(mark){
							$.ajax({
								url : "addBlackLevel1Batch.action",
								dataType : "json",
								type : "post",
								async : false,
								data : {
									'phoneStatusArr' : JSON.stringify(phoneStatusArr)
								},
								success:function (data){
									if (data.result >=0) {
										$('.popup_de .show_msg').text('新增成功！');
										$('.popup_de .btn_cancel').css('display', 'none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click', function() {
											$('#btn_del').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_del').css('display', 'none');
											}, 400);
											$('.popup_de').removeClass('bbox');
										})
										$('#myTab').bootstrapTable('refresh', {
											url : 'searchPhone.action'
										});
									} else {
										$('.popup_de .show_msg').text('新增出错！');
										$('.popup_de .btn_cancel').css('display', 'none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click', function() {
											$("#edit_saveBtn").attr("disabled", false);
											$('.popup_de').removeClass('bbox');
										})
										$('#myTab').bootstrapTable('refresh', {
											url : 'searchPhone.action'
										});
									}
								}
							});
							mark=false;
						}
					}
	
				})

			}else{
				$('.popup_de .show_msg').text('白名单数据不进行新增投诉黑操作');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click',function(){
					$('.popup_de').removeClass('bbox');
				})
			}
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}

	})
	$("#btn_addWhite").click(function(){
		mark=true;
		if (passed('批量新增白名单')) {
			var dataArr = $('#myTab').bootstrapTable('getSelections');
			if(!checkEmpty(dataArr)){
				$('.popup_de .show_msg').text('确定要批量新增白名单吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				$('.popup_de .btn_submit').one('click', function() {
					var array = new Array();
					var i = 0;
					for (i; i < dataArr.length; i++) {
						phoneStatusArr.push(new phoneStatus(dataArr[i].phoneNumber,dataArr[i].status,dataArr[i].appId));
					}
					if (phoneStatusArr.length > 0) {
						if(mark){
							$.ajax({
								url : "addWhiteBatch.action",
								dataType : "json",
								type : "post",
								async : false,
								data : {
									'phoneStatusArr' : JSON.stringify(phoneStatusArr)
								},
								success:function (data){
									if (data.result >=0) {
										$('.popup_de .show_msg').text('新增成功！');
										$('.popup_de .btn_cancel').css('display', 'none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click', function() {
											$('#btn_del').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_del').css('display', 'none');
											}, 400);
											$('.popup_de').removeClass('bbox');
										})
										$('#myTab').bootstrapTable('refresh', {
											url : 'searchPhone.action'
										});
									} else {
										$('.popup_de .show_msg').text('新增出错！');
										$('.popup_de .btn_cancel').css('display', 'none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click', function() {
											$("#edit_saveBtn").attr("disabled", false);
											$('.popup_de').removeClass('bbox');
										})
										$('#myTab').bootstrapTable('refresh', {
											url : 'searchPhone.action'
										});
									}
								}
							});
							mark=false;
						}
					}
	
				})

			}else{
				$('.popup_de .show_msg').text('新增白名单号码必须为无状态号码');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click',function(){
					$('.popup_de').removeClass('bbox');
				})
			}
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	
});

//菜单添加表单验证
function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons: {
	           valid: 'glyphicon glyphicon-ok',
	           invalid: 'glyphicon glyphicon-remove',
	           validating: 'glyphicon glyphicon-refresh'
	       },
	       fields: {
	    	   addPhone: {
	                 validators: {
	                     notEmpty: {
	                         message: '手机号码不能为空'
	                     },
	                     regexp: {
	                         regexp: /^1[0-9]{10}$/,
	                         message: '请输入正确的手机号码'
	                     }
	                 }
	             }
	             
	       }
	});
}

function addBlack2Validator() {
	$('#addBlack2Form').bootstrapValidator({
		feedbackIcons: {
	           valid: 'glyphicon glyphicon-ok',
	           invalid: 'glyphicon glyphicon-remove',
	           validating: 'glyphicon glyphicon-refresh'
	       },
	       fields: {
	    	   addBlack2Phone: {
	                 validators: {
	                     notEmpty: {
	                         message: '手机号码不能为空'
	                     },
	                     regexp: {
	                         regexp: /^1[0-9]{10}$/,
	                         message: '请输入正确的手机号码'
	                     }
	                 }
	             }
	             
	       }
	});
}


function AddView(phoneNumber,appId,status) {
	if(passed('新增')){
		$('#addPhone').val(phoneNumber);
		
		$("#addLevel").html("");
		var select = $("#addLevel");  
		
		if(status=="empty"){
			select.append("<option value='black1'>投诉黑名单</option><option value='black3'>普通黑名单</option><option value='black5'>关键黑名单</option><option value='white'>白名单</option><option value='shunt'>实号库</option>");
		}else if(status=="shunt"){
			select.append("<option value='black1'>投诉黑名单</option><option value='black3'>普通黑名单</option><option value='black5'>关键黑名单</option>");
		}else if(status=="black1"){
			select.append("<option value='black3'>普通黑名单</option><option value='black5'>关键黑名单</option><option value='shunt'>实号库</option>");
		}else if(status=="black2"){
			select.append("<option value='black1'>投诉黑名单</option><option value='black3'>普通黑名单</option><option value='black5'>关键黑名单</option><option value='shunt'>实号库</option>");
		}else if(status=="black3"){
			select.append("<option value='black1'>投诉黑名单</option><option value='black5'>关键黑名单</option><option value='shunt'>实号库</option>");
		}else if(status=="black5"){
			select.append("<option value='black1'>投诉黑名单</option><option value='black3'>普通黑名单</option><option value='shunt'>实号库</option>");
		}
		$('#addLevel').selectpicker('refresh');  
        $('#addLevel').selectpicker('render');  
        
		$("#addBody").modal();
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
}


function AddViewByBlack2(phoneNumber,appId,status) {
	if(passed('新增回复黑')){
		$('#addBlack2Phone').val(phoneNumber);
		
		$("#addBlack2Body").modal();
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
}

/**删除单条数据**/
function DeleteByPhone(phoneNumber,appId,status) {
	mark=true;
	if(passed('删除')){
		$('.popup_de .show_msg').text('确定要删该手机号码吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			if(mark){
				$.post(
						"deletePhone.action",{
							phoneNumber:phoneNumber,
							appId:appId,
							status:status
						},
						function(data){
							if (data.result >0) {
								$('.popup_de .show_msg').text('删除成功!');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#myTab').bootstrapTable('refresh',{url:'searchPhone.action'});
							}else if(data.result == '-2'){
								$('.popup_de .show_msg').text('手机号码为空!');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
							}else if(data.result == '-3'){
								$('.popup_de .show_msg').text('删除表名为空!');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
							}else if(data.result == '-4'){
								$('.popup_de .show_msg').text('该手机号码不在任何表中!');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
							}else {
								$('.popup_de .show_msg').text('删除失败!');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
							}
						});
				mark=false;
			}
		})
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
}

function queryParams(params) {
	return {
		phoneNumbers:getSplitString($("#phoneNumbers").val()),
		appId:$("#search_appId").val()
	}
}

function actionFormatter(value, row, index) {
//value = 1, row = Object {id: 1, phoneNumber: "18655104509", appId: "1", status: "black2", select: undefined}, index = 0
var phoneNumber=row.phoneNumber;
var appId=row.appId;
var status=row.status;
var result = "";
if (status == 'empty') {
	result += "<button class='btn btn-default btn-xs' onclick=\"AddView('" + phoneNumber + "','"+appId+"','"+status+"')\" title='新增'>新增</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"AddViewByBlack2('" + phoneNumber + "','"+appId+"','"+status+"')\" title='新增回复黑'>新增回复黑</button>";
	
}else if (status == 'white'){
	result += "<button class='btn btn-default btn-xs' onclick=\"DeleteByPhone('" + phoneNumber + "','"+appId+"','"+status+"')\" title='删除'>删除</button>";
	
}else {
	result += "<button class='btn btn-default btn-xs' onclick=\"AddView('" + phoneNumber + "','"+appId+"','"+status+"')\" title='新增'>新增</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"AddViewByBlack2('" + phoneNumber + "','"+appId+"','"+status+"')\" title='新增回复黑'>新增回复黑</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"DeleteByPhone('" + phoneNumber + "','"+appId+"','"+status+"')\" title='删除'>删除</button>";
}
return result;
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

function checkMobile(s){  
    var length = s.length;  
//    if(length == 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|)+\d{8})$/.test(s) )   
    if(length == 11 && /^(1[0-9]{10})$/.test(s)){
    	return true; 
    }
    if(/^(([+]|[0]{2})[0-9]*)$/.test(s)){
    	return true; 
    }
	return false;   
}   

function checkEmpty(dataArr){  
	var i = 0;
	for (i; i < dataArr.length; i++) {
		if(dataArr[i].status=='empty'){
			return false;
		}
	}
	return true;
}

function checkWhite(dataArr){
	if(dataArr == '' || dataArr==null || dataArr == undefined){
		return false;
	}
	var i = 0;
	for (i; i < dataArr.length; i++) {
		if(dataArr[i].status=='white'){
			return false;
		}
	}
	return true;
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

//// 对每一个弹出的modal，在显示事件触发的时候，重新计算modal的z-index值，最新的modal，其z-index值最大
//$(document).ready(function() {
//	$(document).on('show.bs.modal', '.modal', function() {
//		var zIndex = 1040 + (10 * $('.modal:visible').length);
//		$(this).css('z-index', zIndex);
//	});
//});
//
//$(".modal-backdrop").remove();
