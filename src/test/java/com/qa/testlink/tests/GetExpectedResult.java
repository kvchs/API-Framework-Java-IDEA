package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import com.qa.utils.TestLinkUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.testng.annotations.Test;

import java.util.List;

public class GetExpectedResult {
    TestLinkAPI api = TestLinkUtil.connect("https://reqres.in","0d071e8b44527d9fc0aff623edc22db9");

    @Test
    public void getExpectedResult(){
        TestCase testCase = api.getTestCaseByExternalId("zz-2", null);
        List<TestCaseStep> testCaseSteps = testCase.getSteps();
        for (TestCaseStep testCaseStep: testCaseSteps){
            String expectResult = testCaseStep.getExpectedResults();
            System.out.println(expectResult);
            String s1 = expectResult.split("<p>")[1].split("</p>")[0];
            String s2 = s1.replaceAll("<br />", "");
            String result = StringEscapeUtils.unescapeHtml4(s2);
            System.out.println(result);
        }

    }
}
