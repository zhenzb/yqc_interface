package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.service.shop.ShopRedenvelopesStreetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 18:12
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopRedenvelopesStreetServiceImpl implements ShopRedenvelopesStreetService {
    @Resource
    private E01RedenvelopesStreetDao redenvelopesStreetDao;

    @Override
    public E01RedenvelopesStreetDO getRedenvelopesStreetById(Long id) {
        if (id != null){
            return redenvelopesStreetDao.selectById(id);
        }
        return null;
    }

    @Override
    public List<E01RedenvelopesStreetDO> listRedenvelopesStreetBy(EntityWrapper<E01RedenvelopesStreetDO> ew) {
        return redenvelopesStreetDao.selectList(ew);
    }
}


