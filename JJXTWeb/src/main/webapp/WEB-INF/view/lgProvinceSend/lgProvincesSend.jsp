<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分省发送</title>
<style type="text/css">
.td {
	width: 100px;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<select name="product" id="search_productId"
						class="selectpicker show-tick form-control" placeholder="全部产品" data-width="98%" data-live-search="true">
						<option value="">全部产品</option>
						<c:forEach var="s" items="${products }">
							<option value="${s.id}">${s.id}:${s.productName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="channelId" id="search_channelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="carriers" id="search_carriers"
						class="selectpicker show-tick form-control" placeholder="全部运营商"
						data-width="98%" data-live-search="true">
						<option value="">全部运营商</option>
						<option value="cmcc">移动</option>
						<option value="unicom">联通</option>
						<option value="telecom">电信</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="province" id="search_province"
						class="selectpicker show-tick form-control" placeholder="全部省份"
						data-width="98%" data-live-search="true">
						<option value="">全部省份</option>
						<option value="上海">上海</option>
						<option value="云南">云南</option>
						<option value="内蒙古">内蒙古</option>
						<option value="北京">北京</option>
						<option value="吉林">吉林</option>
						<option value="四川">四川</option>
						<option value="天津">天津</option>
						<option value="宁夏">宁夏</option>
						<option value="安徽">安徽</option>
						<option value="山东">山东</option>
						<option value="山西">山西</option>
						<option value="广东">广东</option>
						<option value="广西">广西</option>
						<option value="新疆">新疆</option>
						<option value="江苏">江苏</option>
						<option value="江西">江西</option>
						<option value="河北">河北</option>
						<option value="河南">河南</option>
						<option value="浙江">浙江</option>
						<option value="海南">海南</option>
						<option value="湖北">湖北</option>
						<option value="湖南">湖南</option>
						<option value="甘肃">甘肃</option>
						<option value="福建">福建</option>
						<option value="西藏">西藏</option>
						<option value="贵州">贵州</option>
						<option value="辽宁">辽宁</option>
						<option value="重庆">重庆</option>
						<option value="陕西">陕西</option>
						<option value="青海">青海</option>
						<option value="黑龙江">黑龙江</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="status" id="search_status"
						class="selectpicker show-tick form-control" placeholder="状态"
						data-width="98%" data-live-search="true">
						<option value="">分省发送状态</option>
						<option value="normal">正常</option>
						<option value="paused">暂停</option>
					</select>
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		<table id="mytab" class="table table-hover"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
			<button id="btn_pau" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量暂停
				</button>
			<button id="btn_str" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量启动
				</button>
		</div>
	</div>
	<div class="popup_de xycenter" style="z-index: 9999">
		<div class="popup_box">
			<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit" value="确定">确定</div>
				<div class="popup_btn btn_cancel" value="取消">取消</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/lgProvincesSend/lgProvincesSend.js"></script>
</html>