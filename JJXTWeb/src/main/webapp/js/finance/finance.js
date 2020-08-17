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
     	url:"findFinance.action",//请求后台url
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
     	pageList:[10,20,50],//分页步进值
     	showRefresh:false,//刷新按钮
     	showColumns:false,//是否显示所有的列
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
         		valign:'middle',
         		visible:false
         	},
         	{
         		title:'ID',
         		field:'id',
         		visible:false
         	},
         	{
         		title:'帐户',
         		field:'appName',
         	},
         	{
         		title:'销售',
         		field:'sales',
         	},
         	{
         		title:'充值时间',
         		field:'addTime',
         		formatter: function(value,row,index){
         			return changeDateFormat(value);
         		}
         	},
         	{
         		title:'类型',
         		field:'changeType',
         	},
         	{
         		title:'充值条数',
         		field:'changeNum',
         	},
         	{
         		title:'单价(分)',
         		field:'price',
         		formatter: function(value,row,index){
         			if(row.changeType == '充值' || row.changeType == '退款'){
         				return value;
         			}
         			return '';
         		}
         	},
         	{
         		title:'应收(元)',
         		field:'dueFrom',
         		formatter: function(value,row,index){
         			if(row.changeType == '充值' || row.changeType == '退款'){
         				return value;
         			}
         			return '';
         		}
         	},
         	{
         		title:'到帐',
         		field:'isBill',
    			formatter : function(value, row, index) {
         			if(row.changeType == '充值' || row.changeType == '退款'){
         				if (value == 'yes') {
        					return '<span style="color:#00ff00">'+'到帐'+'</span>'; ;
        				} else {
        					return '<span style="color:red">'+'未到帐'+'</span>'; ;
        				}
         			}
         			return '';
    			},
         		
         	},
         	{
         		title:'收款银行',
         		field:'bankName',
         	},
         	{
         		title:'备注',
         		field:'notice',
         		width:200,
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
    
    
	//充值表单验证
    
    $("#search_btn").click(function () {
       	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#search_appname").val('');
    	$("#search_addName").val('');
    	$("#search_changeType").val('');
    	$("#search_bill").val('');
    	$("#startTime").val('');
    	$("#endTime").val('');
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1,limit:30,sort:'',order:'asc'});
    });
});


function tableHeight() {
    return $(window).height()-80;
}

function queryParams(params){
	return{
		pageSize: params.limit, //页面大小
		pageIndex:params.pageNumber, //页码
		appName:$("#search_appname").val(),
		addName:$("#search_addName").val(),
		changeType:$("#search_changeType").val(),
		isBill:$("#search_bill").val(),
		startTime:$("#startTime").val(),
		endTime:$("#endTime").val()
	}
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

function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    if(row.changeType == '充值' || row.changeType == '退款'){
    	if(row.isBill == 'no'){
    		result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"writeOff('" + row.id + "')\" title='销帐'><span class='glyphicon glyphicon-pencil'></span></a>";
    	}
    }
    return result;
}

/**销帐**/
function writeOff(id){
	mark=true;
	if(passed('销帐')){
		$('.popup_de .show_msg').text('确定要销帐吗？');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			if(mark){
				$.ajax({
					url:"writeOff.action",
					data:{id:id},
					dataType:"json",
					type:"post",
					success:function (result){
						if(result.result > 0){
							toastr.success("销帐成功!")
							$('#mytab').bootstrapTable('refresh',{url:'findFinance.action'});
							$('.popup_de').removeClass('bbox');
						}else{
							toastr.error("销帐失败!")
							$('#mytab').bootstrapTable('refresh',{url:'findFinance.action'});
							$('.popup_de').removeClass('bbox');
						}
					}
				});
				mark=false;
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
	$("#startTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('changeDate', function(event) {
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
	}).on('changeDate', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var endTime = event.date;
		$('#startTime').datetimepicker('setEndDate', endTime);
	});
});


/** 自定义toastr **/
$(function(){
    //参数设置，若用默认值可以省略以下面代码
    toastr.options = {
       "positionClass": "toast-bottom-full-width",//弹出窗的位置
    };
});
