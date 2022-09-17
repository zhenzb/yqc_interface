package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A08ContactUsDao;
import com.youqiancheng.mybatis.model.A08ContactUsDO;
import com.youqiancheng.service.manager.service.A08ContactUsService;
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
public class A08ContactUsServiceImpl implements A08ContactUsService {
    @Autowired
    private A08ContactUsDao a08ContactUsDao;


    @Override
    public A08ContactUsDO get(Long id){
        return a08ContactUsDao.get(id);
    }


    @Override
    public List<A08ContactUsDO> listHDPage(Map<String, Object> map) {
        return a08ContactUsDao.listHDPage(map);
    }


    @Override
    public List<A08ContactUsDO> list(Map<String, Object> map) {
        return a08ContactUsDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a08ContactUsDao.count(map);
    }


    @Override
    public int insert(A08ContactUsDO a08ContactUs) {
        return a08ContactUsDao.insert(a08ContactUs);
    }


    @Override
    public int insertBatch(List<A08ContactUsDO> a08ContactUss){
        return a08ContactUsDao.insertBatch(a08ContactUss);
    }


    @Override
    public int update(A08ContactUsDO a08ContactUs) {
        a08ContactUs.setUpdateTime(LocalDateTime.now());
        return a08ContactUsDao.updateById(a08ContactUs);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a08ContactUsDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a08ContactUsDao.updateStatus(param);
        }
    }
