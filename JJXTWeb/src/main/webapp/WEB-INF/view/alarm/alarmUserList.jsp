<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
</head>
<jsp:include page="/head.html"></jsp:include>	
<body>
	<div class="tableBody">
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加用户
			</button>
		</div>
	</div>

	<div class="modal fade" id="addBody" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">添加报警用户</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label">用户简称：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="userName" id="userName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">真实姓名：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="describe" id="describe" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">手机号码：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="phoneNumber" id="phone" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">邮箱：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="email" id="email" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">微信：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="wechat" id="wechat" />
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

	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">修改报警用户</h4>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">id</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" readonly="readonly" name="userId" id="editId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">用户简称：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" readonly="readonly" name="userName" id="editName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">真实姓名：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="describe" id="editDescribe" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">手机号码：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="phoneNumber" id="editPhone" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">邮箱：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="email" id="editEmail" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">微信：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="wechat" id="editWechat" />
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
	<div class="modal fade" id="addTypeBody" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close addType_backBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">添加报警</h4>
				</div>
				<div class="ibox-content">
					<form id="addTypeForm" method="post" class="form-horizontal">
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">userId</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="userId" id="addUserId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">报警类型：</label>
						<div class="col-sm-6">
							<select name="type" id="addType" class="selectpicker show-tick form-control" placeholder="请选择" data-width="98%" data-live-search="true">
							<option value="">请选择</option>
							<c:forEach var="types" items="${types }">
							<option value="${types.typeId }">${types.type }</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">短信报警：</label>
						<div class="col-sm-2">
							<select name="isPhone" id="addPhone" class="selectpicker show-tick form-control" data-width="98%">
							<option value="yes">是</option>
							<option value="no">否</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">邮件报警：</label>
						<div class="col-sm-2">
							<select name="isEmail" id="addEmail" class="selectpicker show-tick form-control" data-width="98%">
							<option value="yes">是</option>
							<option value="no">否</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">微信报警：</label>
						<div class="col-sm-2">
							<select name="isWechat" id="addWechat" class="selectpicker show-tick form-control" data-width="98%">
							<option value="yes">是</option>
							<option value="no">否</option>
							</select>
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-2">
							<button type="button" class="btn btn-primary btn-block"
								id="addType_saveBtn">保存</button>
						</div>
						<div class="col-sm-2 col-sm-offset-1">
							<button type="button" class="btn btn-default btn-block edit_backBtn"
								id="addType_backBtn">返回</button>
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	 -->
	 
	<div class="modal fade" id="linkType" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog" style="width:700px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                      		报警类型列表
                    </h4>
                </div>
                <div class="modal-body" id="menuBody" style="height:300px;overflow:auto;">
                    <table id="linkTypeList" class="table table-bordered table-hover">
                    </table> 
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
	src="${pageContext.request.contextPath}/js/alarm/alarmUserList.js"></script>
</html>