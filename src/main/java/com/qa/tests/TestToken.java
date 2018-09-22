package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.data.User;
import com.qa.restclient.RestClient;
import com.qa.utils.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * refer to :接口自动化框架： 4.接口Token传递
 * https://blog.csdn.net/qq_34693151/article/details/81906177
 */
public class TestToken {
    CloseableHttpResponse closeableHttpResponse;
    RestClient restClient = new RestClient();

    @Test
    public void loginMethod() throws Exception {
        String auth = "{\"email\": \"peter@klaven\",\"password\": \"cityslicka\"}";
        JSONObject authJson = JSON.parseObject(auth);
        HashMap<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        closeableHttpResponse = restClient.post("/api/login",auth ,map);
        HashMap<String, String> logToken = TestUtil.getToken(closeableHttpResponse, "token");
        System.out.println(logToken);
    }
}
