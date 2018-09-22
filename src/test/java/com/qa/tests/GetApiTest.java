package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.utils.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetApiTest extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    final static Logger log = Logger.getLogger(GetApiTest.class);

    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = properties.getProperty("HOST");
        log.info("测试的host: " + host);
        url = host + "/api/users";
    }

    @Test
    public void getAPITest() throws IOException {
        log.info("开始执行测试用例");
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        log.info("判断响应状态码");
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "response code is not 200:error");

        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = JSON.parseObject(responseString);
        System.out.println(responseJson);

        log.info("判读返回值的正确性");
        String s = TestUtil.getValueByJPath(responseJson, "total");
        String s2 = TestUtil.getValueByJPath(responseJson, "data[2]/first_name");
        System.out.println(s2);
        Assert.assertEquals(s2, "Emma", "first name is not Emma");
        log.info("结束测试任务");
    }
}
