<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分省优享</title>
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
					<input type="text" class="form-control" name="id" id="searchId" placeholder="ID(精确)" />
				</div>
				<div class="col-sm-4">
					<select name="channelId" id="searchChannelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="appId" id="searchAppId"
						class="selectpicker show-tick form-control" placeholder="全部帐户"
						data-width="98%" data-live-search="true">
						<option value="">全部帐户</option>
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
		</div>
		<table id="mytab" class="table table-hover"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
		</div>
	</div>
	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">新增分省优享</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">通道</label>
							<div class="col-sm-10">
								<select name="channelId" id="channelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">帐户</label>
							<div class="col-sm-10">
								<select name="appId" id="appId"
									class="selectpicker show-tick form-control" placeholder="全部帐户"
									data-width="98%" data-live-search="true">
									<option value="">全部帐户</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">短信类型</label>
							<div class="col-sm-10">
								<select name="contentType" id="contentType" class="form-control">
									<option value ="ALL">ALL</option>
									<option value ="BULK">BULK</option>
									<option value ="NOTIF">NOTIF</option>
									<option value ="CODE">CODE</option>
								</select>
							</div>
						 </div>
						 <div class="form-group">
							<label class="col-sm-2 control-label">省份</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="province"
									id="province">
							</div>
						 </div>
						 <div class="form-group">
							<label class="col-sm-2 control-label">增加条数</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="number"
									id="number">
							</div>
						 </div>
						 <div class="form-group">
							<label class="col-sm-2 control-label">百分比</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="percent"
									id="percent">
							</div>
						 </div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button"
									class="btn btn-default btn-block add_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">更新分省优享</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="editForm" method="post" class="form-horizontal">
						<input type="hidden" name="id" id="editId">
						<div class="form-group">
							<label class="col-sm-2 control-label">通道</label>
							<div class="col-sm-10">
								<select name="channelId" id="editChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">帐户</label>
							<div class="col-sm-10">
								<select name="appId" id="editAppId"
									class="selectpicker show-tick form-control" placeholder="全部帐户"
									data-width="98%" data-live-search="true">
									<option value="">全部帐户</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">短信类型</label>
							<div class="col-sm-10">
								<select name="contentType" id="editContentType" class="form-control">
									<option value ="ALL">ALL</option>
									<option value ="BULK">BULK</option>
									<option value ="NOTIF">NOTIF</option>
									<option value ="CODE">CODE</option>
								</select>
							</div>
						 </div>
						 <div class="form-group">
							<label class="col-sm-2 control-label">省份</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="province"
									id="editProvince">
							</div>
						 </div>
						 <div class="form-group">
							<label class="col-sm-2 control-label">增加条数</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="number"
									id="editNumber">
							</div>
						 </div>
						 <div class="form-group">
							<label class="col-sm-2 control-label">百分比</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="percent"
									id="editPercent">
							</div>
						 </div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="edit_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button"
									class="btn btn-default btn-block edit_backBtn">返回</button>
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
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/chanChangeNum/chanChangeNumList.js"></script>
</html>
</html>