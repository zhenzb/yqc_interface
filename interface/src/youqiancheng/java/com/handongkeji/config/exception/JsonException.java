package com.handongkeji.config.exception;

import com.youqiancheng.vo.result.ResultEnum;
import lombok.Getter;

/**
 * 错误处理类
 * 异常类封装——定义抛出的异常类
 */
@Getter
public class JsonException extends RuntimeException {

    private Integer code;

    public JsonException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public JsonException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }

    public JsonException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
