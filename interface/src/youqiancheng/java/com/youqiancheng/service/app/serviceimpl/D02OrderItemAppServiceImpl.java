package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.service.app.service.D02OrderItemAppService;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.app.D02OrderItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
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
public class D02OrderItemAppServiceImpl implements D02OrderItemAppService {
    @Autowired
    private D02OrderItemDao d02OrderItemDao;

    @Override
    public D02OrderItemDO get(Long id){
        return d02OrderItemDao.get(id);
    }

   @Override
    public List<D02OrderItemVO> listHDPage(Map<String, Object> map) {
        return d02OrderItemDao.listAppHDPage(map);
    }

   @Override
    public List<D02OrderItemVO> list5HDPage(Map<String, Object> map) {
        return d02OrderItemDao.listApp5HDPage(map);
    }
   @Override
    public List<D01OrderStatusVo> getAppShopStatusOrderHDPage(Map<String, Object> map) {
        return d02OrderItemDao.getAppShopStatusOrderHDPage(map);
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
        d02OrderItem.setUpdateTime(LocalDateTime.now());

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

    @Override
    public D02OrderItemVO getOrderItmeAndOrder(Long id) {
        return d02OrderItemDao.getOrderItmeAndOrder(id);
    }
}
