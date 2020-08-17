<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div class="col-sm-2">
					<select name="appId" id="appId"
						class="selectpicker show-tick form-control" placeholder="选择账户"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">选择账户</option>
						<c:forEach var="s" items="${apps }">
							<option value="${s.id}">${s.id}:${s.appName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<select name="channelId" id="channelId"
						class="selectpicker show-tick form-control" placeholder="选择通道"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">选择通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="logDate" id="logDate"
						placeholder="选择日期" />
				</div>
				<div class="col-sm-3" style="margin: 0px 0px;">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active"> 
							<input type="radio" name="rateButton" value="1" checked="checked">到达率
						</label>
						<label class="btn btn-default"> 
							<input type="radio" name="rateButton" value="2">成功率
						</label> 
					</div>
				</div>
				
				<div class="col-sm-1">
					<button class="btn btn-primary" id="search_btn">搜索</button>
				</div>
			</div>
		</div>
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
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

</body>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/statistics/arrivalRateList.js"></script>
</html>