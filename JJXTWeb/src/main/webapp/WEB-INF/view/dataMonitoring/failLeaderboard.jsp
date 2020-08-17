<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控汇总</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<section id="main-content">
		<section class="wrapper">
			<div class="row">
				<div class="col-sm-6">
					<section class="panel">
						<header class="panel-heading">
							<div class="col-sm-10">
								<select id="appId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账户</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>	
							<input class="btn btn-primary" type="button" id="search_appId" value="搜索">
						</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>排行榜</th>
									<th>状态</th>
									<th>数量</th>
								</tr>
							</thead>
							<tbody id="appBody">
								
							</tbody>
						</table>
					</section>
				</div>
				<div class="col-sm-6">
					<section class="panel">
						<header class="panel-heading">
							<div class="col-sm-10">
								<select id="channelId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${chans }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
							<input class="btn btn-primary" type="button" id="search_channelId" value="搜索">
						</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>排行榜</th>
									<th>状态</th>
									<th>数量</th>
								</tr>
							</thead>
							<tbody  id="channelBody">
								
							</tbody>
						</table>
					</section>
				</div>
			</div>
		</section>
	</section>
	<div class="popup_de xycenter" style="z-index: 9999">
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
	src="${pageContext.request.contextPath}/js/dataMonitoring/failLeaderboard.js"></script>
</html>