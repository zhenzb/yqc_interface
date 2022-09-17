package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Getter
@Setter
public class ExamineSaveEditForm {
    @NotNull(message = "商家审核标记不能为空")
    @ApiModelProperty(value = "商家审核标记：启用停用；是否开启商家入驻信息审核，关闭则为信息免审核状态",name ="examineFlag")
    private Integer examineFlag;

    @NotNull(message = "身份证和营业执照上传不能为空")
    @ApiModelProperty(value = "身份证和营业执照上传：关闭后小程序端商家入驻不在有上传身份证和营业执照的选项",name ="uploadFlag")
    private Integer uploadFlag;
    @ApiModelProperty(value = "入住须知",name ="content")
    private String content;
}
