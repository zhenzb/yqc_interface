package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "支付设置实体")
@TableName("b08_pay_set")
public class B08PaySetDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    private String payPwd;
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
    @TableField(exist = false)
    @ApiModelProperty(value = "手机号",name ="phone")
    private String phone;



}
