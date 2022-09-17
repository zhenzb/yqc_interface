package com.handongkeji.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class ContextUtil {
	private static ServletContext servletContext;
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		ContextUtil.context = context;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		ContextUtil.servletContext = servletContext;
	}

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
		    .getRequestAttributes()).getRequest();
		return request;
	}

	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}

//	public static final WebAgent getSessionAgent() {
//		HttpSession session = getSession();
//		WebAgent agent = (WebAgent) session.getAttribute(Constants.AGENT_SESSION);
//		return agent;
//	}

	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?"
		    + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}
}