package com.youqiancheng.service.app.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.youqiancheng.form.app.B02AppPayBalanceForm;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.B12PromotionAccountDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface B02UserAccountAppService {

     B02UserAccountDO get(Long id);

     List<B02UserAccountDO> list(Map<String, Object> map);
     List<B02UserAccountDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B02UserAccountDO b02UserAccount);

     int insertBatch(List<B02UserAccountDO> b02UserAccounts);

     int update(B02UserAccountDO b02UserAccount);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     //获得余额
     BigDecimal getAccountBalanceByUserId(Long id);
    //余额支付
     int  payByUserBalance(B02AppPayBalanceForm from);

     //用户提现
     Boolean saveWithdrawalApplication(F06WithdrawalApplicationDO f06WithdrawalApplicationDO, BigDecimal big, B02UserAccountDO userAccount);

     //查询用户提现次数
    List<F06WithdrawalApplicationDO> selectUserWithdrawalNumber(Long accountId);

    public Boolean saveInvitationWithdrawalApplication(F06WithdrawalApplicationDO f06WithdrawalApplicationDO,BigDecimal big,BigDecimal withdrawalMoney,B12PromotionAccountDO promotionAccount);

}
