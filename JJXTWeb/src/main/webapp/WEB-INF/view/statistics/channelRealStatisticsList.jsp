<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<div class="col-sm-3">
					<select name="channelId" id="channelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<c:choose>
								<c:when test="${fn:contains(buttons,'显示优享通道')}">
									<option value="${s.channelId}">${s.channelId}:${s.name }</option>
								</c:when>
								<c:otherwise>
									<c:if test="${s.channelId != 347}">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="supplierId" id="supplierId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">全部供应商</option>
               			<c:forEach var="s" items="${supplierKeys }">
							<option value="${s.id}">${s.id}:${s.supplierKey }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2">
					<select name="head" id="head"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">全部商务</option>
               			<c:forEach var="s" items="${heads }">
							<option value="${s.head}">${s.head }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="startTime" id="startTime"
						placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="endTime" id="endTime"
						placeholder="结束时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="name" id="name"
						placeholder="通道名称(模糊)" />
				</div>
				<div class="col-sm-3">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active"> 
							<input type="radio" name="statisticType" value="no" checked="checked">合计
						</label>
						<label class="btn btn-default"> 
							<input type="radio" name="statisticType" value="yes">分天
						</label> 
					</div>
				</div>
				
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
	</div>
<input type="hidden" name="buttons" value="${buttons }" id="buttons"/>
</body>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/statistics/channelRealStatisticsList.js"></script>
</html>