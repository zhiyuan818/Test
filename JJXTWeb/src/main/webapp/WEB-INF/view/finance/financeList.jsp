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
		</style>
	</head>
	<jsp:include page="/head.html"></jsp:include>
	<body>
		<div class="tableBody">
			<div class="panel panel-default" style="margin-bottom: 0px;">
				<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
				<input type="text" class="form-control" name="appName" id="search_appname" placeholder="帐户(精确)"/>
				</div>
				<div class="col-sm-2">
					<select class="selectpicker show-tick form-control" id="search_addName" >
						<option value="">全部销售</option>
						<c:forEach items="${managers}" var="m">
							<option value="${m.managerName}">${m.chineseName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select class="selectpicker show-tick form-control" id="search_changeType" placeholder="类型">
						<option value="">全部类型</option>
						<option value="充值">充值</option>
						<option value="赠送">赠送</option>
						<option value="失败回补">失败回补</option>
						<option value="核减">核减</option>
						<option value="扣罚">扣罚</option>
						<option value="退款">退款</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select class="selectpicker show-tick form-control" id="search_bill">
						<option value="">是否到账</option>
						<option value="yes">已到账</option>
						<option value="no">未到账</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="startTime"
						id="startTime" placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="endTime"
						id="endTime" placeholder="结束时间" />
				</div>
				<div class="col-sm-2 pull-right">
				<button class="btn btn-primary" id="search_btn">搜索</button>
				<button class="btn btn-default" id="search_back">重置</button>
				</div>
				</div>
			</div>
			<table id="mytab" class="table table-hover"></table>
		</div>
   	    <div class="popup_de xycenter">
			<div class="popup_box">
				<span class="popup_close">×</span>
			    <span class="show_msg"></span>
			    <div class="btn_box">
				    <div class="popup_btn btn_submit">确定</div>
					<div class="popup_btn btn_cancel">取消</div>
			    </div>
			</div>	
		</div>
		<input type="hidden" name="buttons" value="${buttons}" id="buttons">
	</body>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/finance/finance.js"></script>
</html>