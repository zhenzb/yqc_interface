/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: SendCodeDO
 * Author:   ytf
 * Date:     2020/4/22 15:03
 * Description: 验证码实体
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.vo;

import lombok.Data;

/**
 * 功能：
 * 〈验证码实体〉
 *
 * @author ytf
 * @create 2020/4/22
 * @since 1.0.0
 */
@Data
public class SendCodeDO {
    private String code;
    private long sendTime;
}
