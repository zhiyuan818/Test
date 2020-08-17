<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重推状态报告</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<form id="form" method="post" class="form-horizontal">
				<div class="ibox-title">
					<h5>重推条件</h5>
				</div>
				<div class="ibox-content">
					<div class="form-group">
						<label class="col-sm-1 control-label">账户</label>
						<div class="col-sm-3">
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
						<label class="col-sm-1 control-label">日期</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="logDate"
								id="logDate" />
						</div>
						<label class="col-sm-1 control-label">运营商</label>
						<div class="col-sm-3">
							<select id="provider" name="provider"
								class="selectpicker show-tick form-control">
								<option value="">未知</option>
								<option value="cmcc">移动</option>
								<option value="unicom">联通</option>
								<option value="telecom">电信</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-1 control-label">开始时间</label>
						<div class="col-sm-3 role add">
							<input type="text" class="form-control" name="beginTime"
								id="beginTime" />
						</div>
						<label class="col-sm-1 control-label">结束时间</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="endTime"
								id="endTime" />
						</div>
						<label class="col-sm-1 control-label">标记</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="sendFlag"
								id="sendFlag" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-1 control-label">内容ID</label>
						<div class="col-sm-3 role add">
							<input type="text" class="form-control" name="uniqueId"
								id="uniqueId" />
						</div>
						<label class="col-sm-1 control-label">通道消息ID</label>
						<div class="col-sm-3 role add">
							<input type="text" class="form-control" name="channelMsgId"
								id="channelMsgId" />
						</div>
						<label class="col-sm-1 control-label">通道</label>
						<div class="col-sm-3 role add">
							<select name="channelId" id="channelId"
								class="selectpicker show-tick form-control" placeholder="全部通道"
								data-width="98%" data-first-option="false" required
								data-live-search="true">
								<option value="">全部通道</option>
								<c:forEach var="s" items="${channels }">
									<option value="${s.channelId}">${s.channelId}:${s.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-1 control-label">手机号码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="destNumber"
								id="destNumber" />
						</div>
						<label class="col-sm-1 control-label">源号码段</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="sourceSegment"
								id="sourceSegment" />
						</div>
						<label class="col-sm-1 control-label">内容</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="content"
								id="content" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-3">
							<button type="button" class="btn btn-default btn-block"
								id="btn_searchMT">查询下行</button>
						</div>
						<div class="col-sm-2 col-sm-offset-2">
							<button type="button" class="btn btn-default btn-block"
								id="btn_searchMO">查询上行</button>
						</div>
					</div>
				</div>
				<div>
					<h5>重推结果</h5>
				</div>
				<div class="ibox-content">
					<div class="form-group">
						<label class="col-sm-1 control-label">状态报告</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="reportStatus"
								id="reportStatus" />
						</div>
						<label class="col-sm-1 control-label">目标标记</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="destSendFlag"
								id="destSendFlag" />
						</div>
						<label class="col-sm-1 control-label">秒数</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="seconds"
								id="seconds" />
						</div>
						<label class="col-sm-1 control-label">数量</label>
						<div class="col-sm-2">
							<input type="number" class="form-control" id="number"
								name="number" min="1" data-step="1"> <input
								type="hidden" id="srcNumber">
						</div>
					</div>

				</div>
			</form>
			<div class="form-group">
				<div class="col-sm-2 col-sm-offset-3">
					<button type="button" class="btn btn-default btn-block"
						id="btn_rep">重推状态报告</button>
				</div>
				<div class="col-sm-2 col-sm-offset-2">
					<button type="button" class="btn btn-default btn-block" id="btn_mo">重推上行</button>
				</div>
			</div>
			<br>
			<div class="hr-line-dashed"></div>
		</div>
	</div>
	<p style="font-size: 10px; color: #ccb590;">
		&nbsp;&nbsp;&nbsp;&nbsp;重推:<br> &nbsp;&nbsp;&nbsp;&nbsp;【1】日期：必填<br>
		&nbsp;&nbsp;&nbsp;&nbsp;【2】账户、内容ID、内容：这3项必须至少填写一项<br>
		&nbsp;&nbsp;&nbsp;&nbsp;【3】开始时间，结束时间为重推的时间范围，填写时必须同时填写<br>
		&nbsp;&nbsp;&nbsp;&nbsp;【4】标记为SHUNT，GEN等，根据需求自行填写<br>
		&nbsp;&nbsp;&nbsp;&nbsp;【5】可根据账户、日期进行全部数据重推，其他选项勿填！！<br>
		&nbsp;&nbsp;&nbsp;&nbsp;【6】按手机号码重推时，必须添加账户
	</p>
	<p style="font-size: 10px; color: #ff5907;">
		&nbsp;&nbsp;&nbsp;&nbsp;注：全部重推时账户、日期必填！<br>
		&nbsp;&nbsp;&nbsp;&nbsp;按通道重推时账户请勿填写！
	</p>
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
	src="${pageContext.request.contextPath}/js/rep/replayState.js"></script>
</html>