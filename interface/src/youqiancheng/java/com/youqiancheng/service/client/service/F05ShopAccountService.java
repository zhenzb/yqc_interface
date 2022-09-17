package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import com.youqiancheng.mybatis.model.F07ShopAccountFlowDO;
import com.youqiancheng.vo.shop.WithdrawalParamVO;

import java.util.List;
import java.util.Map;

/*
 * 商家账户接口
 * */
public interface F05ShopAccountService {

    List<F05ShopAccountDO> getShopAccount(Long id);
    List<F05ShopAccountDO> getAccountByShopId(Long id);
    F05ShopAccountDO get(Long id);
    WithdrawalParamVO getWithdrawalParam(Long id);
     Integer save(F06WithdrawalApplicationDO withdrawalApplication);
    List<F07ShopAccountFlowDO>  getFlow(Map<String,Object> map);
}
