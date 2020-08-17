<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<div class="col-sm-2">
					<input type="text" class="form-control" name="keyWord" id="keyWord"
						placeholder="审核词(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="startTime"
						id="startTime" placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="endTime"
						id="endTime" placeholder="结束时间" />
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
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
		<table id="checktab" class="table table-hover"
			style="word-wrap: break-word"></table>
	</div>

	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>添加审核词</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">审核词</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="keyWord"
									id="keyWord" />
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
					<h5>修改审核词</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">审核词</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="keyWord"
									id="editKeyword" />
								<input type="hidden" id="oldEditKeyword" />
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
	<input type="hidden" id="buttons" value="${buttons }">

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/audit/checkMustList.js"></script>
</html>