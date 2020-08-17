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
		url : "findTemplateList.action",// 请求后台url
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
		uniqueId : "id", // 每一行的唯一标识，一般为主键列
		pageSize : 50,// 单页记录数
		pageList : [20, 50, 100, 200 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar : '#toolbar',// 指定工作栏
		columns : [{
			title : '全选',
			field : 'select',
			checkbox : true,
			width : 25,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'ID',
			field : 'id',
			width : 50,
		},{
			title : '账号',
			field : 'appId',
			formatter : function(value,row,index){
				if(value=='0'){
					return '全局';
				}
				return value+":"+row.appName;
			},
			width : 90
		}, {
			title : '模板规则',
			field : 'rule',
		}, {
			title : '模板说明',
			field : 'templateInfo',
			width : 80,
		}, {
			title : '模板策略',
			field : 'strategy',
			width : 80,
			formatter : function(value,row,index){
				if(value == 'pass'){
					return '通过';
				}else if(value == "redirect"){
					return "通道跳转";
				}else if(value == "audit"){
					return "审核";
				}else if(value == "reject"){
					return "驳回";
				}else if(value == "signext"){
					return "签名扩展跳转";
				}
			}
		}, {
			title : '策略结果',
			field : 'result',
			width : 150,
			formatter : function(value,row,index) {
				if(row.strategy == "signext"){
					return "<a href='javascript:;' data-toggle='modal' data-target='#signExtDetails' onclick=\"findSignExtDetails('"+row.rule+"')\">查看跳转策略</a>";
				}else{
					return value;
				}
			}
		}, {
			title : '标记',
			field : 'keyWord',
			width : 80,
		}, {
			title : '状态',
			field : 'status',
			width : 50,
			formatter : function(value,row,index) {
				if(value == 'normal'){
					return '正常';
				}else if(value == 'paused'){
					
					return "<font color='#ff0000'>暂停</font>";
				}
			}
		}, {
			title : '优先级',
			field : 'ruleIndex',
			width : 60,
		}, {
			title : '状态操作',
			field : 'status',
			width : 80,
			align : 'center',
			valign : 'middle',
			formatter : changeStatus
		}, {
			fileld : 'id',
			title : '操作',
			width : 200,
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
		locale : 'zh-CN'// 中文支持,
	})
	addValidator();
	updateValidator();
	checkValidator();
	copyValidator();
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$("#search_btn").click(function() {
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1
		});
	});
	$("#search_back").click(function() {
		$("#search_appId").selectpicker('val', '');
		$("#search_rule").val('');
		$("#search_templateInfo").val('');
		$("#search_result").val('');
		$("#search_strategy").selectpicker('val', '');
		$("#search_status").selectpicker('val', '');
		$('#mytab').bootstrapTable('refreshOptions', {
			pageNumber : 1,
			limit : 50,
		});
	});
	
	$('#btn_del').click(function() {
		mark=true;
		if(passed('批量删除')){
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de .btn_submit').one('click', function(){
				var ID = [];
				var i = 0;
				for (i; i < dataArr.length; i++) {
					ID[i] = dataArr[i].id;
				}
				if(ID.length > 0){
					if(mark){
						$.post("delTemplateBatch.action", {
							ids : ID
						}, function(data){
							if(data.result > 0){
								$('.popup_de .show_msg').text('删除成功！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function(){
									$('#btn_del').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_del').css('display', 'none');
									}, 400);
									$('#btn_pau').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_pau').css('display', 'none');
									}, 400);
									$('#btn_str').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_str').css('display', 'none');
									}, 400);
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							}else {
								$('.popup_de .show_msg').text('删除出错！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							}
						})
						mark=false;
					}
				}
				
			})
		}else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
		
	});
	
	//批量暂停
	$('#btn_pau').click(function() {
		mark=true;
		if (passed('批量暂停')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要暂停吗?');
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
						$.post("pauseTemplateBatch.action", {
							ids : ID
						}, function(data) {
							if (data.result > 0) {
								$('.popup_de .show_msg').text('暂停成功！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('#btn_del').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_del').css('display', 'none');
									}, 400);
									$('#btn_pau').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_pau').css('display', 'none');
									}, 400);
									$('#btn_str').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_str').css('display', 'none');
									}, 400);
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							} else {
								$('.popup_de .show_msg').text('暂停出错！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
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
	
	$('#btn_str').click(function() {
		mark=true;
		if (passed('批量启动')) {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要启动吗?');
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
						$.post("startTemplateBatch.action", {
							ids : ID
						}, function(data) {
							if (data.result > 0) {
								$('.popup_de .show_msg').text('启动成功！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('#btn_del').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_del').css('display', 'none');
									}, 400);
									$('#btn_pau').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_pau').css('display', 'none');
									}, 400);
									$('#btn_str').addClass('fadeOutRight');
									setTimeout(function() {
										$('#btn_str').css('display', 'none');
									}, 400);
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							} else {
								$('.popup_de .show_msg').text('启动出错！');
								$('.popup_de .btn_cancel').css('display', 'none');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
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
			$('#btn_pau').css('display', 'block').removeClass(
			'fadeOutRight').addClass('animated fadeInRight');
			$('#btn_str').css('display', 'block').removeClass(
			'fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_del').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del').css('display', 'none');
			}, 400);
			$('#btn_pau').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_pau').css('display', 'none');
			}, 400);
			$('#btn_str').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_str').css('display', 'none');
			}, 400);
		}
	});
	$("[name='btSelectAll']").click(function(){
		var dataArr = $('#mytab .selected');
		if (dataArr.length >= 1) {
			$('#btn_del').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
			$('#btn_pau').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
			$('#btn_str').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_del').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_del').css('display', 'none');
			}, 400);
			$('#btn_pau').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_pau').css('display', 'none');
			}, 400);
			$('#btn_str').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_str').css('display', 'none');
			}, 400);
		}
	});
	
	// 点击添加按钮
	$("#btn_add").click(function() {
		if (passed('新增')) {
			$("#addForm")[0].reset();
			$("#addForm").data('bootstrapValidator').destroy();
			$('#addForm').data('bootstrapValidator', null);
			addValidator();
			$("#addResult").attr("style","");
			$("#addCmccDiv").attr("style","display: none");
			$("#addUnicomDiv").attr("style","display: none");
			$("#addTelecomDiv").attr("style","display: none");
			$("#strategy").selectpicker('val', 'pass');
			$("#status").selectpicker('val', 'normal');
			$("#appId").selectpicker('val', '');
			$("#addCmccChannelId").selectpicker('val', '');
			$("#addUnicomChannelId").selectpicker('val', '');
			$("#addTelecomChannelId").selectpicker('val', '');
			$("#add_saveBtn").attr("disabled", false);
			$("#addBody").modal();
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	$('.add_backBtn').click(function() {
		$("#addForm")[0].reset();
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#addResult").attr("style","");
		$("#addCmccDiv").attr("style","display: none");
		$("#addUnicomDiv").attr("style","display: none");
		$("#addTelecomDiv").attr("style","display: none");
		$("#strategy").selectpicker('val', 'pass');
		$("#status").selectpicker('val', 'normal');
		$("#appId").selectpicker('val', '');
		$("#addCmccChannelId").selectpicker('val', '');
		$("#addUnicomChannelId").selectpicker('val', '');
		$("#addTelecomChannelId").selectpicker('val', '');
		$("#add_saveBtn").attr("disabled", false);
		$("#addBody").modal("hide");
	});
	$("#add_saveBtn").click(function() {
		mark=true;
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.btn_submit').one('click', function() {
				$("#add_saveBtn").attr("disabled", true);
				var appId=$("#appId").val();
				var templateInfo=$("#templateInfo").val();
				var strategy =$("#strategy").val();
				var status = $("#status").val();
				if(strategy == "redirect"){
					var cmcc = $("#addCmccChannelId").val();
					var unicom = $("#addUnicomChannelId").val();
					var telecom = $("#addTelecomChannelId").val();
					var res = "";
					if(cmcc != '' && cmcc != null && cmcc != undefined){
						res+=";cmcc:"+cmcc;
					}
					if(unicom != '' && unicom != null && unicom != undefined){
						res+=";unicom:"+unicom;
					}
					if(telecom != '' && telecom != null && telecom != undefined){
						res+=";telecom:"+telecom;
					}
					if(res.length >= 1){
						res = res.substring(1);
					}
					$("#result").val(res);
				}
				var keyWord=$("#keyWord").val();
				var result=$("#result").val();
				var rule=$("#rule").val();
				if(mark){
					$.ajax({
						url : "addTemplate.action",
						data : {
							appId:appId,
							templateInfo:templateInfo,
							strategy:strategy,
							status:status,
							rule:rule,
							keyWord:keyWord,
							result:result
						},
						type : "post",
						success : function(obj) {
							$("#addBody").modal("hide");
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$('#addForm')[0].reset();
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('添加成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							} else {
								$('.popup_de .show_msg').text('添加失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							}
							$("#add_saveBtn").attr("disabled", false);
						},
						dataType : "json"
					})
					mark=false;
				}
			})
		}
	});
	$('.edit_backBtn').click(function() {
		$("#editForm").data('bootstrapValidator').destroy();
		$('#editForm').data('bootstrapValidator', null);
		updateValidator();
		$("#updateResult").attr("style","");
		$("#updateCmccDiv").attr("style","display: none");
		$("#updateUnicomDiv").attr("style","display: none");
		$("#updateTelecomDiv").attr("style","display: none");
		$("#edit_strategy").selectpicker('val', '');
		$("#edit_status").selectpicker('val', 'normal');
		$("#updateCmccChannelId").selectpicker('val', '');
		$("#updateUnicomChannelId").selectpicker('val', '');
		$("#updateTelecomChannelId").selectpicker('val', '');
		$("#changeBody").modal('hide');
		$("#editForm")[0].reset();
	});
	$("#strategy").change(function(){
		var strategy = $("#strategy").val();
		if(strategy == "redirect"){
			$("#addResult").attr("style","display: none");
			$("#addCmccDiv").attr("style","");
			$("#addUnicomDiv").attr("style","");
			$("#addTelecomDiv").attr("style","");
		}else if(strategy == "signext"){
			$("#addResult").attr("style","display: none");
			$("#addCmccDiv").attr("style","display: none");
			$("#addUnicomDiv").attr("style","display: none");
			$("#addTelecomDiv").attr("style","display: none");
		}else{
			$("#addResult").attr("style","");
			$("#addCmccDiv").attr("style","display: none");
			$("#addUnicomDiv").attr("style","display: none");
			$("#addTelecomDiv").attr("style","display: none");
		}
	});
	$("#edit_strategy").change(function(){
		var strategy = $("#edit_strategy").val();
		if(strategy == "redirect"){
			$("#updateResult").attr("style","display: none");
			$("#updateCmccDiv").attr("style","");
			$("#updateUnicomDiv").attr("style","");
			$("#updateTelecomDiv").attr("style","");
		}else if(strategy == "signext"){
			$("#updateResult").attr("style","display: none");
			$("#updateCmccDiv").attr("style","display: none");
			$("#updateUnicomDiv").attr("style","display: none");
			$("#updateTelecomDiv").attr("style","display: none");
		}else{
			$("#updateResult").attr("style","");
			$("#updateCmccDiv").attr("style","display: none");
			$("#updateUnicomDiv").attr("style","display: none");
			$("#updateTelecomDiv").attr("style","display: none");
		}
	});
	$("#copy_strategy").change(function(){
		var strategy = $("#copy_strategy").val();
		if(strategy == "redirect"){
			$("#copyResult").attr("style","display: none");
			$("#copyCmccDiv").attr("style","");
			$("#copyUnicomDiv").attr("style","");
			$("#copyTelecomDiv").attr("style","");
		}else if(strategy == "signext"){
			$("#copyResult").attr("style","display: none");
			$("#copyCmccDiv").attr("style","display: none");
			$("#copyUnicomDiv").attr("style","display: none");
			$("#copyTelecomDiv").attr("style","display: none");
		}else{
			$("#copyResult").attr("style","");
			$("#copyCmccDiv").attr("style","display: none");
			$("#copyUnicomDiv").attr("style","display: none");
			$("#copyTelecomDiv").attr("style","display: none");
		}
	});
});
$("#edit_saveBtn").click(function() {
	mark=true;
	$('#editForm').bootstrapValidator('validate');
	if ($("#editForm").data('bootstrapValidator').isValid()) {
		$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.btn_submit').one('click', function() {
			$("#edit_saveBtn").attr("disabled", true);
			var id=$("#edit_id").val();
			var appId=$("#edit_appId").val();
			var templateInfo=$("#edit_templateInfo").val();
			var strategy =$("#edit_strategy").val();
			var status = $("#edit_status").val();
			if(strategy == "redirect"){
				var cmcc = $("#updateCmccChannelId").val();
				var unicom = $("#updateUnicomChannelId").val();
				var telecom = $("#updateTelecomChannelId").val();
				var res = "";
				if(cmcc != '' && cmcc != null && cmcc != undefined){
					res+=";cmcc:"+cmcc;
				}
				if(unicom != '' && unicom != null && unicom != undefined){
					res+=";unicom:"+unicom;
				}
				if(telecom != '' && telecom != null && telecom != undefined){
					res+=";telecom:"+telecom;
				}
				if(res.length >= 1){
					res = res.substring(1);
				}
				$("#edit_result").val(res);
			}
			var keyWord=$("#edit_keyWord").val();
			var result=$("#edit_result").val();
			var rule=$("#edit_rule").val();
			var ruleIndex=$("#edit_ruleIndex").val();
			if(mark){
				$.ajax({
					url : "updateTemplate.action",
					type : "post",
					async : false,
					data : {
						id:id,
						appId:appId,
						templateInfo:templateInfo,
						strategy:strategy,
						status:status,
						rule:rule,
						keyWord:keyWord,
						result:result,
						ruleIndex:ruleIndex
					},
					success : function(obj) {
						$("#editForm").data('bootstrapValidator').destroy();
						$('#editForm').data('bootstrapValidator', null);
						updateValidator();
						$("#updateResult").attr("style","");
						$("#updateCmccDiv").attr("style","display: none");
						$("#updateUnicomDiv").attr("style","display: none");
						$("#updateTelecomDiv").attr("style","display: none");
						$("#edit_strategy").selectpicker('val', '');
						$("#edit_status").selectpicker('val', 'normal');
						$("#updateCmccChannelId").selectpicker('val', '');
						$("#updateUnicomChannelId").selectpicker('val', '');
						$("#updateTelecomChannelId").selectpicker('val', '');
						$("#editForm")[0].reset();
						$("#changeBody").modal("hide");
						if (obj.result > 0) {
							$('.popup_de .show_msg').text('修改成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							})
						} else {
							$('.popup_de .show_msg').text('修改失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
								$('#mytab').bootstrapTable('refresh', {
									url : 'findTemplateList.action'
								});
							})
						}
						$("#edit_saveBtn").attr("disabled", false);
					},
					error : function() {
						console.log("error");
					}
				});
				mark=false;
			}
		})
	}
})
function actionFormatter(value, row, index) {
	var result = "";
	result += "<button class='btn btn-default btn-xs' onclick=\"updateTemplate('"
			+ row.id + "')\" title='修改'>修改</button>";
	result += "&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"deleteTemplate('"
			+ row.id + "')\" title='删除'>删除</button>";
	result += "&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"checkTemplate('"
			+ row.id + "')\" title='检测'>检测</button>";
	result += "&nbsp;&nbsp;";
	result += "<button class='btn btn-default btn-xs' onclick=\"copyTemplate('"
		+ row.id + "')\" title='复制'>复制</button>";
	return result;
}

function changeStatus(value, row, index) {
	var result = "";
	var id = row.id;
	if (value == 'normal') {
		result += "<button class='btn btn-primary btn-xs' id='over_normal' onclick=\'addToPause("
			+ row.id + ")'\ title='暂停'>暂停</button>";

	} else {
		result += "<button class='btn btn-primary btn-xs' id='over_paused' onclick=\'addToStart("
				+ row.id + ")'\ title='启动'>启动</button>";

	}
	return result;
}

function addToStart(id) {
	mark = true;
	if (passed('启动')) {
		$('.popup_de .show_msg').text('确定要启动吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("startTemplate.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('启动成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findTemplateList.action'
						});
					} else {
						$('.popup_de .show_msg').text('启动失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
				});
				mark = false;
			}
		})
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}

function addToPause(id) {
	mark = true;
	if (passed('暂停')) {
		$('.popup_de .show_msg').text('确定要暂停吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("pauseTemplate.action", {
					id : id
				}, function(data) {
					if (data.result > 0) {
						$('.popup_de .show_msg').text('暂停成功！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findTemplateList.action'
						});
					} else {
						$('.popup_de .show_msg').text('暂停失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
					}
				});
				mark = false;
			}
		})
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}

}

function findSignExtDetails(rule) {
	var val = encodeURIComponent(rule);
	var url = "findSignExtDetails.action?rule="+val;
	$('#signExtTab').bootstrapTable('refresh',{
		url:url
	});
}


$('#signExtTab').bootstrapTable({
	method:'get',
	contentType:"application/x-www-form-urlencoded",
	url:'',
	height:tableHeight()-50,
	dataType:"json",
	cache:false,
	async:false,
	locale:'zh-CN',//中文支持,
	columns:[{
		title:'id',
		field:'id',
		width:50
	},{
		title:'签名',
		field:'sign',
		width:100
	},{
		title:'扩展',
		field:'ext',
		width:80
	},{
		title:'跳转移动通道',
		field:'cmccChannelId',
		width:120,
		formatter:function(value,row,index){
			return value+":"+row.cmccChannelName;
		}
	},{
		title:'跳转联通通道',
		field:'unicomChannelId',
		width:120,
		formatter:function(value,row,index){
			return value+":"+row.unicomChannelName;
		}
	},{
		title:'跳转电信通道',
		field:'telecomChannelId',
		width:120,
		formatter:function(value,row,index){
			return value+":"+row.telecomChannelName;
		}
	}]
	
});

function tableHeight() {
	return $(window).height() - 50;
}
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber, // 页码
		appId : $("#search_appId").val(),
		templateInfo : $("#search_templateInfo").val(),
		rule : $("#search_rule").val(),
		strategy : $("#search_strategy").val(),
		status : $("#search_status").val(),
		result : $("#search_result").val(),
		id : $("#search_id").val()
	}
}
function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appId : {
				validators : {
					notEmpty : {
						message : '账号不能为空'
					},
					callback:{
	         			   message:'账户和模板规则不唯一',
	                        callback:function(value, validator,$field){
	                     	   var appId = $('#appId').val();
	                     	   var rule = $('#rule').val();
	                     	   if(appId==null || appId=='' || appId==undefined || rule ==null || rule == '' || rule == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'validatorAppIdAndRule.action',
	                     				data:{
	                     					appId:appId,
	                     					rule:rule
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   return flag;
	                     	   }
	                        }
						}
				}
			},
			templateInfo : {
				validators : {
					notEmpty : {
						message : '模板说明不能为空'
					},
					stringLength : {
						min : 1,
						max : 30,
						message : '最大不能超过30位'
					}
				}
			},
			rule : {
				validators : {
					notEmpty : {
						message : '模板规则不能为空'
					},
					stringLength : {
						max : 250,
						message : '最大不能超过250位'
					},
					callback:{
	         			   message:'账户和模板规则不唯一',
	                        callback:function(value, validator,$field){
	                     	   var appId = $('#appId').val();
	                     	   var rule = $('#rule').val();
	                     	   if(appId==null || appId=='' || appId==undefined || rule ==null || rule == '' || rule == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   $.ajax({
	                     			  url:'validatorAppIdAndRule.action',
	                     				data:{
	                     					appId:appId,
	                     					rule:rule
	                     				},
	                     				async:false,
	                     				success:function(obj){
	                     					flag=obj.valid;
	                     				},
	                     				type:"post",
	                     				dataType:"json"
	                     		   })
	                     		   return flag;
	                     	   }
	                        }
						}
					}
				},
				result:{
					validators : {
						stringLength : {
							min : 1,
							max : 8,
							message : '最大不能超过8位'
						},
					}
				},
				keyWord:{
					validators : {
						stringLength : {
							min : 1,
							max : 8,
							message : '最大不能超过8位'
						},
					}
				}
			}
	});
}
function copyValidator() {
	$('#copyForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appId : {
				validators : {
					notEmpty : {
						message : '账号不能为空'
					},
					callback:{
						message:'账户和模板规则不唯一',
						callback:function(value, validator,$field){
							var appId = $('#copy_appId').val();
							var rule = $('#copy_rule').val();
							if(appId==null || appId=='' || appId==undefined || rule ==null || rule == '' || rule == undefined){
								return true;
							}else{
								var flag=true;
								$.ajax({
									url:'validatorAppIdAndRule.action',
									data:{
										appId:appId,
										rule:rule
									},
									async:false,
									success:function(obj){
										flag=obj.valid;
									},
									type:"post",
									dataType:"json"
								})
								return flag;
							}
						}
					}
				}
			},
			templateInfo : {
				validators : {
					notEmpty : {
						message : '模板说明不能为空'
					},
					stringLength : {
						min : 1,
						max : 30,
						message : '最大不能超过30位'
					}
				}
			},
			rule : {
				validators : {
					notEmpty : {
						message : '模板规则不能为空'
					},
					stringLength : {
						max : 250,
						message : '最大不能超过250位'
					},
					callback:{
						message:'账户和模板规则不唯一',
						callback:function(value, validator,$field){
							var appId = $('#copy_appId').val();
							var rule = $('#copy_rule').val();
							if(appId==null || appId=='' || appId==undefined || rule ==null || rule == '' || rule == undefined){
								return true;
							}else{
								var flag=true;
								$.ajax({
									url:'validatorAppIdAndRule.action',
									data:{
										appId:appId,
										rule:rule
									},
									async:false,
									success:function(obj){
										flag=obj.valid;
									},
									type:"post",
									dataType:"json"
								})
								return flag;
							}
						}
					}
				}
			},
			result:{
				validators : {
					stringLength : {
						min : 1,
						max : 8,
						message : '最大不能超过8位'
					},
				}
			},
			keyWord:{
				validators : {
					stringLength : {
						min : 1,
						max : 8,
						message : '最大不能超过8位'
					},
				}
			}
		}
	});
}
function updateValidator() {
	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			appId : {
				validators : {
					notEmpty : {
						message : '账号不能为空'
					},
					callback:{
	         			   message:'账户和模板规则不唯一',
	                       callback:function(value, validator,$field){
	                     	   var appId = $('#edit_appId').val();
	                     	   var oldappId = $('#edit_old_appId').val();
	                     	   var rule = $('#edit_rule').val();
	                     	   var oldrule = $('#edit_old_rule').val();
	                     	   if(appId==null || appId=='' || appId==undefined || rule ==null || rule == '' || rule == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   if((appId==oldappId&&rule!=oldrule)||(appId!=oldappId&&rule==oldrule)){
	                     			   $.ajax({
	                     				   url:'validatorAppIdAndRule.action',
	                     				   data:{
	                     					   appId:appId,
	                     					   rule:rule
	                     				   },
	                     				   async:false,
	                     				   success:function(obj){
	                     					   flag=obj.valid;
	                     				   },
	                     				   type:"post",
	                     				   dataType:"json"
	                     			   })
	                     		   }
	                     		   return flag;
	                     	   }
	                        }
						}
				}
			},
			templateInfo : {
				validators : {
					notEmpty : {
						message : '模板说明不能为空'
					},
					stringLength : {
						min : 1,
						max : 30,
						message : '最大不能超过30位'
					}
				}
			},
			rule : {
				validators : {
					notEmpty : {
						message : '模板规则不能为空'
					},
					stringLength : {
						max : 250,
						message : '最大不能超过250位'
					},
					callback:{
	         			   message:'账户和模板规则不唯一',
	                       callback:function(value, validator,$field){
	                     	   var appId = $('#edit_appId').val();
	                     	   var oldappId = $('#edit_old_appId').val();
	                     	   var rule = $('#edit_rule').val();
	                     	   var oldrule = $('#edit_old_rule').val();
	                     	   if(appId==null || appId=='' || appId==undefined || rule ==null || rule == '' || rule == undefined){
	                     			return true;
	                     	   }else{
	                     		   var flag=true;
	                     		   if((appId==oldappId&&rule!=oldrule)||(appId!=oldappId&&rule==oldrule)){
	                     			   $.ajax({
	                     				   url:'validatorAppIdAndRule.action',
	                     				   data:{
	                     					   appId:appId,
	                     					   rule:rule
	                     				   },
	                     				   async:false,
	                     				   success:function(obj){
	                     					   flag=obj.valid;
	                     				   },
	                     				   type:"post",
	                     				   dataType:"json"
	                     			   })
	                     		   }
	                     		   return flag;
	                     	   }
	                        }
						}
					}
				},
				result:{
					validators : {
						stringLength : {
							min : 1,
							max : 8,
							message : '最大不能超过8位'
						},
					}
				},
				keyWord:{
					validators : {
						stringLength : {
							min : 1,
							max : 8,
							message : '最大不能超过8位'
						},
					}
				}
			}
	});
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
function deleteTemplate(id) {
	mark = true;
	if (passed('删除')) {
		$('.popup_de .show_msg').text('确定要删除该模板吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.popup_de .btn_submit').one('click', function() {
			if (mark) {
				$.post("deleteTemplate.action", {
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
							url : 'findTemplateList.action'
						});
					} else {
						$('.popup_de .show_msg').text('删除失败！');
						$('.popup_de .btn_cancel').css('display', 'none');
						$('.popup_de').addClass('bbox');
						$('.popup_de .btn_submit').one('click', function() {
							$('.popup_de').removeClass('bbox');
						})
						$('#mytab').bootstrapTable('refresh', {
							url : 'findTemplateList.action'
						});
					}
				});
				mark = false;
			}
		})
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function updateTemplate(id) {
	if (passed("修改")) {
		$("#editForm").data('bootstrapValidator').destroy();
		$('#editForm').data('bootstrapValidator', null);
		updateValidator();
		$("#updateResult").attr("style","");
		$("#updateCmccDiv").attr("style","display: none");
		$("#updateUnicomDiv").attr("style","display: none");
		$("#updateTelecomDiv").attr("style","display: none");
		$("#edit_strategy").selectpicker('val', '');
		$("#edit_status").selectpicker('val', '');
		$("#updateCmccChannelId").selectpicker('val', '');
		$("#updateUnicomChannelId").selectpicker('val', '');
		$("#updateTelecomChannelId").selectpicker('val', '');
		$("#editForm")[0].reset();
		$.post("findTemplateById.action", {
			id : id
		}, function(obj) {
			$("#edit_id").val(obj.id);
			$("#edit_templateInfo").val(obj.templateInfo);
//			$("#edit_strategy").val(obj.strategy);
			$("#edit_strategy").selectpicker('val', obj.strategy);
			var strategy = $("#edit_strategy").val();
			if(strategy == "redirect"){
				$("#updateResult").attr("style","display: none");
				$("#updateCmccDiv").attr("style","");
				$("#updateUnicomDiv").attr("style","");
				$("#updateTelecomDiv").attr("style","");
				var result = obj.result;
				var results = result.split(";"); 
				for(var i in results){
					var sp = results[i];
					var sps = sp.split(":");
					if(sps[0] == "cmcc"){
						$("#updateCmccChannelId").selectpicker('val', sps[1]);
					}else if(sps[0] == "unicom"){
						$("#updateUnicomChannelId").selectpicker('val', sps[1]);
					}else if(sps[0] == "telecom"){
						$("#updateTelecomChannelId").selectpicker('val', sps[1]);
					}
				}
			}else if(strategy == "signext"){
				$("#updateResult").attr("style","display: none");
				$("#updateCmccDiv").attr("style","display: none");
				$("#updateUnicomDiv").attr("style","display: none");
				$("#updateTelecomDiv").attr("style","display: none");
				$("#edit_result").val("");
			}else{
				$("#updateResult").attr("style","");
				$("#updateCmccDiv").attr("style","display: none");
				$("#updateUnicomDiv").attr("style","display: none");
				$("#updateTelecomDiv").attr("style","display: none");
				$("#edit_result").val(obj.result);
			}
			$("#edit_status").selectpicker('val', obj.status);
			$("#edit_rule").text(obj.rule);
			$("#edit_old_rule").val(obj.rule);
			$("#edit_appId").selectpicker('val', obj.appId);
			$("#edit_old_appId").val(obj.appId);
			$("#edit_ruleIndex").val(obj.ruleIndex);
			$("#edit_keyWord").val(obj.keyWord);
			$("#changeBody").modal();
		}, "json");
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}

function checkTemplate(id){
	if (passed("检测")) {
		$("#checkForm").data('bootstrapValidator').destroy();
		$('#checkForm').data('bootstrapValidator', null);
		checkValidator();
		$("#checkForm")[0].reset();
		$.ajax({
			url:"findTemplateById.action",
			data:{id:id},
			type:"post",
			success:function(data){
				$("#check_id").val(data.id);
				$("#check_rule").val(data.rule);
			},
			dataType:"json"
		});
		$("#checkBody").modal();
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
function copyTemplate(id){
	if (passed("复制")) {
		$("#copyForm")[0].reset();
		$("#copyForm").data('bootstrapValidator').destroy();
		$('#copyForm').data('bootstrapValidator', null);
		copyValidator();
		$("#copyResult").attr("style","");
		$("#copyCmccDiv").attr("style","display: none");
		$("#copyUnicomDiv").attr("style","display: none");
		$("#copyTelecomDiv").attr("style","display: none");
		$("#copy_appId").selectpicker('val', '');
		$("#copy_strategy").selectpicker('val', 'pass');
		$("#copy_status").selectpicker('val', 'normal');
		$("#copyCmccChannelId").selectpicker('val', '');
		$("#copyUnicomChannelId").selectpicker('val', '');
		$("#copyTelecomChannelId").selectpicker('val', '');
		$("#copy_saveBtn").attr("disabled", false);
		$.ajax({
			url:"findTemplateById.action",
			data:{id:id},
			type:"post",
			success:function(data){
				$("#copy_templateInfo").val(data.templateInfo);
				$("#copy_strategy").selectpicker('val', data.strategy);
				var strategy=$("#copy_strategy").val();
				if(strategy == "redirect"){
					$("#copyResult").attr("style","display: none");
					$("#copyCmccDiv").attr("style","");
					$("#copyUnicomDiv").attr("style","");
					$("#copyTelecomDiv").attr("style","");
					var result = data.result;
					var results = result.split(";"); 
					for(var i in results){
						var sp = results[i];
						var sps = sp.split(":");
						if(sps[0] == "cmcc"){
							$("#copyCmccChannelId").selectpicker('val', sps[1]);
						}else if(sps[0] == "unicom"){
							$("#copyUnicomChannelId").selectpicker('val', sps[1]);
						}else if(sps[0] == "telecom"){
							$("#copyTelecomChannelId").selectpicker('val', sps[1]);
						}
					}
				}else if(strategy == "signext"){
					$("#copyResult").attr("style","display: none");
					$("#copyCmccDiv").attr("style","display: none");
					$("#copyUnicomDiv").attr("style","display: none");
					$("#copyTelecomDiv").attr("style","display: none");
					$("#copy_result").val("");
				}else{
					$("#copyResult").attr("style","");
					$("#copyCmccDiv").attr("style","display: none");
					$("#copyUnicomDiv").attr("style","display: none");
					$("#copyTelecomDiv").attr("style","display: none");
					$("#copy_result").val(data.result);
				}
				$("#copy_status").selectpicker('val', data.status);
				$("#copy_rule").text(data.rule);
				$("#copy_keyWord").val(data.keyWord);
				$("#copyBody").modal();
			},
			dataType:"json"
		});
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
}
$('.check_backBtn').click(function() {
	$("#checkForm").data('bootstrapValidator').destroy();
	$('#checkForm').data('bootstrapValidator', null);
	$("#checkBody").modal('hide');
	checkValidator();
	$("#checkForm")[0].reset();
});
$("#check_saveBtn").click(function(){
	var id=$("#check_id").val();
	var content=$("#content").val();
	$.ajax({
		url:"checkContent.action",
		data:{id:id,content:content},
		type:"post",
		success:function(data){
			if(data ==null || data.length==0){
				$('.popup_de .show_msg').text('未命中');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}
			var hit="";
			for(var i in data){
				if(data[i]){
					hit+=(parseInt(i)+parseInt(1))+",";
				}
			}
			hit=hit.substr(0,parseInt(hit.length)-parseInt(1));
			if(hit=='' || hit == null){
				$("#check_result").html("无匹配数据");
			}else{
				$("#check_result").html(hit);
			}
		},
		dataType:"json"
	});
	
});
function checkValidator() {
	$('#checkForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			content : {
				validators : {
					notEmpty : {
						message : '测试内容不能为空'
					}
				}
			}
		}
	});
}
$('.copy_backBtn').click(function() {
	$("#copyForm")[0].reset();
	$("#copyForm").data('bootstrapValidator').destroy();
	$('#copyForm').data('bootstrapValidator', null);
	copyValidator();
	$("#copyResult").attr("style","");
	$("#copyCmccDiv").attr("style","display: none");
	$("#copyUnicomDiv").attr("style","display: none");
	$("#copyTelecomDiv").attr("style","display: none");
	$("#copy_strategy").selectpicker('val', 'pass');
	$("#copy_status").selectpicker('val', 'normal');
	$("#copyCmccChannelId").selectpicker('val', '');
	$("#copyUnicomChannelId").selectpicker('val', '');
	$("#copyTelecomChannelId").selectpicker('val', '');
	$("#copy_saveBtn").attr("disabled", false);
	$("#copyBody").modal("hide");
});
$("#copy_saveBtn").click(function() {
	mark=true;
	$('#copyForm').bootstrapValidator('validate');
	// 如果表单验证正确，则请求后台添加用户
	if ($("#copyForm").data('bootstrapValidator').isValid()) {
		$('.popup_de .show_msg').text('确定要复制填写的内容吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_cancel').css('display', 'inline-block');
		$('.btn_submit').one('click', function() {
			$("#copy_saveBtn").attr("disabled", true);
			var appId=$("#copy_appId").val();
			var templateInfo=$("#copy_templateInfo").val();
			var strategy =$("#copy_strategy").val();
			if(strategy == "redirect"){
				var cmcc = $("#copyCmccChannelId").val();
				var unicom = $("#copyUnicomChannelId").val();
				var telecom = $("#copyTelecomChannelId").val();
				var res = "";
				if(cmcc != '' && cmcc != null && cmcc != undefined){
					res+=";cmcc:"+cmcc;
				}
				if(unicom != '' && unicom != null && unicom != undefined){
					res+=";unicom:"+unicom;
				}
				if(telecom != '' && telecom != null && telecom != undefined){
					res+=";telecom:"+telecom;
				}
				if(res.length >= 1){
					res = res.substring(1);
				}
				$("#copy_result").val(res);
			}
			var status = $("#copy_status").val();
			var keyWord=$("#copy_keyWord").val();
			var result=$("#copy_result").val();
			var rule=$("#copy_rule").val();
			if(mark){
				$.ajax({
					url : "addTemplate.action",
					data : {
						appId:appId,
						templateInfo:templateInfo,
						strategy:strategy,
						status:status,
						rule:rule,
						keyWord:keyWord,
						result:result
					},
					type : "post",
					success : function(obj) {
						$("#copyBody").modal("hide");
						$("#copyForm").data('bootstrapValidator').destroy();
						$('#copyForm').data('bootstrapValidator', null);
						copyValidator();
						$('#copyForm')[0].reset();
						if (obj.result > 0) {
							$('.popup_de .show_msg').text('复制成功！');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$('#mytab').bootstrapTable('refresh', {
								url : 'findTemplateList.action'
							});
						} else {
							$('.popup_de .show_msg').text('复制失败');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$('#mytab').bootstrapTable('refresh', {
								url : 'findTemplateList.action'
							});
						}
						$("#copy_saveBtn").attr("disabled", false);
					},
					dataType : "json"
				})
				mark=false;
			}
		})
	}
});

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