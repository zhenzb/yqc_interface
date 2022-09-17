package com.youqiancheng.service.client.serviceimpl;

import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.app.service.F08ShopAppService;
import com.youqiancheng.service.client.service.F08ShopClienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Service
@Transactional
public class F08ShopClienServiceImpl implements F08ShopClienService {

    @Autowired
    private  F08ShopUserDao f08ShopUserDao;

    @Override
    public F08ShopUserDO getf08ShopUserDO(Map<String, Object> map) {
        List<F08ShopUserDO> list = f08ShopUserDao.list(map);
        if(list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}
