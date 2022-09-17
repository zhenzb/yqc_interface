package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A07HelpCenterDao;
import com.youqiancheng.mybatis.model.A07HelpCenterDO;
import com.youqiancheng.service.manager.service.A07HelpCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Service
@Transactional
public class A07HelpCenterServiceImpl implements A07HelpCenterService {
    @Autowired
    private A07HelpCenterDao a07HelpCenterDao;


    @Override
    public A07HelpCenterDO get(Long id){
        return a07HelpCenterDao.get(id);
    }


    @Override
    public List<A07HelpCenterDO> listHDPage(Map<String, Object> map) {
        return a07HelpCenterDao.listHDPage(map);
    }


    @Override
    public List<A07HelpCenterDO> list(Map<String, Object> map) {
        return a07HelpCenterDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a07HelpCenterDao.count(map);
    }


    @Override
    public int insert(A07HelpCenterDO a07HelpCenter) {
        return a07HelpCenterDao.insert(a07HelpCenter);
    }


    @Override
    public int insertBatch(List<A07HelpCenterDO> a07HelpCenters){
        return a07HelpCenterDao.insertBatch(a07HelpCenters);
    }


    @Override
    public int update(A07HelpCenterDO a07HelpCenter) {
        return a07HelpCenterDao.updateById(a07HelpCenter);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a07HelpCenterDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a07HelpCenterDao.updateStatus(param);
        }
    }
