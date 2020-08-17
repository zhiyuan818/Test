<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="redisKey" id="searchkey"
						placeholder="key(模糊)" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="modelName"
						id="searchmodelName" placeholder="模块名称(模糊)" />
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
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
				<button id="btn_del" type="button" class="btn btn-default"
					style="display: none">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>强制更新
				</button>
			</div>
		</div>
		<table id="mytab" class="table table-hover"
			style="word-wrap: break-word"></table>
	</div>
	
	<div class="addBody"
		style="width: 100%; display: none; position: absolute; top: 10px">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>新增</h5>
				</div>
				<div class="ibox-content">
					<form id="addForm" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">key</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="redisKey"
									id="redisKey" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">数据类型</label>
							<div class="col-sm-4">
			                   <select id="redisType" name="redisType" class="form-control">
			                    	<option value ="hash">hash</option>
			                    	<option value ="list">list</option>
			                    	<option value ="zset">zset</option>
			                    </select>
			                 </div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">最小阈值</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="minimum"
									id="minimum" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">最大阈值</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="maximum"
									id="maximum" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">周期</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="timeLimit"
									id="timeLimit" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">是否打印日志</label>
							<div class="col-sm-4">
			                   <select id="printLog" name="printLog" class="form-control">
			                    	<option value ="no">不打印</option>
			                    	<option value ="yes">打印</option>
			                    </select>
			                 </div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">实体类</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="variant"
									id="variant" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">端口名称</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="redisName"
									id="redisName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模块名称</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="modelName"
									id="modelName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">加载名称</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="mapKey"
									id="mapKey" />
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
					<h5>修改</h5>
				</div>
				<div class="ibox-content">
					<form id="editForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="id" id="editId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">key</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="redisKey"
									id="editredisKey" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">数据类型</label>
							<div class="col-sm-4">
			                   <select id="editredisType" name="redisType" class="form-control">
			                    	<option value ="hash">hash</option>
			                    	<option value ="list">list</option>
			                    	<option value ="zset">zset</option>
			                    </select>
			                 </div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">最小阈值</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="minimum"
									id="editminimum" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">最大阈值</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="maximum"
									id="editmaximum" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">周期</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="timeLimit"
									id="edittimeLimit" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">是否打印日志</label>
							<div class="col-sm-4">
			                   <select id="editprintLog" name="printLog" class="form-control">
			                    	<option value ="no">不打印</option>
			                    	<option value ="yes">打印</option>
			                    </select>
			                 </div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">实体类</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="variant"
									id="editvariant" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">端口名称</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="redisName"
									id="editredisName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">模块名称</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="modelName"
									id="editmodelName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">加载名称</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="mapKey"
									id="editmapKey" />
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
	<input type="hidden" id="buttons" value="${buttons }">

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/loadConfig/loadConfigPageList.js"></script>
</html>