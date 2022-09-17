package com.youqiancheng.service.shop;

import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import com.youqiancheng.vo.shop.WithdrawalParamVO;


/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:52
 * @Version: V1.0
 */
public interface ShopAccountService {
    F05ShopAccountDO getShopAccountById(long id);
    F05ShopAccountDO getShopAccountByUserId();
    void getWithdrawableMoney(Long accountId);
    void updateShopAccount(Long accountId);
    Integer inAccountMaintain(D01OrderDO order);
    Integer outAccountMaintain(F06WithdrawalApplicationDO withdrawalApplication);

    WithdrawalParamVO getWithdrawalParam(Long id);

}
