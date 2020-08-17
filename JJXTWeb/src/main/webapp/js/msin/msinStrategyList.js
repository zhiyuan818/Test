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
		url : "findAllData.action",// 请求后台url
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
		uniqueId : "id", // 每一行的唯一标识，一般为主键列
		pageSize : 100,// 单页记录数
		pageList : [ 50, 100, 200 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar : '#toolbar',// 指定工作栏
		columns : [{
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 50,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'ID',
			field : 'id',
			visible : false
		}, {
			title : '账户',
			field : 'appId',
		}, {
			title : '级别',
			field : 'level',
		}, {
			title : '手机号码',
			field : 'phoneNumber',
		}, {
			fileld : 'id',
			title : '操作',
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
	$('#importForm').bootstrapValidator({
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
			level : {
				validators : {
					notEmpty : {
						message : '级别不能为空'
					}
				}
			},
			phoneNumber : {
				validators : {
					notEmpty : {
						message : '手机号码不能为空'
					},
					regexp : {
						regexp : /^(\(\d{3,4}\)|\d{3,4}-)?\d{11}$/,
						message : '手机号格式不正确'
					}
				}
			}
		}
	});
	
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#sel_appId").val('');
		$("#sel_level").val('');
		$("#sel_phone").val('');
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
	
	//添加
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
		$("#appId").selectpicker('refresh');
		$("#level").selectpicker('refresh');
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		$("#add_saveBtn").attr("disabled", false);
	});
	
	$('#add_saveBtn').click(function() {
		mark = true;
		// 点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$("#add_saveBtn").attr("disabled", true);
			var _info = $('#addForm').serialize();
			data = decodeURIComponent(_info, true);
			if(mark){
				$.post("addMsinStrategy.action", data, function(data) {
					// 后台返回添加成功
					if (data.result == 1) {
						$("#addForm")[0].reset();
						$("#addBody").modal("hide");
						$("#appId").selectpicker('refresh');
						$("#level").selectpicker('refresh');
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						$("#add_saveBtn").attr("disabled", false);
						
						$('.popup_de .show_msg').text('添加成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#mytab').bootstrapTable('refresh',{url : 'findAllData.action'});
						})
						
					}else if(data.result == 0){
						$("#addForm")[0].reset();
						$("#addBody").modal("hide");
						$("#appId").selectpicker('refresh');
						$("#level").selectpicker('refresh');
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						$("#add_saveBtn").attr("disabled", false);
						
						$('.popup_de .show_msg').text('请先添加该账户和级别的配置！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
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
				mark=false;
			}
		}
		
	});
	
	// 根据复选框批量删除
	$('#btn_del').click(function() {
		mark = true;
		if (passed('批量删除')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				if (mark) {
					var ID = [];
					for (var i = 0; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						$.post("delMsinBatch.action",
								{ids : ID},
								function(data) {
									if (data.result > 0) {
										$('.popup_de .show_msg').text('删除成功！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
										$('#mytab').bootstrapTable('refresh',{url : 'findAllData.action'});
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
						$('.popup_de .show_msg').text('请先勾选待删除数据！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
					}
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
	});
	
	// 删除按钮与修改按钮的出现与消失
	$('.table').change(function() {
				var dataArr = $('#mytab .selected');
				if (dataArr.length >= 1) {
					$('#btn_del').css('display', 'block').removeClass(
							'fadeOutRight').addClass('animated fadeInRight');
				} else {
					$('#btn_del').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_del').css('display', 'none');
					}, 400);
				}
			});
	$("[name='btSelectAll']").click(function(){
		var dataArr = $('#mytab .selected');
		if (dataArr.length >= 1) {
			$('#btn_del').css('display', 'block').removeClass(
					'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_del').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del').css('display', 'none');
			}, 400);
		}
	});
	
});


$(function() {
	$('#import_btn').click(function() {
		if(passed('批量导入')){
			$("#importBody").modal();
		}else{
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
		$("#importappId").selectpicker('refresh');
		$("#importlevel").selectpicker('refresh');
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
					if(obj.result == '0'){
						$('.popup_de .show_msg').text('导入成功！');
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
					}else if(obj.result == '-9998'){
						$('.popup_de .show_msg').text('无该账户和级别的配置,请先添加！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						fileNameList.remove(obj.fileName);
					}
				},
				dataType:'json'
			});
		}
	});
	
})


function deleteById(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("delMsinStrategyById.action", {
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
							url : 'findAllData.action'
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




function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteById('"
			+ row.id
			+ "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
	return result;
}


function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		appId : $("#sel_appId").val(),
		level : $("#sel_level").val(),
		phoneNumber : $("#sel_phone").val()
	}
}


function tableHeight() {
	return $(window).height() - 50;
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

