<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通道测试</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="addBody" style="width: 100%; position: absolute; top: 10px">
		<div class="col-sm-22">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>新增通道</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">通道</label>
							<div class="col-sm-5">
								<select name="channelId" id="channelId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.id}">${s.id}:${s.name }</option>
									</c:forEach>
								</select> <span class="help-block">
									请选择测试通道，如果选择空通道，则按“绑定的测试账户”归属的通道下发</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">扩展号</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="extNumber"
									id="extNumber" maxlength="12" /> <span class="help-block">
									扩展号为不超过12位的数字</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">手机号码</label>
							<div class="col-sm-5">
								<textarea rows="6" cols="80" name="destNumber" id="destNumber"></textarea>
								<span class="help-block">手机号码，可以用逗号分割，号码个数不超过100个</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">短信内容</label>
							<div class="col-sm-5">
								<textarea rows="6" cols="80" name="content" id="content"></textarea>
								<span id="help" class="help-block">短信内容 已输入字符 0 个，按 1 条计费</span>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="channel_test">测试</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="popup_de xycenter">
		<div class="popup_box">
			<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit" value="确定">确定</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="buttons" value="${buttons }">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/channel/channelTest.js"></script>
</html>