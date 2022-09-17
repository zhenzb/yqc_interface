package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.F05ShopAccountDO;

import java.math.BigDecimal;
import java.util.List;

/*
 * 商家账户接口
 * */
public interface F05ShopAPPAccountService {
    //查询商家的余额
    BigDecimal getShopTotalBalanceByShopId(Long id);

}
