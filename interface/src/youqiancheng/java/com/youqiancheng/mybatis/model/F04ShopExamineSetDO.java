package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "商家审核设置实体")
@TableName("f04_shop_examine_set")
public class F04ShopExamineSetDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商家审核标记：启用停用；是否开启商家入驻信息审核，关闭则为信息免审核状态",name ="examineFlag")
    private int examineFlag;
    @ApiModelProperty(value = "身份证和营业执照上传：关闭后小程序端商家入驻不在有上传身份证和营业执照的选项",name ="uploadFlag")
    private int uploadFlag;
    @ApiModelProperty(value = "入住须知",name ="content")
    private String content;

}
