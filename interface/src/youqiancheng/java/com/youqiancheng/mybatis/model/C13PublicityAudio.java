package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Zhenzhuobin
 * @Date: 2020/7/22 0022 17:44
 */
@Data
@ApiModel(value = "宣传音频表")
@TableName("c13_publicity_audio")
public class C13PublicityAudio implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;

    @ApiModelProperty(value = "店铺ID",name ="shopId")
    private long shopId;

    @ApiModelProperty(value = "宣传ID",name ="publicityId")
    private long publicityId;

    @ApiModelProperty(value = "音频名称",name ="audioName")
    private String audioName;

    @ApiModelProperty(value = "音频路径",name ="audioUrl")
    private String audioUrl;

    @ApiModelProperty(value = "排序",name = "carouselSort")
    private int carouselSort;

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
}
