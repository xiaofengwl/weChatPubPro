package com.xfwl.token;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfwl.beans.menus.MenuUtil;
import com.xfwl.tools.StringTool;
import com.xfwl.tools.pub.PubProperConfirationTool;

/**
 * 管理如何使用和获取access_token数据
 * 可用性分析：
 * 		如果使用当前获取access_token的这套机制，那么一旦使用到access_token，
 * 	    那么就会去读一次*.properties文件，获取配置信息，经行判断当前access_token是否可用。
 * @author Jason
 *
 */
public class Access_TokenManage {
	/**
	 * 公共数据
	 */
	public static Logger log = Logger.getLogger(AccessTokenServlet.class);
	private static String ACCESS_TOKEN = "";
	/**
	 * 主动发送报文向微信官方服务器索取access_token数据
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	private AccessToken getAccessToken(String appId, String appSecret) {
		String result = "";
		AccessToken token = null;
		if (!ifCurAccessToken()) {//不可继续使用，需要重新获取
			NetWorkUtil netHelper = new NetWorkUtil();

			String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
						               new Object[] { appId, appSecret });
			result = netHelper.getHttpsResponse(Url, "");
			log.info("获取到的access_token=" + result);
			
			JSONObject json = JSON.parseObject(result);
			token = new AccessToken();
			token.setTokenName(json.getString("access_token"));
			token.setExpireSecond(json.getInteger("expires_in").intValue());

			ACCESS_TOKEN = json.getString("access_token");
			log.info("保存微信access_token:" + ACCESS_TOKEN
					+ "\n可通过：AccessTokenServlet.ACCESS_TOKEN获取");
			//创建微信公共号菜单
			try {
				MenuUtil.creatWeiXinMenu(ACCESS_TOKEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//保存access_token数据到配置文件中
			PubProperConfirationTool.setAccessTokenProperties(ACCESS_TOKEN);
		} else {//可以继续使用
			token = new AccessToken();
			token.setTokenName(ACCESS_TOKEN);
			log.info("【延用之前的】access_token:" + ACCESS_TOKEN);			
		}
		log.info("获取access_token：" + ACCESS_TOKEN);
		return token;
	}
	/**
	 * 判断当前获取的access_token是否可继续使用
	 * @return
	 */
	private boolean ifCurAccessToken(){
		//获取配置文件中的access_token数据
		Map<String,String> accessTokenMap=PubProperConfirationTool.getAccessTokenMap();
		String updateTime=accessTokenMap.get("updateTime");
		String access_token=accessTokenMap.get("access_token");
		String timeSlot=accessTokenMap.get("timeSlot");
		/**
		 * 判断处理：当前时间和updateTime间隔不能超过2h
		 */
		double periodTime=Math.floor(Double.valueOf(timeSlot)*60*60);
		double curTime=Double.valueOf(StringTool.getServerTime("yyyyMMddHHmmss"));
		double accessToKeTime=Double.valueOf(updateTime);
		if((curTime-periodTime)>accessToKeTime){//时间在可用范围内
			return true;
		}else{
			return false;
		}
	}
}
