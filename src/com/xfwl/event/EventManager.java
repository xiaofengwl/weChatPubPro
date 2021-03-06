package com.xfwl.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.xfwl.event.ievent.IEventAopHandler;
import com.xfwl.event.ievent.IEventHandler;
import com.xfwl.message.handler.CommonMsgHandler;

/**
 * 事件处理管理类【可以理解为总控制器类】
 * 理论上：AOP可以无限制第分配下去，但是要考虑实际情况，合理即可，不可滥用
 * @author Jason
 * 
 */
public class EventManager implements IEventAopHandler{
	//日志对象
	public static Logger log = Logger.getLogger(EventManager.class);
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
		String eventCode = ((String) reqMap.get("Event")).toString();
		IEventHandler handler = null;
		if ("VIEW".equals(eventCode.toUpperCase())) {
			handler = new ViewHandler();
		} else if ("CLICK".equals(eventCode.toUpperCase())) {
			handler = new ClickHandler();
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
		log.info("-----【event】--------->分配控制器-前-的处理<-------------");
		
	}

	/**
	 * 分配控制器后的处理
	 * 可以做日志记录，资源最后释放等
	 */
	public void afterGotoHander(Map<String, String> paramMap,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		log.info("-----【event】--------->分配控制器-后-的处理<-------------");
		
	}
}
