$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#myTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	$('#myTab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findRolePageList.action",
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
		toolbar : '#toolbar',// 指定工作栏
		columns : [ {
			title : 'ID',
			field : 'id'
		}, {
			title : '用户简称',
			field : 'managerName'
		}, {
			title : '用户名',
			field : 'chineseName'
		}, {
			title : '角色名',
			field : 'roleName'
		}, {
			title : '操作',
			field : 'id',
			width : 80,
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
		locale : 'zh-CN',// 中文支持
	})
	addValidator();
	$("#search_btn").click(function () {
       	$('#myTab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#searchManagerId").val('');
    	$("#searchRoleId").val('');
    	$('#myTab').bootstrapTable('refreshOptions',{pageNumber:1,limit:50});
    });
})


function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			addManagerId : {
				validators : {
					notEmpty : {
						message : '用户不能为空'
					},
					stringLength : {
						min : 1,
						max : 50,
						message : '角色长度必须在1到5位之间'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '必须是数字'
					}
				}
			},
			addRoleId : {
				validators : {
					notEmpty : {
						message : '角色不能为空'
					},
					stringLength : {
						min : 1,
						max : 5,
						message : '角色名称长度必须在1到5位之间'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '必须是数字'
					}
				}
			}
		}
	});
}

// 页面调整的大小
function tableHeight() {
	return $(window).height() - 30;
}
// 传递的参数
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,
		managerId:$("#searchManagerId").val(),
		roleId:$("#searchRoleId").val(),
	}
}

function actionFormatter(value, row, index) {
	var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + row.id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('" + row.id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    return result;
}
//根据ID修改角色关系
function EditViewById(id){

	if (passed('修改')) {
		$.post("findRoleRelationById.action", {
			id : id
		}, function(result) {
			$("#changeBody").modal();
			$('#editId').val(result.id);
			$('#editChineseName').val(result.chineseName);
			$('#editRoleId').selectpicker('val',result.roleId);
			

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

//根据ID删除角色关系
function DeleteByIds(id){	
	mark=true;
	if(passed('删除')){
		$('.popup_de .show_msg').text('确定要删除该角色关系吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display','inline-block');
		$('#btn_submit').one('click',function(){
			if(mark){
				$.post("deleteRoleRelation.action",
						{id:id},
						function(data){
							if(data.result>0){
								$('.popup_de .show_msg').text('删除成功！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function(){
									$('.popup_de').removeClass('bbox');
								})
								$('#myTab').bootstrapTable('refresh',{url:'findRolePageList.action'});
							}else{
								$('.popup_de .show_msg').text('删除失败！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function(){
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


function updateValidator() {
	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			userName : {
				editRoleId : {
					notEmpty : {
						message : '角色ID不能为空'
					},
					stringLength : {
						max : 5,
						message : '长度不能超5'
					},
				}
			}
		}
	});
}

$(function() {
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
	// 点击添加菜单页面返回按钮
	$('.add_backBtn').click(function() {
		$("#addForm")[0].reset();
		$("#addBody").modal('hide');
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#add_saveBtn").prop("disabled", false);
		$("#backupChannelId").selectpicker('refresh');
	});
	// 点击菜单页面提交按钮
	$('#add_saveBtn').click(function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				var _info = $('#addForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.post("addRoleRelation.action", data, function(data) {
						$("#addBody").modal("hide");
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						addValidator();
						$('#addForm')[0].reset();
						$("#backupChannelId").selectpicker('refresh');
						if (data.result > 0) {
							$('.popup_de .show_msg').text('添加成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$('#myTab').bootstrapTable('refresh', {
								url : 'findRolePageList.action'
							});
						} else {
							$('.popup_de .show_msg').text('添加失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$('#myTab').bootstrapTable('refresh', {
								url : 'findRolePageList.action'
							});
						}
						$("#addForm")[0].reset();
						$("#addBody").modal('hide');
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						addValidator();
						$("#backupChannelId").selectpicker('refresh');
					})
					mark=false;
				}
			})
		}
	});	

	
	$('#edit_saveBtn').click(function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#editForm').bootstrapValidator('validate');
		if ($("#editForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要修改吗？');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de .btn_submit').one('click',function() {
				$("#edit_saveBtn").attr("disabled", true);
				// 如果表单验证正确，则请求后台修改配置
				var _info = $('#editForm').serialize();
					data = decodeURIComponent(_info, true);
					if(mark){
						$.post("updateRoleRelation.action",
								data,
								function(data) {
							$("#changeBody").modal("hide");
							if (data.result > 0) {
								$('.popup_de .show_msg').text('修改成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#myTab').bootstrapTable('refresh',{url : 'findRolePageList.action'});
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
						}, "json");
						mark=false;
					}
			})
		}
	});
	
	
	// 点击更新角色关系返回按钮
	$('.edit_backBtn').click(function() {
		$("#editForm")[0].reset();
		$("#changeBody").modal('hide');
		$('#editForm').data('bootstrapValidator', null);
		$("#edit_saveBtn").prop("disabled", false);
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