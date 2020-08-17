/**
 * 签名扩展跳转页面
 */
$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#tempTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	// 生成用户数据
	$('#tempTab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findTemplateList.action",// 请求后台url
		height : tableHeight(),// 高度调整
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : true,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryTempParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		pageSize : 50,// 单页记录数
		pageList : [ 30, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		},{
			title: 'ID',
			field: 'id',
		},{
			title: '签名',
			field:'sign',
		},{
			title: '扩展号',
			field: 'ext',
		},{
			title: '跳转移动通道',
			field: 'cmccChannelId',
			formatter: function(value,row,index) {
				return value+':'+row.cmccChannelName
			}
		},{
			title: '跳转联通通道',
			field: 'unicomChannelId',
			formatter: function(value,row,index) {
				return value+':'+row.unicomChannelName
			}
		},{
			title: '跳转电信通道',
			field: 'telecomChannelId',
			formatter: function(value,row,index) {
				return value+':'+row.telecomChannelName
			}
		},{
			title: '创建时间',
			field: 'createTime',
			formatter: function(value,row,index) {
				return changeDateFormat(value)
			}
		},{
			title : '操作',
			field : 'id',
			width : 100,
			align : 'center',
			valign : 'middle',
			formatter : tempFormatter
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
		
	});
	$("#search_btn1").click(function() {
		$('#tempTab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back1").click(function() {
		$("#channelId").selectpicker('val',''), 
		$("#sign1").val(''),
		$("#ext").val(''),
		$('#tempTab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 50,
			sort : '',
			order : 'asc'
		});

	});
	addTempValidator();
	updateTempValidator();
	
	$("#btn_add1").click(function() {
		if (passed('添加')) {
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft')
						.css('display', 'none');
			}, 500)
			$('.addTempBody').css('display', 'block');
			$('.addTempBody').addClass('animated slideInRight');
			$("#addTemp_saveBtn").attr("disabled", false);
			$("#cmccChannelId").selectpicker('val','');
			$("#unicomChannelId").selectpicker('val','');
			$("#telecomChannelId").selectpicker('val','');
			
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	// 点击添加页面返回按钮
	$('#addTemp_backBtn').click( function() {
		$('.addTempBody').addClass('animated slideOutLeft');
		setTimeout(function() {
			$('.addTempBody').removeClass('animated slideOutLeft').css(
					'display', 'none');
		}, 500)
		$('.tableBody').css('display', 'block').addClass(
				'animated slideInRight');
		$('#addTemp').data('bootstrapValidator').resetForm(true);
		
	});
	$('#addTemp_saveBtn').click( function() {
		// 点击保存时触发表单验证
		$('#addTemp').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addTemp").data('bootstrapValidator').isValid()) {
			$("#addTemp_saveBtn").attr("disabled", true);
			var _info = $('#addTemp').serialize();
			data1 = decodeURIComponent(_info, true);
			$.post("addToSignExtTemplate.action", 
					data1, 
				function(data) {
				// 后台返回添加成功
				if (data.result == 1) {
					$('.addTempBody').addClass('animated slideOutLeft');
					setTimeout(function() {
						$('.addTempBody').removeClass(
								'animated slideOutLeft').css('display',
								'none');
					}, 500);
					$('.tableBody').css('display', 'block').addClass(
							'animated slideInRight');
					$('#addTemp').data('bootstrapValidator').resetForm(
							true);
					$('#tempTab').bootstrapTable('refresh', {
						url : 'findTemplateList.action'
					});
				}else if (data.result == 2) {
					$('.popup_de .show_msg').text('该条数据已存在，请确认！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function() {
						$("#addTemp_saveBtn").attr("disabled", false);
						$('.popup_de').removeClass('bbox');
					})
				} else {
					$('.popup_de .show_msg').text('添加失败，请重新添加！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$("#addTemp_saveBtn").attr("disabled", false);
						$('.popup_de').removeClass('bbox');
					})
				}
			}, "json");
		}
	});
	
	//修改
	$('#editTemp_saveBtn').click( function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#editTempForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台修改配置
		if ($("#editTempForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要修改吗？');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function() {
				var _info = $('#editTempForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.post(
							"updateSignExtTemplate.action",
							data,
							function(data) {
								if (data.result == 1) {
									$('.changeTempBody').addClass('animated slideOutLeft');
									setTimeout(function() {
										$('.changeTempBody').removeClass('animated slideOutLeft')
										.css('display','none');},500);
									$('.tableBody').css('display','block').addClass('animated slideInRight');
									$('#editTempForm').data('bootstrapValidator').resetForm(true);
									$('#tempTab').bootstrapTable('refresh',
											{url : 'findTemplateList.action'});
									$('.popup_de').removeClass('bbox');
								} else if (data.result == 2) {
									$('.popup_de .show_msg').text('该条数据已存在，请确认！');
									$('.popup_de .btn_cancel').css('display','none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click',function() {
										$("#editTemp_saveBtn").attr("disabled", false);
										$('.popup_de').removeClass('bbox');
									})
								} else {
									$('.popup_de .show_msg').text('修改失败，请重新修改！');
									$('.popup_de .btn_cancel').css('display','none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$("#editTemp_saveBtn").attr("disabled", false);
										$('.popup_de').removeClass('bbox');
									})
								}
							}, "json");
					mark=false;
				}
			})
		}
	});
	// 修改页面返回按钮
	$('#editTemp_backBtn').click( function() {
		$('.changeTempBody').addClass('animated slideOutLeft');
		setTimeout(function() {
			$('.changeTempBody').removeClass('animated slideOutLeft')
					.css('display', 'none');
		}, 500)
		$('.tableBody').css('display', 'block').addClass(
				'animated slideInRight');
		$('#editTempForm').data('bootstrapValidator').resetForm(true);
	});
	
	// 批量删除
	$('#btn_del1').click(function() {
		mark=true;
		if (passed('批量删除')) {
			var dataArr = $('#tempTab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de .btn_submit').one('click', function() {
				var ID = [];
				var i = 0;
				for (i; i < dataArr.length; i++) {
					ID[i] = dataArr[i].id;
				}
				if (ID.length > 0) {
					if(mark){
						$.post("delTemplateBatch.action", {
							ids : ID
						}, function(data) {
							if (data.result > 0) {
								$('.popup_de .show_msg').text('删除成功！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('#btn_del1').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_del1').css('display', 'none');
									}, 400);
									$('.popup_de').removeClass('bbox');
								})
								$('#tempTab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							} else {
								$('.popup_de .show_msg').text('删除出错！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$("#editTemp_saveBtn").attr("disabled", false);
									$('.popup_de').removeClass('bbox');
								})
								$('#tempTab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							}
						})
						mark=false;
					}
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
	// 批量删除按钮的出现与消失
	$('.table').change( function() {
		var dataArr = $('#tempTab .selected');
		if (dataArr.length >= 1) {
			$('#btn_del1').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_del1').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del1').css('display', 'none');
			}, 400);
		}
	});
	$("[name='btSelectAll']").click(function(){
		var dataArr = $('#tempTab .selected');
		if (dataArr.length >= 1) {
			$('#btn_del1').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_del1').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del1').css('display', 'none');
			}, 400);
		}
	});
	
});

function tempFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='update_tempBtn' onclick=\"updateSignExtTemp('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_tempBtn' onclick=\"delSignExtTemp('"
			+ row.id + "')\" title='点击删除'>删除</button>";

	return result;
}

function queryTempParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		sign : $("#sign1").val(), 
		ext : $("#ext").val(), 
		channelId : $("#channelId").val()
	}
}

function addTempValidator() {
	$('#addTemp').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			sign : {
				validators : {
					notEmpty : {
						message : '签名不能为空'
					},
					regexp : {
						regexp : /^([【[]).+([】\]]$)/,
						message : '签名必须包含【】或[]'
					},
					stringLength : {
						max : 30,
						message : '签名长度不能超30个字符'
					}
				}
			},
			ext : {
				validators : {
					notEmpty : {
						message : '扩展不能为空' 
					},
					regexp : {
						regexp : /^\d{1,12}$/,
						message : '必须是1~12位的纯数字'
					},
				}
			}
		}
	});
}

function updateTempValidator() {
	$('#editTempForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			sign : {
				validators : {
					notEmpty : {
						message : '签名不能为空'
					},
					regexp : {
						regexp : /^([【[]).+([】\]]$)/,
						message : '签名必须包含【】或[]'
					},
					stringLength : {
						max : 30,
						message : '签名长度不能超30个字符'
					}
				}
			},
			ext : {
				validators : {
					notEmpty : {
						message : '扩展不能为空' 
					},
					regexp : {
						regexp : /^\d{1,12}$/,
						message : '必须是1~12位的纯数字'
					},
				}
			}
		}
	});
}

//删除
function delSignExtTemp(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark){
			$.post("delSignExtTemplate.action", {
				id : id
			}, function(data) {
				if (data.result > 0) {
					$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					$('#tempTab').bootstrapTable('refresh', {
						url : 'findTemplateList.action'
					});
				} else {
					$('.popup_de .show_msg').text('删除失败！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					$('#tempTab').bootstrapTable('refresh', {
						url : 'findTemplateList.action'
					});
				}
			});
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

function updateSignExtTemp(id) {
	if (passed('修改')) {
		$.post("findTemplateById.action", {
			id : id
		}, function(result) {
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css(
						'display', 'none');
			}, 500)
			$('.changeTempBody').css('display', 'block');
			$('.changeTempBody').addClass('animated slideInRight');
			$('#editTempId').val(result.id);
			$("#editCmccChannelId").selectpicker('val', result.cmccChannelId);
			$("#editUnicomChannelId").selectpicker('val', result.unicomChannelId);
			$("#editTelecomChannelId").selectpicker('val', result.telecomChannelId);
			$('#editTempSign').val(result.sign);
			$('#editTempExt').val(result.ext);

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


$("#typePage").click(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#mytab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})

	$('#mytab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findAllList.action",
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
			title : '账户',
			field : 'appId',
			formatter : function(value,row,index) {
				return value+":"+row.appName;
			}
		}, {
			title : '签名',
			field : 'sign',
		}, {
			title : '扩展号',
			field : 'extSrc',
		}, {
			title : '时间',
			field : 'activeTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}, {
			title : '操作',
			field : 'id',
			width : 100,
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
	$("#search_btn2").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back2").click(function() {
		$("#appId").selectpicker('val',''), 
		$("#sign2").val(''),
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
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
			appId : {
				validators : {
					notEmpty : {
						message : '账户不能为空'
					}
				}
			},
			sign : {
				validators : {
					notEmpty : {
						message : '签名不能为空'
					},
					regexp : {
						regexp : /^[^【】[\]]+$/,
						message : '签名不能包含括号'
					},
					stringLength : {
						max : 30,
						message : '签名长度不能超30个字符'
					}
				}
			},
			extSrc : {
				validators : {
					notEmpty : {
						message : '扩展号不能为空'
					},
					regexp : {
						regexp : /^\d{1,12}$/,
						message : '必须是1~12位的纯数字'
					}
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
			appId : {
				validators : {
					notEmpty : {
						message : '账户不能为空'
					}
				}
			},
			sign : {
				validators : {
					notEmpty : {
						message : '签名不能为空'
					},
					regexp : {
						regexp : /^[^【】[\]]+$/,
						message : '签名不能包含括号'
					},
					stringLength : {
						max : 30,
						message : '签名长度不能超30个字符'
					}
				}
			},
			extSrc : {
				validators : {
					notEmpty : {
						message : '扩展号不能为空'
					},
					regexp : {
						regexp : /^\d{1,12}$/,
						message : '必须是1~12位的纯数字'
					}
				}
			}
		}
	});
	importValidator();

});
function importValidator() {
	$('#importForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			uploadFile : {
				validators : {
					notEmpty : {
						message : '请选择Excel格式文件'
					},
					regexp : {
						regexp : /\.xl.{1,2}$/,
						message : '选择正确的excel文件'
					}
				}
			}
		}
	});
}

function tableHeight() {
	return $(window).height() - 130;
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		sign : $("#sign2").val(), 
		appId : $("#appId").val()
		
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

function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='update_btn' onclick=\"updateExtSign('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"delExtSign('"
			+ row.id + "')\" title='点击删除'>删除</button>";

	return result;
}


//删除
function delExtSign(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark){
			$.post("delExtSign.action", {
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
						url : 'findAllList.action'
					});
				} else {
					$('.popup_de .show_msg').text('删除失败！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAllList.action'
					});
				}
			});
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

function updateExtSign(id) {
	if (passed('修改')) {
		$.post("findExtSignById.action", {
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
			$("#editName").selectpicker('val', result.appId);
			$('#editsign').val(result.sign);
			$('#editextSrc').val(result.extSrc);

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


//添加
$(function() {

	$("#btn_add2").click(
			function() {
				if (passed('添加')) {
					$('.tableBody').addClass('animated slideOutLeft');
					setTimeout(function() {
						$('.tableBody').removeClass('animated slideOutLeft')
								.css('display', 'none');
					}, 500)
					$('.addBody').css('display', 'block');
					$('.addBody').addClass('animated slideInRight');
					$("#add_saveBtn").attr("disabled", false);
					$("#addName").selectpicker('refresh');
					
				} else {
					$('.popup_de .show_msg').text('没有权限，请联系管理员！');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}
			});
	// 点击添加页面返回按钮
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
					$.post("addToExtSign.action", 
							data1, 
							function(data) {
						// 后台返回添加成功
						if (data.result == 1) {
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
								url : 'findAllList.action'
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
	//修改
	$('#edit_saveBtn').click(
			function() {
				mark=true;
				// 点击保存时触发表单验证
				$('#editForm').bootstrapValidator('validate');
				// 如果表单验证正确，则请求后台修改配置
				if ($("#editForm").data('bootstrapValidator').isValid()) {
						$('.popup_de .show_msg').text('确定要修改吗？');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click',function() {
							var _info = $('#editForm').serialize();
							data = decodeURIComponent(_info, true);
							if(mark){
								$.post(
										"updateExtSign.action",
										data,
										function(data) {
											if (data.result == 1) {
												$('.changeBody').addClass('animated slideOutLeft');
												setTimeout(function() {
													$('.changeBody').removeClass('animated slideOutLeft')
													.css('display','none');},500);
												$('.tableBody').css('display','block').addClass('animated slideInRight');
												$('#editForm').data('bootstrapValidator').resetForm(true);
												$('#mytab').bootstrapTable('refresh',
														{url : 'findAllList.action'});
												$('.popup_de').removeClass('bbox');
											} else if (data.result == 2) {
												$('.popup_de .show_msg').text('该条数据已存在，请重新修改！');
												$('.popup_de .btn_cancel').css('display','none');
												$('.popup_de').addClass('bbox');
												$('.popup_de .btn_submit').one('click',function() {
													$('.popup_de').removeClass('bbox');
												})
											} else {
												$('.popup_de .show_msg').text('修改失败，请重新修改！');
												$('.popup_de .btn_cancel').css('display','none');
												$('.popup_de').addClass('bbox');
												$('.popup_de .btn_submit').one('click',
														function() {
													$('.popup_de').removeClass('bbox');
												})
											}
										}, "json");
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
		// 批量删除
		$('#btn_del2').click(function() {
			mark=true;
			if (passed('批量删除')) {
				var dataArr = $('#mytab').bootstrapTable('getSelections');
				$('.popup_de .show_msg').text('确定要删除吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				$('.popup_de .btn_submit').one('click', function() {
					var ID = [];
					var i = 0;
					for (i; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						if(mark){
							$.post("delExtSignBatch.action", {
								ids : ID
							}, function(data) {
								if (data.result == 1) {
									$('.popup_de .show_msg').text('删除成功！');
									$('.popup_de .btn_cancel').css('display', 'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$('#btn_del2').addClass('fadeOutRight');
										setTimeout(function() {
											$('#btn_del2').css('display', 'none');
										}, 400);
										$('.popup_de').removeClass('bbox');
									})
									$('#mytab').bootstrapTable('refresh', {
										url : 'findAllList.action'
									});
								} else {
									$('.popup_de .show_msg').text('删除出错！');
									$('.popup_de .btn_cancel').css('display', 'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$("#edit_saveBtn").attr("disabled", false);
										$('.popup_de').removeClass('bbox');
									})
									$('#mytab').bootstrapTable('refresh', {
										url : 'findAllList.action'
									});
								}
							})
							mark=false;
						}
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
		// 批量删除按钮的出现与消失
		$('.table').change(
				function() {
					var dataArr = $('#mytab .selected');
					if (dataArr.length >= 1) {
						$('#btn_del2').css('display', 'block').removeClass(
								'fadeOutRight').addClass('animated fadeInRight');
					} else {
						$('#btn_del2').addClass('fadeOutRight');
						setTimeout(function() {
							$('#btn_del2').css('display', 'none');
						}, 400);
					}
				});
		$("[name='btSelectAll']").click(function(){
			var dataArr = $('#mytab .selected');
			if (dataArr.length >= 1) {
				$('#btn_del2').css('display', 'block').removeClass(
						'fadeOutRight').addClass('animated fadeInRight');
			} else {
				$('#btn_del2').addClass('fadeOutRight');
				setTimeout(function() {
					$('#btn_del2').css('display', 'none');
				}, 400);
			}
		});

		$("#btn_import2").click(function(){
			if(passed('批量导入')){
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
		// 点击批量导入页面返回按钮
		$('.import_backBtn').click(function() {
			$("#importForm")[0].reset();
			$("#importchaId").selectpicker('refresh');
			$("#importFlag").selectpicker('refresh');
			$("#importBody").modal('hide');
			$("#import_saveBtn").attr("disabled", false);
			$("#importForm").data('bootstrapValidator').destroy();
			$('#importForm').data('bootstrapValidator', null);
		});
		$("#import_saveBtn").click(function(){
			var uploadFile=$("#uploadFile").val();
			if(uploadFile =="" ||uploadFile ==null ||uploadFile==undefined){
				alert("请选择excel文件");
				return;
			}
			// 点击保存时触发表单验证
			$('#importForm').bootstrapValidator('validate');
			// 如果表单验证正确
			if ($("#importForm").data('bootstrapValidator').isValid()) {
				$("#import_saveBtn").attr("disabled", true);
				var formData = new FormData($( "#importForm" )[0]);
				$.ajax({
					url : "parseFile.action",
					data : formData,
					dataType : "json",
					type : "post",
					processData: false,
					contentType: false,
					success : function(data) {
						$("#headTab").empty();
						$("#detailsTab").empty();
						var list=data.list;
						var s="";
						var num=0;
						for(var i=0;i<list.length;i++){
							var obj=list[i];
							s+="<tr>";
							if(num<obj.length){
								num=obj.length;
							}
							for(var j=0;j<obj.length;j++){
								s+="<td>"+obj[j]+"</td>";
							}
							s+="<td><input type='checkbox' name='check' value='"+i+"'></td></tr>";
							if(i==9){
								break;
							}
						}
						var sb="<tr>";
						for(var i=0;i<num;i++){
							sb+="<th><select name='column' data='"+i+"' id='column"+i+"'><option value=''>请选择列名</option><option value='appId'>账号ID(appID)</option><option value='sign'>签名</option><option value='extSrc'>扩展号码</option></select></th>";
						}
						sb+="<th>剔除</th></tr>"
						$("#detailsTab").append(s);
						$("#headTab").append(sb);
						$("#hiddenUpdate").val(data.update);
						$("#hiddenFile").val(data.file);
						$("#nextBody").modal();
					}
					
				});
				
				
				$("#importBody").modal('hide');
				$("#nextBody").modal();
				$("#import_saveBtn").attr("disabled", false);
				$("#importForm").data('bootstrapValidator').destroy();
				$('#importForm').data('bootstrapValidator', null);
				importValidator();
			}
		});
		
		$("#next_saveBtn").click(function(){
			var flag=true;
			var sp=new Map();
			var i=0;
			$("[name='column']").each(function(){
				var st=$(this).val();
				if(!(st == null || st == "" || st == undefined)){
					flag=false;
				}
				sp[$(this).val()]=$(this).attr("data");
			});
			if(flag){
				alert("请选择对应的字段");
				return;
			}
			if(Object.keys(sp).length!=3){
				alert("请选择对应的账号ID、扩展号、签名");
				return;
			}
			var check=[];
			var j=0;
			$("[name='check']:checked").each(function(){
				var a=$(this).val();
				check[j]=a;
				j++;
			});
			if(check.length==0){
				check[0]=-1;
			}
			var update=$("#hiddenUpdate").val();
			var file=$("#hiddenFile").val();
			$.ajax({
				url:"importData.action",
				type:"post",
				data:{
					check:check,
					sp:JSON.stringify(sp),
					update:update,
					file:file
				},
				success:function(obj){
					if(obj.result>0){
						$('.popup_de .show_msg').text('导入成功');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}else{
						$('.popup_de .show_msg').text('导入失败');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAllList.action'
					});
					$("#nextBody").modal('hide');
				},
				dataType:"json"
			});
			
		});
});


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


