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
						<header class="panel-heading"> 数据入库 </header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>数据类别</th>
									<th>SYNC:MO</th>
									<th>SYNC:MT</th>
									<th>SYNC:RPT</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>数量</td>
									<td>${syncMo }</td>
									<td>${syncMt }</td>
									<td>${syncRpt }</td>
								</tr>
							</tbody>
						</table>
					</section>
				</div>
				<div class="col-sm-6">
					<section class="panel">
						<header class="panel-heading">白名单</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>数据类别</th>
									<th>数量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>白名单</td>
									<td>${white }</td>
									<td>
										<button class="btn btn-default btn-xs" id="whiteBtn">查找</button>
										<input type="hidden" value="${white }">
									</td>
								</tr>
							</tbody>
						</table>
					</section>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<section class="panel">
						<header class="panel-heading no-border">黑名单</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>数据类别</th>
									<th>数量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>投诉黑名单</td>
									<td>${black1 }</td>
									<td>
										<button class="btn btn-default btn-xs" id="black1Btn">查找</button>
										<input type="hidden" value="${black1 }">
									</td>
								</tr>
								<tr>
									<td>回复黑名单</td>
									<td>${black2 }</td>
									<td>
										<button class="btn btn-default btn-xs" id="black2Btn">查找</button>
										<input type="hidden" value="${black2 }">
									</td>
								</tr>
								<tr>
									<td>普通黑名单</td>
									<td>${black3 }</td>
									<td>
										<button class="btn btn-default btn-xs" id="black3Btn">查找</button>
										<input type="hidden" value="${black3 }">
									</td>
								</tr>
								<tr>
									<td>关键黑名单</td>
									<td>${black5 }</td>
									<td>
										<button class="btn btn-default btn-xs" id="black5Btn">查找</button>
										<input type="hidden" value="${black5 }">
									</td>
								</tr>
							</tbody>
						</table>
					</section>
				</div>
				<div class="col-sm-6">
					<section class="panel">
						<header class="panel-heading">实号库</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>数据类别</th>
									<th>数量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>实号库</td>
									<td>${shunt }</td>
									<td>
										<button class="btn btn-default btn-xs" id="shuntBtn">查找</button>
										<input type="hidden" value="${shunt }">
									</td>
								</tr>
							</tbody>
						</table>
					</section>
					<section class="panel">
						<header class="panel-heading">携号转网</header>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>数据类别</th>
									<th>数量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>携号转网</td>
									<td>${numPortability }</td>
									<td>
										<button class="btn btn-default btn-xs" id="numPortabilityBtn">查找</button>
										<input type="hidden" value="${numPortability }">
									</td>
								</tr>
							</tbody>
						</table>
					</section>
				</div>
			</div>
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">通道优先级 </header>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>通道</th>
								<th>数量</th>
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
		</section>
	</section>
	<div class="modal fade" id="searchTab" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 50px; width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">查找</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<form id="Form">
						<input type="hidden" id="urgentChannelId"> <input
							type="hidden" id="urgentAppId">
						<table class="table table-bordered table-hover">
							<tr id="accountTr" style="display: none;">
								<td class="td">账号：</td>
								<td>
								<select name="appId" id="search_appId" class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
				                    <option value="">全部账户</option>
				                    <c:forEach var="s" items="${apps }">
				                        <option value="${s.id}">${s.id}:${s.appName }</option>
				                    </c:forEach>
				                </select>
								</td>
							</tr>
							<tr>
								<td class="td">手机号码：</td>
								<td><input type="text" class="form-control" id="phone">
								</td>
							</tr>
						</table>
						<input type="hidden" id="searchType" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn_priSub" class="btn btn-primary"
						data-dismiss="modal">查找</button>
					<button type="button" id="btn_close" class="btn btn-default"
						data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="Black2List" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 50px; width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">回复黑名单列表</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>账户</th>
								<th>手机号码</th>
							</tr>
						</thead>
						<tbody id="infoList">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="NumPortabilityList" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 50px; width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">携号转网列表</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>手机号码</th>
								<th>原运营商</th>
								<th>现运营商</th>
							</tr>
						</thead>
						<tbody id="npList">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="cleanList" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="margin-top: 50px; width: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">清理列表</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<label class="col-sm-3 control-label">账户</label>
					<div class="col-sm-9 role add">
						<select name="appIds" id="appIds"
							class="selectpicker show-tick form-control" data-width="98%"
							data-first-option="false" required data-live-search="true">
						</select> <input type="hidden" name="channelId" id="cleanChannelId">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn_cleanSub" class="btn btn-primary"
						data-dismiss="modal">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
	<input type="hidden" id="buttons" value="${buttons }">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dataMonitoring/dataMonitoringList.js"></script>
</html>