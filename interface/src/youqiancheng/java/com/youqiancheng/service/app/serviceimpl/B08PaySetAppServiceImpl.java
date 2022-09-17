package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B08PaySetDao;
import com.youqiancheng.mybatis.model.B08PaySetDO;
import com.youqiancheng.service.app.service.B08PaySetAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
@Transactional
public class B08PaySetAppServiceImpl implements B08PaySetAppService {
    @Autowired
    private B08PaySetDao b08PaySetDao;


    @Override
    public B08PaySetDO get(Long id){
        return b08PaySetDao.get(id);
    }


    @Override
    public List<B08PaySetDO> listHDPage(Map<String, Object> map) {
        return b08PaySetDao.listHDPage(map);
    }


    @Override
    public List<B08PaySetDO> list(Map<String, Object> map) {
        return b08PaySetDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b08PaySetDao.count(map);
    }


    @Override
    public int insert(B08PaySetDO b08PaySet) {
        return b08PaySetDao.insert(b08PaySet);
    }


    @Override
    public int insertBatch(List<B08PaySetDO> b08PaySets){
        return b08PaySetDao.insertBatch(b08PaySets);
    }


    @Override
    public int update(B08PaySetDO b08PaySet) {
        b08PaySet.setUpdateTime(LocalDateTime.now());
        return b08PaySetDao.updateById(b08PaySet);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b08PaySetDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b08PaySetDao.updateStatus(param);
        }
    }
