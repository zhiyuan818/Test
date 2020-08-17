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
		url : "findAuditPageList.action",// 请求后台url
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
		pageList : [10, 50, 100 ],// 分页步进值
		showRefresh : true,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar:'#toolbar',//指定工作栏
		columns : [{
    		title:'全选',
    		field:'select',
    		checkbox:true,
    		width:25,
    		align:'center',
    		valign:'middle'
    	}, {
			title : '通道',
			field : 'channelName',
			width : 150
		}, {
			title : '账户',
			field : 'appName',
			width : 50
		}, {
			title : '命中的必审词',
			field : 'auditResult',
			width : 110
		}, {
			title : '数量',
			field : 'amount',
			width : 50
		}, {
			title : '内容',
			field : 'content',
			formatter : changeContent,
			align : 'center',
			valign : 'middle',
		}, {
			title : '通过',
			field : 'flag',
			align : 'center',
			valign : 'middle',
			formatter : actionOrdinaryFormatter,
			width : 100
		}, {
			title : '驳回',
			field : 'flag',
			align : 'center',
			valign : 'middle',
			formatter : actionModelFormatter,
			width : 100
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
	})
		 //根据复选框批量删除
	 $('#btn_pass').click(function(){
		 if(passed('通过')){
	    	var dataArr=$('#mytab').bootstrapTable('getSelections');
			var map = new Map();
			for(var i=0;i<dataArr.length;i++){
				map[dataArr[i].flag]=dataArr[i].content;
			}
			$.ajax({
				url : "auditOperation.action",
				data : {
					key : JSON.stringify(map),
					flag: 'T'
				},
				dataType : "text",
				type : "post",
				success : function(data) {
					data = eval('(' + data + ')');
					if (data.result == '0') {
						toastr.success("提交成功!");
					} else if(data.result == '-1010'){
						toastr.info("当前审核队列处理正在排队，请稍等重试！");
					} else if (data.result == '-1008') {
						toastr.info("已提交，不要重复提交!");
					} else {
						toastr.warning("提交失败!");
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAuditPageList.action'
					});
				},
				error : function() {
					console.log('error');
				}
			});
		 }else{
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
		}
	 })
	 $('#btn_del').click(function(){
		 if(passed('驳回')){
	    	var dataArr=$('#mytab').bootstrapTable('getSelections');
	    	var map = new Map();
			for(var i=0;i<dataArr.length;i++){
				map[dataArr[i].flag]=dataArr[i].content;
			}
			$.ajax({
				url : "auditOperation.action",
				data : {
					key : JSON.stringify(map),
					flag: 'F'
				},
				dataType : "text",
				type : "post",
				success : function(data) {
					data = eval('(' + data + ')');
					if (data.result == '0') {
						toastr.success("提交成功!");
					} else if(data.result == '-1010'){
						toastr.info("当前审核队列处理正在排队，请稍等重试！");
					} else if (data.result == '-1008') {
						toastr.info("已提交，不要重复提交!");
					} else {
						toastr.warning("提交失败!");
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAuditPageList.action'
					});
				},
				error : function() {
					console.log('error');
				}
			});
		 }else{
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
				$('.popup_de').removeClass('bbox');
			})
		}
	 })
	 $('.table').change(function(){
		var dataArr=$('#mytab .selected');
		if(dataArr.length>=1){
			$('#btn_del').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
			$('#btn_pass').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		}else{
			$('#btn_del').addClass('fadeOutRight');
			setTimeout(function(){
				$('#btn_del').css('display','none');
			},400);	
			$('#btn_pass').addClass('fadeOutRight');
			setTimeout(function(){
				$('#btn_pass').css('display','none');
			},400);	
		}
	});
	$("[name='btSelectAll']").click(function(){
		var dataArr=$('#mytab .selected');
		if(dataArr.length>=1){
			$('#btn_del').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
			$('#btn_pass').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		}else{
			$('#btn_del').addClass('fadeOutRight');
			setTimeout(function(){
				$('#btn_del').css('display','none');
			},400);	
			$('#btn_pass').addClass('fadeOutRight');
			setTimeout(function(){
				$('#btn_pass').css('display','none');
			},400);	
		}
	});
	$("#search_btn").click(function () {
       	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#searchAppId").val('');
    	$("#searchChannelId").val('');
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1,limit:20});
    });
})

function tableHeight() {
	return $(window).height() - 50;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,
		appId: $("#searchAppId").val(),
		channelId : $("#searchChannelId").val()
	// 页码
	}
}
function changeContent(value,row,index) {
	var content = value;
	if (content.length > 200) {
		return "<a href='javascript:;' data-toggle='modal'  data-target='#Details' onclick=\"findDetails('"
				+ row.flag+ "')\" title='详情'>"+content.substring(0, 200)+"...</a>";
	}
	return "<a href='javascript:;' data-toggle='modal'  data-target='#Details' onclick=\"findDetails('"
			+ row.flag + "')\" title='详情'>"+content+"</a>";
}
function actionOrdinaryFormatter(value, row, index) {
	var result = "";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"passOperate('"
			+ value
			+ "')\" title='通过'><span class='glyphicon glyphicon-ok'></span></a>";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"passAndTemplateOperate('"
		+ value
		+ "')\" title='通过加模板'><span class='glyphicon glyphicon-ok-sign'></span></a>";

	return result;
}
function actionModelFormatter(value, row, index) {
	var result = "";
	result += "<a href='javascript:;' class='btn btn-xs blue'  onclick=\"overOperate('"
			+ value
			+ "')\" title='驳回'><span class='glyphicon glyphicon-remove'></span></a>";
	result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"overAndTemplateOperate('"
			+ value
			+ "')\" title='驳回加模板'><span class='glyphicon glyphicon-remove-sign'></span></a>";
	return result;
}
function findDetails(value) {
	$.ajax({
		url : "findAuditDetail.action",
		type:"post",
		data:{key:value},
		success:function(obj){
			$("#Details_app_name").html(obj.appName);
			$("#Details_app_id").html(obj.appId);
			$("#Details_channel_name").html(obj.channelName);
			$("#Details_channel_id").html(obj.channelId);
			$("#Details_auditResult").html(obj.auditResult);
			$("#Details_amount").html(obj.amount);
			$("#Details_content").html(obj.content);
		},
		dataType:"json"
	});
}
function passOperate(key) {
	if (passed('通过')) {
		var content='';
		$.ajax({
			url : "findAuditDetail.action",
			type:"post",
			data:{key:key},
			async:false,
			success:function(obj){
				content=obj.content;
			},
			dataType:"json"
		});
		var map=new Map();
		map[key]=content;
		$.ajax({
			url : "auditOperation.action",
			data : {
				key : JSON.stringify(map),
				flag: 'T'
			},
			dataType : "text",
			type : "post",
			success : function(data) {
				data = eval('(' + data + ')');
				if (data.result == '0') {
					toastr.success("提交成功!");
				} else if(data.result == '-1010'){
					toastr.info("当前审核队列处理正在排队，请稍等重试！");
				} else if (data.result == '-1008') {
					toastr.info("已提交，不要重复提交!");
				} else {
					toastr.warning("提交失败!");
				}
				$('#mytab').bootstrapTable('refresh', {
					url : 'findAuditPageList.action'
				});
			},
			error : function() {
				console.log('error');
			}
		});
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function passAndTemplateOperate(key) {
	if (passed('通过')) {
		var content='';
		$.ajax({
			url : "findAuditDetail.action",
			type:"post",
			data:{key:key},
			async:false,
			success:function(obj){
				content=obj.content;
			},
			dataType:"json"
		});
		var map=new Map();
		map[key]=content;
		$.ajax({
			url : "auditOperation.action",
			data : {
				key : JSON.stringify(map),
				flag: 'T',
				template:'white'
			},
			dataType : "text",
			type : "post",
			success : function(data) {
				data = eval('(' + data + ')');
				if (data.result == '0') {
					toastr.success("提交成功!");
				} else if(data.result == '-1010'){
					toastr.info("当前审核队列处理正在排队，请稍等重试！");
				} else if (data.result == '-1008') {
					toastr.info("已提交，不要重复提交!");
				} else {
					toastr.warning("提交失败!");
				}
				$('#mytab').bootstrapTable('refresh', {
					url : 'findAuditPageList.action'
				});
			},
			error : function() {
				console.log('error');
			}
		});
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function overOperate(key) {
	if (passed('驳回')) {
		var content='';
		$.ajax({
			url : "findAuditDetail.action",
			type:"post",
			data:{key:key},
			async:false,
			success:function(obj){
				content=obj.content;
			},
			dataType:"json"
		});
		var map=new Map();
		map[key]=content;
		$.ajax({
			url : "auditOperation.action",
			data : {
				key : JSON.stringify(map),
				flag: 'F'
			},
			dataType : "text",
			type : "post",
			success : function(data) {
				data = eval('(' + data + ')');
				if (data.result == '0') {
					toastr.success("提交成功!");
				} else if(data.result == '-1010'){
					toastr.info("当前审核队列处理正在排队，请稍等重试！");
				} else if (data.result == '-1008') {
					toastr.info("已提交，不要重复提交!");
				} else {
					toastr.warning("提交失败!");
				}
				$('#mytab').bootstrapTable('refresh', {
					url : 'findAuditPageList.action'
				});
			},
			error : function() {
				console.log('error');
			}
		});
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function overAndTemplateOperate(key) {
	if (passed('驳回')) {
		var content='';
		$.ajax({
			url : "findAuditDetail.action",
			type:"post",
			data:{key:key},
			async:false,
			success:function(obj){
				content=obj.content;
			},
			dataType:"json"
		});
		var map=new Map();
		map[key]=content;
		$.ajax({
			url : "auditOperation.action",
			data : {
				key : JSON.stringify(map),
				flag: 'F',
				template:'black'
			},
			dataType : "text",
			type : "post",
			success : function(data) {
				data = eval('(' + data + ')');
				if (data.result == '0') {
					toastr.success("提交成功!");
				} else if(data.result == '-1010'){
					toastr.info("当前审核队列处理正在排队，请稍等重试！");
				} else if (data.result == '-1008') {
					toastr.info("已提交，不要重复提交!");
				} else {
					toastr.warning("提交失败!");
				}
				$('#mytab').bootstrapTable('refresh', {
					url : 'findAuditPageList.action'
				});
			},
			error : function() {
				console.log('error');
			}
		});
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
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
})
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
$(function(){
    //参数设置，若用默认值可以省略以下面代码
    toastr.options = {
       "positionClass": "toast-bottom-full-width",//弹出窗的位置
    };
});