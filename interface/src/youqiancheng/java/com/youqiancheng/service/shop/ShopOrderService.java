package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.form.shop.ShopOrderSendForm;
import com.youqiancheng.form.shop.otther.ShopOrderStatisticsForm;
import com.youqiancheng.mybatis.model.A19ExpressDO;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;

import java.util.List;
import java.util.Map;


/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/13 13:29
 * @Version: V1.0
 */
public interface ShopOrderService {
    D01OrderDO orderStatistic(ShopOrderStatisticsForm shopOrderStatisticsForm);
    List<D01OrderDO> listOrderHDPage(Map<String, Object> map);
    Integer saveOrUpdateOrder(D01OrderDO order);
    Integer batchUpdateOrder(List<D01OrderDO> orders);
    Integer batchRefundOrder(Long[] ids);
    Integer examineOrderPass(Long[] ids);
    Integer examineOrderRefuse(Long[] ids);
    D01OrderDO getOrderById(long id);
    List<D02OrderItemDO> listOrderItemBy(EntityWrapper<D02OrderItemDO> ew);
    int send(ShopOrderSendForm form);
    //搜索快递公司名称以及编号
    List<A19ExpressDO> getCourierServicesCompanylist(Map<String, Object> map);

}
