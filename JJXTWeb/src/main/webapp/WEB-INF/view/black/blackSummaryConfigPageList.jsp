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
					<select name="appId" id="search_appId"
						class="selectpicker show-tick form-control" placeholder="全部账户"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部账户</option>
						<option value="0">全局</option>
						<c:forEach var="s" items="${apps }">
							<option value="${s.id}">${s.id}:${s.appName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
                    <select name="type" id="search_type"
                        class="selectpicker show-tick form-control" placeholder="类型" data-width="98%" data-live-search="true">
                        <option value="">类型</option>
                        <option value="pass">通过</option>
                        <option value="reject">驳回</option>
                    </select>
                </div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="level"
						id="search_level" placeholder="级别(精确)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="result"
						id="search_result" placeholder="状态码(精确)" />
				</div>
				<div class="col-sm-2">
                    <input type="text" class="form-control" name="sendFlag"
                        id="search_sendFlag" placeholder="标记(精确)" />
                </div>
                <div class="col-sm-2">
	                <select class="selectpicker show-tick form-control" id="search_status" name="status">
	                    <option value="">状态</option>
	                    <option value="normal">正常</option>
	                    <option value="pause">暂停</option>
	                </select>
	            </div>
	            <div class="col-sm-2">
                    <input type="text" class="form-control" name="phone"
                        id="search_phone" placeholder="手机号码(精确)" />
                </div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
		</div>
		<table id="mytab" class="table table-hover"></table>
		<div id="toolbar" class="btn-group pull-right"
			style="margin-right: 20px">
			<button id="btn_add" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
			<!-- <button id="btn_import" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>号码批量操作
            </button> -->
			<button id="btn_delete" type="button" class="btn btn-default"
                style="display: none;">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量删除
            </button>
            <button id="btn_pause" type="button" class="btn btn-default"
                style="display: none;">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量暂停
            </button>
            <button id="btn_start" type="button" class="btn btn-default"
                style="display: none;">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量启动
            </button>
		</div>
	</div>
	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>新增号码配置</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-10">
								<select name="appId" id="appId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账户</option>
									<option value="0">全局</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
                            <label class="col-sm-2 control-label">类型</label>
                            <div class="col-sm-10">
                                <select name="type" id="type"
                                    class="selectpicker show-tick form-control" placeholder="全部类型" data-width="98%">
                                    <option value="pass" selected>通过</option>
                                    <option value="reject">驳回</option>
                                </select>
                            </div>
                        </div>
						<div class="form-group">
							<label class="col-sm-2 control-label">策略级别</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="level"
									id="level" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">状态码</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="result"
									id="result" />
							</div>
						</div>
						<div class="form-group">
                            <label class="col-sm-2 control-label">标记</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="sendFlag"
                                    id="sendFlag" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">说明</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="remark"
                                    id="remark" />
                            <p>注意：1.策略级别必须是大于6的整数; 2.账户、类型和策略级别唯一</p>
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
					<h5>修改号码配置</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<input type="hidden" name="id" id="edit_id">
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-10">
								<select name="appId" id="edit_appId"
									class="selectpicker show-tick form-control" placeholder="全部账户"
									data-width="98%" data-first-option="false" required
									data-live-search="true">
									<option value="">全部账户</option>
									<option value="0">全局</option>
									<c:forEach var="s" items="${apps }">
										<option value="${s.id}">${s.id}:${s.appName }</option>
									</c:forEach>
								</select>
								<input type="hidden" id="edit_old_appId">
							</div>
						</div>
						<div class="form-group">
                            <label class="col-sm-2 control-label">类型</label>
                            <div class="col-sm-10">
                                <select name="type" id="edit_type"
                                    class="selectpicker show-tick form-control" placeholder="全部类型" data-width="98%">
                                    <option value="pass">通过</option>
                                    <option value="reject">驳回</option>
                                </select>
                            </div>
                            <input type="hidden" id="edit_old_type">
                        </div>
						<div class="form-group">
							<label class="col-sm-2 control-label">策略级别</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="level"
									id="edit_level" />
							</div>
							<input type="hidden" id="edit_old_level">
						</div>
						<div class="form-group">
                            <label class="col-sm-2 control-label">状态码</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="result"
                                    id="edit_result" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标记</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="sendFlag"
                                    id="edit_sendFlag" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">说明</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="remark"
                                    id="edit_remark" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">优先级</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="priority"
                                    id="edit_priority" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">策略状态</label>
                            <div class="col-sm-10">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-default"> <input type="radio"
                                        name="status" value="normal">正常
                                    </label> <label class="btn btn-default"> <input type="radio"
                                        name="status" value="paused">暂停
                                    </label>
                                </div>
                                <p>注意：1.策略级别必须是大于6的整数; 2.账户、类型和策略级别唯一</p>
								<p>执行顺序按照优先级从大到小</p>
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
	
	<div class="modal fade" id="importBody" tabindex="-1" role="dialog"
        aria-labelledby="add" aria-hidden="true">
        <div class="modal-dialog" style="width: 720px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="input">号码批量操作</h4>
                </div>
                <div class="modal-body" id="import_body">
                    <form id="importForm" method="post" class="form-horizontal"
                        enctype="multipart/form-data">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">选择级别:</label>
                            <div class="col-sm-4">
                                <select name="level" id="importlevel"
                                    class="selectpicker show-tick form-control" required
                                    data-live-search="true" placeholder="账户">
                                    <option value="">--请选择--</option>
                                    <c:forEach var="s" items="${levels }">
                                        <option value="${s.level}">${s.level}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">操作</label>
                            <div class="col-sm-10">
                                <select name="operation" id="importOperation"
                                    class="selectpicker show-tick form-control" placeholder="全部操作" data-width="98%">
                                    <option value="addBatch" selected>批量添加</option>
                                    <option value="delBatch">批量删除</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上传文件:</label>
                            <div class="col-sm-6">
                                <input type="file" class="form-control" name="uploadFile"
                                    id="uploadFile" /> <font size="2" color="#ccc">注意：<br>
                                    1.只支持txt格式文件(文件后缀为.txt)<br>
                                    2.手机号码用换行分隔（每行一个手机号）
                                </font>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-2 col-sm-offset-2">
                                <button type="button" class="btn btn-primary btn-block"
                                    id="import_saveBtn">确定导入</button>
                            </div>
                            <div class="col-sm-2 col-sm-offset-2">
                                <button type="button"
                                    class="btn btn-default btn-block " id="import_backBtn">返回</button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" id="linkLevel" tabindex="-1" role="dialog"
        aria-labelledby="add" aria-hidden="true">
        <div class="modal-dialog" style="width: 550px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="input">策略级别号码</h4>
                </div>
                <div class="modal-body" id="menuBody">
                    <form id="levelForm" method="post" class="form-horizontal">
                    
                        <div class="form-group">
                            <label class="col-sm-2 control-label">策略级别</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="level"
                                    id="level_name"  readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">数量</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="num"
                                    id="level_num"  readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">手机号码</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="phone"
                                    id="level_phone" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-2 col-sm-offset-2">
                                <button type="button" class="btn btn-primary btn-block"
                                    id="level_searchBtn">查询</button>
                            </div>
                            <div class="col-sm-2">
                                <button type="button" class="btn btn-primary btn-block"
                                    id="level_addBtn">添加</button>
                            </div>
                            <div class="col-sm-2">
                                <button type="button" class="btn btn-primary btn-block"
                                    id="level_delBtn">删除</button>
                            </div>
                            <div class="col-sm-2">
                                <button type="button"
                                    class="btn btn-default" id="level_backBtn">返回</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">结果</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="result"
                                    id="level_result"  readonly="readonly"/>
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
	<input type="hidden" name="buttons" value="${buttons }" id="buttons">
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/black/blackSummaryConfigPageList.js"></script>
</html>