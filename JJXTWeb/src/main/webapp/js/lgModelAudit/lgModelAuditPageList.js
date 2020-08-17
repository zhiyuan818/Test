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
		url : "findLgModelAudit.action",// 请求后台url
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
		}, {
			title : 'ID',
			field : 'id',
			visible : false
		}, {
			title : '模板内容',
			field : 'content',
			width : '60%'
		}, {
			title : '账户',
			field : 'appName',
			formatter : function(value,row,index){
				return row.appId+":"+value;
			}
		}, {
			title : '模板类别',
			field : 'auditFlag',
			width : '10%',
			formatter : function(value, row, index){
				if(value=='black'){
					return '黑模板';
				}else if(value=='white'){
					return '白模板';
				}else{
					return '-';
				}
			}	
		}, {
			title : '时间',
			field : 'createTime',
			width : '15%',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
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
	// 菜单添加表单验证
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			content : {
				validators : {
					notEmpty : {
						message : '模板不能为空'
					},
					stringLength : {
						max : 500,
						message : '审核模板长度不能超过500个字'
					},
					remote : {
						url : "checkAuditContent.action",
						type : "POST",
						delay : 500,
						message : "模板已存在"
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
			id : {
				validators : {
					notEmpty : {
						message : 'ID不能为空'
					}
				}
			},
			content : {
				validators : {
					notEmpty : {
						message : '模板不能为空'
					},
					stringLength : {
						max : 500,
						message : '审核模板长度不能超过500个字'
					},
					callback:{
	         			   message:'审核模板已存在',
	                        callback:function(value, validator,$field){
	                     	   var oldContent = $('#oldEditContent').val();
	                     	   var content = $('#editContent').val();
	                     	   if(oldContent==content){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'checkAuditContent.action',
	                     				data:{
	                     					oldContent:oldContent,
	                     					content:content
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
			}
		}
	});
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#search_content").val('');
		$("#search_appId").val('');
		$("#search_auditFlag").val('');
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 100,
		});
	});
})
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
function tableHeight() {
	return $(window).height() - 50;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		content : $("#search_content").val(),
		appId :$("#search_appId").val(),
		auditFlag :$("#search_auditFlag").val()
	}
}
function changeContent(value) {
	var content = value;
	if (content.length > 20) {
		return content.substring(0, 20) + "···";
	}
	return content;
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
				$("#appId").val('');
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
					$.post("addLgModelAudit.action", data, function(data) {
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
								url : 'findLgModelAudit.action'
							});
						} else {
						}
					})
				}
			});
	// 根据复选框批量删除
	$('#btn_delete').click(function() {
		mark = true;
		if (passed('删除')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除模板吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				if (mark) {
					var ID = [];
					for (var i = 0; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						$.post("deleteLgModelAuditBatch.action",
								{ids : ID},
								function(data) {
									if (data.result > 0) {
										$('.popup_de .show_msg').text('删除成功！');
										$('.popup_de .btn_cancel').css('display','none');
										$('.popup_de').addClass('bbox');
										$('.popup_de .btn_submit').one('click',function() {
											$('.popup_de').removeClass('bbox');
										})
										$('#mytab').bootstrapTable('refresh',{url : 'findLgModelAudit.action'});
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
						$('.popup_de .show_msg').text('请选择要删除的模板！');
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
	$('#btn_edit').click(
			function() {
				if (passed('修改')) {
					var dataArr = $('#mytab').bootstrapTable('getSelections');
					if (typeof (dataArr) != "undefined") {
						$('.tableBody').addClass('animated slideOutLeft');
						setTimeout(function() {
							$('.tableBody')
									.removeClass('animated slideOutLeft').css(
											'display', 'none');
						}, 500)
						$('.changeBody').css('display', 'block');
						$('.changeBody').addClass('animated slideInRight');
						$('#editId').val(dataArr[0].id);
						$('#editContent').val(dataArr[0].content);
						$("#oldEditContent").val(dataArr[0].content);
						$('#editAuditFlag option').each(function(){
							if( $(this).val() == dataArr[0].auditFlag){   
								$(this).prop("selected","selected");
							}   
						});
						$('#editAppId').selectpicker('val',dataArr[0].appId);
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
				$('#editForm').data('bootstrapValidator').resetForm(true);
			})
	// 修改页面保存按钮事件
	$('#edit_saveBtn').click(
			function() {
				$('#editForm').bootstrapValidator('validate');
				if ($("#editForm").data('bootstrapValidator').isValid()) {
					$("#edit_saveBtn").attr("disabled", true);
					var _info = $('#editForm').serialize();
					data = decodeURIComponent(_info, true);
					$.post("updateLgModelAudit.action", data, function(data) {
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
								url : 'findLgModelAudit.action'
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
})
function EditViewById(id) {
	if (passed('修改')) {
		var data = "";
		$.post("findLgModelAuditById.action", {
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
			$('#editId').val(result.id);
			$('#editContent').val(result.content);
			$('#oldEditContent').val(result.content);
			$('#editAuditFlag option').each(function(){
				if( $(this).val() == result.auditFlag){   
					$(this).prop("selected","selected");
				}   
			});
			$('#editAppId').selectpicker('val',result.appId);
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
		$('.popup_de .show_msg').text('确定要删除模板吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deleteLgModelAudit.action", {
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
							url : 'findLgModelAudit.action'
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