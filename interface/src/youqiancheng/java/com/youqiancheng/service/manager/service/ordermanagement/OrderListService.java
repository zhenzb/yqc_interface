package com.youqiancheng.service.manager.service.ordermanagement;


import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;

import java.util.List;
import java.util.Map;

public interface OrderListService {

    D01OrderDO orderDetail(Integer id);
    List<D02OrderItemDO> getDetail(Map<String,Object> map );
    List<D01OrderDO> listOrderHDPage(Map<String,Object> map);
}
