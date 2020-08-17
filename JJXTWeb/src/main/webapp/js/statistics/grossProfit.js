$(function(){
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#appTab').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });
    
    //生成客户数据
    $('#appTab').bootstrapTable({
     	method: 'post',
     	contentType: "application/x-www-form-urlencoded",
     	url:"findAccountProfitList.action",//请求后台url
     	height:tableHeight(),//高度调整
     	dataType:"json",//返回的数据格式
     	cache:false,//是否缓存 默认是true
     	striped: true, //是否显示行间隔色
     	pageNumber: 1, //初始化加载第一页，默认第一页
     	pagination:false,//是否分页
     	queryParamsType:'limit',
     	queryParams:appQueryParams,
     	sortable: true, //是否启用排序
        sortOrder: "asc",//排序方式
     	uniqueId: "id",  //每一行的唯一标识，一般为主键列
     	showRefresh:false,//刷新按钮
     	showColumns:false,//是否显示所有的列
     	clickToSelect: false,//是否启用点击选中行
     	toolbarAlign:'right',
     	buttonsAlign:'right',//按钮对齐方式
     	columns:[
			{
				title : '账户名称',
				field : 'appId',
				align:'center',
				formatter : function(value,row,index){
					if(value == null ){
						return '-';
					}
					return value+":"+row.appName;
				}
			}, {
				title : '账户单价',
				field : 'appPrice',
				align:'center',
				sortable : true,
				formatter : function(value,row,index){
					if(value == null ){
						return '-';
					}
					return value;
				}
			}, {
				title : '通道名称',
				field : 'channelId',
				align:'center',
				formatter : function(value,row,index){
					if(value == null ){
						return '-';
					}
					if(row.type.indexOf('优化') != -1){
						return '-';
					}
					return value+":"+row.channelName;
				}
			}, {
				title : '运营商',
				field : 'provider',
				align:'center',
				formatter : function(value,row,index){
					if(value == null ){
						return '-';
					}
					return value;
				}
			}, {
				title : '类型',
				align:'center',
				field : 'type',
				formatter : function(value,row,index){
					if(value == null ){
						return '-';
					}
					return value;
				}
			}, {
				title : '通道单价',
				align:'center',
				field : 'channelPrice',
				sortable : true,
				formatter : function(value,row,index){
					if(value == null || row.type.indexOf('优化') != -1){
						return '-';
					}
					return value;
				}
			},{
				title : '利润',
				field : 'profit',
				align:'center',
				sortable : true,
				formatter : changeColor
			}, {
				title : '账户相关模板详情',
				field : 'content',
				width : '60%',
				formatter : function(value,row,index){
					if(value == null ){
						return '-';
					}
					return value;
				}
			}],
     	onLoadSuccess: function (data) {
     		
 			 var datas = $('#appTab').bootstrapTable('getData', true);
 			 // 合并单元格
 			 var fieldList=["content"];
 			 mergeCells(data, "content", 1, $('#appTab'),fieldList);      
     		
     		
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
    
    $("#search_btn_app").click(function () {
    	var com=$("#companyKeySelectA").val();
    	if($("#companyKeySelectA").val()==("") && $("#appIdSelectA").val()==("")){
			$('.popup_de .show_msg').text('请选择客户或者账户！');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			
		}else{
			$('#appTab').bootstrapTable('refreshOptions', {url:'findAccountProfitList.action'});
		}
	});
	
    $("#search_back_app").click(function () {
    	$("#companyKeySelectA").val(''),
    	$("#appIdSelectA").val(''),
    	$("#channelIdSelectA").val(''),
    	$("#providerSelectA").val(''),
    	$("#typeSelectA").val(''),
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").prop("selected", false); // 重置原生select的值
			$(this).find("option:first").prop("selected", true);
		});	    	
    	
    	 $('#appTab').bootstrapTable('refresh',{url:'findAccountProfitList.action'});
    });
	    	
});


//通道
$("#channelPage").click(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#channelTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	// 生成用户数据
	$('#channelTab').bootstrapTable({
		method: 'post',
     	contentType: "application/x-www-form-urlencoded",
     	url:"findChannelProfitList.action",//请求后台url
     	height:tableHeight(),//高度调整
     	dataType:"json",//返回的数据格式
     	cache:false,//是否缓存 默认是true
     	striped: true, //是否显示行间隔色
     	pageNumber: 1, //初始化加载第一页，默认第一页
     	pagination:false,//是否分页
     	queryParamsType:'limit',
     	queryParams:channelQueryParams,
     	sortable: true, //是否启用排序
        sortOrder: "asc",//排序方式
     	uniqueId: "id",  //每一行的唯一标识，一般为主键列
     	showRefresh:false,//刷新按钮
     	showColumns:false,//是否显示所有的列
     	clickToSelect: false,//是否启用点击选中行
     	toolbarAlign:'right',
     	buttonsAlign:'right',//按钮对齐方式
		columns : [
			 {
					title : '通道名称',
					field : 'channelId',
					align:'center',
					formatter : function(value,row,index){
						if(value == null ){
							return '-';
						}
						return value+":"+row.channelName;
					}
				}, {
					title : '运营商',
					field : 'provider',
					align:'center',
					formatter : function(value,row,index){
						if(value == null ){
							return '-';
						}
						return value;
					}
				}, {
					title : '类型',
					align:'center',
					field : 'type',
					formatter : function(value,row,index){
						if(value == null ){
							return '-';
						}
						return value;
					}
				}, {
					title : '通道单价',
					align:'center',
					field : 'channelPrice',
					sortable : true,
					formatter : function(value,row,index){
						if(value == null ){
							return '-';
						}
						return value;
					}
				}, {
					title : '产品名称',
					field : 'productId',
					align:'center',
					formatter : function(value,row,index){
						if(value == null ){
							return '-';
						}
						return value+":"+row.productName;
					}
				}, {
			title : '账户名称',
			field : 'appId',
			align:'center',
			formatter : function(value,row,index){
				if(value == null ){
					return '-';
				}
				return value+":"+row.appName;
			}
		}, {
			title : '账户单价',
			field : 'appPrice',
			align:'center',
			sortable : true,
			formatter : function(value,row,index){
				if(value == null ){
					return '-';
				}
				return value;
			}
		},{
			title : '利润',
			field : 'profit',
			align:'center',
			sortable : true,
			formatter : changeColor
		}, {
			title : '通道相关模板详情',
			field : 'content',
			width : '60%',
			formatter : function(value,row,index){
				if(value == null ){
					return '-';
				}
				return value;
			}
		}],
		onLoadSuccess: function (data) {
			
			 var datas = $('#channelTab').bootstrapTable('getData', true);
 			 // 合并单元格
 			 var fieldList=["content"];
 			 mergeCells(data, "content", 1, $('#channelTab'),fieldList); 
			
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
	
	
	 $("#search_btn_chan").click(function () {
		 if($("#channelIdSelectC").val()==("")){
			$('.popup_de .show_msg').text('请选择通道！');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			
		}else{
			$('#channelTab').bootstrapTable('refreshOptions', {url:'findChannelProfitList.action'});
		}
	});
	
    $("#search_back_chan").click(function () {
    	$("#channelIdSelectC").val(''),
    	$("#appIdSelectC").val(''),
    	$("#providerSelectC").val(''),
    	$("#typeSelectC").val(''),
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").prop("selected", false); // 重置原生select的值
			$(this).find("option:first").prop("selected", true);
		});	    	
    	
    	 $('#channelTab').bootstrapTable('refresh',{url:'findChannelProfitList.action'});
    });
    
});




/**
 * 合并单元格
 * 
 * @param data
 *            原始数据（在服务端完成排序）
 * @param fieldName
 *            合并参照的属性名称
 * @param colspan
 *            合并开始列
 * @param target
 *            目标表格对象	 
 * @param fieldList
 *            要合并的字段集合
 */
function mergeCells(data,fieldName,colspan,target,fieldList){	
// 声明一个map计算相同属性值在data对象出现的次数和
var sortMap = {};
for(var i = 0 ; i < data.length ; i++){
    for(var prop in data[i]){
    	//例如people.unit.name
    	var fieldArr=fieldName.split(".");		        
    	getCount(data[i],prop,fieldArr,0,sortMap);
    }
}	  
var index = 0;    
for(var prop in sortMap){
    var count = sortMap[prop];
    for(var i = 0 ; i < fieldList.length ; i++){
    	$(target).bootstrapTable('mergeCells',{index:index, field:fieldList[i], colspan: colspan, rowspan: count});   
	        }
	        index += count;        
	    }
}

/**
 * 递归到最后一层 统计数据重复次数
 * 比如例如people.unit.name 就一直取到name
 * 类似于data["people"]["unit"]["name"]
 */
function getCount(data,prop,fieldArr,index,sortMap){	
	if(index == fieldArr.length-1){			
		if(prop == fieldArr[index]){
            var key = data[prop];            
            if(sortMap.hasOwnProperty(key)){
                sortMap[key] = sortMap[key]+ 1;
            } else {
                sortMap[key] = 1;
            } 
	    }        
		return;
	}		
    if(prop == fieldArr[index]){
        var sdata = data[prop];	   
        index=index+1;
        getCount(sdata,fieldArr[index],fieldArr,index,sortMap);
    } 
    
}



function changeColor(value, row, index) {
	if(value == null ){
		return '-';
	}
	
	var result = value;
	if(Number(value)<=Number(0)){
		result ="<font color='#ff0000'>"+value+"</font>";
	}
		
	return result;
}

function tableHeight() {
    return $(window).height()-50;
}

function appQueryParams(params){
	return{
		companyKey : $("#companyKeySelectA").val(),
		appId : $("#appIdSelectA").val(),
		channelId : $("#channelIdSelectA").val(),
		provider : $("#providerSelectA").val(),
		type : $("#typeSelectA").val()
	}
}

function channelQueryParams(params){
	return{
		channelId : $("#channelIdSelectC").val(),
		appId : $("#appIdSelectC").val(),
		provider : $("#providerSelectC").val(),
		type : $("#typeSelectC").val()
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


});


/** 自定义toastr **/
$(function(){
    //参数设置，若用默认值可以省略以下面代码
    toastr.options = {
       "positionClass": "toast-bottom-full-width",//弹出窗的位置
    };
});