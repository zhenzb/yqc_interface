package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A14UserKnowDao;
import com.youqiancheng.mybatis.model.A14UserKnowDO;
import com.youqiancheng.service.manager.service.A14UserKnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Service
@Transactional
public class A14UserKnowServiceImpl implements A14UserKnowService {
    @Autowired
    private A14UserKnowDao a14UserKnowDao;


    @Override
    public A14UserKnowDO get(Long id){
        return a14UserKnowDao.get(id);
    }


    @Override
    public List<A14UserKnowDO> listHDPage(Map<String, Object> map) {
        return a14UserKnowDao.listHDPage(map);
    }


    @Override
    public List<A14UserKnowDO> list(Map<String, Object> map) {
        return a14UserKnowDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a14UserKnowDao.count(map);
    }


    @Override
    public int insert(A14UserKnowDO a14UserKnow) {
        return a14UserKnowDao.insert(a14UserKnow);
    }


    @Override
    public int insertBatch(List<A14UserKnowDO> a14UserKnows){
        return a14UserKnowDao.insertBatch(a14UserKnows);
    }


    @Override
    public int update(A14UserKnowDO a14UserKnow) {
        a14UserKnow.setUpdateTime(LocalDateTime.now());
        return a14UserKnowDao.updateById(a14UserKnow);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a14UserKnowDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a14UserKnowDao.updateStatus(param);
        }
    }
