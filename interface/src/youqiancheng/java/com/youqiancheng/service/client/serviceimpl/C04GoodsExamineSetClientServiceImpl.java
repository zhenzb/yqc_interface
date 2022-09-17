package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C04GoodsExamineSetDao;
import com.youqiancheng.mybatis.model.C04GoodsExamineSetDO;
import com.youqiancheng.service.client.service.C04GoodsExamineSetClientService;
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
public class C04GoodsExamineSetClientServiceImpl implements C04GoodsExamineSetClientService {
    @Autowired
    private C04GoodsExamineSetDao c04GoodsExamineSetDao;


    @Override
    public C04GoodsExamineSetDO get(Long id){
        return c04GoodsExamineSetDao.get(id);
    }




    @Override
    public List<C04GoodsExamineSetDO> list(Map<String, Object> map) {
        return c04GoodsExamineSetDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return c04GoodsExamineSetDao.count(map);
    }


    @Override
    public int insert(C04GoodsExamineSetDO c04GoodsExamineSet) {
        return c04GoodsExamineSetDao.insert(c04GoodsExamineSet);
    }


    @Override
    public int insertBatch(List<C04GoodsExamineSetDO> c04GoodsExamineSets){
        return c04GoodsExamineSetDao.insertBatch(c04GoodsExamineSets);
    }


    @Override
    public int update(C04GoodsExamineSetDO c04GoodsExamineSet) {
        c04GoodsExamineSet.setUpdateTime(LocalDateTime.now());
        return c04GoodsExamineSetDao.updateById(c04GoodsExamineSet);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return c04GoodsExamineSetDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return c04GoodsExamineSetDao.updateStatus(param);
        }
    }
