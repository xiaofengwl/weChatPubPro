package com.xfwl.event;

import com.xfwl.event.ievent.IEventHandler;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 各种类型的处理器的抽象类
 * @author Jason
 *
 */
public abstract class AbsEventHandler implements IEventHandler {
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
