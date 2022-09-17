package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.F10WithdrawalRuleDO;

import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 14:36
 * @Version: V1.0
 */
public interface ShopWithdrawalRuleService {
    F10WithdrawalRuleDO getWithdrawalRuleById(Long id);
    List<F10WithdrawalRuleDO> listWithdrawalRuleBy(EntityWrapper<F10WithdrawalRuleDO> ew);
}
