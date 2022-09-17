package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "商品审核设置实体")
@TableName("c04_goods_examine_set")
public class C04GoodsExamineSetDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "是否开启商品审核",name ="examineFlag")
    private int examineFlag;
    @ApiModelProperty(value = "购物须知",name ="content")
    private String content;

}
