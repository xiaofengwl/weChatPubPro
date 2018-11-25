package com.xfwl.message.handler;

import com.xfwl.message.handler.imsg.IMsgHandler;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 消息处理器抽象类
 * @author Jason
 *
 */
public abstract class AbsMsgHandler implements IMsgHandler {
	protected Map<String, String> reqMap = null;
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;

	public void setPubData(Map<String, String> reqMap,
			HttpServletRequest request, HttpServletResponse response) {
		this.reqMap = reqMap;
		this.request = request;
		this.response = response;
	}
}
