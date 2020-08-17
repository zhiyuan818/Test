<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通道管理</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<select name="supplierId" id="searchSupplierId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">全部供应商</option>
               			<c:forEach var="s" items="${suppliers }">
							<option value="${s.id}">${s.id}:${s.supplierKey }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2">
					<select name="head" id="searchHead"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">全部商务</option>
               			<c:forEach var="s" items="${heads }">
							<option value="${s.chineseName}">${s.chineseName }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2">
					<select name="channelId" id="searchChannelId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">全部通道</option>
               			<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2">
					<select id="searchChannelStatus" name="channelStatus" class="selectpicker show-tick form-control">
						<option value="">状态</option>
						<option value="normal">正常</option>
						<option value="paused">暂停</option>
						<option value="deleted">删除</option>
					</select>
				</div>
				<div class="col-sm-2">
               		<input type="text" class="form-control" name="account"
						id="searchAccount" placeholder="账户" />
				</div>
				 
				<div class="col-sm-2">
               			<input type="text" class="form-control" name="svcAddr"
						id="searchSvcAddr" placeholder="网关地址" />
				</div>
				<div class="col-sm-2">
               			<input type="text" class="form-control" name="serviceCode"
						id="searchServiceCode" placeholder="服务代码" />
				</div>
				<div class="col-sm-2">
               			<input type="text" class="form-control" name="enterpriseCode"
						id="searchEnterpriseCode" placeholder="企业代码" />
				</div>
				<div class="col-sm-2">
					<select id="searchVariant" name="variant" class="selectpicker show-tick form-control">
						<option value="">通道变体标识</option>
						<c:forEach var="s" items="${variants }">
						<option value="${s.name}">${s.id}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select id="searchPlatformFlag" name="platformFlag" class="selectpicker show-tick form-control">
						<option value="">通道平台标记</option>
						<option value="3">集客三平台</option>
						<option value="4">直客四平台</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select id="searchAllPlatformUsed" name="allPlatformUsed" class="selectpicker show-tick form-control">
						<option value="">是否支持双平台启动</option>
						<option value="yes">支持</option>
						<option value="no">不支持</option>
					</select>
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>

		</div>
		<table id="myTab" class="table table-hover"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
		</div>
	</div>
	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">新增通道</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">通道名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="name" id="name" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">备注</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="notice"
									id="notice" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">所属供应商</label>
							<div class="col-sm-10">
								<select name="supplierId" id="supplierId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部供应商</option>
									<c:forEach var="s" items="${suppliers }">
										<option value="${s.id}">${s.id}:${s.supplierName }</option>
									</c:forEach>
								</select>
								 <input type="hidden" name="supplierId" id="oldSupplierId">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">源号码段</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="sourceSegment"
									id="sourceSegment" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">备用通道</label>
							<div class="col-sm-10">
								<select name="backupChannelId" id="backupChannelId"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">自动签名</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default"> <input type="radio"
										name="autoExtractSigns" value="yes">启用
									</label> <label class="btn btn-default active"> <input
										type="radio" name="autoExtractSigns" value="no"
										checked="checked">不启用
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">支持状态报告</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="haveReport" value="yes" checked="checked">支持
									</label> <label class="btn btn-default"> <input type="radio"
										name="haveReport" value="no">不支持
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">支持上行</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="haveMo" value="yes" checked="checked">支持
									</label> <label class="btn btn-default"> <input type="radio"
										name="haveMo" value="no">不支持
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">运营商</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default"> <input
										type="checkbox" id="toCmcc" name="toCmcc" value="yes">移动
									</label> <label class="btn btn-default"> <input
										type="checkbox" id="toUnicom" name="toUnicom" value="yes">联通
									</label><label class="btn btn-default"> <input
										type="checkbox" id="toTelecom" name="toTelecom" value="yes">电信
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">报备模板</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="baobeiModel" value="before"
										checked="checked">先发后报
									</label> <label class="btn btn-default"> <input type="radio"
										name="baobeiModel" value="after">先报后发
									</label> <label class="btn btn-default"> <input type="radio"
										name="baobeiModel" value="not_required">不报备
									</label> <label class="btn btn-default"> <input type="radio"
										name="baobeiModel" value="before_pj">先报备破解
									</label> <label class="btn btn-default"> <input type="radio"
										name="baobeiModel" value="before_malei">先报备码类
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">信息最大字符数</label>
							<div class="col-sm-10">
								<input type="number" class="form-control" value="500"
									name="sizeMax" id="sizeMax" data-step="10" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">短信计费字符数</label>
							<div class="col-sm-10">
								<input type="number" class="form-control" value="70"
									name="sizeFirst" id="sizeFirst" data-step="1" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">长短信计费字符数</label>
							<div class="col-sm-10">
								<input type="number" class="form-control" name="sizeCharge"
									id="sizeCharge" data-step="1" value="67" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button"
									class="btn btn-default btn-block add_backBtn" id="add_backBtn">返回</button>
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
					<h4 class="modal-title" id="edit">修改通道</h4>
					<p style="font-size: 12px; color: #ff5907;">不支持跨标签保存</p>
				</div>
				<div class="ibox-content tabbable tabs-tabs">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#A" data-toggle="tab">基础设置</a></li>
						<li><a id="hightLevel" data-toggle="tab">技术参数</a></li>
					</ul>
					<div class="hr-line-dashed"></div>
					<div class="tab-content">
						<div class="tab-pane active" id="A">
							<form id="editBasisForm" method="post" class="form-horizontal">
								<input type="hidden" name="id" id="editBasisId">
								<input type="hidden" name="channelId" id="editBasisChannelId">
								<div class="form-group">
									<label class="col-sm-2 control-label">通道名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="name"
											id="editName"  />
										<span id="editSpan"></span>
										<input type="hidden" class="form-control"
											id="oldEditName" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">备注</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="notice"
											id="editNotice" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">所属供应商</label>
									<div class="col-sm-10">
										<select name="supplierId" id="editSupplierId"
											class="selectpicker show-tick form-control"
											data-live-search="true">
											<option value="">全部供应商</option>
											<c:forEach var="s" items="${suppliers }">
												<option value="${s.id}">${s.id}:${s.supplierName }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">运营商</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input id="editToCmcc"
												type="checkbox" name="toCmcc" value="yes">移动
											</label> <label class="btn btn-default"> <input id="editToUnicom"
												type="checkbox" name="toUnicom" value="yes"
												>联通
											</label><label class="btn btn-default"> <input id="editToTelecom"
												type="checkbox" name="toTelecom" value="yes"
												>电信
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道起始时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="submitBegin"
											id="editSubmitBegin" disabled="disabled" /> <font size="2" color="#ccc">4位数字表示，如0830表示早上8:30，最小0000</font>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道结束时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="submitEnd"
											id="editSubmitEnd" disabled="disabled" /> <font size="2" color="#ccc">4位数字表示，如2000表示晚上20:00，最大2400</font>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道月限</label>
									<div class="col-sm-4">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="isMonthLimit" value="yes">启用
											</label> <label class="btn btn-default"> <input type="radio"
												name="isMonthLimit" value="no">不启用
											</label>
										</div>
									</div>
									<label class="col-sm-2 control-label">月限条数</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" name="monthLimitCount"
											id="editMonthLimitCount" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">备用通道</label>
									<div class="col-sm-10">
										<select name="backupChannelId" id="editBackupChannelId"
											class="selectpicker show-tick form-control"
											data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${channels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道价格</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="channelPrice" id="editChannelPrice" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道状态</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="channelStatus" value="normal">正常
											</label> <label class="btn btn-default"> <input type="radio"
												name="channelStatus" value="paused">暂停
											</label> <label class="btn btn-default"> <input type="radio"
												name="channelStatus" value="deleted">删除
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道平台标记</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" name="platformFlag"
											id="editPlatformFlag" />
									</div>
									<label class="col-sm-3 control-label">是否支持双平台启动</label>
									<div class="col-sm-4">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="allPlatformUsed" value="yes">支持
											</label> <label class="btn btn-default active"> <input
												type="radio" name="allPlatformUsed" value="no"
												checked="checked">不支持
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-2">
										<button type="button" class="btn btn-primary btn-block"
											id="edit_basisBtn">保存</button>
									</div>
								</div>
							</form>
						</div>
						<div class="tab-pane" id="B">
							<form id="editHighLevelForm" method="post"
								class="form-horizontal">
								<input type="hidden" name="id" id="editHighLevelId">
								<div class="form-group">
									<label class="col-sm-2 control-label">支持状态报告</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="haveReport" value="yes">支持
											</label> <label class="btn btn-default"> <input type="radio"
												name="haveReport" value="no">不支持
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">支持上行</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="haveMo" value="yes">支持
											</label> <label class="btn btn-default"> <input type="radio"
												name="haveMo" value="no">不支持
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">上行匹配方式</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="moMatch" value="ext">扩展
											</label> <label class="btn btn-default"> <input type="radio"
												name="moMatch" value="phone">手机号
											</label> <label class="btn btn-default"> <input type="radio"
												name="moMatch" value="extphone">扩展+手机号
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">启用用户扩展</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="userExtSrc" value="yes">启用
											</label> <label class="btn btn-default"> <input type="radio"
												name="userExtSrc" value="no">不启用
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">启用自动扩展</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="autoExtSrc" value="yes">启用
											</label> <label class="btn btn-default"> <input type="radio"
												name="autoExtSrc" value="no">不启用
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">自动提取标识</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="autoExtractSigns" value="yes">启用
											</label> <label class="btn btn-default"> <input type="radio"
												name="autoExtractSigns" value="no">不启用
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">源号码类型</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="sourceType" value="single">single
											</label> <label class="btn btn-default"> <input type="radio"
												name="sourceType" value="group">group
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">支持国际</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="toIntl" value="yes">支持
											</label> <label class="btn btn-default"> <input type="radio"
												name="toIntl" value="no">不支持
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">过滤公共黑名单</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="filterGlobalBlack" value="yes">过滤
											</label> <label class="btn btn-default"> <input
												type="radio" name="filterGlobalBlack" value="no">不过滤
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">网关类型</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="gwType" value="http">http
											</label> <label class="btn btn-default"> <input type="radio"
												name="gwType" value="cmpp">cmpp
											</label> <label class="btn btn-default"> <input type="radio"
												name="gwType" value="smgp">smgp
											</label> <label class="btn btn-default"> <input type="radio"
												name="gwType" value="sgip">sgip
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">报备模板</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="baobeiModel" value="before">先发后报
											</label> <label class="btn btn-default"> <input type="radio"
												name="baobeiModel" value="after">先报后发
											</label> <label class="btn btn-default"> <input type="radio"
												name="baobeiModel" value="not_required">不报备
											</label> <label class="btn btn-default"> <input type="radio"
												name="baobeiModel" value="before_pj">先报备破解
											</label> <label class="btn btn-default"> <input type="radio"
												name="baobeiModel" value="before_malei">先报备码类
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道消息ID类型</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="msgidType" value="single">single
											</label> <label class="btn btn-default"> <input type="radio"
												name="msgidType" value="multi">multi
											</label> <label class="btn btn-default"> <input type="radio"
												name="msgidType" value="singleasync">singleasync
											</label> <label class="btn btn-default"> <input type="radio"
												name="msgidType" value="multiasync">multiasync
											</label> <label class="btn btn-default"> <input type="radio"
												name="msgidType" value="dest">dest
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">源号码段</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="sourceSegment"
											id="editSourceSegment" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">信息最大字符数</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" name="sizeMax"
											id="editSizeMax" data-step="1" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">短信计费长度</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" value="70"
											name="sizeFirst" id="editSizeFirst" data-step="1" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">长短信计费长度</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" name="sizeCharge"
											id="editSizeCharge" data-step="1" value="70" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">先报备破解的扩展号</label>
									<div class="col-sm-10">
										<input type="text" class="form-control"
											name="baobeiBeforePjExtSrc" id="editBaobeiBeforePjExtSrc" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">剔除号码</label>
									<div class="col-sm-10 role add">
										<input type="text" class="form-control" name="callOut"
											id="editCallOut" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">源号码最大长度</label>
									<div class="col-sm-10">
										<input type="number" class="form-control"
											name="sourceFullLength" id="eidtSourceFullLength" value="20"
											min="0" data-step="1">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">估算成功率</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" name="successRate"
											id="editSuccessRate" max="100" min="0" data-step="1" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">网关地址</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="svcAddr"
											id="editSvcAddr" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">网关端口</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="svcPort"
											id="editSvcPort" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">登录网关账号</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="account"
											id="editAccount" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">登录网关密码</label>
									<div class="col-sm-10">
										<input type="password" class="form-control" name="password"
											id="editPassword" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">最大连接数</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" name="linkMax"
											id="editLinkMax" min="0" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">每连接最大发送速度（条/秒）</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" name="linkSpeed"
											id="editLinkSpeed" min="0" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">首条计费长度</label>
									<div class="col-sm-10">
										<input type="number" class="form-control"
											name="firstMsgChargeLen" value="70"
											id="editFirstMsgChargeLen" readonly="readonly" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">长短信第二条开始计费长度</label>
									<div class="col-sm-10">
										<input type="number" class="form-control"
											name="subseqMsgChargeLen" value="67"
											id="editSubseqMsgChargeLen" readonly="readonly" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">服务代码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="serviceCode"
											id="editServiceCode" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">企业代码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="enterpriseCode"
											id="editEnterpriseCode" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道特定的参数</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="extras"
											id="editExtras" /> <font size="2" color="#ccc">例:patam1=value1&
											param2=value2&...</font>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道分配的短号码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="shortNum"
											id="editShortNum" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">通道类</label>
									<div class="col-sm-8">
										<select name="variant" id="editVariant"
											class="selectpicker show-tick form-control"
											data-live-search="true" disabled="disabled">
											<option value="">---请选择---</option>
											<c:forEach var="s" items="${variants }">
												<option value="${s.name}">${s.id}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-2" id="div_variant">
										<input type="button" value="修改通道类" onclick='updateVariant()'>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道报警码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="alarmCode"
											id="editAlarmCode" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">通道提交报警码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" name="submitAlarmCode"
											id="editSubmitAlarmCode" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">队列分配</label>
									<div class="col-sm-8">
										<select name="redisFlag" id="editRedisFlag"
											class="selectpicker show-tick form-control" disabled="disabled">
											<option>1</option>
											<option>2</option>
										</select>
										<font size="2" color="#ccc">修改队列分配请修改模块对应线程</font>
									</div>
									<div class="col-sm-2" id="div_redisFlag">
										<input type="button" value="修改队列分配" onclick='updateRedisFlag()'>
									</div>
								</div>
								<div class="form-group" id="model_model">
									<label class="col-sm-2 control-label">模块对应线程</label>
									<div class="col-sm-10">
										<c:forEach items="${models }" var="s">
											${s.name }&nbsp; <input
												style="width:46px" data="${s.value }" type="number" min="0" data-step="1"
												>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</c:forEach>
										<input type="hidden" id="editModel" name="model">
									</div>
								</div>
								<div class="form-group" id="logic_model">
									<label class="col-sm-2 control-label">logic模块的线程数</label>
									<div class="col-sm-10">
										<c:forEach items="${logics }" var="s">
											${s.name }&nbsp; <input
												style="width:46px" data="${s.value }" type="number" min="0" data-step="1"
												>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</c:forEach>
										<input type="hidden" id="editLogicModel" name="logicModel">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-2">
										<button type="button" class="btn btn-primary btn-block"
											id="edit_highLevelBtn">保存</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">线程分配</h4>
				</div>
				<div class="modal-body" id="modelBody"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						id="averageAllocation" data-dismiss="modal">平均分配</button>
					<button type="button" class="btn btn-default" id="masterAllocation"
						data-dismiss="modal">以主分配</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id=linkProduct tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:700px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		关联产品
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody" style="height:400px;overflow:auto;">
	                    <table id="tLinkProduct" class="table table-bordered table-hover">
	                    </table> 
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
	                    </button>
	                </div>
	            </div>
	        </div>
		</div>
		<div class="modal fade" id=batchSwitch tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:900px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		通道关联配置
	                    </h4>
	                </div>
	                
	                <div class="modal-body" id="menuBody" style="height:400px;overflow:auto;">
	                    <table id="tBatchSwitch" class="table table-bordered table-hover">
	                    </table> 
	                </div>
	                <div class="modal-footer">
	                	<label class="col-sm-2 control-label" style="font-size:18px;">批量修改通道:</label>
	                
		                <div class="col-sm-4">
							<select name="channelId" id="batchChannelId"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
		               			<option value=''>全部通道</option>
		               			<c:forEach var="s" items="${normalChannelList }">
									<option value="${s.id}">${s.id}:${s.name }</option>
								</c:forEach>
		               		</select>
						</div>
		                <button id="btn_batch" type="button" class="btn btn-default">批量修改
						</button>
		                <button id="btn_save" type="button" class="btn btn-default">保存
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
					<div class="modal-footer">
						<span style="font-size: 13px; color: #ff5907;">
							注意：切换重发时，当三网通道切换成普通通道，需在重发管理中按实际需求决定是否增加另外两种运营商的通道<br>
						</span>
					</div>
	            </div>
	        </div>
		</div>
		
	<div class="popup_de xycenter" style="z-index: 9999">
		<div class="popup_box" style="width: 320px;height: 180px">
			<span class="popup_close" value="关闭">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit" value="确定">确定</div>
				<div class="popup_btn btn_cancel" value="取消">取消</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="buttons" value="${buttons }">
	<div id="div" style="display: none">${normalChannel }</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/channel/channelPageList.js"></script>
</html>