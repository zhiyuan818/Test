/** 初始化加载 **/
$(function(){
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#mytab').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });
    
    //生成客户数据
    $('#mytab').bootstrapTable({
     	method: 'post',
     	contentType: "application/x-www-form-urlencoded",
     	url:"findReSendConfig.action",//请求后台url
     	height:tableHeight(),//高度调整
     	dataType:"json",//返回的数据格式
     	cache:false,//是否缓存 默认是true
     	striped: true, //是否显示行间隔色
     	pageNumber: 1, //初始化加载第一页，默认第一页
     	pagination:true,//是否分页
     	queryParamsType:'limit',
     	queryParams:queryParams,
     	sortable: true, //是否启用排序
        sortOrder: "asc",//排序方式
     	sidePagination:'server', //服务器端的分页
     	uniqueId: "id",  //每一行的唯一标识，一般为主键列
     	pageSize:10,//单页记录数
     	pageList:[5,10,20,50],//分页步进值
     	showRefresh:false,//刷新按钮
     	showColumns:false,//是否显示所有的列
     	clickToSelect: false,//是否启用点击选中行
     	toolbarAlign:'right',
     	buttonsAlign:'right',//按钮对齐方式
     	paginationShowPageGo: true,
     	toolbar:'#toolbar',//指定工作栏
     	columns:[
         	{
         		title:'全选',
         		field:'select',
         		checkbox:true,
         		width:25,
         		align:'center',
         		valign:'middle',
         	},
         	{
         		title:'ID',
         		field:'id',
         		width: 50
         	},
         	{
         		title:'客户简称',
         		field:'companyKey'
         	},
         	{
         		title:'帐户名称',
         		field:'appName',
         		formatter : appNameResult
         	},
         	{
         		title:'通道名称',
         		field:'appChannelId',
         		formatter : splicingLocation1
         	},
         	{
         		title:'错误码',
         		field:'status'
         	},
         	{
         		title:'短信类型',
         		field:'msgType',
         		formatter : changeResult
         	},
         	{
         		title:'重发要跳转的通道',
         		field:'toChannelId',
         		formatter : splicingLocation2
         	},
         	{
         		title:'忽略值',
         		field:'ignoreChanOrAcc',
         		width:200
         	},
         	{
         		title:'创建时间',
         		field:'createTime',
         		width: 200,
         		formatter : function(value, row, index) {
    				return changeDateFormat(value)
    			}
         	},
         	{
         		fileld:'ID',
         		title:'操作',
         		width:120,
         		align:'center',
         		valign:'middle',
  			formatter:actionFormatter
         	}
     	],
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
		    
		    if ($("input[name='idType']:checked").val() == 'channel'){
		    	$('#mytab').bootstrapTable('showColumn', 'appChannelId');
		    	$('#mytab').bootstrapTable('hideColumn', 'companyKey');
		    	$('#mytab').bootstrapTable('hideColumn','appName');
		    }else{
		    	$('#mytab').bootstrapTable('hideColumn', 'appChannelId');
		    	$('#mytab').bootstrapTable('showColumn','companyKey');
		    	$('#mytab').bootstrapTable('showColumn','appName');
		    }
		    
		    $("input[name='addIdType']").change(function(){
				if ($("input[name='addIdType']:checked").val() == 'channel'){
					$("#addChannelDiv").show();
					$("#addAppIdDiv").hide();
			    }else{
			    	$("#addAppIdDiv").show();
			    	$("#addChannelDiv").hide();
			    }
				$("#addAppId").selectpicker('refresh');
				$("#addChannelId").selectpicker('refresh');
				$("#addToChannelId").selectpicker('refresh');
				$("#addMsgType").selectpicker('refresh');
				$('#addStatus').val();
				});
		    $("input[name='idType']").change(function(){
				if ($("input[name='idType']:checked").val() == 'account'){
					$("#searchCompanyIdDiv").show();
					$("#searchAppIdDiv").show();
					$("#searchChannelIdDiv").hide();
			    }else{
			    	$("#searchChannelIdDiv").show();
			    	$("#searchAppIdDiv").hide();
					$("#searchCompanyIdDiv").hide();
			    }
				});
		    
		    
		},
     	locale:'zh-CN'//中文支持,
     });
    addValidator();
	updateValidator();

    
    $("#search_btn").click(function () {
		 $('#mytab').bootstrapTable('refresh',{url:'findReSendConfig.action'});
		});
	    $("#search_back").click(function () {
	    	$("#searchStatus").val(''),
	    	$("[name='idType']").first().prop("checked",true);
	    	$("[name='idType']").first().parent().addClass("active");
	    	$("[name='idType']").last().parent().removeClass("active");
	    	$("select.selectpicker").each(function() {
				$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
				$(this).find("option").prop("selected", false); // 重置原生select的值
				$(this).find("option:first").prop("selected", true);
			});	    	
	    	
	    	$('#mytab').bootstrapTable('refresh',{url:'findReSendConfig.action'});
	    });
	    	
		
		//添加类型
		$("#btn_add").click(function() {
			if (passed('新增')) {
				$("#addBody").modal();
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
		$('#add_backBtn').click(function() {
			$("#addBody").modal("hide");
			$("#addForm")[0].reset();
			$("[name='addIdType']").first().prop("checked",true);
	    	$("[name='addIdType']").first().parent().addClass("active");
	    	$("[name='addIdType']").last().parent().removeClass("active");
	    	$("#addAppIdDiv").show();
	    	$("#addChannelDiv").hide();
			$("#addAppId").selectpicker('refresh');
			$("#addChannelId").selectpicker('refresh');
			$("#addToChannelId").selectpicker('refresh');
			$("#addMsgType").selectpicker('refresh');
			$('#addStatus').val();
			$("#addForm").data('bootstrapValidator').destroy();
			$('#addForm').data('bootstrapValidator', null);
			addValidator();
			$("#add_saveBtn").attr("disabled", false);
		});
		$('#add_saveBtn').click(function() {
			mark=true;
			// 点击保存时触发表单验证
			$('#addForm').bootstrapValidator('validate');
			// 如果表单验证正确，则请求后台添加用户
			if ($("#addForm").data('bootstrapValidator').isValid()) {
				$("#add_saveBtn").attr("disabled", true);
				var _info = $('#addForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.post("addReSendConfig.action", data, function(data) {
						// 后台返回添加成功
						if (data.result == 1) {
							$("#addForm")[0].reset();
							$("#addBody").modal("hide");
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$("#add_saveBtn").attr("disabled", false);
							$('.popup_de .show_msg').text('添加成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#mytab').bootstrapTable('refresh',{url : 'findReSendConfig.action'});
							})
							
						}else if(data.result == 2){
							$('.popup_de .show_msg').text('该错误码已存在，请勿重复添加！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$("#add_saveBtn").attr("disabled", false);
								$('.popup_de').removeClass('bbox');
							})
						}else {
							$('.popup_de .show_msg').text('添加失败，请重新添加！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$("#add_saveBtn").attr("disabled", false);
								$('.popup_de').removeClass('bbox');
							})
						}
						$("[name='addIdType']").first().prop("checked",true);
				    	$("[name='addIdType']").first().parent().addClass("active");
				    	$("[name='addIdType']").last().parent().removeClass("active");
				    	$("#addAppIdDiv").show();
				    	$("#addChannelDiv").hide();
				    	$("#addAppId").selectpicker('refresh');
				    	$("#addChannelId").selectpicker('refresh');
				    	$("#addToChannelId").selectpicker('refresh');
				    	$("#addMsgType").selectpicker('refresh');
				    	$('#addStatus').val();
					})
					mark=false;
				}
			}
		});
		
		
	    $('#edit_saveBtn').click(function() {
			mark=true;
			// 点击保存时触发表单验证
			$('#editForm').bootstrapValidator('validate');
			if ($("#editForm").data('bootstrapValidator').isValid()) {
				$('.popup_de .show_msg').text('确定要修改吗？');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click',function() {
					$("#edit_saveBtn").attr("disabled", true);
					// 如果表单验证正确，则请求后台修改配置
					var _info = $('#editForm').serialize();
						data = decodeURIComponent(_info, true);
						if(mark){
							$.post("updateReSendConfig.action",
									data,
									function(data) {
								$("#changeBody").modal("hide");
								if (data.result > 0) {
									$('.popup_de .show_msg').text('修改成功！');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$('.popup_de').removeClass('bbox');
										$('#mytab').bootstrapTable('refresh',{url : 'findReSendConfig.action'});
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
		// 修改页面返回按钮
		$('#edit_backBtn').click( function() {
			$("#editAppId").selectpicker('refresh');
			$("#editChannelId").selectpicker('refresh');
			$("#editToChannelId").selectpicker('refresh');
			$("#editMsgType").selectpicker('refresh');
			$('#editStatus').val();
			$("#changeBody").modal('hide');
			$("#editForm")[0].reset();
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
							$.post("delResendConfigBatch.action", {
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
										url : 'findReSendConfig.action'
									});
								} else {
									$('.popup_de .show_msg').text('删除出错！');
									$('.popup_de .btn_cancel').css('display', 'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
									$('#btn_del').css('display', 'none');
									$('.popup_de').removeClass('bbox');
									})
									$('#mytab').bootstrapTable('refresh', {
										url : 'findReSendConfig.action'
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
		
		//弹出框取消按钮事件
		$('.popup_de .btn_cancel').click(function(){
			$('.popup_de').removeClass('bbox');
		})
		//弹出框关闭按钮事件
		$('.popup_de .popup_close').click(function(){
				$('.popup_de').removeClass('bbox');
		})
		
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
		
});


function tableHeight() {
    return $(window).height()-50;
}

function queryParams(params){
	return{
		pageSize: params.limit, //页面大小
		pageIndex:params.pageNumber, //页码
		companyId:$("#searchCompanyId").val(),
		appId:$("#searchAppId").val(),
		channelId:$("#searchChannelId").val(),
		status:$("#searchStatus").val(),
		toChannelId:$("#searchToChannelId").val(),
		msgType:$("#searchMsgType").val(),
		idType : $("input[name='idType']:checked").val()
	}
}

function appNameResult(value, row, index) {
	var name = "";
	name = row.appChannelId + ":" + value;
	return name;
}
function splicingLocation1(value, row, index) {
	if(value == null && row.channelName != null){
		return row.channelName;
	}
	var name = "";
	name = value + ":" + row.channelName;
	return name;
}
function splicingLocation2(value, row, index) {
	if(value == null && row.channelName != null){
		return row.channelName;
	}
	var name = "";
	name = value + ":" + row.toChannelName;
	return name;
}

function changeResult(value, row, index) {
	var result = "";
	switch(value) {
    case value="all":
    	result = "所有";
       break;
    case value="bulk":
    	result = "营销";
       break;
    case value="notif":
    	result = "行业";
       break;
    case value="code":
    	result = "码类";
       break;
    default:
    	result = "未知";
}

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


function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditById('" + row.id + "','" + row.idType + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"delById('" + row.id + "','" + row.idType + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    return result;
}


function EditById(id,idType) {
	
	if (passed('修改')) {
		$.post("findReSendConfigById.action", {
			id : id,
			idType : idType
		}, function(result) {
			$("#changeBody").modal();
			if (idType == 'channel'){
				$("#editChannelDiv").show();
				$("#editAppIdDiv").hide();
		    }else{
		    	$("#editAppIdDiv").show();
		    	$("#editChannelDiv").hide();
		    }
			$('#editAppId').selectpicker('val',result.appChannelId);
			$('#editChannelId').selectpicker('val',result.appChannelId);
			$('#editStatus').val(result.status);
			$('#editId').val(result.id);
			$('#editIdType').val(result.idType);
			$('#editIgnoreChanOrAcc').val(result.ignoreChanOrAcc);
			$('#editMsgType').selectpicker('val',result.msgType);
			$('#editToChannelId').selectpicker('val',result.toChannelId);
			
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
function delById(id,idType) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("delReSendConfig.action", {
					id : id,
					idType : idType
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
						url : 'findReSendConfig.action'
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


function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appId : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					
				}
			},
			channelId : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					
				}
			},
			status : {
				validators : {
					notEmpty : {
						message : '错误码不能为空'
					},
					stringLength : {
						max : 30,
						message : '长度不能超30'
					}
				}
			},
			toChannelId : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					regexp: {
	 	    			   regexp: /^[1-9]\d*$/,
	 	    			   message: '不可选择空通道' 
	 	    		   },
					
				}
			},
			msgType : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					
				}
			},
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
			appId : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					
				}
			},
			channelId : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					
				}
			},
			status : {
				validators : {
					notEmpty : {
						message : '错误码不能为空'
					},
					stringLength : {
						max : 30,
						message : '长度不能超30'
					}
				}
			},
			toChannelId : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					regexp: {
	 	    			   regexp: /^[1-9]\d*$/,
	 	    			   message: '不可选择空通道' 
	 	    		   },
					
				}
			},
			msgType : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					
				}
			},
		}
	});
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


/** 自定义toastr **/
$(function(){
    //参数设置，若用默认值可以省略以下面代码
    toastr.options = {
       "positionClass": "toast-bottom-full-width",//弹出窗的位置
    };
});