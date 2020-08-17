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
		url : "findNumSegmentList.action",// 请求后台url
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
     		title:'全选',
     		field:'select',
     		checkbox:true,
     		width:25,
     		align:'center',
     		valign:'middle',
     	},{
			title : 'ID',
			field : 'id',
//			visible : false
		}, {
			title : '号段',
			field : 'seg'
		}, {
			title : '运营商',
			field : 'carrier',
			formatter : parseCarrier
		}, {
			title : '操作',
			field : 'id',
			width : 150,
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
	
	$("#search_btn").click(function () {
       	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#searchSeg").val('');
    	$("#searchCarrier").val('');
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1,limit:50});
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

//添加
$(function() {
	addValidator();
	updateValidator();
	
	$("#btn_add").click(
			function() {
				if (passed('新增')) {
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
					$.post("addNumSegment.action", data, function(data) {
						// 后台返回添加成功
						if (data.result > 0) {
							toastr.success("新增成功!")
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
						} else {
							$('.popup_de .show_msg').text('添加失败，请重新添加！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$("#add_saveBtn").attr("disabled", false);
								$('.popup_de').removeClass('bbox');
							})
						}
						$('#mytab').bootstrapTable('refresh', {
							url : 'findNumSegmentList.action'
						});
					})
				}
			});
	
	$('#edit_saveBtn').click(function(){
		mark=true;
		$('#editForm').bootstrapValidator('validate');
    	if($("#editForm").data('bootstrapValidator').isValid()){	
    		$("#edit_saveBtn").attr("disabled", true);

			$('.popup_de .show_msg').text('确定要修改吗？');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',
				function() {
					$("#edit_saveBtn").attr("disabled", false);
					// 如果表单验证正确，则请求后台修改配置
					var _info = $('#editForm').serialize();
						data = decodeURIComponent(_info, true);
						if(mark){
							$.post("updateNumSegment.action",
									data,
									function(data) {
								if(data.result>0){
									toastr.success("修改成功!");
									$('.changeBody').addClass('animated slideOutLeft');
									setTimeout(function(){
										$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
									},500)
									$('.tableBody').css('display','block').addClass('animated slideInRight'); 
									$('#mytab').bootstrapTable('refresh',{url:'findNumSegmentList.action'});
									$("#edit_saveBtn").attr("disabled",false);
									
									$('.popup_de').removeClass('bbox');
								}else{
									toastr.success("修改失败!");
									$('.changeBody').addClass('animated slideOutLeft');
									setTimeout(function(){
										$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
									},500)
									$('.tableBody').css('display','block').addClass('animated slideInRight'); 
									//刷新人员管理主页
									$('#mytab').bootstrapTable('refresh',{url:'findNumSegmentList.action'});
									//修改页面表单重置
									
									$('.popup_de').removeClass('bbox');
								}
							})
							mark=false;
						}
							})
	    	}
		});

	//修改页面回退按钮事件
	   $('#edit_backBtn').click(function(){
	    	$('.changeBody').addClass('animated slideOutLeft');
	    	setTimeout(function(){
				$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
			},500)
	    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
	    	$('#editForm').data('bootstrapValidator').resetForm(true);
	    })
	    
	    //批量删除
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
						$.post("delNumSegmentBatch.action", {
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
									url : 'findNumSegmentList.action'
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
									url : 'findNumSegmentList.action'
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
})

function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			seg : {
				validators : {
					notEmpty : {
						message : '号段不能为空'
					},
					remote : {
						url : "findNumSegmentByCondition.action",
						type : "POST",
						delay : 500,
						message : "号段已存在"
					},
					stringLength : {
						min : 3,
						max : 4,
						message : '号段长度为3-4位'
					},
					regexp : {
						regexp : /^1[0-9]*$/,
						message : '必须是数字,并且以1开头'
					}
				}
			},
			carrier : {
				validators : {
					notEmpty : {
						message : '运营商不能为空'
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
			carrier : {
				validators : {
					notEmpty : {
						message : '运营商不能为空'
					}
				}
			}
			
		}
	});
}

//修改
function updateNumSeg(id) {
	if (passed('修改')) {
		$.post("findNumSegmentById.action", {
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
			$('#editSeg').val(result.seg);
			$('#editCarrier').val(result.carrier);

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

//删除
function delNumSeg(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("deleteNumSegById.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						
					} else {
						$('.popup_de .show_msg').text('删除失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findNumSegmentList.action'
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


function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='update_numSeg' onclick=\"updateNumSeg('"
			+ row.id + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs' id='delete_numSeg' onclick=\"delNumSeg('"
			+ row.id + "')\" title='点击删除'>删除</button>";
	return result;
}

function tableHeight() {
	return $(window).height();
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,	// 页码
		seg:$("#searchSeg").val(),
		carrier:$("#searchCarrier").val()
	}
}

function parseCarrier(value) {
	var val = "";
	if(value=='cmcc'){
		val="移动";
	}else if(value=='unicom'){
		val="联通";
	}else if(value=='telecom'){
		val="电信";
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

/** 自定义toastr **/
$(function(){
    //参数设置，若用默认值可以省略以下面代码
    toastr.options = {
       "positionClass": "toast-bottom-full-width",//弹出窗的位置
    };
}); 
