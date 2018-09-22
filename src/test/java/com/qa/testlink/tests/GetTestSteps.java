package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.utils.TestLinkUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetTestSteps {

    TestLinkAPI api;

    @Test
    public void getSteps(){
        api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");
        TestSuite[] testSuites = TestLinkUtil.getFirstLeverTestSuite(api, "api-test-java");
        TestCase[] cases = TestLinkUtil.getTestCaseForSuite(api, testSuites[0]);
        List<TestCase> list = Arrays.asList(cases);
        List<TestCaseStep> steps = null;
        for (TestCase testCase: list){
            System.out.println(testCase.getFullExternalId() + " getFullExternalId");
            //获取单个测试用例对象，并得到测试用例下的步骤和期望结果
            TestCase tc = api.getTestCaseByExternalId(testCase.getFullExternalId(), null);
            steps = tc.getSteps();
            for(TestCaseStep testCaseStep: steps){
//                System.out.println(testCaseStep.getActions());
                String orginalStr = testCaseStep.getActions();
                //去除p标签
                String s1 = orginalStr.split("<p>")[1].split("</p>")[0];
                String s2 = s1.replaceAll("<br />", "" );
                // 把&quot改成正常显示的双引号
                String s3 = StringEscapeUtils.unescapeHtml4(s2);
                System.out.println(s3);

            }
        }
    }
}
