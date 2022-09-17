package com.youqiancheng.vo.client;

import lombok.Data;

/**
 * @ClassName D06OrderStatusNumVO
 * @Description TODO
 * @Author zzb
 * @Date 2022/8/24 21:54
 * @Version 1.0
 **/
@Data
public class D06OrderStatusNumVO {

    private String img;
    private String til;
    private String id;
    //订单各状态数量
    private int num;
}
