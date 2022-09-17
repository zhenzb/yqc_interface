package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Getter
@Setter
public class ShopUsertSaveOrUpdateForm {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "账户ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Integer id;

    @ApiModelProperty(value = "状态",name ="status")
    private Integer status;
    @ApiModelProperty(value = "密码",name ="pwd")
    private String pwd;

}
