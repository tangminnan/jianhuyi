package com.jianhuyi.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestUtils {

  public static Map<String, Object> getData(String url, String request, String requestMethod)
      throws UnsupportedEncodingException, IOException {
    String charset = "UTF-8";
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    HttpURLConnection connect = (HttpURLConnection) (new URL(url).openConnection());
    connect.setRequestMethod(requestMethod);
    connect.setDoOutput(true);
    connect.setConnectTimeout(1000 * 10);
    connect.setReadTimeout(1000 * 80);
    connect.setRequestProperty(
        "ContentType", "application/json"); // 采用通用的URL百分号编码的数据MIME类型来传参和设置请求头
    connect.setDoInput(true);
    // 连接
    connect.connect();
    // 发送数据
    connect.getOutputStream().write(request.getBytes(charset));
    // 接收数据
    int responseCode = connect.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_CREATED) {
      InputStream in = connect.getInputStream();
      byte[] data = new byte[1024];
      int len = 0;
      while ((len = in.read(data, 0, data.length)) != -1) {
        outStream.write(data, 0, len);
      }
      in.close();
    }
    Map<String, List<String>> header = connect.getHeaderFields();

    Map<String, Object> res = new HashMap<>();
    res.put("token", header.get("X-Subject-Token").get(0));

    // 关闭连接
    connect.disconnect();
    String response = outStream.toString("UTF-8");
    res.put("response", response);
    return res;
  }

  /**
   * 发送post请求，参数用map接收
   *
   * @param url 地址
   * @param map 参数
   * @return 返回值
   */
  public static String postMap(String url, Map<String, String> map) {
    String result = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost post = new HttpPost(url);
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    CloseableHttpResponse response = null;
    try {
      post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
      response = httpClient.execute(post);
      if (response != null && response.getStatusLine().getStatusCode() == 200) {
        HttpEntity entity = response.getEntity();
        result = entityToString(entity);
      }
      return result;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * get请求，参数拼接在地址上
   *
   * @param url 请求地址加参数
   * @return 响应
   */
  public static String get(String url) {
    String result = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet get = new HttpGet(url);
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(get);
      if (response != null && response.getStatusLine().getStatusCode() == 200) {
        HttpEntity entity = response.getEntity();
        result = entityToString(entity);
      }
      return result;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * get请求，参数放在map里
   *
   * @param url 请求地址
   * @param map 参数map
   * @return 响应
   */
  public static String get(String url, Map<String, String> map) {
    String result = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    CloseableHttpResponse response = null;
    try {
      URIBuilder builder = new URIBuilder(url);
      builder.setParameters(pairs);
      HttpGet get = new HttpGet(builder.build());
      response = httpClient.execute(get);
      if (response != null && response.getStatusLine().getStatusCode() == 200) {
        HttpEntity entity = response.getEntity();
        result = entityToString(entity);
      }
      return result;
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  /**
   * post请求，参数为json字符串
   *
   * @param url 请求地址
   * @param jsonString json字符串
   * @return 响应
   */
  public static String postJson(String url, String jsonString) {
    String result = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost post = new HttpPost(url);
    CloseableHttpResponse response = null;
    try {
      post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
      response = httpClient.execute(post);
      if (response != null && response.getStatusLine().getStatusCode() == 200) {
        HttpEntity entity = response.getEntity();
        result = entityToString(entity);
      }
      return result;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static String upload(String url, MultipartFile localFile, String token) {
    String respJson = null;
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    try {
      httpClient = HttpClients.createDefault();

      // 把一个普通参数和文件上传给下面这个地址 是一个servlet
      HttpPost httpPost = new HttpPost(url);
      httpPost.setHeader("X-Auth-Token", token);

      MultipartEntityBuilder builder = MultipartEntityBuilder.create();

      builder.addBinaryBody(
          "images",
          localFile.getInputStream(),
          ContentType.MULTIPART_FORM_DATA,
          localFile.getName());

      HttpEntity reqEntity = builder.build();

      httpPost.setEntity(reqEntity);

      // 发起请求 并返回请求的响应
      response = httpClient.execute(httpPost);

      // 获取响应对象
      HttpEntity resEntity = response.getEntity();
      if (resEntity != null) {
        // 打印响应长度
        // 打印响应内容
        respJson = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
      }

      // 销毁
      EntityUtils.consume(resEntity);
    } catch (Exception e) {
      System.out.println("出错啦...." + e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return respJson;
  }

  private static String entityToString(HttpEntity entity) throws IOException {
    String result = null;
    if (entity != null) {
      long lenth = entity.getContentLength();
      if (lenth != -1 && lenth < 2048) {
        result = EntityUtils.toString(entity, "UTF-8");
      } else {
        InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
        CharArrayBuffer buffer = new CharArrayBuffer(2048);
        char[] tmp = new char[1024];
        int l;
        while ((l = reader1.read(tmp)) != -1) {
          buffer.append(tmp, 0, l);
        }
        result = buffer.toString();
      }
    }
    return result;
  }
}
