<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.td {
	width: 100px;
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>

<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="supplierKey"
						id="search_supplierKey" placeholder="供应商简称(模糊)" />
				</div>
				<div class="col-sm-2">
					<select name="head" id="search_head"  class="selectpicker show-tick form-control" data-width="98%" data-live-search="true">
               			<option value="">商务</option>
						<c:forEach var="s" items="${heads}">
							<option value="${s.chineseName}">${s.chineseName}</option>
						</c:forEach>
               		</select>
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		
		<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div id="toolbar" class="btn-group pull-right"
				style="margin-right: 20px">
				<button id="btn_add" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
				</button>
				<button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
				</button>
			</div>
		</div>
		<table id="mytab" class="table table-hover"></table>
	</div>
	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>新增通道供应商</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">供应商全称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="supplierName"
									id="supplierName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">供应商简称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="supplierKey"
									id="supplierKey" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">商务人员</label>
							<div class="col-sm-4">
								<select class="form-control" id="head" name="head">
									<option value="">请选择商务人员</option>
									<c:forEach var="s" items="${heads}">
										<option value="${s.chineseName}">${s.chineseName}</option>
									</c:forEach>
								</select>
							</div>
							<p class="help-block" style="font-size: 14px; color: #ff5907;">新增公司前，需要先新增商务人员</p>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">公司类型</label>
							<div class="col-sm-4">
								<select class="form-control" id="supplierType" name="supplierType">
									<option value="">请选择公司类型</option>
									<option value="direct">直客</option>
									<option value="agent">代理商</option>
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
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>修改通道供应商</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<input type="hidden" name="id" id="editId">
						<div class="form-group">
							<label class="col-sm-2 control-label">供应商名称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="supplierName"
									id="editSupplierName" />
								<input type="hidden" id="oldEditSupplierName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">供应商简称</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="supplierKey"
									id="editSupplierKey" />
								<input type="hidden" id="oldEditSupplierKey" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">商务人员</label>
							<div class="col-sm-4">
								<select class="form-control" id="editHead" name="head">
									<option value="">请选择商务人员</option>
									<c:forEach var="s" items="${heads}">
										<option value="${s.chineseName}">${s.chineseName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">公司类型</label>
							<div class="col-sm-4">
								<select class="form-control" id="editSupplierType" name="supplierType">
									<option value="">请选择公司类型</option>
									<option value="direct">直客</option>
									<option value="agent">代理商</option>
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
	<div class="modal fade" id="linkAccount" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:700px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		关联通道
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody" style="height:400px;overflow:auto;">
	                    <table id="tLinkAccount" class="table table-bordered table-hover">
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
			<span class="popup_close">×</span> <span class="show_msg"></span>
			<div class="btn_box">
				<div class="popup_btn btn_submit">确定</div>
				<div class="popup_btn btn_cancel">取消</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/channelSupplier/channelSupplier.js"></script>
</html>