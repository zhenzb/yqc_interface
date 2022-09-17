package com.youqiancheng.form.manager.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class ShopQueryForm {
    @ApiModelProperty(value = "商家类型",name ="type")
    private Integer type;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private Integer examineStatus;
    @ApiModelProperty(value = "名称",name ="name")
    private String name;
}
