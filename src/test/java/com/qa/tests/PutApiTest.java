package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.data.User;
import com.qa.restclient.RestClient;
import com.qa.utils.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class PutApiTest extends TestBase{
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = properties.getProperty("HOST");
        url = host + "/api/users/2";
    }

    @Test
    public void putTest() throws IOException {
        restClient = new RestClient();

        //准备请求头信息
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        User user = new User("charley", "test->java development");
        String userJsonString = JSON.toJSONString(user);

        closeableHttpResponse = restClient.put(url, userJsonString, headerMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "error");

        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);

        String name = TestUtil.getValueByJPath(responseJson, "name");
        String job = TestUtil.getValueByJPath(responseJson, "job");
        System.out.println(name + "============" + job);
        Assert.assertEquals(name, "charley", "name error ");
        Assert.assertEquals(job, "test->java development", "job error ");
    }

}
