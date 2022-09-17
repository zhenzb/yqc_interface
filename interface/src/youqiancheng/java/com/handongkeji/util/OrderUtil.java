/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrderUtil
 * Author:   Mr.Chen
 * Date:     2019/2/19 9:49
 * Description: 订单工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.handongkeji.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订单工具〉
 *
 * @author Mr.Chen
 * @create 2019/2/19
 * @since 1.0.0
 */
public class OrderUtil {

    /**
     * 生成订单号
     * @return
     */
    public static String createOrderNo(Long userid,int OrderType) {
        String order="";
        switch (OrderType){
            case 1:
                order="YX";  //优选下单
                break;
            case 2:
                order="LX";  //拉新
                break;
            case 3:
                order="MB";//会员
                break;
            case 4:
                order="SK";//手机开方
                break;
            case 5:
                order="TK";//图片开方
                break;
            case 6:
                order="BK";//辩证开方
                break;
            case 7:
                order="DS";//打赏
                break;
            case 8:
                order="TC";//提成
                break;
            case 9:
                order="TX";//提现
                break;
            case 10:
                order="DG";//提现
                break;
        }
        //生成
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String orderNo = order + sdf.format(new Date()) + (1 + (int) (Math.random() * 10000)) + userid;
        return orderNo;
    }

    public static String createWithdrawCode(){
        return String.valueOf(Math.random() * 9000 + 1000).substring(0, 4);
    }

    public static String createOrdertitle(int OrderType){
        String ordertitle="";
        switch (OrderType){
            case 1:
                ordertitle="电话诊金";
                break;
            case 2:
                ordertitle="图文诊金";
                break;
            case 3:
                ordertitle="咨询诊金";
                break;
            case 4:
                ordertitle="治疗服务费";
                break;
            case 5:
                ordertitle="打赏";
                break;
            case 6:
                ordertitle="提现";
                break;
            case 7:
                ordertitle="邀请奖励";
                break;
            case 8:
                ordertitle="TC";//提成
                break;
            case 9:
                ordertitle="TX";//提现
                break;
        }
        return ordertitle;
    }
}
