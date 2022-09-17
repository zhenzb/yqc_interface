package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "商品选择属性保存实体")
public class C08SelectAttributeSaveForm {
    @ApiModelProperty(value = "属性项ID",name ="projectId")
    private long projectId;
    @ApiModelProperty(value = "属性项名称",name ="projectName")
    private String projectName;
    @ApiModelProperty(value = "选择属性内容",name ="content")
    private String content1;
    @ApiModelProperty(value = "商品ID",name ="goodsId")
    private long goodsId;



}
