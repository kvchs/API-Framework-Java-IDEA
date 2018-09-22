package main;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.utils.TestLinkUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class RunAllCase {
    final static Logger log = Logger.getLogger(RunAllCase.class);

    public static void main(String[] args) throws IOException {

        TestBase testBase = new TestBase();

        final String url = testBase.properties.getProperty("testLinkUrl");
        final String devKey = testBase.properties.getProperty("devKey");
        final String HOST = testBase.properties.getProperty("HOST");
        log.info("测试服务器地址： " + HOST);
        final String projectName = testBase.properties.getProperty("projectName");
        log.info("测试的项目名称： " + projectName);

        log.info("连接测试环境");
        final TestLinkAPI api = TestLinkUtil.connect(url, devKey);

        log.info("获取测试套件");
        TestSuite[] testSuites = TestLinkUtil.getFirstLeverTestSuite(api, projectName);

        for (TestSuite testSuite : testSuites) {
//            System.out.println(testSuite.getName());
            TestCase[] testCases = TestLinkUtil.getTestCaseForSuite(api, testSuite);
            log.info("成功获取测试套件"+ testSuite.getName() +"下的测试用例");

            for (TestCase testCase: testCases){
                log.info("根据External id查找测试用例对象");
                TestCase tc = api.getTestCaseByExternalId(testCase.getFullExternalId(), null);
                List<TestCaseStep> testCaseSteps = tc.getSteps();
                for(TestCaseStep testCaseStep: testCaseSteps){
                    String action = testCaseStep.getActions();
                    log.info("获取到测试用例步骤中的action");
//                    System.out.println(action);

                    String expectResult = TestLinkUtil.getExpectedResult(api, testCase);
                    String sendRequest = TestLinkUtil.getTestCaseAction(api, testCase);
//                    System.out.println(result);
                    String requestType = TestLinkUtil.getRequestType(sendRequest);
                    log.info("获取到了接口请求方式: " + requestType);

                    if ("get".equalsIgnoreCase(requestType)){
                        String fullUrl = HOST + TestLinkUtil.getApiUrl(sendRequest);
                        log.info("测试接口为：" + fullUrl);
                        String expectCode = TestLinkUtil.getStatusCode(expectResult);
                        log.info("解析出期待的结果为: " + expectCode);

                        RestClient restClient = new RestClient();
                        CloseableHttpResponse response = restClient.get(fullUrl);
                        log.info("Get请求发送成功 "+ fullUrl);

                        int responseCode = restClient.getStatusCode(response);
                        if ((String.valueOf(responseCode)).equalsIgnoreCase(expectCode)){
                            log.info("断言成功，测试通过");
                        }else{
                            log.error("实际: " + responseCode + " ，期望结果: " + expectCode +"; 结果错误");
                        }
                    }else if("post".equalsIgnoreCase(requestType)){
                        log.info("该请求是POST请求");
                        String fullUrl = HOST + TestLinkUtil.getApiUrl(sendRequest);
                        log.info("测试接口为：" + fullUrl);
                        String expectCode = TestLinkUtil.getStatusCode(expectResult);
                        log.info("解析出期待的结果为: " + expectCode);

                        String jsonParam = TestLinkUtil.getJsonParam(sendRequest);
//                        System.out.println(jsonParam);
                        HashMap<String, String> headerMap = new HashMap<>();
                        headerMap.put("Content-Type", "application/json");
                        RestClient restClient = new RestClient();
                        CloseableHttpResponse response = restClient.post(fullUrl, jsonParam, headerMap);
                        log.info("POST请求发送成功 "+ fullUrl);

                        int responseCode = restClient.getStatusCode(response);
                        if ((String.valueOf(responseCode)).equalsIgnoreCase(expectCode)){
                            log.info("断言成功，测试通过");
                        }else{
                            log.error("实际: " + responseCode + " ，期望结果: " + expectCode +"; 结果错误");
                        }
                    }else {
                        log.info("=============请添加其他请求处理方式=================");
                    }

                }
            }
        }

    }
}
