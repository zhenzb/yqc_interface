package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP——用户信息修改实体")
public class B01UserUpdateForm implements Serializable {
    @ApiModelProperty(value = "用户Id(数据库自增)")
    private long id;
    @ApiModelProperty(value = "昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "手机号",name ="mobile")
    private String mobile;
    @ApiModelProperty(value = "用户头像",name ="pic")
    private String pic;
    @ApiModelProperty(value = "性别",name ="sex")
    private int sex;
    @ApiModelProperty(value = "生日",name ="birthday")
    private Date birthday;

}
