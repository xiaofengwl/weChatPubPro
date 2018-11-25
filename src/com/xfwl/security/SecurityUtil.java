package com.xfwl.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SecurityUtil {
	public static StringBuffer encryptMsg(String data, String encygen) {
		return new StringBuffer(AESUtil.encrypt(data, encygen));
	}

	public static StringBuffer deCioherMsg(String data, String deciogen) {
		return new StringBuffer(AESUtil.decrypt(data, deciogen));
	}

	public static StringBuffer createMsgFormat(Map<String, Object> dataMap) {
		StringBuffer msg = new StringBuffer();
		msg.append("<service>");

		msg.append("<SYS_HEAD>");

		msg.append("<HEAD_BSNCODE>100001</HEAD_BSNCODE>");
		msg.append("</SYS_HEAD>");
		msg.append("<BODY>");

		Set<Map.Entry<String, Object>> set = dataMap.entrySet();
		Iterator<Map.Entry<String, Object>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> ey = (Map.Entry) it.next();
			String key = (String) ey.getKey();
			String value = (String) ey.getValue();
			msg.append("<" + key + ">" + value + "</" + key + ">");
		}
		msg.append("</BODY>");
		msg.append("</service>");
		return msg;
	}

	public static Map<String, Object> parseRecMsgFormat(String xml) {
		Map<String, Object> recMap = new HashMap();
		Document doc = null;
		Element root = null;
		List<Element> elems = null;
		List<Element> elems_head = null;
		List<Element> elems_body = null;
		try {
			doc = DocumentHelper.parseText(xml);
			root = doc.getRootElement();
			elems = root.elements();
			for (Element elem : elems) {
				if ("SYS_HEAD".equals(elem.getName())) {
					elems_head = elem.elements();
					for (Element head : elems_head) {
						if ("HEAD_BSNCODE".equals(head.getName())) {
							recMap.put("HEAD_BSNCODE", head.getText());
						}
					}
				} else if ("BODY".equals(elem.getName())) {
					elems_body = elem.elements();
					for (Element body : elems_body) {
						recMap.put(body.getName(), body.getText());
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return recMap;
	}
}
