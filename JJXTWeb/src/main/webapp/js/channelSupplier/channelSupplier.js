$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#mytab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	});

	// 生成客户数据
	$('#mytab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findChannelSupplierList.action",// 请求后台url
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
			visible : true
		}, {
			title : '供应商全称',
			field : 'supplierName',
			visible : true
		}, {
			title : '供应商简称',
			field : 'supplierKey',
			visible : true
		}, {
			title : '商务',
			field : 'head',
			visible : true
		}, {
			title : '供应商类型',
			field : 'supplierType',
			visible : true,
			formatter : function(value, row, index) {
				if (value == 'agent') {
					return "代理商";
				} else if (value == 'direct') {
					return "直客";
				}
			}
		}, {
			title : '关联通道数',
			field : 'count',
			width : 25,
			align : "center",
			valign : 'middle',
			formatter : function(value, row, index) {
				var result = "";
				result += "<a href='javascript:;' class='btn btn-xs blue' data-toggle='modal'  data-target='#linkAccount' onclick=\"linkChannel('" + row.id + "')\" >" + value + "</a>";
				return result;
			}
		}, {
			title : '更新时间',
			field : 'updateTime',
			visible : true,
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
	});

	// 菜单添加表单验证
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			supplierName : {
				validators : {
					notEmpty : {
						message : '供应商全称不能为空'
					},
                    stringLength :{
                 	   min:0,
                 	   max:50,
                 	   message:"供应商全称长度不能超过50"
                    },
                    remote:{
                 	   url:"validatorSupplierName.action",
                 	   type:"POST",
                 	   delay:500,
                 	   message:"供应商名称已存在"
                    }
				}
			},
			supplierKey : {
				validators : {
					notEmpty : {
						message : '供应商简称不能为空'
					},
                    stringLength :{
                  	   min:0,
                  	   max:30,
                  	   message:"供应商简称长度不能超过30"
                     },
                     remote:{
                   	   url:"validatorSupplierKey.action",
                   	   type:"POST",
                   	   delay:500,
                   	   message:"供应商简称已存在"
                      }
				}
			},
			head : {
				validators : {
					notEmpty : {
						message : '请选择商务'
					}
				}
			},
			supplierType : {
				validators : {
					notEmpty : {
						message : '请选择供应商类型'
					}
				}
			}
		}
	});

	// 菜单编辑表单验证
	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			supplierName : {
				validators : {
					notEmpty : {
						message : '供应商全称不能为空'
					},                  
					stringLength :{
	                 	   min:0,
	                 	   max:50,
	                 	   message:"供应商全称长度不能超过50"
                    },
                    callback:{
          			   message:'供应商全称已存在',
                         callback:function(value, validator,$field){
                      	   var oldSupplierName = $('#oldEditSupplierName').val();
                      	   var supplierName = $('#editSupplierName').val();
                      	   if(oldSupplierName==supplierName){
                      			return true;
                      	   }else{
                      		   var flag=true;
                      		   $.ajax({
                      			  url:'validatorSupplierName.action',
                      				data:{
                      					supplierName:supplierName,
                      					oldSupplierName:oldSupplierName
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
			},
			supplierKey : {
				validators : {
					notEmpty : {
						message : '供应商简称不能为空'
					},
            		stringLength :{
		           	   min:0,
		           	   max:30,
		           	   message:"供应商简称长度不能超过30"
            		},
            		callback:{
          			   message:'供应商简称已存在',
                         callback:function(value, validator,$field){
                      	   var oldSupplierKey = $('#oldEditSupplierKey').val();
                      	   var supplierKey = $('#editSupplierKey').val();
                      	   if(oldSupplierKey==supplierKey){
                      			return true;
                      	   }else{
                      		   var flag=true;
                      		   $.ajax({
                      			  url:'validatorSupplierKey.action',
                      				data:{
                      					supplierKey:supplierKey,
                      					oldSupplierKey:oldSupplierKey
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
			},
			head : {
				validators : {
					notEmpty : {
						message : '请选择商务'
					}
				}
			},
			supplierType : {
				validators : {
					notEmpty : {
						message : '请选择供应商类型'
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
		$("#search_supplierKey").val('');
		$("#search_head").val('');
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 30,
			sort : '',
			order : 'asc'
		});
	});

});

function tableHeight() {
	return $(window).height() - 50;
}

function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + row.id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"delById('" + row.id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
	return result;
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		supplierKey : $("#search_supplierKey").val(),
		head : $("#search_head").val()
	}
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
	$('#edit_saveBtn').click(function() {
		mark=true;
				$('#editForm').bootstrapValidator('validate');
				if ($("#editForm").data('bootstrapValidator').isValid()) {
					$('.popup_de .show_msg').text('确定要修改吗？');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$("#edit_saveBtn").attr("disabled", true);
						var _info = $('#editForm').serialize();
						data = decodeURIComponent(_info, true);
						if(mark){
							$.post("updateChannelSupplier.action", data, function(data) {
								if (data.result > 0) {
									toastr.success("修改成功!");
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
										url : 'findChannelSupplierList.action'
									});
									// 修改页面表单重置
									$('#editForm').data('bootstrapValidator').resetForm(true);
									$('.popup_de').removeClass('bbox');
								} else {
									toastr.success("修改失败!");
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
										url : 'findChannelSupplierList.action'
									});
									// 修改页面表单重置
									$('#editForm').data('bootstrapValidator').resetForm(true);
									$('.popup_de').removeClass('bbox');
								}
								$("#edit_saveBtn").attr("disabled", false);
							})
							mark=false;
						}
					})

				}
			})

	// 新增客户按钮事件
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
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}
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
					$.post("addChannelSupplier.action", data, function(data) {
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
							$('#mytab').bootstrapTable('refresh', {
								url : 'findChannelSupplierList.action'
							});
						} else {
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
							$('#mytab').bootstrapTable('refresh', {
								url : 'findChannelSupplierList.action'
							});
						}
						$("#add_saveBtn").attr("disabled", false);
					})
				} else {// 验证失败按钮可用
					$("#add_saveBtn").attr("disabled", false);
				}
			});
	
	// 批量删除
	$('#btn_del').click(function() {
		mark=true;
		if (passed('批量删除')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要批量删除吗?');
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
						$.post("delChannelSupplierBatch.action", {
							ids : ID
						}, function(data) {
							if (data.result >= 1) {
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
									url : 'findChannelSupplierList.action'
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
									url : 'findChannelSupplierList.action'
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
	
});

/** 按钮权限* */
function passed(button) {
	flag = false;
	var buttons = $("#buttons").val();
	if (buttons == null) {
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

function EditViewById(id) {
	if (passed('修改')) {
		var data = "";
		$.post("findChannelSupplierById.action", {
			id : id
		}, function(result) {
			data = result;
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css(
						'display', 'none');
			}, 500)
			$('.changeBody').css('display', 'block');
			$('.changeBody').addClass('animated slideInRight');
			$('#editId').val(result.id);
			$('#editSupplierName').val(result.supplierName);
			$('#oldEditSupplierName').val(result.supplierName);
			$('#editSupplierKey').val(result.supplierKey);
			$('#oldEditSupplierKey').val(result.supplierKey);
			$('#editHead').val(result.head);
			$('#editSupplierType').val(result.supplierType);
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


function delById(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("delChannelSupplierById.action", {
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
					$('#mytab').bootstrapTable('refresh', {
						url : 'findChannelSupplierList.action'
					});
				}, "json");
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

/** 格式化时间* */
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

function linkChannel(supplierId){
//	$('#tLinkAccount').remove();
	var url = "findLinkChannel.action?id="+supplierId;
	$('#tLinkAccount').bootstrapTable('refresh', {
		url : url
	});
}

$('#tLinkAccount').bootstrapTable({
	method: 'get',
	url:'',//请求后台url
	height:tableHeight(),//高度调整
	dataType:"json",//返回的数据格式
	cache:false,//是否缓存 默认是true
	async:false,
	columns:[
		{
     		title:'通道名称',
     		field:'channelId',
     		width: 200,
     		formatter : function(value,row,index){
				return value+":"+row.channelName;
			}
     	},
     	{
     		title:'单价',
     		field:'channelPrice',
     		width: 200
     	},
     	{
     		title:'状态',
     		field:'channelStatus',
     		width: 200,
			formatter : function(value, row, index) {
				if (value == 'normal') {
					return "正常";
				} else if (value == 'paused') {
					return "暂停";
				} else if(value == 'deleted'){
					return "删除";
				}
			}
     	}
	],
	locale:'zh-CN'//中文支持,
});

/** 自定义toastr **/
$(function(){
    //参数设置，若用默认值可以省略以下面代码
    toastr.options = {
       "positionClass": "toast-bottom-full-width",//弹出窗的位置
    };
});