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
public class NoUtil {

    /**
     * 生成订单号
     * @return
     */
    public static String createNo(Long userid,String order) {
        //生成
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String orderNo = order + sdf.format(new Date()) + (1 + (int) (Math.random() * 10000))+userid;
        return orderNo;
    }
}
