package com.qa.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;

public class TestUtil {

    /**
     * 获取返回的token,使用JsonPath获取Json路径
     * @param closeableHttpResponse
     * @param jsonPath
     * @return
     */
    public static HashMap<String, String> getToken(CloseableHttpResponse closeableHttpResponse, String jsonPath) throws Exception {
        HashMap<String, String> responseToken = new HashMap<>();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        ReadContext context = JsonPath.parse(responseString);
        String Token = context.read(jsonPath);
        if (null == Token || "".equals(Token)){
            throw new Exception("Token 不存在");
        }
        responseToken.put("x-ba-token", Token);
        return responseToken;
    }

    /**
     * 获取状态码
     * @param closeableHttpResponse
     * @return
     */
    public static int getStatusCode(CloseableHttpResponse closeableHttpResponse){
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        return statusCode;
    }

    /**
     * 遍历excel,sheet参数
     * @param filePath
     * @param sheetId
     * @return
     */
    public static Object[][] dtt(String filePath, int sheetId) throws IOException {
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheetAt(sheetId);
        int numberRow = sheet.getPhysicalNumberOfRows();
        int numberCell = sheet.getRow(0).getLastCellNum();

        Object[][] dttData = new Object[numberRow][numberCell];
        for (int i = 0; i < numberRow; i++){
            if (null==sheet.getRow(i) || "".equals(sheet.getRow(i))){
                continue;
            }
            for (int j = 0; j < numberCell;j++){
                if (null==sheet.getRow(i).getCell(j) || "".equals(sheet.getRow(i).getCell(j))){
                    continue;
                }
                XSSFCell cell = sheet.getRow(i).getCell(j);
                cell.setCellType(CellType.STRING);
                dttData[i][j] = cell.getStringCellValue();
            }
        }
        return dttData;

    }

    public static String getValueByJPath(JSONObject responseJson, String path){
        Object obj = responseJson;
        for (String s: path.split("/")){
            if (!s.isEmpty()){
                if(!(s.contains("[") || s.contains("]"))){
                    obj = ((JSONObject) obj).get(s);
                }else if ((s.contains("[") || s.contains("]"))){
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]","" )));

                }
            }
        }
        return obj.toString();
    }

//    public static HashMap<String, String> getToken(CloseableHttpResponse closeableHttpResponse, String jsonPath) throws Exception {
//        HashMap<String, String> responseToken = new HashMap<>();
//        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
//        ReadContext context = JsonPath.parse(responseString);
//        String Token = context.read(jsonPath);
//        if (null == Token || "".equals(Token)){
//            throw new Exception("token不存在");
//        }
//        responseToken.put("x-ba-token", Token);
//        return responseToken;
//    }
}
