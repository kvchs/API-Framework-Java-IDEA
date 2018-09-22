package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class GetTestSuite {

    TestLinkAPI api;

    @Test
    public void getSuite(){
        api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");
        int projectId = TestLinkUtil.getProjectIdByName(api, "api-test-java");
        TestSuite[] suites = api.getFirstLevelTestSuitesForTestProject(projectId);
        for (TestSuite suite: suites){
            System.out.println(suite.getName());
        }

    }
}
