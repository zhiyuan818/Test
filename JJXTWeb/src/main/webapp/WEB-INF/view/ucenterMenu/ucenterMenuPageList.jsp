<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<label class="col-sm-1 control-label"
					style="text-align: right; margin-top: 5px">菜单名称：</label>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="searchTitle"
						id="searchTitle" placeholder="模糊" />
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
					<h5>新增菜单</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">菜单名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="title" id="title" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">菜单路径</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="menuLink"
									id="menuLink" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">图标名称</label>
							<div class="col-sm-10 role add">
								<select name="iconClass" id="iconClass"
									class="show-tick form-control">
									<option value="icon-user">icon-user</option>
									<option value="icon-phone">icon-phone</option>
									<option value="icon-list">icon-list</option>
									<option value="icon-search">icon-search</option>
									<option value="icon-star">icon-star</option>
									<option value="icon-tag">icon-tag</option>
									<option value="icon-bookmark">icon-bookmark</option>
									<option value="icon-heart">icon-heart</option>
									<option value="icon-thumbs-down-alt">icon-thumbs-down-alt</option>
									<option value="icon-cog">icon-cog</option>
									<option value="icon-wrench">icon-wrench</option>
									<option value="icon-thumbs-up-alt">icon-thumbs-up-alt</option>
									<option value="icon-double-angle-right">icon-double-angle-right</option>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">父菜单名称</label>
							<div class="col-sm-10 role add">
								<select id="parentId" name="parentId" class="show-tick form-control">
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">按钮</label>
							<div class="col-sm-10 role add" id="div_buttons">
								<input name="buttons" type="checkbox" value="新增" />新增 <input
									name="buttons" type="checkbox" value="删除" />删除 <input
									name="buttons" type="checkbox" value="修改" />修改 <span
									onclick="changeToInput(this)">自定义</span>
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
					<h5>更新菜单</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">菜单名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="title"
									id="editTitle" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">菜单路径</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="menuLink"
									id="editMenuLink" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">图标名称</label>
							<div class="col-sm-10">
								<select name="iconClass" id="editIconClass"
									class="show-tick form-control">
									<option value="icon-user">icon-user</option>
									<option value="icon-phone">icon-phone</option>
									<option value="icon-list">icon-list</option>
									<option value="icon-search">icon-search</option>
									<option value="icon-star">icon-star</option>
									<option value="icon-tag">icon-tag</option>
									<option value="icon-bookmark">icon-bookmark</option>
									<option value="icon-heart">icon-heart</option>
									<option value="icon-thumbs-down-alt">icon-thumbs-down-alt</option>
									<option value="icon-cog">icon-cog</option>
									<option value="icon-wrench">icon-wrench</option>
									<option value="icon-thumbs-up-alt">icon-thumbs-up-alt</option>
									<option value="icon-double-angle-right">icon-double-angle-right</option>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">父菜单名称</label>
							<div class="col-sm-10">
								<select id="editParentId" name="parentId"
									class="show-tick form-control">
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">按钮</label>
							<div class="col-sm-10 role add" id="buttonsDiv">
								<input name="buttons" type="checkbox" value="新增" />新增 <input
									name="buttons" type="checkbox" value="删除" />删除 <input
									name="buttons" type="checkbox" value="修改" />修改
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
	src="${pageContext.request.contextPath}/js/ucenterMenu/ucenterMenuPageList.js"></script>
</html>