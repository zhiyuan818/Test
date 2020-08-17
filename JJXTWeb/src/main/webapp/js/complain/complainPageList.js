$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#searchComplainTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	
	$('#searchComplainTab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findComplainPageList.action",
		height : tableHeight(),
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : true,// 是否分页
		queryParamsType : 'limit',
		queryParams : querySearchParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		pageSize : 20,// 单页记录数
		pageList : [ 20, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : 'ID',
			field : 'id',
		}, {
			title : '账户ID',
			field : 'appId',
			sortable : true
		}, {
			title : '账户名称',
			field : 'appName',
		}, {
			title : '通道名称',
			field : 'channelId',
			formatter : ChannelName
		}, {
			title : '手机号码',
			field : 'destNumber',
		}, {
			title : '源号码',
			field : 'srcNumber',
		}, {
			title : '内容',
			field : 'content',
		}, {
			title : '提交时间',
			field : 'submitTime',
			width : 150,
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}, {
			title : '添加时间',
			field : 'addTime',
			width : 150,
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}],
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
	$("#search_complain_btn").click(function() {
		$('#searchComplainTab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
		
	$("#search_complain_back").click(function() {
		$("#searchAppId").val(''),
		$("#searchChannelId").val(''),
		$("#searchDestNumber").val(''),
		$("#searchSrcNumber").val(''),// 源号码
		$("#searchContent").val(''),
		$("select.selectpicker").each(function(){
    		$(this).selectpicker('val',$(this).find('option:first').val());    //重置bootstrap-select显示
    		$(this).find("option").attr("selected", false);                    //重置原生select的值
    		$(this).find("option:first").attr("selected", true);
    	});
		$('#searchComplainTab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
			sort : '',
			order : 'asc'
		});
	});
	
	// 添加
	$("#btn_add").click(
		function() {
			if (passed('添加投诉')) {
				$('.searchBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.searchBody').removeClass('animated slideOutLeft')
							.css('display', 'none');}, 500)
				$('.tableBody').css('display', 'block');
				$('.tableBody').addClass('animated slideInRight');
			} else {
				$('.popup_de .show_msg').text('没有权限，请联系管理员！');
				$('.popup_de .btn_cancel').css('display', 'none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			}
		});
	
	
	$('#complaintab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "",
		height : tableHeight(),
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : false,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "linkId", // 每一行的唯一标识，一般为主键列
		pageSize : 20,// 单页记录数
		pageList : [ 20, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : 'ID',
			field : 'id',
			visible : false

		}, {
			title : '账户ID',
			field : 'appId',
			sortable : true
		}, {
			title : '账户名称',
			field : 'appName',
		}, {
			title : '通道ID',
			field : 'channelId',
		}, {
			title : '手机号码',
			field : 'destNumber',
		}, {
			title : '源号码',
			field : 'srcNumber',
		}, {
			title : '标记',
			field : 'sendFlag'
		}, {
			title : '内容',
			field : 'content',
		}, {
			title : '提交时间',
			field : 'submitTime',
			width : 150,
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}, {
			title : '操作',
			field : 'linkId',
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
	$("#search_btn").click(function() {
		if($("#startTime").val()=="" || $("#endTime").val()==""){
			$('.popup_de .show_msg').text('请选择开始、结束时间！');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}else if($("#destNumber").val()=="" && $("#srcNumber").val()==""){
			$('.popup_de .show_msg').text('至少填写一个搜索条件！');
			$('.popup_de .btn_cancel').css('display','none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}else{
			var startTimeDate = new Date($("#startTime").val());
			startTimeDate.setHours(0);
			startTimeDate.setMinutes(0);
			startTimeDate.setSeconds(0);
			var endTimeDate = new Date($("#endTime").val());
			endTimeDate.setHours(0);
			endTimeDate.setMinutes(0);
			endTimeDate.setSeconds(0);
			var beginDate = new Date("2019-03-01");
			beginDate.setHours(0);
			beginDate.setMinutes(0);
			beginDate.setSeconds(0);
			if(endTimeDate.getTime() <=  new Date(new Date().setHours(0, 0, 0, 0)).getTime() && startTimeDate.getTime() >= beginDate.getTime()){
				if((endTimeDate.getTime() - startTimeDate.getTime()) <= 31*(1000*24*60*60)){
				$('#complaintab').bootstrapTable('refreshOptions', {url:'findMtList.action'});
				}else{
					$('.popup_de .show_msg').text('查询日期不可超过一个月！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					})
					return ;
				}
			}else{
				$('.popup_de .show_msg').text('查询日期超过范围！');
				$('.popup_de .btn_cancel').css('display','none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click',function(){
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}
		}
	});
		
	$("#search_back").click(function() {
		$("#destNumber").val(''),// 手机号码
		$("#srcNumber").val(''),// 源号码
		$("#startTime").val(currentTime()),// 开始时间
		$("#endTime").val(currentTime()),// 结束时间
		$("select.selectpicker").each(function(){
    		$(this).selectpicker('val',$(this).find('option:first').val());    //重置bootstrap-select显示
    		$(this).find("option").attr("selected", false);                    //重置原生select的值
    		$(this).find("option:first").attr("selected", true);
    	});
		$('#complaintab').bootstrapTable('refreshOptions', {
			sort : '',
			order : 'asc'
		});

	});
	
	$('#search_return').click(
		function() {
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css(
						'display', 'none');
			}, 500)
			$('.searchBody').css('display', 'block').addClass(
					'animated slideInRight');
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
	
	//弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function(){
		$('.popup_de').removeClass('bbox');
	})
	//弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function(){
		$('.popup_de').removeClass('bbox');
	})
	$("#endTime").val(currentTime());
	$("#startTime").val(currentTime());
})


function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    var content=row.content;
    content=content.replace("\r\n", "\\r\\n");
    result +="<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"addPreComplain('"+ row.appId+"','"+row.channelId+"','"+row.destNumber+"','"+row.srcNumber+"','"+content+"','"+row.submitTime +"')\" title='点击添加'>添加投诉</button>";
    
    return result;
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

/*function getLastMonthYestdy(){
    var date = new Date(); //        1    2    3    4    5    6    7    8    9   10    11   12月
    var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);
    var strYear = date.getFullYear();
    var strDay = date.getDate();
    var strMonth = date.getMonth()+1;
    if(strYear%4 == 0 && strYear%100 != 0){//一、解决闰年平年的二月份天数   //平年28天、闰年29天//能被4整除且不能被100整除的为闰年
        daysInMonth[2] = 29;
    }
    if(strMonth - 1 == 0) //二、解决跨年问题
    {
        strYear -= 1;
        strMonth = 12;
    }
    else
    {
        strMonth -= 1;
    }
//    strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];
    strDay = Math.min(strDay,daysInMonth[strMonth]);//三、前一个月日期不一定和今天同一号，例如3.31的前一个月日期是2.28；9.30前一个月日期是8.30

    if(strMonth<10)//给个位数的月、日补零
    {
        strMonth="0"+strMonth;
    }
    if(strDay<10)
    {
        strDay="0"+strDay;
    }
    datastr = strYear+"-"+strMonth+"-"+strDay;
    return datastr;

}*/

function addPreComplain(appId,channelId,destNumber,srcNumber,content,submitTime) {
	mark=true;
	if(passed('添加投诉')){
		$('.popup_de .show_msg').text('确定要添加到投诉吗？');
		$('.popup_de .btn_cancel').css('display','inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			if(mark){
				$.post("addToPreComplain.action",
						{appId:appId,
					channelId:channelId,
					destNumber:destNumber,
					srcNumber:srcNumber,
					content:content,
					submitTime:submitTime},
					function(data){
						if(data.result==1){
							$('.popup_de .show_msg').text('添加成功！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function(){
								$('.popup_de').removeClass('bbox');
							})
							$('#complaintab').bootstrapTable('refresh',{url:'findMtList.action'});
						}else{
							$('.popup_de .show_msg').text('添加失败，请重新添加！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function(){
								$('.popup_de').removeClass('bbox');
							})
						}
					}
					
				)
				mark=false;
			}
		})
		
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de .btn_cancel').css('display','none');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
	
}

function ChannelName(value, row, index) {
	if(value == null && row.channelName != null){
		return row.channelName;
	}
	var name = "";
	name = value + ":" + row.channelName;
	return name;
}

function tableHeight() {
	return $(window).height() - 80;
}


function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		destNumber : $("#destNumber").val(),// 手机号
		srcNumber : $("#srcNumber").val(),// 源号码
		startTime : $("#startTime").val(),// 开始时间
		endTime : $("#endTime").val(),// 结束时间
	}
}

function querySearchParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		appId : $("#searchAppId").val(),
		channelId : $("#searchChannelId").val(),
		destNumber : $("#searchDestNumber").val(),// 手机号
		srcNumber : $("#searchSrcNumber").val(),// 源号码
		content : $("#searchContent").val(),//
	}
}

function changeDateFormat(cellval) {
	var dateVal = cellval + "";
	if (cellval != null) {
		var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1): date.getMonth() + 1;
		var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
		var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
		var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
		return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
	}
	return "";
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


