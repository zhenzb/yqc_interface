/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: PayInfo
 * Author:   ytf
 * Date:     2020/6/11 17:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.controller.wechatpay;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/6/11
 * @since 1.0.0
 */
@Data
public class PayInfo {

    private String orderNo;
    private BigDecimal money;
}
