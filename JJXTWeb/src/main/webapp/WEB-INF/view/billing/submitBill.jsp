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
			<form id="billForm" class="form-horizontal " method="post">
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>客户</strong></label>
					<div class="col-sm-5" >
						<select name="companyId" id="companyId"
							class="selectpicker show-tick form-control" placeholder="全部账户"
							data-width="98%" data-first-option="false" required
							data-live-search="true">
							<option value="405">人人贷</option>
						</select>
					</div>
				</div>
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>账户</strong></label>
					<div class="col-sm-5" >
						<select name="appName" id="appName"
							class="selectpicker show-tick form-control" placeholder="全部账户"
							data-width="98%" data-first-option="false" required
							data-live-search="true">
							<option value="">全部账户</option>
							<c:forEach var="s" items="${apps }">
								<option value="${s.appName}">${s.id}:${s.appName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				 
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>日期</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control" name="billDate" id="billDate"
						placeholder="选择日期" />
					</div>
				</div>
				
				
				<div class="form-group" style="display: none">
						<label class="col-sm-3 control-label">providerName</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="providerName" id="providerName" value = "久佳信通"/>
						</div>
					</div>
				
				
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>状态成功总数</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control" name="successCount" id="successCount" />
					</div>
				</div>
				
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>未知总数</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control"  name="unknownCount" id="unknownCount" />
					</div>
				</div>
				
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>实际计费条数</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control" name="count" id="count" />
					</div>
				</div>
				
				<div class="form-group ">
					<label class="col-sm-3 control-label"><strong>总费用</strong></label>
					<div class="col-sm-5" >
						<input type="text" class="form-control" name="cost" id="cost" placeholder="总费用=单价*实际计费条数，单位元，保留两位小数" />
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-1 col-sm-offset-5">
						<button id="btn_billing" type="button"
							class="btn btn-default btn-block">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- 对整体 进行确认操作 -->
	<div class="popup_de xycenter" style="z-index: 9999">
		<div class="popup_box" style="width: 320px;height: 180px">
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
	src="${pageContext.request.contextPath}/js/billing/submitBill.js"></script>
</html>