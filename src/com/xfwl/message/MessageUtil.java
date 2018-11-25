package com.xfwl.message;

import com.alibaba.fastjson.JSONObject;
import com.xfwl.token.AccessTokenServlet;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageUtil
{
  public static Logger log = Logger.getLogger(MessageUtil.class);
  private static final String DATE_FORMAT = "yyyy/MM/DD HH:mm:ss";
  
  public static Map<String, String> parseXml(HttpServletRequest request)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    
    InputStream in = request.getInputStream();
    log.info("获取输入流");
    
    SAXReader reader = new SAXReader();
    Document doc = reader.read(in);
    
    Element root = doc.getRootElement();
    
    List<Element> elemList = root.elements();
    for (Element elem : elemList)
    {
      log.info(elem.getName() + "|" + elem.getText());
      map.put(elem.getName(), elem.getText());
    }
    in.close();
    in = null;
    return map;
  }
  
  public static String buildXml(Map<String, String> map)
  {
    String result = null;
    
    String msgType = ((String)map.get("MsgType")).toString();
    log.info("MsgType:" + msgType);
    if ("TEXT".equals(msgType.toUpperCase()))
    {
      String Content = ((String)map.get("Content")).toString();
      if ("文本".equals(Content))
      {
        result = buildTextMessage(map, "亲！请问小风有什么可以帮助您的吗？");
      }
      else if ("图片".equals(Content))
      {
        result = buildVoiceMessage(map);
      }
      else if ("语音".equals(Content))
      {
        result = buildImageMessage(map);
      }
      else if ("视频".equals(Content))
      {
        result = buildVideoMessage(map);
      }
      else if ("音乐".equals(Content))
      {
        result = buildMusicMessage(map);
      }
      else if ("图文".equals(Content))
      {
        result = buildNewsMessage(map);
      }
      else
      {
        String fromUserName = ((String)map.get("FromUserName")).toString();
        
        String toUserName = ((String)map.get("ToUserName")).toString();
        
        result = 
          String.format(
          "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[%s]]></Content></xml>", new Object[] {
          





          fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), 
          "请回复如下关键词：\n1.文本\n2.图片\n3.语音\n4.视频\n5.音乐\n6.图文" });
      }
    }
    else if ("IMAGE".equals(msgType.toUpperCase()))
    {
      result = buildImageMessage(map);
    }
    else if ("VOICE".equals(msgType.toUpperCase()))
    {
      result = buildNewsMessage(map);
    }
    else if ("VIDEO".equals(msgType.toUpperCase()))
    {
      result = buildVideoMessage(map);
    }
    else if ("MUSIC".equals(msgType.toUpperCase()))
    {
      result = buildMusicMessage(map);
    }
    else if ("EVENT".equals(msgType.toUpperCase()))
    {
      result = buildTextMessage(map, "感谢您的关注，楼主暂时不在家，有事请留言！");
    }
    else
    {
      String fromUserName = ((String)map.get("FromUserName")).toString();
      
      String toUserName = ((String)map.get("ToUserName")).toString();
      
      result = 
        String.format(
        "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[%s]]></Content></xml>", new Object[] {
        





        fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), 
        "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文" });
      

      uploadFile("videos", "video_1.qsv");
    }
    return result;
  }
  
  public static String buildNewsMessage(Map<String, String> map, String imgUrl, String txtUrl, String title, String desc)
  {
    String fromUserName = (String)map.get("FromUserName");
    String toUserName = (String)map.get("ToUserName");
    String title1 = title;
    String description1 = desc;
    String picUrl1 = imgUrl;
    String textUrl1 = txtUrl;
    
    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount><![CDATA[1]]></ArticleCount><Articles><item><Title><![CDATA[%s]]></Title> <Description><![CDATA[%s]]></Description><PicUrl><![CDATA[%s]]></PicUrl><Url><![CDATA[%s]]></Url></item></Articles></xml>", new Object[] {
      














      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), 
      title1, description1, picUrl1, textUrl1 });
  }
  
  private static String buildNewsMessage(Map<String, String> map)
  {
    String fromUserName = (String)map.get("FromUserName");
    String toUserName = (String)map.get("ToUserName");
    String title1 = "如何学好微信公众号开发";
    String description1 = "详情参考官网API文档说明";
    String picUrl2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542103805440&di=f29b8e3e585fe89b651d90c498a5c8b5&imgtype=0&src=http%3A%2F%2Fwww.zhiqiapp.com%2Fuploads%2Fallimg%2F180625%2F1-1P625140T1514.jpg";
    String textUrl1 = "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140543";
    


    String title2 = "微信订阅号token验证和access_token的有效性";
    String description2 = "22222222222222222222222222";
    String picUrl1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542105539977&di=c01675c769b31e07107e44a144f228d6&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201503%2F14%2F20150314212812_kCLmy.jpeg";
    String textUrl2 = "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140543";
    
    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount><![CDATA[2]]></ArticleCount><Articles><item><Title><![CDATA[%s]]></Title> <Description><![CDATA[%s]]></Description><PicUrl><![CDATA[%s]]></PicUrl><Url><![CDATA[%s]]></Url></item><item><Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description><PicUrl><![CDATA[%s]]]></PicUrl><Url><![CDATA[%s]]]></Url></item></Articles></xml>", new Object[] {
      




















      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), 
      title1, description1, picUrl1, textUrl1, 
      title2, description2, picUrl2, textUrl2 });
  }
  
  public static String buildMusicMessage(Map<String, String> map)
  {
    String fromUserName = ((String)map.get("FromUserName")).toString();
    
    String toUserName = ((String)map.get("ToUserName")).toString();
    String title = "亲爱的路人";
    String description = "多听音乐 心情棒棒 嘻嘻！";
    String hqMusicUrl = "http://www.kugou.com/song/20qzz4f.html?frombaidu#hash=20C16B9CCCCF851D1D23AF52DD963986&album_id=0";
    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[music]]></MsgType><Music>   <Title><![CDATA[%s]]></Title>   <Description><![CDATA[%s]]></Description>   <MusicUrl>< ![CDATA[%s] ]></MusicUrl>   <HQMusicUrl><![CDATA[%s]]></HQMusicUrl></Music></xml>", new Object[] {
      










      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), title, description, hqMusicUrl, hqMusicUrl });
  }
  
  public static String buildVideoMessage(Map<String, String> map)
  {
    String fromUserName = ((String)map.get("FromUserName")).toString();
    
    String toUserName = ((String)map.get("ToUserName")).toString();
    String title = "客官发过来的视频哟~~";
    String description = "客官您呐,现在肯定很开心,对不啦 嘻嘻~~";
    


    String media_id = "hTl1of-w78xO-0cPnF_Wax1QrTwhnFpG1WBkAWEYRr9Hfwxw8DYKPYFX-22hAwSs";
    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[video]]></MsgType><Video>   <MediaId><![CDATA[%s]]></MediaId>   <Title><![CDATA[%s]]></Title>   <Description><![CDATA[%s]]></Description></Video></xml>", new Object[] {
      









      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), media_id, title, description });
  }
  
  public static String buildVoiceMessage(Map<String, String> map)
  {
    String fromUserName = ((String)map.get("FromUserName")).toString();
    
    String toUserName = ((String)map.get("ToUserName")).toString();
    
    String media_id = (String)map.get("MediaId");
    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[voice]]></MsgType><Voice>   <MediaId><![CDATA[%s]]></MediaId></Voice></xml>", new Object[] {
      







      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), media_id });
  }
  
  public static String buildImageMessage(Map<String, String> map)
  {
    String fromUserName = ((String)map.get("FromUserName")).toString();
    
    String toUserName = ((String)map.get("ToUserName")).toString();
    
    String media_id = "08n4VcEAanUFYRx0PycIy9qWF5bCODF5S8Z-W9khGUknpT-NNCUKX21wpp55X_q4";
    


    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[image]]></MsgType><Image>   <MediaId><![CDATA[%s]]></MediaId></Image></xml>", new Object[] {
      







      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), media_id });
  }
  
  public static String buildTextMessage(Map<String, String> map, String content)
  {
    String fromUserName = ((String)map.get("FromUserName")).toString();
    
    String toUserName = ((String)map.get("ToUserName")).toString();
    


    return String.format(
      "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[%s]]></Content></xml>", new Object[] {
      




      fromUserName, toUserName, getUtcTime("yyyy/MM/DD HH:mm:ss"), content });
  }
  
  public static String getUtcTime(String format)
  {
    Date dt = new Date();
    DateFormat df = new SimpleDateFormat(format);
    String nowTime = df.format(dt);
    long dd = 0L;
    try
    {
      dd = df.parse(nowTime).getTime();
    }
    catch (Exception localException) {}
    return String.valueOf(dd);
  }
  
  public static void uploadFile(String path, String name)
  {
    try
    {
      UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil();
      String accessToken = AccessTokenServlet.getAccessToken();
      String localPath = MessageUtil.class.getClassLoader().getResource("").getPath();
      log.info(localPath);
      String filePath = localPath.substring(0, localPath.lastIndexOf("WEB-INF")) + path + "/" + name;
      log.info("上传素材本地路径：" + filePath);
      File file = new File(filePath);
      String type = "IMAGE";
      JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file, 
        accessToken, type);
      log.info(jsonObject.toString());
    }
    catch (Exception e)
    {
      log.info("上传素材失败！");
    }
  }
}
