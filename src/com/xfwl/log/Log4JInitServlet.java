package com.xfwl.log;

import java.io.File;
import java.io.PrintStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class Log4JInitServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		System.out.println("===>Log4JInitServlet正在初始化log4j日志设置信息");
		String log4jLocation = config
				.getInitParameter("log4j-properties-location");
		String root = config.getServletContext().getRealPath("/");
		System.setProperty("webRoot", root);
		System.out.println("项目根路径：webRoot=" + root);
		ServletContext sc = config.getServletContext();
		if (log4jLocation == null) {
			System.err
					.println("===>因为没有log4j-properties-location初始化的文件,所以使用BasicConfigurator初始化");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File file = new File(log4jProp);
			if (file.exists()) {
				System.out.println("===>使用:" + log4jProp + "初始化日志设置信息");
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("===>" + log4jProp
						+ "文件没有找到，所以使用BasicConfigurator初始化");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}
}
