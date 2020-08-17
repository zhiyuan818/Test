<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<label class="col-sm-1 control-label"
					style="text-align: right; margin-top: 5px">用户名：</label>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="searchManagerName"
						id="searchManagerName" placeholder="模糊" />
				</div>
				<label class="col-sm-1 control-label" for="state"
					style="text-align: right; margin-top: 5px">权限类别：</label>
				<div class="col-sm-2">
					<select name="searchTitle" id="searchTitle"
						class="selectpicker show-tick form-control" title="权限类别"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">---请选择---</option>
						<option value="超级管理员">超级管理员</option>
						<option value="管理员">管理员</option>
						<option value="销售">销售</option>
						<option value="客服">客服</option>
						<option value="运营">运营</option>
						<option value="财务">财务</option>
						<option value="售后">售后</option>
						<option value="BD">BD</option>
						<option value="总办">总办</option>
						<option value="商务">商务</option>
					</select>
				</div>
				
				<label class="col-sm-1 control-label" for="state"
					style="text-align: right; margin-top: 5px">账户权限级别：</label>
				<div class="col-sm-2">
					<select name="searchIsAllCustomer" id="searchIsAllCustomer"
						class="selectpicker show-tick form-control" title="账户权限级别"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">---请选择---</option>
						<option value="0">角色及其他</option>
						<option value="1">角色</option>
						<option value="2">全部</option>
					</select>
				</div>
				
				<label class="col-sm-1 control-label" for="state"
					style="text-align: right; margin-top: 5px">通道权限级别：</label>
				<div class="col-sm-2">
					<select name="searchIsAllChannel" id="searchIsAllChannel"
						class="selectpicker show-tick form-control" title="通道权限级别"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">---请选择---</option>
						<option value="0">角色及其他</option>
						<option value="1">角色</option>
						<option value="2">全部</option>
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
					<h5>新增管理员</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="managerName"
									id="managerName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="showPassword"
									id="showPassword" /> <input type="hidden"
									name="managerPassword" id="managerPassword" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control"
									name="showRepeatPassword" id="showRepeatPassword" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">中文姓名</label>
							<div class="col-sm-10 role add">
								<input type="text" class="form-control" name="chineseName"
									id="chineseName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">权限类别</label>
							<div class="col-sm-10">
								<select id="title" name="title"
									class="show-tick form-control">
									<option value="">---请选择---</option>
									<option value="超级管理员">超级管理员</option>
									<option value="管理员">管理员</option>
									<option value="销售">销售</option>
									<option value="客服">客服</option>
									<option value="运营">运营</option>
									<option value="财务">财务</option>
									<option value="售后">售后</option>
									<option value="BD">BD</option>
									<option value="总办">总办</option>
									<option value="商务">商务</option>
								</select>
							</div>
						</div>
						
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户权限级别</label>
							<div class="col-sm-10">
								<select id="isAllCustomer" name="isAllCustomer"
									class="show-tick form-control">
									<option value="">---请选择---</option>
									<option value="0">角色及其他</option>
									<option value="1">角色</option>
									<option value="2">全部</option>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道权限级别</label>
							<div class="col-sm-10">
								<select id="isAllChannel" name="isAllChannel"
									class="show-tick form-control">
									<option value="">---请选择---</option>
									<option value="0">角色及其他</option>
									<option value="1">角色</option>
									<option value="2">全部</option>
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
		style="width: 100%; height: 500px; display: none; position: absolute; top: 10px;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>更新管理员</h5>
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
							<label class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" disabled="disabled" class="form-control"
									name="managerName" id="editManagerName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="showPassword"
									id="editShowPassword" /> <input type="hidden"
									class="form-control" name="managerPassword"
									id="editManagerPassword" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control"
									name="showRepeatPassword" id="editShowRepeatPassword" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">中文姓名</label>
							<div class="col-sm-10 role add">
								<input type="text" class="form-control" name="chineseName"
									id="editChineseName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">头衔</label>
							<div class="col-sm-10">
								<select id="editTitle" name="title"
									class="show-tick form-control">
									<option value="">---请选择---</option>
									<option value="超级管理员">超级管理员</option>
									<option value="管理员">管理员</option>
									<option value="销售">销售</option>
									<option value="客服">客服</option>
									<option value="运营">运营</option>
									<option value="财务">财务</option>
									<option value="售后">售后</option>
									<option value="BD">BD</option>
									<option value="总办">总办</option>
									<option value="商务">商务</option>
								</select>
							</div>
						</div>
						
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户权限级别</label>
							<div class="col-sm-10">
								<select id="editIsAllCustomer" name="isAllCustomer"
									class="show-tick form-control">
									<option value="">---请选择---</option>
									<option value="0">角色及其他</option>
									<option value="1">角色</option>
									<option value="2">全部</option>
								</select>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">通道权限级别</label>
							<div class="col-sm-10">
								<select id="editIsAllChannel" name="isAllChannel"
									class="show-tick form-control">
									<option value="">---请选择---</option>
									<option value="0">角色及其他</option>
									<option value="1">角色</option>
									<option value="2">全部</option>
								</select>
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

	<div class="popup_de xycenter" style="z-index: 9999">
		<div class="popup_box">
			<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit" id="btn_submit" value="确定">确定</div>
				<div class="popup_btn btn_cancel" value="取消">取消</div>
			</div>
		</div>
	</div>
	<!-- ---------------------------------------------------------------------- -->
	<form method="post" action="" class="form-horizontal" role="form"
		id="form_data" onsubmit="return check_form()"
		style="margin: 20px; width: 1100px">
		<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">用户授权</h4>
					</div>
					<div class="modal-body" id="modalBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="submit" class="btn btn-primary">提交</button>
						<span id="tip"> </span>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</form>

	<div class="modal fade" id="menu" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">菜单操作</h4>
				</div>
				<div class="modal-body" id="menuBody"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id="menuButton"
						data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!--  ---------------------------------------------------------------------------------  -->
	<input type="hidden" name="menuId" id="menuId">
	<input type="hidden" name="managerId" id="managerId">
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.md5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ucenterManager/ucenterManagerPageList.js"></script>
</html>