package com.youqiancheng.ability;

import com.handongkeji.config.redis.CacheUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeCheckNumberAbility {


    public Boolean checkCode(String PhoneNumbers){
        Object o = CacheUtils.get(PhoneNumbers);
        if(null !=o){
            Integer integer = Integer.valueOf(o.toString());
            if(integer>10){
                return false;
            }else{
                return true;
            }
        }else {
            return true;
        }
    }

    public void setCacheCode(String PhoneNumbers){
        Object o1 = CacheUtils.get(PhoneNumbers);
        if(null ==o1){
            //12小时过期时间 12*60*60L
            CacheUtils.set(PhoneNumbers,"1",300L);
        }else{
            Integer integer = Integer.valueOf(o1.toString());
            integer =integer+1;
            CacheUtils.set(PhoneNumbers,integer.toString(),300L);
        }
    }
}
