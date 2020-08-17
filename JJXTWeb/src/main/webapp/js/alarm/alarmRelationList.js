function UpdateBean(sendId,isPhone,isEmail,isWechat) {
	this.sendId = sendId;
	this.isPhone = isPhone;
	this.isEmail = isEmail;
	this.isWechat = isWechat;
}

$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#userTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	// 生成用户数据
	$('#userTab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findUserRelation.action",// 请求后台url
		height : tableHeight(),// 高度调整
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : true,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryUserParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		pageSize : 20,// 单页记录数
		pageList : [ 20, 30, 50 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [ {
			title : 'id',
			field : 'sendId',
			/*visible : false*/
		}, {
			title : '用户简称',
			field : 'userName'
		}, {
			title : '用户名',
			field : 'describe'
		},{
			title : '级别',
			field : 'level'
		}, {
			title : '类型ID',
			align : 'center',
			field : 'typeId',
			visible : false
		}, {
			title : '短信通知',
			field : 'isPhone',
			align : 'center',
			formatter : function(value, row, index) {
				var result = "";
				if(value == "yes"){
					result += "<input type='checkbox' class='form-control' checked='checked' id='phone' value='yes' style='zoom:60%;'>"
				}else{
					result += "<input type='checkbox' class='form-control' id='phone' value='no' style='zoom:60%;'>"
				}
				return result;
			}
		}, {
			title : '邮件通知',
			field : 'isEmail',
			align : 'center',
			formatter : function(value, row, index) {
				var result = "";
				if(value == "yes"){
					result += "<input type='checkbox' class='form-control' checked='checked' id='email' value='yes' style='zoom:60%;'>"
				}else{
					result += "<input type='checkbox' class='form-control' id='email' value='no' style='zoom:60%;'>"
				}
				return result;
			}
		}, {
			title : '微信通知',
			field : 'isWechat',
			align : 'center',
			formatter : function(value, row, index) {
				var result = "";
				if(value == "yes"){
					result += "<input type='checkbox' class='form-control' checked='checked' id='wechat' value='yes' style='zoom:60%;'>"
				}else{
					result += "<input type='checkbox' class='form-control' id='wechat' value='no' style='zoom:60%;'>"
				}
				return result;
			}
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
		onPageChange : function(number, size) {
			var tr=$("#userTab tr").length;
			var list = new Array();
			
			for(var i=1;i<tr;i++){
				var isPhone = document.getElementById("userTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[0].checked ? "yes" : "no";
				var isEmail = document.getElementById("userTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[1].checked ? "yes" : "no";
				var isWechat = document.getElementById("userTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[2].checked ? "yes" : "no";
				var sendId = document.getElementById("userTab").rows[i].cells[0].innerHTML;
				list.push(new UpdateBean(sendId,isPhone,isEmail,isWechat));
			}
			if(list.length > 0){
				var json = JSON.stringify(list);
				$.post("validatorRelation.action",
						{json : json},
						function(data) {
					if(data.result == 0){
						mark = true;
						$('.popup_de .show_msg').text('是否保存当前页的修改?');
						$('.popup_de').addClass('bbox');
//						$('.popup_de .btn_cancel').css('display', 'inline-block');
						$('.popup_de .btn_submit').one('click',function(){
							$('.popup_de').removeClass('bbox');
							if(mark){
								$.ajax({
									url : "updateRelationBatch.action",
									data : {'json' : json},
									type : "post",
									success:function(data){
										if (data.result > 0) {
											$('.popup_de .show_msg').text('修改成功！');
											$('.popup_de').addClass('bbox');
											$('.popup_de .btn_submit').one('click', function() {
												$('.popup_de').removeClass('bbox');
											})
											
										} else {
											$('.popup_de .show_msg').text('修改失败！');
											$('.popup_de .btn_cancel').css('display','none');
											$('.popup_de').addClass('bbox');
											$('.popup_de .btn_submit').one('click',function() {
												$('.popup_de').removeClass('bbox');
											})
										}
									}
								});
							}
							mark = false;
						});
						
					}
				}, "json");
				
			}
		},
		locale : 'zh-CN',// 中文支持
	})
	/*addValidator();
	updateValidator();
	addTypeValidator();*/
	
	$("#search_user").click(function () {
       	$('#userTab').bootstrapTable('refreshOptions',{pageNumber:1});
       	
	});
});


/**
 * 类型页面
 */
$("#typePage").click(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#typeTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	// 生成用户数据
	$('#typeTab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findTypeRelation.action",// 请求后台url
		height : tableHeight(),// 高度调整
		dataType : "json",// 返回的数据格式
		cache : false,// 是否缓存 默认是true
		striped : true, // 是否显示行间隔色
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pagination : true,// 是否分页
		queryParamsType : 'limit',
		queryParams : queryTypeParams,
		sortable : true, // 是否启用排序
		sortOrder : "asc",// 排序方式
		sidePagination : 'server', // 服务器端的分页
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		pageSize : 20,// 单页记录数
		pageList : [ 20, 30, 50 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [ {
			title : 'id',
			field : 'sendId',
			/*visible : false*/
		}, {
			title : '报警类型',
			field : 'type'
		}, {
			title : '级别',
			field : 'level'
		}, {
			title : '短信通知',
			field : 'isPhone',
			align : 'center',
			formatter : function(value, row, index) {
				var result = "";
				if(value == "yes"){
					result += "<input type='checkbox' class='form-control' checked='checked' id='isphone' value='yes' style='zoom:60%;'>"
				}else{
					result += "<input type='checkbox' class='form-control' id='isphone' value='no' style='zoom:60%;'>"
				}
				return result;
			}
		}, {
			title : '邮件通知',
			field : 'isEmail',
			align : 'center',
			formatter : function(value, row, index) {
				var result = "";
				if(value == "yes"){
					result += "<input type='checkbox' class='form-control' checked='checked' id='isemail' value='yes' style='zoom:60%;'>"
				}else{
					result += "<input type='checkbox' class='form-control' id='isemail' value='no' style='zoom:60%;'>"
				}
				return result;
			}
		}, {
			title : '微信通知',
			field : 'isWechat',
			align : 'center',
			formatter : function(value, row, index) {
				var result = "";
				if(value == "yes"){
					result += "<input type='checkbox' class='form-control' checked='checked' id='iswechat' value='yes' style='zoom:60%;'>"
				}else{
					result += "<input type='checkbox' class='form-control' id='iswechat' value='no' style='zoom:60%;'>"
				}
				return result;
			}
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
		onPageChange : function(number, size) {
			var tr=$("#typeTab tr").length;
			var list = new Array();
			
			for(var i=1;i<tr;i++){
				var isPhone = document.getElementById("typeTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[0].checked ? "yes" : "no";
				var isEmail = document.getElementById("typeTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[1].checked ? "yes" : "no";
				var isWechat = document.getElementById("typeTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[2].checked ? "yes" : "no";
				var sendId = document.getElementById("typeTab").rows[i].cells[0].innerHTML;
				list.push(new UpdateBean(sendId,isPhone,isEmail,isWechat));
			}
			if(list.length > 0){
				var json = JSON.stringify(list);
				$.post("validatorRelation.action",
						{json : json},
						function(data) {
					if(data.result == 0){
						mark = true;
						$('.popup_de .show_msg').text('是否保存当前页的修改?');
						$('.popup_de').addClass('bbox');
//						$('.popup_de .btn_cancel').css('display', 'inline-block');
						$('.popup_de .btn_submit').one('click',function(){
							$('.popup_de').removeClass('bbox');
							if(mark){
								$.ajax({
									url : "updateRelationBatch.action",
									data : {'json' : json},
									type : "post",
									success:function(data){
										if (data.result > 0) {
											$('.popup_de .show_msg').text('修改成功！');
											$('.popup_de').addClass('bbox');
											$('.popup_de .btn_submit').one('click', function() {
												$('.popup_de').removeClass('bbox');
//												$('#typeTab').bootstrapTable('refresh',{url : 'findTypeRelation.action'});
											})
											
										} else {
											$('.popup_de .show_msg').text('修改失败！');
											$('.popup_de .btn_cancel').css('display','none');
											$('.popup_de').addClass('bbox');
											$('.popup_de .btn_submit').one('click',function() {
												$('.popup_de').removeClass('bbox');
											})
										}
									}
								});
							}
							mark = false;
						});
						
					}
				}, "json");
				
			}
		},
		locale : 'zh-CN',// 中文支持
	})
	/*addValidator();
	updateValidator();
	addTypeValidator();*/
	
	$("#search_type").click(function () {
       	$('#typeTab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
});


//批量修改用户列表
$("#update_user").click(function() {
	mark = true;
	if(passed('修改')){
		$('.popup_de .show_msg').text('确定要保存修改吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click',function(){
			$("update_user").attr("disabled",true);
			var tr=$("#userTab tr").length;
			var list = new Array();
			
			for(var i=1;i<tr;i++){
				var isPhone = document.getElementById("userTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[0].checked ? "yes" : "no";
				var isEmail = document.getElementById("userTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[1].checked ? "yes" : "no";
				var isWechat = document.getElementById("userTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[2].checked ? "yes" : "no";
				var sendId = document.getElementById("userTab").rows[i].cells[0].innerHTML;
				list.push(new UpdateBean(sendId,isPhone,isEmail,isWechat));
			}
			if(list.length > 0 && mark){
				$.ajax({
					url : "updateRelationBatch.action",
					data : {'json' : JSON.stringify(list)},
					type : "post",
					success:function(data){
						if (data.result > 0) {
							$('.popup_de .show_msg').text('修改成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#userTab').bootstrapTable('refresh',{url : 'findUserRelation.action'});
							})
							
						} else {
							$('.popup_de .show_msg').text('修改失败，请重新修改！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function() {
								$('.popup_de').removeClass('bbox');
							})
						}
					}
				});
				mark = false;
			}
		})
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
});


//批量修改类型列表
$("#update_type").click(function() {
	mark = true;
	if(passed('修改')){
		$('.popup_de .show_msg').text('确定要保存修改吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click',function(){
			$("update_type").attr("disabled",true);
			var tr=$("#typeTab tr").length;
			var list = new Array();
			
			for(var i=1;i<tr;i++){
				var isPhone = document.getElementById("typeTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[0].checked ? "yes" : "no";
				var isEmail = document.getElementById("typeTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[1].checked ? "yes" : "no";
				var isWechat = document.getElementById("typeTab").getElementsByTagName("TR")[i].getElementsByTagName("INPUT")[2].checked ? "yes" : "no";
				var sendId = document.getElementById("typeTab").rows[i].cells[0].innerHTML;
				list.push(new UpdateBean(sendId,isPhone,isEmail,isWechat));
			}
			if(list.length > 0 && mark){
				$.ajax({
					url : "updateRelationBatch.action",
					data : {'json' : JSON.stringify(list)},
					type : "post",
					success:function(data){
						if (data.result > 0) {
							$('.popup_de .show_msg').text('修改成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#typeTab').bootstrapTable('refresh',{url : 'findTypeRelation.action'});
							})
							
						} else {
							$('.popup_de .show_msg').text('修改失败，请重新修改！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function() {
								$('.popup_de').removeClass('bbox');
							})
						}
					}
				});
				mark = false;
			}
		})
	}else{
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			$('.popup_de').removeClass('bbox');
		})
	}
});

//弹出框关闭按钮事件
$('.popup_de .popup_close').click(function() {
	$("#update_user").attr("disabled", false);
	$("#update_type").attr("disabled", false);
	$('.popup_de').removeClass('bbox');
});
// 弹出框取消按钮事件
$('.popup_de .btn_cancel').click(function() {
	$("#update_user").attr("disabled", false);
	$("#update_type").attr("disabled", false);
	$('.popup_de').removeClass('bbox');
});



function tableHeight() {
	return $(window).height() - 90;
}


function queryUserParams(params) {
	
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,
		typeId:$("#seltypeId").val(),
		level:$("#searchLevel1").val(),
		selectType : $("input[name='selectType1']:checked").val()
	}
}

function queryTypeParams(params) {
	//获取跳转前pageNumber
	
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,
		userId:$("#seluserId").val(),
		level:$("#searchLevel2").val(),
		selectType : $("input[name='selectType2']:checked").val()
	}
}
/*
function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-default btn-xs' onclick=\"delStrategy('"
			+ value + "')\" title='删除'>删除</button>";
	return result;
}
*/


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
