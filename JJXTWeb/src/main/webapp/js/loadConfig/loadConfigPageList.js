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
		url : "findPageList.action",
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
		pageSize : 50,// 单页记录数
		pageList : [ 20, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [ {
			title : 'ID',
			field : 'id',
		}, {
			title : 'key',
			field : 'redisKey',
		}, {
			title : '数据类型',
			field : 'redisType',
		}, {
			title : '最小阈值',
			field : 'minimum',
		}, {
			title : '最大阈值',
			field : 'maximum',
		}, {
			title : '周期',
			field : 'timeLimit',
		}, {
			title : '是否打印日志',
			field : 'printLog',
		}, {
			title : '实体类',
			field : 'variant',
		}, {
			title : '端口名称',
			field : 'redisName',
		}, {
			title : '模块名称',
			field : 'modelName',
		}, {
			title : '加载名称',
			field : 'mapKey',
		}, {
			title : '操作',
			field : 'id',
			width : 150,
			align : 'center',
			valign : 'middle',
			formatter : actionFormatter
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
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#searchkey").val(''),
		$("#searchmodelName").val(''),// 结束时间
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 50,
			sort : '',
			order : 'asc'
		});

	});

	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			redisKey : {
				validators : {
					notEmpty : {
						message : 'key不能为空'
					},
					stringLength : {
						max : 50,
						message : '长度不能超过50'
					},
				}
			},
		redisType : {
			validators : {
				notEmpty : {
					message : '数据类型不能为空'
				},
			}
		},
		minimum : {
			validators : {
				regexp: {
	    			   regexp: /^[0-9_\.]+$/,
	    			   message: '必需输入数字' 
	    		   },
	    stringLength : {
					max : 10,
					message : '长度不能超过10位'
				},
			}
		},
		maximum : {
			validators : {
				regexp: {
	    			   regexp: /^[0-9_\.]+$/,
	    			   message: '必需输入数字' 
	    		   },
	    stringLength : {
					max : 10,
					message : '长度不能超过10位'
				},
			}
		},
		timeLimit : {
			validators : {
				notEmpty : {
					message : '周期不能为空'
				},
				regexp: {
     			   regexp: /^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|[1-9][0-9][0-9][0-9][0-9])$/,
     			   message: '范围在1-99999之间'
     		   }
			}
		},
		variant : {
			validators : {
				notEmpty : {
					message : '实体类不能为空'
				},
				stringLength : {
					max : 30,
					message : '长度不能超过30'
				},
			}
		},
		redisName : {
			validators : {
				notEmpty : {
					message : '端口名称不能为空'
				},
				stringLength : {
					max : 20,
					message : '长度不能超过20'
				},
			}
		},
		modelName : {
			validators : {
				notEmpty : {
					message : '模块名称不能为空'
				},
				stringLength : {
					max : 30,
					message : '长度不能超过30'
				},
			}
		},
		mapKey : {
			validators : {
				notEmpty : {
					message : '名称不能为空'
				},
				stringLength : {
					max : 30,
					message : '长度不能超过30'
				},
			}
		}
	}
	});

	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			redisKey : {
				validators : {
					notEmpty : {
						message : 'key不能为空'
					},
					stringLength : {
						max : 50,
						message : '长度不能超过50'
					},
				}
			},
		redisType : {
			validators : {
				notEmpty : {
					message : '数据类型不能为空'
				},
			}
		},
		minimum : {
			validators : {
				notEmpty : {
					message : '不能为空'
				},
				regexp: {
	    			   regexp: /^[0-9_\.]+$/,
	    			   message: '必需输入数字' 
	    		   },
	    stringLength : {
					max : 10,
					message : '长度不能超过10位'
				},
			}
		},
		maximum : {
			validators : {
				notEmpty : {
					message : '不能为空'
				},
				regexp: {
	    			   regexp: /^[0-9_\.]+$/,
	    			   message: '必需输入数字' 
	    		   },
	    stringLength : {
					max : 10,
					message : '长度不能超过10位'
				},
			}
		},
		timeLimit : {
			validators : {
				notEmpty : {
					message : '周期不能为空'
				},
				regexp: {
     			   regexp: /^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|[1-9][0-9][0-9][0-9][0-9])$/,
     			   message: '范围在1-99999之间'
     		   }
			}
		},
		variant : {
			validators : {
				notEmpty : {
					message : '实体类不能为空'
				},
				stringLength : {
					max : 30,
					message : '长度不能超过30'
				},
			}
		},
		redisName : {
			validators : {
				notEmpty : {
					message : '端口名称不能为空'
				},
				stringLength : {
					max : 20,
					message : '长度不能超过20'
				},
			}
		},
		modelName : {
			validators : {
				notEmpty : {
					message : '模块名称不能为空'
				},
				stringLength : {
					max : 30,
					message : '长度不能超过30'
				},
			}
		},
		mapKey : {
			validators : {
				notEmpty : {
					message : '名称不能为空'
				},
				stringLength : {
					max : 30,
					message : '长度不能超过30'
				},
			}
		}
	}
	});
})



$(function() {
	// 添加
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
	$('#add_saveBtn').click(
			function() {
				// 点击保存时触发表单验证
				$('#addForm').bootstrapValidator('validate');
				// 如果表单验证正确，则请求后台添加用户
				if ($("#addForm").data('bootstrapValidator').isValid()) {
					$("#add_saveBtn").attr("disabled", true);
					var _info = $('#addForm').serialize();
					data1 = decodeURIComponent(_info, true);
					$.post("addToLoadConfig.action",
							data1, 
							function(data) {
						// 后台返回添加成功
						if (data.result > 0) {
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function() {
								$('.addBody').removeClass(
										'animated slideOutLeft').css('display',
										'none');
							}, 500);
							$('.tableBody').css('display', 'block').addClass(
									'animated slideInRight');
							$('#addForm').data('bootstrapValidator').resetForm(
									true);
							$('#mytab').bootstrapTable('refresh', {
								url : 'findPageList.action'
							});
						} else {
							$('.popup_de .show_msg').text('添加失败，请重新添加！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$("#add_saveBtn").attr("disabled", false);
								$('.popup_de').removeClass('bbox');
							})
						}
					}, "json");
				}
			});
	
	$('#edit_saveBtn').click(function() {
				mark=true;
				// 点击保存时触发表单验证
				$('#editForm').bootstrapValidator('validate');
				// 如果表单验证正确，则请求后台修改配置
				if ($("#editForm").data('bootstrapValidator').isValid()) {
					$('.popup_de .show_msg').text('确定要修改吗？');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function() {
						$("#edit_saveBtn").attr("disabled", true);
						var _info = $('#editForm').serialize();
						data = decodeURIComponent(_info, true);
						if(mark){
							$.post("updateLoadConfig.action",data,
									function(data) {
								if (data.result == 1) {
									$('.changeBody').addClass('animated slideOutLeft');
									setTimeout(function() {
										$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
									},500);
									$('.tableBody').css('display','block').addClass('animated slideInRight');
									$('#editForm').data('bootstrapValidator').resetForm(true);
									$('#mytab').bootstrapTable('refresh',{url : 'findPageList.action'});
									$("#edit_saveBtn").attr("disabled",false);
									$('.popup_de').removeClass('bbox');
								} else {
									$('.popup_de .show_msg').text('修改失败，请重新修改！');
									$('.popup_de .btn_cancel').css('display','none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click',function() {
										$("#edit_saveBtn").attr("disabled",false);
										$('.popup_de').removeClass('bbox');
									})
								}
							}, "json"
							);
							mark=false;
						}
					})
				}


			});
	
	// 修改页面返回按钮
	$('#edit_backBtn').click(
			function() {
				$('.changeBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.changeBody').removeClass('animated slideOutLeft')
							.css('display', 'none');
				}, 500)
				$('.tableBody').css('display', 'block').addClass(
						'animated slideInRight');
				$('#editForm').data('bootstrapValidator').resetForm(true);
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


// 删除
function delLoadConfig(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("delLoadConfig.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}else{
						$('.popup_de .show_msg').text('删除失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findPageList.action'
					});
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


//修改
function updateLoadConfig(id) {
	if (passed('修改')) {
		$.post("findLoadConfigById.action", {
			id : id
		}, function(result) {
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css(
						'display', 'none');
			}, 500)
			$('.changeBody').css('display', 'block');
			$('.changeBody').addClass('animated slideInRight');
			$('#editId').val(result.id);
			$('#editredisKey').val(result.redisKey);
			$('#editredisType').val(result.redisType);
			$('#editminimum').val(result.minimum);
			$('#editmaximum').val(result.maximum);
			$('#edittimeLimit').val(result.timeLimit);
			$('#editprintLog').val(result.printLog);
			$('#editvariant').val(result.variant);
			$('#editredisName').val(result.redisName);
			$('#editmodelName').val(result.modelName);
			$('#editmapKey').val(result.mapKey);

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


function tableHeight() {
	return $(window).height() - 120;
}


function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		redisKey : $("#searchkey").val(),
		modelName : $("#searchmodelName").val()

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


function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='update_btn' onclick=\"updateLoadConfig('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"delLoadConfig('"
			+ row.id + "')\" title='点击删除'>删除</button>";

	return result;
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


