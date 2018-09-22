package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectTest {

    @Test
    public void connectTestLink() throws MalformedURLException {
        String testlink_url = "http://localhost/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
        String devKey = "0d071e8b44527d9fc0aff623edc22db9";

        URL testLinkURL = new URL(testlink_url);
        /**
         * 调用TestLinkAPI构造方法
         * api是TestLinkAPI对象，是我们操作testLink的核心部分
         */
        TestLinkAPI api = new TestLinkAPI(testLinkURL, devKey);
        System.out.println(api.ping());
    }
}
