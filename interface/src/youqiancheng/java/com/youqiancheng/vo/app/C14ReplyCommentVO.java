package com.youqiancheng.vo.app;

import com.baomidou.mybatisplus.annotations.TableField;
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
@ApiModel(value = "APP视图——回复宣传评论内容的评论表")
public class C14ReplyCommentVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "自增id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "评论关联id",name ="commentId")
    private Long commentId;
    @ApiModelProperty(value = "用户id",name ="userId")
    private Long userId;
    @ApiModelProperty(value = "用户名",name ="userName")
    private String userName;
    @ApiModelProperty(value = "针对用户评论内容的评论内容",name ="replyContent")
    private String replyContent;
    @ApiModelProperty(value = "用户回复的评论内容",name ="commentsUsersRespond")
    private String commentsUsersRespond;
    @ApiModelProperty(value = "回复评论人",name ="createPerson")
    private String createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标记",name ="deleteFlag")
    private Integer deleteFlag;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户头像",name ="pic")
    private String pic;
    @TableField(exist = false)
    @ApiModelProperty(value = "性别",name ="sex")
    private int sex;
}
