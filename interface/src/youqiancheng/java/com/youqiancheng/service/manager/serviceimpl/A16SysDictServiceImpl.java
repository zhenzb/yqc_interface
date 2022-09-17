package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A16SysDictDao;
import com.youqiancheng.mybatis.model.A16SysDictDO;
import com.youqiancheng.service.manager.service.A16SysDictService;
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
public class A16SysDictServiceImpl implements A16SysDictService {
    @Autowired
    private A16SysDictDao a16SysDictDao;


    @Override
    public A16SysDictDO get(Long id){
        return a16SysDictDao.get(id);
    }


    @Override
    public List<A16SysDictDO> listHDPage(Map<String, Object> map) {
        return a16SysDictDao.listHDPage(map);
    }


    @Override
    public List<A16SysDictDO> list(Map<String, Object> map) {
        return a16SysDictDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a16SysDictDao.count(map);
    }


    @Override
    public int insert(A16SysDictDO a16SysDict) {
        return a16SysDictDao.insert(a16SysDict);
    }


    @Override
    public int insertBatch(List<A16SysDictDO> a16SysDicts){
        return a16SysDictDao.insertBatch(a16SysDicts);
    }


    @Override
    public int update(A16SysDictDO a16SysDict) {
        a16SysDict.setUpdateTime(LocalDateTime.now());
        return a16SysDictDao.updateById(a16SysDict);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a16SysDictDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a16SysDictDao.updateStatus(param);
        }
    }
