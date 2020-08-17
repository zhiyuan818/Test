<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<jsp:include page="/head.html"></jsp:include>	
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-4">
					<select name="productId" id="searchProductId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-live-search="true">
						<option value="">全部产品</option>
						<c:forEach var="s" items="${products }">
							<option value="${s.id}">${s.id}:${s.productName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<select name="channelId" id="searchChannelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select id="searchProductStatus" name="productStatus" class="selectpicker show-tick form-control">
						<option value="">状态</option>
						<option value="normal">正常</option>
						<option value="pause">暂停</option>
						<option value="deleted">删除</option>
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
					<h4 class="modal-title" id="add">新增产品</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">产品类型</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="productType" value="sms" checked="checked">短信
									</label> <label class="btn btn-default"> <input type="radio"
										name="productType" value="mms">彩信
									</label>&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" color="red">添加之后不能修改</font>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">产品类别</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="productClass" value="行业" checked="checked">行业
									</label> <label class="btn btn-default"> <input type="radio"
										name="productClass" value="营销">营销
									</label>&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" color="red">添加之后不能修改</font>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">产品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="productName"
									id="productName">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="description"
									id="description">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">移动通道</label>
							<div class="col-sm-10">
								<select name="cmccChannelId" id="cmccChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${cmccChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">联通通道</label>
							<div class="col-sm-10">
								<select name="unicomChannelId" id="unicomChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${unicomChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">电信通道</label>
							<div class="col-sm-10">
								<select name="telecomChannelId" id="telecomChannelId"
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
							<label class="col-sm-2 control-label">国际通道</label>
							<div class="col-sm-10">
								<select name="intlChannelId" id="intlChannelId"
									class="selectpicker show-tick form-control" placeholder="全部通道"
									data-width="98%" data-live-search="true">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${intlChannels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">签名要求</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="isSign" value="yes" checked="checked">必须
									</label> <label class="btn btn-default"> <input type="radio"
										name="isSign" value="no">不必须
									</label>&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" color="red">是否必须有签名，假如设置为“必须”，如果信息不带签名，则拒绝提交</font>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button"
									class="btn btn-default btn-block add_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">修改产品</h4>
					<p style="font-size: 12px; color: #ff5907;">分省发送不支持跨标签保存</p>
				</div>
				<div class="ibox-content tabbable tabs-tabs">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#A" data-toggle="tab">基础设置</a></li>
						<li><a href="#B" data-toggle="tab">分省移动</a></li>
						<li><a href="#C" data-toggle="tab">分省联通</a></li>
						<li><a href="#D" data-toggle="tab">分省电信</a></li>
					</ul>
					<div class="hr-line-dashed"></div>
					<div class="tab-content">
						<div class="tab-pane active" id="A">
							<form id="editForm" method="post" class="form-horizontal">
								<input type="hidden" name="id" id="editId">
								<div class="form-group">
									<label class="col-sm-2 control-label">产品属性</label>
									<div class="col-sm-10">
										<input id="productProperty" type="text" disabled="disabled"
											class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">产品名称</label>
									<div class="col-sm-10">
										<input type="text" name="productName" id="editProductName"
											class="form-control">
										<input type="hidden" id="oldEditProductName"
											class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">描述</label>
									<div class="col-sm-10">
										<input type="text" name="description" id="editDescription"
											class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">移动通道</label>
									<div class="col-sm-10">
										<select name="cmccChannelId" id="editCmccChannelId"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select><font size="2" color="red">修改通道需要重新配置分省设置</font>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">联通通道</label>
									<div class="col-sm-10">
										<select name="unicomChannelId" id="editUnicomChannelId"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select><font size="2" color="red">修改通道需要重新配置分省设置</font>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">电信通道</label>
									<div class="col-sm-10">
										<select name="teltcomChannelId" id="editTelecomChannelId"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select><font size="2" color="red">修改通道需要重新配置分省设置</font>
									</div>
								</div>
								<div class="form-group">
							<label class="col-sm-2 control-label">国际通道</label>
								<div class="col-sm-10">
									<select name="intlChannelId" id="editIntlChannelId"
										class="selectpicker show-tick form-control" placeholder="全部通道"
										data-width="98%" data-live-search="true">
										<option value="">全部通道</option>
										<c:forEach var="s" items="${intlChannels }">
											<option value="${s.channelId}">${s.channelId}:${s.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">签名要求</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="isSign" value="yes">必须
											</label> <label class="btn btn-default"> <input type="radio"
												name="isSign" value="no">不必须
											</label>&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" color="red">是否必须有签名，假如设置为“必须”，如果信息不带签名，则拒绝提交</font>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">产品状态</label>
									<div class="col-sm-10">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="productStatus" value="normal">正常
											</label> <label class="btn btn-default"> <input type="radio"
												name="productStatus" value="pause">禁用
											</label> <label class="btn btn-default"> <input type="radio"
												name="productStatus" value="deleted">删除
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-2">
										<button type="button" class="btn btn-primary btn-block"
											id="edit_saveBtn">保存</button>
									</div>
									<div class="col-sm-2 col-sm-offset-1">
										<button type="button"
											class="btn btn-default btn-block edit_backBtn">返回</button>
									</div>
								</div>
							</form>
						</div>
						<div class="tab-pane" id="B"
							style="height: 500px; overflow: auto;">
							<form id="cmccForm" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-2 control-label">上海</label>
									<div class="col-sm-4">
										<select id="cmccShangHai" 
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccShangHaiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">云南</label>
									<div class="col-sm-4">
										<select id="cmccYunNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccYunNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">内蒙古</label>
									<div class="col-sm-4">
										<select id="cmccNeiMengGu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccNeiMengGuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">北京</label>
									<div class="col-sm-4">
										<select id="cmccBeiJing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccBeiJingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">吉林</label>
									<div class="col-sm-4">
										<select id="cmccJiLin"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccJiLinContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">四川</label>
									<div class="col-sm-4">
										<select id="cmccSiChuan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccSiChuanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">天津</label>
									<div class="col-sm-4">
										<select id="cmccTianJin"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccTianJinContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">宁夏</label>
									<div class="col-sm-4">
										<select id="cmccNingXia"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccNingXiaContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">安徽</label>
									<div class="col-sm-4">
										<select id="cmccAnHui"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccAnHuiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">山东</label>
									<div class="col-sm-4">
										<select id="cmccShanDong"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccShanDongContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">山西</label>
									<div class="col-sm-4">
										<select id="cmccShanXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccShanXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">广东</label>
									<div class="col-sm-4">
										<select id="cmccGuangDong"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccGuangDongContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">广西</label>
									<div class="col-sm-4">
										<select id="cmccGuangXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccGuangXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">新疆</label>
									<div class="col-sm-4">
										<select id="cmccXinJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccXinJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">江苏</label>
									<div class="col-sm-4">
										<select id="cmccJiangSu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccJiangSuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">江西</label>
									<div class="col-sm-4">
										<select id="cmccJiangXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccJiangXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">河北</label>
									<div class="col-sm-4">
										<select id="cmccHeBei"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccHeBeiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">河南</label>
									<div class="col-sm-4">
										<select id="cmccHeNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccHeNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">浙江</label>
									<div class="col-sm-4">
										<select id="cmccZheJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccZheJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">海南</label>
									<div class="col-sm-4">
										<select id="cmccHaiNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccHaiNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">湖北</label>
									<div class="col-sm-4">
										<select id="cmccHuBei"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccHuBeiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">湖南</label>
									<div class="col-sm-4">
										<select id="cmccHuNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccHuNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">甘肃</label>
									<div class="col-sm-4">
										<select id="cmccGanSu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccGanSuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">福建</label>
									<div class="col-sm-4">
										<select id="cmccFuJian"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccFuJianContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">西藏</label>
									<div class="col-sm-4">
										<select id="cmccXiZang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccXiZangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">贵州</label>
									<div class="col-sm-4">
										<select id="cmccGuiZhou"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccGuiZhouContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">辽宁</label>
									<div class="col-sm-4">
										<select id="cmccLiaoNing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccLiaoNingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">重庆</label>
									<div class="col-sm-4">
										<select id="cmccChongQing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccChongQingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">陕西</label>
									<div class="col-sm-4">
										<select id="cmccSX"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccSXContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">青海</label>
									<div class="col-sm-4">
										<select id="cmccQingHai"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccQingHaiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">黑龙江</label>
									<div class="col-sm-4">
										<select id="cmccHeiLongJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${cmccChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="cmccHeiLongJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-2">
										<button type="button" class="btn btn-primary btn-block" id="cmcc_btn">保存</button>
									</div>
									<div class="col-sm-2 col-sm-offset-1">
										<button type="button"
											class="btn btn-default btn-block edit_backBtn">返回</button>
									</div>
								</div>
							</form>
						</div>
						<div class="tab-pane" id="C"
							style="height: 500px; overflow: auto;">
							<form id="unicomForm" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-2 control-label">上海</label>
									<div class="col-sm-4">
										<select id="unicomShangHai"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomShangHaiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">云南</label>
									<div class="col-sm-4">
										<select id="unicomYunNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomYunNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">内蒙古</label>
									<div class="col-sm-4">
										<select id="unicomNeiMengGu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomNeiMengGuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">北京</label>
									<div class="col-sm-4">
										<select id="unicomBeiJing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomBeiJingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">吉林</label>
									<div class="col-sm-4">
										<select id="unicomJiLin"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomJiLinContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">四川</label>
									<div class="col-sm-4">
										<select id="unicomSiChuan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomSiChuanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">天津</label>
									<div class="col-sm-4">
										<select id="unicomTianJin"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomTianJinContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">宁夏</label>
									<div class="col-sm-4">
										<select id="unicomNingXia"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomNingXiaContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">安徽</label>
									<div class="col-sm-4">
										<select id="unicomAnHui"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomAnHuiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">山东</label>
									<div class="col-sm-4">
										<select id="unicomShanDong"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomShanDongContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">山西</label>
									<div class="col-sm-4">
										<select id="unicomShanXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomShanXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">广东</label>
									<div class="col-sm-4">
										<select id="unicomGuangDong"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomGuangDongContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">广西</label>
									<div class="col-sm-4">
										<select id="unicomGuangXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomGuangXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">新疆</label>
									<div class="col-sm-4">
										<select id="unicomXinJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomXinJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">江苏</label>
									<div class="col-sm-4">
										<select id="unicomJiangSu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomJiangSuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">江西</label>
									<div class="col-sm-4">
										<select id="unicomJiangXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomJiangXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">河北</label>
									<div class="col-sm-4">
										<select id="unicomHeBei"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomHeBeiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">河南</label>
									<div class="col-sm-4">
										<select id="unicomHeNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomHeNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">浙江</label>
									<div class="col-sm-4">
										<select id="unicomZheJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomZheJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">海南</label>
									<div class="col-sm-4">
										<select id="unicomHaiNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomHaiNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">湖北</label>
									<div class="col-sm-4">
										<select id="unicomHuBei"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomHuBeiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">湖南</label>
									<div class="col-sm-4">
										<select id="unicomHuNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomHuNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">甘肃</label>
									<div class="col-sm-4">
										<select id="unicomGanSu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomGanSuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">福建</label>
									<div class="col-sm-4">
										<select id="unicomFuJian"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomFuJianContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">西藏</label>
									<div class="col-sm-4">
										<select id="unicomXiZang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomXiZangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">贵州</label>
									<div class="col-sm-4">
										<select id="unicomGuiZhou"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomGuiZhouContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">辽宁</label>
									<div class="col-sm-4">
										<select id="unicomLiaoNing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomLiaoNingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">重庆</label>
									<div class="col-sm-4">
										<select id="unicomChongQing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomChongQingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">陕西</label>
									<div class="col-sm-4">
										<select id="unicomSX"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomSXContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">青海</label>
									<div class="col-sm-4">
										<select id="unicomQingHai"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomQingHaiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">黑龙江</label>
									<div class="col-sm-4">
										<select id="unicomHeiLongJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${unicomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="unicomHeiLongJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-2">
										<button type="button" class="btn btn-primary btn-block" id="unicom_btn">保存</button>
									</div>
									<div class="col-sm-2 col-sm-offset-1">
										<button type="button"
											class="btn btn-default btn-block edit_backBtn">返回</button>
									</div>
								</div>
							</form>
						</div>
						<div class="tab-pane" id="D"
							style="height: 500px; overflow: auto;">
							<form id="telecomForm" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-2 control-label">上海</label>
									<div class="col-sm-4">
										<select id="telecomShangHai"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomShangHaiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">云南</label>
									<div class="col-sm-4">
										<select id="telecomYunNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomYunNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">内蒙古</label>
									<div class="col-sm-4">
										<select id="telecomNeiMengGu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomNeiMengGuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">北京</label>
									<div class="col-sm-4">
										<select id="telecomBeiJing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomBeiJingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">吉林</label>
									<div class="col-sm-4">
										<select id="telecomJiLin"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomJiLinContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">四川</label>
									<div class="col-sm-4">
										<select id="telecomSiChuan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomSiChuanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">天津</label>
									<div class="col-sm-4">
										<select id="telecomTianJin"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomTianJinContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">宁夏</label>
									<div class="col-sm-4">
										<select id="telecomNingXia"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomNingXiaContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">安徽</label>
									<div class="col-sm-4">
										<select id="telecomAnHui"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomAnHuiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">山东</label>
									<div class="col-sm-4">
										<select id="telecomShanDong"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomShanDongContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">山西</label>
									<div class="col-sm-4">
										<select id="telecomShanXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomShanXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">广东</label>
									<div class="col-sm-4">
										<select id="telecomGuangDong"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomGuangDongContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">广西</label>
									<div class="col-sm-4">
										<select id="telecomGuangXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomGuangXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">新疆</label>
									<div class="col-sm-4">
										<select id="telecomXinJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomXinJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">江苏</label>
									<div class="col-sm-4">
										<select id="telecomJiangSu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomJiangSuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">江西</label>
									<div class="col-sm-4">
										<select id="telecomJiangXi"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomJiangXiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">河北</label>
									<div class="col-sm-4">
										<select id="telecomHeBei"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomHeBeiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">河南</label>
									<div class="col-sm-4">
										<select id="telecomHeNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomHeNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">浙江</label>
									<div class="col-sm-4">
										<select id="telecomZheJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomZheJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">海南</label>
									<div class="col-sm-4">
										<select id="telecomHaiNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomHaiNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">湖北</label>
									<div class="col-sm-4">
										<select id="telecomHuBei"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomHuBeiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">湖南</label>
									<div class="col-sm-4">
										<select id="telecomHuNan"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomHuNanContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">甘肃</label>
									<div class="col-sm-4">
										<select id="telecomGanSu"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomGanSuContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">福建</label>
									<div class="col-sm-4">
										<select id="telecomFuJian"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomFuJianContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">西藏</label>
									<div class="col-sm-4">
										<select id="telecomXiZang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomXiZangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">贵州</label>
									<div class="col-sm-4">
										<select id="telecomGuiZhou"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomGuiZhouContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">辽宁</label>
									<div class="col-sm-4">
										<select id="telecomLiaoNing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomLiaoNingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">重庆</label>
									<div class="col-sm-4">
										<select id="telecomChongQing"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomChongQingContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">陕西</label>
									<div class="col-sm-4">
										<select id="telecomSX"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomSXContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">青海</label>
									<div class="col-sm-4">
										<select id="telecomQingHai"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomQingHaiContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">黑龙江</label>
									<div class="col-sm-4">
										<select id="telecomHeiLongJiang"
											class="selectpicker show-tick form-control"
											placeholder="全部通道" data-width="98%" data-live-search="true">
											<option value="">全部通道</option>
											<c:forEach var="s" items="${telecomChannels }">
												<option value="${s.channelId}">${s.channelId}:${s.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-6">
										<input id="telecomHeiLongJiangContent" type="text" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-2">
										<button type="button" class="btn btn-primary btn-block" id="telecom_btn">保存</button>
									</div>
									<div class="col-sm-2 col-sm-offset-1">
										<button type="button"
											class="btn btn-default btn-block edit_backBtn">返回</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="Details" tabindex="-3" role="dialog"
		aria-labelledby="desc" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="desc">描述</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-bordered table-hover">
						<tr>
							<td>内容</td>
							<td class="AutoNewline"><p id="Details_content"></p></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="linkAccount" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:700px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		关联帐户
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody" style="height:400px;overflow:auto;">
	                    <table id="tLinkAccount" class="table table-bordered table-hover">
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
		<div class="popup_box" style="width: 320px;height: 180px">
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
	src="${pageContext.request.contextPath}/js/product/productPageList.js"></script>
</html>