package com.youqiancheng.form.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "版本更新查询实体")
public class A13SysVersionSearchForm  {
    @ApiModelProperty(value = "(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "",name ="VersionExplain")
    private String VersionExplain;
    @ApiModelProperty(value = "",name ="VersionUrl")
    private String VersionUrl;
    @ApiModelProperty(value = "",name ="VersionLowest")
    private String VersionLowest;
    @ApiModelProperty(value = "",name ="Version")
    private String Version;
    @ApiModelProperty(value = "",name ="VersionName")
    private String VersionName;
    @ApiModelProperty(value = "1：强制更新  0：非强制",name ="Versionupdate")
    private String Versionupdate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "",name ="VersionTime")
    private LocalDateTime VersionTime;



}
