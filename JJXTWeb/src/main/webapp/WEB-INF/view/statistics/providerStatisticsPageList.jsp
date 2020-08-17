<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按运营商统计</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				
				<div class="col-sm-2">
					<select name="companyKey" id="companyKey"
						class="selectpicker show-tick form-control" placeholder="全部客户"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部客户</option>
						<c:forEach var="s" items="${companys }">
							<option value="${s.companyKey}">${s.id}:${s.companyKey }</option>
						</c:forEach>
					</select>
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
					<select name="sales" id="sales"
						class="selectpicker show-tick form-control" placeholder="全部销售"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部销售</option>
						<c:forEach var="s" items="${sales }">
							<option value="${s.sales}">${s.sales}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="provider" id="provider"
						class="selectpicker show-tick form-control" placeholder="运营商"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">运营商</option>
						<option value="cmcc">移动</option>
						<option value="unicom">联通</option>
						<option value="telecom">电信</option>
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
				<div class="col-sm-4">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active"> 
							<input type="radio" name="statisticType" value="no" checked="checked">合计
						</label>
						<label class="btn btn-default"> 
							<input type="radio" name="statisticType" value="yes">分天
						</label> 
					</div>
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		<table id="mytab" class="table table-hover"></table>
	</div>
	
	
	<div class="modal fade" id="appDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:700px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		账户详情
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody">
	                    <table class="table table-bordered table-hover">
	                    	<tr>
	                    		<td class="td">账户名称</td>
	                    		<td><span id="appName"></span></td>
	                    		<td class="td">账户状态</td>
	                    		<td><span id="appStatus"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">优先级</td>
	                    		<td><span id="priority"></span></td>
	                    		<td class="td">余额</td>
	                    		<td><span id="balance"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">测试模式</td>
	                    		<td><span id="testModel"></span></td>
	                    		<td class="td">默认签名</td>
	                    		<td><span id="defaultSign"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">结算类型</td>
	                    		<td><span id="payment"></span></td>
	                    		<td class="td">计费方式</td>
	                    		<td><span id="chargeBy"></span></td>
	                    	</tr>
	                    	<tr>
        			            <td class="td">单价</td>
	                    		<td><span id="price"></span></td>
        			            <td class="td">移动价格</td>
	                    		<td><span id="priceCmcc"></span></td>
	                    	</tr>
	                    	<tr>
        			            <td class="td">联通价格</td>
	                    		<td><span id="priceUnicom"></span></td>
        			            <td class="td">电信价格</td>
	                    		<td><span id="priceTelecom"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">创建时间</td>
	                    		<td><span id="createTime"></span></td>
	                    		<td class="td">移动通道</td>
	                    		<td><span id="cmccChannelName"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">联通通道</td>
	                    		<td><span id="unicomChannelName"></span></td>
	                    		<td class="td">电信通道</td>
	                    		<td><span id="telecomChannelName"></span></td>
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


</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/statistics/providerStatisticsPageList.js"></script>
</html>