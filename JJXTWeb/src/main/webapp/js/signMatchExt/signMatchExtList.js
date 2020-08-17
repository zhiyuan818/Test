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
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : 'ID',
			field : 'id',
			visible : false
		}, {
			title : '账号',
			field : 'appId',
			formatter : appName,
		}, {
			title : '签名',
			field : 'sign',
		}, {
			title : '扩展号码',
			field : 'ext',
		}, {
			title : '时间',
			field : 'addTime',
			formatter : changeDateFormat
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
		$("#search_sign").val(''), 
		$("#search_ext").val(''),
		$("#search_extLength").val(''),
		$("#search_appId").val(''),
		$("#startTime").val(''),
		$("#endTime").val(''),
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
	addValidator();
	importValidator();
	$("#add_btn").click(function() {
		if (passed("新增")) {
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
		$("#addAppId").selectpicker('refresh');
		$("#add_saveBtn").attr("disabled", false);
		$("#addBody").modal("hide");
	});
	
	$("#add_saveBtn").click(function() {
		mark=true;
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.btn_submit').one('click',function() {
				$("#add_saveBtn").attr("disabled", true);
				var sign = $("#addSign").val();
				var extLength = $("#addExtLength").val();
				var appId = $("#addAppId").val();
				if(mark){
					$.ajax({
						url :"addSignMatchExt.action",
						type:"post",
						data:{
							sign : sign,
							extLength : extLength,
							appId : appId
						},
						success:function(obj) {
							if (obj.result == 1) {
								$('.popup_de .show_msg').text('添加成功');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
							} else if (obj.result == 0) {
								$('.popup_de .show_msg').text('重复添加');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
							} else if (obj.result == -1) {
								$('.popup_de .show_msg').text('查询扩展出错');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
							} else if (obj.result == -2) {
								$('.popup_de .show_msg').text('扩展与扩展位数不一致');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
							} else if (obj.result == -3) {
								$('.popup_de .show_msg').text('扩展位已满');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
							} else if (obj.result == -4) {
								$('.popup_de .show_msg').text('添加失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url : 'findAllList.action'});
							} 
							$("#add_saveBtn").attr("disabled",false);
							$("#addForm")[0].reset();
							$("#addAppId").selectpicker('refresh');
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$("#addBody").modal("hide");
						}, dataType:"json"})
					mark=false;
				}
				})
			}
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
			sign : {
				validators : {
					notEmpty : {
						message : '签名不能为空'
					}
				}
			},
			appId : {
				validators : {
					notEmpty : {
						message : '请选择账号'
					}
				}
			},
			extLength : {
				validators : {
					notEmpty : {
						message : '扩展长度不能为空'
					},
					regexp: {
	    				regexp: /^[2-6]$/,
	    				message: '必须为数字，为2-6之间' 
		    		},
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
		sign : $("#search_sign").val(), 
		ext : $("#search_ext").val(),
		extLength : $("#search_extLength").val(),
		appId : $("#search_appId").val(),
		startTime : $("#startTime").val(),
		endTime : $("#endTime").val()
	}
}


function appName(value, row, index) {
	var channel = "";
	channel = value + ":" + row.appName;
	return channel;
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
	var status = row.baobeiFlag;
	var result = "<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"delSign('"
			+ id + "')\" title='点击删除'>删除</button>&nbsp;&nbsp;";
	return result;
}
function importValidator() {
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
						message : '请选择账号'
					}
				}
			},
			extLength : {
				validators : {
					notEmpty : {
						message : '扩展长度不能为空'
					},
					regexp: {
	    				regexp: /^[2-6]$/,
	    				message: '必须为数字，为2-6之间' 
		    		},
				}
			},
			uploadFile : {
				validators : {
					notEmpty : {
						message : '请选择txt格式文件'
					},
					regexp : {
						regexp : /\.txt$/,
						message : '选择正确的txt文件'
					}
				}
			}
		}
	});
}

function exportValidator() {
	$('#exportForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appId : {
				validators : {
					notEmpty : {
						message : '请选择账号'
					}
				}
			}
		}
	});
}
function delSign(id){
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("delSign.action", {
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

$(function() {
	
	$("#import_btn").click(function() {
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
		$("#importAppId").selectpicker('refresh');
		$("#importExtLength").val('');
		$("#importBody").modal('hide');
		$("#import_saveBtn").attr("disabled", false);
		$("#importForm").data('bootstrapValidator').destroy();
		$('#importForm').data('bootstrapValidator', null);
	});
	
	$("#import_saveBtn").click(function(){
		var uploadFile=$("#uploadFile").val();
		if(uploadFile =="" ||uploadFile ==null ||uploadFile==undefined){
			alert("请选择txt文件");
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
					$('.popup_de .show_msg').text('成功导入签名'+data.result+"条");
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					$("#importBody").modal('hide');
					$("#import_saveBtn").attr("disabled", false);
					$("#importForm").data('bootstrapValidator').destroy();
					$('#importForm').data('bootstrapValidator', null);
					importValidator();
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAllList.action'
					});
				}
				
			});
		}
	});
	
	$("#export_btn").click(function() {
		if(passed('批量导出')){
			$("#exportBody").modal();
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
	$('.export_backBtn').click(function() {
		$("#exportForm")[0].reset();
		$("#exportAppId").selectpicker('refresh');
		$("#exportExtLength").val('');
		$("#exportBody").modal('hide');
		$("#export_saveBtn").attr("disabled", false);
		$("#exportForm").data('bootstrapValidator').destroy();
		$('#exportForm').data('bootstrapValidator', null);
	});
	
	// 点击下一步按钮
	$('#export_saveBtn').click(function() {
		// 点击保存时触发表单验证
		$('#exportForm').bootstrapValidator('validate');
		// 如果表单验证正确
		if ($("#exportForm").data('bootstrapValidator').isValid()) {
			$("#export_saveBtn").attr("disabled", true);
			var appId = $("#exportAppId").val();
			var extLength = $("#exportExtLength").val();
			location.href="exportData.action?appId="+appId+"&extLength="+extLength;
			$("#exportForm")[0].reset();
			$("#exportAppId").selectpicker('refresh');
			$("#exportExtLength").val('');
			$("#exportBody").modal('hide');
			$("#export_saveBtn").attr("disabled", false);
			$("#exportForm").data('bootstrapValidator').destroy();
			$('#exportForm').data('bootstrapValidator', null);
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
	$("#startTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('hide', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var startTime = event.date;
		$('#endTime').datetimepicker('setStartDate', startTime);
		$('#endTime').val("");
	});
	$("#endTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('hide', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var endTime = event.date;
		$('#startTime').datetimepicker('setEndDate', endTime);
	});

})


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

