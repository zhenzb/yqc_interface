package com.youqiancheng.service.app.serviceimpl;

import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.service.app.service.F05ShopAPPAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Service
@Transactional
public class F05ShopAPPAccountServiceImpl  implements F05ShopAPPAccountService {
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    //查询商家的总余额
    @Override
    public BigDecimal getShopTotalBalanceByShopId(Long id) {
        return f05ShopAccountDao.getShopTotalBalanceByShopId(id);
    }
}
