package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;

import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 14:41
 * @Version: V1.0
 */
public interface ShopWithdrawalSetService {
    F09WithdrawalSetDO getWithdrawalSetById(Long id);
    List<F09WithdrawalSetDO> listWithdrawalSet(EntityWrapper<F09WithdrawalSetDO> ew);
}
