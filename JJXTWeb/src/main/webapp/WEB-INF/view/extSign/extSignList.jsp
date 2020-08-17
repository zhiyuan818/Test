<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签名扩展管理</title>
<style type="text/css">
.td {
	width: 100px;
}
.table {
	table-layout: fixed;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#J" data-toggle="tab">签名扩展跳转</a></li>
					<li><a id="typePage" href="#A" data-toggle="tab">扩展+签名</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="J">
						<div class="col-sm-2" style="margin: 10px 0px;">
							<select name="channelId" id="channelId" class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
								<option value="">全部通道</option>
								<c:forEach var="c" items="${channels }">
									<option value="${c.channelId }">${c.channelId }:${c.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2" style="margin: 10px 0px;">
							<input type="text" class="form-control" name="sign1" id="sign1"
								placeholder="签名(模糊)" />
						</div>
						<div class="col-sm-2" style="margin: 10px 0px;">
							<input type="text" class="form-control" name="ext" id="ext"
								placeholder="扩展号(模糊)" />
						</div>
						<div class="col-sm-2" style="margin: 10px 0px;">
							<button class="btn btn-primary" id="search_btn1">搜索</button>
							<button class="btn btn-default" id="search_back1">重置</button>
						</div>
						<div class="col-sm-2" style="margin: 10px 0px;">
							<button id="btn_add1" type="button" class="btn btn-default">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
							</button>
						</div>
						<div class="col-sm-2" style="margin: 10px -100px;">
							<button id="btn_del1" type="button" class="btn btn-default" style="display: none">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
							</button>
						</div>
						<table id="tempTab" class="table table-hover"></table>
					</div>
					
					<div class="tab-pane" id="A">
						<div class="col-sm-2" style="margin: 10px 0px;">
							<select name="appId" id="appId" class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
								<option value="">全部账户</option>
								<c:forEach var="a" items="${apps }">
									<option value="${a.id }">${a.id }:${a.appName }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2" style="margin: 10px 0px;">
							<input type="text" class="form-control" name="sign2" id="sign2"
								placeholder="签名(模糊)" />
						</div>
						<div class="col-sm-2" style="margin: 10px 0px;">
							<button class="btn btn-primary" id="search_btn2">搜索</button>
							<button class="btn btn-default" id="search_back2">重置</button>
						</div>
						<div class="col-sm-4" style="margin: 10px 0px;">
							<button id="btn_add2" type="button" class="btn btn-default">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
							</button>
							<button id="btn_import2" type="button" class="btn btn-default">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量导入
							</button>
						</div>
						<div class="col-sm-2" style="margin: 10px -200px;">
							<button id="btn_del2" type="button" class="btn btn-default" style="display: none">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
							</button>
						</div>
						<table id="mytab" class="table table-hover"></table>
					</div>
						
				</div>
			</div>
		</div>
	</div>

	<div class="addTempBody" style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-10">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>添加签名扩展跳转</h5>
				</div>
				<div class="ibox-content">
					<form id="addTemp" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">签名：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="sign" id="addTempSign" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展号：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="ext" id="addTempExt" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">跳转移动通道：</label>
							<div class="col-sm-4">
								<select name="cmccChannelId" id="cmccChannelId"
									class="selectpicker show-tick form-control" placeholder="请选择账户"
									data-width="98%" data-first-option="false"
									data-live-search="true">
									<option value="">请选择通道</option>
									<c:forEach var="c" items="${cmccChannels}">
										<option value="${c.channelId}">${c.channelId }:${c.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">跳转联通通道：</label>
							<div class="col-sm-4">
								<select name="unicomChannelId" id="unicomChannelId"
									class="selectpicker show-tick form-control" placeholder="请选择账户"
									data-width="98%" data-first-option="false"
									data-live-search="true">
									<option value="">请选择通道</option>
									<c:forEach var="c" items="${unicomChannels}">
										<option value="${c.channelId}">${c.channelId }:${c.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">跳转电信通道：</label>
							<div class="col-sm-4">
								<select name="telecomChannelId" id="telecomChannelId"
									class="selectpicker show-tick form-control" placeholder="请选择账户"
									data-width="98%" data-first-option="false"
									data-live-search="true">
									<option value="">请选择通道</option>
									<c:forEach var="c" items="${telecomChannels}">
										<option value="${c.channelId}">${c.channelId }:${c.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="addTemp_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block"
									id="addTemp_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="changeTempBody"
		style="width: 100%; height: 500px; display: none; position: absolute; top: 10px;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>修改签名扩展跳转</h5>
				</div>
				<div class="ibox-content">
					<form id="editTempForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="id" id="editTempId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">签名：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="sign"
									id="editTempSign" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展号：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="ext"
									id="editTempExt" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">跳转移动通道：</label>
							<div class="col-sm-4">
								<select name="cmccChannelId" id="editCmccChannelId"
									class="selectpicker show-tick form-control" data-width="98%"
									data-first-option="false" data-live-search="true">
									<c:forEach var="c" items="${cmccChannels}">
										<option value="${c.channelId}">${c.channelId}:${c.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">跳转联通通道：</label>
							<div class="col-sm-4">
								<select name="unicomChannelId" id="editUnicomChannelId"
									class="selectpicker show-tick form-control" data-width="98%"
									data-first-option="false" data-live-search="true">
									<c:forEach var="c" items="${unicomChannels}">
										<option value="${c.channelId}">${c.channelId}:${c.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">跳转电信通道：</label>
							<div class="col-sm-4">
								<select name="telecomChannelId" id="editTelecomChannelId"
									class="selectpicker show-tick form-control" data-width="98%"
									data-first-option="false" data-live-search="true">
									<c:forEach var="c" items="${telecomChannels}">
										<option value="${c.channelId}">${c.channelId}:${c.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="editTemp_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block"
									id="editTemp_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>添加扩展+签名</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账户名称：</label>
							<div class="col-sm-4">
								<select name="appId" id="addName"
									class="selectpicker show-tick form-control" placeholder="请选择账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">请选择账户</option>
									<c:forEach var="s" items="${apps}">
										<option value="${s.id}">${s.id}:${s.appName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">签名：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="sign" id="addsign" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展号：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="extSrc"
									id="addextSrc" />
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
		style="width: 100%; height: 500px; display: none; position: absolute; top: 10px;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>修改扩展+签名</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户名：</label>
							<div class="col-sm-4">
								<select name="appId" id="editName"
									class="selectpicker show-tick form-control" data-width="98%"
									data-first-option="false" required data-live-search="true">
									<option value="">请选择账户</option>
									<c:forEach var="s" items="${apps}">
										<option value="${s.id}">${s.id}:${s.appName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">签名：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="sign"
									id="editsign" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展号：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="extSrc"
									id="editextSrc" />
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
	<div class="modal fade" id="importBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close import_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">批量导入</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="importForm" method="post" class="form-horizontal"
						enctype="multipart/form-data">
						<div class="form-group">
							<label class="col-sm-2 control-label">导入模式</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="update" value="yes" checked="checked">添加更新
									</label> <label class="btn btn-default"> <input type="radio"
										name="update" value="no">全量同步
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">上传文件:</label>
							<div class="col-sm-6">
								<input type="file" class="form-control" name="uploadFile"
									id="uploadFile" /> <font size="2" color="#ccc">注意：1.只支持Excel格式文件(文件后缀为xls或xlsx)<br>
									2.文件内容必须包括"签名"和"扩展号"<br>
									3.文件内容的签名不能带【】<br>
									4.文件内容第一行与最后一行不会导入，请放标题头，标题尾
								</font>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="import_saveBtn">下一步</button>
							</div>
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button"
									class="btn btn-default btn-block import_backBtn"
									>返回</button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div>

	</div>
	<div class="modal fade" id="nextBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close import_backBtn" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量导入</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-hover table-bordered">
						<thead id="headTab">
						</thead>
						<tbody id="detailsTab">

						</tbody>
					</table>
					<input type="hidden" id="hiddenUpdate"> <input
						type="hidden" id="hiddenFile">
					<div class="modal-footer">
						<button type="button" class="btn btn-default import_backBtn" data-dismiss="modal">关闭
						</button>
						<button type="submit" class="btn btn-primary" id="next_saveBtn">确定导入</button>
					</div>
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
	<input type="hidden" id="buttons" value="${buttons }">

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/extSign/extSignList.js"></script>
</html>