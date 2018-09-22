package com.qa.utils;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import org.apache.commons.lang3.StringEscapeUtils;

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
    public static TestLinkAPI connect(String url, String devKey){
//        TestBase testBase = new TestBase();
//        String url = testBase.properties.getProperty("testLinkUrl");
//        String devKey = testBase.properties.getProperty("devKey");
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


    public static TestCase[] getTestCaseForSuite(TestLinkAPI api, TestSuite testSuite){
        TestCase[] cases = api.getTestCasesForTestSuite(testSuite.getId(), true, null);

//        List<TestCase> list = new ArrayList<>();
//        for (int i = 0; i< suites.length; i++){
//            TestCase[] a = api.getTestCasesForTestSuite(suites[i].getId(), true, null);
//            list.addAll(Arrays.asList(a));
//        }
//        TestCase[] cases = new TestCase[list.size()];
//        for (int i = 0; i< list.size(); i++){
//            cases[i] = list.get(i);
//        }
        return cases;
    }


    /**
     * 根据测试用例对象，获取测试步骤中的action
     * @param api
     * @param testCase
     * @return
     */
    public static String getTestCaseAction(TestLinkAPI api, TestCase testCase){
        TestCase tc = api.getTestCaseByExternalId(testCase.getFullExternalId(), null);
        List<TestCaseStep> testCaseSteps = tc.getSteps();

        String action = null;
        for (TestCaseStep testCaseStep: testCaseSteps){
            String orginalStr = testCaseStep.getActions();
            //去除p标签
            String s1 = orginalStr.split("<p>")[1].split("</p>")[0];
            String s2 = s1.replaceAll("<br />", "" );
            // 把&quot改成正常显示的双引号
            action = StringEscapeUtils.unescapeHtml4(s2);
        }
        return action;
    }


    /**
     * 获取测试步骤的期望结果
     * @param api
     * @param ttestCase
     * @return
     */
    public static String getExpectedResult(TestLinkAPI api, TestCase ttestCase){
        TestCase testCase = api.getTestCaseByExternalId(ttestCase.getFullExternalId(), null);
        List<TestCaseStep> testCaseSteps = testCase.getSteps();
        String result = null;
        for (TestCaseStep testCaseStep: testCaseSteps){
            String expectResult = testCaseStep.getExpectedResults();
//            System.out.println(expectResult);
            String s1 = expectResult.split("<p>")[1].split("</p>")[0];
            String s2 = s1.replaceAll("<br />", "");
            result = StringEscapeUtils.unescapeHtml4(s2);
//            System.out.println(result);
        }
        return result;
    }

    /**
     * 获取请求的类型
     * @param stepAction
     * @return
     */
    public static String getRequestType(String stepAction){
        JSONObject actionJson = JSON.parseObject(stepAction);
        String resultType = TestUtil.getValueByJPath(actionJson, "method");
        return resultType;
    }


    /**
     * 获取JSON对象中的URL参数
     * @param stepAction
     * @return
     */
    public static String getApiUrl(String stepAction){
        JSONObject jsonObject = JSON.parseObject(stepAction);
        String apiUrl = TestUtil.getValueByJPath(jsonObject, "url");
        return apiUrl;
    }


    public static String getJsonParam(String stepAction){
        JSONObject actionJson = JSON.parseObject(stepAction);
        String jsonString = TestUtil.getValueByJPath(actionJson, "param");
        return jsonString;
    }

//    public static String getResultCode(TestLinkAPI api, TestCase testCase) {
//        String result = TestLinkUtil.getResultCode(api, testCase);
//        JSONObject actionJson = JSON.parseObject(result);
//        String statusCode = TestUtil.getValueByJPath(actionJson, "code");
//        return statusCode;
//    }

    /**
     * 获取期望结果中的状态码
     * @param result
     * @return
     */
    public static String getStatusCode(String result){
        JSONObject actionJson = JSON.parseObject(result);
        String statusCode = TestUtil.getValueByJPath(actionJson, "code");
        return statusCode;
    }
}
