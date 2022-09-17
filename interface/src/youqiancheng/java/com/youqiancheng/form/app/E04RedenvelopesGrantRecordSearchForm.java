package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——红包发放记录查询实体")
public class E04RedenvelopesGrantRecordSearchForm  {
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    @Min(value = 1,message = "街区ID不能小于1")
    private long streetId;
    @ApiModelProperty(value = "商家类型",name ="type")
    private int type;
//    @ApiModelProperty(value = "主营项目",name ="mainProject")
//    private long mainProject;
    @ApiModelProperty(value = "国家ID（商品一级分类）",name ="countryId")
    private int countryId;
//    @ApiModelProperty(value = "国家（商品一级分类）",name ="countryName")
//    private String countryName;
    @ApiModelProperty(value = "省编码",name ="provinceCode")
    private String provinceCode;
    @ApiModelProperty(value = "市编码",name ="cityCode")
    private String cityCode;
    @ApiModelProperty(value = "区编码",name ="areaCode")
    private String areaCode;



}
