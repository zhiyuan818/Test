<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.td {
	width: 100px;
}
.table {
	table-layout: fixed;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="id"
						id="search_id" placeholder="模板ID" />
				</div>
				<div class="col-sm-2">
					<select name="appId" id="search_appId"
						class="selectpicker show-tick form-control" placeholder="全部账号" data-width="98%" data-live-search="true">
						<option value="">全部账号</option>
						<option value="0">全局</option>
						<c:forEach var="s" items="${apps }">
							<option value="${s.id}">${s.id}:${s.appName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="rule"
						id="search_rule" placeholder="模板规则" />
				</div>
				<div class="col-sm-2">
					<select name="strategy" id="search_strategy"
						class="selectpicker show-tick form-control" placeholder="模板策略" data-width="98%" data-live-search="true">
						<option value="">模板策略</option>
						<option value="pass">通过</option>
						<option value="reject">驳回</option>
						<option value="audit">审核</option>
						<option value="redirect">通道跳转</option>
						<option value="signext">签名扩展跳转</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="status" id="search_status" 
						class="selectpicker show-tick form-control" placeholder="状态" data-width="98%" data-live-search="true">
						<option value="">模板状态</option>
						<option value="normal">正常</option>
						<option value="paused">暂停</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="templateInfo"
						id="search_templateInfo" placeholder="模板说明" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="result"
						id="search_result" placeholder="策略结果" />
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
				</button>
			<button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
			<button id="btn_pau" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量暂停
				</button>
			<button id="btn_str" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量启动
				</button>
		</div>
		<table id="mytab" class="table table-hover"></table>
	</div>

	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
			aria-labelledby="add" aria-hidden="true">
			<div class="modal-dialog" style="width: 720px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close add_backBtn"
							data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="add">添加模板策略</h4>
					</div>
					<div class="modal-body" id="add_body">
						<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<select name="appId" id="appId"
									class="selectpicker show-tick form-control" placeholder="全部账号" data-width="98%" data-live-search="true">
									<option value="">全部账号</option>
									<option value="0">全局</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板策略</label>
							<div class="col-sm-10">
								<select name="strategy" id="strategy"
									class="selectpicker show-tick form-control" placeholder="全部策略" data-width="98%">
									<option value="pass">通过</option>
									<option value="reject">驳回</option>
									<option value="audit">审核</option>
									<option value="redirect">通道跳转</option>
									<option value="signext">签名扩展跳转</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">模板说明</label>
							<div class="col-sm-10">
								<input type="text" name="templateInfo" id="templateInfo" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板规则</label>
							<div class="col-sm-10">
<!-- 								<input type="text" name="rule" id="rule" class="form-control">
 -->								<textarea name="rule" id="rule" class="form-control"></textarea>
								<span>1.支持'与&'、'或|'、'开始^'符号;<br>
									  2.规则用英文'()'括起来,例如：(微信);<br>
									  3.括号内只能有一种判断符，例如：(贷款)、(信用卡|贷款)、(贷款&信用卡)、(^贷款);<br>
									  4.判断开头可以使用'^'符，例如：(^信用卡)、(^【信用卡】);<br>
									  5.不能使用括号嵌套<br>
									   错误案例：微信、(微信|威信&验证码)、^(信用卡)、((微信)&(信用卡))等</span>
							</div>
						</div>
						<div id="addResult" class="form-group">
							<label class="col-sm-2 control-label">策略结果</label>
							<div class="col-sm-10">
								<input type="text" name="result" id="result" class="form-control">
							</div>
						</div>
						<div id="addCmccDiv" class="form-group" style="display: none">
							<label class="col-sm-2 control-label">移动通道</label>
							<div class="col-sm-10">
								<select name="cmccChannelId" id="addCmccChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${cmccChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div id="addUnicomDiv" class="form-group"  style="display: none">
							<label class="col-sm-2 control-label">联通通道</label>
							<div class="col-sm-10">
								<select name="unicomChannelId" id="addUnicomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${unicomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div  id="addTelecomDiv" class="form-group"  style="display: none">
							<label class="col-sm-2 control-label">电信通道</label>
							<div class="col-sm-10">
								<select name="telecomChannelId" id="addTelecomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${telecomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">状态</label>
							<div class="col-sm-10">
								<select name="status" id="status"
									class="selectpicker show-tick form-control" placeholder="模板状态" data-width="98%">
									<option value="normal">正常</option>
									<option value="paused">暂停</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">标记</label>
							<div class="col-sm-10">
								<input type="text" name="keyWord" id="keyWord" class="form-control">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block add_backBtn"
									id="add_backBtn">返回</button>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">修改模板策略</h4>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<input type="hidden" name="id" id="edit_id">
								<select name="appId" id="edit_appId"
									class="selectpicker show-tick form-control" placeholder="全部账号" data-width="98%" data-live-search="true">
									<option value="">全部账号</option>
									<option value="0">全局</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
								<input type="hidden" id="edit_old_appId">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板策略</label>
							<div class="col-sm-10">
								<select name="strategy" id="edit_strategy"
									class="selectpicker show-tick form-control" placeholder="全部策略" data-width="98%">
									<option value="pass">通过</option>
									<option value="reject">驳回</option>
									<option value="audit">审核</option>
									<option value="redirect">通道跳转</option>
									<option value="signext">签名扩展跳转</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">模板说明</label>
							<div class="col-sm-10">
								<input type="text" name="templateInfo" id="edit_templateInfo" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板规则</label>
							<div class="col-sm-10">
								<textarea name="rule" id="edit_rule" class="form-control"></textarea>
								<input type="hidden" id="edit_old_rule">
								<span>1.支持'与&'、'或|'、'开始^'符号;<br>
									  2.规则用英文'()'括起来,例如：(微信);<br>
									  3.括号内只能有一种判断符，例如：(贷款)、(信用卡|贷款)、(贷款&信用卡)、(^贷款);<br>
									  4.判断开头可以使用'^'符，例如：(^信用卡)、(^【信用卡】);<br>
									  5.不能使用括号嵌套<br>
									   错误案例：微信、(微信|威信&验证码)、^(信用卡)、((微信)&(信用卡))等</span>
							</div>
						</div>
						<div id="updateResult" class="form-group">
							<label class="col-sm-2 control-label">策略结果</label>
							<div class="col-sm-10">
								<input type="text" name="result" id="edit_result" class="form-control">
							</div>
						</div>
						<div id="updateCmccDiv" class="form-group" style="display: none">
							<label class="col-sm-2 control-label">移动通道</label>
							<div class="col-sm-10">
								<select name="cmccChannelId" id="updateCmccChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${cmccChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div id="updateUnicomDiv" class="form-group"  style="display: none">
							<label class="col-sm-2 control-label">联通通道</label>
							<div class="col-sm-10">
								<select name="unicomChannelId" id="updateUnicomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${unicomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div  id="updateTelecomDiv" class="form-group"  style="display: none">
							<label class="col-sm-2 control-label">电信通道</label>
							<div class="col-sm-10">
								<select name="telecomChannelId" id="updateTelecomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${telecomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">状态</label>
							<div class="col-sm-10">
								<select name="status" id="edit_status"
									class="selectpicker show-tick form-control" placeholder="模板状态" data-width="98%">
									<option value="normal">正常</option>
									<option value="paused">暂停</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">标记</label>
							<div class="col-sm-10">
								<input type="text" name="keyWord" id="edit_keyWord" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">优先级</label>
							<div class="col-sm-10">
								<input type="text" name="ruleIndex" id="edit_ruleIndex" class="form-control">
								<span>模板执行顺序按照优先级从大到小.</span>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="edit_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block edit_backBtn"
									id="edit_backBtn">返回</button>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	<div class="modal fade" id="copyBody" tabindex="-1" role="dialog"
		aria-labelledby="copy" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close copy_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="copy">复制模板策略</h4>
				</div>
				<div class="ibox-content">
					<form id="copyForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<select name="appId" id="copy_appId"
									class="selectpicker show-tick form-control" placeholder="全部账号" data-width="98%" data-live-search="true">
									<option value="">全部账号</option>
									<option value="0">全局</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板策略</label>
							<div class="col-sm-10">
								<select name="strategy" id="copy_strategy"
									class="selectpicker show-tick form-control" placeholder="全部策略" data-width="98%">
									<option value="pass">通过</option>
									<option value="reject">驳回</option>
									<option value="audit">审核</option>
									<option value="redirect">通道跳转</option>
									<option value="signext">签名扩展跳转</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">模板说明</label>
							<div class="col-sm-10">
								<input type="text" name="templateInfo" id="copy_templateInfo" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模板规则</label>
							<div class="col-sm-10">
								<textarea name="rule" id="copy_rule" class="form-control"></textarea>
								<span>1.支持'与&'、'或|'、'开始^'符号;<br>
									  2.规则用英文'()'括起来,例如：(微信);<br>
									  3.括号内只能有一种判断符，例如：(贷款)、(信用卡|贷款)、(贷款&信用卡)、(^贷款);<br>
									  4.判断开头可以使用'^'符，例如：(^信用卡)、(^【信用卡】);<br>
									  5.不能使用括号嵌套<br>
									   错误案例：微信、(微信|威信&验证码)、^(信用卡)、((微信)&(信用卡))等</span>
							</div>
						</div>
						<div id="copyResult" class="form-group">
							<label class="col-sm-2 control-label">策略结果</label>
							<div class="col-sm-10">
								<input type="text" name="result" id="copy_result" class="form-control">
							</div>
						</div>
						<div id="copyCmccDiv" class="form-group" style="display: none">
							<label class="col-sm-2 control-label">移动通道</label>
							<div class="col-sm-10">
								<select name="cmccChannelId" id="copyCmccChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${cmccChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div id="copyUnicomDiv" class="form-group"  style="display: none">
							<label class="col-sm-2 control-label">联通通道</label>
							<div class="col-sm-10">
								<select name="unicomChannelId" id="copyUnicomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${unicomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div  id="copyTelecomDiv" class="form-group"  style="display: none">
							<label class="col-sm-2 control-label">电信通道</label>
							<div class="col-sm-10">
								<select name="telecomChannelId" id="copyTelecomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${telecomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">状态</label>
							<div class="col-sm-10">
								<select name="status" id="copy_status"
									class="selectpicker show-tick form-control" placeholder="模板状态" data-width="98%">
									<option value="normal">正常</option>
									<option value="paused">暂停</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">标记</label>
							<div class="col-sm-10">
								<input type="text" name="keyWord" id="copy_keyWord" class="form-control">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="copy_saveBtn">复制</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block copy_backBtn"
									id="copy_backBtn">返回</button>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	<div class="modal fade" id="checkBody" tabindex="-1" role="dialog"
		aria-labelledby="check" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close check_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="check">模板检测</h4>
				</div>
				<div class="ibox-content">
					<form id="checkForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">模板规则</label>
							<div class="col-sm-10">
								<textarea rows="3" cols="70" name="check_rule" id="check_rule" readonly="readonly"></textarea>
								<input type="hidden" id="check_id" >
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">测试内容</label>
							<div class="col-sm-10">
								<textarea rows="10" cols="70" name="content" id="content" placeholder="请输入测试内容"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">命中结果</label>
							<div class="col-sm-10">
								<textarea rows="3" cols="70" id="check_result"></textarea>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="check_saveBtn">查找</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block check_backBtn"
									id="check_backBtn">返回</button>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="signExtDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:900px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		签名扩展跳转详情
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody" style="height:400px;overflow:auto;">
	                    <table class="table table-bordered table-hover" id="signExtTab">
	                    </table> 
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
	                    </button>
	                </div>
	            </div>
	        </div>
		</div>
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
	src="${pageContext.request.contextPath}/js/template/templateList.js"></script>
</html>