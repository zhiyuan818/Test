menuArg=new Array();
managerMenuArg=new Array();
function managerMenu(id,ucenterManagerId,ucenterMenuId,menuButtons)
{
	this.id=id;
	this.ucenterManagerId=ucenterManagerId;
	this.ucenterMenuId=ucenterMenuId;
	this.menuButtons=menuButtons;
}
$(function(){
	
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#mytab').bootstrapTable('resetView', {
            height: tableHeight()
        })
    })
    
    //生成用户数据
   $('#mytab').bootstrapTable({
    	method: 'post',
    	contentType: "application/x-www-form-urlencoded",
    	url:"findUcenterManager.action",
    	height:tableHeight(),//高度调整
    	dataType:"json",
    	cache:false,
    	striped: true, //是否显示行间隔色
    	pageNumber: 1, //初始化加载第一页，默认第一页
    	pagination:true,//是否分页
    	queryParamsType:'limit',
    	queryParams:queryParams,
    	sortable: true, //是否启用排序
        sortOrder: "asc",//排序方式
    	sidePagination:'server', //服务器端的分页
    	uniqueId: "ID",  //每一行的唯一标识，一般为主键列
    	pageSize:50,//单页记录数
    	pageList:[30,50,100],//分页步进值
    	showRefresh:false,//刷新按钮
    	showColumns:false,
    	clickToSelect: false,//是否启用点击选中行
    	paginationShowPageGo: true,
    	toolbarAlign:'right',
    	buttonsAlign:'right',//按钮对齐方式
    	toolbar:'#toolbar',//指定工作栏
    	columns:[
        	{
        		title:'全选',
        		field:'select',
        		checkbox:true,
        		width:25,
        		align:'center',
        		valign:'middle'
        	},
        	{
        		title:'ID',
        		field:'id',
        		visible:false
        	},
        	{
        		title:'用户名',
        		field:'managerName',
        		sortable:true
        	},
        	{
        		title:'中文名称',
        		field:'chineseName',
        	},
        	{
        		title:'角色',
        		field:'title'
        	},
			{
				   title:'spId',
				   field:'spId',
				   visible:false
			},
			{
        		title:'账户权限级别',
        		field:'isAllCustomer',
        		formatter : function(value, row, index) {
    				if (value == '0') {
    					return "角色及其他";
    				} else if (value == '1') {
    					return "角色";
    				} else if(value == '2') {
    					return "全部";
    				}
    			}
        	},
        	{
        		title:'通道权限级别',
        		field:'isAllChannel',
        		formatter : function(value, row, index) {
    				if (value == '0') {
    					return "角色及其他";
    				} else if (value == '1') {
    					return "角色";
    				} else if(value == '2') {
    					return "全部";
    				}
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
		},
    	locale:'zh-CN'//中文支持,
    })
    addValidator();
    updateValidator();
    $("#search_btn").click(function () {
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#searchManagerName").val("");
    	$("#searchTitle").val("");
    	$("#searchIsAllCustomer").val("");
    	$("#searchIsAllChannel").val("");
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1,pageSize:10,sort:'',order:'asc'});
    });
})

function addValidator(){
	$('#addForm').bootstrapValidator({
       	feedbackIcons: {
               valid: 'glyphicon glyphicon-ok',
               invalid: 'glyphicon glyphicon-remove',
               validating: 'glyphicon glyphicon-refresh'
           },
		           fields : {
			managerName : {
				validators : {
					notEmpty : {
						message : '用户名不能为空'
					},stringLength : {
						min : 4,
						max : 8,
						message : '用户名长度必须在4到8之间'
					},
					remote : {
						url : "checkLoginName.action",
						type : "POST",
						delay : 2000,
						message : "用户名已存在"
					},
					regexp: {
                    	regexp :  /^[0-9a-zA-Z:,]*$/,
                       message : '必须是数字、字母、数字字母'
         		   }
				}
			},
			showPassword : {
				validators : {
					notEmpty : {
						message : '密码不能为空'
					},
					regexp: {
                    	regexp :  /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!_~.@#$%^&*?])[a-zA-Z!_~.@#$%^&*?0-9]{8,16}$/g,
						message : '密码8-16位，包含数字、字母、特殊符号'
         		   }
				}
			},
			showRepeatPassword : {
				validators : {
					notEmpty : {
						message : '密码确认不能为空'
					},
					identical : {
						field : 'showPassword',
						message : '两次密码不相同'
					}
				}
			},
			chineseName : {
				validators : {
					notEmpty : {
						message : '中文名称不能为空'
					},stringLength : {
						min : 2,
						max : 8,
						message : '中文名称长度必须在2到8之间'
					}
				}
			},
			title : {
				validators : {
					notEmpty : {
						message : '角色不能为空'
					}
				}
			}
		}
     });
}
function updateValidator(){
	 // 更新表单验证
    $('#editForm').bootstrapValidator({
       	feedbackIcons: {
               valid: 'glyphicon glyphicon-ok',
               invalid: 'glyphicon glyphicon-remove',
               validating: 'glyphicon glyphicon-refresh'
           },
           fields: {
        	   id:{
        		   validators:{
        			   notEmpty: {
                           message: 'ID不能为空'
                       } 
        		   }
        	   },
        	   managerName: {
                   validators: {
                       notEmpty: {
                           message: '用户名不能为空'
                       },
                   },stringLength : {
						min : 4,
						max : 8,
						message : '用户名长度必须在4到8之间'
					},
					regexp: {
                    	regexp :  /^[0-9a-zA-Z:,]*$/,
                       message : '必须是数字、字母、数字字母'
         		   }
               },
               showPassword: {
                   validators: {
                	   regexp: {
                       	regexp :  /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!_~.@#$%^&*?])[a-zA-Z!_~.@#$%^&*?0-9]{8,16}$/g,
   						message : '密码8-16位，包含数字、字母、特殊符号'
            		   }
                   }
               },
               showRepeatPassword: {
            	   validators: {
                       identical: {
                           field: 'showPassword',
                           message: '两次密码不相同'
                       }
                   }
               },
               chineseName: {
            	   validators: {
            		   notEmpty: {
            			   message: '中文名称不能为空'
            		   },stringLength : {
	   						min : 2,
	   						max : 8,
	   						message : '中文名称长度必须在2到8之间'
   						}
            	   }
               },
               title: {
                   validators: {
                       notEmpty: {
                           message: '角色不能为空'
                       }
                   }
               }
           }
    });
}
//页面调整的大小
function tableHeight() {
    return $(window).height() - 50;
}
//传递的参数
function queryParams(params){
	return{
		pageSize: params.limit, //页面大小
		pageIndex:params.pageNumber, //页码
		sortOrder:params.order, //排序升序or倒序
		sort:params.sort,	//排序字段
		searchManagerName:$("#searchManagerName").val(),//用户名
		searchTitle:$("#searchTitle").val(),//权限类别
		searchIsAllCustomer:$("#searchIsAllCustomer").val(),
		searchIsAllChannel:$("#searchIsAllChannel").val()
	}
}
function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + row.id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('" + row.id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"AuthorizedById('" + row.id + "')\" title='权限分配'><span class='glyphicon glyphicon-edit'></span></a>";

    return result;
}
function EditViewById(id){
	if(passed('修改')){
		$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function() {
			$('.tableBody').removeClass('animated slideOutLeft').css('display',
					'none');
		}, 500)
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		//回显数据
		$.post(
				"findUcenterManagerById.action",
				{id:id},
				function (result) {
					$('#editId').val(result.id);
					$('#editManagerName').val(result.managerName);
					$('#editChineseName').val(result.chineseName);
					$('#editTitle').val(result.title);
					$("#editTitle").find("option[value='" + result.title + "']").attr('selected', true);
					$("#editIsAllCustomer").val(result.isAllCustomer);
					$("#editIsAllCustomer").find("option[value='" + result.isAllCustomer + "']").attr('selected', true);
					$("#editIsAllChannel").val(result.isAllChannel);
					$("#editIsAllChannel").find("option[value='" + result.isAllChannel + "']").attr('selected', true);
				},
				"json"
		);
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
}
function DeleteByIds(id){
	mark=true;
	if(passed('删除')){
		$('.popup_de .show_msg').text('确定要删除该用户吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display','inline-block');
		$('#btn_submit').one('click',function(){
			if(mark){
				$.post("deleteUcenterManager.action",
						{id:id},
						function(data){
							if(data.result>0){
								$('.popup_de .show_msg').text('删除成功！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function(){
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url:'findUcenterManager.action'});
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
function AuthorizedById(id) {
	if(passed('权限分配')){
		$("#addUserModal").modal();
		$.post(
	    		"findUcenterAndMenu.action",
	    		{'id':id},
	    		function (result) {
	    			$("#modalBody").empty();
					var ucenterMenu=result.ucenterMenu;
					var managerMenu=result.managerMenu;
	    			//展示菜单
					for(var i in ucenterMenu){
	    				if(ucenterMenu[i].parentId==0){
	    					$("#modalBody").append("<label><input type='checkbox' onclick='parMenu("+ucenterMenu[i].id+")' value='"+ucenterMenu[i].id+"'>"+ucenterMenu[i].title+"</label>");
	        					for(var j in ucenterMenu){
	        						if(ucenterMenu[j].parentId==ucenterMenu[i].id){
	        							$("#modalBody").append("<input type='checkbox' data='"+ucenterMenu[j].parentId+"' onclick='subMenu("+ucenterMenu[j].id+","+ucenterMenu[j].parentId+")' value='"+ucenterMenu[j].id+"'>"+ucenterMenu[j].title+"<span  data-toggle='modal'  data-target='#menu' onclick='menu("+ucenterMenu[j].id+","+id+")' class='glyphicon glyphicon-pencil'></span>&nbsp;&nbsp;&nbsp;&nbsp;");
	        						}
	        					}
	    					$("#modalBody").append("<br>");
	    				}
	    			}
	    			//回显菜单
	    			$("#addUserModal input[type='checkbox']").each(function () {
						for(var m in managerMenu){
							if(managerMenu[m].ucenterMenuId==$(this).val()){
								$(this).attr('checked',true);
							}
						}
					});
	    			$("#managerId").val(id);
	    			managerMenuArg=result.managerMenu;
	    			menuArg=result.ucenterMenu;
				},
				"json"
	    );
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
}
$(function() {
	//点击添加按钮
	$("#btn_add").click(function() {
		if(passed('新增')){
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function(){
				$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
			},500)
			$('.addBody').css('display','block');
			$('.addBody').addClass('animated slideInRight');
			
		}else{
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
		}
		
	});
	//点击添加菜单页面返回按钮
	 $('#add_backBtn').click(function() {
		 $("#addForm")[0].reset();
		 $('.addBody').addClass('animated slideOutLeft');
    	setTimeout(function(){
			$('.addBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
    	$('.tableBody').css('display','block').addClass('animated slideInRight');  
    	$("#addForm").data('bootstrapValidator').destroy();
        $('#addForm').data('bootstrapValidator', null);
        addValidator();
        $("#add_saveBtn").attr("disabled",false);
	   });
	 //点击菜单页面提交按钮
	 $('#add_saveBtn').click(function() {
		 //点击保存时触发表单验证
	     $('#addForm').bootstrapValidator('validate');
	     $("#managerPassword").val($.md5($("#showPassword").val()));
	       //如果表单验证正确，则请求后台添加用户
	      if($("#addForm").data('bootstrapValidator').isValid()){
	    	  $("#add_saveBtn").attr("disabled",true);
	    	   var _info = $('#addForm').serialize();
	    	   data= decodeURIComponent(_info,true);
	    	   $.post(
					"addUcenterManager.action",
					data,
					function(data){
						//后台返回添加成功
						if(data.result>0){
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function(){
								$('.addBody').removeClass('animated slideOutLeft').css('display','none');
							},500);
							$('.tableBody').css('display','block').addClass('animated slideInRight');
							$("#addForm").data('bootstrapValidator').destroy();
					        $('#addForm').data('bootstrapValidator', null);
					        addValidator();
					        $('#addForm')[0].reset();
							$('#mytab').bootstrapTable('refresh',{url:'findUcenterManager.action'});
						}else{
							$('.popup_de .show_msg').text('新增失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function(){
								$('.popup_de').removeClass('bbox');
							})
						}
						$("#add_saveBtn").attr("disabled",false);
					}
	    	  ) 
	      }
	    });
	 //根据复选框批量删除
	 $('#btn_delete').click(function(){
		 mark=true;
		 if(passed('删除')){
	    	var dataArr=$('#mytab').bootstrapTable('getSelections');
	    	$('.popup_de .show_msg').text('确定要删除该用户吗?');
	    	$('.popup_de .btn_cancel').css('display','inline-block');
	    	$('.popup_de').addClass('bbox');
	    	$('.popup_de .btn_submit').one('click',function(){
	    		if(mark){
	    			var ID=[];
	    			for(var i=0;i<dataArr.length;i++){
	    				ID[i]=dataArr[i].id;
	    			}
	    			if(ID.length>0){
	    				$.post("deleteUcenterManagerBatch.action",
	    						{ids:ID},
	    						function(data){
	    							if(data.result>0){
	    								$('.popup_de .show_msg').text('删除成功！');
	    								$('.popup_de .btn_cancel').css('display','none');
	    								$('.popup_de').addClass('bbox');
	    								$('.popup_de .btn_submit').one('click',function(){
	    									$('.popup_de').removeClass('bbox');
	    								})
	    								$('#mytab').bootstrapTable('refresh',{url:'findUcenterManager.action'});
	    							}else{
	    								$('.popup_de .show_msg').text('删除失败！');
	    								$('.popup_de .btn_cancel').css('display','none');
	    								$('.popup_de').addClass('bbox');
	    								$('.popup_de .btn_submit').one('click',function(){
	    									$('.popup_de').removeClass('bbox');
	    								})
	    								$('#mytab').bootstrapTable('refresh',{url:'findUcenterManager.action'});
	    							}
	    						});
	    			}else{
	    				$('.popup_de .show_msg').text('请选择要删除的菜单！');
	    				$('.popup_de').addClass('bbox');
	    				$('.popup_de .btn_submit').one('click',function(){
	    					$('.popup_de').removeClass('bbox');
	    				})
	    			}
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
	 })
	    //弹出框取消按钮事件
	 $('.popup_de .btn_cancel').click(function(){
		   $('.popup_de').removeClass('bbox');
	 })
	    //弹出框关闭按钮事件
	 $('.popup_de .popup_close').click(function(){
		   $('.popup_de').removeClass('bbox');
	 })
		//删除按钮与修改按钮的出现与消失
	 $('.table').change(function(){
		var dataArr=$('#mytab .selected');
		if(dataArr.length>=1){
			$('#btn_delete').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		}else{
			$('#btn_delete').addClass('fadeOutRight');
			setTimeout(function(){
				$('#btn_delete').css('display','none');
			},400);	
		}
	});
	$("[name='btSelectAll']").click(function(){
		var dataArr=$('#mytab .selected');
		if(dataArr.length>=1){
			$('#btn_delete').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		}else{
			$('#btn_delete').addClass('fadeOutRight');
			setTimeout(function(){
				$('#btn_delete').css('display','none');
			},400);	
		}
	});
	//修改页面回退按钮事件
	$('#edit_backBtn').click(function(){
		$('.changeBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
		$('.tableBody').css('display','block').addClass('animated slideInRight');
		$("#editForm").data('bootstrapValidator').destroy();
        $('#editForm').data('bootstrapValidator', null);
        updateValidator();
	})
	//修改页面保存按钮事件
	$('#edit_saveBtn').click(function(){
		$('#editForm').bootstrapValidator('validate');
		if($("#editForm").data('bootstrapValidator').isValid()){	
		$("#edit_saveBtn").attr("disabled",true);
		var password=$("#editShowPassword").val();
		if(password==""||password==null||password===undefined){
		}else{
			$("#editManagerPassword").val($.md5(password));
		}
		var _info = $('#editForm').serialize();
			data= decodeURIComponent(_info,true);
			$.post("updateUcenterManager.action",
				data,
				function(data){
					if(data.result>0){
						//隐藏修改与删除按钮
						$('#btn_delete').css('display','none');
						$('#btn_edit').css('display','none');
						//回退到人员管理主页
						$('.changeBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
						},500)
				    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
				    	//刷新人员管理主页
				    	$('#mytab').bootstrapTable('refresh',{url:'findUcenterManager.action'});
				    	//修改页面表单重置
				    	$("#editForm").data('bootstrapValidator').destroy();
				        $('#editForm').data('bootstrapValidator', null);
				        updateValidator();
					}else{
						$('.popup_de .show_msg').text('修改失败！');
						$('.popup_de .btn_cancel').css('display','none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click',function(){
							$('.popup_de').removeClass('bbox');
						})
					}
					$("#edit_saveBtn").attr("disabled",false);
			 })
		}
	})
	//菜单按钮保存
	$("#menuButton").click(function () {
		var button="";
		$("#menuBody input[type='checkbox']:checked").each(function (index) {
			if(index==0){
				button+=$(this).val();
			}else{
				button+=","+$(this).val();
			}
		});
		var menuId=$("#menuId").val();
		var managerId=$("#managerId").val();
			var flag=false;
			for(var i in managerMenuArg){
				if(managerMenuArg[i].ucenterMenuId==menuId){
					flag=true;
					managerMenuArg[i].menuButtons=button;
					break;
				}
			}
			if(!flag){
				managerMenuArg.push(new managerMenu(0,managerId,menuId,button));
			}
			$("[value='"+menuId+"']").attr("checked",'checked');
	});
	 	
})
	//异步追加菜单按钮
	function menu(val,id) {
		$("#menuBody").empty();
		$("#menuId").val(val);
		for(var i in menuArg){
			if(menuArg[i].id==val){
				var menubuttons=menuArg[i].buttons;
				if(menubuttons=="" || menubuttons == null || menubuttons == undefined){
				}else{
					var menubutton=menubuttons.split(",");
					$("#menuBody").append("<div class='form-group'>");
						for(var i in menubutton){
							$("#menuBody").append("<label value='0'><input type='checkbox' value='"+menubutton[i]+"'>"+menubutton[i]+"</label>");
						}
					$("#menuBody").append("</div>");
					break;

				}
			}
		}	
		for(var m in managerMenuArg){
			if(managerMenuArg[m].ucenterMenuId==val){
				var managerbuttons=managerMenuArg[m].menuButtons;
				if(managerbuttons!=null){
					var managerbutton=managerbuttons.split(',');
					$("#menu input[type='checkbox']").each(function () {
						for(var n in managerbutton){
							if(managerbutton[n]==$(this).val()){
								$(this).prop('checked',true);
							}
						}
					});
					break;
				}
			}
		}
	
	}
	//点击子菜单
	function subMenu(id,parentId) {
		if($("#addUserModal").find("input[value='"+id+"']").is(":checked")){
			$("#addUserModal").find("input[value='"+parentId+"']").prop('checked',true);
			var managerId=$("#managerId").val();
			var flag1=false;
			var flag2=false;
			for(var i in managerMenuArg){
				if(managerMenuArg[i].ucenterMenuId==id){
					flag1=true;
					break;
				}
				if(managerMenuArg[i].ucenterMenuId==parentId){
					flag2=true;
					break;
				}
			}
			if(!flag1){
				managerMenuArg.push(new managerMenu(0,managerId,id,""));
			}
			if(!flag2){
				managerMenuArg.push(new managerMenu(0,managerId,parentId,""));
			}
		}else{
			for(var i in managerMenuArg){
				if(managerMenuArg[i].ucenterMenuId==id){
					managerMenuArg=removeWithoutCopy(managerMenuArg,managerMenuArg[i]);
					break;
				}
			}
		}
	}
	//点击父菜单
	function parMenu(id) {
		var managerId=$("#managerId").val();
		if(!$("#addUserModal").find("input[value='"+id+"']").is(":checked")){
			$("#addUserModal").find("input[data='"+id+"']").prop('checked',false);
			var flag1=false;
			for(var i in managerMenuArg){
				if(managerMenuArg[i].ucenterMenuId==id){
					managerMenuArg=removeWithoutCopy(managerMenuArg,managerMenuArg[i]);
					break;
				}
			}
			$("#addUserModal").find("input[data='"+id+"']").each(function () {
				for(var i in managerMenuArg){
					if(managerMenuArg[i].ucenterMenuId==$(this).val()){
						managerMenuArg=removeWithoutCopy(managerMenuArg,managerMenuArg[i]);
						break;
					}
				}
			});
		}else{
			$("#addUserModal").find("input[data='"+id+"']").prop('checked',true);
			var flag1=false;
			for(var i in managerMenuArg){
				if(managerMenuArg[i].ucenterMenuId==id){
					flag1=true;
					break;
				}
			}
			if(!flag1){
				managerMenuArg.push(new managerMenu(0,managerId,id,""));
			}
			$("#addUserModal").find("input[data='"+id+"']").each(function () {
				managerMenuArg.push(new managerMenu(0,managerId,$(this).val(),""));
			});
		}
	}
	//授权提交
	function check_form() {
		mark=true;
		if(managerMenuArg.length>0){
			$('.popup_de .show_msg').text('确定保存该数据吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('#btn_submit').one('click',function(){
			if(mark) {
				$.post("authorized.action", {
					'managerMenuArg' : JSON.stringify(managerMenuArg)
				}, function(result) {
					if (result.result > 0) {
						$('.popup_de .show_msg').text('权限分配成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							location.reload();
						})
					} else {
						$('.popup_de .show_msg').text('权限分配失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
							location.reload();
						})
					}
					mark=false;
				}, "json");
			}
		})
		}else{
			$('.popup_de .show_msg').text('请选择要分配的权限');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
		}
		
		return false;
	}
	//删除数组中的元素
	function removeWithoutCopy(arr, item) {  
	     for(var i = 0; i < arr.length; i++){  
	         if(arr[i] == item){  
	             //splice方法会改变数组长度，当减掉一个元素后，后面的元素都会前移，因此需要相应减少i的值  
	             arr.splice(i,1);  
	             i--;  
	         }  
	     }  
	     return arr;  
	} 
	//按钮是否通过
	function passed(button) {
		flag=false;
		var buttons=$("#buttons").val();
		if(buttons == "" || buttons == null || buttons == undefined){
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
	$(document).ajaxComplete(function(event, xhr, settings) {
	    //从http头信息取出 在filter定义的sessionstatus，判断是否是 timeout
	    if(xhr.getResponseHeader("sessionstatus")=="timeout"){ 
	        //从http头信息取出登录的url ＝ loginPath
	        if(xhr.getResponseHeader("loginPath")){
	            alert("会话过期，请重新登陆!");
	            window.location.replace(xhr.getResponseHeader("loginPath"));  
	        }else{  
	            alert("请求超时请重新登陆 !");  
	        }  
	    }  
	}); 