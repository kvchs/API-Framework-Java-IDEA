package com.qa.restclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {
    final static Logger log = Logger.getLogger(RestClient.class);
    public CloseableHttpResponse get(String url) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        //执行请求
        log.info("开始发送无参数的Get请求");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse;

//        //检查响应码
//        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
//        System.out.println("response status code --> " + responseStatusCode);
//
//        //把响应内容存储在字符串对象中
//        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//
//        //创建json对象，把字符串序列化成json对象
//        JSONObject responseJson = JSON.parseObject(responseString);
//        System.out.println("response Json : " +responseJson);
//
//        //获取响应的头信息，返回的是一个数组
//        Header[] headers = httpResponse.getAllHeaders();
//        HashMap<String, String> map = new HashMap<>();
//        for (Header header: headers){
//            map.put(header.getName(), header.getValue());
//        }
//        System.out.println("response header: " + map);
    }

    /**
     * 带请求头信息的Get方法
     * @param url
     * @return
     */
    public CloseableHttpResponse get(String url ,HashMap<String, String> headerMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry: headerMap.entrySet()){
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        log.info("开始发送带参数的Get请求");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse;

    }

    /**
     * POST方法的封装
     * @param url
     * @param entityString
     * @param hashMap
     * @return
     * @throws IOException
     */
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> hashMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(entityString));
        for (Map.Entry<String, String> entry: hashMap.entrySet()){
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        log.info("开始发送POST请求");
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        return httpResponse;
    }


    /**
     * PUT方法的封装
     * @param url
     * @param entityString
     * @param hashMap
     * @return
     * @throws IOException
     */
    public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> hashMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(new StringEntity(entityString));
        for (Map.Entry<String, String> entry: hashMap.entrySet()){
            httpPut.addHeader(entry.getKey(), entry.getValue());
        }
        log.info("开始发送PUT请求");
        CloseableHttpResponse httpResponse = httpClient.execute(httpPut);
        return httpResponse;
    }

    /**
     * DELETE方法的封装
     * @param url
     * @return
     * @throws IOException
     */
    public CloseableHttpResponse delete(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);

        CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
        log.info("开始发送Delete请求");
        return httpResponse;
    }

    /**
     * 获取状态码
     * @param response
     * @return
     */
    public int getStatusCode(CloseableHttpResponse response){
        int statusCode = response.getStatusLine().getStatusCode();
        log.info("得到响应状态码: " + statusCode);
        return statusCode;
    }

    public JSONObject getResponseJson(CloseableHttpResponse response) throws IOException {
        log.info("得到响应数据的String格式");
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject responseJson = JSON.parseObject(responseString);
        log.info("返回Json格式数据");
        return responseJson;
    }

}
