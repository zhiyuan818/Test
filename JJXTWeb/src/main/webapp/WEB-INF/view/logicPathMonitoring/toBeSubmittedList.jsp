<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待提交逻辑模块数据</title>
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
	<div class="modal fade" id="details" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">营销详情</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th>优先级</th>
								<th>积压条数</th>
								<th>账户</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="detailsTab">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="channel" tabindex="-2" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 50px; width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">切换通道</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<form id="logForm">
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
	<div class="modal fade" id="priorityTab" tabindex="-3" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 50px; width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">紧急优先</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<form id="logForm">
						<input type="hidden" id="urgentChannelId"> <input
							type="hidden" id="urgentAppId">
						<table class="table table-bordered table-hover">
							<tr>
								<td class="td">数量</td>
								<td><input type="number" class="form-control"
									id="urgentNumber" min="1" data-step="1">
									<input type="hidden" class="form-control"
									id="oldUrgentNumber"></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn_priSub" class="btn btn-primary"
						data-dismiss="modal">切换</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
	src="${pageContext.request.contextPath}/js/logicPathMonitoring/toBeSubmittedList.js"></script>
</html>