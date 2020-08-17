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
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="searchBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
	               <select name="appId" id="searchAppId" class="selectpicker show-tick form-control" data-width="98%"
	                       data-live-search="true">
	                   <option value="">全部账户</option>
	                   <c:forEach var="s" items="${apps }">
	                       <option value="${s.id}">${s.id}:${s.appName }</option>
	                   </c:forEach>
	               </select>
	           </div>
	           <div class="col-sm-2">
	               <select name="channelId" id="searchChannelId"
	                       class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%"
	                       data-live-search="true">
	                   <option value="">全部通道</option>
	                   <c:forEach var="s" items="${channels}">
	                   	 	<option value="${s.channelId}">${s.channelId}:${s.name }</option>
	                   </c:forEach>
	               </select>
	           </div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="destNumber"
						id="searchDestNumber" placeholder="手机号码(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="srcNumber"
						id="searchSrcNumber" placeholder="源号码(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="content"
						id="searchContent" placeholder="投诉内容(模糊)" />
				</div>

				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_complain_btn">搜索</button>
					<button class="btn btn-default" id="search_complain_back">重置</button>
				</div>
			</div>
		</div>
		<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div id="toolbar" class="btn-group pull-right"
				style="margin-right: 20px">
				<button id="btn_add" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增投诉
				</button>
			</div>
		</div>
		<table id="searchComplainTab" class="table table-hover"></table>
	</div>

	<div class="tableBody" 
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="destNumber"
						id="destNumber" placeholder="手机号码(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="srcNumber"
						id="srcNumber" placeholder="源号码(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="startTime"
						id="startTime" placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="endTime"
						id="endTime" placeholder="结束时间" />
				</div>

				<div class="col-sm-3 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
					<button class="btn btn-default" id="search_return">返回</button>
				</div>
			</div>
		</div>
		<table id="complaintab" class="table table-hover"></table>
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
	src="${pageContext.request.contextPath}/js/complain/complainPageList.js"></script>
</html>