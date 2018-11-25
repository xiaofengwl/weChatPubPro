package com.xfwl.beans.menus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * 开始自定义按钮
 * 
 * @author Jason
 * 
 */
public class MenuUtil {
	public static Logger log = Logger.getLogger(MenuUtil.class);
    /**
     * 自定义按钮需要发送接口数据到微信服务器
     * @param access_token 必要参数
     * @throws IOException
     */
	public static void creatWeiXinMenu(String access_token) throws IOException {
		String param = "";

		param = getMenuJson();
		String Url = String
				.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s",
						new Object[] { access_token });
		JSONObject json = JSON.parseObject(param);
		URL url = new URL(Url);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");

		httpConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Connection", "Keep-Alive");
		httpConn.setRequestProperty("Charset", "UTF-8");
		httpConn.connect();
		DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());

		log.info("自定义菜单上送报文1：\n" + json.toJSONString());

		dos.write(json.toJSONString().getBytes("UTF-8"));
		dos.flush();
		dos.close();

		int resultCode = httpConn.getResponseCode();
		if (200 == resultCode) {
			StringBuffer sb = new StringBuffer();
			String readLine = new String();
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			log.info("微信自定义菜单初始化完成....");
			log.info(sb.toString());
		}
	}

	public static String getMenuJson() {
		Menu menu = getMenu();
		return mebu2Json(menu, true);
	}
	/**
	 * 组装菜单
	 * @return
	 */
	private static Menu getMenu() {
		ViewButton btn11 = new ViewButton();
		btn11.setName("我的博客");
		btn11.setUrl("https://www.cnblogs.com/newwind/");

		ClickButton btn21 = new ClickButton();
		btn21.setName("吃鸡战场");
		btn21.setKey("game_cjzc");

		ClickButton btn22 = new ClickButton();
		btn22.setName("王者荣耀");
		btn22.setKey("game_wzry");

		ClickButton btn23 = new ClickButton();
		btn23.setName("明日之后");
		btn23.setKey("game_mrzh");

		ComplexMenu mainBtn1 = new ComplexMenu();
		mainBtn1.setName("技术交流");
		mainBtn1.setSub_button(new BasicButton[] { btn11 });

		ComplexMenu mainBtn2 = new ComplexMenu();
		mainBtn2.setName("我的游戏");
		mainBtn2.setSub_button(new BasicButton[] { btn21, btn22, btn23 });

		ViewButton btn31 = new ViewButton();
		btn31.setName("与我联系2");
		btn31.setUrl("http://njc5eq.natappfree.cc/WeChatPubAdrPro/index.jsp");

		Menu menu = new Menu();
		menu.setButton(new BasicButton[] { mainBtn1, mainBtn2, btn31 });
		return menu;
	}
	/**
	 * 把组装的菜单，生成json格式的数据
	 * @param menu
	 * @param flag
	 * @return
	 */
	private static String mebu2Json(Menu menu, boolean flag) {
		StringBuffer sb = new StringBuffer();
		sb.append(flag ? "{\"button\":[" : "");

		BasicButton[] bbtns = menu.getButton();

		int index = 0;
		int size = bbtns.length;
		for (BasicButton eachBtn : bbtns) {
			if ((eachBtn instanceof ViewButton)) {
				ViewButton btn = (ViewButton) eachBtn;
				sb.append("{");
				sb.append("\"name\":\"" + btn.getName() + "\",");
				sb.append("\"type\":\"" + btn.getType() + "\",");
				sb.append("\"url\":\"" + btn.getUrl() + "\"");
				sb.append(index == size - 1 ? "}" : "},");
			} else if ((eachBtn instanceof ClickButton)) {
				ClickButton btn = (ClickButton) eachBtn;
				sb.append("{");
				sb.append("\"name\":\"" + btn.getName() + "\",");
				sb.append("\"type\":\"" + btn.getType() + "\",");
				sb.append("\"key\":\"" + btn.getKey() + "\"");
				sb.append(index == size - 1 ? "}" : "},");
			} else if ((eachBtn instanceof ComplexMenu)) {
				ComplexMenu btn = (ComplexMenu) eachBtn;
				sb.append("{");
				sb.append("\"name\":\"" + btn.getName() + "\",");
				sb.append("\"sub_button\":[");

				BasicButton[] cbtns = btn.getSub_button();
				Menu cmenu = new Menu();
				cmenu.setButton(cbtns);
				sb.append(mebu2Json(cmenu, false));

				sb.append(index == size - 1 ? "]}" : "]},");
			}
			index++;
		}
		sb.append(flag ? "]}" : "");
		return sb.toString();
	}
}
