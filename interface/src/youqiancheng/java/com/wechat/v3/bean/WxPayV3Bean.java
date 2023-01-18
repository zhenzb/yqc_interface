package com.wechat.v3.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName WxPayV3Bean
 * @Description TODO
 * @Author zzb
 * @Date 2022/12/31 11:54
 * @Version 1.0
 */

@ConfigurationProperties(prefix = "v3")
@Data
@ToString
public class WxPayV3Bean {
    @Value("${wxpay.app_id}")
    private String appId;
    @Value("${wxpay.mch_id}")
    private String mchId;

    private String mchSerialNo;

    private String apiKey3;
    private String keyPath;
    private String certPath;
    private String certP12Path;
    private String platformCertPath;
    private String notifyUrl;
}
