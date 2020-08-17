<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
.td {
	width: 100px;
}

.AutoNewline {
	Word-break: break-all; /*必须*/
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<select name="appId" id="appId"
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
					<select name="bussiness" id="bussiness"
						class="selectpicker show-tick form-control" placeholder="业务类型"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">业务类型</option>
						<option value="下行">下行</option>
						<option value="上行">上行</option>
						<option value="统计">统计</option>
						<option value="发件箱">发件箱</option>
						<option value="账户">账户</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="operation" id="operation"
						class="selectpicker show-tick form-control" placeholder="操作类型"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">操作类型</option>
						<option value="login">登陆</option>
						<option value="select">查询</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="remarks" id="remarks"
						placeholder="数据" />
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

	<div class="modal fade" id="Details" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">业务日志详情</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-bordered table-hover">
						<tr>
							<td>内容</td>
							<td class="AutoNewline"><p id="Details_content"></p></td>
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
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/khdOperationLog/khdOperationLog.js"></script>
</html>