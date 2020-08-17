package com.jjxt.ssm.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jjxt.ssm.utils.Constant;

public class MenuActionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String model = request.getParameter("model");
		if(model!=null){
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<Integer, String> attribute = (Map<Integer, String>) session.getAttribute(Constant.SERVER_MENU_SESSION);
			String button = attribute.get(model);
			session.setAttribute(Constant.SERVER_MENUBUTTON_SESSION, button);
			return true;
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
