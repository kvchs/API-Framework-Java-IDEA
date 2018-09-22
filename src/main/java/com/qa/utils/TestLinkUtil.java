package com.qa.utils;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.base.TestBase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestLinkUtil {
    /**
     * 连接TestLink
     * @return
     */
    public static TestLinkAPI connect(){
        TestBase testBase = new TestBase();
        String url = testBase.properties.getProperty("testLinkUrl");
        String devKey = testBase.properties.getProperty("devKey");
        TestLinkAPI api = null;
        URL testLinkUrl = null;
        try {
            testLinkUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
        try {
            api = new TestLinkAPI(testLinkUrl, devKey);
        }catch (Exception e){
            e.printStackTrace(System.err);
            System.exit(-1);
        }
        System.out.println(api.ping());
        return api;
    }

    /**
     * 根据项目的名称获取项目的ID
     * @param api
     * @param projectName
     * @return
     */
    public static int getProjectIdByName(TestLinkAPI api, String projectName){
        TestProject project = api.getTestProjectByName(projectName);
        int projectId = project.getId();
        return projectId;
    }

    /**
     * 根据项目ID获取项目下的所有测试计划
     * @param api
     * @param projectId
     * @return
     */
    public static TestPlan[] getAllTestPlanUnderProject(TestLinkAPI api, int projectId){
        TestPlan[] plans = api.getProjectTestPlans(projectId);
        return plans;

    }

    /**
     * 根据项目名称获取项目下的顶层测试套件数组对象
     * @param api
     * @param projectName
     * @return
     */
    public static TestSuite[] getFirstLeverTestSuite(TestLinkAPI api, String projectName){
        int projectId = TestLinkUtil.getProjectIdByName(api, projectName);
        TestSuite[] suites = api.getFirstLevelTestSuitesForTestProject(projectId);
        return suites;
    }


    public static TestCase[] getTestCaseForSuite(TestLinkAPI api, String projectName){
        TestSuite[] suites = TestLinkUtil.getFirstLeverTestSuite(api, projectName);

        List<TestCase> list = new ArrayList<>();
        for (int i = 0; i< suites.length; i++){
            TestCase[] a = api.getTestCasesForTestSuite(suites[i].getId(), true, null);
            list.addAll(Arrays.asList(a));
        }
        TestCase[] cases = new TestCase[list.size()];
        for (int i = 0; i< list.size(); i++){
            cases[i] = list.get(i);
        }
        return cases;
    }
}
