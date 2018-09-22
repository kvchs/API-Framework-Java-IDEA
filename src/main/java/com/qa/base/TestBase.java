package com.qa.base;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    final static Logger log = Logger.getLogger(TestBase.class);
    public Properties properties;

    public int RESPONSE_STATUS_CODE_200 = 200;
    public int RESPONSE_STATUS_CODE_201 = 201;
    public int RESPONSE_STATUS_CODE_404 = 404;
    public int RESPONSE_STATUS_CODE_500 = 500;
    public TestBase() {
        try{
            properties = new Properties();
            log.info("读取配置文件...");
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties");
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
