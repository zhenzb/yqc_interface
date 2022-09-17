package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.service.client.service.D02OrderItemClientService;
import com.youqiancheng.vo.client.D02OrderItemClientVO;
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
public class D02OrderItemClientServiceImpl implements D02OrderItemClientService {
    @Autowired
    private D02OrderItemDao d02OrderItemDao;


    @Override
    public D02OrderItemDO get(Long id){
        return d02OrderItemDao.get(id);
    }


    @Override
    public List<D02OrderItemClientVO> listHDPage(Map<String, Object> map) {
        return d02OrderItemDao.listClientHDPage(map);
    }
   @Override
    public List<D02OrderItemClientVO> list5HDPage(Map<String, Object> map) {
        return d02OrderItemDao.listClient5tHDPage(map);
    }


    @Override
    public List<D02OrderItemDO> list(Map<String, Object> map) {
        return d02OrderItemDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return d02OrderItemDao.count(map);
    }


    @Override
    public int insert(D02OrderItemDO d02OrderItem) {
        return d02OrderItemDao.insert(d02OrderItem);
    }


    @Override
    public int insertBatch(List<D02OrderItemDO> d02OrderItems){
        return d02OrderItemDao.insertBatch(d02OrderItems);
    }


    @Override
    public int update(D02OrderItemDO d02OrderItem) {
        return d02OrderItemDao.updateById(d02OrderItem);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return d02OrderItemDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return d02OrderItemDao.updateStatus(param);
        }
    }
