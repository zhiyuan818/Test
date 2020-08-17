<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="multipart/form-data; charset=utf-8" />
<title>Insert title here</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: -20px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="sign" id="search_sign"
						placeholder="签名(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="ext"
						id="search_ext" placeholder="扩展号" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="extLength"
						id="search_extLength" placeholder="扩展号长度" />
				</div>
				<div class="col-sm-4">
					<select name="appId" id="search_appId"
						class="selectpicker show-tick form-control" placeholder="全部账号"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部账号</option>
						<c:forEach var="s" items="${apps }">
							<option value="${s.id}">${s.id}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="startTime"
						id="startTime" placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="endTime"
						id="endTime" placeholder="结束时间" />
				</div>
				<div class="col-sm-3 pull-right">
					&nbsp;&nbsp;
					<button class="btn btn-primary" id="search_btn">搜索</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
			<div class="panel-body form-group" style="margin-bottom: -5px;">
				<div class="col-sm-3 pull-right">
					&nbsp;
					<button class="btn btn-primary" id="add_btn">添加</button>
					&nbsp;
					<button class="btn btn-primary" id="import_btn">批量导入</button>
					&nbsp;
					<button class="btn btn-primary" id="export_btn">批量导出</button>
				</div>

			</div>
		</div>
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
	</div>
	
	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">新增签名</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<select name="appId" id="addAppId"
									class="selectpicker show-tick form-control" placeholder="全部账号"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账号</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">签名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="sign"
									id="addSign">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展长度</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="extLength"
									id="addExtLength">
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
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<select name="appId" id="importAppId"
									class="selectpicker show-tick form-control" placeholder="全部账号"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账号</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展长度</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="extLength"
									id="importExtLength">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">上传文件:</label>
							<div class="col-sm-6">
								<input type="file" class="form-control" name="uploadFile"
									id="uploadFile" /> <font size="2" color="#ccc">注意：1.只支持txt格式<br>
									2.只需要签名，每个签名一行<br>
									3.文件编码为UTF-8<br>
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
									class="btn btn-default btn-block import_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="exportBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close import_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">批量导出</h4>
				</div>
				<div class="modal-body" id="export_body">
					<form id="exportForm" method="post" class="form-horizontal"
						enctype="multipart/form-data">
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<select name="appId" id="exportAppId"
									class="selectpicker show-tick form-control" placeholder="全部账号"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账号</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展长度</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="extLength"
									id="exportExtLength">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="export_saveBtn">导出</button>
							</div>
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button"
									class="btn btn-default btn-block export_backBtn">返回</button>
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
	src="${pageContext.request.contextPath}/js/signMatchExt/signMatchExtList.js"></script>
</html>