$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#globaltab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})

	// 生成用户数据
	$('#globaltab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findAll.action",// 请求后台url
		height : tableHeight(),// 高度调整
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : false,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		showRefresh : true,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		paginationShowPageGo: true,
		toolbar : '#toolbar',// 指定工作栏
		columns : [ {
			title : 'ID',
			field : 'id',
			visible : false
		}, {
			title : 'key',
			field : 'key',
			width : 250
		}, {
			title : 'val',
			field : 'val',
			width : 550,
			formatter : changeContent
		}, {
			title : '备注',
			field : 'remark',
			formatter : changeRemark
		}, {
			title : '操作',
			field : 'id',
			width : 120,
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
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			key : {
				validators : {
					notEmpty : {
						message : 'key不能为空'
					},
					stringLength : {
						max : 50,
						message : 'key长度不能超50'
					},
					regexp : {
						regexp : /^\w+$/,
						message : '必须由数字、字母和下划线组成'
					}
				}
			},
		val : {
			validators : {
				stringLength : {
					max : 500,
					message : 'val最大长度不能超500'
				}
			}
		},
		remark : {
			validators : {
				stringLength : {
					max : 500,
					message : '备注最大长度不能超500'
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
			key : {
				validators : {
					notEmpty : {
						message : 'key不能为空'
					},
					stringLength : {
						max : 50,
						message : 'key长度不能超50'
					},
					regexp : {
						regexp : /^\w+$/,
						message : '必须由数字、字母和下划线组成'
					}
				}
			},
			val : {
				validators : {
					stringLength : {
						max : 500,
						message : 'val最大长度不能超500'
					}
				}
			},
			remark : {
				validators : {
					stringLength : {
						max : 500,
						message : '备注最大长度不能超500'
					}
				}
			}
		}
	});
})



// 删除
function delGlobal(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("deleteGlobalById.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#globaltab').bootstrapTable('refresh', {
							url : 'findAll.action'
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
function updateGlobal(id) {
	if (passed('修改')) {
		$.post("findGlobalSettingById.action", {
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
			$('#editKey').val(result.key);
			$('#editVal').val(result.val);
			$('#editRemark').val(result.remark);

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

	$("#btn_add").click(
			function() {
				if (passed('添加')) {
					$("#addForm")[0].reset();
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
					data = decodeURIComponent(_info, true);
					$.post("addToGlobalSetting.action", data, function(data) {
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
							$('#globaltab').bootstrapTable('refresh', {
								url : 'findAll.action'
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
					})
				}
			});
	
	$('#edit_saveBtn').click(function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#editForm').bootstrapValidator('validate');
		if ($("#editForm").data('bootstrapValidator')
				.isValid()) {
			$("#edit_saveBtn").attr("disabled", true);

			$('.popup_de .show_msg').text('确定要修改吗？');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function() {
				$("#edit_saveBtn").attr("disabled", false);
				// 如果表单验证正确，则请求后台修改配置
				var _info = $('#editForm').serialize();
					data = decodeURIComponent(_info, true);
					if(mark){
						$.post("dataValidation.action",
								data,
								function(data) {
							if (data.result == 0) {
								$('.changeBody').addClass('animated slideOutLeft');
								setTimeout(function() {
									$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
								},500);
								$('.tableBody').css('display','block').addClass('animated slideInRight');
								$('#editForm').data('bootstrapValidator').resetForm(true);
								$('#globaltab').bootstrapTable('refresh',{url : 'findAll.action'});
								$("#edit_saveBtn").attr("disabled",false);
								$('.popup_de').removeClass('bbox');
							} else if (data.result == 1) {
								$('.popup_de .show_msg').text('数据格式不符，请重新修改！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$("#edit_saveBtn").attr("disabled",false);
									$('.popup_de').removeClass('bbox');
								})
							} else if (data.result == 2) {
								$('.popup_de .show_msg').text('账号名不存在，请重新修改！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$("#edit_saveBtn").attr("disabled",false);
									$('.popup_de').removeClass('bbox');
								})
							} else if (data.result == 3) {
								$('.popup_de .show_msg').text('应改为true/false，请重新修改！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$("#edit_saveBtn").attr("disabled",false);
									$('.popup_de').removeClass('bbox');
								})
							} else {
								$('.popup_de .show_msg').text('修改失败，请重新修改！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$("#edit_saveBtn").attr("disabled",false);
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
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$("#edit_saveBtn").attr("disabled", false);
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$("#edit_saveBtn").attr("disabled", false);
		$('.popup_de').removeClass('bbox');
	})

})


function tableHeight() {
	return $(window).height();
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber
	// 页码
	}
}

function changeContent(value) {
	var val = value;
	if (val == "" || val == null || val == undefined) {
		return '-';
	}
	return val;
}

function changeRemark(value, row, index) {
	var content = value;
	if (content != null && content.length > 20) {
		var result = "<a href='javascript:;' data-toggle='modal'  data-target='#Details' onclick=\"findDetails('" + value + "')\" title='详情'>" + content.substring(0, 20) + "..." + "</a>";
		return result;
	}
	return content;
}

function findDetails(row) {
	$("#Details_content").html(row);
}

function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='update_global' onclick=\"updateGlobal('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_global' onclick=\"delGlobal('"
			+ row.id + "')\" title='点击删除'>删除</button>";

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
