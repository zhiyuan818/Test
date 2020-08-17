<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出账管理</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="ibox float-e-margins">
			<form id="searchForm" class="form-horizontal " method="post">
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>客户</strong></label>
					<div class="col-sm-5" >
						<select name="companyId" id="companyId"
							class="selectpicker show-tick form-control" placeholder="全部账户"
							data-width="98%" data-first-option="false" required
							data-live-search="true">
							<option value="">全部客户</option>
							<c:forEach var="s" items="${companys }">
								<option value="${s.id}">${s.id}:${s.companyKey }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>账户</strong></label>
					<div class="col-sm-5" >
						<select name="appId" id="appId"
							class="selectpicker show-tick form-control" placeholder="全部账户"
							data-width="98%" data-first-option="false" required
							data-live-search="true">
							<option value="">全部账户</option>
							<c:forEach var="s" items="${apps }">
								<option value="${s.id}">${s.id}:${s.appName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>结算方式</strong></label>
					<div class="col-sm-5" >
						<select name="settlement" id="settlement" title="请选择" class="selectpicker show-tick form-control" data-width="98%"  multiple>
							<option value="success">成功</option>
							<option value="unknow">未知</option>
							<option value="fail">失败</option>
						</select>
					</div>
				</div>
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>开始时间</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control time" name="startTime"
							id="startTime" placeholder="开始时间" />
					</div>
				</div>
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>结束时间</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control time" name="endTime"
							id="endTime" placeholder="结束时间" />
					</div>
				</div>
				<div class="form-group">
				<div class="col-sm-10" >
				<label class="col-sm-5 control-label"></label>
						<div class="btn-group" data-toggle="buttons">
							<label class="btn btn-default active"> 
								<input type="radio" name="selectType" value="1" checked="checked">分账户
							</label>
							<label class="btn btn-default"> 
								<input type="radio" name="selectType" value="2">分运营商
							</label> 
						</div>
					</div>
					</div>
				<div class="form-group">
					<div class="col-sm-1 col-sm-offset-5">
						<button id="btn_billing" type="button"
							class="btn btn-default btn-block">出账</button>
					</div>
				</div>
			</form>
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
	src="${pageContext.request.contextPath}/js/billing/billing.js"></script>
</html>