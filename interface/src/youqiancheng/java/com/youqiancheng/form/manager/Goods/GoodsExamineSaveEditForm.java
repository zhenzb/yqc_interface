package com.youqiancheng.form.manager.Goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Getter
@Setter
public class GoodsExamineSaveEditForm {
    @ApiModelProperty(value = "是否开启商品审核",name ="examineFlag")
    private Integer examineFlag;
    @ApiModelProperty(value = "购物须知",name ="content")
    private String content;

}
