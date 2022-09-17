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
 * Date  2020-12-08
 */
@Data
@ApiModel(value = "实体")
@TableName("b11_Invitation_Record")
public class B11InvitationRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "邀请主键(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "邀请人ID",name ="userId")
    private Long userId;
    @ApiModelProperty(value = "被邀请人id",name ="beUserId")
    private Long beUserId;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "邀请时间",name ="createTime")
    private LocalDateTime createTime;

      private String nick;
      private String pic;
    @TableField(exist = false )
    private Long shopId;



}
