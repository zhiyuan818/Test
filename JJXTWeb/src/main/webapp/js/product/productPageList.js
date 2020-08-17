function LgProvincesSend(channelId,province,carriers,productId,contentRule)
{
	this.channelId=channelId;
	this.province=province;
	this.carriers=carriers;
	this.productId=productId;
	this.contentRule=contentRule;
}
$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
		$('#myTab').bootstrapTable('resetView', {
			height : tableHeight()
		})
	})
	$('#myTab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "findProductPageList.action",
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
		pageSize : 50,// 单页记录数
		pageList : [ 20, 50, 100 ],// 分页步进值
		showRefresh : false,// 刷新按钮
		showColumns : false,// 是否显示所有的列
		clickToSelect : false,// 是否启用点击选中行
		paginationShowPageGo: true,
		toolbarAlign : 'right',
		buttonsAlign : 'right',// 按钮对齐方式
		toolbar : '#toolbar',// 指定工作栏
		columns : [{
			title : 'ID',
			field : 'id',
			visible : false

		}, {
			title : '产品名称',
			field : 'productName',
			formatter : changeName
		}, {
			title : '描述',
			field : 'description',
			formatter : changeDescription
		}, {
			title : '移动通道ID',
			field : 'cmccChannelId',
			visible : false
		}, {
			title : '移动通道',
			field : 'cmccChannelName',
			formatter : cmccChannel
		}, {
			title : '联通通道ID',
			field : 'unicomChannelId',
			visible : false
		}, {
			title : '联通通道',
			field : 'unicomChannelName',
			formatter : unicomChannel
		}, {
			title : '电信通道ID',
			field : 'telecomChannelId',
			visible : false
		}, {
			title : '电信通道',
			field : 'telecomChannelName',
			formatter : telecomChannel
		}, {
			title : '国际通道',
			field : 'intlChannelName',
			formatter : intlChannel
		}, {
			title : "签名",
			field : "isSign",
			visible : false
		}, {
			title : "类别",
			field : "productClass",
			visible : false
		}, {
			title : "类型",
			field : "productType",
			visible : false
		}, {
			title : '类别/状态/签名',
			field : 'productStatus',
			formatter : changeResult
		}, {
			title : '账户使用数',
			field : 'appNumber',
			width : 40,
			align : "center",
			valign : 'middle',
			formatter : function(value, row, index) {
				var result = "";
				result += "<a href='javascript:;' class='btn btn-xs blue' data-toggle='modal'  data-target='#linkAccount' onclick=\"linkAccount('" + row.id + "')\" >" + value + "</a>";
				return result;
			}
		}, {
			title : '操作',
			field : 'id',
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
	$("#btn_add").click(function() {
		if (passed("新增")) {
			$("#addBody").modal();
		} else {
			$('.popup_de .show_msg').text('没有权限，请联系管理员！');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
		}
	});
	addValidator();
	updateValidator();
	$('.add_backBtn').click(function() {
		$("#addForm")[0].reset();
		$("#addForm").data('bootstrapValidator').destroy();
		$('#addForm').data('bootstrapValidator', null);
		addValidator();
		$("#add_saveBtn").attr("disabled", false);
		$("#addBody").modal("hide");
		$("#cmccChannelId").selectpicker('refresh');
		$("#unicomChannelId").selectpicker('refresh');
		$("#telecomChannelId").selectpicker('refresh');
	});
	
	$("#search_btn").click(function () {
       	$('#myTab').bootstrapTable('refreshOptions',{pageNumber:1});
	});
    $("#search_back").click(function () {
    	$("#searchProductId").val('');
    	$("#searchProductStatus").val('');
    	$("#searchChannelId").val('');
    	$('#myTab').bootstrapTable('refreshOptions',{pageNumber:1,limit:50});
    });
})

function addValidator() {
	$('#addForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			productName : {
				validators : {
					notEmpty : {
						message : '产品名称不能为空'
					},
					remote : {
						url : "validatorProduct.action",
						type : "POST",
						delay : 500,
						message : "产品名称已存在"
					},
					stringLength : {
						min : 4,
						max : 20,
						message : '产品名称长度必须在4到20位之间'
					}
				}
			},
			description : {
				validators : {
					stringLength : {
						min : 4,
						max : 20,
						message : '描述长度必须在4到20之间'
					}
				}
			}
		}
	});
}
function updateValidator() {
	// 更新表单验证
	$('#editForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			productName : {
				validators : {
					notEmpty : {
						message : '产品名称不能为空'
					},
					stringLength : {
						min : 4,
						max : 20,
						message : '产品名称长度必须在4到20位之间'
					},
					callback:{
         			   message:'产品名称已存在',
                        callback:function(value, validator,$field){
                     	   var oldProductName = $('#oldEditProductName').val();
                     	   var productName = $('#editProductName').val();
                     	   if(oldProductName==productName){
                     			return true;
                     	   }else{
                     		   var flag=true;
                     		   $.ajax({
                     			  url:'validatorProduct.action',
                     				data:{
                     					productName:productName,
                     					oldProductName:oldProductName
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
			description : {
				validators : {
					stringLength : {
						min : 4,
						max : 20,
						message : '描述长度必须在4到20之间'
					}
				}
			}
		}
	});
}
// 页面调整的大小
function tableHeight() {
	return $(window).height() - 30;
}
// 传递的参数
function queryParams(params) {
	return {
		pageSize : params.limit, // 页面大小
		pageIndex : params.pageNumber,
		productId:$("#searchProductId").val(),
		productStatus:$("#searchProductStatus").val(),
		channelId:$("#searchChannelId").val(),
	}
}
function actionFormatter(value, row, index) {
	var id = value;
	var result = "";
	result += "<button class='btn btn-default btn-xs' onclick=\"updateProduct('"
			+ value + "')\" title='修改'>修改</button>";
	return result;
}
function changeName(value, row, index) {
	return row.id + ":" + value;
}
function cmccChannel(value, row, index) {
	return row.cmccChannelId + ":" + value;
}
function unicomChannel(value, row, index) {
	return row.unicomChannelId + ":" + value;
}
function telecomChannel(value, row, index) {
	return row.telecomChannelId + ":" + value;
}
function intlChannel(value, row, index) {
	return row.intlChannelId + ":" + value;
}
function changeDescription(value, row, index) {
	var content = value;
	if (content != null && content.length > 10) {
		var result = "<a href='javascript:;' data-toggle='modal'  data-target='#Details' onclick=\"findDetails('"
				+ value + "')\">" + content.substring(0, 10) + "..." + "</a>";
		return result;
	}
	return content;
	return content;
}
function findDetails(row) {
	$("#Details_content").html(row);
}

function changeResult(value, row, index) {
	var result = "";
	result += row.productClass == '营销' ? '营销'
			: "<font color='#00e2f7'>行业</font>";
	result += "&nbsp;/&nbsp;";
	result += row.productStatus == 'normal' ? "正常"
			: row.productStatus == 'pause' ? "<font color='#f00'>禁用</font>"
					: '删除';
	result += "&nbsp;/&nbsp;";
	result += row.isSign == 'yes' ? '必须' : "<font color='#f00'>免签</font>";
	return result;
}
$(function() {
	$("#add_saveBtn").click(function() {
		mark=true;
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if ($("#addForm").data('bootstrapValidator').isValid()) {
			$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_cancel').css('display','inline-block');
			$('.btn_submit').one('click',function() {
				$("#add_saveBtn").attr("disabled", true);
				var productType = $("#addForm input[name='productType']:checked").val();
				var productClass = $("#addForm input[name='productClass']:checked").val();
				var productName = $("#productName").val();
				var description = $("#description").val();
				var cmccChannelId = $("#cmccChannelId").val();
				var unicomChannelId = $("#unicomChannelId").val();
				var telecomChannelId = $("#telecomChannelId").val();
				var intlChannelId = $("#intlChannelId").val();
				var isSign = $("#addForm input[name='isSign']:checked").val();
				if(mark){
					$.ajax({
						url :"addProduct.action",
						type:"post",
						data:{
							productType : productType,
							productClass : productClass,
							productName : productName,
							description : description,
							cmccChannelId : cmccChannelId,
							unicomChannelId : unicomChannelId,
							telecomChannelId : telecomChannelId,
							intlChannelId : intlChannelId,
							isSign : isSign
						},
						success:function(obj) {
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator',null);
							addValidator();
							$('#addForm')[0].reset();
							$("#cmccChannelId").selectpicker('refresh');
							$("#unicomChannelId").selectpicker('refresh');
							$("#telecomChannelId").selectpicker('refresh');
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('添加成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#myTab').bootstrapTable('refresh',{url : 'findProductPageList.action'});
							} else {
								$('.popup_de .show_msg').text('添加失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click',function() {
									$('.popup_de').removeClass('bbox');
								})
								$('#myTab').bootstrapTable('refresh',{url : 'findProductPageList.action'});
							}
							$("#add_saveBtn").attr("disabled",false);
							$("#addForm")[0].reset();
							$("#addForm").data('bootstrapValidator').destroy();
							$('#addForm').data('bootstrapValidator', null);
							addValidator();
							$("#addBody").modal("hide");
							$("#cmccChannelId").selectpicker('refresh');
							$("#unicomChannelId").selectpicker('refresh');
							$("#telecomChannelId").selectpicker('refresh');
						}, dataType:"json"})
					mark=false;
				}
					})
			}
	});
	$("#edit_saveBtn").click(function() {
		mark=true;
		$('#editForm').bootstrapValidator('validate');
		if ($("#editForm").data('bootstrapValidator').isValid()) {
			var productStatus = $("[name='productStatus']:checked").val();
			var id = $("#editId").val();
			var flag=true;
			if(productStatus=='deleted'){
				$.ajax({
					url : "validatorproductIsUse.action",
					data :{id:id},
					type:"post",
					async:false,
					success:function(obj){
						flag=obj.valid;
					},dataType:"json"
				});
			}
			if(flag){
				$('.popup_de .show_msg').text('确定要保存已填写的内容吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
			}else{
				$('.popup_de .show_msg').text('用户正在使用该产品，确定要保存已填写的内容吗?');
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_cancel').css('display', 'inline-block');
			}
			$('.btn_submit').one('click', function() {
				$("#edit_saveBtn").attr("disabled", true);
				var id = $("#editId").val();
				var productName = $("#editProductName").val();
				var description = $("#editDescription").val();
				var cmccChannelId = $("#editCmccChannelId").val();
				var unicomChannelId = $("#editUnicomChannelId").val();
				var telecomChannelId = $("#editTelecomChannelId").val();
				var intlChannelId = $("#editIntlChannelId").val();
				var productStatus = $("[name='productStatus']:checked").val();
				var isSign = $("#editForm input[name='isSign']:checked").val();
				if(mark){
					$.ajax({
						url : "updateProduct.action",
						type : "post",
						async : false,
						data : {
							id : id,
							productName : productName,
							description : description,
							cmccChannelId : cmccChannelId,
							unicomChannelId : unicomChannelId,
							telecomChannelId : telecomChannelId,
							intlChannelId : intlChannelId,
							productStatus : productStatus,
							isSign : isSign
						},
						success : function(obj) {
							$("#changeBody").modal("hide");
							if (obj.result > 0) {
								$('.popup_de .show_msg').text('修改成功！');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#myTab').bootstrapTable('refresh', {
										url : 'findProductPageList.action'
									});
								})
							} else {
								$('.popup_de .show_msg').text('修改失败');
								$('.popup_de').addClass('bbox');
								$('.popup_de .btn_submit').one('click', function() {
									$('.popup_de').removeClass('bbox');
									$('#myTab').bootstrapTable('refresh', {
										url : 'findProductPageList.action'
									});
								})
							}
							$("#edit_saveBtn").attr("disabled", false);
							$("#editForm").data('bootstrapValidator').destroy();
							$('#editForm').data('bootstrapValidator', null);
							updateValidator();
							$("#changeBody").modal('hide');
							$("#editForm")[0].reset();
							$("#cmccForm")[0].reset();
							$("#unicomForm")[0].reset();
							$("#telecomForm")[0].reset();
							$("select").each(function(){
								$(this).selectpicker('refresh');
							})
							$("#editForm label").removeClass("active");
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
	$('.edit_backBtn').click(function() {
		$("#editForm").data('bootstrapValidator').destroy();
		$('#editForm').data('bootstrapValidator', null);
		updateValidator();
		$("#changeBody").modal('hide');
		$("#editForm")[0].reset();
		$("#cmccForm")[0].reset();
		$("#unicomForm")[0].reset();
		$("#telecomForm")[0].reset();
		$("select").each(function(){
			$(this).selectpicker('refresh');
		})
		$("#editForm label").removeClass("active");
	});
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	$("#cmcc_btn").click(function(){
		var provinces=new Array();
		var cmccShangHai= $("#cmccShangHai").val();
		var cmccYunNan=$("#cmccYunNan").val();
		var cmccNeiMengGu=$("#cmccNeiMengGu").val();
		var cmccBeiJing=$("#cmccBeiJing").val();
		var cmccJiLin=$("#cmccJiLin").val();
		var cmccSiChuan=$("#cmccSiChuan").val();
		var cmccTianjin=$("#cmccTianJin").val();
		var cmccNingXia=$("#cmccNingXia").val();
		var cmccAnHui=$("#cmccAnHui").val();
		var cmccShanDong=$("#cmccShanDong").val();
		var cmccShanXi=$("#cmccShanXi").val();
		var cmccGuangDong=$("#cmccGuangDong").val();
		var cmccGuangXi=$("#cmccGuangXi").val();
		var cmccXinJiang=$("#cmccXinJiang").val();
		var cmccJiangSu=$("#cmccJiangSu").val();
		var cmccJiangXi=$("#cmccJiangXi").val();
		var cmccHeBei=$("#cmccHeBei").val();
		var cmccHeNan=$("#cmccHeNan").val();
		var cmccZheJiang=$("#cmccZheJiang").val();
		var cmccHaiNan=$("#cmccHaiNan").val();
		var cmccHuBei=$("#cmccHuBei").val();
		var cmccHuNan=$("#cmccHuNan").val();
		var cmccGanSu=$("#cmccGanSu").val();
		var cmccFuJian=$("#cmccFuJian").val();
		var cmccXiZang=$("#cmccXiZang").val();
		var cmccGuiZhou=$("#cmccGuiZhou").val();
		var cmccLiaoNing=$("#cmccLiaoNing").val();
		var cmccChongQing=$("#cmccChongQing").val();
		var cmccSX=$("#cmccSX").val();
		var cmccQingHai=$("#cmccQingHai").val();
		var cmccHeiLongJiang=$("#cmccHeiLongJiang").val();
		var id=$("#editId").val();
		if(isExist(cmccShangHai)){
			var content=$("#cmccShangHaiContent").val();
			provinces.push(new LgProvincesSend(cmccShangHai, "上海", "cmcc", id, content));
		}
		if(isExist(cmccYunNan)){
			var content=$("#cmccYunNanContent").val();
			provinces.push(new LgProvincesSend(cmccYunNan, "云南", "cmcc", id, content));
		}
		if(isExist(cmccNeiMengGu)){
			var content=$("#cmccNeiMengGuContent").val();
			provinces.push(new LgProvincesSend(cmccNeiMengGu, "内蒙古", "cmcc", id, content));
		}
		if(isExist(cmccBeiJing)){
			var content=$("#cmccBeiJingContent").val();
			provinces.push(new LgProvincesSend(cmccBeiJing, "北京", "cmcc", id, content));
		}
		if(isExist(cmccJiLin)){
			var content=$("#cmccJiLinContent").val();
			provinces.push(new LgProvincesSend(cmccJiLin, "吉林", "cmcc", id, content));
		}
		if(isExist(cmccSiChuan)){
			var content=$("#cmccSiChuanContent").val();
			provinces.push(new LgProvincesSend(cmccSiChuan, "四川", "cmcc", id, content));
		}
		if(isExist(cmccTianjin)){
			var content=$("#cmccTianJinContent").val();
			provinces.push(new LgProvincesSend(cmccTianjin, "天津", "cmcc", id, content));
		}
		if(isExist(cmccNingXia)){
			var content=$("#cmccNingXiaContent").val();
			provinces.push(new LgProvincesSend(cmccNingXia, "宁夏", "cmcc", id, content));
		}
		if(isExist(cmccAnHui)){
			var content=$("#cmccAnHuiContent").val();
			provinces.push(new LgProvincesSend(cmccAnHui, "安徽", "cmcc", id, content));
		}
		if(isExist(cmccShanDong)){
			var content=$("#cmccShanDongContent").val();
			provinces.push(new LgProvincesSend(cmccShanDong, "山东", "cmcc", id, content));
		}
		if(isExist(cmccShanXi)){
			var content=$("#cmccShanXiContent").val();
			provinces.push(new LgProvincesSend(cmccShanXi, "山西", "cmcc", id, content));
		}
		if(isExist(cmccGuangDong)){
			var content=$("#cmccGuangDongContent").val();
			provinces.push(new LgProvincesSend(cmccGuangDong, "广东", "cmcc", id, content));
		}
		if(isExist(cmccGuangXi)){
			var content=$("#cmccGuangXiContent").val();
			provinces.push(new LgProvincesSend(cmccGuangXi, "广西", "cmcc", id, content));
		}
		if(isExist(cmccXinJiang)){
			var content=$("#cmccXinJiangContent").val();
			provinces.push(new LgProvincesSend(cmccXinJiang, "新疆", "cmcc", id, content));
		}
		if(isExist(cmccJiangSu)){
			var content=$("#cmccJiangSuContent").val();
			provinces.push(new LgProvincesSend(cmccJiangSu, "江苏", "cmcc", id, content));
		}
		if(isExist(cmccJiangXi)){
			var content=$("#cmccJiangXiContent").val();
			provinces.push(new LgProvincesSend(cmccJiangXi, "江西", "cmcc", id, content));
		}
		if(isExist(cmccHeBei)){
			var content=$("#cmccHeBeiContent").val();
			provinces.push(new LgProvincesSend(cmccHeBei, "河北", "cmcc", id, content));
		}
		if(isExist(cmccHeNan)){
			var content=$("#cmccHeNanContent").val();
			provinces.push(new LgProvincesSend(cmccHeNan, "河南", "cmcc", id, content));
		}
		if(isExist(cmccZheJiang)){
			var content=$("#cmccZheJiangContent").val();
			provinces.push(new LgProvincesSend(cmccZheJiang, "浙江", "cmcc", id, content));
		}
		if(isExist(cmccHaiNan)){
			var content=$("#cmccHaiNanContent").val();
			provinces.push(new LgProvincesSend(cmccHaiNan, "海南", "cmcc", id, content));
		}
		if(isExist(cmccHuBei)){
			var content=$("#cmccHuBeiContent").val();
			provinces.push(new LgProvincesSend(cmccHuBei, "湖北", "cmcc", id, content));
		}
		if(isExist(cmccHuNan)){
			var content=$("#cmccHuNanContent").val();
			provinces.push(new LgProvincesSend(cmccHuNan, "湖南", "cmcc", id, content));
		}
		if(isExist(cmccGanSu)){
			var content=$("#cmccGanSuContent").val();
			provinces.push(new LgProvincesSend(cmccGanSu, "甘肃", "cmcc", id, content));
		}
		if(isExist(cmccFuJian)){
			var content=$("#cmccFuJianContent").val();
			provinces.push(new LgProvincesSend(cmccFuJian, "福建", "cmcc", id, content));
		}
		if(isExist(cmccXiZang)){
			var content=$("#cmccXiZangContent").val();
			provinces.push(new LgProvincesSend(cmccXiZang, "西藏", "cmcc", id, content));
		}
		if(isExist(cmccGuiZhou)){
			var content=$("#cmccGuiZhouContent").val();
			provinces.push(new LgProvincesSend(cmccGuiZhou, "贵州", "cmcc", id, content));
		}
		if(isExist(cmccLiaoNing)){
			var content=$("#cmccLiaoNingContent").val();
			provinces.push(new LgProvincesSend(cmccLiaoNing, "辽宁", "cmcc", id, content));
		}
		if(isExist(cmccChongQing)){
			var content=$("#cmccChongQingContent").val();
			provinces.push(new LgProvincesSend(cmccChongQing, "重庆", "cmcc", id, content));
		}
		if(isExist(cmccSX)){
			var content=$("#cmccSXContent").val();
			provinces.push(new LgProvincesSend(cmccSX, "陕西", "cmcc", id, content));
		}
		if(isExist(cmccQingHai)){
			var content=$("#cmccQingHaiContent").val();
			provinces.push(new LgProvincesSend(cmccQingHai, "青海", "cmcc", id, content));
		}
		if(isExist(cmccHeiLongJiang)){
			var content=$("#cmccHeiLongJiangContent").val();
			provinces.push(new LgProvincesSend(cmccHeiLongJiang, "黑龙江", "cmcc", id, content));
		}
		if(provinces.length==0){
			$('.popup_de .show_msg').text('没有选择分省通道');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		$.ajax({
			url :"provinceConfig.action",
			data:{'json':JSON.stringify(provinces)},
			success : function(obj){
				$("#changeBody").modal("hide");
				if (obj.result > 0) {
					$('.popup_de .show_msg').text('分省配置成功！');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
						$("#editForm").data('bootstrapValidator').destroy();
						$('#editForm').data('bootstrapValidator', null);
						updateValidator();
						$("#changeBody").modal('hide');
						$("#editForm")[0].reset();
						$("#cmccForm")[0].reset();
						$("#unicomForm")[0].reset();
						$("#telecomForm")[0].reset();
						$("select").each(function(){
							$(this).selectpicker('refresh');
						})
						$("#editForm label").removeClass("active");
						$('#myTab').bootstrapTable('refresh', {
							url : 'findProductPageList.action'
						});
					})
				} else {
					$('.popup_de .show_msg').text('分省配置成功');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
						$('#myTab').bootstrapTable('refresh', {
							url : 'findProductPageList.action'
						});
					})
				}
			},
			type:"post",
			dataType:"json"
			
		})
	})
	$("#unicom_btn").click(function(){
		var provinces=new Array();
		var unicomShangHai= $("#unicomShangHai").val();
		var unicomYunNan=$("#unicomYunNan").val();
		var unicomNeiMengGu=$("#unicomNeiMengGu").val();
		var unicomBeiJing=$("#unicomBeiJing").val();
		var unicomJiLin=$("#unicomJiLin").val();
		var unicomSiChuan=$("#unicomSiChuan").val();
		var unicomTianjin=$("#unicomTianJin").val();
		var unicomNingXia=$("#unicomNingXia").val();
		var unicomAnHui=$("#unicomAnHui").val();
		var unicomShanDong=$("#unicomShanDong").val();
		var unicomShanXi=$("#unicomShanXi").val();
		var unicomGuangDong=$("#unicomGuangDong").val();
		var unicomGuangXi=$("#unicomGuangXi").val();
		var unicomXinJiang=$("#unicomXinJiang").val();
		var unicomJiangSu=$("#unicomJiangSu").val();
		var unicomJiangXi=$("#unicomJiangXi").val();
		var unicomHeBei=$("#unicomHeBei").val();
		var unicomHeNan=$("#unicomHeNan").val();
		var unicomZheJiang=$("#unicomZheJiang").val();
		var unicomHaiNan=$("#unicomHaiNan").val();
		var unicomHuBei=$("#unicomHuBei").val();
		var unicomHuNan=$("#unicomHuNan").val();
		var unicomGanSu=$("#unicomGanSu").val();
		var unicomFuJian=$("#unicomFuJian").val();
		var unicomXiZang=$("#unicomXiZang").val();
		var unicomGuiZhou=$("#unicomGuiZhou").val();
		var unicomLiaoNing=$("#unicomLiaoNing").val();
		var unicomChongQing=$("#unicomChongQing").val();
		var unicomSX=$("#unicomSX").val();
		var unicomQingHai=$("#unicomQingHai").val();
		var unicomHeiLongJiang=$("#unicomHeiLongJiang").val();
		var id=$("#editId").val();
		if(isExist(unicomShangHai)){
			var content=$("#unicomShangHaiContent").val();
			provinces.push(new LgProvincesSend(unicomShangHai, "上海", "unicom", id, content));
		}
		if(isExist(unicomYunNan)){
			var content=$("#unicomYunNanContent").val();
			provinces.push(new LgProvincesSend(unicomYunNan, "云南", "unicom", id, content));
		}
		if(isExist(unicomNeiMengGu)){
			var content=$("#unicomNeiMengGuContent").val();
			provinces.push(new LgProvincesSend(unicomNeiMengGu, "内蒙古", "unicom", id, content));
		}
		if(isExist(unicomBeiJing)){
			var content=$("#unicomBeiJingContent").val();
			provinces.push(new LgProvincesSend(unicomBeiJing, "北京", "unicom", id, content));
		}
		if(isExist(unicomJiLin)){
			var content=$("#unicomJiLinContent").val();
			provinces.push(new LgProvincesSend(unicomJiLin, "吉林", "unicom", id, content));
		}
		if(isExist(unicomSiChuan)){
			var content=$("#unicomSiChuanContent").val();
			provinces.push(new LgProvincesSend(unicomSiChuan, "四川", "unicom", id, content));
		}
		if(isExist(unicomTianjin)){
			var content=$("#unicomTianJinContent").val();
			provinces.push(new LgProvincesSend(unicomTianjin, "天津", "unicom", id, content));
		}
		if(isExist(unicomNingXia)){
			var content=$("#unicomNingXiaContent").val();
			provinces.push(new LgProvincesSend(unicomNingXia, "宁夏", "unicom", id, content));
		}
		if(isExist(unicomAnHui)){
			var content=$("#unicomAnHuiContent").val();
			provinces.push(new LgProvincesSend(unicomAnHui, "安徽", "unicom", id, content));
		}
		if(isExist(unicomShanDong)){
			var content=$("#unicomShanDongContent").val();
			provinces.push(new LgProvincesSend(unicomShanDong, "山东", "unicom", id, content));
		}
		if(isExist(unicomShanXi)){
			var content=$("#unicomShanXiContent").val();
			provinces.push(new LgProvincesSend(unicomShanXi, "山西", "unicom", id, content));
		}
		if(isExist(unicomGuangDong)){
			var content=$("#unicomGuangDongContent").val();
			provinces.push(new LgProvincesSend(unicomGuangDong, "广东", "unicom", id, content));
		}
		if(isExist(unicomGuangXi)){
			var content=$("#unicomGuangXiContent").val();
			provinces.push(new LgProvincesSend(unicomGuangXi, "广西", "unicom", id, content));
		}
		if(isExist(unicomXinJiang)){
			var content=$("#unicomXinJiangContent").val();
			provinces.push(new LgProvincesSend(unicomXinJiang, "新疆", "unicom", id, content));
		}
		if(isExist(unicomJiangSu)){
			var content=$("#unicomJiangSuContent").val();
			provinces.push(new LgProvincesSend(unicomJiangSu, "江苏", "unicom", id, content));
		}
		if(isExist(unicomJiangXi)){
			var content=$("#unicomJiangXiContent").val();
			provinces.push(new LgProvincesSend(unicomJiangXi, "江西", "unicom", id, content));
		}
		if(isExist(unicomHeBei)){
			var content=$("#unicomHeBeiContent").val();
			provinces.push(new LgProvincesSend(unicomHeBei, "河北", "unicom", id, content));
		}
		if(isExist(unicomHeNan)){
			var content=$("#unicomHeNanContent").val();
			provinces.push(new LgProvincesSend(unicomHeNan, "河南", "unicom", id, content));
		}
		if(isExist(unicomZheJiang)){
			var content=$("#unicomZheJiangContent").val();
			provinces.push(new LgProvincesSend(unicomZheJiang, "浙江", "unicom", id, content));
		}
		if(isExist(unicomHaiNan)){
			var content=$("#unicomHaiNanContent").val();
			provinces.push(new LgProvincesSend(unicomHaiNan, "海南", "unicom", id, content));
		}
		if(isExist(unicomHuBei)){
			var content=$("#unicomHuBeiContent").val();
			provinces.push(new LgProvincesSend(unicomHuBei, "湖北", "unicom", id, content));
		}
		if(isExist(unicomHuNan)){
			var content=$("#unicomHuNanContent").val();
			provinces.push(new LgProvincesSend(unicomHuNan, "湖南", "unicom", id, content));
		}
		if(isExist(unicomGanSu)){
			var content=$("#unicomGanSuContent").val();
			provinces.push(new LgProvincesSend(unicomGanSu, "甘肃", "unicom", id, content));
		}
		if(isExist(unicomFuJian)){
			var content=$("#unicomFuJianContent").val();
			provinces.push(new LgProvincesSend(unicomFuJian, "福建", "unicom", id, content));
		}
		if(isExist(unicomXiZang)){
			var content=$("#unicomXiZangContent").val();
			provinces.push(new LgProvincesSend(unicomXiZang, "西藏", "unicom", id, content));
		}
		if(isExist(unicomGuiZhou)){
			var content=$("#unicomGuiZhouContent").val();
			provinces.push(new LgProvincesSend(unicomGuiZhou, "贵州", "unicom", id, content));
		}
		if(isExist(unicomLiaoNing)){
			var content=$("#unicomLiaoNingContent").val();
			provinces.push(new LgProvincesSend(unicomLiaoNing, "辽宁", "unicom", id, content));
		}
		if(isExist(unicomChongQing)){
			var content=$("#unicomChongQingContent").val();
			provinces.push(new LgProvincesSend(unicomChongQing, "重庆", "unicom", id, content));
		}
		if(isExist(unicomSX)){
			var content=$("#unicomSXContent").val();
			provinces.push(new LgProvincesSend(unicomSX, "陕西", "unicom", id, content));
		}
		if(isExist(unicomQingHai)){
			var content=$("#unicomQingHaiContent").val();
			provinces.push(new LgProvincesSend(unicomQingHai, "青海", "unicom", id, content));
		}
		if(isExist(unicomHeiLongJiang)){
			var content=$("#unicomHeiLongJiangContent").val();
			provinces.push(new LgProvincesSend(unicomHeiLongJiang, "黑龙江", "unicom", id, content));
		}
		if(provinces.length==0){
			$('.popup_de .show_msg').text('没有选择分省通道');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		$.ajax({
			url :"provinceConfig.action",
			data:{'json':JSON.stringify(provinces)},
			success : function(obj){
				$("#changeBody").modal("hide");
				if (obj.result > 0) {
					$('.popup_de .show_msg').text('分省配置成功！');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
						$("#editForm").data('bootstrapValidator').destroy();
						$('#editForm').data('bootstrapValidator', null);
						updateValidator();
						$("#changeBody").modal('hide');
						$("#editForm")[0].reset();
						$("#cmccForm")[0].reset();
						$("#unicomForm")[0].reset();
						$("#telecomForm")[0].reset();
						$("select").each(function(){
							$(this).selectpicker('refresh');
						})
						$("#editForm label").removeClass("active");
						$('#myTab').bootstrapTable('refresh', {
							url : 'findProductPageList.action'
						});
					})
				} else {
					$('.popup_de .show_msg').text('分省配置成功');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
						$('#myTab').bootstrapTable('refresh', {
							url : 'findProductPageList.action'
						});
					})
				}
			},
			type:"post",
			dataType:"json"
			
		})
		
	})
	$("#telecom_btn").click(function(){
		var provinces=new Array();
		var telecomShangHai= $("#telecomShangHai").val();
		var telecomYunNan=$("#telecomYunNan").val();
		var telecomNeiMengGu=$("#telecomNeiMengGu").val();
		var telecomBeiJing=$("#telecomBeiJing").val();
		var telecomJiLin=$("#telecomJiLin").val();
		var telecomSiChuan=$("#telecomSiChuan").val();
		var telecomTianjin=$("#telecomTianJin").val();
		var telecomNingXia=$("#telecomNingXia").val();
		var telecomAnHui=$("#telecomAnHui").val();
		var telecomShanDong=$("#telecomShanDong").val();
		var telecomShanXi=$("#telecomShanXi").val();
		var telecomGuangDong=$("#telecomGuangDong").val();
		var telecomGuangXi=$("#telecomGuangXi").val();
		var telecomXinJiang=$("#telecomXinJiang").val();
		var telecomJiangSu=$("#telecomJiangSu").val();
		var telecomJiangXi=$("#telecomJiangXi").val();
		var telecomHeBei=$("#telecomHeBei").val();
		var telecomHeNan=$("#telecomHeNan").val();
		var telecomZheJiang=$("#telecomZheJiang").val();
		var telecomHaiNan=$("#telecomHaiNan").val();
		var telecomHuBei=$("#telecomHuBei").val();
		var telecomHuNan=$("#telecomHuNan").val();
		var telecomGanSu=$("#telecomGanSu").val();
		var telecomFuJian=$("#telecomFuJian").val();
		var telecomXiZang=$("#telecomXiZang").val();
		var telecomGuiZhou=$("#telecomGuiZhou").val();
		var telecomLiaoNing=$("#telecomLiaoNing").val();
		var telecomChongQing=$("#telecomChongQing").val();
		var telecomSX=$("#telecomSX").val();
		var telecomQingHai=$("#telecomQingHai").val();
		var telecomHeiLongJiang=$("#telecomHeiLongJiang").val();
		var id=$("#editId").val();
		if(isExist(telecomShangHai)){
			var content=$("#telecomShangHaiContent").val();
			provinces.push(new LgProvincesSend(telecomShangHai, "上海", "telecom", id, content));
		}
		if(isExist(telecomYunNan)){
			var content=$("#telecomYunNanContent").val();
			provinces.push(new LgProvincesSend(telecomYunNan, "云南", "telecom", id, content));
		}
		if(isExist(telecomNeiMengGu)){
			var content=$("#telecomNeiMengGuContent").val();
			provinces.push(new LgProvincesSend(telecomNeiMengGu, "内蒙古", "telecom", id, content));
		}
		if(isExist(telecomBeiJing)){
			var content=$("#telecomBeiJingContent").val();
			provinces.push(new LgProvincesSend(telecomBeiJing, "北京", "telecom", id, content));
		}
		if(isExist(telecomJiLin)){
			var content=$("#telecomJiLinContent").val();
			provinces.push(new LgProvincesSend(telecomJiLin, "吉林", "telecom", id, content));
		}
		if(isExist(telecomSiChuan)){
			var content=$("#telecomSiChuanContent").val();
			provinces.push(new LgProvincesSend(telecomSiChuan, "四川", "telecom", id, content));
		}
		if(isExist(telecomTianjin)){
			var content=$("#telecomTianJinContent").val();
			provinces.push(new LgProvincesSend(telecomTianjin, "天津", "telecom", id, content));
		}
		if(isExist(telecomNingXia)){
			var content=$("#telecomNingXiaContent").val();
			provinces.push(new LgProvincesSend(telecomNingXia, "宁夏", "telecom", id, content));
		}
		if(isExist(telecomAnHui)){
			var content=$("#telecomAnHuiContent").val();
			provinces.push(new LgProvincesSend(telecomAnHui, "安徽", "telecom", id, content));
		}
		if(isExist(telecomShanDong)){
			var content=$("#telecomShanDongContent").val();
			provinces.push(new LgProvincesSend(telecomShanDong, "山东", "telecom", id, content));
		}
		if(isExist(telecomShanXi)){
			var content=$("#telecomShanXiContent").val();
			provinces.push(new LgProvincesSend(telecomShanXi, "山西", "telecom", id, content));
		}
		if(isExist(telecomGuangDong)){
			var content=$("#telecomGuangDongContent").val();
			provinces.push(new LgProvincesSend(telecomGuangDong, "广东", "telecom", id, content));
		}
		if(isExist(telecomGuangXi)){
			var content=$("#telecomGuangXiContent").val();
			provinces.push(new LgProvincesSend(telecomGuangXi, "广西", "telecom", id, content));
		}
		if(isExist(telecomXinJiang)){
			var content=$("#telecomXinJiangContent").val();
			provinces.push(new LgProvincesSend(telecomXinJiang, "新疆", "telecom", id, content));
		}
		if(isExist(telecomJiangSu)){
			var content=$("#telecomJiangSuContent").val();
			provinces.push(new LgProvincesSend(telecomJiangSu, "江苏", "telecom", id, content));
		}
		if(isExist(telecomJiangXi)){
			var content=$("#telecomJiangXiContent").val();
			provinces.push(new LgProvincesSend(telecomJiangXi, "江西", "telecom", id, content));
		}
		if(isExist(telecomHeBei)){
			var content=$("#telecomHeBeiContent").val();
			provinces.push(new LgProvincesSend(telecomHeBei, "河北", "telecom", id, content));
		}
		if(isExist(telecomHeNan)){
			var content=$("#telecomHeNanContent").val();
			provinces.push(new LgProvincesSend(telecomHeNan, "河南", "telecom", id, content));
		}
		if(isExist(telecomZheJiang)){
			var content=$("#telecomZheJiangContent").val();
			provinces.push(new LgProvincesSend(telecomZheJiang, "浙江", "telecom", id, content));
		}
		if(isExist(telecomHaiNan)){
			var content=$("#telecomHaiNanContent").val();
			provinces.push(new LgProvincesSend(telecomHaiNan, "海南", "telecom", id, content));
		}
		if(isExist(telecomHuBei)){
			var content=$("#telecomHuBeiContent").val();
			provinces.push(new LgProvincesSend(telecomHuBei, "湖北", "telecom", id, content));
		}
		if(isExist(telecomHuNan)){
			var content=$("#telecomHuNanContent").val();
			provinces.push(new LgProvincesSend(telecomHuNan, "湖南", "telecom", id, content));
		}
		if(isExist(telecomGanSu)){
			var content=$("#telecomGanSuContent").val();
			provinces.push(new LgProvincesSend(telecomGanSu, "甘肃", "telecom", id, content));
		}
		if(isExist(telecomFuJian)){
			var content=$("#telecomFuJianContent").val();
			provinces.push(new LgProvincesSend(telecomFuJian, "福建", "telecom", id, content));
		}
		if(isExist(telecomXiZang)){
			var content=$("#telecomXiZangContent").val();
			provinces.push(new LgProvincesSend(telecomXiZang, "西藏", "telecom", id, content));
		}
		if(isExist(telecomGuiZhou)){
			var content=$("#telecomGuiZhouContent").val();
			provinces.push(new LgProvincesSend(telecomGuiZhou, "贵州", "telecom", id, content));
		}
		if(isExist(telecomLiaoNing)){
			var content=$("#telecomLiaoNingContent").val();
			provinces.push(new LgProvincesSend(telecomLiaoNing, "辽宁", "telecom", id, content));
		}
		if(isExist(telecomChongQing)){
			var content=$("#telecomChongQingContent").val();
			provinces.push(new LgProvincesSend(telecomChongQing, "重庆", "telecom", id, content));
		}
		if(isExist(telecomSX)){
			var content=$("#telecomSXContent").val();
			provinces.push(new LgProvincesSend(telecomSX, "陕西", "telecom", id, content));
		}
		if(isExist(telecomQingHai)){
			var content=$("#telecomQingHaiContent").val();
			provinces.push(new LgProvincesSend(telecomQingHai, "青海", "telecom", id, content));
		}
		if(isExist(telecomHeiLongJiang)){
			var content=$("#telecomHeiLongJiangContent").val();
			provinces.push(new LgProvincesSend(telecomHeiLongJiang, "黑龙江", "telecom", id, content));
		}
		if(provinces.length==0){
			$('.popup_de .show_msg').text('没有选择分省通道');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}
		$.ajax({
			url :"provinceConfig.action",
			data:{'json':JSON.stringify(provinces)},
			success : function(obj){
				$("#changeBody").modal("hide");
				if (obj.result > 0) {
					$('.popup_de .show_msg').text('分省配置成功！');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
						$("#editForm").data('bootstrapValidator').destroy();
						$('#editForm').data('bootstrapValidator', null);
						updateValidator();
						$("#changeBody").modal('hide');
						$("#editForm")[0].reset();
						$("#cmccForm")[0].reset();
						$("#unicomForm")[0].reset();
						$("#telecomForm")[0].reset();
						$("select").each(function(){
							$(this).selectpicker('refresh');
						})
						$("#editForm label").removeClass("active");
						$('#myTab').bootstrapTable('refresh', {
							url : 'findProductPageList.action'
						});
					})
				} else {
					$('.popup_de .show_msg').text('分省配置失败');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click', function() {
						$('.popup_de').removeClass('bbox');
						$('#myTab').bootstrapTable('refresh', {
							url : 'findProductPageList.action'
						});
					})
				}
			},
			type:"post",
			dataType:"json"
			
		})
	})
})
function updateProduct(id) {
	$("#editForm label").removeClass("active");
	if (passed("修改")) {
		$("#editForm")[0].reset();
		$("#cmccForm")[0].reset();
		$("#unicomForm")[0].reset();
		$("#telecomForm")[0].reset();
		$("select").each(function(){
			$(this).selectpicker('refresh');
		})
		$.post("findProductById.action", {
			id : id
		}, function(obj) {
			var product=obj.product;
			var sends=obj.provincesSends;
			$("#editId").val(product.id);
			$("#productProperty").val(
					" " + product.id + " / "
							+ (product.productType == "sms" ? '短信' : '彩信') + " / "
							+ product.productClass);
			$("#editProductName").val(product.productName);
			$("#oldEditProductName").val(product.productName);
			$("#editDescription").val(product.description);
			$("#editCmccChannelId").selectpicker('val', product.cmccChannelId);
			$("#editUnicomChannelId").selectpicker('val', product.unicomChannelId);
			$("#editTelecomChannelId")
					.selectpicker('val', product.telecomChannelId);
			$("#editIntlChannelId")
			.selectpicker('val', product.intlChannelId);

			$("#editForm [name='productStatus']").map(function() {
				if ($(this).val() == product.productStatus) {
					$(this).attr("checked", "checked");
					$(this).parent().addClass("active")
				}
			});
			$("#editForm [name='isSign']").map(function() {
				if ($(this).val() == product.isSign) {
					$(this).attr("checked", "checked");
					$(this).parent().addClass("active")
				}
			});
			var st="";
			for(var i in sends){
				st += sends[i].carriers;
				if (sends[i].province == '北京') {
					st+="BeiJing";	
				} else if (sends[i].province == '上海') {
					st+="ShangHai";
				} else if (sends[i].province == '云南') {
					st+="YunNan";
				} else if (sends[i].province == '内蒙古') {
					st+="NeiMengGu";
				} else if (sends[i].province == '吉林') {
					st+="JiLin";
				} else if (sends[i].province == '四川') {
					st+="SiChuan";
				} else if (sends[i].province == '天津') {
					st+="TianJin";
				} else if (sends[i].province == '宁夏') {
					st+="NingXia";
				} else if (sends[i].province == '安徽') {
					st+="AnHui";
				} else if (sends[i].province == '山东') {
					st+="ShanDong";
				} else if (sends[i].province == '山西') {
					st+="ShanXi";
				} else if (sends[i].province == '广东') {
					st+="GuangDong";
				} else if (sends[i].province == '广西') {
					st+="GuangXi";
				} else if (sends[i].province == '新疆') {
					st+="XinJiang";
				} else if (sends[i].province == '江苏') {
					st+="JiangSu";
				} else if (sends[i].province == '江西') {
					st+="JiangXi";
				} else if (sends[i].province == '河北') {
					st+="HeBei";
				} else if (sends[i].province == '河南') {
					st+="HeNan";
				} else if (sends[i].province == '浙江') {
					st+="ZheJiang";
				} else if (sends[i].province == '海南') {
					st+="HaiNan";
				} else if (sends[i].province == '湖北') {
					st+="HuBei";
				} else if (sends[i].province == '湖南') {
					st+="HuNan";
				} else if (sends[i].province == '甘肃') {
					st+="GanSu";
				} else if (sends[i].province == '福建') {
					st+="FuJian";
				} else if (sends[i].province == '西藏') {
					st+="XiZang";
				} else if (sends[i].province == '贵州') {
					st+="GuiZhou";
				} else if (sends[i].province == '辽宁') {
					st+="LiaoNing";
				} else if (sends[i].province == '重庆') {
					st+="ChongQing";
				} else if (sends[i].province == '陕西') {
					st+="SX";
				} else if (sends[i].province == '青海') {
					st+="QingHai";
				} else if (sends[i].province == '黑龙江') {
					st+="HeiLongJiang";
				}
				$("#"+st).selectpicker('val', sends[i].channelId);
				$("#"+st+"Content").val(sends[i].contentRule);
				st="";
			}
			$("#changeBody").modal();
			$('#editForm').bootstrapValidator('validate');
		}, "json");
	} else {
		$('.popup_de .show_msg').text('没有权限，请联系管理员！');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			$('.popup_de').removeClass('bbox');
		})
	}
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
function isExist(value){
	var flag=true;
	if(value =="" || value==null || value== undefined){
		flag=false;
	}
	return flag;
}
function sleep(n) {

    var start = new Date().getTime();

    while(true)  if(new Date().getTime()-start > n) break;

}

function linkAccount(productId){
//	$('#tLinkAccount').remove();
//	var url = "findLinkAcc.action?id="+productId;
	var url = "findLinkAcc.action?id="+productId;
	$('#tLinkAccount').bootstrapTable('refresh', {
		url : url
	});
}


	$('#tLinkAccount').bootstrapTable({
		method: 'get',
		contentType : "application/x-www-form-urlencoded",
		url:'',//请求后台url
		height:tableHeight(),//高度调整
		dataType:"json",//返回的数据格式
		cache:false,//是否缓存 默认是true
		async:false,
		
		columns:[{
				title:'帐户名称',
				field:'appName',
				width: 200
			},
			{
				title:'单价',
				field:'price',
				width: 200
			},
			{
				title:'状态',
				field:'appStatus',
				width: 200,
				formatter : function(value, row, index) {
					if (value == 'normal') {
						return "正常";
					} else if (value == 'pause') {
						return "暂停";
					} else if(value == 'deleted'){
						return "删除";
					}
				}
			}
			],
			locale:'zh-CN'//中文支持,
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