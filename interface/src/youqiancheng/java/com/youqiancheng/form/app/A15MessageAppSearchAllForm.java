package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "APP——消息查询所有计数实体")
public class A15MessageAppSearchAllForm {
    @NotNull(message = "请输入用户id")
    @ApiModelProperty(value = "用户ID",name ="userId")
    private Long userId;



    public static void main(String[] args) {
        //long id=Long.parseLong(null);
        //long id1=Long.parseLong("");
        long id2=Long.parseLong("0");
       // System.out.println("*******"+id);
        //System.out.println("&&&&"+id1);
        System.out.println("^^^^^^^^"+id2);
    }
}
