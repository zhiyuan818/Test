<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#A" data-toggle="tab">账户</a></li>
					<li><a id="channelPage" href="#C" data-toggle="tab">通道</a></li>
				</ul>
				
				<div class="tab-content">
					<!-- 账户面板 -->
					<div class="tab-pane active" id="A">
						<div class="col-sm-2">
							<select name="companyKey" id="companyKeySelectA"
								class="selectpicker show-tick form-control" placeholder="全部客户"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部客户</option>
								<c:forEach var="s" items="${companys }">
									<option value="${s.companyKey}">${s.id}:${s.companyKey }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select name="appId" id="appIdSelectA"
								class="selectpicker show-tick form-control" placeholder="全部账户"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部账户</option>
								<c:forEach var="s" items="${apps }">
									<option value="${s.id}">${s.id}:${s.appName }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-3">
							<select name="channelId" id="channelIdSelectA"
								class="selectpicker show-tick form-control" placeholder="全部通道"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部通道</option>
								<c:forEach var="s" items="${channels }">
									<option value="${s.channelId}">${s.channelId}:${s.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select name="provider" id="providerSelectA"
								class="selectpicker show-tick form-control" placeholder="全部运营商"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部运营商</option>
								<option value="cmcc">移动</option>
								<option value="unicom">联通</option>
								<option value="telecom">电信</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select name="type" id="typeSelectA"
								class="selectpicker show-tick form-control" placeholder="全部类型"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部类型</option>
								<option value="主用">主用</option>
								<option value="账户分省">账户分省</option>
								<option value="通道分省">通道分省</option>
								<option value="按账户重发">按账户重发</option>
								<option value="按通道重发">按通道重发</option>
								<option value="优化">优化</option>
							</select>
						</div>
						
						<div class="col-sm-2 pull-right">
							<button class="btn btn-primary" id="search_btn_app">搜索</button>
							<button class="btn btn-default" id="search_back_app">重置</button>
						</div>
						<table id="appTab" class="table table-hover"></table>
					</div>
					
					<!-- 通道面板 -->
					<div class="tab-pane" id="C">
						<div class="col-sm-3">
							<select name="channelId" id="channelIdSelectC"
								class="selectpicker show-tick form-control" placeholder="全部通道"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部通道</option>
								<c:forEach var="s" items="${channels }">
									<option value="${s.channelId}">${s.channelId}:${s.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select name="appId" id="appIdSelectC"
								class="selectpicker show-tick form-control" placeholder="全部账户"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部账户</option>
								<c:forEach var="s" items="${apps }">
									<option value="${s.id}">${s.id}:${s.appName }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select name="provider" id="providerSelectC"
								class="selectpicker show-tick form-control" placeholder="全部运营商"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部运营商</option>
								<option value="cmcc">移动</option>
								<option value="unicom">联通</option>
								<option value="telecom">电信</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select name="type" id="typeSelectC"
								class="selectpicker show-tick form-control" placeholder="全部类型"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部类型</option>
								<option value="主用">主用</option>
								<option value="账户分省">账户分省</option>
								<option value="通道分省">通道分省</option>
								<option value="按账户重发">按账户重发</option>
								<option value="按通道重发">按通道重发</option>
								<option value="优化">优化</option>
							</select>
						</div>
						
						<div class="col-sm-2 pull-right">
							<button class="btn btn-primary" id="search_btn_chan">搜索</button>
							<button class="btn btn-default" id="search_back_chan">重置</button>
						</div>
						<table id="channelTab" class="table table-hover"></table>
					</div>
				</div>
			
			</div>
		</div>
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

	<input type="hidden" name="buttons" value="${buttons }" id="buttons"/>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/statistics/grossProfit.js"></script>
</html>