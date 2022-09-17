package com.youqiancheng.form.manager.Goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GoodsDeleteForm {

    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
}
