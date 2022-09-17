package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface D06PayOrderClientService {


    D06PayOrderDO deductInventory(Long orderid);
    int addInventory(String orderNo);

    long updatePayStatus(String orderNo,String tradeNo,int type);
    int updateCustomService(D03CustomerServiceDO customService);
    int updateTransfer(F06WithdrawalApplicationDO transferAccountsInfo );

    D03CustomerServiceDO getCustomService(Long id);
    F06WithdrawalApplicationDO getTransferAccountsInfo(Long id);
    D06PayOrderDO getPayOrderByItemId(Long id);
    D06PayOrderDO get(Long id) ;

}
