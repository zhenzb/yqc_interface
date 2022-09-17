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

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "打赏记录实体")
@TableName("c12_reward_record")
public class C12RewardRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "店铺",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "打赏用户",name ="userId")
    private long userId;
    @ApiModelProperty(value = "打赏金额",name ="money")
    private BigDecimal money;
    @ApiModelProperty(value = "创建人——打赏用户名字",name ="createPerson")
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
    @ApiModelProperty(value = "用户头像",name ="userLogo")
    private String userLogo;
    @TableField(exist = false)
    @ApiModelProperty(value = "昵称",name ="nick")
    private String nick;
    @TableField(exist = false)
    @ApiModelProperty(value = "手机号",name ="mobile")
    private String mobile;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @ApiModelProperty(value = "支付类型",name ="payType")
    private int payType;
    @ApiModelProperty(value = "支付流水",name ="payNo")
    private String payNo;



}
