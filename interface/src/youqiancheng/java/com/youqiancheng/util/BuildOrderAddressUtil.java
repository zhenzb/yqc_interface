package com.youqiancheng.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.service.shop.ShopOrderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BuildOrderAddressUtil {
    @Resource
    private ShopOrderService shopOrderService;

    public void buildAddress(List<D01OrderDO> orderlist){
        //拼接详细地址
        for (D01OrderDO d01OrderDO : orderlist) {
            d01OrderDO.setShippingAddress(d01OrderDO.getProvince()+d01OrderDO.getCity()+d01OrderDO.getArea()+d01OrderDO.getShippingAddress());
        }
        for (D01OrderDO order:orderlist){
            EntityWrapper<D02OrderItemDO> ew = new EntityWrapper<>();
            ew.eq("order_id",order.getId())
                    .orderBy("id",false)
                    .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
            List<D02OrderItemDO> d02OrderItemDOS = shopOrderService.listOrderItemBy(ew);
            int sum = 0;
            for(D02OrderItemDO d02:d02OrderItemDOS){
                sum = sum+d02.getNum();
            }
            order.setNum(sum);
            order.setOrderItem(d02OrderItemDOS);
        }
    }
}
