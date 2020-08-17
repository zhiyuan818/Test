/** 初始化加载 **/
$(function(){
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#appShuntPoliciesTab').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });
   
    //生成客户数据
    $('#appShuntPoliciesTab').bootstrapTable({
     	method: 'post',
     	contentType: "application/x-www-form-urlencoded",
     	url:"findAppShuntPoliciesPageList.action",//请求后台url
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
     	uniqueId: "appId",  //每一行的唯一标识，一般为主键列
     	pageSize:20,//单页记录数
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
         		title:'客户简称',
         		field:'companyName',
         		formatter:function(value,row,index){
         			return row.appId+":"+value;
         		}
         	},
         	{
         		title:'账户名称',
         		field:'appName',
         	},
         	{
         		title:'包基数',
         		field:'ignoredPackMin',
         	},
         	{
         		title:'前基数',
         		field:'ignoredPackHead',
         	},
         	{
         		title:'后基数',
         		field:'ignoredPackTail',
         	},
         	{
         		title:'忽略省份',
         		field:'ignoredProvinces',
         	},
         	{
         		title:'忽略运营商',
         		field:'ignoredCarriers',
         	},
         	{
         		title:'优享比例',
         		field:'appShuntLevel',
         	},
         	{
         		title:'优享内容',
         		field:'content',
         	},
         	{
         		title:'省份/内容比例',
         		field:'percent',
         	},
         	{
         		fileld:'appId',
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
		},
     	locale:'zh-CN'//中文支持,
     });
    
    $("#search_btn").click(function () {
       	$('#appShuntPoliciesTab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			url : "findAppShuntPoliciesPageList.action"
			});
	});
    $("#search_back").click(function () {
    	$("#search_appName").val('');
    	$("#search_ignoredProvinces").val('');
    	$("#search_ignoredCarriers").val('');
    	$("#search_ignoredPackMin").val('');
    	$("#search_ignoredPackHead").val('');
    	$("#search_ignoredPackTail").val('');
    	$('#appShuntPoliciesTab').bootstrapTable('refreshOptions',{pageNumber:1,limit:30,sort:'',order:'asc'});
    });
    
    //菜单添加表单验证
    $("#btn_add").click(function() {
    	$("#addForm label").removeClass("active");
		if (passed("新增")) {
			
			$("#addForm [name='isSyncSubApp']").map(function() {
				if ($(this).val() == "false") {
					$(this).attr("checked", "checked");
					$(this).parent().addClass("active")
				}
			});
			
			$("#addBody").modal();
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
    addValidator();
    updateValidator();
	$('.add_backBtn').click(function() {
		$("#addForm")[0].reset();
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#add_saveBtn").attr("disabled", false);
		$("#addBody").modal("hide");
		$("#appId").selectpicker('refresh');
	});
    
	$("#add_saveBtn").click(function() {
		mark=true;
		$('#addForm').bootstrapValidator('validate');
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.btn_submit').one('click',function() {
				$("#add_saveBtn").attr("disabled", true);
				
//				var appId = $("#appId").val();
//				var ignoredPackMin = $("#ignoredPackMin").val();
//				var ignoredPackHead = $("#ignoredPackHead").val();
//				var ignoredPackTail = $("#ignoredPackTail").val();
//				
//				var arr = new Array();
//                $("#ignoredProvinces :checkbox[checked]").each(function(i){
//                    arr[i] = $(this).val();
//                });
//                var vals = arr.join(",");
//				
//				var ignoredCarriers = $("#addForm checkbox[name='ignoredCarriers']:checked").val(); 
//				var isSyncSubApp = $("#addForm input[name='isSyncSubApp']:checked").val(); 
				
				var _info = $('#addForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.ajax({
						url :"addAppShuntPolicies.action",
						type : "post",
						data:data,
						success:function(obj) {
							$("#addBody").modal("hide");
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator',null);
							addValidator();
							$('#addForm')[0].reset();
							$("#appId").selectpicker('refresh');
							
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('添加成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#appShuntPoliciesTab').bootstrapTable('refresh',{url : 'findAppShuntPoliciesPageList.action'});
							} else {
								$('.popup_de .show_msg').text('添加失败 !');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#appShuntPoliciesTab').bootstrapTable('refresh',{url : 'findAppShuntPoliciesPageList.action'});
							}
							$("#add_saveBtn").attr("disabled",false);
						}, dataType:"json"})
					mark=false;
				}
					})
			}
	});
    
	//菜单修改表单
	$("#edit_saveBtn").click(function() {
		mark=true;
		$('#editForm').bootstrapValidator('validate');
		if ($("#editForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已修改的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				$("#edit_saveBtn").attr("disabled", true);
				var _info = $('#editForm').serialize();
				data = decodeURIComponent(_info, true);
				if(mark){
					$.ajax({
						url : "updateAppShuntPolicies.action",
						type : "post",
						async : false,
						data : data,
						success : function(obj) {
							$("#changeBody").modal("hide");
							$("#editForm").data('bootstrapValidator').destroy();
							$('#editForm').data('bootstrapValidator',null);
							updateValidator();
							$('#editForm')[0].reset();
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('修改成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#appShuntPoliciesTab').bootstrapTable('refresh', {
										url : 'findAppShuntPoliciesPageList.action'
									});
								})
							} else {
								$('.popup_de .show_msg').text('修改失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#appShuntPoliciesTab').bootstrapTable('refresh', {
										url : 'findAppShuntPoliciesPageList.action'
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
	})
	$('.edit_backBtn').click(function() {
		$("#editForm").data('bootstrapValidator').destroy();
		$('#editForm').data('bootstrapValidator', null);
		updateValidator();
		$("#changeBody").modal('hide');
		$("#editForm")[0].reset();
		$("#edit_saveBtn").attr("disabled", false);
		
		$("#editForm label").removeClass("active");
	});
	
	
	//批量删除
	$('#btn_del').click(function() {
		mark=true;
		if (passed('批量删除')) {
			var dataArr = $('#appShuntPoliciesTab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de .btn_submit').one('click', function() {
				var ID = [];
				var i = 0;
				for (i; i < dataArr.length; i++) {
					ID[i] = dataArr[i].appId;
				}
				if (ID.length > 0) {  
					if(mark){
						$.post("delAppShuntPoliciesBatch.action", {
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
								$('#appShuntPoliciesTab').bootstrapTable('refresh', {
									url : 'findAppShuntPoliciesPageList.action'
								});
							} else {
								$('.popup_de .show_msg').text('删除出错！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$("#edit_saveBtn").attr("disabled", false);
									$('.popup_de').removeClass('bbox');
								})
								$('#appShuntPoliciesTab').bootstrapTable('refresh', {
									url : 'findAppShuntPoliciesPageList.action'
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
				var dataArr = $('#appShuntPoliciesTab .selected');
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
		var dataArr = $('#appShuntPoliciesTab .selected');
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
						message : '账户不能为空'
					}
				}
			},
			ignoredPackMin : {
				validators : {
					regexp: {
 	    			   regexp: /^[0-9_\.]+$/,
 	    			   message: '必需输入数字' 
 	    		   }
				}
			},
			ignoredPackHead : {
				validators : {
					regexp: {
 	    			   regexp: /^[0-9_\.]+$/,
 	    			   message: '必需输入数字' 
 	    		   }
				}
			},
			ignoredPackTail : {
				validators : {
					regexp: {
 	    			   regexp: /^[0-9_\.]+$/,
 	    			   message: '必需输入数字' 
 	    		   }
				}
			},
			appShuntLevel:{
         	   validators: {
         		   regexp: {
         			   regexp: /^(?:0|[1-9][0-9]?|100)$/,
         			   message: '范围在0-100之间'
         		   }
         	   }
            },
			content:{
	         	   validators: {
	         		  stringLength : {
							max : 500,
							message : '优享内容长度必须在0到500位之间'
						}
	         	   }
	            }
            ,
            percent:{
         	   validators: {
         		   regexp: {
         			   regexp: /^(?:0|[1-9][0-9]?|100)$/,
         			   message: '范围在0-100之间'
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
			appId : {
				validators : {
					notEmpty : {
						message : '账户不能为空'
					}
				}
			},
			ignoredPackMin : {
				validators : {
					regexp: {
 	    			   regexp: /^[0-9_\.]+$/,
 	    			   message: '必需输入数字' 
 	    		   }
				}
			},
			ignoredPackHead : {
				validators : {
					regexp: {
 	    			   regexp: /^[0-9_\.]+$/,
 	    			   message: '必需输入数字' 
 	    		   }
				}
			},
			ignoredPackTail : {
				validators : {
					regexp: {
 	    			   regexp: /^[0-9_\.]+$/,
 	    			   message: '必需输入数字' 
 	    		   }
				}
			},
			 appShuntLevel:{
          	   validators: {
          		   regexp: {
          			   regexp: /^(?:0|[1-9][0-9]?|100)$/,
          			   message: '范围在0-100之间'
          		   }
          	   }
             },
 			  content:{
	         	   validators: {
	         		  stringLength : {
							max : 500,
							message : '优享内容长度必须在0到500位之间'
						}
	         	   }
	            },
	          percent:{
	        	  validators: {
	        		  regexp: {
	        			  regexp: /^(?:0|[1-9][0-9]?|100)$/,
	        			  message: '范围在0-100之间'
	        		  }
	        	  }
	          }
			
		}
	});
}

function tableHeight() {
    return $(window).height()-60;
}

function queryParams(params){
	return{
		pageSize: params.limit, //页面大小
		pageIndex:params.pageNumber, //页码
		
		appName:$("#search_appName").val(),
		ignoredProvinces:$("#search_ignoredProvinces").val(),
		ignoredCarriers:$("#search_ignoredCarriers").val(),
		ignoredPackMin:$("#search_ignoredPackMin").val(),
		ignoredPackHead:$("#search_ignoredPackHead").val(),
		ignoredPackTail:$("#search_ignoredPackTail").val()
	}
}

function actionFormatter(value, row, index) {
	var result = "";
	result += "<button class='btn btn-primary btn-xs' onclick=\"EditViewById('"
			+ row.appId + "')\" title='点击修改'>修改</button>&nbsp;&nbsp;";
	result += "<button class='btn btn-primary btn-xs'  onclick=\"DeleteById('"
			+ row.appId + "')\" title='点击删除'>删除</button>";
    return result;
}

function DeleteById(appId) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("deleteAppShuntPoliciesById.action", 
						{appId : appId}, 
						function(data) {
							if (data.result > 0) {
								$('.popup_de .show_msg').text('删除成功！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
							}else{
								$('.popup_de .show_msg').text('删除失败!');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
							}
							$('#appShuntPoliciesTab').bootstrapTable('refresh', {
								url : 'findAppShuntPoliciesPageList.action'
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

function EditViewById(appId) {
	$("#editForm label").removeClass("active");
	if (passed('修改')) {
		$.post("findAppShuntPoliciesByAppId.action", {
			appId : appId
		}, function(obj) {
			$("#changeBody").modal();
			$("#editId").val(obj.id);
			$("#editAppId").val(obj.appId);
			$("#editAppName").val(obj.appName);
			$("#editIgnoredPackMin").val(obj.ignoredPackMin);
			$("#editIgnoredPackHead").val(obj.ignoredPackHead);
			$("#editIgnoredPackTail").val(obj.ignoredPackTail);
			$('#editAppShuntLevel').val(obj.appShuntLevel);
			$('#editContent').val(obj.content);
			$('#editPercent').val(obj.percent);
			
			$("#ignoredProvincesDiv").empty();
			$("#ignoredProvincesDiv")
					.append(
							"<input name='ignoredProvinces' type='checkbox' value='北京' />北京");
			$("#ignoredProvincesDiv")
					.append(
							"<input name='ignoredProvinces' type='checkbox' value='上海' />上海");
			$("#ignoredProvincesDiv")
					.append(
							"<input name='ignoredProvinces' type='checkbox' value='天津' />天津");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='重庆' />重庆");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='黑龙江' />黑龙江");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='辽宁' />辽宁");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='吉林' />吉林");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='山东' />山东");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='安徽' />安徽");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='山西' />山西");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='河北' />河北");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='河南' />河南");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='江苏' />江苏");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='江西' />江西");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='浙江' />浙江");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='湖南' />湖南");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='湖北' />湖北");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='福建' />福建");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='广东' />广东");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='广西' />广西");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='宁夏' />宁夏");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='四川' />四川");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='内蒙古' />内蒙古");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='云南' />云南");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='新疆' />新疆");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='海南' />海南");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='甘肃' />甘肃");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='贵州' />贵州");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='西藏' />西藏");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='陕西' />陕西");
			$("#ignoredProvincesDiv")
			.append(
					"<input name='ignoredProvinces' type='checkbox' value='青海' />青海");
			
			if (obj.ignoredProvinces == "" || obj.ignoredProvinces == null
					|| obj.ignoredProvinces == undefined) {
			} else {
				var express = obj.ignoredProvinces.split(','); // 去掉它们之间的分割符“，”
				var boxObj = $("#changeBody input:checkbox[name='ignoredProvinces']"); // 获取所有的复选框
				for (i = 0; i < boxObj.length; i++) {
					for (j = 0; j < express.length; j++) {
						if (boxObj[i].value == express[j]) // 如果值与修改前的值相等
						{
							boxObj[i].checked = true;
							break;
						}
					}
				}
			}
			
			$("#ignoredCarriersDiv").empty();
			$("#ignoredCarriersDiv")
					.append(
							"<input name='ignoredCarriers' type='checkbox' value='cmcc' />移动");
			$("#ignoredCarriersDiv")
					.append(
							"<input name='ignoredCarriers' type='checkbox' value='unicom' />联通");
			$("#ignoredCarriersDiv")
					.append(
							"<input name='ignoredCarriers' type='checkbox' value='telecom' />电信");
			if (obj.ignoredCarriers == "" || obj.ignoredCarriers == null
					|| obj.ignoredCarriers == undefined) {
			} else {
				var express = obj.ignoredCarriers.split(','); // 去掉它们之间的分割符“，”
				var boxObj = $("#changeBody input:checkbox[name='ignoredCarriers']"); // 获取所有的复选框
				for (i = 0; i < boxObj.length; i++) {
					for (j = 0; j < express.length; j++) {
						if (boxObj[i].value == express[j]) // 如果值与修改前的值相等
						{
							boxObj[i].checked = true;
							break;
						}
					}
				}
			}
			
			$("#editForm [name='isSyncSubApp']").map(function() {
				if ($(this).val() == "false") {
					$(this).attr("checked", "checked");
					$(this).parent().addClass("active")
				}
			});
			$('#editForm').bootstrapValidator('validate');
			
		}, "json");
	}else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}

function passed(button) {
	flag=false;
	var buttons=$("#buttons").val();
	if(buttons == null){
		return flag;
	}
	var menubutton=buttons.split(",");
	for(var i in menubutton){
		if(menubutton[i]==button){
			flag=true;
		}
	}
	return flag;
}


$(function() {
	//弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function(){
		$('.popup_de').removeClass('bbox');
	})
			//弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function(){
			$('.popup_de').removeClass('bbox');
	})
	
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
