fileNameList=new Array();
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
		url : "findBlackSummaryConfigPageList.action",// 请求后台url
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
		uniqueId : "keys", // 每一行的唯一标识，一般为主键列
		pageSize : 100,// 单页记录数
		pageList : [ 50, 100, 200 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar : '#toolbar',// 指定工作栏
		columns : [ {
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		},{
			title : 'phone',
			field : 'phone',
			visible : false
		}, {
			title : 'ID',
			field : 'id',
			width : 50,
		}, {
			title : '账户',
			field : 'appId',
			width : 100,
			formatter : function(value,row,index){
				if(value=='0'){
					return '全局';
				}
				return value+":"+row.appName;
			}
		}, {
			title : '类型',
			field : 'type',
			width : 60,
			formatter : function(value,row,index){
				if(value == 'pass'){
					return '通过';
				}else if(value == "reject"){
					return "驳回";
				}
			}
		}, {
			title : '策略级别',
			field : 'level',
			width : 80,
			formatter : queryLevel,
		}, {
			title : '状态码',
			field : 'result',
			width : 80,
		}, {
			title : '标记',
			field : 'sendFlag',
			width : 80,
		}, {
			title : '优先级',
			field : 'priority',
			width : 60,
		}, {
			title : '策略状态',
			field : 'status',
			width : 60,
			formatter : function(value, row, index) {
				if (value == 'normal') {
					return "正常";
				} else if (value == 'paused') {
					return "暂停";
				}
			}
		}, {
			title : '说明',
			field : 'remark',
			width : 100,
		}, {
			fileld : 'ID',
			title : '操作',
			width : 230,
			align : 'center',
			valign : 'middle',
			formatter : actionFormatter
		} ],
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
	// 菜单添加表单验证
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appId : {
				validators : {
					notEmpty : {
						message : '账户不能为空'
					},
					callback:{
	         			   message:'账户、类型和策略级别不唯一',
	                       callback:function(value, validator,$field){
	                     	   var appId = $('#appId').val();
	                     	   var type = $('#type').val();
	                     	   var level = $('#level').val();
	                     	   if(appId==null || appId=='' || appId==undefined || type ==null || type == '' || type == undefined || level ==null || level == '' || level == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		  $.ajax({
	                     			  url:'validatorAppIdAndTypeAndLevel.action',
	                     				data:{
	                     					appId:appId,
	                     					type:type,
	                     					level:level
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   return flag;
	                     	   }
	                        }
						}
				}
			},
			type : {
				validators : {
					notEmpty : {
						message : '类型不能为空'
					},
					callback:{
	         			   message:'账户、类型和策略级别不唯一',
	                       callback:function(value, validator,$field){
	                     	   var appId = $('#appId').val();
	                     	   var type = $('#type').val();
	                     	   var level = $('#level').val();
	                     	   if(appId==null || appId=='' || appId==undefined || type ==null || type == '' || type == undefined || level ==null || level == '' || level == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		  $.ajax({
	                     			  url:'validatorAppIdAndTypeAndLevel.action',
	                     				data:{
	                     					appId:appId,
	                     					type:type,
	                     					level:level
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   return flag;
	                     	   }
	                        }
						}
				}
			},
			level:{
				validators : {
					notEmpty : {
						message : '策略级别不能为空'
					},
					callback:{
	         			   message:'必需是大于6的整数；账户、类型和策略级别不唯一',
	                       callback:function(value, validator,$field){
	                    	   var ex = /^\d+$/;
		                 	   if (ex.test(value) && value>6 ){
		                 		  var appId = $('#appId').val();
		                     	   var type = $('#type').val();
		                     	   var level = $('#level').val();
		                     	   if(appId==null || appId=='' || appId==undefined || type ==null || type == '' || type == undefined || level ==null || level == '' || level == undefined){
		                     			return true;
		                     	   }else{
		                     		   var flag=true;
		                     		  $.ajax({
		                     			  url:'validatorAppIdAndTypeAndLevel.action',
		                     				data:{
		                     					appId:appId,
		                     					type:type,
		                     					level:level
		                     				},
		                     				async:false,
		                     				success:function(obj){
		                     					flag=obj.valid;
		                     				},
		                     				type:"post",
		                     				dataType:"json"
		                     		   })
		                     		   return flag;
		                     	   } 
		                 	   }else{
		                 		  return false; 
		                 	   }
	                        }
						}
				}
			},
			result:{
				validators : {
					stringLength : {
						max : 10,
						message : '状态码长度不能超过10个字'
					}
				}
			},
			sendFlag:{
				validators : {
					stringLength : {
						max : 10,
						message : '标记长度不能超过10个字'
					}
				}
			},
			priority:{
				validators : {
					notEmpty : {
						message : '优先级不能为空'
					}
				}
			},
			remark:{
				validators : {
					notEmpty : {
						message : '说明不能为空'
					},
					stringLength : {
						max : 50,
						message : '说明长度不能超过50个字'
					}
				}
			},
		}
	});
	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			id : {
				validators : {
					notEmpty : {
						message : 'ID不能为空'
					}
				}
			},
			appId : {
				validators : {
					notEmpty : {
						message : '账户不能为空'
					},
					callback:{
	         			   message:'账户、类型和策略级别不唯一',
	                       callback:function(value, validator,$field){
	                     	   var appId = $('#edit_appId').val();
	                     	   var type = $('#edit_type').val();
	                     	   var level = $('#edit_level').val();
	                     	   var oldappId = $('#edit_old_appId').val();
	                     	   var oldtype = $('#edit_old_type').val();
	                     	   var oldlevel = $('#edit_old_level').val();
	                     	   if(appId==null || appId=='' || appId==undefined || type ==null || type == '' || type == undefined || level ==null || level == '' || level == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
                     			  $.ajax({
	                     			  url:'validatorAppIdAndTypeAndLevel.action',
	                     				data:{
	                     					appId:appId,
	                     					type:type,
	                     					level:level
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   return flag;
	                     	   }
	                        }
						}
				}
			},
			type : {
				validators : {
					notEmpty : {
						message : '类型不能为空'
					},
					callback:{
	         			   message:'账户、类型和策略级别不唯一',
	                       callback:function(value, validator,$field){
	                     	   var appId = $('#edit_appId').val();
	                     	   var type = $('#edit_type').val();
	                     	   var level = $('#edit_level').val();
	                     	   var oldappId = $('#edit_old_appId').val();
	                     	   var oldtype = $('#edit_old_type').val();
	                     	   var oldlevel = $('#edit_old_level').val();
	                     	   if(appId==null || appId=='' || appId==undefined || type ==null || type == '' || type == undefined || level ==null || level == '' || level == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
                     			  $.ajax({
	                     			  url:'validatorAppIdAndTypeAndLevel.action',
	                     				data:{
	                     					appId:appId,
	                     					type:type,
	                     					level:level
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   return flag;
	                     	   }
	                        }
						}
				}
			},
			level:{
				validators : {
					notEmpty : {
						message : '策略级别不能为空'
					},
					callback:{
	         			   message:'必需是大于6的整数；账户、类型和策略级别不唯一',
	                       callback:function(value, validator,$field){
	                    	   var ex = /^\d+$/;
		                 	   if (ex.test(value) && value>6 ){
		                 		  var appId = $('#edit_appId').val();
		                     	   var type = $('#edit_type').val();
		                     	   var level = $('#edit_level').val();
		                     	   var oldappId = $('#edit_old_appId').val();
		                     	   var oldtype = $('#edit_old_type').val();
		                     	   var oldlevel = $('#edit_old_level').val();
		                     	   if(appId==null || appId=='' || appId==undefined || type ==null || type == '' || type == undefined || level ==null || level == '' || level == undefined){
		                     			return true;
		                     	   }else{
		                     		   var flag=true;
	                     			  $.ajax({
		                     			  url:'validatorAppIdAndTypeAndLevel.action',
		                     				data:{
		                     					appId:appId,
		                     					type:type,
		                     					level:level
		                     				},
		                     				async:false,
		                     				success:function(obj){
		                     					flag=obj.valid;
		                     				},
		                     				type:"post",
		                     				dataType:"json"
		                     		   })
		                     		   return flag;
		                     	   } 
		                 	   }else{
		                 		  return false; 
		                 	   }
	                        }
						}
				}
			},
			result:{
				validators : {
					stringLength : {
						max : 10,
						message : '状态码长度不能超过10个字'
					}
				}
			},
			sendFlag:{
				validators : {
					stringLength : {
						max : 10,
						message : '标记长度不能超过10个字'
					}
				}
			},
			priority:{
				validators : {
					notEmpty : {
						message : '优先级不能为空'
					}
				}
			},
			remark:{
				validators : {
					notEmpty : {
						message : '说明不能为空'
					},
					stringLength : {
						max : 50,
						message : '说明长度不能超过50个字'
					}
				}
			},
		}
	});
	$('#importForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			level : {
				validators : {
					notEmpty : {
						message : '级别不能为空'
					}
				}
			},
			uploadFile : {
				validators : {
					notEmpty : {
						message : '请选择txt格式文件'
					},
					regexp : {
						regexp : /\.txt$/,
						message : '请选择正确的txt格式文件'
					}
				}
			}
		}
	});
	$('#levelForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			phone : {
				validators : {
					notEmpty : {
						message : '手机号码不能为空'
					},
					regexp : {
						regexp : /^1[0-9]{10}$/,
						message : '请输入正确的手机号码格式'
					}
				}
			}
		}
	});
	
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#search_appId").val('');
		$("#search_type").val('');
		$("#search_level").val('');
		$("#search_result").val('');
		$("#search_sendFlag").val('');
		$("#search_status").val('');
		$("#search_phone").val('');
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 100,
		});
	});
})
function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	if (row.status == 'normal') {
		result += "<button class='btn btn-primary btn-xs' id='over_normal' onclick=\'addToPause("
			+ row.id + ")'\ title='暂停'>暂停</button>";
	} else {
		result += "<button class='btn btn-primary btn-xs' id='over_paused' onclick=\'addToStart("
				+ row.id + ")'\ title='启动'>启动</button>";
	}
	result += "&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"EditViewById('"
		+ row.id + "')\" title='修改'>修改</button>";
	result += "&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"DeleteByIds('"
		+ row.id + "')\" title='删除'>删除</button>";
	if(row.phone != ''){
		result += "&nbsp;&nbsp;";
		result += "<button class='btn btn-default btn-xs' onclick=\"DeletePhone('"
			+ row.level + "','"+ row.phone +"')\" title='删除号码'>删除号码</button>";
	}
	
	return result;
}
function queryLevel(value, row, index) {
	var result = "<a href='javascript:;' class='btn btn-xs blue' data-toggle='modal'  data-target='#linkLevel' onclick=\"linkLevel('" + value + "')\" >" + value + "</a>";
	return result;
}
function tableHeight() {
	return $(window).height() - 50;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		appId :$("#search_appId").val(),
		type : $("#search_type").val(),
		level : $("#search_level").val(),
		result : $("#search_result").val(),
		sendFlag : $("#search_sendFlag").val(),
		status :$("#search_status").val(),
		phone :$("#search_phone").val()
	}
}

function addToStart(id) {
	mark = true;
	if (passed('启动')) {
		$('.popup_de .show_msg').text('确定要启动吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("startSummaryConfig.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('启动成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findBlackSummaryConfigPageList.action'
						});
					} else {
						$('.popup_de .show_msg').text('启动失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
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
}

function addToPause(id) {
	mark = true;
	if (passed('暂停')) {
		$('.popup_de .show_msg').text('确定要暂停吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("pauseSummaryConfig.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('暂停成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findBlackSummaryConfigPageList.action'
						});
					} else {
						$('.popup_de .show_msg').text('暂停失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
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

}

function linkLevel(level) {
	$.ajax({
		url:"findLevel.action",
		data:{level:level},
		dataType:"json",
		type:"post",
		success:function (result){
			$('#level_name').val(result.name);
			$('#level_num').val(result.num);
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
	// 点击添加按钮
	$("#btn_add").click(
			function() {
				if (passed('新增')) {
					$('.tableBody').addClass('animated slideOutLeft');
					setTimeout(function() {
						$('.tableBody').removeClass('animated slideOutLeft')
								.css('display', 'none');
					}, 500)
					$('.addBody').css('display', 'block');
					$('.addBody').addClass('animated slideInRight');
					$("#add_saveBtn").attr("disabled", false);
					$("#appId").selectpicker('refresh');
				} else {
					$('.popup_de .show_msg').text('没有权限，请联系管理员！');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}
			});
	// 点击添加菜单页面返回按钮
	$('#add_backBtn').click(
			function() {
				$('.addBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.addBody').removeClass('animated slideOutLeft').css(
							'display', 'none');
				}, 500)
				$('.tableBody').css('display', 'block').addClass(
						'animated slideInRight');
				$('#addForm').data('bootstrapValidator').resetForm(true);
			});
	// 点击菜单页面提交按钮
	$('#add_saveBtn').click(
			function() {
				// 点击保存时触发表单验证
				$('#addForm').bootstrapValidator('validate');
				// 如果表单验证正确，则请求后台添加用户
				if ($("#addForm").data('bootstrapValidator').isValid()) {
					var _info = $('#addForm').serialize();
					data = decodeURIComponent(_info, true);
					data=encodeURI(data);
					$.post("addBlackSummaryConfig.action", data, function(data) {
						// 后台返回添加成功
						if (data.result > 0) {
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function() {
								$('.addBody').removeClass(
										'animated slideOutLeft').css('display',
										'none');
							}, 500);
							$("#appId").val('');
							$('.tableBody').css('display', 'block').addClass(
									'animated slideInRight');
							$('#addForm').data('bootstrapValidator').resetForm(
									true);
							$('#mytab').bootstrapTable('refresh', {
								url : 'findBlackSummaryConfigPageList.action'
							});
						} else {
						}
					})
				}
			});
	$("#btn_import").click(function(){
		if(passed('号码批量操作')){
			$("#importBody").modal();
		}else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	$('#import_backBtn').click(function() {
		$("#importForm")[0].reset();
		$("#importlevel").selectpicker('refresh');
		$("#importOperation").selectpicker('refresh');
		$("#importBody").modal('hide');
		$("#import_saveBtn").attr("disabled", false);
		$("#importForm").data('bootstrapValidator').destroy();
		$('#importForm').data('bootstrapValidator', null);
	});
	//点击确定保存
	$('#import_saveBtn').click(function() {
		var uploadFile=$("#uploadFile").val();
		if(uploadFile == "" || uploadFile == null || uploadFile == undefined){
			alert("请上传文件！");
			return;
		}
		uploadFile=uploadFile.substring(uploadFile.lastIndexOf("\\")+1,uploadFile.length);
		if(fileNameList.indexOf(uploadFile) > 0){
			$('.popup_de .show_msg').text('该文件已导入！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		fileNameList.push(uploadFile);
		// 点击保存时触发表单验证
		$('#importForm').bootstrapValidator('validate');
		// 如果表单验证正确
		if($("#importForm").data('bootstrapValidator').isValid()){
			$("#import_saveBtn").attr("disabled", true);
			var formData = new FormData($( "#importForm" )[0]);
			$.ajax({
				url : "importFile.action",
				type : "post",
				data : formData,
				processData: false,
				contentType: false,
				success : function(obj) {
					if(obj.result > 0){
						$('.popup_de .show_msg').text('操作成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							location.reload();
						})
					}else if(obj.result == '-9999'){
						$('.popup_de .show_msg').text('文件（格式）错误！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$("#import_saveBtn").attr("disabled", false);
							$('.popup_de').removeClass('bbox');
						})
						fileNameList.remove(obj.fileName);
					}else {
						$('.popup_de .show_msg').text('操作失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$("#import_saveBtn").attr("disabled", false);
							$('.popup_de').removeClass('bbox');
						})
						fileNameList.remove(obj.fileName);
					}
					$("#importBody").modal('hide');
				},
				dataType:'json'
			});
		}
	});
	
	// 根据复选框批量删除
	$('#btn_delete').click(function() {
		mark = true;
		if (passed('批量删除')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除数据吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				if (mark) {
					var ID = [];
					for (var i = 0; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						$.post("deleteBlackSummaryConfigBatch.action",
								{ids : ID},
								function(data) {
									if (data.result > 0) {
										$('.popup_de .show_msg').text('删除成功！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('#btn_delete').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_delete').css('display', 'none');
											}, 400);
											$('#btn_pause').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_pause').css('display', 'none');
											}, 400);
											$('#btn_start').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_start').css('display', 'none');
											}, 400);
											$('.popup_de').removeClass('bbox');
										})
										$('#mytab').bootstrapTable('refresh',{url : 'findBlackSummaryConfigPageList.action'});
									} else {
										$('.popup_de .show_msg').text('删除失败！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
									}
								});
					} else {
						$('.popup_de .show_msg').text('请选择要删除的数据！');
						$('.popup_de').addClass('bbox');
					}
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
	})
	// 根据复选框批量暂停
	$('#btn_pause').click(function() {
		mark = true;
		if (passed('批量暂停')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要暂停吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				if (mark) {
					var ID = [];
					for (var i = 0; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						$.post("pauseSummaryConfigBatch.action",
								{ids : ID},
								function(data) {
									if (data.result > 0) {
										$('.popup_de .show_msg').text('暂停成功！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('#btn_delete').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_delete').css('display', 'none');
											}, 400);
											$('#btn_pause').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_pause').css('display', 'none');
											}, 400);
											$('#btn_start').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_start').css('display', 'none');
											}, 400);
											$('.popup_de').removeClass('bbox');
										})
										$('#mytab').bootstrapTable('refresh',{url : 'findBlackSummaryConfigPageList.action'});
									} else {
										$('.popup_de .show_msg').text('暂停失败！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
									}
								});
					} else {
						$('.popup_de .show_msg').text('请选择要暂停的数据！');
						$('.popup_de').addClass('bbox');
					}
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
	})
	// 根据复选框批量启动
	$('#btn_start').click(function() {
		mark = true;
		if (passed('批量启动')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要启动吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				if (mark) {
					var ID = [];
					for (var i = 0; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						$.post("startSummaryConfigBatch.action",
								{ids : ID},
								function(data) {
									if (data.result > 0) {
										$('.popup_de .show_msg').text('启动成功！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('#btn_delete').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_delete').css('display', 'none');
											}, 400);
											$('#btn_pause').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_pause').css('display', 'none');
											}, 400);
											$('#btn_start').addClass('fadeOutRight');
											setTimeout(function() {
												$('#btn_start').css('display', 'none');
											}, 400);
											$('.popup_de').removeClass('bbox');
										})
										$('#mytab').bootstrapTable('refresh',{url : 'findBlackSummaryConfigPageList.action'});
									} else {
										$('.popup_de .show_msg').text('启动失败！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
									}
								});
					} else {
						$('.popup_de .show_msg').text('请选择要启动的数据！');
						$('.popup_de').addClass('bbox');
					}
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
	})
	
	// 批量按钮的出现与消失
	$('.table').change(
			function() {
				var dataArr = $('#mytab .selected');
				if (dataArr.length >= 1) {
					$('#btn_delete').css('display', 'block').removeClass(
							'fadeOutRight').addClass('animated fadeInRight');
					$('#btn_pause').css('display', 'block').removeClass(
						'fadeOutRight').addClass('animated fadeInRight');
					$('#btn_start').css('display', 'block').removeClass(
						'fadeOutRight').addClass('animated fadeInRight');
				} else {
					$('#btn_delete').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_delete').css('display', 'none');
					}, 400);
					$('#btn_pause').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_pause').css('display', 'none');
					}, 400);
					$('#btn_start').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_start').css('display', 'none');
					}, 400);
				}
			});
	$("[name='btSelectAll']").click(function(){
		var dataArr = $('#mytab .selected');
		if (dataArr.length == 1) {
			$('#btn_edit').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_edit').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_edit').css('display', 'none');
			}, 400);
		}
		if (dataArr.length >= 1) {
			$('#btn_delete').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
			$('#btn_pause').css('display', 'block').removeClass(
				'fadeOutRight').addClass('animated fadeInRight');
			$('#btn_start').css('display', 'block').removeClass(
				'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_delete').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_delete').css('display', 'none');
			}, 400);
			$('#btn_pause').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_pause').css('display', 'none');
			}, 400);
			$('#btn_start').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_start').css('display', 'none');
			}, 400);
		}
	});
	// 修改页面回退按钮事件
	$('#edit_backBtn').click(
			function() {
				$('.changeBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.changeBody').removeClass('animated slideOutLeft').css(
							'display', 'none');
				}, 500)
				$('.tableBody').css('display', 'block').addClass(
						'animated slideInRight');
				$('#editForm').data('bootstrapValidator').resetForm(true);
				$("#editForm label").removeClass("active");
			})
	// 修改页面保存按钮事件
	$('#edit_saveBtn').click(
			function() {
				$('#editForm').bootstrapValidator('validate');
				if ($("#editForm").data('bootstrapValidator').isValid()) {
					$("#edit_saveBtn").attr("disabled", true);
					var _info = $('#editForm').serialize();
					data = decodeURIComponent(_info, true);
					$.post("updateBlackSummaryConfig.action", data, function(data) {
						if (data.result > 0) {
							// 隐藏修改与删除按钮
							$('#btn_delete').css('display', 'none');
							$('#btn_edit').css('display', 'none');
							// 回退到人员管理主页
							$('.changeBody').addClass('animated slideOutLeft');
							setTimeout(function() {
								$('.changeBody').removeClass(
										'animated slideOutLeft').css('display',
										'none');
							}, 500)
							$('.tableBody').css('display', 'block').addClass(
									'animated slideInRight');
							// 刷新人员管理主页
							$('#mytab').bootstrapTable('refresh', {
								url : 'findBlackSummaryConfigPageList.action'
							});
							// 修改页面表单重置
							$('#editForm').data('bootstrapValidator')
									.resetForm(true);
						} else {
						}
						$("#edit_saveBtn").attr("disabled", false);
					})
				}
			})
			
	$('#level_backBtn').click(function() {
		$("#linkLevel").modal('hide');
		$("#level_searchBtn").attr("disabled", false);
		$("#level_addBtn").attr("disabled", false);
		$('#levelForm').data('bootstrapValidator', null);
		$("#level_phone").val('');
		$("#level_result").val('');
	});
	
	$('#level_searchBtn').click(
			function() {
				$('#levelForm').bootstrapValidator('validate');
				if ($("#levelForm").data('bootstrapValidator').isValid()) {
				var model ="search";
				var name = $("#level_name").val();
				var phone = $("#level_phone").val();
				$.post("handleLevel.action", {
					phone : phone,
					name : name,
					model : model
				}, function(obj) {
					var searchResult = obj.searchResult;
					if (searchResult) {
						$('#level_result').val(phone+' 手机号码存在！');
					} else {
						$('#level_result').val(phone+' 手机号码不存在！');
					}
				}, "json");
			}
		});
	
	$('#level_addBtn').click(
		function() {
			var model ="add";
			var name = $("#level_name").val();
			var phone = $("#level_phone").val();
			$.post("handleLevel.action", {
				phone : phone,
				name : name,
				model : model
			}, function(obj) {
				var addResult = obj.addResult;
				$("#level_phone").val('');
				if (addResult >= 1) {
					$('#level_result').val(phone+' 手机号添加成功！');
				} else {
					$('#level_result').val(phone+' 手机号添加失败！');
				}
			}, "json");
		});
	
	$('#level_delBtn').click(
		function() {
			var model ="delete";
			var name = $("#level_name").val();
			var phone = $("#level_phone").val();
			$.post("handleLevel.action", {
				phone : phone,
				name : name,
				model : model
			}, function(obj) {
				var deleteResult = obj.deleteResult;
				$("#level_phone").val('');
				if (deleteResult >= 1) {
					$('#level_result').val(phone+' 手机号删除成功！');
				} else {
					$('#level_result').val(phone+' 手机号删除失败！');
				}
			}, "json");
		});
})
function EditViewById(id) {
	if (passed('修改')) {
		var data = "";
		$.post("findBlackSummaryConfigById.action", {
			id : id
		}, function(result) {
			data = result
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css(
						'display', 'none');
			}, 500)
			$('.changeBody').css('display', 'block');
			$('.changeBody').addClass('animated slideInRight');
			$('#edit_id').val(result.id);
			$('#edit_appId').selectpicker('val',result.appId);
			$("#edit_old_appId").val(result.appId);
			$('#edit_type').selectpicker('val',result.type);
			$('#edit_old_type').val(result.type);
			$('#edit_level').val(result.level);
			$('#edit_old_level').val(result.level);
			$('#edit_result').val(result.result);
			$('#edit_sendFlag').val(result.sendFlag);
			$('#edit_remark').val(result.remark);
			$('#edit_priority').val(result.priority);
			$("#editForm input[name='status']").map(function() {
				if ($(this).val() == result.status) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			
		}, "json");
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
// 删除单条数据
function DeleteByIds(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除数据吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deleteBlackSummaryConfig.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findBlackSummaryConfigPageList.action'
						});
					} else {
						$('.popup_de .show_msg').text('删除失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
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
}
function DeletePhone(level,phone) {
	mark = true;
	if (passed('删除号码')) {
		$('.popup_de .show_msg').text('确定要删除号码吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deletePhone.action", {
					level : level,
					phone : phone
				}, function(data) {
					if (data > 0) {
						$('.popup_de .show_msg').text('删除号码成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findBlackSummaryConfigPageList.action'
						});
					} else {
						$('.popup_de .show_msg').text('删除号码失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
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