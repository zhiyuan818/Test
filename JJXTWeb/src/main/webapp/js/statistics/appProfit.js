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
		url : "findAppProfitList.action",// 请求后台url
		//height : tableHeight(),// 高度调整
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : false,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
//		showFooter:true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : '日期',
			field : 'logDate',
			valign:'middle',
            align:'center',
			formatter : function(value, row, index) {
				return changeLogDateFormat(value)
			},
//			footerFormatter: function(value){
//				return "总计";
//			}

		}, {
			title : '客户简称',
			field : 'companyKey',
            align:'center',
		}, {
			title : '账户',
			field : 'appName',
			align:'center',
		}, {
			title : '销售',
			field : 'sales',
            align:'center',
		}, {
			title : '运营商',
			field : 'provider',
			align:'center',
		},{
			title : '提交总数(计费)',
			field : 'appReportDelivrdCharge',
			align:'center',
			sortable : true 
//			footerFormatter: function(value){
//				var count=0;
//				for (var i=0; i<value.length;i++) {
//		               count = accAdd(count,value[i].appReportDelivrdCharge);
//		        }
//				return count;
//			}
		}, {
			title : '单价',
			field : 'appPrice',
			align:'center',
			sortable : true 
		}, {
			title : '通道名称',
			field : 'channelName',
			align:'center',
			formatter : function(value,row,index){
				if(value == null ){
					return '-';
				}
				return row.channelId+":"+value;
			}
		}, {
			title : '提交总数(计费)',
			field : 'channelReportDelivrdCharge',
			align:'center',
			sortable : true 
//			footerFormatter: function(value){
//				var count=0;
//				for (var i=0; i<value.length;i++) {
//		               count = accAdd(count,value[i].channelReportDelivrdCharge);
//		        }
//				return count;
//			}
		}, {
			title : '通道单价',
			align:'center',
			field : 'channelPrice',
			sortable : true 
		}, {
			title : '收入',
			field : 'income',
			align:'center',
			sortable : true 
//			formatter : function(value,row,index){
//				return (value*row.appReportDelivrdCharge/100).toFixed(2);
//			},
//			footerFormatter: function(value){
//				var count=0;
//				for (var i=0; i<value.length;i++) {
//		               count = accAdd(count.toFixed(2),(value[i].appPrice*value[i].appReportDelivrdCharge/100).toFixed(2));
//		        }
//				return count.toFixed(2);
//			}
		}, {
			title : '成本',
			field : 'cost',
			align:'center',
			sortable : true 
//			formatter : function(value,row,index){
//				return (value*row.channelReportDelivrdCharge/100).toFixed(2);
//			},
//			footerFormatter: function(value){
//				var count=0;
//				for (var i=0; i<value.length;i++) {
//		               count = accAdd(count.toFixed(2),(value[i].channelPrice*value[i].channelReportDelivrdCharge/100).toFixed(2));
//		        }
//				return count.toFixed(2);
//			}
		}, {
			title : '利润',
			field : 'profit',
			align:'center',
			sortable : true ,
			width:100,
//			formatter : function(value, row, index) {
//				return (row.appPrice*row.appReportDelivrdCharge/100-value*row.channelReportDelivrdCharge/100).toFixed(2);
//			},
//			footerFormatter: function(value){
//				var count=0;
//				for (var i=0; i<value.length;i++) {
//					var sub=accSub((value[i].appPrice*value[i].appReportDelivrdCharge/100).toFixed(2),(value[i].channelPrice*value[i].channelReportDelivrdCharge/100).toFixed(2));
//		            count =accAdd(count.toFixed(2),sub);
//		        }
//				return count.toFixed(2);
//			}
		}, {
			title : '利润率',
			field : 'profitMargin',
			align:'center',
			sortable : true ,
			formatter : function(value, row, index) {
				if(parseFloat(value)<parseFloat(25)){
					return "<font color='red'>"+value+"%</font>"
				}
				return value+"%";
			},
//			formatter : function(value, row, index) {
//				var sum=row.appPrice*row.appReportDelivrdCharge/100;
//				if(sum==0){
//					return "0.00%";
//				}
//				return ((row.appPrice*row.appReportDelivrdCharge/100-value*row.channelReportDelivrdCharge/100)/(row.appPrice*row.appReportDelivrdCharge/100)*100).toFixed(2)+"%";
//			},
//			footerFormatter: function(value){
//				var cost=0;
//				var profit=0;
//				for (var i=0; i<value.length;i++) {
//					cost += parseFloat((value[i].appPrice*value[i].appReportDelivrdCharge/100).toFixed(2));
//					profit +=parseFloat(parseFloat((value[i].appPrice*value[i].appReportDelivrdCharge/100).toFixed(2))-parseFloat((value[i].channelPrice*value[i].channelReportDelivrdCharge/100).toFixed(2)));
//		        }
//				if(cost==0){
//					return "<font color='red'>0.00%</font>";
//				}
//				var pro=(profit/cost*100).toFixed(2);
//				if(parseFloat(pro)<parseFloat(25)){
//					return "<font color='red'>"+pro+"%</font>"
//				}
//				return (profit/cost*100).toFixed(2)+"%";
//			}
		}],
		/*onPostBody:function () {
            //合并页脚
            merge_footer();
        },*/
		onLoadSuccess: function (data) {
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
		    for(var i=0;i<data.length;i++){
		    	if(!passed('显示优享通道') && data[i].channelId == '347'){
		    		data.splice(i,1);
		    	}
		    }
		    $('#mytab').bootstrapTable('load',data);
		},
		locale : 'zh-CN'// 中文支持,
	});
	
	 $("#search_btn").click(function () {
		 $('#mytab').bootstrapTable('refresh',{url:'findAppProfitList.action'});
	});
    $("#search_back").click(function () {
    	$("#companyKey").val(''),
    	$("#appId").val(''),
    	$("#sales").val(''),
    	$("#channelId").val(''),
    	$("#startTime").val(currentTime()),
    	$("#endTime").val(currentTime()),
    	$("[name='statisticType']").first().prop("checked",true);
    	$("[name='statisticType']").first().parent().addClass("active");
    	$("[name='statisticType']").last().parent().removeClass("active");
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").prop("selected", false); // 重置原生select的值
			$(this).find("option:first").prop("selected", true);
		});	    	
    	
    	$('#mytab').bootstrapTable('refresh',{url:'findAppProfitList.action'});
    });
    $("#startTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd',// 显示格式
		initialDate : new Date(),
		minView : 2,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('hide', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var startTime = event.date;
		$('#endTime').datetimepicker('setStartDate', startTime);
		$('#endTime').val("");
	});
	$("#endTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd',// 显示格式
		initialDate : new Date(),
		minView : 2,
		autoclose : true,// 选中自动关闭
		todayBtn : true,// 显示今日按钮
		locale : moment.locale('zh-cn')
	}).on('hide', function(event) {
		event.preventDefault();
		event.stopPropagation();
		var endTime = event.date;
		$('#startTime').datetimepicker('setEndDate', endTime);
	});
})
function accAdd(arg1,arg2){
  var r1,r2,m;
  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
  m=Math.pow(10,Math.max(r1,r2))
  return (arg1*m+arg2*m)/m
}
function accSub(arg1,arg2){
	  var r1,r2,m,n;
	  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	  m=Math.pow(10,Math.max(r1,r2));
	  //last modify by deeka
	  //动态控制精度长度
	  n=(r1>=r2)?r1:r2;
	  return ((arg1*m-arg2*m)/m).toFixed(n);
}
function merge_footer() {
        //获取table表中footer 并获取到这一行的所有列
        var footer_tbody = $('.fixed-table-footer table tbody');
        var footer_tr = footer_tbody.find('>tr');
        var footer_td = footer_tr.find('>td');
        var footer_td_1 = footer_td.eq(0);
        //由于我们这里做统计只需要两列，故可以将除第一列与最后一列的列全部隐藏，然后再设置第一列跨列
        //遍历隐藏中间的列 下标从1开始
        for(var i=1;i<4;i++) {
            footer_td.eq(i).hide();
        }
        //设置跨列
        footer_td_1.prop('colspan', 4).show();
        //这里可以根据自己的表格来设置列的宽度 使对齐
        footer_td_1.attr('width', "100px").show();
}
function currentTime() {
	var today = new Date();
	var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-"
			+ today.getDate();
	// 对日期格式进行处理
	var date = new Date(nowdate);
	var mon = date.getMonth() + 1;
	var day = date.getDate();
	var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-"
			+ (day < 10 ? "0" + day : day);
	return nowdate;
}

function tableHeight() {
	return $(window).height() - 130;
}

function queryParams(params) {
	return {
		companyId : $("#companyId").val(),
		appId : $("#appId").val(),
		sales : $("#sales").val(),
		channelId : $("#channelId").val(),
		statisticType : $("input[name='statisticType']:checked").val(),
		startTime : $("#startTime").val(),// 开始时间
		endTime : $("#endTime").val(),// 结束时间
		sort : params.sort,
		sortOrder : params.order
	}
}

function changeLogDateFormat(cellval) {
	var dateVal = cellval + "";
	if (cellval != null) {
		var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(
				")/", ""), 10));
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1)
				: date.getMonth() + 1;
		var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date
				.getDate();

		return date.getFullYear() + "-" + month + "-" + currentDate;
	}
	return "";
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

		return  month + "-" + currentDate + " "	+ hours + ":" + minutes;
	}
	return "";
}

function changeColor(value, row, index) {
	var result = "";
	if(value != null){
		var percentage = value.replace("%","");
		result += Number(percentage)<Number(85)?"<font color='#ff0000'>"+value+"</font>":value;
	}
		
	return result;
}


function changeToBlue(value, row, index) {
	var result = "";
	if(value != null){
		result += "<font color='#6B8E23'>"+value+"</font>";
	}
	return result;
}

function changeToRed(value, row, index) {
	var result = "";
	if(value != null){
		result += "<font color='#cc3300'>"+value+"</font>";
	}
	return result;
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


//$(function() {
//	var dateType = $(".dateTypeDiv input[name='dateType']:checked").val();
//	if (dateType == 'day') {
//		$('#dayDiv').attr("style", "display:block;");
//
//		$('#dateValue').datetimepicker({
//			language : 'zh-CN',// 显示中文
//			format : 'yyyy-mm-dd',// 显示格式
//			minView : "month",// 设置只显示到月份
//			initialDate : new Date(),
//			autoclose : true,// 选中自动关闭
//			todayBtn : true,// 显示今日按钮
//			locale : moment.locale('zh-cn')
//		});
//		document.getElementById("dateValue").value = currentTime();
//	}
//
//
//	$('#day').change(function() {
//		$('#dayDiv').attr("style", "display:block;");
//		$('#weekDiv').attr("style", "display:none;");
//		$('#monthDiv').attr("style", "display:none;");
//		$('#yearDiv').attr("style", "display:none;");
//
//		$('.dayValue').datetimepicker({
//			language : 'zh-CN',// 显示中文
//			format : 'yyyy-mm-dd',// 显示格式
//			minView : "month",// 设置只显示到月份
//			initialDate : new Date(),
//			autoclose : true,// 选中自动关闭
//			todayBtn : true,// 显示今日按钮
//			locale : moment.locale('zh-cn')
//		});
//		document.getElementById("dateValue").value = currentTime();
//	});
//
//	$('#week').change(function() {
//		$('#dayDiv').attr("style", "display:none;");
//		$('#weekDiv').attr("style", "display:block;");
//		$('#monthDiv').attr("style", "display:none;");
//		$('#yearDiv').attr("style", "display:none;");
//
//		$('.weekValue').datetimepicker({
//			language : 'zh-CN',// 显示中文
//			format : 'yyyy-mm-dd',// 显示格式
//			minView : "month",// 设置只显示到月份
//			initialDate : new Date(),
//			autoclose : true,// 选中自动关闭
//			todayBtn : true,// 显示今日按钮
//			locale : moment.locale('zh-cn')
//		});
//		document.getElementById("dateValue").value =  currentTime();
//	});
//
//	$('#month').change(function() {
//		$('#dayDiv').attr("style", "display:none;");
//		$('#weekDiv').attr("style", "display:none;");
//		$('#monthDiv').attr("style", "display:block;");
//		$('#yearDiv').attr("style", "display:none;");
//
//		$('.monthValue').datetimepicker({
//			language : 'zh-CN',// 显示中文
//			format : 'yyyy-mm',// 显示格式
//			weekStart: 1,
//	         startView: 3,
//	         minView: 3,
//			initialDate : new Date(),
//			autoclose : true,// 选中自动关闭
//			todayBtn : true,// 显示今日按钮
//			locale : moment.locale('zh-cn')
//		});
//		document.getElementById("dateValue").value = currentMonth();
//	});
//
//	$('#year').change(function() {
//		$('#dayDiv').attr("style", "display:none;");
//		$('#weekDiv').attr("style", "display:none;");
//		$('#monthDiv').attr("style", "display:none;");
//		$('#yearDiv').attr("style", "display:block;");
//
//		$('.yearValue').datetimepicker({
//			language : 'zh-CN',// 显示中文
//			format : 'yyyy',// 显示格式
//			startView: 'decade',
//			 minView: 'decade',
//			maxViewMode: 2,
//			minViewMode:2,
//			autoclose : true,// 选中自动关闭
//			todayBtn : true,// 显示今日按钮
//			locale : moment.locale('zh-cn')
//		});
//		document.getElementById("dateValue").value = currentYear();
//	});
//
//})


function currentMonth() {
	var today = new Date();
	var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) ;
	// 对日期格式进行处理
	var date = new Date(nowdate);
	var mon = date.getMonth() + 1;
	var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon);
	return mydate;
}

function currentYear() {
	var today = new Date();
	var nowdate = (today.getFullYear());
	return nowdate;
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