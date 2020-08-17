<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="multipart/form-data; charset=utf-8" />
<title>Insert title here</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: -20px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="sign" id="sign"
						placeholder="签名(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="minExt"
						id="minExt" placeholder="扩展号最小长度" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="maxExt"
						id="maxExt" placeholder="扩展号最大长度" />
				</div>
				<div class="col-sm-2">
					<select name="baobeiFlag" id="baobeiFlag"
						class="selectpicker show-tick form-control" placeholder="报备状态">
						<option value="">全部状态</option>
						<option value="yes">已报备</option>
						<option value="no">未报备</option>
						<option value="doing">报备中</option>
						<option value="fail">报备失败</option>
					</select>
				</div>
				<div class="col-sm-4">
					<select name="channelId" id="channelId"
						class="selectpicker show-tick form-control" placeholder="全部通道"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">全部通道</option>
						<c:forEach var="s" items="${channels }">
							<option value="${s.channelId}">${s.channelId}:${s.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="startTime"
						id="startTime" placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="endTime"
						id="endTime" placeholder="结束时间" />
				</div>
				<div class="col-sm-3 pull-right">
					&nbsp;&nbsp;
					<button class="btn btn-primary" id="search_btn">搜索</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn btn-default" id="search_back">重置</button>
				</div>
			</div>
			<div class="panel-body form-group" style="margin-bottom: -5px;">
				<div class="col-sm-3 pull-right">
					&nbsp;
					<button class="btn btn-primary" id="import_btn">批量导入</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn btn-primary" id="export_btn">批量导出</button>
				</div>

			</div>
		</div>
		<!--  	<div class="panel-body form-group" style="margin-bottom: 0px;">
			<div id="toolbar" class="btn-group pull-right"
				style="margin-right: 20px">
				<button id="btn_add" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
				</button>
			</div>
		</div>          -->
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
	</div>


	<div class="modal fade" id="importBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close import_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">批量导入</h4>
				</div>
				<div class="modal-body" id="add_body">
					<form id="importForm" method="post" class="form-horizontal"
						enctype="multipart/form-data">
						<div class="form-group">
							<label class="col-sm-2 control-label">导入模式</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="update" value="yes" checked="checked">添加更新
									</label> <label class="btn btn-default"> <input type="radio"
										name="update" value="no">全量同步
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">是否必须扩展</label>
							<div class="col-sm-10">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-default active"> <input
										type="radio" name="ext" value="yes" checked="checked">是
									</label> <label class="btn btn-default"> <input type="radio"
										name="ext" value="no">否
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">选择报备状态：</label>
							<div class="col-sm-4">
								<select name="baobeiFlag" id="importFlag"
									class="selectpicker show-tick form-control" placeholder="报备状态">
									<option value="">全部状态</option>
									<option value="yes">已报备</option>
									<option value="no">未报备</option>
									<option value="doing">报备中</option>
									<option value="fail">报备失败</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">选择通道:</label>
							<div class="col-sm-6">
								<select name="channelId" id="importchaId"
									class="selectpicker show-tick form-control" required
									data-live-search="true" placeholder="报备状态">
									<option value="">全部通道</option>
									<c:forEach var="s" items="${channels }">
										<option value="${s.channelId}">${s.channelId}:${s.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">上传文件:</label>
							<div class="col-sm-6">
								<input type="file" class="form-control" name="uploadFile"
									id="uploadFile" /> <font size="2" color="#ccc">注意：1.只支持Excel格式文件(文件后缀为xls或xlsx)<br>
									2.文件内容必须包括"签名"和"扩展号"<br>
									3.文件内容的签名不能带【】<br>
									4.文件内容第一行与最后一行不会导入，请放标题头，标题尾
								</font>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" class="btn btn-primary btn-block"
									id="import_saveBtn">下一步</button>
							</div>
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button"
									class="btn btn-default btn-block import_backBtn">返回</button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div>

	</div>


	<div class="modal fade" id="nextBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量导入</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-hover table-bordered">
						<thead id="headTab">
						</thead>
						<tbody id="detailsTab">

						</tbody>
					</table>
					<input type="hidden" id="hiddenChannelId"> <input
						type="hidden" id="hiddenUpdate"> <input type="hidden"
						id="hiddenExt"> <input type="hidden" id="hiddenBaobeiFlag">
					<input type="hidden" id="hiddenFile">
					<div class="modal-footer">
						<button type="button" class="btn btn-default import_backBtn"
							data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary" id="next_saveBtn">确定导入</button>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal fade" id="exportBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量导出</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-hover table-bordered">
						<tbody id="detailsTab">
							<tr>
								<td><input type="checkbox" name="param" value="sign">签名</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="extSrc">扩展号</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="channelName">通道名称</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="companyName">公司名称</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="classIfy">分类</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="baobeiFlag">报备状态</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="param" value="activeTime">时间</td>
							</tr>
						</tbody>
					</table>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="submit" class="btn btn-primary" id="export_saveBtn">确定导出</button>
					</div>
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
	src="${pageContext.request.contextPath}/js/signBaobei/signBaobeiList.js"></script>
</html>