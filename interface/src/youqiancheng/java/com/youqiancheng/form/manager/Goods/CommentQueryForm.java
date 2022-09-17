package com.youqiancheng.form.manager.Goods;

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
public class CommentQueryForm {

    @ApiModelProperty(value = "商品名称",name ="goodsName")
    private String goodsName;
}
