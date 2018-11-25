package com.xfwl.message.handler.imsg;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 消息处理器规则定义接口
 * @author Jason
 *
 */
public abstract interface IMsgHandler {
	public abstract void dealHandler(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse);

	public abstract void setPubData(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse);
}
