package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B09BankCardDao;
import com.youqiancheng.mybatis.model.B09BankCardDO;
import com.youqiancheng.service.client.service.B09BankCardClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
@Transactional
public class B09BankCardClientServiceImpl implements B09BankCardClientService {
    @Autowired
    private B09BankCardDao b09BankCardDao;


    @Override
    public B09BankCardDO get(Long id){
        return b09BankCardDao.get(id);
    }


    @Override
    public List<B09BankCardDO> listHDPage(Map<String, Object> map) {
        return b09BankCardDao.listHDPage(map);
    }


    @Override
    public List<B09BankCardDO> list(Map<String, Object> map) {
        return b09BankCardDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b09BankCardDao.count(map);
    }


    @Override
    public int insert(B09BankCardDO b09BankCard) {
        return b09BankCardDao.insert(b09BankCard);
    }


    @Override
    public int insertBatch(List<B09BankCardDO> b09BankCards){
        return b09BankCardDao.insertBatch(b09BankCards);
    }


    @Override
    public int update(B09BankCardDO b09BankCard) {
        return b09BankCardDao.updateById(b09BankCard);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return b09BankCardDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b09BankCardDao.updateStatus(param);
        }
    }
