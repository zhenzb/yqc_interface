package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A13SysVersionDao;
import com.youqiancheng.mybatis.model.A13SysVersionDO;
import com.youqiancheng.service.manager.service.A13SysVersionService;
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
public class A13SysVersionServiceImpl implements A13SysVersionService {
    @Autowired
    private A13SysVersionDao a13SysVersionDao;


    @Override
    public A13SysVersionDO get(Long id){
        return a13SysVersionDao.get(id);
    }


    @Override
    public List<A13SysVersionDO> listHDPage(Map<String, Object> map) {
        return a13SysVersionDao.listInfoHDPage(map);
    }


    @Override
    public List<A13SysVersionDO> list(Map<String, Object> map) {
        return a13SysVersionDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a13SysVersionDao.count(map);
    }


    @Override
    public int insert(A13SysVersionDO a13SysVersion) {
        return a13SysVersionDao.insert(a13SysVersion);
    }


    @Override
    public int insertBatch(List<A13SysVersionDO> a13SysVersions){
        return a13SysVersionDao.insertBatch(a13SysVersions);
    }


    @Override
    public int update(A13SysVersionDO a13SysVersion) {
        a13SysVersion.setUpdateTime(LocalDateTime.now());
        return a13SysVersionDao.updateById(a13SysVersion);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return a13SysVersionDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a13SysVersionDao.updateStatus(param);
        }
    }
