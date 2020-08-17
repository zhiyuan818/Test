<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.td {
	width: 110px;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="logDate" id="logDate"
						placeholder="日志日期" />
				</div>
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
					<input type="text" class="form-control" name="destNumber"
						id="destNumber" placeholder="号码(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="channelMsgId"
						id="channelMsgId" placeholder="通道消息ID(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="reportStatus"
						id="reportStatus" placeholder="回执(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="sendFlag"
						id="sendFlag" placeholder="标记(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="sign" id="sign"
						placeholder="签名(精确)" />
				</div>
				<div class="col-sm-4">
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
				<div class="col-sm-2">
					<select name="province" id="province"
						class="selectpicker show-tick form-control" placeholder="省份"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部省份</option>
						<option value="上海">上海</option>
						<option value="云南">云南</option>
						<option value="内蒙古">内蒙古</option>
						<option value="北京">北京</option>
						<option value="吉林">吉林</option>
						<option value="四川">四川</option>
						<option value="天津">天津</option>
						<option value="宁夏">宁夏</option>
						<option value="安徽">安徽</option>
						<option value="山东">山东</option>
						<option value="山西">山西</option>
						<option value="广东">广东</option>
						<option value="广西">广西</option>
						<option value="新疆">新疆</option>
						<option value="江苏">江苏</option>
						<option value="江西">江西</option>
						<option value="河北">河北</option>
						<option value="河南">河南</option>
						<option value="浙江">浙江</option>
						<option value="海南">海南</option>
						<option value="湖北">湖北</option>
						<option value="湖南">湖南</option>
						<option value="甘肃">甘肃</option>
						<option value="福建">福建</option>
						<option value="西藏">西藏</option>
						<option value="贵州">贵州</option>
						<option value="辽宁">辽宁</option>
						<option value="重庆">重庆</option>
						<option value="陕西">陕西</option>
						<option value="青海">青海</option>
						<option value="黑龙江">黑龙江</option>
						<option value="未知">未知</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="submitStatus" id="submitStatus"
						class="selectpicker show-tick form-control" placeholder="提交状态">
						<option value="">提交状态</option>
						<option value="success">提交成功</option>
						<option value="error">提交失败</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="reportStatus1" id="reportStatus1"
						class="selectpicker show-tick form-control" placeholder="回执状态">
						<option value="">回执状态</option>
						<option value="1">回执成功</option>
						<option value="2">回执失败</option>
						<option value="3">回执未知</option>
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
	<div class="modal fade" id="Details" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">下行日志详情</h4>
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
							<td class="td">手机号码</td>
							<td><span id="Details_dest_number"></span></td>
							<td class="td">归属地</td>
							<td><span id="Details_province"></span>&nbsp;&nbsp;<span
								id="Details_city"></span></td>
						</tr>
						<tr>
							<td class="td">运营商</td>
							<td><span id="Details_provider"></span></td>
							<td class="td">发送标记</td>
							<td><span id="Details_send_flag"></span></td>
						</tr>
						<tr>
							<td class="td">源号码</td>
							<td><span id="Details_src_number"></span></td>
							<td class="td">扩展号</td>
							<td><span id="Details_ext_src"></span></td>
						</tr>
						<tr>
							<td class="td">通道</td>
							<td><span id="Details_channel_name"></span></td>
							<td class="td">通道ID</td>
							<td><span id="Details_channel_id"></span></td>
						</tr>
						<tr>
							<td class="td">通道消息ID</td>
							<td><span id="Details_channel_msg_id"></span></td>
							<td class="td">计费条数</td>
							<td><span id="Details_lm_total"></span></td>
						</tr>
						<tr>
							<td class="td">消息类别</td>
							<td><span id="Details_message_class"></span></td>
							<td class="td">产品ID</td>
							<td><span id="Details_product_id"></span></td>
						</tr>
						<tr>
							<td class="td">账户提交时间</td>
							<td><span id="Details_log_time"></span></td>
							<td class="td">网关提交时间</td>
							<td><span id="Details_submit_time"></span></td>
						</tr>
						<tr>
							<td class="td">回执时间</td>
							<td><span id="Details_report_time"></span></td>
							<td class="td">提交状态</td>
							<td><span id="Details_submit_status"></span></td>
						</tr>
						<tr>
							<td class="td">网关回执</td>
							<td><span id="Details_report_status"></span></td>
							<td class="td">唯一键</td>
							<td><span id="Details_link_id"></span></td>
						</tr>
						<tr>
							<td class="td">模块路径</td>
							<td><span id="Details_logic_path"></span></td>
							<td class="td">消息ID</td>
							<td><span id=Details_message_id></span></td>
						</tr>
						<tr>
							<td class="td">唯一ID</td>
							<td><span id="Details_unique_id"></span></td>
							<td class="td">批次号</td>
							<td><span id=Details_batch_id></span></td>
						</tr>
						<tr>
							<td class="td">内容</td>
							<td colspan="3"><p id="Details_content"></p></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="company" tabindex="-1" role="dialog"
		aria-labelledby="myCompanyLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myCompanyLabel">客户详情</h4>
				</div>
				<div class="modal-body" id="companyBody">
					<table class="table table-bordered table-hover">
						<tr>
							<td class="td">客户简称</td>
							<td><span id="Details_company_key"></span></td>
						</tr>
						<tr>
							<td class="td">公司名称</td>
							<td><span id="Details_company_name"></span></td>
						</tr>
					</table>
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
	src="${pageContext.request.contextPath}/js/mt/mtList.js"></script>
</html>