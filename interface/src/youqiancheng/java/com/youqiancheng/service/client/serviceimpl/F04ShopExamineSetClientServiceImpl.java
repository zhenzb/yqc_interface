package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.F04ShopExamineSetDao;
import com.youqiancheng.mybatis.model.F04ShopExamineSetDO;
import com.youqiancheng.service.client.service.F04ShopExamineSetClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
@Transactional
public class F04ShopExamineSetClientServiceImpl implements F04ShopExamineSetClientService {
    @Autowired
    private F04ShopExamineSetDao f04ShopExamineSetDao;


    @Override
    public F04ShopExamineSetDO get(Long id){
        return f04ShopExamineSetDao.get(id);
    }



    @Override
    public List<F04ShopExamineSetDO> list(Map<String, Object> map) {
        return f04ShopExamineSetDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return f04ShopExamineSetDao.count(map);
    }


    @Override
    public int insert(F04ShopExamineSetDO f04ShopExamineSet) {
        return f04ShopExamineSetDao.insert(f04ShopExamineSet);
    }


    @Override
    public int insertBatch(List<F04ShopExamineSetDO> f04ShopExamineSets){
        return f04ShopExamineSetDao.insertBatch(f04ShopExamineSets);
    }


    @Override
    public int update(F04ShopExamineSetDO f04ShopExamineSet) {
        return f04ShopExamineSetDao.updateById(f04ShopExamineSet);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return f04ShopExamineSetDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return f04ShopExamineSetDao.updateStatus(param);
        }
    }
