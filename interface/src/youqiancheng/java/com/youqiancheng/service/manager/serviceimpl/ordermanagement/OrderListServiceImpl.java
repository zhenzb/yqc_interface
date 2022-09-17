package com.youqiancheng.service.manager.serviceimpl.ordermanagement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.D01OrderDao;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.service.manager.service.ordermanagement.OrderListService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderListServiceImpl implements OrderListService {
    @Resource
    private D01OrderDao d01OrderDao;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private D02OrderItemDao d02OrderItemDao;


    public OrderListServiceImpl() {
    }


    /**
    　* @Description: 订单详情
    　* @author shalongteng
    　* @date 2020/4/16 18:27
    　*/
    @Override
    public D01OrderDO orderDetail(Integer id) {
        D01OrderDO d01OrderDO = d01OrderDao.selectById(id);
        if(d01OrderDO == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        B01UserDO b01UserDO = b01UserDao.selectById(d01OrderDO.getUserId());
        if(b01UserDO == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        d01OrderDO.setB01UserDO(b01UserDO);

        EntityWrapper<D02OrderItemDO> ew = new EntityWrapper<>();
        ew.eq("order_id",d01OrderDO.getId());
        List<D02OrderItemDO> d02OrderItemDOList = d02OrderItemDao.selectList(ew);
        if(d02OrderItemDOList == null || d02OrderItemDOList.size() == 0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        d01OrderDO.setOrderItem(d02OrderItemDOList);
        d01OrderDO.setLength(d01OrderDO.getOrderItem().size());
        return d01OrderDO;
    }

    @Override
    public List<D02OrderItemDO> getDetail(Map<String,Object> map ) {
        List<D02OrderItemDO> d02OrderItemDOS = d02OrderItemDao.listHDPage(map);
        return d02OrderItemDOS;
    }

    @Override
    public List<D01OrderDO> listOrderHDPage(Map<String, Object> map) {
        return d01OrderDao.listHDPage(map);
    }
}
