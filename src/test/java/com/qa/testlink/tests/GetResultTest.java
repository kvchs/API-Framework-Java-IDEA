package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetResultTest {

    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");
    public void getResult(){
        String ExternalID = "zz-1";
        TestCase tc = api.getTestCaseByExternalId(ExternalID, null);
        System.out.println(TestLinkUtil.getExpectedResult(api, tc));
    }
}
