package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A12ServiceAgreementDao;
import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;
import com.youqiancheng.service.manager.service.A12ServiceAgreementService;
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
public class A12ServiceAgreementServiceImpl implements A12ServiceAgreementService {
    @Autowired
    private A12ServiceAgreementDao a12ServiceAgreementDao;


    @Override
    public A12ServiceAgreementDO get(Long id){
        return a12ServiceAgreementDao.get(id);
    }


    @Override
    public List<A12ServiceAgreementDO> listHDPage(Map<String, Object> map) {
        return a12ServiceAgreementDao.listHDPage(map);
    }


    @Override
    public List<A12ServiceAgreementDO> list(Map<String, Object> map) {
        return a12ServiceAgreementDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a12ServiceAgreementDao.count(map);
    }


    @Override
    public int insert(A12ServiceAgreementDO a12ServiceAgreement) {
        return a12ServiceAgreementDao.insert(a12ServiceAgreement);
    }


    @Override
    public int insertBatch(List<A12ServiceAgreementDO> a12ServiceAgreements){
        return a12ServiceAgreementDao.insertBatch(a12ServiceAgreements);
    }


    @Override
    public int update(A12ServiceAgreementDO a12ServiceAgreement) {
        a12ServiceAgreement.setUpdateTime(LocalDateTime.now());
        return a12ServiceAgreementDao.updateById(a12ServiceAgreement);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a12ServiceAgreementDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a12ServiceAgreementDao.updateStatus(param);
        }
    }
