/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: A15MessageAppVO
 * Author:   ytf
 * Date:     2020/4/8 17:23
 * Description: 视图——消息实体
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 功能：
 * 〈视图——消息实体〉
 *
 * @author ytf
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
@ApiModel(value = "APP视图————消息实体")
public class A15MessageAppVO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id")
    private long id;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @ApiModelProperty(value = "接收人ID",name ="reciveId")
    private long reciveId;
    @ApiModelProperty(value = "发送方ID（商家ID）",name ="sendId")
    private long sendId;
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
    @ApiModelProperty(value = "消息ID",name ="messageId")
    private long messageId;
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "是否读取",name ="isRead")
    private int isRead;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "读取时间",name ="readTime")
    private LocalDateTime readTime;
    @ApiModelProperty(value = "关联ID——已读接口参数",name ="relationId")
    private long relationId;

}
