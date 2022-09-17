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
@ApiModel(value = "实名认证实体")
@TableName("b07_authentication")
public class B07AuthenticationDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "名字",name ="name")
    private String name;
    @ApiModelProperty(value = "身份证号",name ="cardNo")
    private String cardNo;
    @ApiModelProperty(value = "照片",name ="url")
    private String url;
    @ApiModelProperty(value = "反面照片",name ="backUrl")
    private String backUrl;
    @ApiModelProperty(value = "审核状态",name ="status")
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
    @ApiModelProperty(value = "银行卡号",name ="bankCardNum")
    private String bankCardNum;
    @ApiModelProperty(value = "e签宝用户账号标识", name="EAccountId")
    private String EAccountId;
    @ApiModelProperty(value = "刷脸照片", name="facePhotoUrl")
    private String facePhotoUrl;
    @ApiModelProperty(value = "证件类型", name="certType")
    private String certType;
    @ApiModelProperty(value = "签署合同Url", name="certType")
    private String contractUrl;
    @ApiModelProperty(value = "签署流程Id", name="flowId")
    private  String flowId;
}
