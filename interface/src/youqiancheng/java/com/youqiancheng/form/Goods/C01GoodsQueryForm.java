package com.youqiancheng.form.Goods;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 18:17
 */
@Data
@ApiModel(value="商品列表查询实体")
public class C01GoodsQueryForm   {
    @ApiModelProperty(value = "三级分类",name ="thirdCategoryId")
    private Long thirdCategoryId;
    @ApiModelProperty(value = "店铺名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "排序类别（1-综合，2-销量，3-价格）",name ="sortType")
    private String sortType;
    @ApiModelProperty(value = "排序类别规则（1-从低到高，2-从高到低）",name ="orderRule")
    private String orderRule;
}
