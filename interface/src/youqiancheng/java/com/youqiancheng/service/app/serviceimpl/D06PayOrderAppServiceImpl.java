package com.youqiancheng.service.app.serviceimpl;


import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.D01OrderDao;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.dao.D06PayOrderDao;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.service.app.service.D06PayOrderAppService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class D06PayOrderAppServiceImpl  implements D06PayOrderAppService {
    @Autowired
    private D06PayOrderDao d06PayOrderDao;
    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private D02OrderItemDao d02OrderItemDao;
    @Override
    @Transactional
    public int deleteOrderById(Long id) {
        //1:先去查这个id对应的支付订单
       D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(id);
       //2:通过支付订单的id去查商家订单表.是一个集合
      List<D01OrderDO> d01OrderList= d01OrderDao.getOrderByPayOrderId(d06PayOrderDO.getId());
      //3:查询订单明细
        List<D02OrderItemDO> d02OrderItemDOS3 = new ArrayList<>();
        for (D01OrderDO d01:d01OrderList
             ) {
            List<D02OrderItemDO> orderItmeByOrderId = d02OrderItemDao.getOrderItmeByOrderId(d01.getId());
            d02OrderItemDOS3.addAll(orderItmeByOrderId);
            System.out.print("----------------------------------------"+d01);
        }
        //修改支付订单状态
        if(StatusConstant.OrderStatus.cancel.getCode()!=d06PayOrderDO.getOrderStatus()){
            throw new JsonException(ResultEnum.DELETE_FAIL,"支付订单状态错误:只有已取消状态才可以删除");
        }
        d06PayOrderDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        d06PayOrderDao.updateById(d06PayOrderDO);
        //修改商家订单
        for (D01OrderDO aDo : d01OrderList) {
            if(StatusConstant.OrderStatus.cancel.getCode()!=aDo.getOrderStatus()){
                throw new JsonException(ResultEnum.DELETE_FAIL,"商家订单状态错误:只有已取消状态才可以删除");
            }
         /*   List<D01OrderDO> paylist=  d01OrderList.stream().filter(s->s.getOrderStatus()==StatusConstant.OrderStatus.cancel.getCode()).collect(Collectors.toList());
             paylist.stream().forEach(e->e.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode()));
            d01OrderDao.updateList(paylist);
            List<D01OrderDO> paylist=  d01OrderList.stream().filter(s->s.getOrderStatus()==StatusConstant.OrderStatus.cancel.getCode()).collect(Collectors.toList());
            Optional<D01OrderDO> first = paylist.stream().filter(e -> e.getDeleteFlag() != StatusConstant.DeleteFlag.delete.getCode()).findFirst();*/
            aDo.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        }

        d01OrderDao.updateList(d01OrderList);
        //修改订单明细
        for (D02OrderItemDO aDoi : d02OrderItemDOS3) {
            aDoi.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        }
        d02OrderItemDao.updateList(d02OrderItemDOS3);
        return  1;
    }

    @Override
    public int deleteOrderByOrderItemId(Long id) {
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(id);
        if(d02OrderItemDO.getOrderStatus() == 4 || d02OrderItemDO.getOrderStatus()>=5){
            d02OrderItemDO.setDeleteFlag(2);
            d02OrderItemDao.updateById(d02OrderItemDO);
            List<D02OrderItemDO> orderItemByOrderId = d02OrderItemDao.getOrderItmeByOrderId(d02OrderItemDO.getOrderId());
            if(orderItemByOrderId.size() == 0){
                D01OrderDO d01 = new D01OrderDO();
                d01.setDeleteFlag(2);
                d01.setId(d02OrderItemDO.getOrderId());
                d01OrderDao.updateById(d01);
            }
        }else{
            throw new JsonException(ResultEnum.DELETE_FAIL,"订单状态错误:只有已完成或已退款状态才可以删除");
        }
        return 1;
    }
}
