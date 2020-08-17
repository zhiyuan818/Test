<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息库管理</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="ibox float-e-margins">
			<form id="searchForm" class="form-horizontal " method="post">
				<div class="form-group">
					<label class="col-sm-3 control-label"><strong>账号</strong></label>
					<div class="col-sm-5" >
						<select name="appId" id="search_appId" class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
		                    <option value="">全部账户</option>
		                    <c:forEach var="s" items="${apps }">
		                        <option value="${s.id}">${s.id}:${s.appName }</option>
		                    </c:forEach>
		                </select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label"><strong>批量号码</strong></label>
					<div class="col-sm-5" >
						<textarea class="form-control" id="phoneNumbers" rows="8"
							name="phoneNumbers" placeholder="请输入手机号码"></textarea>
						<p class="help-block">手机号码，可以用换行、逗号、空格分割</p>
						<p style="font-size: 10px; color: #ff5907;">
						规则:<br>
						【1】查询号码不能超过100个,不能重复<br>
						【2】添加时,白名单 与 黑名单,实号库 互斥<br>
						【3】添加时,投诉黑名单,回复黑名单,普通黑名单,关键黑名单,实号库互相兼容<br>
						【4】查询回复黑名单需要选择账号,只能查询对应账号的回复黑<br>
						</p>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2 col-sm-offset-5">
						<button id="btn_searchPhone" type="button"
							class="btn btn-default btn-block">查询</button>
					</div>
				</div>
			</form>
		</div>
	<!-- 查询结果 -->
	<div id="toolbar" class="btn-group pull-right"
		style="margin-right: 20px">
		<button id="btn_del" type="button" class="btn btn-default"
			style="display: none">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
		</button>
		<button id="btn_addBlack1" type="button" class="btn btn-default"
			style="display: none">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量添加投诉黑
		</button>
		<button id="btn_addWhite" type="button" class="btn btn-default"
			style="display: none">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量添加白名单
		</button>
	</div>
	<table id="myTab" class="table table-hover"></table>
	</div>


	<!-- 对查询结果进行增删操作 -->
	<div class="modal" tabindex="1" role="dialog" id="myModal"
		aria-labelledby="myModalLabel">
		<div class="popup_de xycenter">
			<div class="popup_box">
				<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
				<div class="btn_box">
					<div class="popup_btn btn_submit" value="确定">确定</div>
					<div class="popup_btn btn_cancel" value="取消">取消</div>
				</div>
			</div>
		</div>
	</div>

	
	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 600px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">新增号码状态</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
								<label class="col-sm-2 control-label">手机号码</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="addPhone" id="addPhone" />
								</div>
						</div>
						<div class="form-group">
		                        <label class="col-sm-2 control-label">名单级别</label>
							    <div class="col-sm-10">
								<select id="addLevel" name="addLevel" class="col-sm-4">
			                    			<option value="">...请选择名单级别...</option>
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
									class="btn btn-default btn-block add_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
				

	<!-- 对查询结果进行添加回复黑名单操作 -->
	<div class="modal fade" id="addBlack2Body" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 600px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close addBlack2_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">新增回复黑号码状态</h4>
				</div>
				<div class="modal-body">
					<form id="addBlack2Form" method="post" class="form-horizontal">
						<div class="form-group">
								<label class="col-sm-2 control-label">手机号码</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="addBlack2Phone" id="addBlack2Phone" />
								</div>
						</div>
		                    <div class="form-group">
								<label class="col-sm-2 control-label">所属账号</label>
								<div class="col-sm-4">
									<select name="addBlack2AppId" id="addBlack2AppId" class="selectpicker show-tick form-control" placeholder="请输入账户" data-width="98%" data-first-option="false" required data-live-search="true" >
								<option value="">请选择账户</option>
								<c:forEach var="s" items="${apps }">
									<option value="${s.id}">${s.id}:${s.appName }</option>
								</c:forEach>
							</select>
								</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="addBlack2_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button"
									class="btn btn-default btn-block addBlack2_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 对整体 进行确认操作 -->
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
	src="${pageContext.request.contextPath}/js/dataManage/dataManage.js"></script>
</html>