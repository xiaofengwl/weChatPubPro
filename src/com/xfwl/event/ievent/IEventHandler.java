package com.xfwl.event.ievent;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface IEventHandler {
	/**
	 * 公共处理器事件处理方法
	 * @param paramMap	请求参数
	 * @param paramHttpServletRequest	请求对象
	 * @param paramHttpServletResponse	响应对象
	 */
	public abstract void dealHandler(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse);
	/**
	 * 初始化参数方法
	 * @param paramMap	请求参数
	 * @param paramHttpServletRequest	请求对象
	 * @param paramHttpServletResponse	响应对象
	 */
	public abstract void setPubData(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse);
}
