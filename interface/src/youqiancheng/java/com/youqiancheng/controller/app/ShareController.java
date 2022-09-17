package com.youqiancheng.controller.app;

import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.wechatpay.weixinpay.sign.SignUtil;
import com.youqiancheng.vo.result.ResultVo;
import io.lettuce.core.dynamic.annotation.Value;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = {"微信分享朋友,朋友圈"})
@RestController
@RequestMapping(value = "weixin")
public class ShareController {

/* @Value("${weixin.appid}")
    private  String appid;*/


     SignUtil signUtil = new SignUtil();

    @ApiOperation(value = "签名")
    @PostMapping("/signa")
    ResultVo signa() {
        Map map= new HashMap();
        //要有微信对接分享朋友圈的公共号
       // map.put("appId",appid);
        //签名时间戳
        map.put("timestamp",System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-", "").length());
        String nonce_str=uuid.toString().replace("-", "");
        //生产签名字符串
        map.put("nonceStr",nonce_str);

         String sing=signUtil.createSign(map);
        //签名
        map.put("signature",sing);

        return ResultVOUtils.success(map);
    }

    @ApiOperation(value = "分享朋友圈内容")
    @PostMapping("/fenxing")
    ResultVo saveNewUser() {
        Map map= new HashMap();

        // 分享标题要从数据库查
       // map.put("title",appid);
        // 分享描述
        map.put("desc",System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-", "").length());
        String nonce_str=uuid.toString().replace("-", "");
        // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        map.put("link",nonce_str);
        String sing=signUtil.createSign(map);
        // 分享图标
        map.put("imgUrl",sing);
        return ResultVOUtils.success(map);
    }

}
