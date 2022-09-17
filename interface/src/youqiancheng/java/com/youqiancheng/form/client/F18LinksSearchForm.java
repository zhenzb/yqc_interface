package com.youqiancheng.form.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2021-11-20
 */
@Data
@ApiModel(value = "查询实体")
public class F18LinksSearchForm {
    @ApiModelProperty(value = "(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "",name ="sourceId")
    private Long sourceId;
    @ApiModelProperty(value = "",name ="linkName")
    private String linkName;
    @ApiModelProperty(value = "",name ="linkUrl")
    private String linkUrl;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "",name ="isDelete")
    private Integer isDelete;



}
