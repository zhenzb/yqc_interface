package com.youqiancheng.form.manager.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class UserAuthForm {
    @ApiModelProperty(value = "名字",name ="name")
    private long name;
    @ApiModelProperty(value = "审核状态",name ="status")
    private int status;
}
