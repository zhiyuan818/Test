package com.jjxt.ssm.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.utils.Constant;

public class ValidateFilter extends AbstractFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		UcenterManager ucenter = null;
		// 判断如果是login.jsp 放行
		String reurl = request.getRequestURI();
		String uri = reurl.substring(reurl.lastIndexOf("/") + 1);
		if ("".equals(uri) || "login.jsp".equals(uri) || "outLogin.action".equals(uri)
				|| "checkLoginName.action".equals(uri) || "adminLogin.action".equals(uri) || "noPower.jsp".equals(uri)
				|| uri.endsWith("png") || uri.endsWith("jpg") || uri.endsWith("img") || uri.endsWith("css")
				|| uri.endsWith("js")) {
			chain.doFilter(request, response);
		} else {
			// 下面是判断是否有session，也就是用户是否已登录状态;
			HttpSession session = request.getSession();
			ucenter = (UcenterManager) session.getAttribute(Constant.SERVER_USER_SESSION);
			String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI()))
					+ request.getContextPath();
			if (ucenter == null) {
				// 1:判断是否是ajax请求
				if (request.getHeader("x-requested-with") != null
						&& "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
					// 向http头添加 状态 sessionstatus
					response.setHeader("sessionstatus", "timeout");
					response.addHeader("loginPath", url + "/login.jsp");
					response.setStatus(403);
					// 向http头添加登录的url
					return;
				}
				response.sendRedirect(url + "/login.jsp");
				return;
			} else {
				chain.doFilter(request, response);
			}
		}
	}

}