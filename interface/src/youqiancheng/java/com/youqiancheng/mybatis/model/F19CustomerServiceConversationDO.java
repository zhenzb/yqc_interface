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
 * Date  2022-02-17
 */
@Data
@ApiModel(value = "实体")
@TableName("f19_customer_service_conversation")
public class F19CustomerServiceConversationDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "",name ="conversationId")
    private String conversationId;
    @ApiModelProperty(value = "",name ="userId")
    private Long userId;
    @ApiModelProperty(value = "",name ="shopId")
    private Long shopId;
    @ApiModelProperty(value = "",name ="content")
    private String content;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "会话时间",name ="conversationTime")
    private LocalDateTime conversationTime;
    @ApiModelProperty(value = "1:未读 2：已读",name ="readStatus")
    private Integer readStatus;
    @ApiModelProperty(value = "是否删除 1：未删除 2：已删除",name ="delStatus")
    private Integer delStatus;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;



}
