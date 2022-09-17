package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C11RoomDao;
import com.youqiancheng.mybatis.model.C11RoomDO;
import com.youqiancheng.service.app.service.C11RoomAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Service
@Transactional
public class C11RoomAppServiceImpl implements C11RoomAppService {
    @Autowired
    private C11RoomDao c11RoomDao;


    @Override
    public C11RoomDO get(Long id){
        return c11RoomDao.get(id);
    }


    @Override
    public List<C11RoomDO> listHDPage(Map<String, Object> map) {
        return c11RoomDao.listHDPage(map);
    }


    @Override
    public List<C11RoomDO> list(Map<String, Object> map) {
        return c11RoomDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return c11RoomDao.count(map);
    }


    @Override
    public int insert(C11RoomDO c11Room) {
        return c11RoomDao.insert(c11Room);
    }


    @Override
    public int insertBatch(List<C11RoomDO> c11Rooms){
        return c11RoomDao.insertBatch(c11Rooms);
    }


    @Override
    public int update(C11RoomDO c11Room) {
        return c11RoomDao.updateById(c11Room);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return c11RoomDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return c11RoomDao.updateStatus(param);
        }
    }
