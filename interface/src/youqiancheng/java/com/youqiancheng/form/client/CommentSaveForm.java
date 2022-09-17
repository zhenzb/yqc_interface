package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——宣传评论实体")
public class CommentSaveForm {
    @ApiModelProperty(value = "商品ID", name = "goodsId")
    @Min(value = 1,message = "商品ID不能为空")
    private Long goodsId;
    @ApiModelProperty(value = "评论内容", name = "content")
    private String content;
    @ApiModelProperty(value = "评价星级", name = "level")
    private int level;
    @ApiModelProperty(value = "评价用户ID", name = "userId")
    @Min(value = 1,message = "用户ID不能为空")
    private Long userId;
}
