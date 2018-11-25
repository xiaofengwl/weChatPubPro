package com.xfwl.token;

import com.xfwl.event.EventManager;
import com.xfwl.message.MessageUtil;
import com.xfwl.message.handler.MessageHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/**
 * 和微信服务器建立联系
 * @author Jason
 *
 */
public class WXServletToken extends HttpServlet {
	public static Logger log = Logger.getLogger(WXServletToken.class);

	public void destroy() {
		super.destroy();
	}

	/**
	 * doGet 和微信官方服务接通
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 获得请求的token数据
		 */
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		log.info("##开始校验签名##");
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		log.info("##校验签名通过##");
		out.flush();
		out.close();
	}

	public void doPost2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("请求进入");
		String result = "";
		try {
			log.info("开始构造消息");

			Map<String, String> map = MessageUtil.parseXml(request);

			String msgType = ((String) map.get("MsgType")).toString();

			log.info("开始构造消息");
			if ("TEXT,IMAGE,VOICE,VIDEO,MUSIC,NEWS".contains(msgType
					.toUpperCase())) {
				new MessageHandler().gotoHandler(map, request, response);
			} else if ("EVENT".contains(msgType.toUpperCase())) {
				new EventManager().gotoHandler(map, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("发生异常：" + e.getMessage());
		}
	}

	public void init() throws ServletException {
	}
}
