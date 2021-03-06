<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.td {
	width: 100px;
}
.table {
	table-layout: fixed;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
				<div class="panel-body form-group" style="margin-bottom: 0px;">
					<div class="col-sm-2">
						<input type="text" class="form-control" name="seg" id="searchSeg" placeholder="号段(精确)"/>
					</div>
					<div class="col-sm-2">
				        <select id="searchCarrier" name="carrier" class="form-control">
				        		<option value ="">请选择运营商</option>
	                  			<option value ="cmcc">移动</option>
	                  			<option value ="unicom">联通</option>
	                  			<option value ="telecom">电信</option>
	                 	</select>
					</div>
					<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
					</div>
				</div>
				
			</div>
	
		<table id="mytab" class="table table-hover"></table>
		<div id="toolbar" class="btn-group pull-right" style="margin-right: 20px">
		         <button id="btn_add" type="button" class="btn btn-default">
		             <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		         </button>
		         <button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
			</div>
	</div>

	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>新增号段</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">号段</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="seg" id="seg" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">运营商</label>
							<div class="col-sm-10">
								<select name="carrier" id="carrier"
									class="selectpicker show-tick form-control">
									<option value ="">请选择运营商</option>
		                  			<option value ="cmcc">移动</option>
		                  			<option value ="unicom">联通</option>
		                  			<option value ="telecom">电信</option>
								</select>
							</div>
						</div>
						
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="add_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block"
									id="add_backBtn">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="changeBody"
		style="width: 100%; height: 500px; display: none; position: absolute; top: 10px;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>修改号段</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">号段</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="seg" id="editSeg" readonly="readonly"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">运营商</label>
							<div class="col-sm-10">
								<select name="carrier" id="editCarrier"
									class="selectpicker show-tick form-control">
		                  			<option value ="cmcc">移动</option>
		                  			<option value ="unicom">联通</option>
		                  			<option value ="telecom">电信</option>
								</select>
							</div>
						</div>
						
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="edit_saveBtn">保存</button>
							</div>
							<div class="col-sm-2 col-sm-offset-1">
								<button type="button" class="btn btn-default btn-block"
									id="edit_backBtn">返回</button>
							</div>
						</div>
					</form>
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
	<input type="hidden" name="buttons" value="${buttons }" id="buttons" />

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/numSegment/numSegment.js"></script>
</html>