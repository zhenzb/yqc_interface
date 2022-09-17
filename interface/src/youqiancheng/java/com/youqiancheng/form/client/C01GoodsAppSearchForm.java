package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "PC——商品表查询实体")
public class C01GoodsAppSearchForm {

    @ApiModelProperty(value = "商品名称",name ="name")
    private String name;
    @ApiModelProperty(value = "排序key:1综合；2销量，3价格,4评价",name ="orderKey")
    private int  orderKey;
    @ApiModelProperty(value = "排序类型：desc 降序；asc 升序",name ="orderType")
    private String  orderType;

    @ApiModelProperty(value = "三级分类",name ="thirdCategoryId")
    private long  thirdCategoryId;

    @ApiModelProperty(value = "二级分类",name ="secondCategoryId")
    private long  secondCategoryId;

}
