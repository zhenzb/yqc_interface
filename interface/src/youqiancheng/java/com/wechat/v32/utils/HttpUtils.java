package com.wechat.v32.utils;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * @ClassName HttpUtils
 * @Description 回调Body内容处理工具
 * @Author zzb
 * @Date 2022/12/31 13:34
 * @Version 1.0
 */
public class HttpUtils {

    /** * 将通知参数转化为字符串 * @param request * @return */
    public static String readData(HttpServletRequest request) {

        BufferedReader br = null;
        try {

            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {

                if (result.length() > 0) {

                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {

            throw new RuntimeException(e);
        } finally {

            if (br != null) {

                try {

                    br.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
