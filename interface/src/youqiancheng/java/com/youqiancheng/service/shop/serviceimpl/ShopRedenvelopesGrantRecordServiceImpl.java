package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.dao.E04RedenvelopesGrantRecordDao;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.service.shop.ShopRedenvelopesGrantRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 14:46
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopRedenvelopesGrantRecordServiceImpl implements ShopRedenvelopesGrantRecordService {
    @Resource
    private E04RedenvelopesGrantRecordDao redenvelopesGrantRecordDao;

    @Override
    public E04RedenvelopesGrantRecordDO getRedenvelopesGrantRecordById(Long id) {
        if (id != null){
            return redenvelopesGrantRecordDao.selectById(id);
        }
        return null;
    }

    @Override
    public List<E04RedenvelopesGrantRecordDO> listRedenvelopesGrantRecordBy(EntityWrapper<E04RedenvelopesGrantRecordDO> ew) {
        return redenvelopesGrantRecordDao.selectList(ew);
    }
}


