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
		url : "findCustomer.action",// 请求后台url
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
		pageList : [ 5, 10, 20, 50 ],// 分页步进值
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
			valign : 'middle',
			visible : false
		}, {
			title : 'ID',
			field : 'id',
			visible : true
		}, {
			title : '公司全称',
			field : 'companyName',
			visible : true
		}, {
			title : '公司简称',
			field : 'companyKey',
			visible : true
		}, {
			title : '销售',
			field : 'sales',
			visible : true
		}, {
			title : '客服',
			field : 'saleAfter',
			visible : true
		}, {
			title : '类型',
			field : 'companyType',
			visible : true,
			formatter : function(value, row, index) {
				if (value == 'agent') {
					return "代理商";
				} else if (value == 'direct') {
					return "直客";
				}
			}
		}, {
			title : '关联帐户',
			field : 'count',
			width : 25,
			align : "center",
			valign : 'middle',
			formatter : function(value, row, index) {
				var result = "";
				result += "<a href='javascript:;' class='btn btn-xs blue' data-toggle='modal'  data-target='#linkAccount' onclick=\"linkAccount('" + row.id + "')\" >" + value + "</a>";
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
			companyName : {
				validators : {
					notEmpty : {
						message : '公司全称不能为空'
					},
                    stringLength :{
                 	   min:0,
                 	   max:50,
                 	   message:"公司全称长度不能超过50"
                    },
                    remote:{
                 	   url:"validatorCustomer.action",
                 	   type:"POST",
                 	   delay:500,
                 	   message:"公司名称已存在"
                    }
				}
			},
			companyKey : {
				validators : {
					notEmpty : {
						message : '公司简称不能为空'
					},
                    stringLength :{
                  	   min:0,
                  	   max:30,
                  	   message:"公司简称长度不能超过30"
                     },
                     remote:{
                   	   url:"validatorCustomer.action",
                   	   type:"POST",
                   	   delay:500,
                   	   message:"公司简称已存在"
                      }
				}
			},
			sales : {
				validators : {
					notEmpty : {
						message : '请选择销售'
					}
				}
			},
			saleAfter : {
				validators : {
					notEmpty : {
						message : '请选择客服'
					}
				}
			},
			companyType : {
				validators : {
					notEmpty : {
						message : '请选择客户类型'
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
			companyName : {
				validators : {
					notEmpty : {
						message : '公司全称不能为空'
					},                  
					stringLength :{
	                 	   min:0,
	                 	   max:50,
	                 	   message:"公司全称长度不能超过50"
                    },
                    callback:{
          			   message:'公司全称已存在',
                         callback:function(value, validator,$field){
                      	   var oldCompanyName = $('#oldEditCompanyName').val();
                      	   var companyName = $('#editCompanyName').val();
                      	   if(oldCompanyName==companyName){
                      			return true;
                      	   }else{
                      		   var flag=true;
                      		   $.ajax({
                      			  url:'validatorCustomer.action',
                      				data:{
                      					companyName:companyName,
                      					oldCompanyName:oldCompanyName
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
			companyKey : {
				validators : {
					notEmpty : {
						message : '客户简称不能为空'
					},
            		stringLength :{
		           	   min:0,
		           	   max:30,
		           	   message:"公司简称长度不能超过30"
            		},
            		callback:{
          			   message:'公司简称已存在',
                         callback:function(value, validator,$field){
                      	   var oldCompanyKey = $('#oldEditCompanyKey').val();
                      	   var companyKey = $('#editCompanyKey').val();
                      	   if(oldCompanyKey==companyKey){
                      			return true;
                      	   }else{
                      		   var flag=true;
                      		   $.ajax({
                      			  url:'validatorCustomer.action',
                      				data:{
                      					companyKey:companyKey,
                      					oldCompanyKey:oldCompanyKey
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
			sales : {
				validators : {
					notEmpty : {
						message : '请选择销售'
					}
				}
			},
			saleAfter : {
				validators : {
					notEmpty : {
						message : '请选择客服'
					}
				}
			},
			companyType : {
				validators : {
					notEmpty : {
						message : '请选择客户类型'
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
		$("#search_companykey").val('');
		$("#search_sales").val('');
		$("#search_saleAfter").val('');
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
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('"
			+ row.id
			+ "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
	return result;
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		companyKey : $("#search_companykey").val(),
		sales : $("#search_sales").val(),
		saleAfter : $("#search_saleAfter").val()
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
							$.post("updateCustomer.action", data, function(data) {
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
										url : 'findCustomer.action'
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
										url : 'findCustomer.action'
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
					$.post("addCustomer.action", data, function(data) {
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
								url : 'findCustomer.action'
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
								url : 'findCustomer.action'
							});
						}
						$("#add_saveBtn").attr("disabled", false);
					})
				} else {// 验证失败按钮可用
					$("#add_saveBtn").attr("disabled", false);
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
		$.post("findCustomerById.action", {
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
			$('#editCompanyName').val(result.companyName);
			$('#oldEditCompanyName').val(result.companyName);
			$('#editCompanyKey').val(result.companyKey);
			$('#oldEditCompanyKey').val(result.companyKey);
			$('#editSales').val(result.sales);
			$('#editSaleAfter').val(result.saleAfter);
			$('#editCompanyType').val(result.companyType);
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

function linkAccount(companyId){
//	$('#tLinkAccount').remove();
	var url = "findLinkAcc.action?id="+companyId;
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
     		title:'帐户名称',
     		field:'appName',
     		width: 200
     	},
     	{
     		title:'单价',
     		field:'price',
     		width: 200
     	},
     	{
     		title:'状态',
     		field:'appStatus',
     		width: 200,
			formatter : function(value, row, index) {
				if (value == 'normal') {
					return "正常";
				} else if (value == 'pause') {
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