package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——银行卡保存实体")
public class B09BankCardSaveForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "账号",name ="cardNo")
    private String cardNo;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;

}
