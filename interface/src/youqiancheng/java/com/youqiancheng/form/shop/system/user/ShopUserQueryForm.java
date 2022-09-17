package com.youqiancheng.form.shop.system.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ShopUserQueryForm {
    @ApiModelProperty("管理员姓名")
    private String userName;
}
