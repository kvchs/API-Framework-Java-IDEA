package com.qa.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.*;

/**
 * 使用extentReport插件实现report样式
 * 方法：实现testNG的IReporter接口，再通过testng.xml中的listener标签进行监听
 * <p>
 * <p>
 * TestNg的IReporter接口的使用
 * IReporter接口是干嘛的？就是让用户自定义报告的，很多人想要自定义报告，于是乎找各种插件，比如什么testng-xslt啊，reportng啊，各种配置，最后出来的结果，还不能定制化，但为什么不能自已定制一份呢？testng的IReporter接口就接供了这样的功能，我们只需要实现这个接口，并加上监听，就能拿到所有的信息了，你可以把这些信息存数据库，或者自已生成一个html，亦或者写在EXCEL里啊等等，都可以，下面给出IReporter接口的实现方式：
 * https://www.cnblogs.com/llining/p/5479139.html
 */
public class ExtentTestNGReporterListener implements IReporter {
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "index.html";
    private ExtentReports extentReports;

    @Override
    public void generateReport(List<XmlSuite> list, List<ISuite> list1, String s) {
        init();
        boolean createSuiteNode = false;
        if (list1.size() > 1) {
            createSuiteNode = true;
        }

        for (ISuite suite : list1) {
            Map<String, ISuiteResult> result = suite.getResults();
            //如果suite里面没有任何用例，直接跳过，不在报告里面生成
            if (result.size() == 0) {
                continue;
            }
            //统计suite下的成功，失败，跳过的总用例数
            int suiteFailSize = 0;
            int suitePassSize = 0;
            int suiteSkipSize = 0;
            ExtentTest suiteTest = null;
            if (createSuiteNode) {
                suiteTest = extentReports.createTest(suite.getName()).assignCategory(suite.getName());
            }
            boolean createSuiteResultNode = false;
            if (result.size() > 1) {
                createSuiteResultNode = true;
            }
            for (ISuiteResult r : result.values()) {
                ExtentTest resultNode;
                ITestContext context = r.getTestContext();
                if (createSuiteResultNode) {
                    if (null == suiteTest) {
                        resultNode = extentReports.createTest(r.getTestContext().getName());
                    } else {
                        resultNode = suiteTest.createNode(r.getTestContext().getName());
                    }
                } else {
                    resultNode = suiteTest;
                }
                if (resultNode != null) {
                    resultNode.getModel().setName(suite.getName() + ":" + r.getTestContext().getName());
                    if (resultNode.getModel().hasCategory()) {
                        resultNode.assignCategory(r.getTestContext().getName());
                    } else {
                        resultNode.assignCategory(suite.getName(), r.getTestContext().getName());
                    }
                    resultNode.getModel().setStartTime(r.getTestContext().getStartDate());
                    resultNode.getModel().setEndTime(r.getTestContext().getEndDate());

                    //统计SuiteResult下的数据
                    int passSize = r.getTestContext().getPassedTests().size();
                    int failSize = r.getTestContext().getFailedTests().size();
                    int skipSize = r.getTestContext().getSkippedTests().size();
                    suitePassSize += passSize;
                    suiteFailSize += failSize;
                    suiteSkipSize += skipSize;
                    if (failSize > 0) {
                        resultNode.getModel().setStatus(Status.FAIL);
                    }
                    resultNode.getModel().setDescription(String.format("Pass: %s; Fail: %s,; Skip: %s;", passSize, failSize, skipSize));
                }
                buildTestNodes(resultNode, context.getFailedTests(), Status.FAIL);
                buildTestNodes(resultNode, context.getSkippedTests(), Status.SKIP);
                buildTestNodes(resultNode, context.getPassedTests(), Status.PASS);
            }
            if (suiteTest!= null){
                suiteTest.getModel().setDescription(String.format("Pass: %s; Fail: %s,; Skip: %s;", suitePassSize, suiteFailSize, suiteSkipSize));
                if(suiteFailSize>0){
                    suiteTest.getModel().setStatus(Status.FAIL);
                }
            }
        }
        extentReports.flush();
    }

    private void init() {
        File reportDir = new File(OUTPUT_FOLDER);
        if (!reportDir.exists() && !reportDir.isDirectory()) {
            reportDir.mkdir();
        }
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
        htmlReporter.config().setDocumentTitle("API自动化测试报告");
        htmlReporter.config().setReportName("API自动化测试报告");

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
//        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        htmlReporter.config().setCSS(".node.level-1 ul{display:none;} .node.level-1.active ul{display:block;}");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setReportUsesManualConfiguration(true);
    }

    private void buildTestNodes(ExtentTest extentTest, IResultMap tests, Status status) {
        String[] categories = new String[0];
        if (extentTest != null) {
            List<TestAttribute> categoryList = extentTest.getModel().getCategoryContext().getAll();
            categories = new String[categoryList.size()];
            for (int index = 0; index < categoryList.size(); index++) {
                categories[index] = categoryList.get(index).getName();
            }
        }

        ExtentTest test;
        if (tests.size() > 0) {
            Set<ITestResult> treeSet = new TreeSet<>((o1, o2) -> o1.getStartMillis() < o2.getStartMillis() ? -1 : 1);
            treeSet.addAll(tests.getAllResults());
            for (ITestResult result : treeSet) {
                Object[] parameters = result.getParameters();
                String name = "";
                for (Object param : parameters) {
                    name += param.toString();
                }
                for (int i = 0; i < parameters.length; i++) {
                    name = parameters[0].toString();
                }
                if (name.length() > 0) {
                    if (name.length()>100) {
                        name = name.substring(0, 100) + "...";
                    }
                } else {
                    name = result.getMethod().getMethodName();
                }
                if (extentTest == null) {
                    test = extentReports.createTest(name);
                } else {
                    test = extentTest.createNode(name).assignCategory(categories);
                }

//                test.getModel().setDescription();
                for (String group : result.getMethod().getGroups()) {
                    test.assignCategory(group);
                }
                List<String> outputlist = Reporter.getOutput(result);
                for (String output : outputlist) {
//                    将log输出到报告中
                    test.debug(output);
                }
                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                } else {
                    test.log(status, "Test " + status.toString().toUpperCase() + "ED");
                }

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }

    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
