<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控统计数据</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body onload="load()">
	<section id="main-content">
		<section class="wrapper">
			<div style="padding:10px;clear: both;">
				<div class="col-sm-1">
					<select name="class" id="class" class="selectpicker show-tick form-control" data-width="98%" data-live-search="false">
						<option value="count">平台汇总</option>
						<option value="acc">账号</option>
						<option value="chan">通道</option>
					</select>
				</div>
				<input type="hidden" id="oldClass">
				<div class="col-sm-3">
					<select name="data" id="data" class="selectpicker show-tick form-control" title="请选择"  data-width="98%" multiple data-live-search="true">
				</select>
				</div>
				<input type="hidden" id="oldData">
				<div class="col-sm-1" id="div3" style="display: none">
					<select name="type" id="type" class="selectpicker show-tick form-control" data-width="98%" data-live-search="false">
						<option value="group">聚合</option>
						<option value="contrast">比较</option>
					</select>
				</div>
				<input type="hidden" id="oldType">
				<div class="col-sm-3">
					<select name="category" id="category" class="selectpicker show-tick form-control" title="请选择"  placeholder="全部数据"   data-width="98%" multiple  data-live-search="false">
					</select>
				</div>
				<input type="hidden" id="oldCategory">
				<div class="col-sm-2">
					<input type="text" id="selectTime" class="form-control" placeholder="查看时间（min）" value="30"/>
				</div>
				<input type="hidden" id="oldSelectTime">
				<div class="col-sm-2">
					<input value="查询"  type="button" class="btn btn-primary" id="btn_sub" />
					<input value="重置"  type="button" class="btn btn-close" id="btn_clo" />
				</div>
				<div id="psLine" style="height:650px;width: 100%;"></div>
			</div>
		</section>
	</section>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/echarts-all.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dataMonitoring/statisticChart.js"></script>
</html>