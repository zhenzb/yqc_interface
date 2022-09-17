package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A06BaseInfoDao;
import com.youqiancheng.mybatis.model.A06BaseInfoDO;
import com.youqiancheng.service.manager.service.A06BaseInfoService;
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
public class A06BaseInfoServiceImpl implements A06BaseInfoService {
    @Autowired
    private A06BaseInfoDao a06BaseInfoDao;


    @Override
    public A06BaseInfoDO get(Long id){
        return a06BaseInfoDao.get(id);
    }


    @Override
    public List<A06BaseInfoDO> listHDPage(Map<String, Object> map) {
        return a06BaseInfoDao.listHDPage(map);
    }


    @Override
    public List<A06BaseInfoDO> list(Map<String, Object> map) {
        return a06BaseInfoDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a06BaseInfoDao.count(map);
    }


    @Override
    public int insert(A06BaseInfoDO a06BaseInfo) {
        return a06BaseInfoDao.insert(a06BaseInfo);
    }


    @Override
    public int insertBatch(List<A06BaseInfoDO> a06BaseInfos){
        return a06BaseInfoDao.insertBatch(a06BaseInfos);
    }


    @Override
    public int update(A06BaseInfoDO a06BaseInfo) {
        return a06BaseInfoDao.updateById(a06BaseInfo);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a06BaseInfoDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a06BaseInfoDao.updateStatus(param);
        }
    }
