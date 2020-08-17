<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.td {
	width: 100px;
}

.table {
	table-layout: fixed;
}
</style>
</head>
<jsp:include page="/head.html"></jsp:include>	
<body>
	<div class="tableBody">
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
			</button>
		</div>
	</div>

	<div class="modal fade" id="addBody" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">添加报警模块</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label">模块名称：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="modelName" id="modelName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">密码：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="modelPwd" id="modelPwd" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">鉴权IP：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="authIp" id="authIp" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">签名：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="sign" id="sign" />
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-2">
							<button type="button" class="btn btn-primary btn-block"
								id="add_saveBtn">保存</button>
						</div>
						<div class="col-sm-2 col-sm-offset-1">
							<button type="button" class="btn btn-default btn-block add_backBtn"
								id="add_backBtn">返回</button>
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">修改报警模块</h4>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">id</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="modelId" id="editId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">模块名称：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="modelName" id="editModelName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">密码：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="modelPwd" id="editModelPwd" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">鉴权IP：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="authIp" id="editAuthIp" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">签名：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="sign" id="editSign" />
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-2">
							<button type="button" class="btn btn-primary btn-block"
								id="edit_saveBtn">保存</button>
						</div>
						<div class="col-sm-2 col-sm-offset-1">
							<button type="button" class="btn btn-default btn-block edit_backBtn"
								id="edit_backBtn">返回</button>
						</div>
					</div>
				</form>
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
	src="${pageContext.request.contextPath}/js/alarm/alarmModelList.js"></script>
</html>