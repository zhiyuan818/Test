<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
					<input type="text" class="form-control" name="content"
						id="search_content" placeholder="内容(模糊)" />
				</div>
				<div class="col-sm-2">
					<select name="appId" id="search_appId"
						class="selectpicker show-tick form-control" placeholder="全部账户"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部账户</option>
						<c:forEach var="s" items="${apps }">
							<option value="${s.id}">${s.id}:${s.appName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="level" id="search_level" class="selectpicker show-tick form-control" placeholder="全部级别"
						data-width="98%" data-first-option="false" required data-live-search="true">
						<option value="">全部级别</option>
						<c:forEach var="l" items="${levels }">
							<option value="${l.level }">${l.levelName }</option>
						</c:forEach>
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
			<button id="btn_edit" type="button" class="btn btn-default"
				style="display: none; border-radius: 0">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-default"
				style="display: none;">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
		</div>
	</div>
	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>新增忽略优享内容</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">忽略优享内容</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="content"
									id="content" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-2">
								<select name="appId" id="appId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账户</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">忽略级别</label>
							<div class="col-sm-2">
								<select name="level" id="level" class="selectpicker show-tick form-control" placeholder="全部级别"
									data-width="98%" data-first-option="false" required data-live-search="true">
									<option value="">全部级别</option>
									<option value="6">6级黑</option>
									<option value="all">全局</option>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block"
									id="add_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="changeBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>修改忽略优享内容</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<input type="hidden" name="id" id="editId">
						<div class="form-group">
							<label class="col-sm-2 control-label">忽略优享内容</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="content"
									id="editContent" />
								<input type="hidden" id="oldEditContent">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-2">
								<select name="appId" id="editAppId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账户</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
								<input type="hidden" id="oldEditAppId">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">忽略级别</label>
							<div class="col-sm-2">
								<select name="level" id="editLevel" class="selectpicker show-tick form-control" placeholder="全部级别"
									data-width="98%" data-first-option="false" required data-live-search="true">
									<option value="6">6级黑</option>
									<option value="all">全局</option>
								</select>
								<input type="hidden" id="oldEditLevel">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="edit_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block"
									id="edit_backBtn">返回</button>
							</div>
						</div>
					</form>
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
	src="${pageContext.request.contextPath}/js/accountIgnoreBlackShunt/accountIgnoreBlackShunt.js"></script>
</html>