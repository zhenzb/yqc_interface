package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.dao.F09WithdrawalSetDao;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import com.youqiancheng.service.shop.ShopWithdrawalSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 14:41
 * @Version: V1.0
 */
@Service
public class ShopWithdrawalSetServiceImpl implements ShopWithdrawalSetService {
    @Resource
    private F09WithdrawalSetDao withdrawalSetDao;

    @Override
    public F09WithdrawalSetDO getWithdrawalSetById(Long id) {
        if (id != null){
            return withdrawalSetDao.selectById(id);
        }
        return null;
    }

    @Override
    public List<F09WithdrawalSetDO> listWithdrawalSet(EntityWrapper<F09WithdrawalSetDO> ew) {
        return withdrawalSetDao.selectList(ew);
    }
}


