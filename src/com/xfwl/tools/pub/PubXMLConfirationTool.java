package com.xfwl.tools.pub;

import com.xfwl.tools.StringTool;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 配置文件公共管理类
 * 
 * @author Jason
 * 
 */
public class PubXMLConfirationTool {
	public static void main(String[] args) {		
		System.out.println(PubXMLConfirationTool.getSysParamData("access_token_url"));				
		
	}
	/**
	 * 获取settings.xml中配置的【系统参数】
	 * @param sysKeyId  系统参数keyID
	 * @return 
	 */
	public static String getSysParamData(String sysKeyId){
		String sysValue=null;
		if(!StringTool.isEmptyOrNull(sysKeyId)){
			Set<Map.Entry<String, String>> set = SettingsXmlTool.getSysParamMap().entrySet();
			Iterator<Map.Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> ey = (Map.Entry) it.next();
				String key = (String) ey.getKey();
				if(sysKeyId.equals(key)){
					sysValue= (String) ey.getValue();
				}
			}
		}
		return sysValue;		
	}
	/**
	 * 获取settings.xml中配置的【自定义参数】
	 * @param selfType  自定义参数的KColl的ID
	 * @param selfKeyId  自定义参数keyID
	 * @return 
	 */
	public static String getSelfParamData(String selfType,String selfKeyId){
		String selfValue=null;
		if(!StringTool.isEmptyOrNull(selfType)|| !StringTool.isEmptyOrNull(selfKeyId)){
			Set<Entry<String, Map<String, String>>> set = SettingsXmlTool.getSelfParamMap().entrySet();
			Iterator<Entry<String, Map<String, String>>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, Map<String, String>> eachSelfMap = (Map.Entry<String, Map<String, String>>) it.next();
				//获取每一个自定义作用域的id
				String key = (String) eachSelfMap.getKey();
				if(selfType.equals(key)){
					Map<String, String> eachMap=eachSelfMap.getValue();					
					selfValue=eachMap.get(selfKeyId);
				}
			}
		}
		return selfValue;		
	}
	/**
	 * 获取settings.xml中配置的【自定义参数】Map
	 * @param selfKeyId  自定义参数keyID
	 * @return 
	 */
	public static Map<String, String> getSelfParamMap(String selfType){
		Map<String, String> selfMap=null;
		if(!StringTool.isEmptyOrNull(selfType)){
			Set<Entry<String, Map<String, String>>> set = SettingsXmlTool.getSelfParamMap().entrySet();
			Iterator<Entry<String, Map<String, String>>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, Map<String, String>> eachSelfMap = (Map.Entry<String, Map<String, String>>) it.next();
				//获取每一个自定义作用域的id
				String key = (String) eachSelfMap.getKey();
				if(selfType.equals(key)){
					selfMap=eachSelfMap.getValue();
					break;
				}
			}
		}
		return selfMap;		
	}
	/**
	 * 打印map集合中的数据
	 */
	private static void printMap(Map<String, String> map){
		Set<Map.Entry<String, String>> set = map.entrySet();
		Iterator<Map.Entry<String, String>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> ey = (Map.Entry) it.next();
			String key = (String) ey.getKey();
			String value = (String) ey.getValue();
			System.out.println(key+":"+value);
		}
	}
}
