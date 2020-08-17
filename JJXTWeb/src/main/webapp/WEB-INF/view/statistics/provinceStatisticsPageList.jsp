<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按省份统计</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div class="col-sm-2"  id = "companyDiv"  style="display: none;">
					<select name="companyId" id="companyId"
						class="selectpicker show-tick form-control" placeholder="全部客户"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部客户</option>
						<c:forEach var="s" items="${companys }">
							<option value="${s.id}">${s.id}:${s.companyKey }</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-sm-2" id = "appDiv"  style="display: none;">
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
				<div class="col-sm-4" id = "channelDiv">
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
				<div class="col-sm-4">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active"> 
							<input type="radio" name="separateType" value="channel" checked="checked">分通道
						</label>
						<label class="btn btn-default"> 
							<input type="radio" name="separateType" value="app">分账户
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
<input type="hidden" name="buttons" value="${buttons }" id="buttons"/>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/statistics/provinceStatisticsPageList.js"></script>
</html>