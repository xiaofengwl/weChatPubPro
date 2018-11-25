package com.xfwl.message.handler;

import com.xfwl.message.MessageUtil;
import com.xfwl.message.handler.imsg.IMsgHandler;
import com.xfwl.tools.StringTool;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/**
 * 普通消息处理器
 * @author Jason
 *
 */
public class CommonMsgHandler extends AbsMsgHandler implements IMsgHandler {
	public static Logger log = Logger.getLogger(CommonMsgHandler.class);

	public void dealHandler(Map<String, String> reqMap,
			HttpServletRequest request, HttpServletResponse response) {
		String fromUserName = (String) reqMap.get("FromUserName");
		String toUserName = (String) reqMap.get("ToUserName");
		String content = (String) reqMap.get("Content");
		if ("1".equals(content)) {
			try {
				String pageUrl = "http://yx4ydh.natappfree.cc/WeChatPubAdrPro/index.jsp";
				log.info("开始跳转到指定界面：" + pageUrl);

				request.getRequestDispatcher(pageUrl)
						.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("【" + fromUserName + "】您好\n");
			sb.append("您提出的问题“" + content + "”太难了~o~\n小风回答不上来~");
			String result = MessageUtil.buildTextMessage(reqMap, sb.toString());
			if (StringTool.isEmptyOrNull(result)) {
				result = "未正确响应";
			}
			try {
				response.getWriter().println(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
