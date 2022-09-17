package com.youqiancheng.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName TianXingJianAPI
 * @Description TODO
 * @Author zzb
 * @Date 2022/3/20 19:34
 * @Version 1.0
 **/
public class TianXingJianAPI {


    /**
     * @param number
     *            :请求接口
     *            :参数
     * @return 返回结果
     */
    public String getOrderTracesByJson(String number) {
        String httpUrl = "http://api.tianapi.com/kuaidi/index?key=8b35a041e0c31951973501cf22a23aa7&number="+number;
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        TianXingJianAPI tianXingJianAPI = new TianXingJianAPI();
        String httpUrl = "http://api.tianapi.com/kuaidi/index?key=8b35a041e0c31951973501cf22a23aa7&number=462286030732158";
        String jsonResult = tianXingJianAPI.getOrderTracesByJson(httpUrl);

        System.out.println(jsonResult);
    }
}
