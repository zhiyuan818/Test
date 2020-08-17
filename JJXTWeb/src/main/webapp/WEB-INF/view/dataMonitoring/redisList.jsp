<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控队列</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<table id="mytab" class="table table-hover"></table>
	</div>
	<div class="modal fade" id="resultTab" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 100px; width: 800px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn_close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">查找</h4>
				</div>
				<div class="modal-body" id="versionBody">
					<form id="Form">
						<table class="table table-bordered table-hover">
							<tr>
								<td class="td">输入：</td>
								<td><textarea rows="2" cols="90" id="key" placeholder="提示"></textarea>
								</td>
							</tr>
							<tr>
								<td class="td">输出：</td>
								<td><textarea rows="5" cols="90" id="value" readonly="readonly"></textarea>
								</td>
							</tr>
						</table>
						<input type="hidden" id="searchValue" />
						<input type="hidden" id="searchName" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn_priSub" class="btn btn-primary">查找</button>
					<button type="button" id="btn_close" class="btn btn-default btn_close"
						data-dismiss="modal">关闭</button>
				</div>
			</div>
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
	<input type="hidden" id="buttons" value="${buttons }">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dataMonitoring/redisList.js"></script>
</html>