package com.xfwl.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfwl.beans.menus.MenuUtil;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
/**
 * 主动沟通微信服务器获取access_token数据
 * @author Jason
 *
 */
public class AccessTokenServlet extends HttpServlet {
	public static Logger log = Logger.getLogger(AccessTokenServlet.class);
	private static String ACCESS_TOKEN = "15_ImHuxQUMSRpWCIGkC99orVpCn4zDRfifNNNzCSv2ORtEN8-uJI_Z6l92fPOmvyat8okn0gUNti8erFkm80efIiMLAWbs4Litx3xscaJBk-hyxZpQyCJxQXX9iqy_9ba3mG5I-M-IlzTqkrSLJEMjACAPKS";

	public static String getAccessToken() {
		if ((ACCESS_TOKEN == null) || (ACCESS_TOKEN.length() == 0)) {
			log.info("微信access_token获取失败");
			return "";
		}
		return ACCESS_TOKEN;
	}

	public void init() throws ServletException {
		log.info("-----启动AccessTokenServlet-----");
		super.init();

		final String appId = getInitParameter("appId");
		final String appSecret = getInitParameter("appSecret");
		new Thread(new Runnable() {
			public void run() {
				for (;;) {
					try {
						AccessTokenInfo.accessToken = AccessTokenServlet.this
								.getAccessToken(appId, appSecret);
						if (AccessTokenInfo.accessToken != null) {
							AccessTokenServlet.log
									.info("access_token获取成功，休息7000s");
							Thread.sleep(7000000L);
						} else {
							AccessTokenServlet.log
									.info("access_token获取失败，休息3s，重新获取");
							Thread.sleep(3000L);
						}
					} catch (Exception e) {
						AccessTokenServlet.log.info("发生异常：" + e.getMessage());
						e.printStackTrace();
						try {
							System.out
									.println("access_token获取出现异常，休息1s，重新while循环获取");
							Thread.sleep(10000L);
						} catch (Exception e1) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	private AccessToken getAccessToken(String appId, String appSecret) {
		String result = "";
		AccessToken token = null;
		if ((ACCESS_TOKEN == null) || (ACCESS_TOKEN.length() == 0)) {
			NetWorkUtil netHelper = new NetWorkUtil();

			String Url = String
					.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
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
		} else {
			token = new AccessToken();
			token.setTokenName(ACCESS_TOKEN);
			log.info("【延用之前的】access_token:" + ACCESS_TOKEN);
			try {
				MenuUtil.creatWeiXinMenu(ACCESS_TOKEN);//创建微信公共号菜单
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("获取access_token：" + ACCESS_TOKEN);
		return token;
	}
}
