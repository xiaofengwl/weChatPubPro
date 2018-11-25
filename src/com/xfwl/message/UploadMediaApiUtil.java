package com.xfwl.message;

import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.log4j.Logger;

public class UploadMediaApiUtil
{
  public static Logger log = Logger.getLogger(UploadMediaApiUtil.class);
  private static final String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload";
  private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
  
  public static void main(String[] args)
  {
    File file = new File("./");
  }
  
  public static String getDownloadUrl(String token, String mediaId)
  {
    return String.format("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s", new Object[] { token, mediaId });
  }
  
  public JSONObject uploadMedia(File file, String token, String type)
  {
    log.info("上传前数据准备~");
    if ((file == null) || (token == null) || (type == null)) {
      return null;
    }
    if (!file.exists())
    {
      log.info("上传文件不存在,请检查！");
      return null;
    }
    JSONObject jsonObject = null;
    PostMethod post = new PostMethod("https://api.weixin.qq.com/cgi-bin/media/upload");
    post.setRequestHeader("Connection", "Keep-Alive");
    post.setRequestHeader("Cache-Control", "no-cache");
    
    HttpClient httpClient = new HttpClient();
    
    Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
    Protocol.registerProtocol("https", myhttps);
    try
    {
      FilePart media = new FilePart("media", file);
      Part[] parts = {
        new StringPart("access_token", token), 
        new StringPart("type", type), 
        media };
      
      MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
      post.setRequestEntity(entity);
      int status = httpClient.executeMethod(post);
      if (status == 200)
      {
        String text = post.getResponseBodyAsString();
        jsonObject = JSONObject.parseObject(text);
        log.info("上传成功，状态:" + status);
      }
      else
      {
        log.info("上传失败，状态:" + status);
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (HttpException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return jsonObject;
  }
  
  public static File downloadMedia(String fileName, String token, String mediaId)
  {
    String path = getDownloadUrl(token, mediaId);
    if ((fileName == null) || (path == null)) {
      return null;
    }
    File file = null;
    HttpURLConnection conn = null;
    InputStream inputStream = null;
    FileOutputStream fileOut = null;
    try
    {
      URL url = new URL(path);
      conn = (HttpURLConnection)url.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("GET");
      
      inputStream = conn.getInputStream();
      if (inputStream != null)
      {
        file = new File(fileName);
      }
      else
      {
        File localFile1 = file;return localFile1;
      }
      fileOut = new FileOutputStream(file);
      if (fileOut != null)
      {
        int c = inputStream.read();
        while (c != -1)
        {
          fileOut.write(c);
          c = inputStream.read();
        }
      }
    }
    catch (Exception localException)
    {
      if (conn != null) {
        conn.disconnect();
      }
      try
      {
        inputStream.close();
        fileOut.close();
      }
      catch (IOException localIOException1) {}
    }
    finally
    {
      if (conn != null) {
        conn.disconnect();
      }
      try
      {
        inputStream.close();
        fileOut.close();
      }
      catch (IOException localIOException2) {}
    }
    return file;
  }
}
