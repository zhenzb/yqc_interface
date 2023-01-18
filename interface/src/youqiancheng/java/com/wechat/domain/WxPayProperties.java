package com.wechat.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName WxPayProperties
 * @Description TODO
 * @Author zzb
 * @Date 2022/12/31 8:43
 * @Version 1.0
 */
@Data
public class WxPayProperties {
    @Value("${wxpay.app_id}")
    private String app_id;

    @Value("${wxpay.sub_app_id}")
    private String sub_app_id;

    @Value("${wxpay.spbill_create_ip}")
    private String spbill_create_ip;

    @Value("${wxpay.key}")
    private String key;

    @Value("${wxpay.mch_id}")
    private String mch_id;

    @Value("${wxpay.sub_mch_id}")
    private String sub_mch_id;

    @Value("${wxpay.notify_url}")
    private String notify_url;
}
