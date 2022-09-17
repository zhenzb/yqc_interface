package com.handongkeji.util;

import com.handongkeji.dto.RouterDTO;
import com.handongkeji.util.manager.JwtUtils;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.dao.system.A01AdminMapper;
import com.youqiancheng.mybatis.model.A01Admin;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DecryptToken {
    protected static final Log logger = LogFactory.getLog(DecryptToken.class);

    @Autowired
    private A01AdminMapper mbUserMapper;
    @Autowired
    private F08ShopUserDao shopUserDao;

    /**
     * @param token
     * @return Long
     * @throws
     * @Title: decryptToken
     * @Author victor 2016年1月20日
     * @Description: 解密
     */
    public Map<String, Object> decryptToken(String token) {
        Map<String, Object> map = new HashMap<>();
        String code = "0";
        // 验证 token
        Claims claims = JwtUtils.parse(token);
        if (claims == null) {
            logger.info("token无效");
            code = "400";
        }
        Long uid = null;
        A01Admin user = null;
        F08ShopUserDO shopUser = null;
        if (claims != null) {
            uid = Long.valueOf(claims.get("admin_id").toString());
            String logintime = claims.get("lastLoginTime")==null?"":claims.get("lastLoginTime").toString();
            try {
                user = mbUserMapper.selectById(uid);
                //商城管理
                shopUser = shopUserDao.selectById(uid);
                if (user != null || shopUser != null) {
//                    //判断用户是否被下线
//                    if (user.mbUserMapper() != null) {// 判断用户是否是最新注册第一次登陆，第一次登陆没有上次登陆时间
//                        if (compareTimeAccurateSecond(Long.parseLong(logintime), user.getLastLoginTime())) {
//                            // 时间戳校验成功
//                            code = "200";
//                        } else {
//                            // 校验失败 ，时间戳过期
//                            code = "201";
//                        }
//                    } else {
//                        code = "400";
//                    }

                    code = "200";
                } else {// 找不到对应的用户ID
                    code = "404";
                }
            } catch (Exception e) {
                e.printStackTrace();
                code = "400";
            }
        }
        map.put("uid", uid);
        map.put("member", user);
        map.put("shopUser", shopUser);
        map.put("code", code);
        return map;
    }

    public boolean compareTimeAccurateSecond(Long timestamp, Date datatime) {
        return (timestamp / 1000) == (datatime.getTime() / 1000);
    }
}
