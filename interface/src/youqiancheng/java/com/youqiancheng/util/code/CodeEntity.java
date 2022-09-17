package com.youqiancheng.util.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodeEntity {
    @ApiModelProperty(value = "用户名/手机号",name ="phone")
    private String phone;
    @ApiModelProperty(value = "图片验证码/短信验证码",name ="code")
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "验证码生成时间",name ="createTime")
    private LocalDateTime createTime;
}


