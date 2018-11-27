package com.xfwl.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
/**
 * 字符串工具类
 * @author Jason
 *
 */
public class StringTool {
	public static Logger log = Logger.getLogger(StringTool.class);
	/**
	 * 字符串判空
	 * @param str
	 * @return
	 */
	public static boolean isEmptyOrNull(String str) {
		return (str == null) || ("".equals(str)) || (str.length() == 0);
	}
	/**
	 * 获取服务器当前事件
	 * @param timeFormat 格式化
	 * @return
	 */
	public static String getServerTime(String timeFormat){
		String format = isEmptyOrNull(timeFormat)? "yyyyMMddHHmmss" : timeFormat;
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat( format );
		return formatter.format( date );		
	}
	/**
	 * 重新格式化时间
	 * 
	 * @param datetime
	 *            原有时间字符串，如20050101
	 * @param oldFormat
	 *            原有时间字符串的格式，如20050101160145为"yyyyMMddHHmmss"
	 * @param newFormat
	 *            新的时间字符串的格式，如2005-01-01 16:01:45为"yyyy-MM-dd HH:mm:ss"
	 * @return String
	 */
	public static String convertTimeFormat( String datetime, String oldFormat, String newFormat )
	{
		SimpleDateFormat oldFmt = null, newFmt = null;
		oldFmt = new SimpleDateFormat( oldFormat );
		newFmt = new SimpleDateFormat( newFormat );
		Date date = null;
		try
		{
			date = oldFmt.parse( datetime );
			return newFmt.format( date );
		}
		catch ( ParseException ex )
		{
			ex.printStackTrace();
		}
		return datetime;
	}
}
