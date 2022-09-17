package com.youqiancheng.form.manager.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告提交保存表单
 */
@Data
public class A09NoticeSaveForm {

    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @ApiModelProperty(value = "商品分类",name ="categoryId")
    private long categoryId;

}
