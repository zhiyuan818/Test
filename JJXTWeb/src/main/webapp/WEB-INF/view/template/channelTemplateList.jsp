<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-3">
					<select name="channelId" id="search_channelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="channelAppId"
						id="search_channelAppId" placeholder="通道应用ID(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="channelTemplateId"
						id="search_channelTemplateId" placeholder="通道模板ID(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="channelTemplate"
						id="search_channelTemplate" placeholder="通道模板(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="extras"
						id="search_extras" placeholder="参数(模糊)" />
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
				<div class="panel-body form-group" style="margin-bottom: -5px;">
					<div class="col-sm-3 pull-right">
						&nbsp;
						<button class="btn btn-primary" id="import_btn">批量导入</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="btn btn-primary" id="export_btn">批量导出</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
	
				</div>
			</div>
		</div>
		<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div id="toolbar" class="btn-group pull-right"
				style="margin-right: 20px">
				<button id="btn_add" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
				</button>
				<button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
			</div>
		</div>
		<table id="mytab" class="table table-hover" style="word-wrap: break-word"></table>
	</div>

	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
			aria-labelledby="add" aria-hidden="true">
			<div class="modal-dialog" style="width: 720px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close add_backBtn"
							data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="add">添加通道模板</h4>
					</div>
					<div class="modal-body" id="add_body">
						<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">通道</label>
							<div class="col-sm-10">
								<select name="channelId" id="channelId"
									class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道应用ID</label>
							<div class="col-sm-10">
								<input type="text" name="channelAppId" id="channelAppId" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道模板ID</label>
							<div class="col-sm-10">
								<input type="text" name="channelTemplateId" id="channelTemplateId" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道模板</label>
							<div class="col-sm-10">
								<input type="text" name="channelTemplate" id="channelTemplate" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板</label>
							<div class="col-sm-10">
								<input type="text" name="template" id="template" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">参数</label>
							<div class="col-sm-10">
								<input type="text" name="extras" id="extras" class="form-control">
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
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">修改通道模板</h4>
				</div>
				<div class="modal-body" id="edit_body">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道</label>
							<div class="col-sm-10">
								<select name="channelId" id="editChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道应用ID</label>
							<div class="col-sm-10">
								<input type="text" name="channelAppId" id="editChannelAppId" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道模板ID</label>
							<div class="col-sm-10">
								<input type="text" name="channelTemplateId" id="editChannelTemplateId" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道模板</label>
							<div class="col-sm-10">
								<input type="text" name="channelTemplate" id="editChannelTemplate" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板</label>
							<div class="col-sm-10">
								<input type="text" name="template" id="editTemplate" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">参数</label>
							<div class="col-sm-10">
								<input type="text" name="extras" id="editExtras" class="form-control">
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
							<label class="col-sm-2 control-label">选择通道:</label>
							<div class="col-sm-4">
								<select name="channelId" id="importChannelId"
									class="selectpicker show-tick form-control" required
									data-live-search="true" placeholder="通道">
									<option value="">--请选择--</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="col-sm-2 control-label">选择通道应用ID:</label>
							<div class="col-sm-4">
								<select name="channelAppId" id="importChannelAppId"
									class="selectpicker show-tick form-control" required
									data-live-search="true" placeholder="账户">
									<option value="">--请选择--</option>
									<c:forEach var="s" items="${channelAppIds }">
										<option value="${s.channelAppId}">${s.channelAppId}</option>
									</c:forEach>
								</select>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="col-sm-2 control-label">参数是否需要拼接</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="isExtras" value="yes" checked="checked">是
									</label> <label class="btn btn-default"> <input type="radio"
										name="isExtras" value="no">否
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">上传文件:</label>
							<div class="col-sm-6">
								<input type="file" class="form-control" name="uploadFile"
									id="uploadFile" /> <font size="2" color="#ccc">注意：<br>
									1.只支持Excel格式文件(文件后缀为xls或xlsx)<br>
									2.文件内容必须包括"通道应用ID","通道模板内容"，"通道模板ID"，"extras"<br>
									3.可判断参数是否需要拼接,不拼接参数的标题头格式:extras,只为一列;<br>
									4.不拼接参数的标题头格式:extras=key1,extras=key2,可为多列,拼接后格式为:key1=value1&key2=value2
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
									class="btn btn-default btn-block import_backBtn" id="import_backBtn">返回</button>
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
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量导入</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-hover table-bordered" style="word-wrap: break-word">
						<thead id="headTab">
						</thead>
						<tbody id="detailsTab">

						</tbody>
					</table>
					<input type="hidden" id="hiddenChannelId"> 
					<input type="hidden" id="hiddenIsExtras">
					<!-- <input type="hidden" id="hiddenChannelAppId"> -->
					<input type="hidden" id="hiddenFile">
					<div class="modal-footer">
						<button type="button" class="btn btn-default import_backBtn"
							data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary" id="next_saveBtn">确定导入</button>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<div class="modal fade" id="exportBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量导出</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-hover table-bordered">
						<tbody id="detailsTab">
							<tr>
								<td><input type="checkbox" name="param" value="channelId">通道ID</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="channelName">通道名称</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="channelAppId">通道应用ID</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="channelTemplateId">通道模板ID</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="channelTemplate">通道模板内容</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="template">模板内容</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="extras">参数</td>
							</tr>
						</tbody>
					</table>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="submit" class="btn btn-primary" id="export_saveBtn">确定导出</button>
					</div>
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
	src="${pageContext.request.contextPath}/js/template/channelTemplateList.js"></script>
</html>