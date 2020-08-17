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
     	url:"findAccount.action",//请求后台url
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
         		visible:false
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
         		field:'appName'
         	},
            {
    			title : '销售',
    			field : 'sales'
    		}, 
    		{
    			title : '客服',
    			field : 'saleAfter'
    		},
         	{
         		title:'计费类型',
         		field:'chargeBy',
         		formatter:function(value,row,index){
         			return value=="delivrd"?'成功计费':value=="delivrdunknown"?'成功+未知':'提交计费';
         		}
         	},{
         		title:'结算类型',
         		field:'payment',
         		formatter:function(value,row,index){
         			return value=="advance"?'预付费':'后付费';
         		}
         	},
         	{
         		title:'产品',
         		field:'productId',
         		formatter : changeContent,
         	},
         	{
         		title:'帐户状态',
         		field:'appStatus',
    			formatter : function(value, row, index) {
    				if (value == 'normal') {
    					return "正常";
    				} else if (value == 'pause') {
    					return "暂停";
    				} else if(value == 'deleted') {
    					return "删除";
    				}
    			}
         	},
         	{
         		title:'账户扩展',
         		field:'appExtSrc'
         	},
         	{
         		title:'单价',
         		field:'price'
         	},
         	{
         		title:'发送量',
         		field:'sentCount'
         	},
         	{
         		title:'帐户余额',
         		field:'balance',
    			formatter : function(value, row, index) {
    				return row.limitCount-row.sentCount-row.payCount;
    			}
         	},
         	{
         		title:'创建时间',
         		field:'createTime',
         		width: 200,
         		formatter: function(value,row,index){
         			return changeDateFormat(value);
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
     });
    addValidator();
	updateValidator();
	chargeValidator();
});

function addValidator() {
	//菜单添加表单验证
    $('#addForm').bootstrapValidator({
    	group: '.rowGroup',
    	feedbackIcons: {
               valid: 'glyphicon glyphicon-ok',
               invalid: 'glyphicon glyphicon-remove',
               validating: 'glyphicon glyphicon-refresh'
           },
           fields: {
        	   appName: {
                   validators: {
                       notEmpty: {
                           message:'账户名称不能为空'
                       },
                       remote:{
                    	   url:"validatorAccount.action",
                    	   type:"POST",
                    	   delay:500,
                    	   message:"帐户名称已存在"
                       },
                       stringLength :{
                     	   min:6,
                     	   max:6,
                     	   message:"账户名称长度为6位"
                        },
                        regexp: {
                        	regexp :  /^[0-9a-zA-Z:,]*$/,
                           message : '必须是数字、字母、数字字母'
             		   }
                   }
               },
               productId: {
            	   validators:{
            		   notEmpty:{
            			   message:'请选择产品'
            		   }
            	   }
               },
               serviceTimeBegin: {
            	   validators:{
            		   notEmpty:{
            			   message:'请输入开始时间'
            		   },
            		   regexp: {
    	    			   regexp: /^[0-9]+$/,
    	    			   message: '必需输入数字' 
    	    		   },
    	    		   stringLength :{
                     	   max:4,
                     	   message:"最大长度不超过4位"
                        },
            	   }
               },
               serviceTimeEnd: {
            	   validators:{
            		   notEmpty:{
            			   message:'请输入结束时间'
            		   },
            		   regexp: {
    	    			   regexp: /^[0-9]+$/,
    	    			   message: '必需输入数字' 
    	    		   },
    	    		   stringLength :{
                     	   max:4,
                     	   message:"最大长度不超过4位"
                        },
            	   }
               },
               minLimitCount: {
    	    	   validators: {
    	    		   regexp: {
    	    			   regexp: /^[0-9]+$/,
    	    			   message: '必需输入数字' 
    	    		   },
    	    		   notEmpty:{
    	    			   message:'不能为空'
    	    		   }
    	    	   }
               },
               limitCount:{
    	    	   validators: {
    	    		   regexp: {
    	    			   regexp: /^[0-9]+$/,
    	    			   message: '必需输入数字' 
    	    		   },
    	    		   notEmpty:{
    	    			   message:'不能为空'
    	    		   }
    	    	   }
               },
               dayLimitCount: {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               monthLimitCount: {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               dayLimitContentCount: {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               price : {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9.]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               contAuditCount:{
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               sourceSegment:{
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9]+$/,
            			   message: '必需输入数字'
            		   }
            	   }
               },
               moSourceSegment:{
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9]+$/,
            			   message: '必需输入数字'
            		   }
            	   }
               },
               priceUnicom : {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9.]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               priceTelecom : {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9.]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               priceCmcc : {
            	   validators: {
            		   regexp: {
            			   regexp: /^[0-9.]+$/,
            			   message: '必需输入数字'
            		   },
            		   notEmpty:{
            			   message:'不能为空'
            		   }
            	   }
               },
               maxConnection:{
            	   validators: {
            		   notEmpty:{
            			   message:'不能为空'
            		   },
            		   regexp: {
            			   regexp: /^([5-9]|[1-9][0-9]|100)$/,
            			   message: '范围在5-100之间'
            		   }
            	   }
               },
               fakeCompanyId: {
    	    	   validators: {
                       notEmpty: {
                           message:'请选择公司'
                       }
                   }
               },
               defaultSign:{
    	    	   validators: {
                       notEmpty: {
                           message:'默认签名不能为空'
                       },
                       regexp : {
                    	   	regexp : /^[^【】[\]]+$/,
	   						message : '签名不能包含括号'
                       }
                   }
               },
               rptSyncAddress:{
            	   validators:{
            		   stringLength :{
                     	   max:100,
                     	   message:"最大长度不超过100位"
                        },
            		   callback:{
            			   message:'当为推送时,状态报告地址不能为空',
                           callback:function(value, validator,$field){
                        	   var rptSyncModel = $('#rptSyncModel').val();
                        	   if(rptSyncModel=='push'){
                        		   if(value==null || value.replace(/(^\s*)|(\s*$)/g,"")==''){
                        			   return false;
                        		   }else{
                        			   return true;
                        		   }
                        	   }else{
                        		   return true;
                        	   }
                           }
            		   }
            	   }
               },
               moSyncAddress:{
            	   validators:{
            		   stringLength :{
                     	   max:100,
                     	   message:"最大长度不超过100位"
                        },
            		   callback:{
            			   message:'当上行为推送时,上行地址不能为空',
                           callback:function(value, validator,$field){
                        	   var moSyncModel = $('#moSyncModel').val();
                        	   if(moSyncModel=='push'){
                        		   if(value==null || value.replace(/(^\s*)|(\s*$)/g,"")==''){
                        			   return false;
                        		   }else{
                        			   return true;
                        		   }
                        	   }else{
                        		   return true;
                        	   }
                           }
            		   }
            	   }
               },
               appExtSrc:{
            	   validators: {
            		   regexp: {
            			   regexp: /^[1-9]{1}[0-9]*$/,
            			   message: '必需输入数字'
            		   },
		               stringLength :{
		             	   max:10,
		             	   message:"扩展不能超过10位"
		               },
		               notEmpty:{
            			   message:'不能为空'
            		   },
            		   remote:{
                    	   url:"validatorAccountExt.action",
                    	   type:"POST",
                    	   delay:500,
                    	   message:"帐户扩展已存在"
                       }
            	   }
               },
               blackLevel:{
            	   validators:{
            		   regexp: {
            			   regexp: /^([0-9]+,)*[0-9]+$/,
            			   message: '必需输入数字,用英文逗号隔开;开头和结尾不能是英文逗号不能有连续英文逗号'
            		   },
            		   callback:{
            			   message:"普通黑与优化黑互斥",
            			   callback:function(value, validator,$field){
            				   var blackArr=value.split(",");
            				   for(var i in blackArr){
            					   	if(blackArr[i]=="3"){
            					   		for(var j in blackArr){
            					   			if(blackArr[j]=="4"){
            					   				return false;
            					   			}
            					   		}
            					   	}
	            		    	}
            				   return true;
            			   }
            		   }
            	   }
               },
               authIp:{
            	   validators:{
            		   callback:{
            			   message:"IP格式不正确",
            			   callback:function(value, validator,$field){
	            				var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
	            				var regNum = /^([1-9]|[1-2][0-9]|3[0-2])$/
	            				if(value == null || value =='' || value == undefined){
	            					return true;
	            				}
	            				var authIps=value.split(",");
	            				var mark=true;
	            		    	for(var i in authIps){
	            		    		var index = authIps[i].indexOf("/");
	            		    		if(index >= 7){
	            		    			if(!regNum.test(authIps[i].slice(index+1))){
		            		    			mark=false;
	            		    			}
	            		    			authIps[i]=authIps[i].slice(0,index);
	            		    		}
	            		    		if(!reg.test(authIps[i])){
	            		    			mark=false;
	            		    		}
	            		    	}
	            		    	return mark;
            			   }
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

function updateValidator() {
	//菜单修改表单验证
    $('#editForm').bootstrapValidator({
    	group: '.rowGroup',   	
    	feedbackIcons: {
	               valid: 'glyphicon glyphicon-ok',
	               invalid: 'glyphicon glyphicon-remove',
	               validating: 'glyphicon glyphicon-refresh'
	           },
	           fields: {
	               minLimitCount: {
	    	    	   validators: {
	    	    		   regexp: {
	    	    			   regexp: /^[0-9]+$/,
	    	    			   message: '必需输入数字' 
	    	    		   }
	    	    	   }
	               },
	               serviceTimeBegin: {
	            	   validators:{
	            		   notEmpty:{
	            			   message:'请输入开始时间'
	            		   },
	            		   regexp: {
	    	    			   regexp: /^[0-9]+$/,
	    	    			   message: '必需输入数字' 
	    	    		   },
	    	    		   stringLength :{
	                     	   max:4,
	                     	   message:"最大长度不超过4位"
	                        },
	            	   }
	               },
	               serviceTimeEnd: {
	            	   validators:{
	            		   notEmpty:{
	            			   message:'请输入结束时间'
	            		   },
	            		   regexp: {
	    	    			   regexp: /^[0-9]+$/,
	    	    			   message: '必需输入数字' 
	    	    		   },
	    	    		   stringLength :{
	                     	   max:4,
	                     	   message:"最大长度不超过4位"
	                        },
	            	   }
	               },
	               limitCount:{
	    	    	   validators: {
	    	    		   regexp: {
	    	    			   regexp: /^[0-9]+$/,
	    	    			   message: '必需输入数字' 
	    	    		   }
	    	    	   }
	               },
	               companyId:{
	            	   validators:{
	            		   notEmpty:{
	            			   message:'请选择公司'
	            		   }
	            	   }
	               },
	               productId: {
	            	   validators:{
	            		   notEmpty:{
	            			   message:'请选择产品'
	            		   }
	            	   }
	               },
	               defaultSign:{
	            	   validators:{
	            		   notEmpty:{
	            			   message:'默认签名不能为空!'
	            		   },
	            		   regexp : {
	                    	   	regexp : /^[^【】[\]]+$/,
		   						message : '签名不能包含括号'
	                       }
	            	   }
	               },
	               price : {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9.]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               contAuditCount:{
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               sourceSegment:{
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               moSourceSegment:{
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               maxConnection:{
	            	   validators: {
	            		   notEmpty:{
	            			   message:'不能为空'
	            		   },
	            		   regexp: {
	            			   regexp: /^([5-9]|[1-9][0-9]|100)$/,
	            			   message: '范围在5-100之间'
	            		   }
	            	   }
	               },
	               priceUnicom : {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9.]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               priceTelecom : {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9.]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               priceCmcc : {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9.]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               dayLimitCount: {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               monthLimitCount: {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               dayLimitContentCount: {
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[0-9]+$/,
	            			   message: '必需输入数字'
	            		   }
	            	   }
	               },
	               rptSyncAddress:{
	            	   validators:{
	            		   stringLength :{
	                     	   max:100,
	                     	   message:"最大长度不超过100位"
	                        },
	            		   callback:{
	            			   message:'当为推送时,状态报告地址不能为空',
	                           callback:function(value, validator,$field){
	                        	   var rptSyncModel = $('#editRptSyncModel').val();
	                        	   if(rptSyncModel=='push'){
	                        		   if(value==null || value.replace(/(^\s*)|(\s*$)/g,"")==''){
	                        			   return false;
	                        		   }else{
	                        			   return true;
	                        		   }
	                        	   }else{
	                        		   return true;
	                        	   }
	                           }
	            		   }
	            	   }
	               },
	               moSyncAddress:{
	            	   validators:{
	            		   stringLength :{
	                     	   max:100,
	                     	   message:"最大长度不超过100位"
	                        },
	            		   callback:{
	            			   message:'当上行为推送时,上行地址不能为空',
	                           callback:function(value, validator,$field){
	                        	   var moSyncModel = $('#editMoSyncModel').val();
	                        	   if(moSyncModel=='push'){
	                        		   if(value==null || value.replace(/(^\s*)|(\s*$)/g,"")==''){
	                        			   return false;
	                        		   }else{
	                        			   return true;
	                        		   }
	                        	   }else{
	                        		   return true;
	                        	   }
	                           }
	            		   }
	            	   }
	               },
	               appExtSrc:{
	            	   validators: {
	            		   regexp: {
	            			   regexp: /^[1-9]{1}[0-9]*$/,
	            			   message: '必需输入不能0开头的数字'
	            		   },
			               stringLength :{
			             	   max:10,
			             	   message:"扩展不能超过10位"
			               },
			               notEmpty:{
	            			   message:'不能为空'
	            		   },
			               callback:{
		         			   message:'帐户扩展已存在',
		                        callback:function(value, validator,$field){
		                     	   var oldAppExtSrc = $("#oldAppExtSrc").val();
		                     	   var appExtSrc = $("#editAppExtSrc").val();
		                     	   var appStatus =$("#editAppStatus").val();
		                     	   
		                     	   if(oldAppExtSrc==appExtSrc || appExtSrc =='' ){
		                     			return true;
		                     	   }else{
		                     		   var flag=true;
		                     		   $.ajax({
		                     			  url:'validatorAccountExt.action',
		                     				data:{
		                     					oldAppExtSrc:oldAppExtSrc,
		                     					appExtSrc:appExtSrc
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
	               appExtras:{
	            	   validators: {
			               stringLength :{
			             	   max:250,
			             	   message:"账户扩展不能超过250位"
			               }
	            	   }
	               },
	               blackLevel:{
	            	   validators:{
	            		   regexp: {
	            			   regexp: /^([0-9]+,)*[0-9]+$/,
	            			   message: '必需输入数字,用英文逗号隔开;开头和结尾不能是英文逗号不能有连续英文逗号'
	            		   },
	            		   callback:{
	            			   message:"普通黑与优化黑互斥",
	            			   callback:function(value, validator,$field){
	            				   var blackArr=value.split(",");
	            				   for(var i in blackArr){
	            					   	if(blackArr[i]=="3"){
	            					   		for(var j in blackArr){
	            					   			if(blackArr[j]=="4"){
	            					   				return false;
	            					   			}
	            					   		}
	            					   	}
		            		    	}
	            				   return true;
	            			   }
	            		   }
	            	   }
	               },
	               authIp:{
	            	   validators:{
	            		   callback:{
	            			   message:"IP格式不正确",
	            			   callback:function(value, validator,$field){
		            				var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
		            				var regNum = /^([1-9]|[1-2][0-9]|3[0-2])$/
		            				if(value == null || value =='' || value == undefined){
		            					return true;
		            				}
	            					var authIps=value.split(",");
		            				var mark=true;
		            		    	for(var i in authIps){
		            		    		var index = authIps[i].indexOf("/");
		            		    		if(index >= 7){
		            		    			if(!regNum.test(authIps[i].slice(index+1))){
			            		    			mark=false;
		            		    			}
		            		    			authIps[i]=authIps[i].slice(0,index);
		            		    		}
		            		    		if(!reg.test(authIps[i])){
		            		    			mark=false;
		            		    		}
		            		    	}
		            		    	return mark;
	            			   }
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

function chargeValidator() {
	//充值表单验证
    $('#chargeForm').bootstrapValidator({
    	feedbackIcons: {
	               valid: 'glyphicon glyphicon-ok',
	               invalid: 'glyphicon glyphicon-remove',
	               validating: 'glyphicon glyphicon-refresh'
	           },
	           fields: {
	        	   changeNum: {
	    	    	   validators: {
	            		   notEmpty:{
	            			   message:'请输入充值条数'
	            		   },
	    	    		   regexp: {
	    	    			   regexp: /^[0-9]+$/,
	    	    			   message: '必需输入数字' 
	    	    		   },
	    	    		   remote : {
								url : "validatorChargeNum.action",
								type : "POST",
								delay : 500,
								data : {
									changeType : function(){return $("#changeType").val();},
									changeNum : function(){return $("#changeNum").val();},
									appId : function(){return $("#cAppId").val();}
								},
								message : "余额不足"
							}
	    	    	   }
	               }
	           }
     });
    
    $("#search_btn").click(function () {
       	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#search_companyId").val('');
    	$("#search_appId").val('');
    	$("#search_appStatus").val('');
    	$("#searchProductId").val('');
    	$("#searchChannelId").val('');
    	$("#searchAppExtSrc").val('');
    	$("#searchSales").val('');
    	$("#searchSaleAfter").val('');
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1,limit:30,sort:'',order:'asc'});
    });
}

function tableHeight() {
    return $(window).height()-50;
}

function queryParams(params){
	return{
		pageSize: params.limit, //页面大小
		pageIndex:params.pageNumber, //页码
		companyId:$("#search_companyId").val(),
		appId:$("#search_appId").val(),
		appStatus:$("#search_appStatus").val(),
		product:$("#searchProductId").val(),
		channelId:$("#searchChannelId").val(),
		appExtSrc:$("#searchAppExtSrc").val(),
		sales:$("#searchSales").val(),
		saleAfter:$("#searchSaleAfter").val()
	}
}

function changeContent(value, row, index) {
		var result = "<a href='javascript:;' data-toggle='modal'  data-target='#productDetails' onclick=\"findProductDetails('" + value + "')\" title='详情'>" + value+":"+row.productName + "</a>";
		return result;
}

function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs blue' data-toggle='modal'  data-target='#Details' onclick=\"findDetails('" + row.id + "')\" title='详情'><span class='glyphicon glyphicon-edit'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + row.id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    //result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('" + row.id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' data-toggle='modal'  data-target='#chargeModal' onclick=\"charge('" + row.id + "')\" title='充值'><span class='glyphicon glyphicon-euro'></span></a>";
    return result;
}

/**格式化时间**/
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
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

/**删除单条数据**/
function DeleteByIds(id) {
	mark = true;
	if(passed('删除')){
		var num=0;
		$.ajax({
			url:"findAccountSub.action",
			data:{id:id},
			dataType:"json",
			async:false, 
			success:function (obj){
				num=obj.length;
			}
		});
		if(num>0){
			$('.popup_de .show_msg').text('账户存在子账户，请先删除子账户');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		$('.popup_de .show_msg').text('确定要删除删用户吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			if(mark){
				$.post(
						"deleteAccountById.action",
						{id:id},
						function(data){
							if(data.result>0){
								$('.popup_de .show_msg').text('删除成功！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function(){
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
							}else{
								$('.popup_de .show_msg').text('删除失败！');
								$('.popup_de .btn_cancel').css('display','none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function(){
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
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

function EditViewById(id) {
	if(passed('修改')){
		var data="";
		$.post(
				"findAccountById.action",
				{id:id},
				function (result) {
					data=result;
					$('.tableBody').addClass('animated slideOutLeft');
					setTimeout(function(){
						$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
					},500)
					$('.changeBody').css('display','block');
					$('.changeBody').addClass('animated slideInRight');
//					$('#editForm')[0].reset();
					$('#editId').val(result.id);
					$('#editAccName').val(result.appName);
					$('#editAppStatus').val(result.appStatus);
					$("#editServiceTimeBegin").val(result.serviceTimeBegin);
					$("#editServiceTimeEnd").val(result.serviceTimeEnd);
					$('#editRptSyncModel').val(result.rptSyncModel);
					$('#editRptSyncAddress').val(result.rptSyncAddress);
					$('#editMoSyncModel').val(result.moSyncModel);
					$('#editMoSyncAddress').val(result.moSyncAddress);
					$('#editIsMinLimit').val(result.isMinLimit);
					$('#editMinLimitCount').val(result.minLimitCount);
					$('#editIsDayLimit').val(result.isDayLimit);
					$('#editDayLimitCount').val(result.dayLimitCount);
					$('#editIsMonthLimit').val(result.isMonthLimit);
					$('#editMonthLimitCount').val(result.monthLimitCount);
					$('#editIsDayContentLimit').val(result.isDayContentLimit);
					$('#editDayLimitContentCount').val(result.dayLimitContentCount);
					$('#editPriority').val(result.priority);
					$('#editSkipMustWords').val(result.skipMustWords);
					$('#editCheckWordsType').val(result.checkWordsType);
					$('#editIsModel').val(result.isModel);
					$('#editIsTemplate').val(result.isTemplate);
					$("#editBlackLevel").val(result.blackLevel);
					$('#editAppRoles').val(result.appRoles);
					$('#editAppExtSrc').val(result.appExtSrc);
					$('#oldAppExtSrc').val(result.appExtSrc);
					$('#editDefaultSign').val(result.defaultSign);
					$('#editIsExt').val(result.isExt);
					$('#editSourceSegment').val(result.sourceSegment);
					$('#editTestModel').val(result.testModel);
					$('#editMaxConnection').val(result.maxConnection);
					$('#editIsContAudit').val(result.isContAudit);
					$('#editContAuditCount').val(result.contAuditCount);
					$('#editPrice').val(result.price);
					$('#editPriceUnicom').val(result.priceUnicom);
					$('#editPriceCmcc').val(result.priceCmcc);
					$('#editPriceTelecom').val(result.priceTelecom);
					$('#editDefaultSign').val(result.defaultSign);
					$('#editTestModel').val(result.testModel);
					$('#editIsShuntPhone').val(result.isShuntPhone);
					$("#editMoFlag").val(result.moFlag);
					$('#editCompanyId').selectpicker('val',result.companyId);
					if(result.appParentId != '0'){
						$('#editCompanyId').attr("disabled",true);
						$("#editSpan").html("子账户不能修改公司");
					}
					$('#editProductId').selectpicker('val',result.productId);
					$("#editPayment").val(result.payment);
					$("#editChargeBy").val(result.chargeBy);
					$("#editAuthIp").val(result.authIp);
					$("#editIsDayLimitCheck").val(result.isDayLimitCheck);
					$("#editIsDefaultSignSubmit").val(result.isDefaultSignSubmit);
					$("#editMoSourceSegment").val(result.moSourceSegment);
					$("#editIsSgip").val(result.isSgip);
					$("#editAppExtras").val(result.appExtras);
					$("#editRedisFlag").val(result.redisFlag);
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



function findProductDetails(id) {
	$.ajax({
		url:"findProductById.action",
		data:{id:id},
		dataType:"json",
		type:"post",
		success:function (result){
			$("#detailname").html(result.id+":"+result.productName);
			$("#detailclass").html((result.productType == "sms" ? '短信' : '彩信') + " / " + result.productClass);
			if(result.productStatus == 'normal'){
    			$("#detailstatus").html("正常");
    		}else if(result.productStatus == 'pause'){
    			$("#detailstatus").html("暂停");
    		}else if(result.productStatus == 'deleted'){
    			$("#detailstatus").html("删除");
    		}
			if(result.isSign == 'yes'){
				$("#detailsign").html("必须");
			}else if(result.isSign == 'no'){
				$("#detailsign").html("不必须");
			}
			$("#detailcmcc").html(result.cmccChannelId +" : "+ result.cmccChannelName);
			$("#detailunicom").html(result.unicomChannelId +" : "+ result.unicomChannelName);
			$("#detailtelecom").html(result.telecomChannelId +" : "+ result.telecomChannelName);
			
		}
	});
	
}


function findDetails(value) {
	var id = value;
	$.ajax({
		url:"findAccountById.action",
		data:{id:id},
		dataType:"json",
		type:"post",
		success:function (result){
    		$("#detailApp").html(result.id+":"+result.appName);
//    		$("#detailAppId").html(result.id);
    		$("#detailAppPassword").html("<input type='password' value='********' readonly='readonly' size='10'><input type='button' onclick='showPassword("+result.id+")' value='显示密码' />");
    		
    		if(result.appStatus == 'normal'){
    			$("#detailAppStatus").html("正常");
    		}else if(result.appStatus == 'pause'){
    			$("#detailAppStatus").html("暂停");
    		}else if(result.appStatus == 'deleted'){
    			$("#detailAppStatus").html("删除");
    		}
    		if(result.rptSyncModel == 'push'){
    			$("#detailRptSyncModel").html('推送');
    		}else if(result.rptSyncModel == 'pull'){
    			$("#detailRptSyncModel").html('拉取');
    		}
    		$("#detailRptSyncAddress").html(result.rptSyncAddress);
    		if(result.priority =='hight'){
    			$("#detailPriority").html('高');
    		}else if(result.priority =='normal'){
    			$("#detailPriority").html('正常');
    		}else if(result.priority =='low'){
    			$("#detailPriority").html('低');
    		}else if(result.priority =='urgent'){
    			$("#detailPriority").html('紧急');
    		}
    		$("#detailIsShuntPhone").html(result.isShuntPhone);
    		if(result.isShuntPhone == 'yes'){
    			$("#detailIsShuntPhone").html('是');
    		}else{
    			$("#detailIsShuntPhone").html('否');
    		}
    		if(result.moSyncModel == 'push'){
    			$("#detailMoSyncModel").html('推送');
    		}else if(result.moSyncModel == 'pull'){
    			$("#detailMoSyncModel").html('拉取');
    		}
    		$("#detailMoSyncAddress").html(result.moSyncAddress);
    		if(result.skipMustWords == 'yes'){
    			$("#detailSkipMustWords").html('是');
    		} else {
    			$("#detailSkipMustWords").html('否');
    		}
    		if(result.isDayLimit == 'yes'){
    			$("#detailIsDayLimit").html('是');
    		}else{
    			$("#detailIsDayLimit").html('否');
    		}
    		$("#detailDayLimitCount").html(result.dayLimitCount);
    		if(result.isModel == 'yes'){
    			$("#detailIsModel").html('是');
    		}else{
    			$("#detailIsModel").html('否');
    		}
    		if(result.isMinLimit == 'yes'){
    			$("#detailIsMinLimit").html('是');
    		}else{
    			$("#detailIsMinLimit").html('否');
    		}
    		$("#detailMinLimitCount").html(result.minLimitCount);
    		if(result.checkWordsType == 'yes'){
    			$("#detailCheckWordsType").html('是');
    		}else{
    			$("#detailCheckWordsType").html('否');
    		}
    		if(result.isDayContentLimit == 'yes'){
    			$("#detailIsDayContentLimit").html('是');
    		}else{
    			$("#detailIsDayContentLimit").html('否');
    		}
    		$("#detailDayLimitContentCount").html(result.dayLimitContentCount);
    		$("#detailDefaultSign").html(result.defaultSign);
    		$("#detailCompanyId").html(result.companyName);
    		$("#detailProductId").html(result.productName);
    		if(result.appParentId=='0'){
    			$("#detailAppParentId").html("-");
    		}else{
    			$("#detailAppParentId").html(result.appParentId+":"+result.appParentName);
    		}
    		$("#detailPrice").html(result.price);
    		$("#detailPriceCmcc").html(result.priceCmcc);
    		$("#detailPriceUnicom").html(result.priceUnicom);
    		$("#detailPriceTelecom").html(result.priceTelecom);
    		if(result.subNum==0){
    			$("#detailSubAppId").html('0');
    		}else{
    			$("#detailSubAppId").html('<a onclick="findSubList('+result.id+')"><font color="#0f0">'+result.subNum+'</font></a>');
    		}
    		if(result.isTemplate== 'yes'){
    			$("#detailIsTemplate").html("是");
    		}else{
    			$("#detailIsTemplate").html("否");
    		}
    		if(result.isContAudit== 'yes'){
    			$("#detailIsContAudit").html("是");
    		}else{
    			$("#detailIsContAudit").html("否");
    		}
    		$("#detailContAuditCount").html(result.contAuditCount);
    		if(result.isMonthLimit == 'yes'){
    			$("#detailIsMonthLimit").html('是');
    		}else{
    			$("#detailIsMonthLimit").html('否');
    		}
    		$("#detailMonthLimitCount").html(result.monthLimitCount);
    		if(result.isDayLimitCheck == 'yes'){
    			$("#detailIsDayLimitCheck").html('是');
    		}else{
    			$("#detailIsDayLimitCheck").html('否');
    		}
    		if(result.isDefaultSignSubmit == 'yes'){
    			$("#detailIsDefaultSignSubmit").html('是');
    		}else{
    			$("#detailIsDefaultSignSubmit").html('否');
    		}
    		if(result.isExt == 0){
    			$("#detailIsExt").html('固定');
    		}else{
    			$("#detailIsExt").html('不固定');
    		}
    		if(result.moFlag == 1){
    			$("#detailMoFlag").html('固定');
    		}else{
    			$("#detailMoFlag").html('不固定');
    		}
    		$("#detailSourceSegment").html(result.sourceSegment);
    		$("#detailMoSourceSegment").html(result.moSourceSegment);
            if(result.appRoles == 1){
                $("#detailAppRoles").html('支持');
            }else{
                $("#detailAppRoles").html('不支持');
            }
            if(result.isSgip == 'yes'){
    			$("#detailIsSgip").html('是');
    		}else{
    			$("#detailIsSgip").html('否');
    		}
            $("#detailRedisFlag").html(result.redisFlag);
            
		}
	});
}
function showPassword(value) {
	if(passed('显示密码')){
		$.ajax({
			url:"findAccountById.action",
			data:{id:value},
			dataType:"json",
			type:"post",
			success:function (result){
				$("#detailAppPassword").html("<input type='text' value='"+result.appPassword+"' readonly='readonly' size='10'><button onclick='hidePassword("+result.id+")'>隐藏密码</button>");
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
function hidePassword(value) {
	$("#detailAppPassword").html("<input type='password' value='********' readonly='readonly' size='10'><button onclick='showPassword("+value+")'>显示密码</button>");
}
/**充值**/
function charge(id){
	if(passed('充值')){
		$.ajax({
			url:"findAccountById.action",
			data:{id:id},
			dataType:"json",
			type:"post",
			success:function (result){
				$("#cAppId").val(result.id);
				$("#cCompanyId").val(result.companyId);
	    		$("#cAppName").val(result.appName);
	    		$("#cPrice").val(result.price);
	    		$("#climitCount").val(result.limitCount-result.sentCount);
			}
		});
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
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
    //修改页面保存按钮事件
   $('#edit_saveBtn').click(function(){
	   	mark=true;
	    $("#editForm").data('bootstrapValidator').updateStatus('rptSyncAddress', 'callback').validateField('rptSyncAddress');
	    $("#editForm").data('bootstrapValidator').updateStatus('moSyncAddress', 'callback').validateField('moSyncAddress');
    	$('#editForm').bootstrapValidator('validate');
    	if($("#editForm").data('bootstrapValidator').isValid()){
	    	var id=$("#editId").val();
	    	var flag='';
	    	var appStatus=$("#editAppStatus").val();
	    	if(appStatus=='deleted'){
	    		$.ajax({
	    			url:"validatorAccountIsUse.action",
	    			type:"post",
	    			data:{id:id},
	    			async:false,
	    			success:function(obj){
	    				flag=obj;
	    			},
	    			dataType:"json"
	    		});
	    	}
	    	if(flag==2){
	    		$('.popup_de .show_msg').text('对扩展号有影响，确定要修改吗？');
		    	$('.popup_de').addClass('bbox');
	    	}else{
	    		$('.popup_de .show_msg').text('确定要修改吗？');
	    		$('.popup_de').addClass('bbox');
	    	}
	    	mark=true;
	    	$('.popup_de .btn_submit').one('click',function(){
//	    		var _info = $('#editForm').serialize();
//	    		data= decodeURIComponent(_info,true);
	    		var id=$("#editId").val();
	    		var appName=$("#editAccName").val();
	    		var appStatus=$("#editAppStatus").val();
	    		var companyId=$("#editCompanyId").val();
	    		var productId=$("#editProductId").val();
	    		var serviceTimeBegin=$("#editServiceTimeBegin").val();
	    		var serviceTimeEnd=$("#editServiceTimeEnd").val();
	    		var rptSyncModel=$("#editRptSyncModel").val();
	    		var rptSyncAddress=$("#editRptSyncAddress").val();
	    		var moSyncModel=$("#editMoSyncModel").val();
	    		var moSyncAddress=$("#editMoSyncAddress").val();
	    		var isMinLimit=$("#editIsMinLimit").val();
	    		var minLimitCount=$("#editMinLimitCount").val();
	    		var isDayLimit=$("#editIsDayLimit").val();
	    		var dayLimitCount=$("#editDayLimitCount").val();
	    		var isMonthLimit=$("#editIsMonthLimit").val();
	    		var monthLimitCount=$("#editMonthLimitCount").val();
	    		var isDayContentLimit=$("#editIsDayContentLimit").val();
	    		var dayLimitContentCount=$("#editDayLimitContentCount").val();
	    		var isTemplate=$("#editIsTemplate").val();
	    		var skipMustWords=$("#editSkipMustWords").val();
	    		var checkWordsType=$("#editCheckWordsType").val();
	    		var isModel=$("#editIsModel").val();
	    		var isContAudit=$("#editIsContAudit").val();
	    		var contAuditCount=$("#editContAuditCount").val();
	    		var isDayLimitCheck=$("#editIsDayLimitCheck").val();
	    		var isDefaultSignSubmit=$("#editIsDefaultSignSubmit").val();
	    		var chargeBy=$("#editChargeBy").val();
	    		var payment=$("#editPayment").val();
	    		var appExtSrc=$("#editAppExtSrc").val();
	    		var oldAppExtSrc=$("#oldAppExtSrc").val();
	    		var defaultSign=$("#editDefaultSign").val();
	    		var isExt=$("#editIsExt").val();
	    		var sourceSegment=$("#editSourceSegment").val();
	    		var moFlag=$("#editMoFlag").val();
	    		var moSourceSegment=$("#editMoSourceSegment").val();
	    		var testModel=$("#editTestModel").val();
	    		var maxConnection=$("#editMaxConnection").val();
	    		var priority=$("#editPriority").val();
	    		var isShuntPhone=$("#editIsShuntPhone").val();
	    		var isSgip=$("#editIsSgip").val();
	    		var appExtras=$("#editAppExtras").val();
	    		var authIp=$("#editAuthIp").val();
	    		var price=$("#editPrice").val();
	    		var priceCmcc=$("#editPriceCmcc").val();
	    		var priceUnicom=$("#editPriceUnicom").val();
	    		var priceTelecom=$("#editPriceTelecom").val();
	    		var blackLevel=$("#editBlackLevel").val();
	    		var appRoles=$("#editAppRoles").val();
	    		var redisFlag = $("#editRedisFlag").val();
	    		if(mark){
	    			$.ajax({url:"updateAccount.action",
	    					data:{
	    						id : id,
	    						appName : appName,
	    						appStatus : appStatus,
	    						companyId : companyId,
	    						productId : productId,
	    						serviceTimeBegin : serviceTimeBegin,
	    						serviceTimeEnd : serviceTimeEnd,
	    						rptSyncModel : rptSyncModel,
	    						rptSyncAddress : rptSyncAddress,
	    						moSyncModel : moSyncModel,
	    						moSyncAddress : moSyncAddress,
	    						isMinLimit : isMinLimit,
	    						minLimitCount : minLimitCount,
	    						isDayLimit : isDayLimit,
	    						dayLimitCount : dayLimitCount,
	    						isMonthLimit : isMonthLimit,
	    						monthLimitCount : monthLimitCount,
	    						isDayContentLimit : isDayContentLimit,
	    						dayLimitContentCount : dayLimitContentCount,
	    						isTemplate : isTemplate,
	    						skipMustWords : skipMustWords,
	    						checkWordsType : checkWordsType,
	    						isModel : isModel,
	    						isContAudit : isContAudit,
	    						contAuditCount : contAuditCount,
	    						isDayLimitCheck : isDayLimitCheck,
	    						isDefaultSignSubmit : isDefaultSignSubmit,
	    						chargeBy : chargeBy,
	    						payment : payment,
	    						appExtSrc : appExtSrc,
	    						oldAppExtSrc : oldAppExtSrc,
	    						defaultSign : defaultSign,
	    						isExt : isExt,
	    						sourceSegment : sourceSegment,
	    						moFlag : moFlag,
	    						moSourceSegment : moSourceSegment,
	    						testModel : testModel,
	    						maxConnection : maxConnection,
	    						priority : priority,
	    						isShuntPhone : isShuntPhone,
	    						isSgip : isSgip,
	    						appExtras : appExtras,
	    						authIp : authIp,
	    						price : price,
	    						priceCmcc : priceCmcc,
	    						priceUnicom : priceUnicom,
	    						priceTelecom : priceTelecom,
	    						blackLevel : blackLevel,
	    						appRoles : appRoles,
	    						redisFlag:redisFlag
	    					},type:"post",
	    					success: function(data){
	    				if(data.result>0){
	    					toastr.success("修改成功!")
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
	    					$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
	    					//修改页面表单重置
	    					$('#editForm')[0].reset();
	    					$('.popup_de').removeClass('bbox');
	    					$('#editCompanyId').attr("disabled",false);
	    					$("#editSpan").html("");
	    				}else{
	    					toastr.success("修改失败!")
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
	    					$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
	    					//修改页面表单重置
	    					
	    					$('.popup_de').removeClass('bbox');
	    					$('#editCompanyId').attr("disabled",false);
	    					$("#editSpan").html("");
	    				}
	    					}})
	    			mark=false;
	    		}
	    	})
    	}
   })
   $("#appParentId").change(function(){
	   var appId=$(this).val();
	   if(appId == '0'){
		   $("#companyId").attr("disabled",false);
		   return ;
	   }
	   $.ajax({
		   url:"changeCompany.action",
		   data:{appId:appId},
		   type:"post",
		   success:function(obj){
			   $("#companyId").selectpicker('val',obj.companyId);
			   $("#oldCompanyId").val(obj.companyId);
			   $("#companyId").attr("disabled",true);
		   },
		   dataType:"json"
	   });
   });
	$("#companyId").change(function(){
		var companyId=$(this).val();
		$("#oldCompanyId").val(companyId);
	});
    //修改页面回退按钮事件
   $('#edit_backBtn').click(function(){
    	$('.changeBody').addClass('animated slideOutLeft');
    	setTimeout(function(){
			$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
    	$('#editForm').data('bootstrapValidator').resetForm(true);
    	$('#editCompanyId').attr("disabled",false);
    	$('#editForm')[0].reset();
		$("#editSpan").html("");
    })
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
	
	//点击菜单页面提交按钮
	$('#add_saveBtn').click(function() {
		 //点击保存时触发表单验证
		 $("#addForm").data('bootstrapValidator').updateStatus('rptSyncAddress', 'callback').validateField('rptSyncAddress');
		 $("#addForm").data('bootstrapValidator').updateStatus('moSyncAddress', 'callback').validateField('moSyncAddress');
		 $('#addForm').bootstrapValidator('validate');
	       //如果表单验证正确，则请求后台添加用户
	      if($("#addForm").data('bootstrapValidator').isValid()){
	    	   var _info = $('#addForm').serialize();
	    	   data= decodeURIComponent(_info,true);
	    	   $.post(
					"addAccount.action",
					data,
					function(data){
						//后台返回添加成功
						if(data.result == 1){
							toastr.success("新增成功!")
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function(){
								$('.addBody').removeClass('animated slideOutLeft').css('display','none');
							},500);
							$('.tableBody').css('display','block').addClass('animated slideInRight');
							$("#addForm")[0].reset();
					    	$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$("#companyId").selectpicker('refresh');
							$("#productId").selectpicker('refresh');
							$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
						}else if(data.result == 2){
							toastr.error("新增失败，账户名称重复!")
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function(){
								$('.addBody').removeClass('animated slideOutLeft').css('display','none');
							},500);
							$('.tableBody').css('display','block').addClass('animated slideInRight');
							$("#addForm")[0].reset();
					    	$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$("#companyId").selectpicker('refresh');
							$("#productId").selectpicker('refresh');
							$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
						}else{
							toastr.error("新增失败!")
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function(){
								$('.addBody').removeClass('animated slideOutLeft').css('display','none');
							},500);
							$('.tableBody').css('display','block').addClass('animated slideInRight');
							$("#addForm")[0].reset();
					    	$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$("#companyId").selectpicker('refresh');
							$("#productId").selectpicker('refresh');
							$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
						}
						$('#companyId').attr("disabled",false);
					}
	    	  ) 
	      }else{//验证失败按钮可用
	      }
    });
	
	//点击添加菜单页面返回按钮
	$('#add_backBtn').click(function() {
		 $('.addBody').addClass('animated slideOutLeft');
	    	setTimeout(function(){
				$('.addBody').removeClass('animated slideOutLeft').css('display','none');
			},500)
	    	$('.tableBody').css('display','block').addClass('animated slideInRight');  
	    	$("#addForm")[0].reset();
	    	$("#addForm").data('bootstrapValidator').destroy();
			$('#addForm').data('bootstrapValidator', null);
			$('#companyId').attr("disabled",false);
			addValidator();
			$("#companyId").selectpicker('refresh');
			$("#productId").selectpicker('refresh');
   });
	
   //充值提交按钮
	$('#chargeBtn').click(function(){
		mark=true;
		$('#chargeForm').bootstrapValidator('validate');
		if($("#chargeForm").data('bootstrapValidator').isValid()){
			$('.popup_de .show_msg').text('确定要充值吗？');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				var _info = $('#chargeForm').serialize();
				data= decodeURIComponent(_info,true);
				if(mark){
					$.post(
							"chargeAccount.action",
							data,
							function(data){
								//后台返回添加成功
								if(data.result>0){
									toastr.success("充值成功!");
									$('#chargeForm').data('bootstrapValidator').resetForm(true);
									$('#chargeModal').modal('hide');
									$('.popup_de').removeClass('bbox');
									$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
								}else{
									toastr.success("充值失败!");
									$('#chargeForm').data('bootstrapValidator').resetForm(true);
									$('#chargeModal').modal('hide');
									$('.popup_de').removeClass('bbox');
									$('#mytab').bootstrapTable('refresh',{url:'findAccount.action'});
								}
							}
					) 
					mark=false;
				}
			});
		}
	});
});
function findSubList(id){
	var url = "findSubList.action?id="+id;
	$('#tLinkAccount').bootstrapTable('refresh', {
		url : url
	});
	$("#linkAccount").modal();
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