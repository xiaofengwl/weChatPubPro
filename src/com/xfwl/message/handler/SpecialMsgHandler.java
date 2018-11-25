package com.xfwl.message.handler;

import com.xfwl.message.handler.imsg.IMsgHandler;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 特殊消息处理器
 * @author Jason
 *
 */
public class SpecialMsgHandler extends AbsMsgHandler implements IMsgHandler {
	public void dealHandler(Map<String, String> reqMap,
			HttpServletRequest request, HttpServletResponse response) {
	}
}
