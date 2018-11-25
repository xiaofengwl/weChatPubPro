package com.xfwl.servlets;

import com.alibaba.fastjson.JSONObject;
import com.xfwl.message.UploadMediaApiUtil;
import com.xfwl.token.AccessTokenServlet;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "UploadMediaServlet", urlPatterns = { "/servlet/UploadMediaServlet" }, loadOnStartup = -1, initParams = {})
public class UploadMediaServlet extends HttpServlet {
	public static Logger log = Logger.getLogger(UploadMediaServlet.class);
	/**
	 * 初始化
	 */
	public void init() throws ServletException {
		log.info("-----启动UploadMediaServlet-----");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	/**
	 * doGet上传素材
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//测试上传素材
		UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil();
		String accessToken = AccessTokenServlet.getAccessToken();
		String filePath = "E:/DevelopmentSoftwares/worksplaces/myeclipse2014_ws/ServerClientPro/WebRoot/images/weixin/wx_1.jpg";
		log.info("上传素材本地路径：" + filePath);
		File file = new File(filePath);
		String type = "IMAGE";// 上传图片
		JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,
				accessToken, type);
		log.info(jsonObject.toString());
	}
}
