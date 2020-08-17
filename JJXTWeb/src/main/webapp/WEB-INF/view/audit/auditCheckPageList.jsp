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
						<select name="appId" id="searchAppId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
                   			<option value="">全部账户</option>
                   			<c:forEach var="s" items="${apps }">
								<option value="${s.id}">${s.id}:${s.appName }</option>
							</c:forEach>
                   		</select>
					</div>
					<div class="col-sm-4">
					<select name="product" id="searchChannelId"
						class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%" data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
					<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
					</div>
				</div>
				
			</div>
			 
		<div id="toolbar" class="btn-group pull-left"
			style="margin-left: 20px">
			<button id="btn_pass" type="button" class="btn btn-default"
				style="display: none;">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量通过
			</button>
			<button id="btn_del" type="button" class="btn btn-default" style="display: none;">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量驳回
			</button>
		</div>
		<table id="mytab" class="table table-hover"></table>
	</div>
	<div class="modal fade" id="Details" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">详情</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-bordered table-hover">
						<tr>
							<td class="td">账户</td>
							<td><span id="Details_app_name"></span></td>
							<td class="td">账户ID</td>
							<td><span id="Details_app_id"></span></td>
						</tr>
						<tr>
							<td class="td">通道</td>
							<td><span id="Details_channel_name"></span></td>
							<td class="td">通道ID</td>
							<td><span id="Details_channel_id"></span></td>
						</tr>
						<tr>
							<td class="td">命中的必审词</td>
							<td><span id="Details_auditResult"></span></td>
							<td class="td">数量</td>
							<td><span id="Details_amount"></span></td>
						</tr>
						<tr>
							<td class="td">内容</td>
							<td colspan="3"><p id="Details_content"></p></td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
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
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/audit/auditCheckPageList.js"></script>
</html>