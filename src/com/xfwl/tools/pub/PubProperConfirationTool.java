package com.xfwl.tools.pub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.xfwl.event.EventManager;
import com.xfwl.token.AccessTokenServlet;
import com.xfwl.tools.StringTool;

/**
 * 系统*.properties文件解析和管理
 * @author Jason
 *
 */
public class PubProperConfirationTool {
	//日志对象
		public static Logger log = Logger.getLogger(PubProperConfirationTool.class);
	/**
	 * access_token的数据集合
	 */
	private static Map<String,String> accessTokenMap;
	/**
	 * 系统启动初始化执行一次
	 * 写入：access_token.properties 文件写入的数据
	 * @return
	 */
	public static void setAccessTokenProperties(String curAccessToken){
		/**
		 * 数据准备
		 */
		accessTokenMap=new HashMap<String, String>();
		Properties accessTokenProp=null;
		FileOutputStream ous=null;
		try {
			accessTokenProp=new Properties();
			/**
			 * 数据处理
			 */
			String access_token=curAccessToken;
			String updateTime=StringTool.getServerTime("yyyyMMddHHmmss");
			String timeSlot=PubXMLConfirationTool.getSysParamData("timeSlot");
			
			accessTokenMap.put("ACCESS_TOKEN", access_token);//access_toke数据
			accessTokenMap.put("UPDATE_TIME", updateTime);//最新更新时间：YYYYDDMMHHssmm
			accessTokenMap.put("TIME_SLOT", timeSlot);//更新时间区间
			/**
			 * 保存access_token信息到配置文件中
			 */
			String localpath=PubProperConfirationTool.class.getClassLoader().getResource("").getPath();
			String path=localpath.substring(0, localpath.lastIndexOf("WEB-INF"))+PubXMLConfirationTool.getSysParamData("access_tokenFile");
			//保存输出	
			ous=new FileOutputStream(new File(path),false);
			accessTokenProp.setProperty("access_token", access_token);
			accessTokenProp.setProperty("updateTime", updateTime);
			accessTokenProp.store(ous, StringTool.convertTimeFormat(updateTime,"yyyyMMddHHmmss","YYYY/MM/DD HH:mm:ss")+" write the new access_token");
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("找不到指定文件："+PubXMLConfirationTool.getSysParamData("access_tokenFile"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("打开文件流失败："+PubXMLConfirationTool.getSysParamData("access_tokenFile"));
		}finally{
			if(ous!=null){
				try {
					ous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 每次获取access_token的方法
	 * @return
	 */
	public static Map<String,String> getAccessTokenProperties(){
		Properties accessTokenProp=null;
		FileInputStream fis=null;
		try {
			accessTokenProp=new Properties();
			String localpath=PubProperConfirationTool.class.getClassLoader().getResource("").getPath();
			String path=localpath.substring(0, localpath.lastIndexOf("WEB-INF"))+PubXMLConfirationTool.getSysParamData("access_tokenFile");
			fis=new FileInputStream(new File(path));
			accessTokenProp.load(fis);
			/**
			 * 保存数据
			 */
			/*accessTokenMap.put("ACCESS_TOKEN", accessTokenProp.getProperty("access_token"));//access_toke数据
			accessTokenMap.put("UPDATE_TIME", accessTokenProp.getProperty("updateTime"));//最新更新时间：YYYYDDMMHHssmm
			accessTokenMap.put("TIME_SLOT", PubXMLConfirationTool.getSysParamData("timeSlot"));//更新时间区间
*/			
			System.out.println(accessTokenProp.getProperty("access_token"));
			System.out.println(accessTokenProp.getProperty("updateTime"));
			System.out.println(PubXMLConfirationTool.getSysParamData("timeSlot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return accessTokenMap;		
	}
	
	
	public static void main(String[] args) {
		getAccessTokenProperties();
	}
	/**
	 * 获取access_token数据集合
	 * @return
	 */
	public static Map<String, String> getAccessTokenMap() {
		return accessTokenMap;
	}
	
}
