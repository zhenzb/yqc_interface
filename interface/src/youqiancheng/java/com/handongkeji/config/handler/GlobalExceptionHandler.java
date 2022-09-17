package com.handongkeji.config.handler;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 错误回调
 * 统一异常处理返回
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 拦截API异常
    @ExceptionHandler(value = JsonException.class)
    public ResultVo handlerJsonException(JsonException e) {
        // 返回对应的错误信息
        return ResultVOUtils.error(e.getCode(), e.getMessage());
    }

    // 拦截API异常
    @ExceptionHandler(value = RuntimeException.class)
    public ResultVo handlerRuntimeException(RuntimeException e) {
        //log.error(e.getMessage());
        e.printStackTrace();
        // 返回对应的错误信息
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
    }

    /**
     *  校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(BindException.class)
    public ResultVo validationBodyException(BindException exception){
        String res="";
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError)error;
                res+=fieldError.getDefaultMessage();
            }
//            errors.forEach(p ->{
//
//                FieldError fieldError = (FieldError) p;
//                //logger.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
//                   //     "},errorMessage{"+fieldError.getDefaultMessage()+"}");
//            });
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, res);
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"请填写正确信息");
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResultVo parameterTypeException(HttpMessageConversionException exception){

        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,exception.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo validationBodyException(MethodArgumentNotValidException exception){
        String res="";
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError)error;
                res+=fieldError.getDefaultMessage();
            }
//            errors.forEach(p ->{
//
//                FieldError fieldError = (FieldError) p;
//                //logger.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
//                   //     "},errorMessage{"+fieldError.getDefaultMessage()+"}");
//            });
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, res);
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"请填写正确信息");
    }

}
