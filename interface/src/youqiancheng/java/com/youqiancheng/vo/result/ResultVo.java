package com.youqiancheng.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果类
 * @param <T>
 */
@Data
@ApiModel(value = "返回实体")
public class ResultVo<T> {
    @ApiModelProperty(value = "返回code;0表示成功")
    private Integer code;
    @ApiModelProperty(value = "返回的消息")
    private String message;
    @ApiModelProperty(value = "返回的数据")
    private T data;
}
