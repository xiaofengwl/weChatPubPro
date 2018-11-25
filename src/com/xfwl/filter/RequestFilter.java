package com.xfwl.filter;

import com.xfwl.security.SecurityUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
/**
 * 请求过滤器
 * @author Jason
 *
 */
public class RequestFilter implements Filter {
	public static Logger log = Logger.getLogger(RequestFilter.class);
	private FilterConfig config = null;

	public void destroy() {
		log.info("销毁...");
	}
	/**
	 * 开始过滤操作
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		 // 强制类型转换  
        HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) resp; 
        // 获取web.xm设置的编码集，设置到Request、Response中  
        request.setCharacterEncoding(config.getInitParameter("charset"));  
        response.setContentType(config.getInitParameter("contentType"));  
        response.setCharacterEncoding(config.getInitParameter("charset"));
        /**
         * 解析请求报文
         */
		log.info("执行中...");
		String miWen = getRequestBody(request);
		if (("".equals(miWen)) || (miWen == null)) {
			 // 将请求转发到目的地  
            chain.doFilter(request, response); 
		} else {
			log.info("[密文]" + miWen);
			StringBuffer MingWen = SecurityUtil.deCioherMsg(miWen, "lvjun");
			log.info("[明文]" + MingWen);
			Map<String, Object> recMap = SecurityUtil.parseRecMsgFormat(MingWen
					.toString());
			Set<Map.Entry<String, Object>> set = recMap.entrySet();
			Iterator<Map.Entry<String, Object>> it = set.iterator();
			log.info("----------------------");
			while (it.hasNext()) {
				Map.Entry<String, Object> ey = (Map.Entry) it.next();
				String key = (String) ey.getKey();
				String value = (String) ey.getValue();
				log.info(key + ":" + value);
			}
			log.info("----------------------");
			 /**
             * 处理数据并生成响应报文并返回
             */
			Map<String, Object> sendMap = new HashMap();
			sendMap.put("RETURN_CODE", "90");
			sendMap.put("RETURN_MSG", "交易成功");
			StringBuffer sb = SecurityUtil.createMsgFormat(sendMap);
			StringBuffer finRespData = SecurityUtil.encryptMsg(sb.toString(),
					"lvjun");
			writeResponse(response, finRespData.toString());
		}
	}
	/**
	 * 获取请求数据
	 * @param req
	 * @return
	 */
	private String getRequestBody(HttpServletRequest req) {
		try {
			BufferedReader reader = req.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			log.info("获取请求报文数据失败！");
		}
		return "";
	}
	/**
	 * 输出响应数据
	 * @param response
	 * @param responseString
	 * @throws IOException
	 */
	private void writeResponse(ServletResponse response, String responseString)
	        throws IOException {
	    PrintWriter out = response.getWriter();
	    out.print(responseString);
	    out.flush();
	    out.close();
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		log.info("初始化...");
	}
}
