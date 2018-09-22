package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetStatusCode {

    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");

    @Test
    public void getJsonParam(){
        TestCase testCase = api.getTestCaseByExternalId("zz-3", null);

        String result = TestLinkUtil.getExpectedResult(api, testCase);
        JSONObject actionJson = JSON.parseObject(result);
        String statusCode = TestUtil.getValueByJPath(actionJson, "code");
//        System.out.println(jsonString);

        System.out.println(statusCode);
        System.out.println(TestLinkUtil.getStatusCode(result));
    }
}
