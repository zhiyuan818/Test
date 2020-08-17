<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/china.js"></script>
<style>#china-map {width:900px; height: 600px;float: left}
		#provinceTab tr td {padding :0px 8px 0px 5px }	
</style>
<title>中国地图</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body onload="load()">
	<section id="main-content">
		<section class="wrapper">
			<div style="padding:10px;clear: both;">
				<div class="col-sm-2">
					<select name="class" id="class" class="selectpicker show-tick form-control" data-width="98%" data-live-search="false">
						<option value="平台">平台汇总</option>
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
				<div class="col-sm-1" id="div" style="display: none">
					<select name="type" id="type" class="selectpicker show-tick form-control" data-width="98%" data-live-search="false">
						<option value="group">聚合</option>
						<option value="contrast">比较</option>
					</select>
				</div>
				<input type="hidden" id="oldType">
				<div class="col-sm-2">
					<input value="查询"  type="button" class="btn btn-primary" id="btn_sub" />
					<input value="重置"  type="button" class="btn btn-close" id="btn_clo" />
				</div>
				<br>
				<div>
					<div class="col-lg-2" id="div1" style="width: 1120px;height:250px;"></div>	
					<div id="china-map"></div>
					<div style="float: left" >
				    	<table id="provinceTab" class="table table-hover table-bordered">
				    	</table>
    				</div>
				</div>	
			</div>
		</section>
	</section>
	<div class="modal fade" id="addBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">地图详情</h4>
				</div>
				<div class="modal-body" id="add_body">
					<table id="provinceTab" class="table table-hover">
					</table>
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
	src="${pageContext.request.contextPath}/js/map/chinaMap.js"></script>
</html>