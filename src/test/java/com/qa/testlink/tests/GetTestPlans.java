package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class GetTestPlans {
    TestLinkAPI api;

    @Test
    public void getTestPlans(){
        api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");
        int projectId = TestLinkUtil.getProjectIdByName(api, "api-test-java");
        TestPlan[] plans = TestLinkUtil.getAllTestPlanUnderProject(api, projectId);
        for (TestPlan plan: plans){
            System.out.println(plan.getName());
        }
    }
}
