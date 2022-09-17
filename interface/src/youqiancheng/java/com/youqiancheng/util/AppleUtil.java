/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: AppleUtil
 * Author:   ytf
 * Date:     2020/6/9 16:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.handongkeji.config.exception.JsonException;
import com.youqiancheng.vo.result.ResultEnum;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/6/9
 * @since 1.0.0
 */
@Component
public class AppleUtil {

    public String appleAuth(String jwt) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject = HttpClientUtils.doGet("https://appleid.apple.com/auth/keys");
        } catch (Exception e) {
            throw new JsonException(ResultEnum.NOT_NETWORK, "向苹果发送请求获取公钥失败"+e);
        }
        String keys = jsonObject.getString("keys");
        JSONArray arr = JSONObject.parseArray(keys);
        JSONObject jsonObject1 = JSONObject.parseObject(arr.getString(0));
        Jwk jwa = Jwk.fromValues(jsonObject1);
        try {
            // 生成苹果公钥
            PublicKey publicKey = jwa.getPublicKey();
//            String hearder = new String(Base64.decodeBase64(jwt.split("\\.")[0]));
            if (jwt.split("\\.").length > 1) {
                String claim = new String(Base64.decodeBase64(jwt.split("\\.")[1]));
                String aud = JSONObject.parseObject(claim).get("aud").toString();
                String sub = JSONObject.parseObject(claim).get("sub").toString();
                return verify(publicKey, jwt, aud, sub);
            }
            return "FAIL";
        } catch (InvalidPublicKeyException e) {
            throw new JsonException(ResultEnum.NOT_NETWORK,"转换苹果公钥失败"+e);
        }
    }

    public static String verify(PublicKey key, String jwt, String audience, String subject) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(key);
        jwtParser.requireIssuer("https://appleid.apple.com");
        jwtParser.requireAudience(audience);
        jwtParser.requireSubject(subject);
        try {
            Jws<Claims> claim = jwtParser.parseClaimsJws(jwt);
            if (claim != null && claim.getBody().containsKey("auth_time")) {
                return "SUCCESS";
            }
            return "FAIL";
        } catch (ExpiredJwtException e) {
            throw new JsonException(ResultEnum.NOT_NETWORK,"苹果token过期"+e);
        } catch (Exception e) {
            throw new JsonException(ResultEnum.NOT_NETWORK,"苹果token非法"+e);
        }
    }



}
