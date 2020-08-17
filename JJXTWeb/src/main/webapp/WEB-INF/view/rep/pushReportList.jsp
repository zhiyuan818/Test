<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推送状态报告监控</title>
<style type="text/css">
.td {
	width: 100px;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div class="col-sm-2">
				<select name="appId" id="searchAppId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
                 			<option value="">全部账户</option>
                 			<c:forEach var="s" items="${apps }">
						<option value="${s.id}">${s.id}:${s.appName }</option>
					</c:forEach>
                 		</select>
			</div>
			<div class="col-sm-2 pull-right">
				<button class="btn btn-primary" id="search_btn">搜索</button>
				<button class="btn btn-default" id="search_back">重置</button>
			</div>
		</div>
		<table id="mytab" class="table table-hover"></table>
	</div>

	<div class="popup_de xycenter">
		<div class="popup_box">
			<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit" id="btn_submit" value="确定">确定</div>
				<div class="popup_btn btn_cancel" value="取消">取消</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/rep/pushReportList.js"></script>
</html>