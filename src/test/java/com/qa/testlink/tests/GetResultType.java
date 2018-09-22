package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetResultType {
    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");

    @Test
    public void getResultMethod_Get(){
        String ExternalID = "zz-1";
        TestCase tc = api.getTestCaseByExternalId(ExternalID, null);
        String action = TestLinkUtil.getTestCaseAction(api, tc);
        JSONObject actionJson = JSON.parseObject(action);
        String method = TestUtil.getValueByJPath(actionJson, "method");
        System.out.println(method);
        System.out.println(TestLinkUtil.getRequestType(action));
    }

    @Test
    public void getResultMethod_Post(){
        String ExternalID = "zz-3";
        TestCase tc = api.getTestCaseByExternalId(ExternalID, null);
        String action = TestLinkUtil.getTestCaseAction(api, tc);
        System.out.println(action.trim());
        JSONObject actionJson = JSON.parseObject(action.trim());
        String method = TestUtil.getValueByJPath(actionJson, "method");
        System.out.println(method);

        System.out.println(TestLinkUtil.getRequestType(action));
    }

}
