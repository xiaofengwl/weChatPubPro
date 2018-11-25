package com.xfwl.event.ievent;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器的AOP规则定义
 * @author Jason
 *
 */
public interface IEventAopHandler {
	/**
	 * 分配控制前的处理
	 * @param paramMap	请求参数
	 * @param paramHttpServletRequest	请求对象
	 * @param paramHttpServletResponse	响应对象
	 */
	public abstract void beforeGotoHander(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse);
	/**
	 * 分配控制后的处理
	 * @param paramMap	请求参数
	 * @param paramHttpServletRequest	请求对象
	 * @param paramHttpServletResponse	响应对象
	 */
	public abstract void afterGotoHander(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse);
}
