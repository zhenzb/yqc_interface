package com.wechat;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.HttpHeaders;
import com.handongkeji.util.Constants;
import com.handongkeji.util.HttpUtils;
import com.handongkeji.util.JsonBean;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.util.HttpUtil;
import com.youqiancheng.util.R;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoginController zzb
 * @Description TODO
 * @Author zzb
 * @Date 2022/7/6 21:54
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/wechatMbUser")
public class LoginController {

    @GetMapping("getUserOpenId")
    @ApiOperation(value="小程序获取授权", notes="小程序授权登录获取OpenId")
    public JsonBean getUserOpenId(String js_code) {
        JsonBean result = new JsonBean();
        String httpURL = "https://api.weixin.qq.com/sns/jscode2session?&grant_type=authorization_code" + "&appid=wxeb5de11b49702a70"
                + "&secret=e921b4f7077b2be78217a7d4b4ac8a69" + "&js_code=" + js_code;
        String rsForAccessToken = HttpUtils.requestHttp(httpURL);
        JSONObject obj = JSONObject.parseObject(rsForAccessToken);
        log.info("根据code:{}，获取openId的result{}",js_code,obj.toJSONString());
        String openId = obj.getString("openid");
        String session_key = obj.getString("session_key");
        //obj.getString("unionid")
        Map<String,Object> map = new HashMap<>();
        map.put("openId", openId);
        map.put("session_key", session_key);
        result.setData(map);
        result.setStatus(Constants.$Success);
        result.setMessage(Constants.EXECUTION_SUCCESS_MESSAGE);
        log.info("获取微信openId:{}",openId);
        return result;
    }


    /**
     * 解密用户信息
     */
    @GetMapping("/getUserPhone")
    public ResultVo getUserPhone(String code) {
        //获取token
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxeb5de11b49702a70&secret=e921b4f7077b2be78217a7d4b4ac8a69";
        String rsForAccessToken = HttpUtils.requestHttp(tokenUrl);
        JSONObject obj = JSONObject.parseObject(rsForAccessToken);
        log.info("获取token的result{}",obj.toJSONString());
        String access_token = obj.getString("access_token");
        // 使用前端code获取手机号码 参数为json格式
        net.sf.json.JSONObject params = new  net.sf.json.JSONObject();
        params.put("code", code);
        String URL ="https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + access_token;
        String post = com.youqiancheng.util.HttpUtils.sendPost(params, URL);
        System.out.println("获取手机号："+post);
        JSONObject userPhone = JSONObject.parseObject(post);
        JSONObject phone_info = userPhone.getJSONObject("phone_info");
        return ResultVOUtils.success(phone_info.getString("phoneNumber"));
    }
}
