package com.youqiancheng.service.manager.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.youqiancheng.mybatis.dao.A15MessageDao;
import com.youqiancheng.mybatis.dao.A17MessageUserDao;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.mybatis.model.A17MessageUserDO;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.service.manager.service.A15MessageService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Service
@Transactional
public class A15MessageServiceImpl implements A15MessageService {
    @Autowired
    private A15MessageDao a15MessageDao;
    @Autowired
    private A17MessageUserDao a17MessageUserDao;
    @Autowired
    private B01UserDao b01UserDao;


    @Override
    public A15MessageDO get(Long id){
        return a15MessageDao.get(id);
    }


    @Override
    public List<A15MessageDO> listHDPage(Map<String, Object> map) {
        return a15MessageDao.listHDPage(map);
    }


    @Override
    public List<A15MessageDO> list(Map<String, Object> map) {
        return a15MessageDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return a15MessageDao.count(map);
    }


    @Override
    public int insert(A15MessageDO a15Message) {
        Integer insert = a15MessageDao.insert(a15Message);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"消息插入失败");
        }
        return insert;
    }

    /**
     * 功能描述: <br>
     * 〈发布〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
   @Override
    public int release(List<Long> ids) {
       //插入用户消息关联记录
       for (Long id : ids) {
           List<A17MessageUserDO> a17MessageUsers=new ArrayList<>();
           List<B01UserDO> list = b01UserDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
           if(list==null||list.size()<=0){
               throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到用户信息，无法发送消息");
           }
           for (B01UserDO b01UserDO : list) {
               A17MessageUserDO dto=new A17MessageUserDO();
               dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
               dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
               dto.setMessageId(id);
               dto.setUserId(b01UserDO.getId());
               dto.setType(TypeConstant.MessageReadType.buyer.getCode());
               a17MessageUsers.add(dto);
           }
           int num =a17MessageUserDao.insertBatch(a17MessageUsers);
           if(num<=0){
               throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"批量插入用户消息失败");
           }
       }
       HashMap<String,Object> param=new HashMap<>();
       param.put("ids",ids);
       param.put("status", StatusConstant.ReleaseStatus.release.getCode());
        a15MessageDao.updateStatus(param);
        //推送消息
       for (Long id : ids) {
           A15MessageDO a15MessageDO = a15MessageDao.get(id);
           String content = a15MessageDO.getContent();
           String title = a15MessageDO.getTitle();
           try {
               List<B01UserDO> list = b01UserDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
               for (B01UserDO b01UserDO : list) {
                   UmengPush.sendIOSCustomizedcast("yqc_type", "yqc_"+b01UserDO.getId(), title,title, content, "1","1",null,null,null);  // IOS 单推
                   UmengPush.sendAndroidCustomizedcast("yqc_type", "yqc_"+b01UserDO.getId(), title, title, content, "1","1",null,null,null); // android 单推

               }
               //UmengPush.sendIOSBroadcast(title, title, content, "0","99",null);   // IOS全推
              // UmengPush.sendAndroidBroadcast(title, title, content, "0","99",null);  //  android 全推
           } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
               throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息推送失败:"+ e.getMessage());
           }
       }
       return 1;
    }


    @Override
    public int insertBatch(List<A15MessageDO> a15Messages){
        return a15MessageDao.insertBatch(a15Messages);
    }


    @Override
    public int update(A15MessageDO a15Message) {
        return a15MessageDao.updateById(a15Message);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
         a15MessageDao.updateStatus(param);
         //删除关联记录
        for (Long id : ids) {
            List<A17MessageUserDO> a17MessageUserDOS = a17MessageUserDao.selectList(
                    new EntityWrapper<A17MessageUserDO>()
                            .eq("message_id",id)
            );
            List<Long> collect = a17MessageUserDOS.stream().map(A17MessageUserDO::getId).collect(Collectors.toList());
            a17MessageUserDao.deleteBatchIds(collect);
        }

        return 1;
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return a15MessageDao.updateStatus(param);
        }
    }
