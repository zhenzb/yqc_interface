package com.youqiancheng.controller.wechatpay.weixinpay.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * @Author Captain Ren
 * @Description 这里定义服务层需要请求器标准接口
 * @Date 2019/4/16 0016 15:55
 * @Param
 * @return
 **/
public interface IServiceRequest
{
    // Service依赖的底层https请求器必须实现这么一个接口
    public String sendPost(String api_url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;
}
