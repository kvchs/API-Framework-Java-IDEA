package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetJsonParam {

    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");

    @Test
    public void getJsonParam(){
        TestCase testCase = api.getTestCaseByExternalId("zz-3", null);

        String action = TestLinkUtil.getTestCaseAction(api, testCase);
        JSONObject actionJson = JSON.parseObject(action);
        String jsonString = TestUtil.getValueByJPath(actionJson, "param");
//        System.out.println(jsonString);

        System.out.println(TestLinkUtil.getJsonParam(action));
    }
}
