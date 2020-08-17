$(function() {
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
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
     	url:"findRedisList.action",//请求后台url
     	height:tableHeight(),//高度调整
     	dataType:"json",//返回的数据格式
     	cache:false,//是否缓存 默认是true
     	striped: true, //是否显示行间隔色
     	uniqueId: "id",  //每一行的唯一标识，一般为主键列
     	showRefresh:false,//刷新按钮
     	showColumns:false,//是否显示所有的列
     	clickToSelect: false,//是否启用点击选中行
     	toolbarAlign:'right',
     	buttonsAlign:'right',//按钮对齐方式
     	toolbar:'#toolbar',//指定工作栏
     	columns:[
         	{
         		title:'名称',
         		field:'remark'
         	},
         	{
         		title:'键',
         		field:'key',
         		formatter : function(value, row, index) {
    				return "<a href='javascript:;' class='btn btn-xs blue' onclick=\"findDetails('" + value +"','"+ row.name + "')\">"+value+"</a>";
    			}

         	},
         	{
         		title:'端口',
         		field:'port'
         	},{
         		title:'类型',
         		field:'type',
         	},
         	{
         		title:'键详情',
         		field:'keyDetail',
         	},
         	{
         		title:'值详情',
         		field:'valueDetail',
         	},
         	{
         		title:'属于',
         		field:'name'
         	},
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

	$("#btn_priSub").click(function() {
		var value=$("#key").val();
		var name=$("#searchName").val();
		var key=$("#searchValue").val();
		if(value == '' || value == null || value == undefined){
			$('.popup_de .show_msg').text('键不能为空');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}else{
			$.ajax({
				url :"findRedisResult.action",
				type:"post",
				data:{
					key:key,
					value:value,
					name:name
				},
				success:function(obj){
					var result=obj.result;
					if(result==null  || result == undefined || result == ''){
						result="查询不到对应结果!";
					}
					if(result == true){
						result="存在!";
					}
					$("#value").val(result);
				},
				dataType:"json"
			});
		}
	});
	$(".btn_close").click(function() {
		$("#key").val('');
		$("#value").val('');
	});
})
function tableHeight() {
    return $(window).height()-50;
}
//key增加链接
function findDetails(value,name){
	if(value=='u:version'){
		$("#key").attr("placeholder","请输入：对象名称，例：account");
	}else if(value=='id:match:name'){
		$("#key").attr("placeholder","请输入：账号ID，例：1");
	}else if(value=='logic:province'){
		$("#key").attr("placeholder","请输入：分省主键ID，例：1");
	}else if(value=='logic:blacksign'){
		$("#key").attr("placeholder","请输入：签名，例：阿里巴巴");
	}else if(value=='logic:policies'){
		$("#key").attr("placeholder","请输入：账号ID，例：1");
	}else if(value=='logic:extsign'){
		$("#key").attr("placeholder","请输入：账号ID_扩展号，例：1_10690000");
	}else if(value=='logic:modispatch'){
		$("#key").attr("placeholder","请输入：长号码，例：10690000");
	}else if(value=='logic:model'){
		$("#key").attr("placeholder","请输入：模板主键ID，例：1");
	}else if(value=='AD:account_id'){
		$("#key").attr("placeholder","请输入：账号ID，例：1");
	}else if(value=='MOAD:account_id'){
		$("#key").attr("placeholder","请输入：账号ID，例：1");
	}else if(value=='acc:balance'){
		$("#key").attr("placeholder","请输入：账号ID，例：1");
	}else if(value=='logic:minlimit'){
		$("#key").attr("placeholder","请输入：账号ID_手机号，例：1_13800000000");
	}else if(value=='logic:daylimit'){
		$("#key").attr("placeholder","请输入：手机号_账号ID，例：13800000000_1");
	}else if(value=='logic:daylimit:content'){
		$("#key").attr("placeholder","请输入：手机号_账号ID_内容，例：13800000000_1_验证码");
	}else if(value=='msgext:ranext'){
		$("#key").attr("placeholder","请输入：长号，例：10690000");
	}else if(value=='ranext:msgext'){
		$("#key").attr("placeholder","请输入：随机数，例：131420");
	}else if(value=='logic:account'){
		$("#key").attr("placeholder","请输入：账号名称，例：jjxt");
	}else if(value=='logic:channel'){
		$("#key").attr("placeholder","请输入：通道ID，例：1");
	}else if(value=='logic:presegment'){
		$("#key").attr("placeholder","请输入：手机号前7位，例：1380000");
	}else if(value=='logic:numsegment'){
		$("#key").attr("placeholder","请输入：手机号前3位，例：138");
	}else if(value=='logic:blackkeyword'){
		$("#key").attr("placeholder","请输入：敏感词主键ID，例：1");
	}else if(value=='logic:glsetting'){
		$("#key").attr("placeholder","请输入：配置键，例：key");
	}else if(value=='logic:report:config'){
		$("#key").attr("placeholder","请输入：uri，例：v2/send");
	}else if(value=='logic:resend'){
		$("#key").attr("placeholder","请输入：重发Id，例：7");
	}else if(value=='logic:timer:task'){
		$("#key").attr("placeholder","请输入：任务名，例：SFTPFileMonPull");
	}else if(value=='logic:channel:template:(channelId)'){
		$("#key").attr("placeholder","请输入：模板通道Id，例：408");
	}else if(value=="logic:sign:ext"){
		$("#key").attr("placeholder","请输入：签名，例：久佳");
	}else if(value=="logic:sign:ext:max"){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text("最大值为"+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='sync:shuntphone'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='msgkey:msgid'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='SYNC:MO'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='SYNC:MT'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='SYNC:RPT'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='audit:appId:channelId:近似度Hash'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='logic:audit:cont'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='auditModel:(appId)'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='sync:black2'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='msgtemplate:(appId)'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='logic:monthlimit:(appId)'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else if(value=='logic:channel:monthlimit:(channelId)'){
		$.ajax({
			url :"findRedisResult.action",
			type:"post",
			async:false,
			data:{
				key:value,
				name:name
			},
			success:function(obj){
				$('.popup_de .show_msg').text('当前数量：'+obj.result);
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			},
			dataType:"json"
		});
		return ;
	}else{
		$("#key").attr("placeholder","暂时不提供该功能！");
	}
	$("#searchValue").val(value);
	$("#searchName").val(name);
	$("#resultTab").modal();
}

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