<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.td {
	width: 100px;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
		<div class="panel-body form-group" style="margin-bottom:0px;">
			<div class="col-sm-2">
			<select name="channelId" id="channelId"
						class="selectpicker show-tick form-control" placeholder="客户简称(精确)"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">查询全部</option>     
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
			</div>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="logDate" id="logDate" placeholder="月份" />
			</div>
			
			<div class="col-sm-2 pull-right">
				<button class="btn btn-primary" id="search_btn">搜索</button>
				<button class="btn btn-default" id="search_back">重置</button>
			</div>
		</div>
		</div>
		<table id="chacomplaintab" class="table table-hover"></table>
	</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/complain/channelComplain.js"></script>
</html>