package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.D01OrderStatusrForm;
import com.youqiancheng.form.app.D06PayOrderSearchForm;
import com.youqiancheng.form.app.PayOrderPayForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.app.D01OrderVO;
import com.youqiancheng.vo.app.OrderVO;
import com.youqiancheng.vo.client.D01OrderDOVo;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface D06PayOrderAppService {
     //删除D06数据库的支付订单
    int  deleteOrderById(Long id);

    int deleteOrderByOrderItemId(Long id);

}
