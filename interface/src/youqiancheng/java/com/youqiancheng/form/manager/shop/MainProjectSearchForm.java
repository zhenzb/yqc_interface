package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MainProjectSearchForm {
    @ApiModelProperty(value = "商家类型1、商品2、宣传 3、实体店",name ="type")
    private Integer type;
}
