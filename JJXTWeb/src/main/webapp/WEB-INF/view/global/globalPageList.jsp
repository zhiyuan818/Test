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
		<table id="globaltab" class="table table-hover"
			style="word-wrap: break-word"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
			</button>
		</div>
	</div>

	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>添加配置</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">配置：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="key" id="key" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">值：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="val" id="val" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">备注：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="remark" id="remark" />
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
					<h5>修改配置</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">配置：</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="key" id="editKey" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">值：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="val" id="editVal" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">备注：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="remark" id="editRemark" />
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
	
		<div class="modal fade" id="Details" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:400px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel"> 备注详情  </h4>
	                </div>
	                <div class="modal-body" id="menuBody">
	                    <table class="table table-bordered table-hover">
	                    	<tr>
	                    		<td class="AutoNewline"><p id="Details_content"></p></td>
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
	src="${pageContext.request.contextPath}/js/global/globalPageList.js"></script>
</html>