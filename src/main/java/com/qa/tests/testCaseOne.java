package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.qa.base.TestBase;
import com.qa.data.User;
import com.qa.parameters.PostParameters;
import com.qa.restclient.RestClient;
import com.qa.utils.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class testCaseOne extends TestBase {
    TestBase testBase;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    String host;
    String testCaseExcel;
    HashMap<String, String> postHeader = new HashMap<>();
    String tokenPath;
    HashMap<String, String> token = new HashMap<>();

    @BeforeClass
    public void setUp() {
        testBase = new TestBase();
        restClient = new RestClient();
        postHeader.put("Content-Type", "application/json");
        host = properties.getProperty("HOST");
        testCaseExcel = properties.getProperty("testCaseData");
        tokenPath = properties.getProperty("tokenPath");
    }

    @Test(dataProvider = "postData")
    public void logonPost(String url, String username, String password) throws IOException {
//        PostParameters postParameters = new PostParameters(username, password);
        User user = new User(username, password);
        String userJonString = JSON.toJSONString(user);
        closeableHttpResponse = restClient.post(host + url, userJonString, postHeader);
        int StatusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(StatusCode, 201, "error happened");

    }

    @DataProvider(name = "postData")
    public Object[][] post() throws IOException {
        return TestUtil.dtt(testCaseExcel, 0);
    }

    @DataProvider(name = "get")
    public Object[][] get() throws IOException {
        return TestUtil.dtt(testCaseExcel, 1);
    }

    @DataProvider(name = "delete")
    public Object[][] delete() throws IOException {
        return TestUtil.dtt(testCaseExcel, 2);
    }

    @Test(dataProvider = "get", dependsOnMethods = {"deleteApi"})
    public void getApi(String url) throws Exception {
        String getPath = String.valueOf(host + url).trim();
        closeableHttpResponse = restClient.get(getPath);

        // token获取实例
//        token = TestUtil.getToken(closeableHttpResponse, tokenPath);
//        之后用带参数的GET方法把token传递进去
        int StatusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(StatusCode, 200, "get request error");
        Reporter.log("请求路径： " + getPath);
        Reporter.log("状态码: " + StatusCode);

    }

    @Test(dataProvider = "delete")
    public void deleteApi(String url) throws IOException {
        String deletePath = String.valueOf(host + url).trim();
        closeableHttpResponse = restClient.get(deletePath);
        int StatusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(StatusCode, 201, "get request error");
        Reporter.log("请求路径： " + deletePath);
        Reporter.log("状态码: " + StatusCode);

    }

    @AfterClass
    public void tearDown() {
        System.out.println("测试结束");
    }


}
