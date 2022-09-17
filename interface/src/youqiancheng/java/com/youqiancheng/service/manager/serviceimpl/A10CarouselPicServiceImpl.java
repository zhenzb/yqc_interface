package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A10CarouselPicDao;
import com.youqiancheng.mybatis.model.A10CarouselPicDO;
import com.youqiancheng.service.manager.service.A10CarouselPicService;
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
public class A10CarouselPicServiceImpl implements A10CarouselPicService {
    @Autowired
    private A10CarouselPicDao a10CarouselPicDao;


    @Override
    public A10CarouselPicDO get(Long id){
        return a10CarouselPicDao.get(id);
    }


    @Override
    public List<A10CarouselPicDO> listHDPage(Map<String, Object> map) {
        return a10CarouselPicDao.listHDPage(map);
    }


    @Override
    public List<A10CarouselPicDO> list(Map<String, Object> map) {
        return a10CarouselPicDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a10CarouselPicDao.count(map);
    }


    @Override
    public int insert(A10CarouselPicDO a10CarouselPic) {
        return a10CarouselPicDao.insert(a10CarouselPic);
    }


    @Override
    public int insertBatch(List<A10CarouselPicDO> a10CarouselPics){
        return a10CarouselPicDao.insertBatch(a10CarouselPics);
    }


    @Override
    public int update(A10CarouselPicDO a10CarouselPic) {
        a10CarouselPic.setUpdateTime(LocalDateTime.now());
        return a10CarouselPicDao.updateById(a10CarouselPic);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a10CarouselPicDao.updateStatus(param);
    }

    @Override
    public int delete(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return a10CarouselPicDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a10CarouselPicDao.updateStatus(param);
        }
    }
