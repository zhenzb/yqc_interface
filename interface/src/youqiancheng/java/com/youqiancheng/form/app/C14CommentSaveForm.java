package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——回复用户评论宣传的评论实体")
public class C14CommentSaveForm {
    @ApiModelProperty(value = "评论ID", name = "id")
    private Long id;
    @ApiModelProperty(value = "针对用户评论内容的评论内容", name = "replyContent")
    private String replyContent;
    @ApiModelProperty(value = "评论--评论用户的ID", name = "userId")
    private Long userId;
    @ApiModelProperty(value = "评论--评论用户的名称", name = "userName")
    private String userName;
}
