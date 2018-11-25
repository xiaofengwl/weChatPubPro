package com.xfwl.tools;

import com.xfwl.message.MessageUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * 文件工具类
 * 
 * @author Jason
 * 
 */
public class FilleTool {
	public static Logger log = Logger.getLogger(FilleTool.class);

	/**
	 * 解析txt文件
	 * 
	 * @param localFilePath
	 * @param fileName
	 * @return
	 */
	public static String parseTxt(String localFilePath, String fileName) {
		StringBuffer sb = new StringBuffer();
		String basePath = null;
		String webAppsPath = null;
		if (StringTool.isEmptyOrNull(localFilePath)) {
			basePath = MessageUtil.class.getClassLoader().getResource("")
					.getPath();
			webAppsPath = basePath
					.substring(0, basePath.lastIndexOf("WEB-INF")) + fileName;
		} else {
			webAppsPath = localFilePath + File.pathSeparator + fileName;
		}
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(new File(
					webAppsPath)));
		} catch (FileNotFoundException localFileNotFoundException) {
		}
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
