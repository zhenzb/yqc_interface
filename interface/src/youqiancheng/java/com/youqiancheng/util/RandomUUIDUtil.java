package com.youqiancheng.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @ClassName RandomUUIDUtil
 * @Description TODO
 * @Author zzb
 * @Date 2022/2/17 21:33
 * @Version 1.0
 **/
@Component
public class RandomUUIDUtil {

    public static String getUUID(){
       return UUID.randomUUID().toString().replaceAll("-","");
    }
}
