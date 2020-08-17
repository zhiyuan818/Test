$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#checktab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})

	$('#checktab').bootstrapTable({
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
		}, {
			title : '审核词',
			field : 'keyWord',
		}, {
			title : '时间',
			field : 'createTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}, {
			title : '操作',
			field : 'id',
			width : 150,
			align : 'center',
			valign : 'middle',
			formatter : actionFormatter
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
	})
	$("#search_btn").click(function() {
		$('#checktab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#keyWord").val(''), $("#startTime").val(''),// 开始时间
		$("#endTime").val(''),// 结束时间
		$('#checktab').bootstrapTable('refreshOptions', {
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
			keyWord : {
				validators : {
					notEmpty : {
						message : '审核词不能为空'
					},
					stringLength : {
						max : 20,
						message : '审核词长度不能超过20个字'
					},
					remote : {
						url : "findByKeyWord.action",
						type : "POST",
						delay : 500,
						message : "审核词已存在"
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
			keyWord : {
				validators : {
					notEmpty : {
						message : '审核词不能为空'
					},
					stringLength : {
						max : 20,
						message : '审核词长度不能超过20个字'
					},
					callback:{
	         			   message:'审核词已存在',
	                        callback:function(value, validator,$field){
	                     	   var oldKeyWord = $('#oldEditKeyword').val();
	                     	   var keyWord = $('#editKeyword').val();
	                     	   if(oldKeyWord == keyWord){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'findByKeyWord.action',
	                     				data:{
	                     					oldKeyWord:oldKeyWord,
	                     					keyWord:keyWord
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

})


$(function() {
	// 添加
	$("#btn_add").click(
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
					$.post("addToCheckMust.action", data1, function(data) {
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
							$('#checktab').bootstrapTable('refresh', {
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
							$.post("updateCheckMust.action",data,
									function(data) {
								if (data.result == 1) {
									$('.changeBody').addClass('animated slideOutLeft');
									setTimeout(function() {
										$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
									},500);
									$('.tableBody').css('display','block').addClass('animated slideInRight');
									$('#editForm').data('bootstrapValidator').resetForm(true);
									$('#checktab').bootstrapTable('refresh',{url : 'findPageList.action'});
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
	
	$('#btn_del').click(function() {
		mark=true;
		if (passed('批量删除')) {
			var dataArr = $('#checktab').bootstrapTable('getSelections');
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
						$.post("delCheckMustBatch.action", {
							ids : ID
						}, function(data) {
							if (data.result == 1) {
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
								$('#checktab').bootstrapTable('refresh', {
									url : 'findPageList.action'
								});
							} else {
								$('.popup_de .show_msg').text('删除出错！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								})
								$('#checktab').bootstrapTable('refresh', {
									url : 'findPageList.action'
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
				var dataArr = $('#checktab .selected');
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
		var dataArr = $('#checktab .selected');
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

// 删除
function delCheckMust(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("delCheckMust.action", {
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
					$('#checktab').bootstrapTable('refresh', {
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

// 修改
function updateCheckMust(id) {
	if (passed('修改')) {
		$.post("findCheckMustById.action", {
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
			$('#editKeyword').val(result.keyWord);
			$('#oldEditKeyword').val(result.keyWord);

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
		keyWord : $("#keyWord").val(),// 敏感词
		startTime : $("#startTime").val(),
		endTime : $("#endTime").val()

	}
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
	result += "<button class='btn btn-primary btn-xs' id='update_btn' onclick=\"updateCheckMust('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"delCheckMust('"
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
