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
		url : "findKhdOutboxList.action",// 请求后台url
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
		uniqueId : "ID", // 每一行的唯一标识，一般为主键列
		pageSize : 20,// 单页记录数
		pageList : [ 50, 100, 200 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [{
			title : '账户',
			field : 'appName',
			width : 70
		}, {
			title : '文件名称',
			field : 'fileName',
			width : 100
		}, {
			title : '发送数',
			field : 'sendNum',
			width : 70,
			formatter : function (value, row, index){
				if(value == null || value == '' || value == undefined ){
					return "0";
				}
				return value;
			}
		},  {
			title : '成功数',
			field : 'sendSuccNum',
			width : 70,
			formatter : function (value, row, index){
				if(value == null || value == '' || value == undefined ){
					return "0";
				}
				return value;
			}
		},  {
			title : '失败数',
			field : 'sendFailNum',
			width : 70,
			formatter : function (value, row, index){
				if(value == null || value == '' || value == undefined ){
					return "0";
				}
				return value;
			}
		}, {
			title : '时间',
			field : 'dateTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			},
			width : 150
		}, {
			title : '结果',
			field : 'remarks',
			width : 100
		}, {
			title : '内容',
			field : 'content',
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

	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
		});
	});
})

function tableHeight() {
	return $(window).height() - 120;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		appId : $("#appId").val(),
	}
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
		var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
				.getSeconds();

		return date.getFullYear() + "-" + month + "-" + currentDate + " "
				+ hours + ":" + minutes + ":" + seconds;
	}
	return "";
}
function actionFormatter(value, row, index) {
	var content = value;
	if(content == '' || content == null || content == undefined){
		return "<a href='javascript:;' onclick=\"findDetails('"+row.linkId+ "')\" title='详情'>"+content+"</a>";
	}
	if (content.length > 70) {
		return "<a href='javascript:;' onclick=\"findDetails('"+row.linkId+ "')\" title='详情'>"+content.substring(0, 70) + "···</a>";
	}
	return "<a href='javascript:;' onclick=\"findDetails('"+row.linkId+ "')\" title='详情'>"+ content + "</a>";;
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