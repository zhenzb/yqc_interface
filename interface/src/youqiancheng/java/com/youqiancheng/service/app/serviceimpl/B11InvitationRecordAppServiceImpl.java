package com.youqiancheng.service.app.serviceimpl;

import com.youqiancheng.mybatis.dao.B11InvitationRecordDao;
import com.youqiancheng.mybatis.model.B11InvitationRecordDO;
import com.youqiancheng.service.app.service.B11InvitationRecordAppService;
import com.handongkeji.constants.StatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-12-08
 */
@Service
public class B11InvitationRecordAppServiceImpl implements B11InvitationRecordAppService {
    @Autowired
    private B11InvitationRecordDao b11InvitationRecordDao;

    
    @Override
    public B11InvitationRecordDO get(Long id){
        return b11InvitationRecordDao.get(id);
    }

    
    @Override
    public List<B11InvitationRecordDO> listHDPage(Map<String, Object> map) {
        return b11InvitationRecordDao.listHDPage(map);
    }

    
    @Override
    public List<B11InvitationRecordDO> list(Map<String, Object> map) {
        return b11InvitationRecordDao.list(map);
    }


    
    @Override
    public int count(Map<String, Object> map){
        return b11InvitationRecordDao.count(map);
    }

    
    @Override
    public int insert(B11InvitationRecordDO b11InvitationRecord) {
        return b11InvitationRecordDao.insert(b11InvitationRecord);
    }

    
    @Override
    public int insertBatch(List<B11InvitationRecordDO> b11InvitationRecords){
        return b11InvitationRecordDao.insertBatch(b11InvitationRecords);
    }

    
    @Override
    public int update(B11InvitationRecordDO b11InvitationRecord) {
        return b11InvitationRecordDao.updateById(b11InvitationRecord);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b11InvitationRecordDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b11InvitationRecordDao.updateStatus(param);
        }
    }
