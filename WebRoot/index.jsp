<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>我的微信首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
  </head>
  
  <body >
    	微信公众号测试。 <br>
    	<img style="height:100%;width:100%;" 
    	src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542287875457&di=be5dc09c11b9f00ce48161ac72c2794e&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20160304%2Fmp61773552_1457053166698_1.gif">
  </body>
</html>
