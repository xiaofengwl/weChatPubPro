package com.xfwl.event;

import com.xfwl.event.ievent.IViewHandler;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/**
 * 视图处理器
 * @author Jason
 *
 */
public class ViewHandler extends AbsEventHandler implements IViewHandler {
	public static Logger log = Logger.getLogger(ViewHandler.class);

	public void dealHandler(Map<String, String> reqMap,
			HttpServletRequest request, HttpServletResponse response) {
		String eventKey = ((String) reqMap.get("EventKey")).toString();
		log.info("【VIEW】:eventKey:" + eventKey);
	}
}
