package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class GetTestSuite {

    TestLinkAPI api;

    @Test
    public void getSuite(){
        api = TestLinkUtil.connect();
        int projectId = TestLinkUtil.getProjectIdByName(api, "api-test-java");
        TestSuite[] suites = api.getFirstLevelTestSuitesForTestProject(projectId);
        for (TestSuite suite: suites){
            System.out.println(suite.getName());
        }

    }
}
