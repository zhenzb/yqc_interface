package com.youqiancheng.vo.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "我的宣传返回实体")
public class C10PublicityVO  {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "店铺Id",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "简介",name ="goodsDesc")
    private String goodsDesc;
    @ApiModelProperty(value = "动态主题图",name ="icon")
    private String icon;
    @ApiModelProperty(value = "浏览量",name ="browseVolume")
    private int browseVolume;
    @ApiModelProperty(value = "评论数",name ="commentCount")
    private int commentCount;

}
