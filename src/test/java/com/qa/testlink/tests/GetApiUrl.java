package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetApiUrl {
    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");

    @Test
    public void getApiUrl(){
        TestCase testCase = api.getTestCaseByExternalId("zz-1", null);
        String ac = TestLinkUtil.getTestCaseAction(api, testCase);
        JSONObject jsonObject = JSON.parseObject(ac);
        System.out.println(TestUtil.getValueByJPath(jsonObject, "url"));
    }
}
