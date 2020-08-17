<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警策略管理</title>
<style type="text/css">
.td {
	width: 200px;
}

.table {
	table-layout: fixed;
}
</style>
</head>
<jsp:include page="/head.html"></jsp:include>	
<body>
	<div class="tableBody">
	
	
	<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				
				<div class="col-sm-2">
					<select name="modelId" id="modelId"
						class="selectpicker show-tick form-control" placeholder="全部模块"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部模块</option>
						<c:forEach var="s" items="${models }">
							<option value="${s.modelId}">${s.modelId}:${s.modelName }</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-sm-2">
					<select name="typeId" id="typeId"
						class="selectpicker show-tick form-control" placeholder="全部类型"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部类型</option>
						<c:forEach var="s" items="${types }">
							<option value="${s.typeId}">${s.typeId}:${s.type }</option>
						</c:forEach>
					</select>
				</div>
				

				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
	
	
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
			</button>
		</div>
	</div>

	<div class="modal fade" id="addBody" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">添加报警策略配置</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label">模块名称：</label>
						<div class="col-sm-4">
							<select name="modelId" id="modelId" class="selectpicker show-tick form-control" placeholder="请选择" data-width="98%" data-live-search="true">
							<option value="">请选择</option>
							<c:forEach var="models" items="${models }">
							<option value="${models.modelId }">${models.modelId }:${models.modelName }</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">报警类型：</label>
						<div class="col-sm-6">
							<select name="typeId" id="typeId" class="selectpicker show-tick form-control" placeholder="请选择" data-width="98%" data-live-search="true">
							<option value="">请选择</option>
							<c:forEach var="types" items="${types }">
							<option value="${types.typeId }">${types.typeId }:${types.type }</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">等级：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="level" id="level" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务开始时间：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="startTime" id="startTime" placeholder="例如（0000）"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务结束时间：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="endTime" id="endTime" placeholder="例如（2400）"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">沉默期：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="cycle" id="cycle" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">忽略值：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="ignore" id="ignore" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">策略：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="strategy" id="strategy" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">状态</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input type="radio"
										name="status" value="normal" checked="checked">正常
									</label> <label class="btn btn-default"> <input type="radio"
										name="status" value="paused">暂停
								</div>
							</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">描述：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="description" id="description" />
						</div>
					</div>
					
					<div class="hr-line-dashed"></div>
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
	
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close edit_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="edit">修改报警类型</h4>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">id</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="configId" id="editId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">模块名称:</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" readonly="readonly" name="modelId" id="editModelId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">报警类型：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" readonly="readonly" name="typeId" id="editTypeId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">等级：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" readonly="readonly" name="level" id="editLevel" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务开始时间：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="startTime" id="editsTime" placeholder="例如（0000）"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务结束时间：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="endTime" id="editeTime" placeholder="例如（2400）"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">沉默期：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="cycle" id="editCycle" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">忽略值：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="ignore" id="editIgnore" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">策略：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="strategy" id="editStrategy" />
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-2 control-label">状态</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active"> 
							<input type="radio" name="editStatus" id="exitNormal" value="normal">正常
						</label>
						<label class="btn btn-default"> 
							<input type="radio" name="editStatus" id="exitPaused" value="paused">暂停
						</label> 
					</div>
							</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">描述：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="description" id="editDescription" />
						</div>
					</div>
					
					<div class="hr-line-dashed"></div>
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
	
	<div class="modal fade" id="typeDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	        <div class="modal-dialog" style="width:500px">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel">
	                      		类型详情
	                    </h4>
	                </div>
	                <div class="modal-body" id="menuBody">
	                    <table class="table table-bordered table-hover">
	                    	<tr>
	                    		<td class="td" width="30px">报警类型</td>
	                    		<td><span id="type"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td" width="30px">是否使用模板</td>
	                    		<td><span id="isModel"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td" width="30px">模板</td>
	                    		<td><span id="typeModel"></span></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="td" width="30px">模板描述</td>
	                    		<td><span id="description"></span></td>
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
	src="${pageContext.request.contextPath}/js/alarm/alarmConfigList.js"></script>
</html>