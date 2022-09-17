package com.youqiancheng.vo.shop;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-06-15
 */
@Data
@ApiModel(value = "A19_快递鸟快递公司档案实体")
@TableName("a19_express")
public class A19ExpressVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "类型",name ="name")
    private String name;
    @ApiModelProperty(value = "单据号",name ="code")
    private String code;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private Integer deleteFlag;



}
