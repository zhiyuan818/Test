<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
<div class="tableBody">
    <div class="panel panel-default" style="margin-bottom: 0px;">
        <div class="panel-body form-group" style="margin-bottom: 0px;">
            <div class="col-sm-2" id="searchCompanyIdDiv">
                <select name="companyId" id="searchCompanyId" class="selectpicker show-tick form-control"
                        data-width="98%" data-live-search="true">
                    <option value="">全部客户</option>
                    <c:forEach var="s" items="${complains }">
                        <option value="${s.id}">${s.id}:${s.companyKey }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2" id="searchAppIdDiv">
                <select name="appId" id="searchAppId" class="selectpicker show-tick form-control" data-width="98%"
                        data-live-search="true">
                    <option value="">全部账户</option>
                    <c:forEach var="s" items="${apps }">
                        <option value="${s.id}">${s.id}:${s.appName }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-4" id="searchChannelIdDiv"  style="display: none;">
                <select name="channelId" id="searchChannelId"
                        class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%"
                        data-live-search="true">
                    <option value="">全部通道</option>
                    <c:forEach var="channel" items="${channels}">
                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2">
               			<input type="text" class="form-control" name="status"
						id="searchStatus" placeholder="错误码" />
				</div>
            <div class="col-sm-2">
                <select class="selectpicker show-tick form-control" id="searchMsgType" name="msgType">
                    <option value="">短信类型</option>
                    <option value="all">所有</option>
                    <option value="bulk">营销</option>
                    <option value="notif">行业</option>
                    <option value="code">码类</option>
                </select>
            </div>
			<div class="col-sm-2">
                <select name="toChannelId" id="searchToChannelId"
                        class="selectpicker show-tick form-control" placeholder="跳转通道" data-width="98%"
                        data-live-search="true">
                    <option value="">跳转通道</option>
                    <c:forEach var="channel" items="${channels}">
                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-4">
				<div class="btn-group" data-toggle="buttons">
					<label class="btn btn-default active"> 
						<input type="radio" name="idType" value="account" checked="checked">账户
					</label>
					<label class="btn btn-default"> 
						<input type="radio" name="idType" value="channel">通道
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
    <div id="toolbar" class="btn-group pull-right" style="margin-right: 20px">
        <button id="btn_del" type="button" class="btn btn-default" style="display: none">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量删除
		</button>
        <button id="btn_add" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </button>
    </div>
</div>

<div class="modal fade" id="addBody" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">添加重发配置</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="addForm" method="post" class="form-horizontal">
					
					<div class="form-group">
						<label class="col-sm-2 control-label">重发方式</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> 
										<input type="radio" name="addIdType" value="account" checked="checked">账户
									</label>
									<label class="btn btn-default"> 
										<input type="radio" name="addIdType" value="channel">通道
									</label> 
								</div>
							</div>
					</div>
					
					<div class="form-group" id="addAppIdDiv">
						<label class="col-sm-2 control-label">账户：</label>
						<div class="col-sm-6">
							<select name="appId" id="addAppId" class="selectpicker show-tick form-control" data-width="98%"
			                        data-live-search="true">
			                    <option value="">全部账户</option>
			                    <c:forEach var="s" items="${apps }">
			                        <option value="${s.id}">${s.id}:${s.appName }</option>
			                    </c:forEach>
			                </select>
						</div>
					</div>
					<div class="form-group" id="addChannelDiv" style="display: none;">
						<label class="col-sm-2 control-label">通道：</label>
						<div class="col-sm-6">
							<select name="channelId" id="addChannelId"
			                        class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%"
			                        data-live-search="true">
			                    <option value="">全部通道</option>
			                    <c:forEach var="channel" items="${channels}">
			                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
			                    </c:forEach>
			                </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">错误码：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="status" id="addStatus" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">跳转通道：</label>
						<div class="col-sm-6">
							<select name="toChannelId" id="addToChannelId"
                        class="selectpicker show-tick form-control" placeholder="跳转通道" data-width="98%"
                        data-live-search="true">
                    <option value="">跳转通道</option>
                    <c:forEach var="channel" items="${chanNormal}">
                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
                    </c:forEach>
                </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">类型：</label>
						<div class="col-sm-6">
							<select class="selectpicker show-tick form-control" id="addMsgType" name="msgType">
			                    <option value="all">所有</option>
			                    <option value="bulk">营销</option>
			                    <option value="notif">行业</option>
			                    <option value="code">码类</option>
			                </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">忽略值：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="ignoreChanOrAcc" id="ignoreChanOrAcc" />
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
					<h4 class="modal-title" id="edit">修改账户</h4>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">id</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="id" id="editId" />
						</div>
					</div>
					<div class="form-group" style="display: none">
						<label class="col-sm-2 control-label">idType</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="idType" id="editIdType" />
						</div>
					</div>
					<div class="form-group" id="editAppIdDiv">
						<label class="col-sm-2 control-label">账户:</label>
						<div class="col-sm-6">
							<select name="appId" id="editAppId" class="selectpicker show-tick form-control" data-width="98%"
                        	data-live-search="true">
                   			 <option value="">全部账户</option>
                   			 <c:forEach var="s" items="${apps }">
                      			  <option value="${s.id}">${s.id}:${s.appName }</option>
                   			 </c:forEach>
               				 </select>
						</div>
					</div>
					<div class="form-group" id="editChannelDiv">
						<label class="col-sm-2 control-label">通道:</label>
						<div class="col-sm-6">
							<select name="channelId" id="editChannelId"
		                        class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%"
		                        data-live-search="true">
		                    <option value="">全部通道</option>
		                    <c:forEach var="channel" items="${channels}">
		                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
		                    </c:forEach>
		                </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">错误码：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="status" id="editStatus" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">短信类型:</label>
						<div class="col-sm-6">
							<select class="selectpicker show-tick form-control" id="editMsgType" name="msgType">
                   				 <option value="all">所有</option>
                    <option value="bulk">营销</option>
                    <option value="notif">行业</option>
                    <option value="code">码类</option>
                			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">要跳转的通道:</label>
						<div class="col-sm-6">
							<select name="toChannelId" id="editToChannelId"
                        class="selectpicker show-tick form-control" placeholder="跳转通道" data-width="98%"
                        data-live-search="true">
                    <option value="">跳转通道</option>
                    <c:forEach var="channel" items="${chanNormal}">
                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
                    </c:forEach>
                </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">忽略值：</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="ignoreChanOrAcc" id="editIgnoreChanOrAcc" />
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/reSend/reSendConfigList.js"></script>
</html>