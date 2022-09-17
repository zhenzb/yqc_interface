package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "商家表实体")
@TableName("f01_shop")
public class F01ShopDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "商家ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商家类型",name ="type")
    private int type;
    @ApiModelProperty(value = "主营项目",name ="mainProject")
    private long mainProject;
    @ApiModelProperty(value = "主营项目名称",name ="projectName")
    private String projectName;
    @ApiModelProperty(value = "国家ID（商品一级分类）",name ="countryId")
    private int countryId;
    @ApiModelProperty(value = "国家（商品一级分类）",name ="countryName")
    private String countryName;
    @ApiModelProperty(value = "省编码",name ="provinceCode")
    private String provinceCode;
    @ApiModelProperty(value = "市编码",name ="cityCode")
    private String cityCode;
    @ApiModelProperty(value = "区编码",name ="areaCode")
    private String areaCode;
    @ApiModelProperty(value = "省",name ="province")
    private String province;
    @ApiModelProperty(value = "市",name ="city")
    private String city;
    @ApiModelProperty(value = "区",name ="area")
    private String area;
    @ApiModelProperty(value = "详细地址",name ="address")
    private String address;
    @ApiModelProperty(value = "经度",name ="longitude")
    private String longitude;
    @ApiModelProperty(value = "纬度",name ="latitude")
    private String latitude;
    @ApiModelProperty(value = "名称",name ="name")
    private String name;
    @ApiModelProperty(value = "浏览量",name ="browseVolume")
    private int browseVolume;
    @ApiModelProperty(value = "联系人",name ="contacts")
    private String contacts;
    @ApiModelProperty(value = "电话",name ="phone")
    private String phone;
    @ApiModelProperty(value = "logoURl",name ="logo")
    private String logo;
    @ApiModelProperty(value = "简介",name ="shopDesc")
    private String shopDesc;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private int examineStatus;
    @ApiModelProperty(value = "隐藏电话标记：1未隐藏，2隐藏",name ="hide")
    private Integer hide;
    @ApiModelProperty(value = "拒绝理由",name ="reason")
    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "审核通过时间:入驻时间",name ="examineTime")
    private LocalDateTime examineTime;
    @ApiModelProperty(value = "状态:停用，启用",name ="status")
    private int status;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
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
    @ApiModelProperty(value = "申请人",name ="userId")
    private long userId;
    @ApiModelProperty(value = "收藏量",name ="collectionVolume")
    private int collectionVolume;

    @TableField(exist = false)
    @ApiModelProperty(value = "账户总余额",name ="accountBalance")
    private BigDecimal accountBalance;
    @TableField(exist = false)
    @ApiModelProperty(value = "账户总余额",name ="distance")
    private BigDecimal distance;
    @TableField(exist = false)
    @ApiModelProperty(value = "商品数量",name ="sumGoods")
    private Integer sumGoods;
    @ApiModelProperty(value = "支付宝支付二维码",name ="alipayUrl")
    private String alipayUrl;
    @ApiModelProperty(value = "微信二维码",name ="wechatUrl")
    private String wechatUrl;
    @TableField(exist = false)
    @ApiModelProperty(value = "附件图片",name ="picList")
    private List<F02ShopPicDO> picList;
    @TableField(exist = false)
    @ApiModelProperty(value = "友情链接",name ="linksList")
    private List<F18LinksDO> linksList;

    @TableField(exist = false)
    @ApiModelProperty(value = "橱窗图片",name ="img_list")
    private List<String> imgList;

}
