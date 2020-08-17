<%@ page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${platformName}</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/ace.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/ace-rtl.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/ace-skins.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-table.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrapValidator.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/body.css" />	
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/list/pageList.css" />
</head>

<body>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<div class="sidebar" id="sidebar">
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large"
						style="background-color: #d1e3f6">
						<a class="iframeurl" href="index.jsp"
							style="text-decoration: none;"><font color="${platformNameCocol}"
							size="4px">${platformName}</font></a>
					</div>
					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span> <span class="btn btn-info"></span>

						<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
					</div>
				</div>
				<!-- #sidebar-shortcuts -->
				<div id="menu_style" class="menu_style"></div>
				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left"
						data-icon1="icon-double-angle-left"
						data-icon2="icon-double-angle-right"></i>
				</div>
			</div>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a class="iframeurl"
							href="index.jsp">首页</a></li>
						<li class="active"><span class="Current_page iframeurl"></span></li>
						<li class="active" id="parentIframe"><span
							class="parentIframe iframeurl"></span></li>
						<li class="active" id="parentIfour"><span
							class="parentIfour iframeurl"></span></li>
					</ul>
					<ul class="pull-right" style="margin-right: 30px">
						<li class="dropdown"><a data-toggle="dropdown"
							class="dropdown-toggle" href="#"> <span class="username">欢迎：${ucenter.chineseName}，角色：${ucenter.title}</span>
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu extended logout">
								<li><a id="personal_btn"><i></i>个人信息</a></li>
								<li><a onclick="outlogin()"><i class="icon_key_alt"></i>退出登录</a></li>
							</ul></li>
					</ul>
				</div>

				<iframe id="iframe"
					style="border: 0; width: 100%; background-color: #FFF;"
					name="iframe" frameborder="0" src="home.jsp"> </iframe>
			</div>
		</div>
	</div>
	<div class="modal fade" id="changeBody" tabindex="-1" role="dialog"
		aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog" style="width: 720px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close add_backBtn"
						data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="add">修改个人信息</h4>
				</div>
				<div class="tab-pane active">
					<form id="personalForm" method="post" class="form-horizontal">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="id" id="id" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group" style="margin-top: 20px;">
							<label class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-8">
								<input type="text" disabled="disabled" class="form-control"
									name="managerName" id="managerName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" name="showPassword"
									id="showPassword" /> <input type="hidden" class="form-control"
									name="managerPassword" id="managerPassword" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control"
									name="showRepeatPassword" id="showRepeatPassword" />
							</div>
						</div>

						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">中文名称</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="chineseName"
									id="chineseName" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">角色</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" disabled="disabled"
									name="title" id="title" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-2 col-sm-offset-3">
								<button type="button" class="btn btn-primary btn-block"
									id="update_saveBtn">修改</button>
							</div>
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button"
									class="btn btn-default btn-block add_backBtn" id="edit_backBtn">返回</button>
							</div>
						</div>

					</form>
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
	</div>
	
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/sidebar-menu.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/ace-extra.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/typeahead-bs2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/layer/layer.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/laydate/laydate.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.nicescroll.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/dist/echarts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrapValidator.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap-table.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.md5.js"></script>
<script type="text/javascript">
	$(function () {
		$(function() {
			var id=${ucenter.id};
			if(id==null){
				alert("登录超时");
			}else{
				$.ajax({
					url:"ucenterMenu/findUcenterMenuByUcenterManagerId.action",
					data:{id:id},
					dataType:"json",
					type:"post",
					success:function (result){
						var showlist = $("<ul class=\"sidebar-menu nav nav-list\"></ul>");
				        isFirstMenu=result.ucenterMenu.length;
				        showall(result.ucenterMenu, showlist);
				        $("#menu_style").append(showlist);
					}
				});
			}
		})
		
					$.each($(".submenu"), function() {
						var $aobjs = $(this).children("li");
						var rowCount = $aobjs.size();
						var divHeigth = $(this).height();
						$aobjs.height(divHeigth / rowCount);
					});
					//初始化宽度、高度

					$("#main-container").height($(window).height() - 46);
					$("#iframe").height($(window).height() - 80);

					$(".sidebar").height($(window).height());
					var thisHeight = $("#nav_list").height(
							$(window).outerHeight() - 133);
					$(".submenu").height();
					$("#nav_list").children(".submenu").css("height", thisHeight);

					//当文档窗口发生改变时 触发  
					$(window).resize(
							function() {
								$("#main-container")
										.height($(window).height() - 46);
								$("#iframe").height($(window).height() - 80);
								$(".sidebar").height($(window).height());

								var thisHeight = $("#nav_list").height(
										$(window).outerHeight() - 133);
								$(".submenu").height();
								$("#nav_list").children(".submenu").css("height",
										thisHeight);
							});
					$(document).on('click', '.iframeurl', function() {
						var cid = $(this).attr("name");
						var cname = $(this).attr("title");
						$("#iframe").attr("src", cid).ready();
						$("#Bcrumbs").attr("href", cid).ready();
						$(".Current_page a").attr('href', cid).ready();
						$(".Current_page").attr('name', cid);
						$(".Current_page").html(cname).css({
							"color" : "#333333",
							"cursor" : "default"
						}).ready();
					});

		//注销登录js
		
		$("#personal_btn").click(function(){
			$("#changeBody").modal();
			var id=${ucenter.id};
			var managerName="${ucenter.managerName}";
			var chineseName='${ucenter.chineseName}';
			var title='${ucenter.title}';
			$('#id').val(id);
			$('#managerName').val(managerName);
			$('#chineseName').val(chineseName);
			$('#title').val(title);
			
		});
		updateValidator();
		$('#update_saveBtn').click(function(){
			mark=true;
			$('#personalForm').bootstrapValidator('validate');
			if($("#personalForm").data('bootstrapValidator').isValid()){	
			$("#update_saveBtn").attr("disabled",true);
			$('.popup_de .btn_cancel').css('display', 'inline-block');
			$('.popup_de .show_msg').text('确定要修改吗？');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click',function(){
			if(mark){
				var password=$("#showRepeatPassword").val();
				if(password==""||password==null||password===undefined){
				}else{
					$("#managerPassword").val($.md5(password));
				}
					
				var _info = $('#personalForm').serialize();
				data= decodeURIComponent(_info,true);
				$.post("ucenterManager/updateUcenterManager.action",
					data,
					function(data){
						if(data.result>0){
							$('.popup_de .show_msg').text('修改成功,请重新登录！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',function() {
								$("#personalForm")[0].reset();
								$("#changeBody").modal('hide');
								$("#update_saveBtn").attr("disabled", false);
						    	//修改页面表单重置
						    	$("#personalForm").data('bootstrapValidator').destroy();
						        $('#personalForm').data('bootstrapValidator', null);
						        updateValidator();
						        $('.popup_de').removeClass('bbox');
						        outlogin();
							})
							
						}else {
							$('.popup_de .show_msg').text('修改失败，请重新修改！');
							$('.popup_de .btn_cancel').css('display','none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click',
									function() {
								$("#update_saveBtn").attr("disabled",false);
								$('.popup_de').removeClass('bbox');
							})
						}
				 });
				mark=false;
			}
			})
		}
	});
		// 返回按钮
		$('#edit_backBtn').click(
				function() {
					$("#personalForm")[0].reset();
					$("#changeBody").modal('hide');
					$("#update_saveBtn").attr("disabled", false);
					$("#personalForm").data('bootstrapValidator').destroy();
					$('#personalForm').data('bootstrapValidator', null);
					updateValidator();
				});
		// 弹出框取消按钮事件
		$('.popup_de .btn_cancel').click(function() {
			$('.popup_de').removeClass('bbox');
			$("#update_saveBtn").attr("disabled", false);
			$("#personalForm").data('bootstrapValidator').destroy();
	        $('#personalForm').data('bootstrapValidator', null);
	        updateValidator();
		})
		// 弹出框关闭按钮事件
		$('.popup_de .popup_close').click(function() {
			$('.popup_de').removeClass('bbox');
			$("#update_saveBtn").attr("disabled", false);
			$("#personalForm").data('bootstrapValidator').destroy();
	        $('#personalForm').data('bootstrapValidator', null);
	        updateValidator();
		})
	})
	function outlogin() {
			window.location.href = "${pageContext.request.contextPath}/ucenterManager/outLogin.action";
		}
	function showall(menu_list, parent) {
        for (var menu in menu_list) {
            if (menu_list[menu].children.length > 0) {
                var li = $("<li class=\"home\"></li>");
                if(isFirstMenu==0){
                    li = $("<li class=\"home\"></li>");
                }else{
                    li = $("<li class=\"treeview home\"></li>");
                    isFirstMenu=isFirstMenu-1;
                }
                $(li).append("<a name=\'"+menu_list[menu].menuLink+"\' href='#' title=\'"+menu_list[menu].text+"\' class=\"iframeurl\" ><i class=\'"+menu_list[menu].iconClass+"\'></i> <span class='menu-text'><font size='3px'>"+menu_list[menu].text+"</font></span></a>");
                var nextParent=$("<ul class=\"treeview-menu submenu\"></ul>");
                $(nextParent).appendTo(li);
                $(li).appendTo(parent);
                showall(menu_list[menu].children, nextParent);
            }
            else {
                $("<li><a name=\'"+menu_list[menu].menuLink+"\' href='#' title=\'"+menu_list[menu].text+"\' class=\"iframeurl\" ><i class=\'"+menu_list[menu].iconClass+"\'></i><font size='3px'>"+menu_list[menu].text+"</font></a></li>").appendTo(parent);
            }
        }
    }
	function updateValidator() {
		$('#personalForm').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				showPassword: {
	                validators: {
	                	notEmpty : {
							message : '密码不能为空'
						},
						regexp: {
	                    	regexp :  /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!_~.@#$%^&*?])[a-zA-Z!_~.@#$%^&*?0-9]{8,16}$/g,
							message : '密码8-16位，包含数字、字母、特殊符号'
	         		   }
	                }
	            },
	            showRepeatPassword: {
	         	   validators: {
	         		  	notEmpty : {
							message : '密码不能为空'
						},
	                    identical: {
	                        field: 'showPassword',
	                        message: '两次密码不相同'
	                    }
	                }
	            },
				chineseName : {
					validators : {
						notEmpty : {
							message : '中文姓名不能为空'
						}
					}
				}
			}
		});
	}
</script>
</html>