package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "PC——用户地址表实体")
public class B06UserAddressUpdateForm {
    @ApiModelProperty(value = "主键id(数据库自增)")
    @Min(value = 1,message = "ID不能为空")
    private long id;
    @ApiModelProperty(value = "收货人",name ="receiver")
    private String receiver;
    @ApiModelProperty(value = "手机号",name ="phone")
    private String phone;
    @ApiModelProperty(value = "省编码",name ="provinceCode")
    private String provinceCode;
    @ApiModelProperty(value = "省",name ="province")
    private String province;
    @ApiModelProperty(value = "市编码",name ="cityCode")
    private String cityCode;
    @ApiModelProperty(value = "市",name ="city")
    private String city;
    @ApiModelProperty(value = "区编码",name ="areaCode")
    private String areaCode;
    @ApiModelProperty(value = "区",name ="area")
    private String area;
    @ApiModelProperty(value = "详细地址",name ="address")
    private String address;
    @ApiModelProperty(value = "是否默认",name ="isDefault")
    private int isDefault;
    @ApiModelProperty(value = "别名",name ="name")
    private String name;
    @ApiModelProperty(value = "固话",name ="tel")
    private String tel;
}
