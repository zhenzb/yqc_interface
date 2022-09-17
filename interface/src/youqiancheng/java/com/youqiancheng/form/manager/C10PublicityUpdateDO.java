package com.youqiancheng.form.manager;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "宣传实体")
public class C10PublicityUpdateDO {
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "简介",name ="goodsDesc")
    private String goodsDesc;
    @ApiModelProperty(value = "动态主题图",name ="icon")
    private String icon;
    @ApiModelProperty(value = "内容链接地址：视频音频",name ="contentUrl")
    private String contentUrl;
    @ApiModelProperty(value = "时长",name ="duration")
    private String duration;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private long orderNum;
}
