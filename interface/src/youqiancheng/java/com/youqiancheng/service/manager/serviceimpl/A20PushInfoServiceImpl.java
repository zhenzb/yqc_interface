package com.youqiancheng.service.manager.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.push.UmengPush;
import com.youqiancheng.mybatis.dao.A20PushInfoDao;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.model.A20PushInfoDO;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.service.manager.service.A20PushInfoService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-06-17
 */
@Service
@Transactional
public class A20PushInfoServiceImpl implements A20PushInfoService {
    @Autowired
    private A20PushInfoDao a20PushInfoDao;
   @Autowired
    private B01UserDao b01UserDao;


    @Override
    public A20PushInfoDO get(Long id){
        return a20PushInfoDao.get(id);
    }


    @Override
    public List<A20PushInfoDO> listHDPage(Map<String, Object> map) {
        return a20PushInfoDao.listHDPage(map);
    }


    @Override
    public List<A20PushInfoDO> list(Map<String, Object> map) {
        return a20PushInfoDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a20PushInfoDao.count(map);
    }


    @Override
    public int insert(A20PushInfoDO a20PushInfo) {
         a20PushInfoDao.insert(a20PushInfo);
        try {
            List<B01UserDO> list = b01UserDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
            for (B01UserDO b01UserDO : list) {
                UmengPush.sendIOSCustomizedcast("yqc_type", "yqc_"+b01UserDO.getId(), a20PushInfo.getTitle(),a20PushInfo.getTitle(), a20PushInfo.getContent(), "1","1",null,null,null);  // IOS 单推
                UmengPush.sendAndroidCustomizedcast("yqc_type", "yqc_"+b01UserDO.getId(), a20PushInfo.getTitle(), a20PushInfo.getTitle(), a20PushInfo.getContent(), "1","1",null,null,null); // android 单推

            }
            //UmengPush.sendIOSBroadcast(a20PushInfo.getTitle(), a20PushInfo.getTicker(),
                    //a20PushInfo.getContent(), "0",a20PushInfo.getId().toString(),null);   // IOS全推

            //UmengPush.sendAndroidBroadcast(a20PushInfo.getTitle(), a20PushInfo.getTicker(),
                    //a20PushInfo.getContent(), "0",a20PushInfo.getId().toString(),null);  //  android 全推

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.NOT_NETWORK,"推送失败");
        }
        return 1;
    }


    @Override
    public int insertBatch(List<A20PushInfoDO> a20PushInfos){
        return a20PushInfoDao.insertBatch(a20PushInfos);
    }


    @Override
    public int update(A20PushInfoDO a20PushInfo) {
        return a20PushInfoDao.updateById(a20PushInfo);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return a20PushInfoDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a20PushInfoDao.updateStatus(param);
        }
    }
