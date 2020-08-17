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
		url : "findAllStrategyConfig.action",// 请求后台url
		height : tableHeight(),// 高度调整
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : false,// 是否分页
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		showRefresh : true,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		queryParamsType : 'limit',
		queryParams : queryParams,
		clickToSelect : false,// 是否启用点击选中行
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar : '#toolbar',// 指定工作栏
		columns : [ {
			title : 'ID',
			field : 'configId',
			width : 20,
		}, {
			title : '模块ID',
			field : 'modelId',
			visible : false
		}, {
			title : '模块名称',
			field : 'modelName',
			width : 40,
			formatter : function(value, row, index) {
				return row.modelId+":"+value;
			}
		}, {
			title : '报警类型',
			field : 'typeId',
			width : 100,
			formatter : jointType
		}, {
			title : '报警类型',
			field : 'type',
			visible : false
		}, {
			title : '等级',
			field : 'level',
			width : 30,
		}, {
			title : '开始时间',
			field : 'startTime',
			width : 40,
		}, {
			title : '结束时间',
			field : 'endTime',
			width : 40,
		}, {
			title : '沉默期',
			field : 'cycle',
			width : 30,
		}, {
			title : '状态',
			field : 'status',
			width : 30,
			formatter : changeName
		}, {
			title : '忽略值',
			field : 'ignore',
			width : 80,
		}, {
			title : '策略',
			field : 'strategy',
			width : 100,
		}, {
			title : '描述',
			field : 'description',
			width : 100,
		}, {
			title : '操作',
			field : 'typeId',
			width : 60,
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
	});
	addValidator();
	updateValidator();
	
	
	$("#search_btn").click(function () {
		 $('#mytab').bootstrapTable('refresh',{url:'findAllStrategyConfig.action'});
		});
	    $("#search_back").click(function () {
	    	$("select.selectpicker").each(function() {
				$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
				$(this).find("option").prop("selected", false); // 重置原生select的值
				$(this).find("option:first").prop("selected", true);
			});	    	
	    	
	    	$('#mytab').bootstrapTable('refresh',{url:'findAllStrategyConfig.action'});
	    });
	
	//添加用户
	$("#btn_add").click(function() {
		if (passed('添加')) {
			$("#addBody").modal();
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	// 点击添加菜单页面返回按钮
	$('#add_backBtn').click(function() {
		$("#addBody").modal("hide");
		$("#addForm")[0].reset();
		$("#modelId").selectpicker('refresh');
		$("#typeId").selectpicker('refresh');
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#add_saveBtn").attr("disabled", false);
	});
	$('#add_saveBtn').click(function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$("#add_saveBtn").attr("disabled", true);
			var _info = $('#addForm').serialize();
			data = decodeURIComponent(_info, true);
			if(mark){
				$.post("addStrategyConfig.action", data, function(data) {
					// 后台返回添加成功
					if (data.result == 1) {
						
						$("[name='status']").first().prop("checked",true);
				    	$("[name='status']").first().parent().addClass("active");
				    	$("[name='status']").last().parent().removeClass("active");
				    	$("#modelId").selectpicker('refresh');
				    	$("#typeId").selectpicker('refresh');
				    	$('#level').val();
				    	$('#startTime').val();
				    	$('#endTime').val();
				    	$('#cycle').val();
				    	$('#ignore').val();
				    	$('#strategy').val();
				    	$('#description').val();
						$("#addForm")[0].reset();
						$("#addBody").modal("hide");
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						addValidator();
						$("#add_saveBtn").attr("disabled", false);
						$('.popup_de .show_msg').text('添加成功！');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#mytab').bootstrapTable('refresh',{url : 'findAllStrategyConfig.action'});
						})
						
					} else if(data.result == 2){
						$('.popup_de .show_msg').text('添加重复，请重新添加！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$("#add_saveBtn").attr("disabled", false);
							$('.popup_de').removeClass('bbox');
						})
					}else {
						$('.popup_de .show_msg').text('添加失败，请重新添加！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$("#add_saveBtn").attr("disabled", false);
							$('.popup_de').removeClass('bbox');
						})
					}
				})
				mark=false;
			}
		}
	});
	$('#edit_saveBtn').click(function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#editForm').bootstrapValidator('validate');
		if ($("#editForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要修改吗？');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function() {
				$("#edit_saveBtn").attr("disabled", true);
				
					if(mark){
						$.ajax({
							url:"updateConfigById.action",
							type:"post",
							async:false,
							data:{
								configId:$("#editId").val(),
								startTime:$("#editsTime").val(),
								endTime:$("#editeTime").val(),
								cycle:$("#editCycle").val(),
								status:$("#editForm [name='editStatus']:checked").val(),
								ignore:$("#editIgnore").val(),
								strategy:$("#editStrategy").val(),
								description:$("#editDescription").val()
							},
							success:function(obj){
								$("#changeBody").modal("hide");
								if (obj.result > 0) {
									$('.popup_de .show_msg').text('修改成功！');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$('.popup_de').removeClass('bbox');
										$('#mytab').bootstrapTable('refresh',{url : 'findAllStrategyConfig.action'});
									})
								} else {
									$('.popup_de .show_msg').text('修改失败，请重新修改！');
									$('.popup_de .btn_cancel').css('display','none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click',function() {
										$('.popup_de').removeClass('bbox');
									})
								}
								$("#edit_saveBtn").attr("disabled",false);
							}
						});
						/*// 如果表单验证正确，则请求后台修改配置
						var _info = $('#editForm').serialize();
						data = decodeURIComponent(_info, true);
						  $.post("updateConfigById.action",
								data,
								function(data) {
							$("#changeBody").modal("hide");
							if (data.result > 0) {
								$('.popup_de .show_msg').text('修改成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#mytab').bootstrapTable('refresh',{url : 'findAllStrategyConfig.action'});
								})
							} else {
								$('.popup_de .show_msg').text('修改失败，请重新修改！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
							}
							$("#edit_saveBtn").attr("disabled",false);
						}, "json");*/
						mark=false;
					}
			})
		}
	});
	// 修改页面返回按钮
	$('#edit_backBtn').click( function() {
		$("#editForm").data('bootstrapValidator').destroy();
		$('#editForm').data('bootstrapValidator', null);
		updateValidator();
		$("#modelId").selectpicker('refresh');
		$("#typeId").selectpicker('refresh');
		$("#changeBody").modal('hide');
		$("#editForm")[0].reset();
	});
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$("#edit_saveBtn").attr("disabled", false);
		$('.popup_de').removeClass('bbox');
	});
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$("#edit_saveBtn").attr("disabled", false);
		$('.popup_de').removeClass('bbox');
	});
	
});


// 删除
function delAlarmType(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("delConfigById.action", {
					configId : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findAllStrategyConfig.action'
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
				mark=false;
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

// 修改
function updateAlarmType(id) {
	if (passed('修改')) {
		$.post("findConfigById.action", {
			configId : id
		}, function(result) {
			$("#changeBody").modal();
			$('#editId').val(result.configId);
			$('#editModelId').val(result.modelName);
			$('#editTypeId').val(result.type);
			$('#editLevel').val(result.level);
			$('#editsTime').val(result.startTime);
			$('#editeTime').val(result.endTime);
			$('#editCycle').val(result.cycle);
			if (result.status == 'normal') {
				$("#exitNormal").prop("checked", true);
				$("#exitNormal").parent().addClass("active");
				$("#exitPaused").parent().removeClass("active");
			}else{
				$("#exitPaused").prop("checked", true);
				$("#exitPaused").parent().addClass("active");
				$("#exitNormal").parent().removeClass("active");
			}
			$('#editIgnore').val(result.ignore);
			$('#editStrategy').val(result.strategy);
			$('#editDescription').val(result.description);
		}, "json");

	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de .btn_cancel').css('display', 'none');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}


function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			modelName : {
				validators : {
					notEmpty : {
						message : '模块名称不能为空'
					},
				}
			},
			type : {
				validators : {
					notEmpty : {
						message : '报警类型不能为空'
					},
					
				}
			},
			level : {
				validators : {
					notEmpty : {
						message : '等级不能为空'
					},
					stringLength : {
						max : 10,
						message : '长度不能超10位'
					},
					regexp: {
	 	    			   regexp: /^[0-9_\.]+$/,
	 	    			   message: '必需输入数字' 
	 	    		},
					
				}
			},
			startTime : {
				validators : {
					regexp: {
 	    			   regexp: /^\d{4}$/,
 	    			   message: '必需输入4位数字' 
 	    		   },
					
				}
			},
			endTime : {
				validators : {
					regexp: {
						regexp: /^\d{4}$/,
						message: '必需输入4位数字' 
					},
					
				}
			},
			cycle : {
				validators : {
					notEmpty : {
						message : '沉默期不能为空'
					},
					stringLength : {
						max : 10,
						message : '长度不能超10位'
					},
					regexp: {
	 	    			   regexp: /^[0-9_\.]+$/,
	 	    			   message: '必需输入数字' 
	 	    		   },
					
				}
			},
			ignore : {
				validators : {
					stringLength : {
						max : 250,
						message : '长度不能超250'
					},
					
				}
			},
			strategy : {
				validators : {
					stringLength : {
						max : 500,
						message : '长度不能超500'
					},
					
				}
			},
			description : {
				validators : {
					stringLength : {
						max : 250,
						message : '长度不能超250'
					},
					
				}
			},
		}
	});
}

function updateValidator() {
	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			startTime : {
				validators : {
					regexp: {
 	    			   regexp: /^\d{4}$/,
 	    			   message: '必需输入4位数字' 
 	    		   },
					
				}
			},
			endTime : {
				validators : {
					regexp: {
						regexp: /^\d{4}$/,
						message: '必需输入4位数字' 
					},
					
				}
			},
			cycle : {
				validators : {
					notEmpty : {
						message : '沉默期不能为空'
					},
					stringLength : {
						max : 10,
						message : '长度不能超10位'
					},
					regexp: {
	 	    			   regexp: /^[0-9_\.]+$/,
	 	    			   message: '必需输入数字' 
	 	    		   },
					
				}
			},
			ignore : {
				validators : {
					stringLength : {
						max : 250,
						message : '长度不能超250'
					},
					
				}
			},
			strategy : {
				validators : {
					stringLength : {
						max : 500,
						message : '长度不能超500'
					},
					
				}
			},
			description : {
				validators : {
					stringLength : {
						max : 250,
						message : '长度不能超250'
					},
					
				}
			},
		}
	});
}

function tableHeight() {
	return $(window).height();
}

function queryParams(params) {
	return {
		modelId : $("#modelId").val(),
		typeId : $("#typeId").val(),
	}
}


function jointType(value, row, index) {
	var result = "";
	result += "<a href='javascript:;' data-toggle='modal'  data-target='#typeDetails' onclick=\"findTypeDetails('" + value + "')\" title='详情'>" + value+":"+row.type + "</a>";
	return result;
}

function findTypeDetails(id) {
	$.ajax({
		url:"findTypeDetail.action",
		data:{typeId:id},
		dataType:"json",
		type:"post",
		success:function (result){
			$("#type").html(result.type);
			if(result.isModel == 'yes'){
				$("#isModel").html("是");
			}else{
				$("#isModel").html("否");
			}
			$("#typeModel").html(result.typeModel);
			$("#description").html(result.description);
		}
	});
}

function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='update_alarm' onclick=\"updateAlarmType('"
			+ row.configId + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_alarm' onclick=\"delAlarmType('"
			+ row.configId + "')\" title='点击删除'>删除</button>";

	return result;
}

function changeName(value, row, index) {
	var result = "正常";
	if(value != "normal"){
		result = "暂停"
	}
	return result;
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
