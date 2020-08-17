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
		url : "findChannelPageList.action",
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
			field : 'channelId'
		}, {
			title : '通道名称',
			field : 'name'
		}, {
			title : '供应商',
			field : 'supplierKey'
		}, {
			title : '移动',
			field : 'toCmcc',
			formatter : isSupport
		}, {
			title : '联通',
			field : 'toUnicom',
			formatter : isSupport
		}, {
			title : '电信',
			field : 'toTelecom',
			formatter : isSupport
		}, {
			title : '回执/上行/状态/平台/双平台',
			field : 'productStatus',
			formatter : changeResult,
		}, {
			title : '价格',
			field : 'channelPrice',
		}, {
			title : '连接数',
			field : 'linkMax'
		}, {
			title : '速度',
			field : 'linkSpeed'
		}, {
			title : '通道类',
			field : 'variant'
		}, {
			title : '模块线程',
			field : 'model'
		}, {
			title : 'logic线程',
			field : 'logicModel'
		}, {
			title : '产品数',
			field : 'productNumber',
//			width : 40,
			align : "center",
			valign : 'middle',
			formatter : function(value, row, index) {
				var result = "";
				result += "<a href='javascript:;' class='btn btn-xs blue' data-toggle='modal'  data-target='#linkProduct' onclick=\"linkProduct('" + row.channelId + "')\" >" + value + "</a>";
				return result;
			}
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
	updateBasisValidator();
	updateHightLevelValidator();
	
	$("#search_btn").click(function () {
       	$('#myTab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#searchSupplierId").val('');
    	$("#searchHead").val('');
    	$("#searchChannelId").val('');
    	$("#searchChannelStatus").val('');
    	$("#searchAccount").val('');
    	$("#searchSvcAddr").val('');
    	$("#searchServiceCode").val('');
    	$("#searchEnterpriseCode").val('');
    	$("#searchVariant").val('');
    	$("#searchPlatformFlag").val('');
    	$("#searchAllPlatformUsed").val('');
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
    	$('#myTab').bootstrapTable('refreshOptions',{pageNumber:1,limit:50});
    });
})

function isSupport(value, row, index) {
	if (value == "no") {
		return "<font color='red'>不支持</font>";
	}
	return "支持";
}
function changeResult(value, row, index) {
	var result = "";
	result += row.haveReport == "yes" ? '有' : '无';
	result += "&nbsp;/&nbsp;";
	result += row.haveMo == "yes" ? '有' : "<font color='#f00'>无</font>";
	result += "&nbsp;/&nbsp;";
	result += row.channelStatus == 'normal' ? "正常"
			: row.channelStatus == 'paused' ? "<font color='#f00'>暂停</font>"
					: "<font color='#f00'>删除</font>";
	result += "&nbsp;/&nbsp;";
	result += row.platformFlag;
	result += "&nbsp;/&nbsp;";
	result += row.allPlatformUsed == "yes" ? '支持' : "<font color='#f00'>不支持</font>";
	return result;
}
function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '通道名称不能为空'
					},
					remote : {
						url : "validatorChannel.action",
						type : "POST",
						delay : 500,
						message : "通道名称已存在"
					},
					stringLength : {
						min : 4,
						max : 50,
						message : '通道名称长度必须在4到50位之间'
					}
				}
			},
			sourceSegment : {
				validators : {
					stringLength : {
						max : 20,
						message : '源号码段长度必须小于20位'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '必须是数字'
					}
				}
			},
			sizeMax : {
				validators : {
					notEmpty : {
						message : '短信最大字符不能为空'
					},
					regexp : {
						regexp : /^(\d|[1-9]\d|[1-9]\d{2}|1000)$/,
						message : '短信最大字符小于等于1000'
					}
				}
			},
			sizeFirst : {
				validators : {
					notEmpty : {
						message : '短信计费条数不能为空'
					},
					regexp : {
						regexp : /^(\d*)$/,
						message : '必须是数字'
					},stringLength : {
						max : 10,
						message : '最大长度为10位'
					}
				}
			},
			sizeCharge : {
				validators : {
					notEmpty : {
						message : '长短信计费条数不能为空'
					},
					regexp : {
						regexp : /^(\d*)$/,
						message : '必须是数字'
					},stringLength : {
						max : 10,
						message : '最大长度为10位'
					}
				}
			},
			supplierId: {
 	    	   validators: {
                    notEmpty: {
                        message:'请选择供应商'
                    }
                }
            },
            redisFlag:{
         	   validators: {
         		   notEmpty:{
         			   message:'不能为空'
         		   },
         		   regexp: {
         			   regexp: /^([1-9])$/,
         			   message: '范围在1-9之间'
         		   }
         	   }
            }
		}
	});
}
function updateBasisValidator() {
	// 更新表单验证
	$('#editBasisForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '通道名称不能为空'
					},
					stringLength : {
						min : 4,
						max : 50,
						message : '通道名称长度必须在4到50位之间'
					},callback:{
	         			   message:'通道名称已存在',
	                        callback:function(value, validator,$field){
	                     	   var oldName = $('#oldEditName').val();
	                     	   var name = $('#editName').val();
	                     	   if(oldName==name){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'validatorChannel.action',
	                     				data:{
	                     					oldName:oldName,
	                     					name:name
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
			submitBegin : {
				validators : {
					notEmpty : {
						message : '通道起始时间不能为空'
					}
				}
			},
			submitEnd : {
				validators : {
					notEmpty : {
						message : '通道结束时间不能为空'
					}
				}
			},
			channelPrice : {
				validators : {
					notEmpty : {
						message : '通道价格不能为空'
					},regexp : {
						regexp : /^\d+(?:\.\d{0,2})?$/,
						message : '价格不正确'
					}
				}
			}
		}
	});
}
function updateHightLevelValidator() {
	// 更新表单验证
	$('#editHighLevelForm')
			.bootstrapValidator(
					{
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							sourceSegment : {
								validators : {
									stringLength : {
										max : 20,
										message : '源号码段长度必须小于20位'
									},
									regexp : {
										regexp : /^[0-9]*$/,
										message : '必须是数字'
									}
								}
							},
							svcAddr : {
								validators : {
									notEmpty : {
										message : '网关网址不能为空'
									}
								}
							},
							svcPort : {
								validators : {
									notEmpty : {
										message : '网关端口不能为空'
									},
									regexp : {
										regexp : /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
										message : '必须正确的端口'
									}
								}
							},
							account : {
								validators : {
									notEmpty : {
										message : '网关账号不能为空'
									},stringLength : {
										max : 60,
										message : '最大长度为60位'
									}
								}
							},
							password : {
								validators : {
									notEmpty : {
										message : '网关密码不能为空'
									},stringLength : {
										max : 200,
										message : '最大长度为200位'
									}
								}
							},
							sizeMax : {
								validators : {
									notEmpty : {
										message : '短信最大字符不能为空'
									},
									regexp : {
										regexp : /^(\d|[1-9]\d|[1-9]\d{2}|1000)$/,
										message : '短信最大字符小于等于1000'
									}
								}
							},
							sizeFirst : {
								validators : {
									notEmpty : {
										message : '短信计费条数不能为空'
									},
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										max : 10,
										message : '最大长度为10位'
									}
								}
							},
							sizeCharge : {
								validators : {
									notEmpty : {
										message : '长短信计费条数不能为空'
									},
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										max : 10,
										message : '最大长度为10位'
									}
								}
							},
							baobeiBeforePjExtSrc : {
								validators : {
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										min : 0,
										max : 255,
										message : '最大长度为255'
									}
								}
							},
							callOut : {
								validators : {
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										max : 30,
										message : '最大长度为30位'
									}
								}
							},
							linkMax : {
								validators : {
									notEmpty : {
										message : '最大连接数不能为空'
									},
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										min : 0,
										max : 4,
										message : '最大连接数长度必须在0到4位之间'
									}
								}
							},
							linkSpeed : {
								validators : {
									notEmpty : {
										message : '下发速度不能为空'
									},
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										min : 0,
										max : 4,
										message : '下发速度长度必须在0到4位之间'
									}
								}
							},
							serviceCode : {
								validators : {
									stringLength : {
										min : 0,
										max : 30,
										message : '服务代码长度必须在0到30位之间'
									},regexp : {
										regexp :  /^[0-9a-zA-Z]*$/,
										message : '必须是数字、字母、数字字母'
									}
								}
							},
							enterpriseCode : {
								validators : {
									stringLength : {
										min : 0,
										max : 30,
										message : '企业代码长度必须在0到30位之间'
									},regexp : {
										regexp :  /^[0-9a-zA-Z]*$/,
										message : '必须是数字、字母、数字字母'
									}
								}
							},
							shortNum : {
								validators : {
									regexp : {
										regexp : /^(\d*)$/,
										message : '必须是数字'
									},stringLength : {
										min : 0,
										max : 10,
										message : '短号码长度必须在0到10位之间'
									}
								}
							},
							variant : {
								validators : {
									notEmpty : {
										message : '通道类不能为空'
									},regexp : {
//										regexp :  /^[0-9a-zA-Z:,]*$/,
										regexp :  /^[0-9a-zA-Z_]{1,}$/,
										message : '必须是数字、字母、数字字母、下划线'
									},stringLength : {
										min : 0,
										max : 30,
										message : '通道类长度必须在0到30位之间'
									}
								}
							},
							alarmCode : {
								validators : {
									regexp : {
										regexp :  /^[0-9a-zA-Z:,]*$/,
										message : '必须是数字、字母、数字字母'
									},stringLength : {
										min : 0,
										max : 100,
										message : '报警码长度必须在0到100位之间'
									}
								}
							},
							submitAlarmCode : {
								validators : {
									stringLength : {
										min : 0,
										max : 100,
										message : '报警码长度必须在0到100位之间'
									}
								}
							},
							channelPrice : {
								validators : {
									regexp : {
										regexp : /^[0-9]{1}\d*(\.\d{1,2})?$/,
										message : '小数点后保留两位'
									}
								}
							},redisFlag:{
				         	   validators: {
				        		   notEmpty:{
				        			   message:'不能为空'
				        		   },
				        		   regexp: {
				        			   regexp: /^([1-9])$/,
				        			   message: '范围在1-9之间'
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
		supplierId:$("#searchSupplierId").val(),
		head:$("#searchHead").val(),
		channelId:$("#searchChannelId").val(),
		channelStatus:$("#searchChannelStatus").val(),
		account:$("#searchAccount").val(),
		svcAddr:$("#searchSvcAddr").val(),
		serviceCode:$("#searchServiceCode").val(),
		enterpriseCode:$("#searchEnterpriseCode").val(),
		variant:$("#searchVariant").val(),
		platformFlag:$("#searchPlatformFlag").val(),
		allPlatformUsed:$("#searchAllPlatformUsed").val(),
	}
}
function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<a href='javascript:;'  class='btn btn-xs blue' data-toggle='modal'  data-target='#batchSwitch' onclick=\"batchSwitchs('"
		+ row.channelId + "')\" title='切换'><span class='glyphicon glyphicon-share-alt'></span></a>";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"updateProduct('"
			+ row.id + "')\" title='修改'><span class='glyphicon glyphicon-pencil'></span></a>";
	return result;
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
		$("#supplierId").selectpicker('refresh');
	});
	// 点击菜单页面提交按钮
	$('#add_saveBtn').click(function() {
		mark=true;
		// 点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		var toCmcc=$("#toCmcc").parent().hasClass("active");
		var toUnicom=$("#toUnicom").parent().hasClass("active");
		var toTelecom=$("#toTelecom").parent().hasClass("active");
		if(toCmcc || toUnicom || toTelecom){
		}else{
			$('.popup_de .show_msg').text('请选择一个运营商');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				var _info = $('#addForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.post("addChannel.action", data, function(data) {
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
								url : 'findChannelPageList.action'
							});
						} else {
							$('.popup_de .show_msg').text('添加失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$('#myTab').bootstrapTable('refresh', {
								url : 'findChannelPageList.action'
							});
						}
						$("#addForm")[0].reset();
						$("#addBody").modal('hide');
						$("#addForm").data('bootstrapValidator').destroy();
						$('#addForm').data('bootstrapValidator', null);
						addValidator();
						$("#backupChannelId").selectpicker('refresh');
						$("#supplierId").selectpicker('refresh');
					})
					mark=false;
				}
			})
		}
	});
	$('.edit_backBtn').click(function() {
		$("#changeBody").modal('hide');
		$("#editBasisForm")[0].reset();
		$("#editHighLevelForm")[0].reset();
		$("#editBasisForm input[type='checkbox']").prop("checked",false);
		$("#editBasisForm").data('bootstrapValidator').destroy();
		$('#editBasisForm').data('bootstrapValidator', null);
		$("#editHighLevelForm").data('bootstrapValidator').destroy();
		$('#editHighLevelForm').data('bootstrapValidator', null);
		updateBasisValidator();
		updateHightLevelValidator();
		$("#editBasisForm label").removeClass("active");
		$("#editHighLevelForm label").removeClass("active");
		$("#editBackupChannelId").selectpicker('refresh');
		$("#editSupplierId").selectpicker('refresh');
	})
	$('#edit_basisBtn').click(function() {
		mark=true;
		$('#editBasisForm').bootstrapValidator('validate');
		if ($("#editBasisForm").data('bootstrapValidator').isValid()) {
			var result="";
			var channelId=$("#editBasisChannelId").val();
			var toCmcc=$("#editToCmcc").parent().hasClass("active");
			var toUnicom=$("#editToUnicom").parent().hasClass("active");
			var toTelecom=$("#editToTelecom").parent().hasClass("active");
			if(toCmcc || toUnicom || toTelecom){
			}else{
				$('.popup_de .show_msg').text('请选择一个运营商');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}
			var channelStatus=$("#editBasisForm [name='channelStatus']:checked").val();
			if(channelStatus=='deleted'){
				$.ajax({
					url:"channelIsUse.action",
					data:{id:channelId},
					type:"post",
					async:false,
					success:function(obj){
						result=obj;
					}
				});
			}
			if(result==3){
				$('.popup_de .show_msg').text('产品、短信模板正在使用，确定要保存已填写的内容吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
			}else if(result==2){
				$('.popup_de .show_msg').text('产品正在使用，确定要保存已填写的内容吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
			}else if(result==1){
				$('.popup_de .show_msg').text('短信模板正在使用，确定要保存已填写的内容吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
			}else{
				$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
			}
			$('.btn_submit').one('click', function() {
				var _info = $('#editBasisForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.post("updateChannelBasis.action", data, function(data) {
						if (data.result > 0) {
							$('.popup_de .show_msg').text('修改成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#myTab').bootstrapTable('refresh', {
									url : 'findChannelPageList.action'
								});
							})
						} else {
							$('.popup_de .show_msg').text('修改失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#myTab').bootstrapTable('refresh', {
									url : 'findChannelPageList.action'
								});
							})
						}
						$("#changeBody").modal('hide');
						$("#editBasisForm")[0].reset();
						$("#editBasisForm").data('bootstrapValidator').destroy();
						$('#editBasisForm').data('bootstrapValidator', null);
						updateBasisValidator();
					})
					mark=false;
				}
			})
		}
	})
	
	$('#edit_highLevelBtn').click(function() {
		var editVariant = $("#editVariant").val();
		if(editVariant==null || editVariant=='' || editVariant == undefined){
				$('.popup_de .show_msg').text('通道类未选择');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				return;
		}
		
		var extras=$("#editExtras").val();
		var flag=true;
		var map=new Map();
		var extrass=[];
		if(extras==null || extras=='' || extras == undefined){
		}else{
			extrass=extras.split("&");
			for(var i in extrass){
				var extrasss=extrass[i].split("=");
				map.set(extrasss[0],extrasss[0]);
				if(extrasss.length!=2){
					flag=false;
					break;
				}
			}
		}
		if(map.size != parseInt(extrass.length)){
			$('.popup_de .show_msg').text('通道特定参数重复');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		if(!flag){
			$('.popup_de .show_msg').text('通道特定参数格式不正确！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		var sum=new Number(0);
		var mark=false;
		$("#model_model input[type='number']").map(function(){
			sum=Number(sum)+Number($(this).val());
			if(parseInt($(this).val())<=0){
				mark=true;
			}
		});
		if(mark){
			$('.popup_de .show_msg').text('模块线程数不能小于等于0');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return;
		}
		mark=false;
		$("#logic_model input[type='number']").map(function(){
			if(parseInt($(this).val())<=0){
				mark=true;
			}
		});
		if(mark){
			$('.popup_de .show_msg').text('logic模块线程数不能小于等于0');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return;
		}
		
		var linkMax=$("#editLinkMax").val();
		if(parseInt(sum) > parseInt(linkMax)){
			$('.popup_de .show_msg').text('线程数不能大于最大连接数！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		sign=true;
		$('#editHighLevelForm').bootstrapValidator('validate');
		if ($("#editHighLevelForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				//------计算model
				var model="";
				$("#model_model input[type='number']").map(function(){
					if(parseInt($(this).val())>0){
						model+=$(this).attr("data")+":"+$(this).val()+",";
					}
				});
				var logic="";
				$("#logic_model input[type='number']").map(function(){
					if(parseInt($(this).val())>0){
						logic+=$(this).attr("data")+":"+$(this).val()+",";
					}
				});
				if(model == null || model == '' || model == undefined){
					
				}else{
					model=model.substring(0,model.length-1);
				}
				if(logic == null || logic == '' || logic == undefined){
					
				}else{
					logic=logic.substring(0,logic.length-1);
				}
				var id=$("#editHighLevelId").val();
				var haveReport=$("#editHighLevelForm input[name='haveReport']:checked").val();
				var haveMo=$("#editHighLevelForm input[name='haveMo']:checked").val();
				var moMatch=$("#editHighLevelForm input[name='moMatch']:checked").val();
				var userExtSrc=$("#editHighLevelForm input[name='userExtSrc']:checked").val();
				var autoExtSrc=$("#editHighLevelForm input[name='autoExtSrc']:checked").val();
				var autoExtractSigns=$("#editHighLevelForm input[name='autoExtractSigns']:checked").val();
				var sourceType=$("#editHighLevelForm input[name='sourceType']:checked").val();
				var toIntl=$("#editHighLevelForm input[name='toIntl']:checked").val();
				var filterGlobalBlack=$("#editHighLevelForm input[name='filterGlobalBlack']:checked").val();
				var gwType=$("#editHighLevelForm input[name='gwType']:checked").val();
				var baobeiModel=$("#editHighLevelForm input[name='baobeiModel']:checked").val();
				var msgidType=$("#editHighLevelForm input[name='msgidType']:checked").val();
				var sourceSegment=$("#editSourceSegment").val();
				var sizeMax=$("#editSizeMax").val();
				var sizeFirst=$("#editSizeFirst").val();
				var sizeCharge=$("#editSizeCharge").val();
				var baobeiBeforePjExtSrc=$("#editBaobeiBeforePjExtSrc").val();
				var callOut=$("#editCallOut").val();
				var sourceFullLength=$("#eidtSourceFullLength").val();
				var successRate=$("#editSuccessRate").val();
				var svcAddr=$("#editSvcAddr").val();
				var svcPort=$("#editSvcPort").val();
				var account=$("#editAccount").val();
				var password=$("#editPassword").val();
				var linkMax=$("#editLinkMax").val();
				var linkSpeed=$("#editLinkSpeed").val();
				var firstMsgChargeLen=$("#editFirstMsgChargeLen").val();
				var subseqMsgChargeLen=$("#editSubseqMsgChargeLen").val();
				var serviceCode=$("#editServiceCode").val();
				var enterpriseCode=$("#editEnterpriseCode").val();
				var extras=$("#editExtras").val();
				var shortNum=$("#editShortNum").val();
				var variant=$("#editVariant").val();
				var channelPrice=$("#editChannelPrice").val();
				var alarmCode=$("#editAlarmCode").val();
				var submitAlarmCode=$("#editSubmitAlarmCode").val();
				var redisFlag = $("#editRedisFlag").val();
				if(sign){
					$.ajax({url:"updateChannelLevel.action" ,data:{
						id:id,
						haveReport:haveReport, 
						haveMo:haveMo,
						moMatch:moMatch,
						userExtSrc:userExtSrc,
						autoExtSrc:autoExtSrc,
						autoExtractSigns:autoExtractSigns,
						sourceType:sourceType,
						toIntl:toIntl,
						filterGlobalBlack:filterGlobalBlack,
						gwType:gwType,
						baobeiModel:baobeiModel,
						sourceSegment:sourceSegment,
						msgidType:msgidType,
						sizeMax:sizeMax,
						sizeFirst:sizeFirst,
						sizeCharge:sizeCharge,
						baobeiBeforePjExtSrc:baobeiBeforePjExtSrc,
						callOut:callOut,
						sourceFullLength:sourceFullLength,
						successRate:successRate,
						svcAddr:svcAddr,
						svcPort:svcPort,
						account:account,
						password:password,
						linkMax:linkMax,
						linkSpeed:linkSpeed,
						firstMsgChargeLen:firstMsgChargeLen,
						subseqMsgChargeLen:subseqMsgChargeLen,
						serviceCode:serviceCode,
						enterpriseCode:enterpriseCode,
						extras:extras,
						shortNum:shortNum,
						variant:variant,
						channelPrice:channelPrice,
						alarmCode:alarmCode,
						submitAlarmCode:submitAlarmCode,
						model:model,
						logicModel:logic,
						redisFlag:redisFlag
						
					},type:"post",success: function(data) {
						if (data.result > 0) {
							$('.popup_de .show_msg').text('修改成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#myTab').bootstrapTable('refresh', {
									url : 'findChannelPageList.action'
								});
							})
						} else {
							$('.popup_de .show_msg').text('修改失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#myTab').bootstrapTable('refresh', {
									url : 'findChannelPageList.action'
								});
							})
						}
						$("#changeBody").modal('hide');
						$("#editHighLevelForm")[0].reset();
						$("#editHighLevelForm").data('bootstrapValidator').destroy();
						$('#editHighLevelForm').data('bootstrapValidator', null);
						updateHightLevelValidator();
						$("#editHighLevelForm label").removeClass("active");
						$("#editBackupChannelId").selectpicker('refresh');
						$("#editSupplierId").selectpicker('refresh');
					}})
					sign=false;
				}
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
	$("#hightLevel").click(function() {
		if (passed('技术参数')) {
			$(this).attr("href", "#B");
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$(this).parent().removeClass("active");
				$('.popup_de').removeClass('bbox');
			})
		}
	});
})
function updateVariant() {
	$("#editVariant").removeAttr("disabled");
	$("#div_variant").html("<input type='button' value='保存通道类'  onclick='saveVariant()'>");
	$("#editVariant").selectpicker('refresh');
}
function saveVariant() {
	$("#editVariant").attr("disabled","disabled");
	$("#div_variant").html("<input type='button' value='修改通道类'  onclick='updateVariant()'>");
	$("#editVariant").selectpicker('refresh');
}

function updateRedisFlag() {
	$("#editRedisFlag").removeAttr("disabled");
	$("#div_redisFlag").html("<input type='button' value='保存队列分配'  onclick='saveRedisFlag()'>");
	$("#editRedisFlag").selectpicker('refresh');
}
function saveRedisFlag() {
	$("#editRedisFlag").attr("disabled","disabled");
	$("#div_redisFlag").html("<input type='button' value='修改队列分配'  onclick='updateRedisFlag()'>");
	$("#editRedisFlag").selectpicker('refresh');
}

function updateProduct(id) {
	if (passed('修改')) {
		$("#changeBody").modal();
		// 回显数据
		$.post("findChannelById.action", {
			id : id
		}, function(obj) {
			$("#editBasisForm label").removeClass("active");
			$("#editHighLevelForm label").removeClass("active");
			$("#editBasisId").val(obj.id);
			$("#editBasisChannelId").val(obj.channelId);
			$("#editName").val(obj.name);
			$("#oldEditName").val(obj.name);
			$("#editNotice").val(obj.notice);
			$("#editSupplierId").selectpicker('val', obj.supplierId);
			$("#editRedisFlag").selectpicker('val', obj.redisFlag);
			if (obj.toCmcc == 'yes') {
				$("#editToCmcc").prop("checked", true);
				$("#editToCmcc").parent().addClass("active");
			}
			if (obj.toUnicom == 'yes') {
				$("#editToUnicom").prop("checked", true);
				$("#editToUnicom").parent().addClass("active");
			}
			if (obj.toTelecom == 'yes') {
				$("#editToTelecom").prop("checked",true);
				$("#editToTelecom").parent().addClass("active");
			}
			$("#editSubmitBegin").val(obj.submitBegin);
			$("#editSubmitEnd").val(obj.submitEnd);
			$("#editBasisForm input[name='isMonthLimit']").map(function() {
				if ($(this).val() == obj.isMonthLimit) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editMonthLimitCount").val(obj.monthLimitCount);
			$("#editBackupChannelId").selectpicker('val', obj.backupChannelId);
			$("#editChannelPrice").val(obj.channelPrice);
			$("#editBasisForm input[name='channelStatus']").map(function() {
				if ($(this).val() == obj.channelStatus) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editPlatformFlag").val(obj.platformFlag);
			$("#editBasisForm input[name='allPlatformUsed']").map(function() {
				if ($(this).val() == obj.allPlatformUsed) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			// -----------------------------------------------
			$("#editHighLevelId").val(obj.id);
			$("#editSourceSegment").val(obj.sourceSegment);
			$("#editSizeMax").val(obj.sizeMax);
			$("#editSizeFirst").val(obj.sizeFirst);
			$("#editSizeCharge").val(obj.sizeCharge);
			$("#editCallOut").val(obj.callOut);
			$("#eidtSourceFullLength").val(obj.sourceFullLength);
			$("#editHighLevelForm input[name='sourceType']").map(function() {
				if ($(this).val() == obj.sourceType) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='msgidType']").map(function() {
				if ($(this).val() == obj.msgidType) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='haveReport']").map(function() {
				if ($(this).val() == obj.haveReport) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='haveMo']").map(function() {
				if ($(this).val() == obj.haveMo) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editSuccessRate").val(obj.successRate);
			$("#editBaobeiBeforePjExtSrc").val(obj.baobeiBeforePjExtSrc);
			$("#editHighLevelForm input[name='autoExtSrc']").map(function() {
				if ($(this).val() == obj.autoExtSrc) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='userExtSrc']").map(function() {
				if ($(this).val() == obj.userExtSrc) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='baobeiModel']").map(function() {
				if ($(this).val() == obj.baobeiModel) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='filterGlobalBlack']").map(
					function() {
						if ($(this).val() == obj.filterGlobalBlack) {
							$(this).prop("checked", true);
							$(this).parent().addClass("active");
						}
					});
			$("#editHighLevelForm input[name='toIntl']").map(function() {
				if ($(this).val() == obj.toIntl) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editHighLevelForm input[name='gwType']").map(function() {
				if ($(this).val() == obj.gwType) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			$("#editSvcAddr").val(obj.svcAddr);
			$("#editSvcPort").val(obj.svcPort);
			$("#editAccount").val(obj.account);
			$("#editPassword").val(obj.password);
			$("#editLinkMax").val(obj.linkMax);
			$("#editLinkSpeed").val(obj.linkSpeed);
			$("#editFirstMsgChargeLen").val(obj.firstMsgChargeLen);
			$("#editSubseqMsgChargeLen").val(obj.subseqMsgChargeLen);
			$("#editServiceCode").val(obj.serviceCode);
			$("#editEnterpriseCode").val(obj.enterpriseCode);
			$("#editExtras").val(obj.extras);
			$("#editShortNum").val(obj.shortNum);
			$("#editVariant").selectpicker('val', obj.variant);
			$("#editHighLevelForm input[name='autoExtractSigns']").map(
					function() {
						if ($(this).val() == obj.autoExtractSigns) {
							$(this).prop("checked", true);
							$(this).parent().addClass("active");
						}
					});
			$("#editChannelPrice").val(obj.channelPrice);
			$("#editAlarmCode").val(obj.alarmCode);
			$("#editSubmitAlarmCode").val(obj.submitAlarmCode);
			$("#editHighLevelForm input[name='moMatch']").map(function() {
				if ($(this).val() == obj.moMatch) {
					$(this).prop("checked", true);
					$(this).parent().addClass("active");
				}
			});
			var models=[];
			if(obj.model!=null){
				models=obj.model.split(",");
			}
			var logicModels=[];
			if(obj.logicModel!=null){
				logicModels=obj.logicModel.split(",");
			}
			var m="";
			for(var i in models){
				m=models[i].split(":");
				$("#model_model input[type='number']").map(function(){
					if(m[0]==$(this).attr("data")){
						$(this).val(m[1]);
					}
				});
				
			}
			var l="";
			for(var i in logicModels){
				l=logicModels[i].split(":");
				$("#logic_model input[type='number']").map(function(){
					if(l[0]==$(this).attr("data")){
						$(this).val(l[1]);
					}
				});
				
			}
		}, "json");
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
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

function linkProduct(id){
	var url = "findLinkProduct.action?id="+id;
	$('#tLinkProduct').bootstrapTable('refresh', {
		url : url
	});
}

$('#tLinkProduct').bootstrapTable({
	method: 'get',
	contentType : "application/x-www-form-urlencoded",
	url:'',//请求后台url
	height:tableHeight(),//高度调整
	dataType:"json",//返回的数据格式
	cache:false,//是否缓存 默认是true
	async:false,
	
	columns:[{
		title:'产品ID',
		field:'id',
		width: 100
	},{
		title:'产品名称',
		field:'productName',
		width: 200
	},{
		title:'产品类别',
		field:'productClass',
		width: 100
	},{
		title:'产品状态',
		field:'ProductStatus',
		width: 100,
		formatter : changeProductStatusResult
	},{
		title:'是否签名',
		field:'IsSign',
		width: 100,
		formatter : changeIsSignResult
	}
	],
	locale:'zh-CN'//中文支持,
});
function changeProductStatusResult(value, row, index) {
	var result = "";
	result += row.productStatus == 'normal' ? "正常"
			: "<font color='#f00'>暂停</font>";
	return result;
}

function changeIsSignResult(value, row, index) {
	var result = "";
	result += row.isSign == 'yes' ? "是"
			: "否";
	return result;
}

function batchSwitchs(id){
	var url = "findChannelAllConfigByChannelId.action?id="+id;
	$('#tBatchSwitch').bootstrapTable('refresh', {
		url : url
	});
	$("#batchChannelId").val('');
	$("#batchChannelId").selectpicker('refresh');
}


$('#tBatchSwitch').bootstrapTable({
	method: 'get',
	contentType : "application/x-www-form-urlencoded",
	url:'',//请求后台url
	height:tableHeight(),//高度调整
	dataType:"json",//返回的数据格式
	cache:false,//是否缓存 默认是true
	async:false,
	showColumns : false,// 是否显示所有的列
	clickToSelect : false,// 是否启用点击选中行
	paginationShowPageGo: true,
//	toolbarAlign : 'right',
	buttonsAlign : 'right',// 按钮对齐方式
//	toolbar:'#toolbarDiv',//指定工作栏
	
	columns:[{
		title:'全选',
		field:'select',
		checkbox:true,
		width:25,
		align:'center',
		valign:'middle'
		},{
		title:'类型',
		field:'type',
		width: 150
		},{	
		title:'运营商',
		field:'provider',
		width: 50,
		formatter : function(value, row, index) {
			var result;
			if(value == 'cmcc'){
				return '移动';
			}else if(value == 'unicom'){
				return '联通';
			}else if(value == 'telecom'){
				return '电信';
			}else{
				return '错误';
			}
		}
		},{
		title:'使用方',
		field:'consumerId',
		width: 300,
		formatter : function(value, row, index) {
			return row.consumerId+":"+row.consumerName;
		}
		},{
		title:'使用方状态',
		field:'consumerStatus',
		width: 50,
		formatter : function(value, row, index) {
			var result = "";
			result += value == 'normal' ? "正常"
					: (value == 'pause')||(value == 'paused') ? "<font color='#f00'>暂停</font>"
							: "<font color='#f00'>删除</font>";
			return result;
		}
		},{
		title:'原通道',
		field:'channelId',
		width: 400,
		formatter : function(value, row, index) {
			return row.channelId+":"+row.name;
		}
		},{
		title:'新通道',
		field:'newChannelId',
		width: 400,
		formatter : function(value, row, index) {
			var option = '';
			var obj=eval("("+$("#div").text()+")");	
			option += "<div class='col-sm-50'>";
			option += "<select class='form-control'  onchange='changeSelect(this,"+index+")' placeholder='全部通道'  data-live-search='true'>";
			option += "<option value=''>全部通道</option>";
			for(var i in obj){
				if (value == ''){
					option += "<option value="+obj[i].id+">"+obj[i].id+":"+obj[i].name+"</option>";
					continue;
				}
				if (value != null && obj[i].id == value){
					option += "<option value="+obj[i].id+" selected='selected'>"+obj[i].id+":"+obj[i].name+"</option>";

				}else{
					option += "<option value="+obj[i].id+">"+obj[i].id+":"+obj[i].name+"</option>";
				}
				}
			option += "</select>";
			option += "</div>";
			return option;
		}
		}
	],
	locale:'zh-CN'//中文支持,
});


function changeSelect(obj, index){
	var indexSelected = obj.selectedIndex;
	var valueSelected = obj.options[obj.selectedIndex].value;
	//回写数据
	$('#tBatchSwitch').bootstrapTable('updateCell', {
	    index: index,
	    field: 'newChannelId',
	    value: valueSelected
	  })
}


//批量修改（选中）
$('#btn_batch').click(function(){
	if($("#batchChannelId").val() != ''){
	var dataId = $.map($("#tBatchSwitch").bootstrapTable('getSelections'), function (row) {
		return row.id;
		});
	if(dataId.length > 0){
	for(var i = 0;i < dataId.length;i++){
		$('#tBatchSwitch').bootstrapTable('updateCell', {
	    index: dataId[i]-1,
	    field: 'newChannelId',
	    value: $("#batchChannelId").val()
	  })
	}
	
	$("#batchChannelId").val('');
	$("#batchChannelId").selectpicker('refresh');
	}else{
		$('.popup_de .show_msg').text('请选择要替换的跳转策略');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
	}else{
		$('.popup_de .show_msg').text('请选择通道');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
})


//批量修改（全部）
//$('#btn_all').click(function(){
//	if($("#batchChannelId").val() != ''){
//	var rows=$('#tBatchSwitch').bootstrapTable("getData").length;
//	for(var i=0;i < rows; i++){
//	$('#tBatchSwitch').bootstrapTable('updateCell', {
//	    index: i,
//	    field: 'newChannelId',
//	    value: $("#batchChannelId").val()
//	  })
//	}
//	$("#batchChannelId").val('');
//	$("#batchChannelId").selectpicker('refresh');
//
//	}else{
//		$('.popup_de .show_msg').text('请选择一条通道');
//		$('.popup_de').addClass('bbox');
//		$('.popup_de .btn_submit').one('click', function() {
//			$('.popup_de').removeClass('bbox');
//		})
//	}
//})


//根据复选框批量保存
$('#btn_save').click(function(){
	 if(passed('通道批量切换')){
   	var dataArr=$('#tBatchSwitch').bootstrapTable('getSelections');
   	var dataNewChannel = $.map($("#tBatchSwitch").bootstrapTable('getSelections'), function (row) {
   							if (row.newChannelId  == ''){
   								return ;
   							}
   							return row.newChannelId;
   						});
		var list = [];
		if(dataArr.length == 0){
		$('.popup_de .show_msg').text('请至少选择一个保存项');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
		}else if(dataArr.length > 0 && dataArr.length > dataNewChannel.length){
			$('.popup_de .show_msg').text('选中项未选择新通道');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}else{
		var config = null;
		for(var i=0;i<dataArr.length;i++){
			config = new Object();
			config.type = dataArr[i].type;
			config.newChannelId = dataArr[i].newChannelId;
			config.provider = dataArr[i].provider;
			config.consumerId = dataArr[i].consumerId;
			config.channelId = dataArr[i].channelId;
			list.push(config);
		}
		$.post("updateChannelCongifg.action", {
			array : JSON.stringify(list)
		}, function(data) {
			if (data.result ==  1) {
				$('.popup_de .show_msg').text('修改成功！');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
					$('#tBatchSwitch').bootstrapTable('refresh', {
						url : 'findChannelAllConfigByChannelId.action?id=' + config.channelId
					});
				})
			} else if(data.result == 2){
				$('.popup_de .show_msg').text('存在部分修改失败');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
					$('#tBatchSwitch').bootstrapTable('refresh', {
						url : 'findChannelAllConfigByChannelId.action?id=' + config.channelId
					});
				})
			}else{
				$('.popup_de .show_msg').text('全部修改失败');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
					$('#tBatchSwitch').bootstrapTable('refresh', {
						url : 'findChannelAllConfigByChannelId.action?id=' + config.channelId
					});
				})
			}
//			$("#batchSwitch").modal('hide');
			
		}, "json");
		}
		
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
		$(this).parent().removeClass("active");
		$('.popup_de').removeClass('bbox');
		})
	}		
})

$(document).ajaxComplete(function(event, xhr, settings) {
	$("#saveVariant").click(function(){
		$("#editVariant").prop("disabled","disabled");
		$("#div_variant").html("<input type='button' value='修改通道类' id='updateVariant'>");
		
	});
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