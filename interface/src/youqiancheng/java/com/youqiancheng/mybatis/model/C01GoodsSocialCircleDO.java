package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "商品表实体")
@TableName("c01_goods")
public class C01GoodsSocialCircleDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "商品表id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "一级分类",name ="categoryId")
    private long categoryId;
    @ApiModelProperty(value = "二级分类ID",name ="secondCategoryId")
    private long secondCategoryId;
    @ApiModelProperty(value = "三级分类",name ="thirdCategoryId")
    private long thirdCategoryId;
    @ApiModelProperty(value = "商品名称",name ="name")
    private String name;
    @ApiModelProperty(value = "商品原价",name ="price")
    private BigDecimal price;
    @ApiModelProperty(value = "商品总库存数",name ="stock")
    private int stock;
    @ApiModelProperty(value = "商品描述——文本",name ="goodsDesc")
    private String goodsDesc;
    @ApiModelProperty(value = "销售数量",name ="saleNum")
    private int saleNum;
    @ApiModelProperty(value = "上架状态",name ="isSale")
    private int isSale;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private int examineStatus;
    @ApiModelProperty(value = "店铺Id",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "店铺名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "商品缩略图",name ="icon")
    private String icon;
    @ApiModelProperty(value = "浏览量",name ="browseVolume")
    private int browseVolume;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @ApiModelProperty(value = "拒绝理由",name ="reason")
    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "综合排序",name ="comprehensiveSort")
    private int comprehensiveSort;
    @ApiModelProperty(value = "评价排序",name ="evaluateSort")
    private int evaluateSort;
    @ApiModelProperty(value = "类型",name ="type")
    private String type;
    @ApiModelProperty(value = "商品编码",name ="goodsNo")
    private String goodsNo;
    @ApiModelProperty(value = "商品简介——文字",name ="introduction")
    private String introduction;
    @ApiModelProperty(value = "收藏量",name ="collectionVolume")
    private String collectionVolume;
    @TableField(exist = false)
    @ApiModelProperty(value = "图片数组",name ="picArr")
    private String[] picArr;
    @TableField(exist = false )
    private C02GoodsPicDO c02GoodsPicDO;
    @TableField(exist = false )
    @ApiModelProperty(value = "一级分类",name ="categoryIdName")
    private String categoryIdName;
    @TableField(exist = false )
    @ApiModelProperty(value = "二级分类",name ="secondCategoryIdName")
    private String secondCategoryIdName;
    @TableField(exist = false )
    @ApiModelProperty(value = "三级分类",name ="thirdCategoryIdName")
    private String thirdCategoryIdName;
    @TableField(exist = false )
    @ApiModelProperty(value = "轮播图",name ="picList")
    private List<String> picList;
    @TableField(exist = false )
    @ApiModelProperty(value = "省",name ="province")
    private String province;
    @TableField(exist = false )
    @ApiModelProperty(value = "市",name ="city")
    private String city;
    @TableField(exist = false )
    @ApiModelProperty(value = "区",name ="area")
    private String area;
    @TableField(exist = false )
    @ApiModelProperty(value = "评论数",name ="evaluateCount")
    private int evaluateCount;
    @TableField(exist = false )
    @ApiModelProperty(value = "库存信息",name ="skuList")
    private List<C09GoodsSkuDO> skuList;
}
