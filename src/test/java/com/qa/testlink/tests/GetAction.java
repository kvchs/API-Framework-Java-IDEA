package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class GetAction {
    TestLinkAPI api;

    @Test
    public void getActionString(){
        api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");
        TestSuite[] testSuites = TestLinkUtil.getFirstLeverTestSuite(api, "api-test-java");
        TestCase[] testCases = TestLinkUtil.getTestCaseForSuite(api, testSuites[0]);
        for (TestCase testCase: testCases){
            String action = TestLinkUtil.getTestCaseAction(api, testCase);
            System.out.println(action);
        }

    }
}
