<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色关系管理</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<select name="managerId" id="searchManagerId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">用户</option>
               			<c:forEach var="m" items="${managerMap }">
							<option value="${m.managerId}">${m.managerId}:${m.chineseName }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2">
					<select name="roleId" id="searchRoleId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">角色</option>
               			<c:forEach var="r" items="${roleMap }">
							<option value="${r.roleId}">${r.roleId}:${r.roleName }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>

		</div>
		<table id="myTab" class="table table-hover"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
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
					<h4 class="modal-title" id="add">新增角色关系</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">	
						<div class="form-group">
							<label class="col-sm-2 control-label">用户</label>
							<div class="col-sm-10">
								<select name="managerId" id="managerId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部用户</option>
									<c:forEach var="m" items="${managerMap }">
										<option value="${m.managerId}">${m.managerId}:${m.chineseName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">角色</label>
							<div class="col-sm-10">
								<select name="roleId" id="roleId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部角色</option>
									<c:forEach var="r" items="${roleMap }">
										<option value="${r.roleId}">${r.roleId}:${r.roleName }</option>	
									</c:forEach>
								</select>
							</div>
						</div>
					
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button"
									class="btn btn-default btn-block add_backBtn" id="add_backBtn">返回</button>
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
					<h4 class="modal-title" id="edit">修改角色关系</h4>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">id</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" readonly="readonly" name="id" id="editId" />
						</div>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" disabled="disabled" class="form-control"
									name="chineseName" id="editChineseName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">角色</label>
							<div class="col-sm-10">
							
							<select name="roleId" id="editRoleId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部角色</option>
									<c:forEach var="r" items="${roleMap }">
										<option value="${r.roleId}">${r.roleId}:${r.roleName }</option>	
									</c:forEach>
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
							<button type="button" class="btn btn-default btn-block edit_backBtn"
								id="edit_backBtn">返回</button>
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	
	
	<!--  
	<div class="changeBody"
		style="width: 100%; height: 500px; display: none; position: absolute; top: 10px;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
				<button type="button" class="close "edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h5>更新角色关系</h5>
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
									name="chineseName" id="editChineseName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">角色</label>
							<div class="col-sm-10">
							
							<select name="roleId" id="editRoleId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部角色</option>
									<c:forEach var="r" items="${roleMap }">
										<option value="${r.roleId}">${r.roleId}:${r.roleName }</option>	
									</c:forEach>
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
								<button type="button"
									class="btn btn-default btn-block edit_backBtn" id="edit_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	-->
	<<div class="popup_de xycenter" style="z-index: 9999">
		<div class="popup_box">
			<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit" id="btn_submit" value="确定">确定</div>
				<div class="popup_btn btn_cancel" id="btn_cancel" value="取消">取消</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" id="buttons" value="${buttons }">
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/role/roleRelationList.js"></script>
</html>