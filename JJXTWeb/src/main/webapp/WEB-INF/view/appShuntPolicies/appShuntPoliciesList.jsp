<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户优享</title>
<style type="text/css">
.td {
	width: 100px;
}
</style>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>

	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="appName"
						id="search_appName" placeholder="账户名称(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="ignoredProvinces"
						id="search_ignoredProvinces" placeholder="忽略省份(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="ignoredCarriers" id="search_ignoredCarriers"
						placeholder="忽略运营商(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="ignoredPackMin"
						id="search_ignoredPackMin" placeholder="包基数(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="ignoredPackHead"
						id="search_ignoredPackHead" placeholder="前基数(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="ignoredPackTail"
						id="search_ignoredPackTail" placeholder="后基数(精确)" />
				</div>

				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		<div class="panel-body form-group" style="margin-bottom: 0px;">
		<div id="toolbar" class="btn-group pull-right" style="margin-right: 20px">
		         <button id="btn_add" type="button" class="btn btn-default">
		             <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		         </button>
		         <button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
			</div>
		<table id="appShuntPoliciesTab" class="table table-hover"
			style="word-wrap: break-word"></table>
	</div>
	</div>
	
	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">新增账户优享</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" method="post" class="form-horizontal">
						
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-4">
								<select name="appId" id="appId"
									class="selectpicker show-tick form-control" placeholder="请选择账户"
									data-width="98%" data-first-option="false" required data-live-search="true" >
									<option value="">请选择账户</option>
									<c:forEach var="s" items="${apps }">
									<option value="${s.id}">${s.id}:${s.appName }</option>
								</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">包基数</label>
							<div class="col-sm-4 role add">
								<input type="text" class="form-control" name="ignoredPackMin"
									id="ignoredPackMin" />
							</div>
							<font size="2" color="#ff5907">每个包的条数小于等于包基数不优享,不填默认0</font>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">前基数</label>
							<div class="col-sm-4 role add">
								<input type="text" class="form-control" name="ignoredPackHead"
									id="ignoredPackHead" />
							</div>
							<font size="2" color="#ff5907">账户开始发送量小于等于前基数不优享,不填默认0</font>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">后基数</label>
							<div class="col-sm-4 role add">
								<input type="text" class="form-control" name="ignoredPackTail"
									id="ignoredPackTail" />
							</div>
							<font size="2" color="#ff5907">账户末尾发送量小于等于后基数不优享,不填默认0</font>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">优享忽略省份</label>
							<div class="col-sm-10 role add">
								<input name="ignoredProvinces" type="checkbox" value="北京" />北京
								 <input name="ignoredProvinces" type="checkbox" value="上海" />上海
								 <input name="ignoredProvinces" type="checkbox" value="天津" />天津
								 <input name="ignoredProvinces" type="checkbox" value="重庆" />重庆
								 <input name="ignoredProvinces" type="checkbox" value="黑龙江" />黑龙江
								 <input name="ignoredProvinces" type="checkbox" value="辽宁" />辽宁
								 <input name="ignoredProvinces" type="checkbox" value="吉林" />吉林
								 <input name="ignoredProvinces" type="checkbox" value="山东" />山东
								 <input name="ignoredProvinces" type="checkbox" value="安徽" />安徽
								 <input name="ignoredProvinces" type="checkbox" value="山西" />山西
								 <input name="ignoredProvinces" type="checkbox" value="河北" />河北
								 <input name="ignoredProvinces" type="checkbox" value="河南" />河南
								 <input name="ignoredProvinces" type="checkbox" value="江苏" />江苏
								 <input name="ignoredProvinces" type="checkbox" value="江西" />江西
								 <input name="ignoredProvinces" type="checkbox" value="浙江" />浙江
								 <input name="ignoredProvinces" type="checkbox" value="湖南" />湖南
								 <input name="ignoredProvinces" type="checkbox" value="湖北" />湖北
								 <input name="ignoredProvinces" type="checkbox" value="福建" />福建
								 <input name="ignoredProvinces" type="checkbox" value="广东" />广东
								 <input name="ignoredProvinces" type="checkbox" value="广西" />广西
								 <input name="ignoredProvinces" type="checkbox" value="宁夏" />宁夏
								 <input name="ignoredProvinces" type="checkbox" value="四川" />四川
								 <input name="ignoredProvinces" type="checkbox" value="内蒙古" />内蒙古
								 <input name="ignoredProvinces" type="checkbox" value="云南" />云南
								 <input name="ignoredProvinces" type="checkbox" value="新疆" />新疆
								 <input name="ignoredProvinces" type="checkbox" value="海南" />海南
								 <input name="ignoredProvinces" type="checkbox" value="甘肃" />甘肃
								 <input name="ignoredProvinces" type="checkbox" value="贵州" />贵州
								 <input name="ignoredProvinces" type="checkbox" value="西藏" />西藏
								 <input name="ignoredProvinces" type="checkbox" value="陕西" />陕西
								 <input name="ignoredProvinces" type="checkbox" value="青海" />青海
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">优享忽略运营商</label>
							<div class="col-sm-10 role add">
								<input name="ignoredCarriers" type="checkbox" value="cmcc" />移动 <input
									name="ignoredCarriers" type="checkbox" value="unicom"/>联通 <input
									name="ignoredCarriers" type="checkbox" value="telecom" />电信
							</div>
						</div>
						<div class="form-group">
                    		<label class="col-sm-2 control-label">优享比例</label>
                    		<div class="col-sm-4">
								<input type="text" class="form-control" name="appShuntLevel" id="appShuntLevel" value="-1" maxlength="6"/>
	                    	</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-sm-2 control-label">优享内容</label>
                    		<div class="col-sm-5">
								<textarea rows="3" cols="50" name="content" id="content"></textarea>
	                    	</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-sm-2 control-label">省份/内容比例</label>
                    		<div class="col-sm-4">
								<input type="text" class="form-control" name="percent" id="percent" value="0" maxlength="6"/>
	                    	</div>
                    	</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">是否同步到子账户</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default"> <input type="radio"
										name="isSyncSubApp" value="true">同步
									</label> <label class="btn btn-default active"> <input
										type="radio" name="isSyncSubApp" value="false"
										checked="checked">不同步
									</label>
								</div>
							</div>
						</div>
						
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
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改账户优享</h4>
				</div>
				<div class="modal-body">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">appId</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="appId" id="editAppId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-4">
								<input id="editAppName" name="appName" type="text" disabled="disabled"
									class="form-control">
							</div>
							</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">包基数</label>
							<div class="col-sm-4 role add">
								<input type="text" class="form-control" name="ignoredPackMin"
									id="editIgnoredPackMin" />
							</div>
							<font size="2" color="#ff5907">每个包的条数小于等于包基数不优享,不填默认0</font>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">前基数</label>
							<div class="col-sm-4 role add">
								<input type="text" class="form-control" name="ignoredPackHead"
									id="editIgnoredPackHead" />
							</div>
							<font size="2" color="#ff5907">账户开始发送量小于等于前基数不优享,不填默认0</font>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">后基数</label>
							<div class="col-sm-4 role add">
								<input type="text" class="form-control" name="ignoredPackTail"
									id="editIgnoredPackTail" />
							</div>
							<font size="2" color="#ff5907">账户末尾发送量小于等于后基数不优享,不填默认0</font>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">优享忽略省份</label>
							<div class="col-sm-10 role add"  id="ignoredProvincesDiv">
								<input name="ignoredProvinces" type="checkbox" value="北京" />北京
								 <input name="ignoredProvinces" type="checkbox" value="上海" />上海
								 <input name="ignoredProvinces" type="checkbox" value="天津" />天津
								 <input name="ignoredProvinces" type="checkbox" value="重庆" />重庆
								 <input name="ignoredProvinces" type="checkbox" value="黑龙江" />黑龙江
								 <input name="ignoredProvinces" type="checkbox" value="辽宁" />辽宁
								 <input name="ignoredProvinces" type="checkbox" value="吉林" />吉林
								 <input name="ignoredProvinces" type="checkbox" value="山东" />山东
								 <input name="ignoredProvinces" type="checkbox" value="安徽" />安徽
								 <input name="ignoredProvinces" type="checkbox" value="山西" />山西
								 <input name="ignoredProvinces" type="checkbox" value="河北" />河北
								 <input name="ignoredProvinces" type="checkbox" value="河南" />河南
								 <input name="ignoredProvinces" type="checkbox" value="江苏" />江苏
								 <input name="ignoredProvinces" type="checkbox" value="江西" />江西
								 <input name="ignoredProvinces" type="checkbox" value="浙江" />浙江
								 <input name="ignoredProvinces" type="checkbox" value="湖南" />湖南
								 <input name="ignoredProvinces" type="checkbox" value="湖北" />湖北
								 <input name="ignoredProvinces" type="checkbox" value="福建" />福建
								 <input name="ignoredProvinces" type="checkbox" value="广东" />广东
								 <input name="ignoredProvinces" type="checkbox" value="广西" />广西
								 <input name="ignoredProvinces" type="checkbox" value="宁夏" />宁夏
								 <input name="ignoredProvinces" type="checkbox" value="四川" />四川
								 <input name="ignoredProvinces" type="checkbox" value="内蒙古" />内蒙古
								 <input name="ignoredProvinces" type="checkbox" value="云南" />云南
								 <input name="ignoredProvinces" type="checkbox" value="新疆" />新疆
								 <input name="ignoredProvinces" type="checkbox" value="海南" />海南
								 <input name="ignoredProvinces" type="checkbox" value="甘肃" />甘肃
								 <input name="ignoredProvinces" type="checkbox" value="贵州" />贵州
								 <input name="ignoredProvinces" type="checkbox" value="西藏" />西藏
								 <input name="ignoredProvinces" type="checkbox" value="陕西" />陕西
								 <input name="ignoredProvinces" type="checkbox" value="青海" />青海
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">优享忽略运营商</label>
							<div class="col-sm-10 role add" id="ignoredCarriersDiv">
								<input name="ignoredCarriers" type="checkbox" value="移动" />移动 <input
									name="ignoredCarriers" type="checkbox" value="联通" />联通 <input
									name="ignoredCarriers" type="checkbox" value="电信" />电信
							</div>
						</div>
						<div class="form-group">
                    		<label class="col-sm-2 control-label">优享比例</label>
                    		<div class="col-sm-4">
								<input type="text" class="form-control" name="appShuntLevel" id="editAppShuntLevel" maxlength="6"/>
	                    	</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-sm-2 control-label">优享内容</label>
                    		<div class="col-sm-5">
                    		<textarea rows="3" cols="40" name="content" id="editContent"></textarea>
	                    	</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-sm-2 control-label">省份/内容比例</label>
                    		<div class="col-sm-4">
								<input type="text" class="form-control" name="percent" id="editPercent" maxlength="6"/>
	                    	</div>
                    	</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">是否同步到子账户</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default"> <input type="radio"
										name="isSyncSubApp" value="true">同步
									</label> <label class="btn btn-default"> <input
										type="radio" name="isSyncSubApp" value="false">不同步
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
								<button type="button" class="btn btn-default btn-block edit_backBtn"
									id="edit_backBtn">返回</button>
							</div>
						</div>
					</form>
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
	src="${pageContext.request.contextPath}/js/appShuntPolicies/appShuntPoliciesList.js"></script>
</html>