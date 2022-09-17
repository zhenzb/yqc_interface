package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "用户地址表实体")
@TableName("b06_user_address")
public class B06UserAddressDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "收货人",name ="receiver")
    private String receiver;
    @ApiModelProperty(value = "手机号",name ="phone")
    private String phone;
    @ApiModelProperty(value = "别名",name ="name")
    private String name;
    @ApiModelProperty(value = "固话",name ="tel")
    private String tel;
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



}
