package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.qa.utils.TestLinkUtil;
import com.qa.utils.TestUtil;
import org.testng.annotations.Test;

public class GetSuiteANdCases {
    TestLinkAPI api = TestLinkUtil.connect();

    @Test
    public void getCases(){
        TestCase[] cases = TestLinkUtil.getTestCaseForSuite(api, "api-test-java");
        for (TestCase testCase: cases){
            System.out.println(testCase.getName() +" " + testCase.getId());
        }
    }
}
