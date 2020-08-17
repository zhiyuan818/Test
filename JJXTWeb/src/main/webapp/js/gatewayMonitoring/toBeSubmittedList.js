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
		url : "findToBeSubmittedList.action",// 请求后台url
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
		pageSize : 100,// 单页记录数
		pageList : [ 50, 100, 200 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		paginationShowPageGo: true,
		columns : [ {
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'key',
			field : 'key',
			visible : false
		}, {
			title : 'channelId',
			field : 'channelId',
			visible : false
		}, {
			title : '通道',
			field : 'channelName',
			formatter : changeContent

		}, {
			title : '码类待发数量',
			field : 'codeAmount',
			formatter : actionCodeFormatter
		}, {
			title : '行业营销待发数量',
			field : 'amount',
			formatter : actionFormatter
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
		locale : 'zh-CN'// 中文支持,
	})

})

function tableHeight() {
	return $(window).height() - 60;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber
	// 页码
	}
}
function changeContent(value, row, index) {
	return row.channelId + ":" + value;
}
function actionFormatter(value, row, index) {
	var result = "";
	if(value == null || value == "" || value == undefined){
		return 0;
	}
	result +=value+"&nbsp;&nbsp;<button class='btn btn-primary btn-xs' onclick=\"updateGlobal('"
			+ row.key + "')\" title='切换通道'>切换通道</button>";
	return result;
}
function actionCodeFormatter(value, row, index) {
	var result = "";
	if(value == null || value == "" || value == undefined){
		return 0;
	}
	result +=value+"&nbsp;&nbsp;<button class='btn btn-primary btn-xs' onclick=\"updateGlobal('"
	+ row.codeKey + "')\" title='切换通道'>切换通道</button>";
	return result;
}
function updateGlobal(value) {
	if (passed('切换通道')) {
		$.post("findAmountByKey.action", {
			key : value
		}, function(obj) {
			var codeFlag = '';
			if(value.indexOf("GWCODE")>=0){
				codeFlag = 0;
			}else{
				codeFlag = 1;
			}
			var opt = '';
			var channels=obj.channels;
			for(var i in channels){
				opt+="<option value='"+channels[i].channelId+"'>"+channels[i].platformFlag+":"+channels[i].channelId+":"+channels[i].name+"</option>";
			}
			var cmccopt = '';
			var cmccChannels=obj.cmccChannels;
			for(var i in cmccChannels){
				cmccopt+="<option value='"+cmccChannels[i].channelId+"'>"+cmccChannels[i].platformFlag+":"+cmccChannels[i].channelId+":"+cmccChannels[i].name+"</option>";
			}
			var unicomopt = '';
			var unicomChannels=obj.unicomChannels;
			for(var i in unicomChannels){
				unicomopt+="<option value='"+unicomChannels[i].channelId+"'>"+unicomChannels[i].platformFlag+":"+unicomChannels[i].channelId+":"+unicomChannels[i].name+"</option>";
			}
			var telecomopt = '';
			var telecomChannels=obj.telecomChannels;
			for(var i in telecomChannels){
				telecomopt+="<option value='"+telecomChannels[i].channelId+"'>"+telecomChannels[i].platformFlag+":"+telecomChannels[i].channelId+":"+telecomChannels[i].name+"</option>";
			}
			var flag = '';
			if(obj.gw.flag){
				flag = 1;
			}else{
				flag = 0;
			}
			$("#switchTable").empty();
			$("#switchTable").append("<tr><td class='td'>当前通道</td><td><span id='channelName'>"+obj.gw.channelId + ":" + obj.gw.channelName+"</span><input type='hidden' value='"+obj.gw.channelId+"' id='src_channelId'><input type='hidden' value='"+codeFlag+"' id='flag' /><input type='hidden' value='"+flag+"' id='channelFlag'/></td></tr>");
			$("#switchTable").append("<tr><td class='td'>数量</td><td><input type='number' value='"+obj.gw.amount+"' class='form-control' id='amount' min='1' max='"+obj.gw.amount+"' data-step='1'><input type='hidden' class='form-control' value='"+obj.gw.amount+"' id='oldAmount'></td></tr>");
			if(obj.gw.flag){
				if("yes" == obj.gw.carriers.cmcc){
					$("#switchTable").append("<tr><td class='td'>移动通道</td><td><select name='channelId' id='cmcc_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+cmccopt+"</select></td></tr>");
					$("#cmcc_channelId").selectpicker("refresh");
				}
				if("yes" == obj.gw.carriers.unicom){
					$("#switchTable").append("<tr><td class='td'>联通通道</td><td><select name='channelId' id='unicom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+unicomopt+"</select></td></tr>");
					$("#unicom_channelId").selectpicker("refresh");
				}
				if("yes" == obj.gw.carriers.telecom){
					$("#switchTable").append("<tr><td class='td'>电信通道</td><td><select name='channelId' id='telecom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+telecomopt+"</select></td></tr>");
					$("#telecom_channelId").selectpicker("refresh");
				}
			} else {
				$("#switchTable").append("<tr><td class='td'>目标通道</td><td><select name='channelId' id='dest_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+opt+"</select></td></tr>");
				$("#dest_channelId").selectpicker("refresh");
			}
		}, "json");
		$("#channel").modal();
	} else {
		$('.modal').map(function() {
			$(this).modal('hide');
		});
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
$(function() {
	$("#btn_back").click(function() {
		$("#gwForm")[0].reset();
	});
	$("#btn_sub").click(function() {
		var src_channelId = $("#src_channelId").val();
		var dest_channelId = $("#dest_channelId").val();
		var amount = $("#amount").val();
		var oldAmount = $("#oldAmount").val();
		var flag = $("#flag").val();
		var channelFlag = $("#channelFlag").val();
		var cmcc_channelId = $("#cmcc_channelId").val();
		var unicom_channelId = $("#unicom_channelId").val();
		var telecom_channelId = $("#telecom_channelId").val();
		if(parseInt(amount)<=0){
			$('.popup_de .show_msg').text('数量必须大于0');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		if(parseInt(amount)>parseInt(oldAmount)){
			$('.popup_de .show_msg').text('当前的最大数量为'+oldAmount+",请重新输入");
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		$.post("switchChannel.action", {
			src_channelId : src_channelId,
			dest_channelId : dest_channelId,
			amount : amount,
			flag : flag,
			channelFlag : channelFlag,
			cmcc_channelId : cmcc_channelId,
			unicom_channelId : unicom_channelId,
			telecom_channelId : telecom_channelId
		}, function(obj) {
			var data = obj;
			if (data.result == '0') {
				$('.popup_de .show_msg').text('切换成功!');
				$('.popup_de .btn_cancel').css('display', 'none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			} else {
				$('.popup_de .show_msg').text('切换失败!');
				$('.popup_de .btn_cancel').css('display', 'none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			}
			$('#mytab').bootstrapTable('refresh', {
				url : 'findToBeSubmittedList.action'
			});
		}, "json");
	});
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