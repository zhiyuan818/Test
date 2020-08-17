$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#mytab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})

	$('#mytab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findAllList.action",
		height : tableHeight(),
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
			title : '通道名称',
			field : 'channelId',
			formatter : channelName,
		}, {
			title : '通道名称',
			field : 'channelName',
			visible : false
		}, {
			title : '公司名称',
			field : 'companyName',
		}, {
			title : '签名',
			field : 'sign',
		}, {
			title : '扩展号码',
			field : 'extSrc',
		}, {
			title : '报备状态',
			field : 'baobeiFlag',
		}, {
			title : '时间',
			field : 'activeTime',
			formatter : changeDateFormat
		}, {
			title : '操作',
			field : 'id',
			width : 150,
			align : 'center',
			valign : 'middle',
			formatter : actionFormatter
		}, ],
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
	importValidator();
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#sign").val(''), 
		$("#maxExt").val(''),
		$("#minExt").val(''),
		$("#baobeiFlag").val(''),
		$("#channelId").val(''),
		$("#startTime").val(''),
		$("#endTime").val(''),
		$("select.selectpicker").each(function() {
			$(this).selectpicker('val', $(this).find('option:first').val()); // 重置bootstrap-select显示
			$(this).find("option").attr("selected", false); // 重置原生select的值
			$(this).find("option:first").attr("selected", true);
		});
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 20,
			sort : '',
			order : 'asc'
		});

	});

})


function importValidator() {
	$('#importForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			baobeiFlag : {
				validators : {
					notEmpty : {
						message : '请选择报备状态'
					}
				}
			},
			channelId : {
				validators : {
					notEmpty : {
						message : '请选择通道'
					}
				}
			},
			uploadFile : {
				validators : {
					notEmpty : {
						message : '请选择Excel格式文件'
					},
					regexp : {
						regexp : /\.xl.{1,2}$/,
						message : '选择正确的excel文件'
					}
				}
			}
		}
	});
}



function tableHeight() {
	return $(window).height() - 130;
}


function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		sortOrder : params.order, // 排序升序or倒序
		sort : params.sort, // 排序字段
		sign : $("#sign").val(), 
		maxExt : $("#maxExt").val(),
		minExt : $("#minExt").val(),
		baobeiFlag : $("#baobeiFlag").val(),
		channelId : $("#channelId").val(),
		startTime : $("#startTime").val(),
		endTime : $("#endTime").val()
	}
}


function channelName(value, row, index) {
	var channel = "";
	channel = value + ":" + row.channelName;
		return channel;
	
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
	var id = value;
	var status = row.baobeiFlag;
	var result = "<button class='btn btn-primary btn-xs' id='delete_btn' onclick=\"delSignBaobei('"
			+ id + "')\" title='点击删除'>删除</button>&nbsp;&nbsp;";
	if (status == 'yes') {
		result += "<button class='btn btn-primary btn-xs' id='no_btn' onclick=\"changeToNo('"
				+ id +"','"+ status + "')\" title='点击修改'>改为未报备</button>";

	} else {
		result += "<button class='btn btn-primary btn-xs' id='yes_btn' onclick=\"changeToYes('"
				+ id +"','"+ status + "')\" title='点击修改'>改为已报备</button>";

	}
	
	return result;
}


function delSignBaobei(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("delSignBaobei.action", {
					'id' : id,
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('删除成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}else{
						$('.popup_de .show_msg').text('删除失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAllList.action'
					});
				}, "json");
				mark = false;
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


function changeToNo(id, status) {
	mark = true;
	if (passed('改为未报备')) {
		$('.popup_de .show_msg').text('确定要修改吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("changeBaobeiFlag.action", {
					'id' : id,
					flag : status,
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('修改成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}else{
						$('.popup_de .show_msg').text('修改失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAllList.action'
					});
				}, "json");
				mark = false;
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


function changeToYes(id, status) {
	mark = true;
	if (passed('改为已报备')) {
		$('.popup_de .show_msg').text('确定要修改吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("changeBaobeiFlag.action", {
					'id' : id,
					flag : status,
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('修改成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}else{
						$('.popup_de .show_msg').text('修改失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
					$('#mytab').bootstrapTable('refresh', {
						url : 'findAllList.action'
					});
				}, "json");
				mark = false;
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


$(function() {
	$("#import_btn").click(function() {
		if(passed('批量导入')){
			$("#importBody").modal();
		}else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
		
	});
	// 点击批量导入页面返回按钮
	$('.import_backBtn').click(function() {
		$("#importForm")[0].reset();
		$("#importchaId").selectpicker('refresh');
		$("#importFlag").selectpicker('refresh');
		$("#importBody").modal('hide');
		$("#import_saveBtn").attr("disabled", false);
		$("#importForm").data('bootstrapValidator').destroy();
		$('#importForm').data('bootstrapValidator', null);
	});
	
	// 点击下一步按钮
	$('#import_saveBtn').click(function() {
		var uploadFile=$("#uploadFile").val();
		if(uploadFile =="" ||uploadFile ==null ||uploadFile==undefined){
			alert("请选择excel文件");
			return;
		}
		// 点击保存时触发表单验证
		$('#importForm').bootstrapValidator('validate');
		// 如果表单验证正确
		if ($("#importForm").data('bootstrapValidator').isValid()) {
			$("#import_saveBtn").attr("disabled", true);
			var formData = new FormData($( "#importForm" )[0]);
			$.ajax({
				url : "parseFile.action",
				data : formData,
				dataType : "json",
				type : "post",
				processData: false,
				contentType: false,
				success : function(data) {
					$("#headTab").empty();
					$("#detailsTab").empty();
					var list=data.list;
					var s="";
					var num=0;
					for(var i=0;i<list.length;i++){
						var obj=list[i];
						s+="<tr>";
						num=obj.length;
						for(var j=0;j<obj.length;j++){
							s+="<td>"+obj[j]+"</td>";
						}
						s+="<td><input type='checkbox' name='check' value='"+i+"'></td></tr>";
						if(i==9){
							break;
						}
					}
					var sb="<tr>";
					for(var i=0;i<num;i++){
						sb+="<th><select name='column' data='"+i+"' id='column"+i+"'><option value=''>请选择列名</option><option value='sign'>签名</option><option value='extSrc'>扩展号码</option><option value='companyName'>公司名称</option></select></th>";
					}
					sb+="<th>剔除</th></tr>"
					$("#detailsTab").append(s);
					$("#headTab").append(sb);
					$("#hiddenChannelId").val(data.channelId);
					$("#hiddenUpdate").val(data.update);
					$("#hiddenExt").val(data.ext);
					$("#hiddenBaobeiFlag").val(data.baobeiFlag);
					$("#hiddenFile").val(data.file);
					$("#nextBody").modal();
				}
				
			});
			
			
			$("#importBody").modal('hide');
			$("#nextBody").modal();
			$("#import_saveBtn").attr("disabled", false);
			$("#importForm").data('bootstrapValidator').destroy();
			$('#importForm').data('bootstrapValidator', null);
			importValidator();
			
		}
	});
	
	$("#next_saveBtn").click(function(){
		var flag=true;
		var sp=new Map();
		var i=0;
		$("[name='column']").each(function(){
			var st=$(this).val();
			if(!(st == null || st == "" || st == undefined)){
				flag=false;
			}
			sp[$(this).val()]=$(this).attr("data");
		});
		if(flag){
			alert("请选择对应的字段");
			return;
		}
		if(Object.keys(sp).length!=3){
			alert("请选择对应的扩展号、签名、公司名称");
			return;
		}
		var check=[];
		var j=0;
		$("[name='check']:checked").each(function(){
			var a=$(this).val();
			check[j]=a;
			j++;
		});
		if(check.length==0){
			check[0]=-1;
		}
		var channelId=$("#hiddenChannelId").val();
		var update=$("#hiddenUpdate").val();
		var ext=$("#hiddenExt").val();
		var baobeiFlag=$("#hiddenBaobeiFlag").val();
		var file=$("#hiddenFile").val();
		$.ajax({
			url:"importData.action",
			type:"post",
			data:{
				check:check,
				sp:JSON.stringify(sp),
				channelId:channelId,
				update:update,
				ext:ext,
				baobeiFlag:baobeiFlag,
				file:file
			},
			success:function(obj){
				if(obj.result>0){
					$('.popup_de .show_msg').text('导入成功');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}else{
					$('.popup_de .show_msg').text('导入失败');
					$('.popup_de .btn_cancel').css('display', 'none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
					})
				}
				$('#mytab').bootstrapTable('refresh', {
					url : 'findAllList.action'
				});
				$("#nextBody").modal('hide');
			},
			dataType:"json"
		});
		
	});
	
	$("#export_btn").click(function(){
		$("#exportBody").modal();
	});
	$("#export_saveBtn").click(function(){
		var param=new Array();
		var i=0;
		$("[name='param']:checked").each(function(){
			param.push($(this).val());
		});
		location.href="exportData.action?param="+encodeURI(JSON.stringify(param));
		
		
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

$(function() {
	$("#startTime").datetimepicker({
		language : 'zh-CN',// 显示中文
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
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
		format : 'yyyy-mm-dd hh:ii:ss',// 显示格式
		initialDate : new Date(),
		minuteStep : 1,
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

