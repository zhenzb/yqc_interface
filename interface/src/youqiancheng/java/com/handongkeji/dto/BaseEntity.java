package com.handongkeji.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
　* @Description: 抽取实体类公共字段，不带id
　* @author shalongteng
　* @date 2020/4/1 9:12
　*/
@Data
public class BaseEntity implements Serializable {
	@TableField(value = "create_person",fill = FieldFill.INSERT)
	@ApiModelProperty("创建人")
	private String createPerson;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty("修改人")
	@TableField(fill = FieldFill.UPDATE)
	private String updatePerson;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField(fill = FieldFill.UPDATE)
	@ApiModelProperty("修改时间")
	private LocalDateTime updateTime;

	@ApiModelProperty("删除标识 1没有删除 2 表示删除")
	private Integer deleteFlag;

}
