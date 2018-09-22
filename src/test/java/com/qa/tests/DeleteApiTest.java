package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteApiTest extends TestBase {
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
    public void deleteApiTest() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.delete(url);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 204, "not 204");
    }
}
