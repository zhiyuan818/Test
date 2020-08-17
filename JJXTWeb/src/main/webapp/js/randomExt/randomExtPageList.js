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
		url : "findRandomExtPageList.action",// 请求后台url
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
		pageSize : 50,// 单页记录数
		pageList : [ 20, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar : '#toolbar',// 指定工作栏
		columns : [ {
     		title:'全选',
     		field:'select',
     		checkbox:true,
     		width:25,
     		align:'center',
     		valign:'middle',
     	},{
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
			title : '6位随机扩展',
			field : 'randomExt'
		}, {
			title : '消息原扩展',
			field : 'msgext',
		}, {
			title : '更新时间',
			field : 'updateTime',
			formatter : function(value, row, index) {
				return changeDateFormat(value)
			}
		}, {
			title : '操作',
			field : 'id',
			width : 150,
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
		locale : 'zh-CN'// 中文支持,
	})
	
	$("#search_btn").click(function () {
       	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#appId").val('');
    	$("#randomExt").val('');
    	$("#msgext").val('');
    	
    	$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});	  
    	
    	$('#mytab').bootstrapTable('refreshOptions',{pageNumber:1,limit:50});
    });
	

	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$("#edit_saveBtn").attr("disabled", false);
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$("#edit_saveBtn").attr("disabled", false);
		$('.popup_de').removeClass('bbox');
	})

})


$(function() {
	    
	    $('#btn_del').click(function() {
	    	mark=true;
			if (passed('批量删除')) {
				var dataArr = $('#mytab').bootstrapTable('getSelections');
				$('.popup_de .show_msg').text('确定要删除吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
				$('.popup_de .btn_submit').one('click', function() {
					var ID = [];
					var i = 0;
					for (i; i < dataArr.length; i++) {
						ID[i] = dataArr[i].id;
					}
					if (ID.length > 0) {
						if(mark){
							$.post("delRandomExtBatch.action", {
								ids : ID
							}, function(data) {
								if (data.result > 0) {
									$('.popup_de .show_msg').text('删除成功！');
									$('.popup_de .btn_cancel').css('display', 'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$('#btn_del').addClass('fadeOutRight');
										setTimeout(function() {
											$('#btn_del').css('display', 'none');
										}, 400);
										$('.popup_de').removeClass('bbox');
									})
									$('#mytab').bootstrapTable('refresh', {
										url : 'findRandomExtPageList.action'
									});
								} else {
									$('.popup_de .show_msg').text('删除出错！');
									$('.popup_de .btn_cancel').css('display', 'none');
									$('.popup_de').addClass('bbox');
									$('.popup_de .btn_submit').one('click', function() {
										$("#edit_saveBtn").attr("disabled", false);
										$('.popup_de').removeClass('bbox');
									})
									$('#mytab').bootstrapTable('refresh', {
										url : 'findRandomExtPageList.action'
									});
								}
							})
							mark=false;
						}
					}

				})
			} else {
				$('.popup_de .show_msg').text('没有权限，请联系管理员！');
				$('.popup_de .btn_cancel').css('display', 'none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
			}

		});
		// 批量删除按钮的出现与消失
		$('.table').change(function() {
			var dataArr = $('#mytab .selected');
			if (dataArr.length >= 1) {
				$('#btn_del').css('display', 'block').removeClass(
						'fadeOutRight').addClass('animated fadeInRight');
			} else {
				$('#btn_del').addClass('fadeOutRight');
				setTimeout(function() {
					$('#btn_del').css('display', 'none');
				}, 400);
			}
		});
		$("[name='btSelectAll']").click(function(){
			var dataArr = $('#mytab .selected');
			if (dataArr.length >= 1) {
				$('#btn_del').css('display', 'block').removeClass(
						'fadeOutRight').addClass('animated fadeInRight');
			} else {
				$('#btn_del').addClass('fadeOutRight');
				setTimeout(function() {
					$('#btn_del').css('display', 'none');
				}, 400);
			}
		});
});

//删除
function delRandomExt(id) {
	mark=true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗？');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			if(mark){
				$.post("deleteRandomExtById.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findRandomExtPageList.action'
						});
					} else {
						$('.popup_de .show_msg').text('删除失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
				});
				mark=false;
			}

		})
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de .btn_cancel').css('display', 'none');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}

}


function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-primary btn-xs' id='delete_randomExt' onclick=\"delRandomExt('"
			+ row.id + "')\" title='点击删除'>删除</button>";
	return result;
}

function tableHeight() {
	return $(window).height();
}

function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,	// 页码
		
		appId:$("#appId").val(),
		randomExt:$("#randomExt").val(),
		msgext:$("#msgext").val()
	}
}

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
