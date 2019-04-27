package com.greelee.tool.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Slf4j
public class HttpUtil {


    private HttpUtil() {
    }

    /**
     * 模拟请求头 User-Agent
     */
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.2 (KHTML, like Gecko) Chrome/22.0.1216.0 Safari/537.2";

    private static final String QUESTION_MARK = "?";

    /**
     * 默认请求设置
     */
    private static final RequestConfig DEFAULT_REQUEST_CONFIG;

    static {
        DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
                //设置连接超时时间，单位毫秒
                .setConnectTimeout(3000)
                //设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的
                .setConnectionRequestTimeout(1000)
                //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用
                .setSocketTimeout(3000)
                .build();

    }

    /**
     * 发送HttpGet请求
     *
     * @param url 请求地址
     * @return 响应字符串
     */
    public static String sendGet(String url) throws IOException {
        return sendGet(url, DEFAULT_REQUEST_CONFIG);
    }

    /**
     * 发送 http get 请求
     *
     * @param api      请求 api 地址
     * @param paramMap 请求参数
     * @return 响应字符串
     */
    public static String sendGet(String api, Map<String, String> paramMap) throws IOException {
        return sendGet(api, paramMap, DEFAULT_REQUEST_CONFIG);
    }

    /**
     * 发送 http get 请求
     *
     * @param api           请求 api 地址
     * @param paramMap      请求参数
     * @param requestConfig 请求设置
     * @return 响应字符串
     */
    public static String sendGet(String api, Map<String, String> paramMap, RequestConfig requestConfig) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(api);
        if (paramMap.size() > 0) {
            stringBuilder.append(QUESTION_MARK);
            paramMap.forEach((k, v) -> {
                try {
                    v = URLEncoder.encode(v, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                stringBuilder.append(k).append("=").append(v).append("&");
            });
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return sendGet(stringBuilder.toString(), requestConfig);
    }

    /**
     * 发送HttpGet请求
     *
     * @param url           请求地址
     * @param requestConfig 请求设置
     * @return 响应字符串
     */
    public static String sendGet(String url, RequestConfig requestConfig) throws IOException {
        //1.获得一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        //添加请求头
        httpGet.addHeader("User-Agent", USER_AGENT);
        log.info("\n请求地址为:" + url);
        //3.执行get请求并返回结果
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (null != response) {
                //4.处理结果，这里将结果返回为字符串
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                log.info("\n状态码:" + statusLine.getStatusCode()
                        + "\n返回内容:" + result);
                return result;
            }
        }
        return null;
    }

    /**
     * 发送HttpPost请求
     *
     * @param url      请求地址
     * @param paramMap 参数值映射
     * @return 响应字符串
     */
    public static String sendPost(String url, Map<String, String> paramMap) throws IOException {
        return sendPost(url, paramMap, DEFAULT_REQUEST_CONFIG);
    }

    /**
     * 发送HttpPost请求
     *
     * @param url           请求地址
     * @param paramMap      参数值映射
     * @param requestConfig 请求设置
     * @return 响应字符串
     */
    public static String sendPost(String url, Map<String, String> paramMap, RequestConfig requestConfig) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        paramMap.forEach((k, v) -> {
            try {
                v = URLEncoder.encode(v, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.add(new BasicNameValuePair(k, v));
        });
        return sendPost(url, params, requestConfig);
    }

    /**
     * 发送HttpPost请求
     *
     * @param url    请求地址
     * @param params 参数值映射
     * @return 响应字符串
     */
    public static String sendPost(String url, List<NameValuePair> params) throws IOException {
        return sendPost(url, params, DEFAULT_REQUEST_CONFIG);
    }

    /**
     * 发送HttpPost请求
     *
     * @param url           请求地址
     * @param params        参数值映射
     * @param requestConfig 请求设置
     * @return 响应字符串
     */
    public static String sendPost(String url, List<NameValuePair> params, RequestConfig requestConfig) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(requestEntity);
        log.info("\n请求地址为:" + url);
        log.info("\n请求参数为:" + params.toString());
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (null != response) {
                HttpEntity responseEntity = response.getEntity();
                StatusLine statusLine = response.getStatusLine();
                String result = EntityUtils.toString(responseEntity);
                log.info("\n状态码:" + statusLine.getStatusCode()
                        + "\n返回内容:" + result);
                return result;
            }
        }
        return null;
    }
}
