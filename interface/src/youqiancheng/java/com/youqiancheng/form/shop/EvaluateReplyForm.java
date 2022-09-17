package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--公共获取ID集
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class EvaluateReplyForm {
    @ApiModelProperty("id")
    private Long  id;
    @ApiModelProperty("回复")
    private String  reply;
}


