/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: TracesVO
 * Author:   ytf
 * Date:     2020/6/18 13:25
 * Description: 物流视图
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.vo.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能：
 * 〈物流视图〉
 *
 * @author ytf
 * @create 2020/6/18
 * @since 1.0.0
 */
@Data
public class TracesInfoVO {
    @ApiModelProperty(value = "到达地点以及描述",name ="AcceptStation")
    private String AcceptStation;
    @ApiModelProperty(value = "到达各个站点时间",name ="AcceptTime")
    private String AcceptTime;
}
