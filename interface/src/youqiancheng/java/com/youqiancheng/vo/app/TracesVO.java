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

import java.util.List;

/**
 * 功能：
 * 〈物流视图〉
 *
 * @author ytf
 * @create 2020/6/18
 * @since 1.0.0
 */
@Data
public class TracesVO {
    @ApiModelProperty(value = "快递运单号",name ="LogisticCode")
    private String LogisticCode;
    @ApiModelProperty(value = "快递公司编号",name ="ShipperCode")
    private String ShipperCode;
    @ApiModelProperty(value = "快递公司名称",name ="ExpressName")
    private String ExpressName;
    @ApiModelProperty(value = "货物到达地址",name ="Traces")
    private List<TracesInfoVO> Traces;
    @ApiModelProperty(value = "状态",name ="State")
    private String State;
    private String EBusinessID;
    private boolean Success;





}
