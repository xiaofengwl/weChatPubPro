package com.xfwl.tools.pub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 系统settings.xml文件解析 <?xml version="1.0" encoding="UTF-8"?> <settings.xml>
 * <sysXml id="settings"> <kColl id="sysParam" desc="系统参数"> <field id="系统参数1"
 * value="参数值1" desc="参数值1描述" /> <field id="系统参数2" value="参数值2" desc="参数值2描述" />
 * </kColl> <kColl id="selfParam1" desc="自定义参数范围id1"> <field id="自定义参数1"
 * value="参数值1" desc="参数值1描述" /> <field id="自定义参数2" value="参数值2" desc="参数值2描述"
 * /> </kColl> <kColl id="selfParam2" desc="自定义参数范围id2"> <field id="自定义参数1"
 * value="参数值1" desc="参数值1描述" /> <field id="自定义参数2" value="参数值2" desc="参数值2描述"
 * /> </kColl> </sysXml> </settings.xml>
 * 
 * @author Jason
 * 
 */
public class SettingsXmlTool {
	public static Logger log = Logger.getLogger(SettingsXmlTool.class);
	/**
	 * 存放系统配置参数
	 */
	private static Map<String, String> sysParamMap;
	/**
	 * 存放自定义配置参数
	 */
	private static Map<String, Map<String, String>> selfParamMap;
	/**
	 * 项目根路径
	 */
	private static String localPath = SettingsXmlTool.class.getClassLoader()
			.getResource("").getPath();
	private static SettingsXmlTool instance;
	/**
	 * 静态块初始化
	 */
	static {
		sysParamMap = new HashMap();
		selfParamMap = new HashMap();
		parseSettingXml(localPath
				.substring(0, localPath.lastIndexOf("classes"))
				+ "conf/settings.xml");
	}

	/**
	 * 单例模式
	 */
	public static SettingsXmlTool getInstance() {
		if (instance == null) {
			instance = new SettingsXmlTool();
		}
		return instance;
	}

	/**
	 * 按照settings.xml的配置格式解析文件
	 * 
	 * @param filePath
	 */
	private static void parseSettingXml(String filePath) {
		File file = new File(filePath);
		InputStream fis = null;

		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			fis = new FileInputStream(file);
			doc = reader.read(fis);

			Element root = doc.getRootElement();

			List<Element> elemList = root.elements();
			for (Element elem : elemList) {
				if ("sysXml".equals(elem.getName())) {
					List<Element> sysXmlList = elem.elements();
					for (Element kColl : sysXmlList) {
						String kcollId = kColl.attributeValue("id");
						if ("SYSPARAM".equals(kcollId.toUpperCase())) {
							List<Element> sysParamList = kColl.elements();
							for (Element sysParam : sysParamList) {
								sysParamMap.put(sysParam.attributeValue("id").trim(), sysParam.attributeValue("value").trim());
							}
						} else {
							List<Element> pubParamList = kColl.elements();
							Map<String, String> selfMap = new HashMap();
							for (Element selefParam : pubParamList) {
								selfMap.put(selefParam.attributeValue("id").trim(),selefParam.attributeValue("value").trim());
							}
							selfParamMap.put(kcollId, selfMap);
						}
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error("settings.xml文件解析失败！" + e.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("文件路径不存在，获取settings.xml文件流失败！\n" + e.getMessage());
			fis = null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Map<String, String> getSysParamMap() {
		return sysParamMap;
	}

	public static Map<String, Map<String, String>> getSelfParamMap() {
		return selfParamMap;
	}
}
