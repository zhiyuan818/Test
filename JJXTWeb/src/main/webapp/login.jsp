<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	isELIgnored="false" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"
	rel="stylesheet" />
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
<script
	src="${pageContext.request.contextPath}/assets/js/ace-extra.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/layer/layer.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.md5.js"
	type="text/javascript"></script>
<title>短信综合管理信息平台</title>
<script type="text/javascript">
	//登录失败提示：用户名或者密码错误
	function checkLogin() {
		var check = document.getElementById("fail").value;
		if (check != "") {
			layer.alert('用户名或密码错误，请重新输入！！！', {
				title : '提示框',
				icon : 0,
			});
		}

	}
</script>
</head>

<body class="login-layout Reg_log_style" onload="checkLogin()">
	<input type="hidden" id="fail" value="${errorinfo}"></input>
	<%
		request.getSession().removeAttribute("errorinfo");
		ResourceBundle resource = ResourceBundle.getBundle("config");
		String platformName = "集客短信管理平台";
		try{
			platformName = new String(resource.getString("platformName").getBytes("ISO-8859-1"), "UTF-8");
		}catch(Exception e){
			out.print("<script>alert('config.properties缺少platformName参数');</script>"); 
		}
	%>
	<div class="logintop">
	</div>
	<div class="loginbody">
		<div class="login-container">
			<div class="center">
				<h1>
					<i class="icon-leaf green"></i> <span class="orange"><%=platformName%></span>
			    </h1>
				<h4 class="white">Enterprise SMS Management System</h4>
			</div>
			<div class="space-6"></div>
			<div class="position-relative">
				<div id="login-box" class="login-box widget-box no-border visible">
					<div class="widget-body">
						<div class="widget-main">
							<h4 class="header blue lighter bigger">
								<i class="icon-coffee green"></i> 欢迎登陆
							</h4>

							<div class="login_icon">
								<img src="images/login.png" />
							</div>
							<form name="form1" action="ucenterManager/adminLogin.action"
								method="post" onsubmit="return login()">
								<fieldset>
									<ul>
										<li class="frame_style form_error"><label
											class="user_icon"></label> <input name="managerName"
											placeholder="账号" type="text" id="username" /> <input
											type="hidden" name="managerPassword"></li>
										<li class="frame_style form_error"><label
											class="password_icon"></label> <input placeholder="密码"
											type="password" id="managerPassword" /></li>
									</ul>
									<div class="clearfix">
										<button type="submit"
											class="width-35 pull-right btn btn-sm btn-primary"
											id="login_btn" style="margin-right: 100px;">
											<i class="icon-key"></i> 登陆
										</button>
									</div>
									<div class="space-4"></div>
								</fieldset>
							</form>
							<div class="social-or-login center">
								<span class="bigger-110">提示</span>
							</div>
							<div class="social-login center">本网站系统不对IE8以下浏览器支持，请见谅。</div>
						</div>
					</div>
					<!-- /widget-body -->
				</div>
				<!-- /login-box -->
			</div>
			<!-- /position-relative -->
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$("input[type='text'],input[type='password']").blur(
							function() {
								var $el = $(this);
								var $parent = $el.parent();
								$parent.attr('class', 'frame_style')
										.removeClass(' form_error');
								if ($el.val() == '') {
									$parent.attr('class', 'frame_style')
											.addClass(' form_error');
								}
							});
					$("input[type='text'],input[type='password']").focus(
							function() {
								var $el = $(this);
								var $parent = $el.parent();
								$parent.attr('class', 'frame_style')
										.removeClass(' form_errors');
								if ($el.val() == '') {
									$parent.attr('class', 'frame_style')
											.addClass(' form_errors');
								} else {
									$parent.attr('class', 'frame_style')
											.removeClass(' form_errors');
								}
							});
				});
		function login() {
			var accountCode = $("#username").val();
			if (accountCode == "") {
				layer.alert('用户名不能为空！！！', {
					title : '提示框',
					icon : 0,
				});
				return false;
			}

			var password = $("#managerPassword").val();
			if (password == "") {
				layer.alert('密码不能为空！！！', {
					title : '提示框',
					icon : 0,
				});
				return false;
			}
			var pwd = $.md5(password);
			$("[name='managerPassword']").val(pwd);
			return true;
		}
		function browserRedirect() {
		    var sUserAgent = navigator.userAgent.toLowerCase();
		    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
		    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
		    var bIsAndroid = sUserAgent.match(/android/i) == "android";
		    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
		    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
		    	$("img").attr("src","");
		    }
		}
		browserRedirect();
	</script>
</body>
</html>

