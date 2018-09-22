package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class GetProjectInfo {
    TestLinkAPI api;

    @Test
    public void getProjectInfo(){
        api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");
        TestProject[] projects = api.getProjects();
        int projectId = 0;
        for (TestProject project: projects){
            System.out.println(project.getName() + " == " + project.getId());
            if ("api-test-java".equals(project.getName())){
                projectId = project.getId();
            }
        }

        TestPlan[] plans = api.getProjectTestPlans(projectId);
        for (TestPlan plan: plans){
            System.out.println(plan.getName());
        }
    }
}
