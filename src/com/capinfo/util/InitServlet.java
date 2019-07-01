package com.capinfo.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capinfo.wdbl.service.ProcessInfoService;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = -7672417781132478273L;
	private static WebApplicationContext context;

	public void init() throws ServletException {
		context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		System.out.println("---------------------- 初始化成功！-----------------------------");
		
		/*获取流程信息*/
		ProcessInfoService processInfoService = (ProcessInfoService) context.getBean("processInfoService");
		processInfoService.initProcessConfig();
	}

	public static Object getBean(String id) {
		Object bean = context.getBean(id);
		return bean;
	}
}
