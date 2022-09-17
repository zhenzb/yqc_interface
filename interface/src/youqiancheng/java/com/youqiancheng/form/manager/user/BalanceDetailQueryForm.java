package com.youqiancheng.form.manager.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class BalanceDetailQueryForm {
    @NotNull(message = "请输入账号id")
    @ApiModelProperty(value = "账号id")
    private Long id;
    @ApiModelProperty(value = "状态 收入传1 支出传2")
    private Short type;
}
