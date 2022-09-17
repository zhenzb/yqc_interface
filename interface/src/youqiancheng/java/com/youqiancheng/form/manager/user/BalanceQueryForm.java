package com.youqiancheng.form.manager.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter
@Setter
public class BalanceQueryForm {
    @NotNull(message = "请输入用户id")
    @ApiModelProperty(value = "用户Id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
}
