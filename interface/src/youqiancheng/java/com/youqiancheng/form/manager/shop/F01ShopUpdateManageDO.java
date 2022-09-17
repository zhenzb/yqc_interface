package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "商家表修改实体")
public class F01ShopUpdateManageDO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "商家ID")
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
    @ApiModelProperty(value = "隐藏电话标记：1未隐藏，2隐藏",name ="hide")
    private Integer hide;
    @ApiModelProperty(value = "身份证图片数组",name ="idCardList")
    private List<String> idCardList;
    @ApiModelProperty(value = "营业执照数组",name ="licenseList")
    private List<String> licenseList;
    @ApiModelProperty(value = "其他资料",name ="otherInfoList")
    private List<String> otherInfoList;

}
