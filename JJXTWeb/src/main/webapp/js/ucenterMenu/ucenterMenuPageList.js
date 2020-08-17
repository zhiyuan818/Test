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
		url : "findUcenterMenu.action",// 请求后台url
		height : tableHeight(),// 高度调整
		toolbar : '#toolbar',// 工具按钮容器
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
		pageList : [ 30, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : true,// 是否显示所有的列
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
		}, {
			title : 'ID',
			field : 'id',
			visible : false
		}, {
			title : '菜单名称',
			field : 'title',
			sortable : true
		}, {
			title : '菜单路径',
			field : 'menuLink'
		}, {
			title : '图标名称',
			field : 'iconClass',
		}, {
			title : '父类名称',
			field : 'parentName'
		}, {
			title : 'parentId',
			field : 'parentId',
			visible : false
		}, {
			title : 'spId',
			field : 'spId',
			visible : false
		}, {
			title : '按钮',
			field : 'buttons',
		}, {
			fileld : 'ID',
			title : '操作',
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
	addValidator();
	updateValidator();
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#searchTitle").val("");
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 10,
			sort : '',
			order : 'asc'
		});
	});
})
function addValidator() {
	// 菜单添加表单验证
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			title : {
				validators : {
					notEmpty : {
						message : '菜单名称不能为空'
					},stringLength : {
						min : 2,
						max : 10,
						message : '菜单名称长度必须在2到10之间'
					}
				}
			},
			iconClass : {
				validators : {
					notEmpty : {
						message : '图标路径不能为空'
					}
				}
			},
			parentId : {
				validators : {
					notEmpty : {
						message : '父菜单不能为空'
					}
				},
				regexp : {
					regexp : '/^(0|[1-9][0-9]*)$/',
					message : '请选择父类菜单'
				}
			}
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
			id : {
				validators : {
					notEmpty : {
						message : 'ID不能为空'
					}
				}
			},
			title : {
				validators : {
					notEmpty : {
						message : '菜单名称不能为空'
					},stringLength : {
						min : 2,
						max : 10,
						message : '菜单名称长度必须在2到10之间'
					}
				}
			},
			iconClass : {
				validators : {
					notEmpty : {
						message : '图标路径不能为空'
					}
				}
			},
			parentId : {
				validators : {
					notEmpty : {
						message : '父菜单不能为空'
					}
				},
				regexp : {
					regexp : /[0-9]*/,
					message : '请选择父类菜单'
				}
			}
		}
	});
}
function tableHeight() {
	return $(window).height() - 50;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		searchTitle : $("#searchTitle").val()
	// 模糊字段
	}
}
function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('"
			+ row.id
			+ "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
	result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('"
			+ row.id
			+ "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";

	return result;
}
// 每行操作的修改数据
function EditViewById(id) {
	if (passed('修改')) {
		var data = new Object();
		$.post(
		"findUcenterMenuById.action",
		{
			id : id
		},
		function(obj) {
			var parent=obj.parentMenu;
			var result=obj.menu;
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass(
						'animated slideOutLeft').css('display',
						'none');
			}, 500)
			$('.changeBody').css('display', 'block');
			$('.changeBody').addClass('animated slideInRight');
			$('#editId').val(result.id);
			$('#editTitle').val(result.title);
			$('#editMenuLink').val(result.menuLink);
			$('#editIconClass option').each(function(){
			if( $(this).val() == result.iconClass){   
				$(this).attr("selected","selected");
			}   
			}); 
			$("#buttonsDiv").empty();
			$("#buttonsDiv").append(
							"<input name='buttons' type='checkbox' value='新增' />新增");
			$("#buttonsDiv")
					.append(
							"<input name='buttons' type='checkbox' value='删除' />删除");
			$("#buttonsDiv")
					.append(
							"<input name='buttons' type='checkbox' value='修改' />修改");
			var boxObj = $(".changeBody input:checkbox[name='buttons']"); // 获取所有的复选框
			// var expresslist = '${supplierExpressids}';
			// //用el表达式获取在控制层存放的复选框的值为字符串类型
			if (result.buttons == "" || result.buttons == null
					|| result.buttons == undefined) {
			} else {
				var express = result.buttons.split(','); // 去掉它们之间的分割符“，”
				for (i = 0; i < express.length; i++) {
					if (express[i] != '新增'
							&& express[i] != '删除'
							&& express[i] != '修改') {
						// 插入替换后的Input
						$("#buttonsDiv").append(
								"<input type='checkbox' name='buttons' value='"
										+ express[i] + "'>"
										+ express[i] + " ");
					}
				}
				var boxObj = $(".changeBody input:checkbox[name='buttons']"); // 获取所有的复选框
				for (i = 0; i < boxObj.length; i++) {
					for (j = 0; j < express.length; j++) {
						if (boxObj[i].value == express[j]) // 如果值与修改前的值相等
						{
							boxObj[i].checked = true;
							break;
						}
					}
				}
			}
			$("#buttonsDiv").append("<span onclick='changeToInput(this)'>自定义</span>");
			$("#editParentId").empty();
			$("#editParentId").append("<option value=''>---请选择---</option>");
			$("#editParentId").append("<option value='0' >根菜单</option>");
			for ( var i in parent) {
				$("#editParentId").append(
						"<option value='" + parent[i].id + "'>"
								+ parent[i].title + "</option>");
			}
			$("#editParentId")
					.find("option[value='" + result.parentId + "']").attr(
							'selected', true);
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
		$('.popup_de .show_msg').text('确定要删除该菜单吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deleteUcenterMenu.action", {
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
							url : 'findUcenterMenu.action'
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
$(function() {
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
					// ajax 追加父菜单名称
					$.ajax({
						url : "findParentMenu.action",
						dataType : "json",
						type : "post",
						success : function(result) {
							$("#parentId").empty();
							$("#parentId").append(
									"<option value=''>---请选择---</option>");
							$("#parentId").append(
									"<option value='0'>根菜单</option>");
							for ( var i in result) {
								$("#parentId")
										.append(
												"<option value='"
														+ result[i].id + "'>"
														+ result[i].title
														+ "</option>");
							}
						}
					});
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
				$("#addForm")[0].reset();
				$('.addBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.addBody').removeClass('animated slideOutLeft').css(
							'display', 'none');
				}, 500)
				$('.tableBody').css('display', 'block').addClass(
						'animated slideInRight');
				$("#addForm").data('bootstrapValidator').destroy();
				$('#addForm').data('bootstrapValidator', null);
				addValidator();
				$("#div_buttons").empty();
				$("#div_buttons").append("<input name='buttons' type='checkbox' value='新增' />新增 <input name='buttons' type='checkbox' value='删除' />删除 <input name='buttons' type='checkbox' value='修改' />修改 <span onclick='changeToInput(this)'>自定义</span>");
				$("#add_saveBtn").attr("disabled", false);
			});
	// 点击菜单页面提交按钮
	$('#add_saveBtn').click(
			function() {
				// 点击保存时触发表单验证
				$('#addForm').bootstrapValidator('validate');
				// 如果表单验证正确，则请求后台添加用户
				if ($("#addForm").data('bootstrapValidator').isValid()) {
					$("#add_saveBtn").attr("disabled", true);
					var _info = $('#addForm').serialize();
					data = decodeURIComponent(_info, true);
					$.post("addUcenterMenu.action", data, function(data) {
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
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$('#addForm')[0].reset();
							$('#mytab').bootstrapTable('refresh', {
								url : 'findUcenterMenu.action'
							});
						} else {
							$('.popup_de .show_msg').text('新增失败！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function(){
								$('.popup_de').removeClass('bbox');
							})
							$('#mytab').bootstrapTable('refresh',{url:'findUcenterManager.action'});
						}
						$("#add_saveBtn").attr("disabled", false);
					})
				}
			});
	// 根据复选框批量删除
	$('#btn_delete')
			.click(
					function() {
						mark = true;
						if (passed('删除')) {
							var dataArr = $('#mytab').bootstrapTable(
									'getSelections');
							$('.popup_de .show_msg').text('确定要删除该菜单吗?');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_cancel').css('display',
									'inline-block');
							$('.popup_de .btn_submit')
									.one(
											'click',
											function() {
												if (mark) {
													var ID = [];
													for (var i = 0; i < dataArr.length; i++) {
														ID[i] = dataArr[i].id;
													}
													if (ID.length > 0) {
														$
																.post(
																		"deleteUcenterMenuBatch.action",
																		{
																			ids : ID
																		},
																		function(
																				data) {
																			if (data.result > 0) {
																				$(
																						'.popup_de .show_msg')
																						.text(
																								'删除成功！');
																				$(
																						'.popup_de .btn_cancel')
																						.css(
																								'display',
																								'none');
																				$(
																						'.popup_de')
																						.addClass(
																								'bbox');
																				$(
																						'.popup_de .btn_submit')
																						.one(
																								'click',
																								function() {
																									$(
																											'.popup_de')
																											.removeClass(
																													'bbox');
																								})
																				$(
																						'#mytab')
																						.bootstrapTable(
																								'refresh',
																								{
																									url : 'findUcenterMenu.action'
																								});
																			} else {
																				$('.popup_de .show_msg').text('删除失败！');
											    								$('.popup_de .btn_cancel').css('display','none');
											    								$('.popup_de').addClass('bbox');
											    								$('.popup_de .btn_submit').one('click',function(){
											    									$('.popup_de').removeClass('bbox');
											    								})
																			}
																		});
													} else {
														$('.popup_de .show_msg')
																.text(
																		'请选择要删除的菜单！');
														$('.popup_de')
																.addClass(
																		'bbox');
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
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 删除按钮与修改按钮的出现与消失
	$('.table').change(
			function() {
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
				} else {
					$('#btn_delete').addClass('fadeOutRight');
					setTimeout(function() {
						$('#btn_delete').css('display', 'none');
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
		} else {
			$('#btn_delete').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_delete').css('display', 'none');
			}, 400);
		}
	});
	// 更新菜单数据
	$('#btn_edit')
			.click(
					function() {
						if (passed('修改')) {
							var dataArr = $('#mytab').bootstrapTable(
									'getSelections');
							if (typeof (dataArr) != "undefined") {
								$('.tableBody').addClass(
										'animated slideOutLeft');
								setTimeout(function() {
									$('.tableBody').removeClass(
											'animated slideOutLeft').css(
											'display', 'none');
								}, 500)
								$('.changeBody').css('display', 'block');
								$('.changeBody').addClass(
										'animated slideInRight');
								$('#editId').val(dataArr[0].id);
								$('#editTitle').val(dataArr[0].title);
								$('#editMenu_link').val(dataArr[0].menuLink);
								$("#editIconClass").find("option[value='" + dataArr[0].iconClass + "']").attr('selected', true);
								$("#buttonsDiv").empty();
								$("#buttonsDiv")
										.append(
												"<input name='buttons' type='checkbox' value='新增' />新增");
								$("#buttonsDiv")
										.append(
												"<input name='buttons' type='checkbox' value='删除' />删除");
								$("#buttonsDiv")
										.append(
												"<input name='buttons' type='checkbox' value='修改' />修改");
								if (dataArr[0].buttons == ""
										|| dataArr[0].buttons == null
										|| dataArr[0].buttons == undefined) {
								} else {
									var express = dataArr[0].buttons.split(','); // 去掉它们之间的分割符“，”
									for (i = 0; i < express.length; i++) {
										if (express[i] != '新增'
												&& express[i] != '删除'
												&& express[i] != '修改') {
											// 插入替换后的Input
											$("#buttonsDiv").append(
													"<input type='checkbox' name='buttons' value='"
															+ express[i] + "'>"
															+ express[i] + " ");
										}
									}
									var boxObj = $(".changeBody input:checkbox[name='buttons']"); // 获取所有的复选框
									for (i = 0; i < boxObj.length; i++) {
										for (j = 0; j < express.length; j++) {
											if (boxObj[i].value == express[j]) // 如果值与修改前的值相等
											{
												boxObj[i].checked = true;
												break;
											}
										}
									}
								}
								$("#buttonsDiv")
										.append(
												"<span onclick='changeToInput(this)'>自定义</span>");
								// 获取父菜单
								$
										.ajax({
											url : "findParentMenu.action",
											dataType : "json",
											type : "post",
											success : function(result) {
												$("#editParentId").empty();
												$("#editParentId")
														.append(
																"<option value=''>---请选择---</option>");
												$("#editParentId")
														.append(
																"<option value='0' >根菜单</option>");
												for ( var i in result) {
													$("#editParentId")
															.append(
																	"<option value='"
																			+ result[i].id
																			+ "'>"
																			+ result[i].title
																			+ "</option>");
												}
												$("#editParentId")
														.find(
																"option[value='"
																		+ dataArr[0].parentId
																		+ "']")
														.attr('selected',
																'selected');
											}
										});
							}

						} else {
							$('.popup_de .show_msg').text('没有权限，请联系管理员！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
						}
					})
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
				$("#editForm").data('bootstrapValidator').destroy();
				$('#editForm').data('bootstrapValidator', null);
				updateValidator();
			})
	// 修改页面保存按钮事件
	$('#edit_saveBtn').click(
			function() {
				$('#editForm').bootstrapValidator('validate');
				if ($("#editForm").data('bootstrapValidator').isValid()) {
					$("#edit_saveBtn").attr("disabled", true);
					var _info = $('#editForm').serialize();
					data = decodeURIComponent(_info, true);
					$.post("updateUcenterMenu.action", data,
							function(data) {
								if (data.result > 0) {
									// 隐藏修改与删除按钮
									$('#btn_delete').css('display', 'none');
									$('#btn_edit').css('display', 'none');
									// 回退到人员管理主页
									$('.changeBody').addClass(
											'animated slideOutLeft');
									setTimeout(function() {
										$('.changeBody').removeClass(
												'animated slideOutLeft').css(
												'display', 'none');
									}, 500)
									$('.tableBody').css('display', 'block')
											.addClass('animated slideInRight');
									// 刷新人员管理主页
									$('#mytab').bootstrapTable('refresh', {
										url : 'findUcenterMenu.action'
									});
									// 修改页面表单重置
									$("#editForm").data('bootstrapValidator')
											.destroy();
									$('#editForm').data('bootstrapValidator',
											null);
									updateValidator();
								} else {
									$('.popup_de .show_msg').text('修改失败！');
    								$('.popup_de .btn_cancel').css('display','none');
    								$('.popup_de').addClass('bbox');
    								$('.popup_de .btn_submit').one('click',function(){
    									$('.popup_de').removeClass('bbox');
    								})
								}
								$("#edit_saveBtn").attr("disabled", false);
							}, "json")
				}
			})

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
function changeToInput(obj) {
	var Newobj = document.createElement('input');
	Newobj.value = obj.innerText;
	Newobj.setAttribute("type", "text");
	Newobj.setAttribute("class", "Input_Text");
	Newobj.setAttribute("name", obj.name);
	Newobj.setAttribute("onblur", 'changeToCheckbox(this)');
	// 插入替换后的Input
	obj.parentNode.appendChild(Newobj);
	// 删除表单原控件
	// obj.removeNode();
	obj.parentNode.removeChild(obj);
}
function changeToCheckbox(obj) {
	var Newobj = document.createElement('input');
	Newobj.value = obj.value;
	mark=false;
	$("[name='buttons']").map(function() {
		if($(this).val()==Newobj.value){
			$('.popup_de .show_msg').text('该按钮已存在！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			mark=true;
		}
	})
	if(mark){
		return;
	}
	Newobj.setAttribute("type", "checkbox");
	Newobj.setAttribute("class", "Input_Text");
	Newobj.setAttribute("name", 'buttons');
	// 插入替换后的Input
	obj.parentNode.appendChild(Newobj);
	obj.parentNode.append(obj.value + " ");
	var Newobj2 = document.createElement('span');
	Newobj2.innerText = '自定义';
	Newobj2.setAttribute("onclick", "changeToInput(this)");
	obj.parentNode.appendChild(Newobj2);
	// 删除表单原控件
	// obj.removeNode();
	obj.parentNode.removeChild(obj);
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