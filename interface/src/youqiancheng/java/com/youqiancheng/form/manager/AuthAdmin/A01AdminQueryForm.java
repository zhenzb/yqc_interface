package com.youqiancheng.form.manager.AuthAdmin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class A01AdminQueryForm {
    @ApiModelProperty("管理员姓名")
    private String userName;
}
