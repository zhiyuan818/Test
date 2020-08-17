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
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		columns : [ {
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'channelId',
			field : 'channelId',
			visible : false
		}, {
			title : '通道',
			field : 'channelName',
			formatter : changeContent

		}, {
			title : '码类积压条数',
			field : 'codeNumber',
			formatter : changeCodeNumber
		}, {
			title : '行业积压条数',
			field : 'industryNumber',
			formatter : changeIndustryNumber
		}, {
			title : '营销积压条数',
			field : 'marketNumber',
			formatter : changeMarketNumber
		}, {
			title : '紧急条数',
			field : 'urgentNumber'
		}, {
			title : '下行速度(/s)',
			field : 'gwSubmitSpeed',
			formatter : changeToRed
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

})

function tableHeight() {
	return $(window).height() - 100;
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
function changeToRed(value, row, index) {
	var result = "";
	if(value != null){
		result += "<font color='#cc3300'>"+value+"</font>";
	}
	return result;
}
function changeCodeNumber(value, row, index) {
	var result = "";
	result += value
			+ "&nbsp;&nbsp;<button class='btn btn-default btn-xs' onclick=\"codeSwitchChannel('"
			+ value + "','" + row.channelName + "','" + row.channelId
			+ "')\" title='切换通道'>切换通道</button>";
	return result;
}
function changeIndustryNumber(value, row, index) {
	var result = "";
	result += value
			+ "&nbsp;&nbsp;<button class='btn btn-warning btn-xs' onclick=\"IndustrySwitchChannel('"
			+ value + "','" + row.channelName + "','" + row.channelId
			+ "')\" title='切换通道'>切换通道</button>";
	return result;
}
function changeMarketNumber(value, row, index) {
	var result = "";
	result += value
			+ "&nbsp;&nbsp;<button class='btn btn-info btn-xs' onclick=\"details('"
			+ row.channelId + "')\" title='详细内容'>详细内容</button>";
	return result;
}
function codeSwitchChannel(value, channelName, channelId) {
	if (passed('码类切换')) {
//		$("#channelName").html(channelId + ":" + channelName);
//		$("#amount").val(value);
//		$("#oldAmount").val(value);
//		$("#amount").attr('max', value);
//		$("#priority").val(0);
//		$("#src_channelId").val(channelId);
//		$("#channel").modal();
		$.ajax({
			url:"findChannel.action",
			type:'post',
			data:{
				channelId:channelId
			},
			success:function(obj){
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
				if(obj.channelFlag){
					flag = 1;
				}else{
					flag = 0;
				}
				var codeFlag = 0;
				$("#switchTable").empty();
				$("#switchTable").append("<tr><td class='td'>当前通道</td><td><span id='channelName'>"+channelId + ":" + channelName+"</span><input type='hidden' value='"+channelId+"' id='src_channelId'><input type='hidden' value='"+codeFlag+"' id='flag' /><input type='hidden' value='"+flag+"' id='channelFlag'/><input type='hidden' value='' id='appId'></td></tr>");
				$("#switchTable").append("<tr><td class='td'>数量</td><td><input type='number' value='"+value+"' class='form-control' id='amount' min='1' max='"+value+"' data-step='1'><input type='hidden' class='form-control' value='"+value+"' id='oldAmount'></td></tr>");
				if(obj.channelFlag){
					if("yes" == obj.carriers.cmcc){
						$("#switchTable").append("<tr><td class='td'>移动通道</td><td><select name='channelId' id='cmcc_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+cmccopt+"</select></td></tr>");
						$("#cmcc_channelId").selectpicker("refresh");
					}
					if("yes" == obj.carriers.unicom){
						$("#switchTable").append("<tr><td class='td'>联通通道</td><td><select name='channelId' id='unicom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+unicomopt+"</select></td></tr>");
						$("#unicom_channelId").selectpicker("refresh");
					}
					if("yes" == obj.carriers.telecom){
						$("#switchTable").append("<tr><td class='td'>电信通道</td><td><select name='channelId' id='telecom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+telecomopt+"</select></td></tr>");
						$("#telecom_channelId").selectpicker("refresh");
					}
				} else {
					$("#switchTable").append("<tr><td class='td'>目标通道</td><td><select name='channelId' id='dest_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+opt+"</select></td></tr>");
					$("#dest_channelId").selectpicker("refresh");
				}
			},
			dataType:"json"
			
		});
		$("#channel").modal();
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function IndustrySwitchChannel(value, channelName, channelId) {
	if (passed('行业切换')) {
//		$("#channelName").html(channelId + ":" + channelName);
//		$("#amount").val(value);
//		$("#oldAmount").val(value);
//		$("#amount").attr('max', value);
//		$("#priority").val(1);
//		$("#src_channelId").val(channelId);
//		$("#channel").modal();
		$.ajax({
			url:"findChannel.action",
			type:'post',
			data:{
				channelId:channelId
			},
			success:function(obj){
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
				if(obj.channelFlag){
					flag = 1;
				}else{
					flag = 0;
				}
				var codeFlag = 1;
				$("#switchTable").empty();
				$("#switchTable").append("<tr><td class='td'>当前通道</td><td><span id='channelName'>"+channelId + ":" + channelName+"</span><input type='hidden' value='"+channelId+"' id='src_channelId'><input type='hidden' value='"+codeFlag+"' id='flag' /><input type='hidden' value='"+flag+"' id='channelFlag'/><input type='hidden' id='appId'></td></tr>");
				$("#switchTable").append("<tr><td class='td'>数量</td><td><input type='number' value='"+value+"' class='form-control' id='amount' min='1' max='"+value+"' data-step='1'><input type='hidden' class='form-control' value='"+value+"' id='oldAmount'></td></tr>");
				if(obj.channelFlag){
					if("yes" == obj.carriers.cmcc){
						$("#switchTable").append("<tr><td class='td'>移动通道</td><td><select name='channelId' id='cmcc_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+cmccopt+"</select></td></tr>");
						$("#cmcc_channelId").selectpicker("refresh");
					}
					if("yes" == obj.carriers.unicom){
						$("#switchTable").append("<tr><td class='td'>联通通道</td><td><select name='channelId' id='unicom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+unicomopt+"</select></td></tr>");
						$("#unicom_channelId").selectpicker("refresh");
					}
					if("yes" == obj.carriers.telecom){
						$("#switchTable").append("<tr><td class='td'>电信通道</td><td><select name='channelId' id='telecom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+telecomopt+"</select></td></tr>");
						$("#telecom_channelId").selectpicker("refresh");
					}
				} else {
					$("#switchTable").append("<tr><td class='td'>目标通道</td><td><select name='channelId' id='dest_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+opt+"</select></td></tr>");
					$("#dest_channelId").selectpicker("refresh");
				}
			},
			dataType:"json"
			
		});
		$("#channel").modal();
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}

function details(channelId) {
	$.ajax({
		url : "findMarketDetail.action",
		data : {
			channelId : channelId
		},
		success : function(obj) {
			$("#detailsTab").empty();
			if (obj.length > 0) {
				for ( var i in obj) {
					$("#detailsTab").append("<tr><td>"+ (obj[i].priority == 'hight' ? '高': obj[i].priority == 'low' ? '低': obj[i].priority == 'normal' ? '正常': obj[i].priority == 'urgent' ? '极高': '')
						+ "</td><td>"+ obj[i].marketNumber+ "</td><td>"+ obj[i].appName+ "</td><td><button class='btn btn-warning btn-xs' onclick=\"priorityTab('"
						+ obj[i].marketNumber+ "','"+ obj[i].channelId+ "','"+ obj[i].appId+ "')\" title='紧急优先'>紧急优先</button>&nbsp;&nbsp;<button class='btn btn-warning btn-xs' onclick=\"marketSwitchChannel('"
						+ obj[i].marketNumber+ "','"+ obj[i].channelName+ "','"+ obj[i].channelId+ "','"+ obj[i].appId+ "')\" title='积压切换'>积压切换</button></td></tr>");
				}
			} else {
				$("#detailsTab").append("<tr><td colspan='4' align='center'>没有找到匹配的记录</td></tr>");
			}
			$("#details").modal();
		}
	});
}
function marketSwitchChannel(value, channelName, channelId, appId) {
	if (passed('营销切换')) {
		$.ajax({
			url:"findChannel.action",
			type:'post',
			data:{
				channelId:channelId
			},
			success:function(obj){
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
				if(obj.channelFlag){
					flag = 1;
				}else{
					flag = 0;
				}
				var codeFlag = 2;
				$("#switchTable").empty();
				$("#switchTable").append("<tr><td class='td'>当前通道</td><td><span id='channelName'>"+channelId + ":" + channelName+"</span><input type='hidden' value='"+channelId+"' id='src_channelId'><input type='hidden' value='"+codeFlag+"' id='flag' /><input type='hidden' value='"+flag+"' id='channelFlag'/><input type='hidden' value='"+appId+"' id='appId'></td></tr>");
				$("#switchTable").append("<tr><td class='td'>数量</td><td><input type='number' value='"+value+"' class='form-control' id='amount' min='1' max='"+value+"' data-step='1'><input type='hidden' class='form-control' value='"+value+"' id='oldAmount'></td></tr>");
				if(obj.channelFlag){
					if("yes" == obj.carriers.cmcc){
						$("#switchTable").append("<tr><td class='td'>移动通道</td><td><select name='channelId' id='cmcc_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+cmccopt+"</select></td></tr>");
						$("#cmcc_channelId").selectpicker("refresh");
					}
					if("yes" == obj.carriers.unicom){
						$("#switchTable").append("<tr><td class='td'>联通通道</td><td><select name='channelId' id='unicom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+unicomopt+"</select></td></tr>");
						$("#unicom_channelId").selectpicker("refresh");
					}
					if("yes" == obj.carriers.telecom){
						$("#switchTable").append("<tr><td class='td'>电信通道</td><td><select name='channelId' id='telecom_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+telecomopt+"</select></td></tr>");
						$("#telecom_channelId").selectpicker("refresh");
					}
				} else {
					$("#switchTable").append("<tr><td class='td'>目标通道</td><td><select name='channelId' id='dest_channelId' class='selectpicker show-tick form-control' placeholder='全部账户' data-width='98%' data-first-option='false' required data-live-search='true'><option value=''>全部通道</option>"+opt+"</select></td></tr>");
					$("#dest_channelId").selectpicker("refresh");
				}
			},
			dataType:"json"
			
		});
		$("#channel").modal();
	} else {
		$("#details").modal('hide');
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function priorityTab(value, channelId, appId) {
	if (passed('紧急优先')) {
		$("#urgentNumber").val(value);
		$("#oldUrgentNumber").val(value);
		$("#urgentNumber").attr('max', value);
		$("#urgentChannelId").val(channelId);
		$("#urgentAppId").val(appId);
		$("#priorityTab").modal();
	} else {
		$("#details").modal('hide');
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
$(function() {
	$("#btn_back").click(function() {
		$("#logForm")[0].reset();
	});
	$("#btn_sub").click(
			function() {
				var src_channelId = $("#src_channelId").val();
				var dest_channelId = $("#dest_channelId").val();
				var cmcc_channelId = $("#cmcc_channelId").val();
				var unicom_channelId = $("#unicom_channelId").val();
				var telecom_channelId = $("#telecom_channelId").val();
				var amount = $("#amount").val();
				var oldAmount = $("#oldAmount").val();
				if (parseInt(amount) <= 0) {
					$('.popup_de .show_msg').text('请选择正确的切换条数!');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					$('#mytab').bootstrapTable('refresh', {
						url : 'findToBeSubmittedList.action'
					});
					return;
				}
				if (parseInt(amount) > parseInt(oldAmount)) {
					$('.popup_de .show_msg').text('当前数量为'+oldAmount+",请重新输入");
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
					$('#mytab').bootstrapTable('refresh', {
						url : 'findToBeSubmittedList.action'
					});
					return;
				}
				var priority = $("#flag").val();
				var channelFlag = $("#channelFlag").val();
				var appId = $("#appId").val();
				$.post("switchChannel.action", {
					src_channelId : src_channelId,
					dest_channelId : dest_channelId,
					amount : amount,
					appId : appId,
					priority : priority,
					cmcc_channelId : cmcc_channelId,
					unicom_channelId : unicom_channelId,
					telecom_channelId : telecom_channelId,
					channelFlag : channelFlag
				}, function(obj) {
					var data = obj;
					if (data.result == '0') {
						$('.modal').map(function() {
							$(this).modal('hide');
						});
						$('.popup_de .show_msg').text('切换成功!');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					} else {
						$('.modal').map(function() {
							$(this).modal('hide');
						});
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
	$("#btn_priSub").click(function() {
		var src_channelId = $("#urgentChannelId").val();
		var amount = $("#urgentNumber").val();
		var oldAmount = $("#oldUrgentNumber").val();
		var appId = $("#urgentAppId").val();
		if (parseInt(amount) <= 0 || parseInt(oldAmount)<parseInt(amount)) {
			$('.popup_de .show_msg').text('请选择正确的切换条数!');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			$('#mytab').bootstrapTable('refresh', {
				url : 'findToBeSubmittedList.action'
			});
			return;
		}
		$.post("urgentPriority.action", {
			src_channelId : src_channelId,
			amount : amount,
			appId : appId
		}, function(obj) {
			var data = obj;
			if (data.result == '0') {
				$('.modal').map(function() {
					$(this).modal('hide');
				});
				$('.popup_de .show_msg').text('切换成功!');
				$('.popup_de .btn_cancel').css('display', 'none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				$('#mytab').bootstrapTable('refresh', {
					url : 'findToBeSubmittedList.action'
				});
			} else {
				$('.modal').map(function() {
					$(this).modal('hide');
				});
				$('.popup_de .show_msg').text('切换失败!');
				$('.popup_de .btn_cancel').css('display', 'none');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				$('#mytab').bootstrapTable('refresh', {
					url : 'findToBeSubmittedList.action'
				});
			}
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