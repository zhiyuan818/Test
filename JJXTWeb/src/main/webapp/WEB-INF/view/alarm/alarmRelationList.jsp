<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警配置管理</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#U" data-toggle="tab">用户</a></li>
					<li><a id="typePage" href="#T" data-toggle="tab">类型</a></li>
				</ul>
			<!-- <div class="hr-line-dashed"></div> --> <!-- 虚线 -->
			<div class="tab-content">
				<div class="tab-pane active" id="U">
					<div class="col-sm-2" style="margin: 10px 0px;">
					<select name="typeId" id="seltypeId" class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
						<c:forEach var="t" items="${types }">
							<option value="${t.typeId }">${t.typeId}:${t.type}</option>
						</c:forEach>
					</select>
					</div>
					<div class="col-sm-2" style="margin: 10px 0px;">
               			<input type="text" class="form-control" name="level"
						id="searchLevel1" placeholder="等级" />
					</div>
					<div class="col-sm-3" style="margin: 10px 0px;">
						<div class="btn-group" data-toggle="buttons">
							<label class="btn btn-default active"> 
								<input type="radio" name="selectType1" value="1" checked="checked">全部项
							</label>
							<label class="btn btn-default"> 
								<input type="radio" name="selectType1" value="2">报警项
							</label> 
						</div>
					</div>
					<div class="col-sm-1" style="margin: 10px 45px 10px -45px;">
						<button class="btn btn-primary" id="search_user">搜索</button>
					</div>
					<div class="col-sm-2 pull-right" style="margin: 10px 0px;">
						<button class="btn btn-default" id="update_user">保存配置</button>
					</div>
					<table id="userTab" class="table table-hover"></table>
				</div>
				
				<div class="tab-pane" id="T">
				<!-- 类型面板 -->
					<div class="col-sm-2" style="margin: 10px 0px;">
					<select name="userId" id="seluserId" class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
						<c:forEach var="u" items="${users }">
							<option value="${u.userId }">${u.userId}:${u.describe}</option>
						</c:forEach>
					</select>
					</div>
					<div class="col-sm-2" style="margin: 10px 0px;">
               			<input type="text" class="form-control" name="level"
						id="searchLevel2" placeholder="等级" />
				</div>
					<div class="col-sm-3" style="margin: 10px 0px;">
						<div class="btn-group" data-toggle="buttons">
							<label class="btn btn-default active"> 
								<input type="radio" name="selectType2" value="1" checked="checked">全部项
							</label>
							<label class="btn btn-default"> 
								<input type="radio" name="selectType2" value="2">报警项
							</label> 
						</div>
					</div>
					<div class="col-sm-1" style="margin: 10px 45px 10px -45px;">
						<button class="btn btn-primary" id="search_type">搜索</button>
					</div>
					<div class="col-sm-2 pull-right" style="margin: 10px 0px;">
						<button class="btn btn-default" id="update_type">保存配置</button>
					</div>
					<table id="typeTab" class="table table-hover"></table>
				</div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/alarm/alarmRelationList.js"></script>
</html>