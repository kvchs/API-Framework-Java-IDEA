package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetSuiteANdCases {
    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");

    @Test
    public void getCases(){
//        TestCase[] cases = TestLinkUtil.getTestCaseForSuite(api, "api-test-java");
        TestSuite[] testSuites = TestLinkUtil.getFirstLeverTestSuite(api, "api-test-java");
        TestCase[] cases = TestLinkUtil.getTestCaseForSuite(api, testSuites[0]);
        for (TestCase testCase: cases){
            System.out.println(testCase.getName() +" " + testCase.getId());
        }
    }
}
