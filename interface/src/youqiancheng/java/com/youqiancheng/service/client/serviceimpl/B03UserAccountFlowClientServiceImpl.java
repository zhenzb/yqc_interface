package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B03UserAccountFlowDao;
import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;
import com.youqiancheng.service.client.service.B03UserAccountFlowClientService;
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
public class B03UserAccountFlowClientServiceImpl implements B03UserAccountFlowClientService {
    @Autowired
    private B03UserAccountFlowDao b03UserAccountFlowDao;


    @Override
    public B03UserAccountFlowDO get(Long id){
        return b03UserAccountFlowDao.get(id);
    }


    @Override
    public List<B03UserAccountFlowDO> listHDPage(Map<String, Object> map) {
        return b03UserAccountFlowDao.listHDPage(map);
    }


    @Override
    public List<B03UserAccountFlowDO> list(Map<String, Object> map) {
        return b03UserAccountFlowDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b03UserAccountFlowDao.count(map);
    }


    @Override
    public int insert(B03UserAccountFlowDO b03UserAccountFlow) {
        return b03UserAccountFlowDao.insert(b03UserAccountFlow);
    }


    @Override
    public int insertBatch(List<B03UserAccountFlowDO> b03UserAccountFlows){
        return b03UserAccountFlowDao.insertBatch(b03UserAccountFlows);
    }


    @Override
    public int update(B03UserAccountFlowDO b03UserAccountFlow) {
        return b03UserAccountFlowDao.updateById(b03UserAccountFlow);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b03UserAccountFlowDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b03UserAccountFlowDao.updateStatus(param);
        }
    }
