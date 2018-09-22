package com.qa.testlink.tests;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import com.qa.utils.TestLinkUtil;
import org.testng.annotations.Test;

public class ConnectTest2 {
    TestLinkAPI api;

    @Test
    public void testConnect(){
        api = TestLinkUtil.connect();
        // 获取到当前testLink下的所有项目，返回一个project对象
        System.out.println(api.getProjects()[0].getName());
        System.out.println(api.getProjectTestPlans(api.getProjects()[0].getId())[0].getName());
    }
}
