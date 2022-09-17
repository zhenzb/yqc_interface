package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "用户商品评价记录图片表实体")
@TableName("d05_evaluate_pic")
public class D05EvaluatePicDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商品评价表id",name ="evaluateId")
    private long evaluateId;
    @ApiModelProperty(value = "图片地址",name ="picUrl")
    private String picUrl;



}
