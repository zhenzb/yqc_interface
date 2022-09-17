package com.youqiancheng.form.client.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/14 18:13
 */
@Data
@ApiModel(value="流水查询",description="流水查询")
public class B03UserAccountFlowQueryForm {
    @ApiModelProperty("账户id")
    private Long id;
    @ApiModelProperty("账户类型")
    private Long type;
}
