package com.youqiancheng.ability;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName F05ShopAccount
 * @Description TODO
 * @Author zzb
 * @Date 2021/10/23 16:50
 * @Version 1.0
 **/
@Component
public class F05ShopAccountAbility {
    @Autowired
    private F05ShopAccountDao shopAccountDao;

    public Boolean addShopAccountAvailableWithdrawMoney(Long accountId,String money){
        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.get(accountId);
        if(f05ShopAccountDO==null){
            return false;
        }
        //可提现金额加上相应值
        BigDecimal resultMoney = DecimalUtil.addBigMal(String.valueOf(f05ShopAccountDO.getAvailableWithdrawMoney()), money, 0, 0);
        f05ShopAccountDO.setAvailableWithdrawMoney(resultMoney);
        shopAccountDao.updateById(f05ShopAccountDO);
        return true;
    }
}
