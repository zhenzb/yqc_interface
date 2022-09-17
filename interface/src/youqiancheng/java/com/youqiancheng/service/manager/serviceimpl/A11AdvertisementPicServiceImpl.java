package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A11AdvertisementPicDao;
import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;
import com.youqiancheng.service.manager.service.A11AdvertisementPicService;
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
public class A11AdvertisementPicServiceImpl implements A11AdvertisementPicService {
    @Autowired
    private A11AdvertisementPicDao a11AdvertisementPicDao;


    @Override
    public A11AdvertisementPicDO get(Long id){
        return a11AdvertisementPicDao.get(id);
    }


    @Override
    public List<A11AdvertisementPicDO> listHDPage(Map<String, Object> map) {
        return a11AdvertisementPicDao.listHDPage(map);
    }


    @Override
    public List<A11AdvertisementPicDO> list(Map<String, Object> map) {
        return a11AdvertisementPicDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a11AdvertisementPicDao.count(map);
    }


    @Override
    public int insert(A11AdvertisementPicDO a11AdvertisementPic) {
        return a11AdvertisementPicDao.insert(a11AdvertisementPic);
    }


    @Override
    public int insertBatch(List<A11AdvertisementPicDO> a11AdvertisementPics){
        return a11AdvertisementPicDao.insertBatch(a11AdvertisementPics);
    }


    @Override
    public int update(A11AdvertisementPicDO a11AdvertisementPic) {
        return a11AdvertisementPicDao.updateById(a11AdvertisementPic);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a11AdvertisementPicDao.updateStatus(param);
    }
   @Override
    public int delete(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return a11AdvertisementPicDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a11AdvertisementPicDao.updateStatus(param);
        }
    }
