<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按账户统计</title>
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
					<select name="saleAfter" id="saleAfter"
						class="selectpicker show-tick form-control" placeholder="全部客服"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部客服</option>
						<c:forEach var="s" items="${saleAfter }">
							<option value="${s.saleAfter}">${s.saleAfter}</option>
						</c:forEach>
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
				<div class="col-sm-3">
					<select name="productId" id="productId"
						class="selectpicker show-tick form-control" placeholder="全部产品"
						data-width="98%" data-first-option="false" required data-live-search="true">
						<option value="">全部产品</option>
						<c:forEach var="s" items="${products }">
							<option value="${s.id}">${s.id}:${s.productName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<select name="channelId" id="channelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<c:choose>
								<c:when test="${fn:contains(buttons,'显示优享通道')}">
									<option value="${s.channelId}">${s.channelId}:${s.name }</option>
								</c:when>
								<c:otherwise>
									<c:if test="${s.channelId != 347}">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active"> 
							<input type="radio" name="statisticType" value="no" checked="checked">合计
						</label>
						<label class="btn btn-default"> 
							<input type="radio" name="statisticType" value="yes">分天
						</label> 
						<label class="btn btn-default"> 
							<input type="radio" name="statisticType" value="chan">分通道
						</label> 
					</div>
				</div>
				
					<%-- <div class="col-sm-5">
						<div class="btn-group dateTypeDiv" data-toggle="buttons">
							<label class="btn btn-default active"> <input type="radio"
								name="dateType" value="day"  id="day" checked>日</label> <label class="btn btn-default"> <input type="radio"
								name="dateType" value="week"   id="week" >周</label> <label class="btn btn-default"> <input type="radio"
								name="dateType" value="month" id="month">月</label><label class="btn btn-default"> <input type="radio"
								name="dateType" value="year" id="year">年</label>
						</div>
					</div>

					<div class="col-sm-2">
						<div id="dayDiv" style="display:none">
							<input class="form-control dayValue"  id="dateValue" name="dateValue"  />
						</div>
						<div id="yearDiv" style="display:none">
							<input class="form-control yearValue"  id="dateValue" name="dateValue"  />
							<select name="dateValue" id="dateValue">
								<option value="">年</option>
								<c:forEach var="s" items="${yearArr}">
									<option value="${s.val}">${s.tag}</option>
								</c:forEach>
							</select>
						</div>
						<div id="monthDiv" style="display:none">
							<input class="form-control monthValue"  id="dateValue" name="dateValue"  />
						</div>
						<div id="weekDiv" style="display:none">
							<input class="form-control weekValue"  id="dateValue" name="dateValue"  />
						</div>
						
					</div>

					<div class="col-sm-5">
						<div class="btn-group groupDateDiv" data-toggle="buttons">
							<label class="btn btn-default"> <input type="radio"
								name="groupDate" value="yes">分天</label> <label class="btn btn-default active"> <input type="radio"
								name="groupDate" value="no" checked>合计</label>
						</div>
					</div> --%>

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
	                    		<td class="td">产品</td>
	                    		<td><span id="productIdName"></span></td>
	                    		<td class="td">移动通道</td>
	                    		<td><span id="cmccChannelName"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">联通通道</td>
	                    		<td><span id="unicomChannelName"></span></td>
	                    		<td class="td">电信通道</td>
	                    		<td><span id="telecomChannelName"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">创建时间</td>
	                    		<td><span id="createTime"></span></td>
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
		
		<div class="modal fade" id="chanDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:700px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		通道详情
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody">
	                    <table class="table table-bordered table-hover">
	                    	<tr>
	                    		<td class="td">通道名称</td>
	                    		<td><span id="channelName"></span></td>
	                    		<td class="td">通道状态</td>
	                    		<td><span id="channelStatus"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td">通道速度</td>
	                    		<td><span id="channelSpeed"></span></td>
	                    		<td class="td">通道价格</td>
	                    		<td><span id="channelPrice"></span></td>
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
<input type="hidden" name="buttons" value="${buttons }" id="buttons"/>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/statistics/appStatisticsPageList.js"></script>
</html>