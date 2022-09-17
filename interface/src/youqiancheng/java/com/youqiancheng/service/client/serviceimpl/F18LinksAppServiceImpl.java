package com.youqiancheng.service.client.serviceimpl;


import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.F18LinksDao;
import com.youqiancheng.mybatis.model.F18LinksDO;
import com.youqiancheng.service.client.service.F18LinksAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-11-20
 */
@Service
public class F18LinksAppServiceImpl implements F18LinksAppService {
    @Resource
    private F18LinksDao f18LinksDao;

    
    @Override
    public F18LinksDO get(Long id){
        return f18LinksDao.get(id);
    }

    
    @Override
    public List<F18LinksDO> listHDPage(Map<String, Object> map) {
        return f18LinksDao.listHDPage(map);
    }

    
    @Override
    public List<F18LinksDO> list(Map<String, Object> map) {
        return f18LinksDao.list(map);
    }


    
    @Override
    public int count(Map<String, Object> map){
        return f18LinksDao.count(map);
    }

    
    @Override
    public int insert(F18LinksDO f18Links) {
        return f18LinksDao.insert(f18Links);
    }

    
    @Override
    public int insertBatch(List<F18LinksDO> f18Linkss){
        return f18LinksDao.insertBatch(f18Linkss);
    }

    
    @Override
    public int update(F18LinksDO f18Links) {
        return f18LinksDao.updateById(f18Links);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return f18LinksDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return f18LinksDao.updateStatus(param);
        }

    @Override
    public int delete(Long id) {
        Integer integer = f18LinksDao.deleteById(id);
        return integer;
    }
}
