<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待发送网关监控数据</title>
<style type="text/css">
.td {
	width: 100px;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<table id="mytab" class="table table-hover"></table>
	</div>
	<div class="modal fade" id="channel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">切换通道</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<form id="gwForm">
						<table id="switchTable" class="table table-bordered table-hover">
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn_sub" class="btn btn-primary"
						data-dismiss="modal">提交</button>
					<button type="button" id="btn_back" class="btn btn-default"
						data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="popup_de xycenter">
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
	src="${pageContext.request.contextPath}/js/gatewayMonitoring/toBeSubmittedList.js"></script>
</html>