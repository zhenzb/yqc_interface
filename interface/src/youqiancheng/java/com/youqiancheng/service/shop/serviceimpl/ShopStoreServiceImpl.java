package com.youqiancheng.service.shop.serviceimpl;

import com.youqiancheng.mybatis.dao.F01ShopDao;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.shop.ShopStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:51
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopStoreServiceImpl implements ShopStoreService {

    @Autowired
    private F01ShopDao f01ShopDao;


    @Override
    public F01ShopDO get(Long id){
        return f01ShopDao.get(id);
    }


    @Override
    public List<F01ShopDO> listHDPage(Map<String, Object> map) {
        return f01ShopDao.listHDPage(map);
    }



    @Override
    public List<F01ShopDO> list(Map<String, Object> map) {
        return f01ShopDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return f01ShopDao.count(map);
    }




    @Override
    public int update(F01ShopDO f01Shop) {
        f01Shop.setUpdateTime(LocalDateTime.now());
        return f01ShopDao.updateById(f01Shop);
    }


}


