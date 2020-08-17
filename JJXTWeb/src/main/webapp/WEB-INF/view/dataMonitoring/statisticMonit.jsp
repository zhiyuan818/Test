<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控统计数据</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body onload="loadCount()">
	<section id="main-content">
		<section class="wrapper">
			<div class="row">
				<div class="col-sm-12">
					<section class="panel">
						<header class="panel-heading"> 统计总计 </header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>数据类别</th>
									<th>账号提交总数</th>
									<th>平台提交总数</th>
									<th>平台状态推送成功</th>
									<th>平台状态推送失败</th>
									<th>平台状态回执成功</th>
									<th>平台状态回执失败</th>
									<th>平台推送上行</th>
									<th>平台接收上行</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>数量</td>
									<td id="api_submit"></td>
									<td id="gw_submit"></td>
									<td id="api_report_succ"></td>
									<td id="api_report_fail"></td>
									<td id="gw_report_succ"></td>
									<td id="gw_report_fail"></td>
									<td id="api_mo"></td>
									<td id="gw_mo"></td>
								</tr>
							</tbody>
						</table>
					</section>
				</div>
			</div>
			<div style="padding:10px;clear: both;">
					<input name="accId" id="accId" type="text" placeholder="请输入账号ID"  />
					<input id="oldAccId" type="hidden" />
					<input type="button" id="accSubmit" value="查询" />
			   	 	<div id="psLine1" style="height:400px;width: 100%;"></div>
			   	 	<div id="psLine3" style="height:400px;width: 100%;"></div>
			</div>
		    <div style="padding:10px;clear: both;">
			    <input name="channelId" id="channelId" type="text" placeholder="请输入通道ID"  />
			    <input id="oldChannelId" type="hidden" />
			    <input type="button" id="chaSubmit" value="查询" />
			    <div id="psLine2" style="height:400px;width: 100%;"></div>
			    <div id="psLine4" style="height:400px;width: 100%;"></div>
		    </div>
			<%-- <div class="row">
				<input name="appId" type="text" placeholder="请输入账号ID"  /><input type="button" id="appSubmit" value="查询" />
				<div class="col-sm-12">
					<section class="panel">
						<header class="panel-heading">账户统计数据</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>账号ID</th>
									<th>账号名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="s">
									<tr>
										<td>${s.channelId }:${s.channelName }</td>
										<td>${s.amount }</td>
										<td><button data="${s.channelId}"
												class="btn btn-default btn-xs cleanAll">全部清理</button></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</section>
				</div>
			</div>
			<div class="row">
				<input name="chanId" type="text" placeholder="请输入通道ID" /><input type="button" id="chanSubmit" value="查询" />
				<div class="col-sm-12">
					<section class="panel">
						<header class="panel-heading">账户统计数据</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>账号ID</th>
									<th>账号名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="s">
									<tr>
										<td>${s.channelId }:${s.channelName }</td>
										<td>${s.amount }</td>
										<td><button data="${s.channelId}"
												class="btn btn-default btn-xs cleanAll">全部清理</button></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</section>
				</div>
			</div> --%>
		</section>
	</section>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/echarts-all.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dataMonitoring/statisticMonit.js"></script>
</html>