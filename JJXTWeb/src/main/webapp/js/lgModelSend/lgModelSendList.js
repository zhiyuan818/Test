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
		url : "findLgModelSendList.action",// 请求后台url
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
		}, {
			title : 'cmccChannelId',
			field : 'cmccChannelId',
			visible : false
		}, {
			title : '移动通道',
			field : 'cmccChannelName',
			formatter : changeCmcc
		}, {
			title : 'unicomChannelId',
			field : 'unicomChannelId',
			visible : false
		}, {
			title : '联通通道',
			field : 'unicomChannelName',
			formatter : changeUnicom
		}, {
			title : 'telecomChannelId',
			field : 'telecomChannelId',
			visible : false
		}, {
			title : '电信通道',
			field : 'telecomChannelName',
			formatter : changeTelecom
		}, {
			title : '模板内容',
			field : 'content',
			width : '50%'
		}, {
			title : '时间',
			field : 'createTime',
			width : '10%',
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
	addValidator();
	updateValidator();
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
		$("#searchId").val('');
		$("#searchContent").val('');
		$("#searchChannelId").selectpicker('val', '');
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 100,
			sort : '',
			order : 'asc'
		});
	});
	// 点击添加按钮
	$("#btn_add").click(function() {
		if (passed('新增')) {
			$("#addBody").modal();
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	$('.add_backBtn').click(function() {
		$("#addForm")[0].reset();
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#add_saveBtn").attr("disabled", false);
		$("#addBody").modal("hide");
		$("#cmccChannelId").selectpicker('refresh');
		$("#unicomChannelId").selectpicker('refresh');
		$("#telecomChannelId").selectpicker('refresh');
	});
	$("#add_saveBtn").click(function() {
		mark=true;
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				$("#add_saveBtn").attr("disabled", true);
				var _info = $('#addForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.ajax({
						url : "addLgModelSend.action",
						data : data,
						type : "post",
						success : function(obj) {
							$("#addBody").modal("hide");
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$('#addForm')[0].reset();
							$("#cmccChannelId").selectpicker('refresh');
							$("#unicomChannelId").selectpicker('refresh');
							$("#telecomChannelId").selectpicker('refresh');
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('添加成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findLgModelSendList.action'
								});
							} else {
								$('.popup_de .show_msg').text('添加失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findLgModelSendList.action'
								});
							}
							$("#add_saveBtn").attr("disabled", false);
						},
						dataType : "json"
					})
					mark=false;
				}
			})
		}
	});
	$('.edit_backBtn').click(function() {
		$("#editForm").data('bootstrapValidator').destroy();
		$('#editForm').data('bootstrapValidator', null);
		updateValidator();
		$("#changeBody").modal('hide');
		$("#editForm")[0].reset();
	});
	$("#edit_saveBtn").click(function() {
		mark=true;
		$('#editForm').bootstrapValidator('validate');
		if ($("#editForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				$("#edit_saveBtn").attr("disabled", true);
				var _info = $('#editForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.ajax({
						url : "updateLgModelSend.action",
						type : "post",
						async : false,
						data : data,
						success : function(obj) {
							$("#changeBody").modal("hide");
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('修改成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#mytab').bootstrapTable('refresh', {
										url : 'findLgModelSendList.action'
									});
								})
							} else {
								$('.popup_de .show_msg').text('修改失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#mytab').bootstrapTable('refresh', {
										url : 'findLgModelSendList.action'
									});
								})
							}
							$("#edit_saveBtn").attr("disabled", false);
						},
						error : function() {
							console.log("error");
						}
					});
					mark=false;
				}
			})
		}
	});
	
	$('#btn_del').click(function() {
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
						$.post("delLgModelSendBatch.action", {
							ids : ID
						}, function(data) {
							if (data.result > 0) {
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
								$('#mytab').bootstrapTable('refresh', {
									url : 'findLgModelSendList.action'
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
									url : 'findLgModelSendList.action'
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

	});
	// 批量删除按钮的出现与消失
	$('.table').change(
			function() {
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
function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"updateLgModelSend('"
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
		content : $("#searchContent").val(),
		channelId : $("#searchChannelId").val(),
		id : $("#searchId").val()
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
			content : {
				validators : {
					notEmpty : {
						message : '短信模板不能为空'
					},
					remote : {
						url : "contentValidate.action",
						type : "POST",
						delay : 500,
						message : "短信模板已存在"
					},
					stringLength : {
						min : 1,
						max : 500,
						message : '短信模板长度必须在1到500位之间'
					}
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
			content : {
				validators : {
					notEmpty : {
						message : '短信模板不能为空'
					},
					stringLength : {
						min : 1,
						max : 500,
						message : '短信模板长度必须在1到500位之间'
					},callback:{
	         			   message:'短信模板已存在',
	                        callback:function(value, validator,$field){
	                     	   var oldContent = $('#oldEditContent').val();
	                     	   var content = $('#editContent').val();
	                     	   if(oldContent==content){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'contentValidate.action',
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
}
function changeContent(value) {
	var content = value;
	if (content.length > 10) {
		return content.substring(0, 20) + "···";
	}
	return content;
}
function changeCmcc(value, row, index) {
	return row.cmccChannelId + ":" + (value == null ? '' : value)
}
function changeUnicom(value, row, index) {
	return row.unicomChannelId + ":" + (value == null ? '' : value)
}
function changeTelecom(value, row, index) {
	return row.telecomChannelId + ":" + (value == null ? '' : value)
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
function DeleteByIds(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除短信模板吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deleteLgModelSend.action", {
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
							url : 'findLgModelSendList.action'
						});
					} else {
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
function updateLgModelSend(id) {
	if (passed("修改")) {
		$.post("findLgModelSendById.action", {
			id : id
		}, function(obj) {
			$("#editId").val(obj.id);
			$("#editCmccChannelId").selectpicker('val', obj.cmccChannelId);
			$("#editUnicomChannelId").selectpicker('val', obj.unicomChannelId);
			$("#editTelecomChannelId")
					.selectpicker('val', obj.telecomChannelId);
			$("#editContent").val(obj.content);
			$("#oldEditContent").val(obj.content);
			$("#changeBody").modal();
		}, "json");
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
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