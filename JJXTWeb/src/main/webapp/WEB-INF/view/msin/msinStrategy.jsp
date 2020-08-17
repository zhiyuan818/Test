<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<select name="appId" id="sel_appId"
						class="selectpicker show-tick form-control" placeholder="账户"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部账户</option>
						<c:forEach var="s" items="${apps }">
							<option value="${s.appId}">${s.appId}:${s.appName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="level" id="sel_level"
						class="selectpicker show-tick form-control" placeholder="级别"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部级别</option>
						<c:forEach var="s" items="${levels }">
							<option value="${s.level}">${s.level}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="phoneNumber" id="sel_phone"
						placeholder="手机号码" />
				</div>
				<div class="col-sm-2 ">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
				<div class="col-sm-2 pull-right">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn btn-primary" id="import_btn">批量导入</button>
				</div>
				<div class="col-sm-2 pull-right">
				<div id="toolbar" class="btn-group pull-right"
				style="margin-right: 20px">
					<button id="btn_del" type="button" class="btn btn-default"
					style="display: none;">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量删除
					</button>
					<button id="btn_add" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
					</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				</div>
			</div>
		</div>
		
		<table id="mytab" class="table table-hover" style="word-wrap: break-word"></table>
	</div>

	<div class="modal fade" id="importBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="input">批量导入</h4>
				</div>
				<div class="modal-body" id="import_body">
					<form id="importForm" method="post" class="form-horizontal"
						enctype="multipart/form-data">

						<div class="form-group">
							<label class="col-sm-2 control-label">选择账户:</label>
							<div class="col-sm-4">
								<select name="appId" id="importappId"
									class="selectpicker show-tick form-control" required
									data-live-search="true" placeholder="账户">
									<option value="">--请选择--</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.appId}">${s.appId}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">选择级别:</label>
							<div class="col-sm-4">
								<select name="level" id="importlevel"
									class="selectpicker show-tick form-control" required
									data-live-search="true" placeholder="账户">
									<option value="">--请选择--</option>
									<c:forEach var="s" items="${levels }">
										<option value="${s.level}">${s.level}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">上传文件:</label>
							<div class="col-sm-6">
								<input type="file" class="form-control" name="uploadFile"
									id="uploadFile" /> <font size="2" color="#ccc">注意：<br>
									1.只支持txt格式文件(文件后缀为.txt)<br>
									2.手机号码用换行分隔（每行一个手机号）
								</font>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="import_saveBtn">确定导入</button>
							</div>
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button"
									class="btn btn-default btn-block " id="import_backBtn">返回</button>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="addBody" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">添加号码策略</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal" enctype="multipart/form-data">
					<div class="form-group">
							<label class="col-sm-2 control-label">账户：</label>
							<div class="col-sm-4">
								<select name="appId" id="appId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账户</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.appId}">${s.appId}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">级别：</label>
							<div class="col-sm-4">
								<select name="level" id="level"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部级别</option>
									<c:forEach var="s" items="${levels }">
										<option value="${s.level}">${s.level}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">手机号码：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="phoneNumber" id="phoneNumber" />
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
	src="${pageContext.request.contextPath}/js/msin/msinStrategyList.js"></script>
</html>