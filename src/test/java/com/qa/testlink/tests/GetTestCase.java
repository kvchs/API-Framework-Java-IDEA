package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class GetTestCase {

    TestLinkAPI api;

    @Test
    public void getTestCases(){
        api = TestLinkUtil.connect();
        TestSuite[] suites = TestLinkUtil.getFirstLeverTestSuite(api, "api-test-java");
        for (TestSuite suite: suites){
            System.out.println(suite.getName());
            TestCase[] cases = api.getTestCasesForTestSuite(suite.getId(), true, null);
            for(TestCase testCase: cases)
                System.out.println(testCase.getName());
        }
    }
}
