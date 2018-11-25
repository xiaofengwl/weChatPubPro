package com.xfwl.event;

import com.xfwl.event.ievent.IClickHandler;
import com.xfwl.message.MessageUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ClickHandler extends AbsEventHandler implements IClickHandler {
	public static Logger log = Logger.getLogger(ClickHandler.class);
	/**
	 * 处理器
	 */
	public void dealHandler(Map<String, String> reqMap,
			HttpServletRequest request, HttpServletResponse response) {
		String eventKey = ((String) reqMap.get("EventKey")).toString();
		String imgUrl = null;
		String txtUrl = null;
		String title = null;
		String desc = null;
		if ("GAME_CJZC".equals(eventKey.toUpperCase())) {
			imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542281826586&di=d011186a5cb5511f21629c6458a18df2&imgtype=0&src=http%3A%2F%2Fimgup01.fanw8.com%2Ffanw8%2Fimage%2F2018%2F0326%2F1522030430716952.jpg";
			title = "吃鸡战场";
			desc = "刺激战场：最全详细攻略之装备介绍，吃鸡小伙伴必看！";
			txtUrl = "https://baijiahao.baidu.com/s?id=1604876590692162344&wfr=spider&for=pc";
		} else if ("GAME_WZRY".equals(eventKey.toUpperCase())) {
			imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542876740&di=156efeaf6258644e02bc527924f649ef&imgtype=jpg&er=1&src=http%3A%2F%2Fuploads.5068.com%2Fallimg%2F171123%2F1-1G123113546.jpg";
			title = "王者荣耀";
			desc = "王者荣耀强制实名制再扩围：本月全国完成实名校验！";
			txtUrl = "http://tech.163.com/18/1101/16/DVHP2CET00097U7R.html";
		} else if ("GAME_MRZH".equals(eventKey.toUpperCase())) {
			imgUrl = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3733273462,1536994191&fm=11&gp=0.jpg";
			title = "明日之后";
			desc = "明日之后怎么钓鱼？明日之后钓鱼任务领取方法钓鱼攻略！";
			txtUrl = "http://www.mnw.cn/keji/shouyou/gonglue/2085960-2.html";
		}
		log.info("【CLICK】:clickId:" + eventKey);

		String result = MessageUtil.buildNewsMessage(reqMap, imgUrl, txtUrl,
				title, desc);
		try {
			response.getWriter().println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
