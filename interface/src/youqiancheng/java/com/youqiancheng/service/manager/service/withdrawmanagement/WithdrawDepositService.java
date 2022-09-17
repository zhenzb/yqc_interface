package com.youqiancheng.service.manager.service.withdrawmanagement;


import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.withdraw.WithdrawRuleForm;
import com.youqiancheng.form.manager.withdraw.WithdrawalSetForm;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import com.youqiancheng.mybatis.model.F10WithdrawalRuleDO;
import com.youqiancheng.vo.result.ResultVo;

import java.util.List;
import java.util.Map;

public interface WithdrawDepositService {

    List<F06WithdrawalApplicationDO> listWithdrawalApplicationHDPage(Map<String, Object> map);

    ResultVo batchPassRefuse(ShopPassRefuseForm shopPassRefuseForm,String ipAddr);

    void addOrEditAudit(WithdrawalSetForm withdrawalSetForm);

    F09WithdrawalSetDO getAudit();

    void addOrEditRule(WithdrawRuleForm withdrawRuleForm);

    List<F10WithdrawalRuleDO> getRule();

}
