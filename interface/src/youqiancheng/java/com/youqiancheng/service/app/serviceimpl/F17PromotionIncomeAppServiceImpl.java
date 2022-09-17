package com.youqiancheng.service.app.serviceimpl;


import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.F17PromotionIncomeDao;
import com.youqiancheng.mybatis.model.F17PromotionIncomeDO;
import com.youqiancheng.service.app.service.F17PromotionIncomeAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-03-06
 */
@Service
public class F17PromotionIncomeAppServiceImpl implements F17PromotionIncomeAppService {
    @Autowired
    private F17PromotionIncomeDao f17PromotionIncomeDao;

    
    @Override
    public F17PromotionIncomeDO get(Long id){
        return f17PromotionIncomeDao.get(id);
    }

    
    @Override
    public List<F17PromotionIncomeDO> listHDPage(Map<String, Object> map) {
        return f17PromotionIncomeDao.listHDPageV2(map);
    }

    
    @Override
    public List<F17PromotionIncomeDO> list(Map<String, Object> map) {
        return f17PromotionIncomeDao.list(map);
    }


    
    @Override
    public int count(Map<String, Object> map){
        return f17PromotionIncomeDao.count(map);
    }

    
    @Override
    public int insert(F17PromotionIncomeDO f17PromotionIncome) {
        return f17PromotionIncomeDao.insert(f17PromotionIncome);
    }

    
    @Override
    public int insertBatch(List<F17PromotionIncomeDO> f17PromotionIncomes){
        return f17PromotionIncomeDao.insertBatch(f17PromotionIncomes);
    }

    
    @Override
    public int update(F17PromotionIncomeDO f17PromotionIncome) {
        return f17PromotionIncomeDao.updateById(f17PromotionIncome);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return f17PromotionIncomeDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return f17PromotionIncomeDao.updateStatus(param);
        }
    }
