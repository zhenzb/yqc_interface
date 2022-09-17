package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class MainProjectDeleteForm {
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
}
