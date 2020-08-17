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
		toolbar : '#toolbar',// 指定工作栏
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'ID',
			field : 'id',
			width : 60
		}, {
			title : '通道名称',
			field : 'channelId',
			formatter : channelName,
		}, {
			title : '通道名称',
			field : 'channelName',
			visible : false
		}, {
			title : '通道应用ID',
			field : 'channelAppId',
			formatter : changeContent,
		}, {
			title : '通道模板ID',
			field : 'channelTemplateId',
			formatter : changeContent,
		}, {
			title : '通道模板',
			field : 'channelTemplate',
		}, {
			title : '模板',
			field : 'template',
		}, {
			title : '参数',
			field : 'extras',
			formatter : changeContent,
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
	importValidator();
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#search_channelId").val(''),
		$("#search_channelAppId").val(''),
		$("#search_channelTemplateId").val(''),
		$("#search_channelTemplate").val(''),
		$("#search_extras").val(''),
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
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
			channelId : {
				validators : {
					notEmpty : {
						message : '通道不能为空'
					}
				}
			},
			channelAppId : {
				validators : {
					notEmpty : {
						message : '通道应用ID不能为空'
					},
					stringLength : {
						max : 80,
						message : '通道应用ID最大不能超过80位'
					}
				}
			},
			channelTemplateId : {
				validators : {
					notEmpty : {
						message : '通道模板ID不能为空'
					}
				},
				stringLength : {
					max : 80,
					message : '通道模板ID最大不能超过80位'
				}
			},
			channelTemplate : {
				validators : {
					notEmpty : {
						message : '通道模板不能为空'
					},
					stringLength : {
						max : 255,
						message : '通道模板最大不能超过255位'
					}
				}
			},
			template : {
				validators : {
					notEmpty : {
						message : '模板不能为空'
					},
					stringLength : {
						max : 255,
						message : '模板最大不能超过255位'
					}
				}
			},
			extras : {
				validators : {
					stringLength : {
						max : 255,
						message : '参数最大不能超过255位'
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
			channelId : {
				validators : {
					notEmpty : {
						message : '通道不能为空'
					}
				}
			},
			channelAppId : {
				validators : {
					notEmpty : {
						message : '通道应用ID不能为空'
					},
					stringLength : {
						max : 80,
						message : '通道应用ID最大不能超过80位'
					}
				}
			},
			channelTemplateId : {
				validators : {
					notEmpty : {
						message : '通道模板ID不能为空'
					}
				},
				stringLength : {
					max : 80,
					message : '通道模板ID最大不能超过80位'
				}
			},
			channelTemplate : {
				validators : {
					notEmpty : {
						message : '通道模板不能为空'
					},
					stringLength : {
						max : 255,
						message : '通道模板最大不能超过255位'
					}
				}
			},
			template : {
				validators : {
					notEmpty : {
						message : '模板不能为空'
					},
					stringLength : {
						max : 255,
						message : '模板最大不能超过255位'
					}
				}
			},
			extras : {
				validators : {
					stringLength : {
						max : 255,
						message : '参数最大不能超过255位'
					}
				}
			}
		}
	});

})


function importValidator() {
	$('#importForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			/*channelAppId : {
				validators : {
					notEmpty : {
						message : '请选择通道应用ID'
					}
				}
			},*/
			channelId : {
				validators : {
					notEmpty : {
						message : '请选择通道'
					}
				}
			},
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
		channelId : $("#search_channelId").val(),
		channelAppId : $("#search_channelAppId").val(),
		channelTemplateId : $("#search_channelTemplateId").val(),
		channelTemplate : $("#search_channelTemplate").val(),
		extras : $("#search_extras").val()
	}
}


function channelName(value, row, index) {
	var channel = "";
	channel = value + ":" + row.channelName;
		return channel;
}

function changeContent(value) {
	var val = value;
	if (val == "" || val == null || val == undefined) {
		return '-';
	}
	return val;
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
	result += "<button class='btn btn-primary btn-xs' id='update_btn' onclick=\"updateChannelTemplate('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"delChannelTemplate('"
			+ row.id + "')\" title='点击删除'>删除</button>";

	return result;
}


function delChannelTemplate(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deleteChannelTemplate.action", {
					'id' : id,
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
						url : 'findAllList.action'
					});
				}, "json");
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

function updateChannelTemplate(id) {
	if (passed('修改')) {
		$.post("findChannelTemplateById.action", {
			id : id
		}, function(result) {
			$('#editId').val(result.id);
			$("#editChannelId").selectpicker('val', result.channelId);
			$('#editChannelAppId').val(result.channelAppId);
			$('#editChannelTemplateId').val(result.channelTemplateId);
			$('#editChannelTemplate').val(result.channelTemplate);
			$('#editTemplate').val(result.template);
			$('#editExtras').val(result.extras);
			$("#changeBody").modal();
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


$(function() {
	
	$("#btn_add").click(function() {
		if (passed('添加')) {
			$("#addBody").modal();
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});

	// 点击添加页面返回按钮
	$('.add_backBtn').click(function() {
		$("#addBody").modal("hide");
		$("#addForm")[0].reset();
		$("#channelId").selectpicker('refresh');
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
				$.post("addChannelTemplate.action", data, function(data) {
					// 后台返回添加成功
					if (data.result == 1) {
						$("#addForm")[0].reset();
						$("#addBody").modal("hide");
						$("#channelId").selectpicker('refresh');
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						$("#add_saveBtn").attr("disabled", false);
						
						$('.popup_de .show_msg').text('添加成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
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
	
	//修改
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
						url : "updateChannelTemplate.action",
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
										url : 'findAllList.action'
									});
								})
							} else {
								$('.popup_de .show_msg').text('修改失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#mytab').bootstrapTable('refresh', {
										url : 'findAllList.action'
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

	
		// 修改页面返回按钮
		$('.edit_backBtn').click(function() {
			$("#changeBody").modal('hide');
			$("#editForm")[0].reset();
			$("#editForm").data('bootstrapValidator').destroy();
			$('#editForm').data('bootstrapValidator', null);
			$("#edit_saveBtn").attr("disabled", false);
		});
		
	
		// 批量删除
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
							$.post("delChannelTemplateBatch.action", {
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
		// 批量加黑按钮的出现与消失
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

		$("#import_btn").click(function(){
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
			$("#importChannelId").selectpicker('refresh');
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
							if(i==2){
								break;
							}
						}
						var sb="<tr>";
						for(var i=0;i<num;i++){
							sb+="<th><select name='column' data='"+i+"' id='column"+i+"'><option value=''>请选择列名</option><option value='channelAppId'>通道应用ID</option><option value='channelTemplate'>通道模板内容</option><option value='channelTemplateId'>通道模板ID</option><option value='extras'>参数</option></select></th>";
						}
						sb+="<th>剔除</th></tr>"
						$("#detailsTab").append(s);
						$("#headTab").append(sb);
						$("#hiddenChannelId").val(data.channelId);
						$("#hiddenIsExtras").val(data.isExtras);
						/*$("#hiddenChannelAppId").val(data.channelAppId);*/
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
			if(Object.keys(sp).length!=4){
				alert("请选择对应的通道应用ID、通道模板内容、通道模板ID、参数");
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
			var channelId=$("#hiddenChannelId").val();
			var isExtras=$("#hiddenIsExtras").val();
			/*var channelAppId=$("#hiddenChannelAppId").val();*/
			var file=$("#hiddenFile").val();
			$.ajax({
				url:"importData.action",
				type:"post",
				data:{
					check:check,
					sp:JSON.stringify(sp),
					channelId:channelId,
					isExtras:isExtras,
					/*channelAppId:channelAppId,*/
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

	
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	
})

$("#export_btn").click(function(){
		$("#exportBody").modal();
	});
	$("#export_saveBtn").click(function(){
		var param=new Array();
		var i=0;
		$("[name='param']:checked").each(function(){
			param.push($(this).val());
		});
		location.href="exportData.action?param="+encodeURI(JSON.stringify(param));
		
		
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

