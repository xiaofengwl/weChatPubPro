package com.xfwl.message.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.xfwl.event.EventManager;
import com.xfwl.message.handler.imsg.IMsgAopHandller;
import com.xfwl.message.handler.imsg.IMsgHandler;
/**
 * 消息处理器分发管理类
 * @author Jason
 *
 */
public class MessageHandler implements IMsgAopHandller{
	//日志对象
	public static Logger log = Logger.getLogger(MessageHandler.class);
	/**
	 * 根据事件类型分配到指定的处理器取处理
	 * @param reqMap	请求参数
	 * @param request	请求对象
	 * @param response	响应对象
	 */
	public void gotoHandler(Map<String, String> reqMap,
			HttpServletRequest request, HttpServletResponse response) {
		//【1】前处理
		beforeGotoHander(reqMap, request, response);
		//【2】处理中
		String msgType = ((String) reqMap.get("MsgType")).toString();
		IMsgHandler handler = null;
		if ("TEXT,IMAGE,VOICE,VIDEO,MUSIC,NEWS".contains(msgType.toUpperCase())) {
			handler = new CommonMsgHandler();
		} else {
			handler = new SpecialMsgHandler();
		}
		handler.setPubData(reqMap, request, response);
		handler.dealHandler(reqMap, request, response);
		//【3】处理后
		afterGotoHander(reqMap, request, response);
	}

	/**
	 * 分配控制器前的处理
	 * 可以记录微信用户的session数据
	 */
	public void beforeGotoHander(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		log.info("-----【message】--------->分配控制器-前-的处理<-------------");
		
	}

	/**
	 * 分配控制器后的处理
	 * 可以做日志记录，资源最后释放等
	 */
	public void afterGotoHander(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		log.info("-----【message】--------->分配控制器-后-的处理<-------------");
		
	}
}
