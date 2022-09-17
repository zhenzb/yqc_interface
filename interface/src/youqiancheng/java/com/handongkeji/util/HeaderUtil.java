package com.handongkeji.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class HeaderUtil {

    public static Map <String,String> getHeaderMap (HttpServletRequest request) {
        String hfmauth = request.getHeader("hfmauth");
        String params = null;
        try {
            params = MD5Util.decode(hfmauth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map <String,String> map = JSON.parseObject(params, Map.class);
        for (String obj : map.keySet()){
            System.out.println("key为："+obj+"值为：" + map.get(obj));
        }
        log.info((String) map.get("authcode"));
        return map;
    }



}
