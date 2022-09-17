package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.dao.F10WithdrawalRuleDao;
import com.youqiancheng.mybatis.model.F10WithdrawalRuleDO;
import com.youqiancheng.service.shop.ShopWithdrawalRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 提现规则
 * @Author: Mr.Deng
 * @Date: 2020/4/22 14:36
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopWithdrawalRuleServiceImpl implements ShopWithdrawalRuleService {
    @Resource
    private F10WithdrawalRuleDao withdrawalRuleDao;

    @Override
    public F10WithdrawalRuleDO getWithdrawalRuleById(Long id) {
        if (id != null){
            return withdrawalRuleDao.selectById(id);
        }
        return null;
    }

    @Override
    public List<F10WithdrawalRuleDO> listWithdrawalRuleBy(EntityWrapper<F10WithdrawalRuleDO> ew) {
        return withdrawalRuleDao.selectList(ew);
    }
}


