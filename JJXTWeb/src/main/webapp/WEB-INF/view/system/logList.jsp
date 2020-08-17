<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
.td {
	width: 100px;
}
.AutoNewline {
	Word-break: break-all; /*必须*/
}
</style>
<jsp:include page="/head.html"></jsp:include>
</head>
<body>
	<div class="tableBody">
		<div class="panel panel-default" style="margin-bottom: 0px;">
			<div class="panel-body form-group" style="margin-bottom: 0px;">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="userName"
						id="userName" placeholder="用户名称（精确）" />
				</div>
				<div class="col-sm-2">
					<select name="bussiness" id="bussiness"
						class="selectpicker show-tick form-control" placeholder="业务类型"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">业务类型</option>
						<option value="菜单">菜单</option>
						<option value="用户">用户</option>
						<option value="审核模板">审核模板</option>
						<option value="配置">配置</option>
						<option value="账户管理">账户管理</option>
						<option value="客户管理">客户管理</option>
						<option value="审核模板审核">审核模板审核</option>
						<option value="GWLog">GWLog</option>
						<option value="LOGICLOG">LOGICLOG</option>
						<option value="敏感词">敏感词</option>
						<option value="审核词">审核词</option>
						<option value="一级黑">一级黑</option>
						<option value="二级黑">二级黑</option>
						<option value="三级黑">三级黑</option>
						<option value="REP">REP</option>
						<option value="投诉">投诉</option>
						<option value="产品">产品</option>
						<option value="优先级">优先级</option>
						<option value="白名单">白名单</option>
						<option value="实号库">实号库</option>
						<option value="通道">通道</option>
						<option value="上行分拣">上行分拣</option>
						<option value="对帐销帐">对帐销帐</option>
						<option value="短信模板">短信模板</option>
						<option value="信息库">信息库</option>
						<option value="签名报备">签名报备</option>
						<option value="扩展号">扩展号</option>
						<option value="黑签名">黑签名</option>
						<option value="号段">号段</option>
						<option value="位置信息">位置信息</option>
						<option value="动态扩展">动态扩展</option>
						<option value="报警管理">报警管理</option>
						<option value="加载redis配置">加载redis配置</option>
						<option value="模板管理">模板管理</option>
						<option value="黑名单配置">黑名单配置</option>
						<option value="号码策略">号码策略</option>
						<option value="角色管理">角色管理</option>
						<option value="重发管理">重发管理</option>
						<option value="签名分配扩展">签名分配扩展</option>
						<option value="通道模板">通道模板</option>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="operation" id="operation"
						class="selectpicker show-tick form-control" placeholder="操作类型"
						data-width="98%" data-first-option="false" required
						data-live-search="true">
						<option value="">操作类型</option>
						<option value="login">登陆</option>
						<option value="outLogin">注销</option>
						<option value="add">新增</option>
						<option value="addBatch">批量新增</option>
						<option value="update">修改</option>
						<option value="updateBatch">批量修改</option>
						<option value="delete">删除</option>
						<option value="deleteBatch">批量删除</option>
						<option value="clean">清理</option>
						<option value="import">导入</option>
						<option value="export">导出</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="newData" id="newData"
						placeholder="修改后数据（模糊）" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="oldData" id="oldData"
						placeholder="修改前数据(模糊)" />
				</div>
				
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="startTime"
						id="startTime" placeholder="开始时间" />
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control time" name="endTime"
						id="endTime" placeholder="结束时间" />
				</div>
				<div class="col-sm-2 pull-right">
					<button class="btn btn-primary" id="search_btn">搜索</button>
					<button class="btn btn-default" id="search_back">重置</button>
				</div>

			</div>
		</div>
		<table id="mytab" class="table table-hover"></table>
	</div>

	<div class="modal fade" id="Details" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">业务日志详情</h4>
				</div>
				<div class="modal-body" id="menuBody">
					<table class="table table-bordered table-hover">
						<tr>
							<td>内容</td>
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
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/system/bizlog.js"></script>
</html>