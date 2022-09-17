package com.handongkeji.util.manager;


import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果的操作类
 */
public class ResultVOUtils {

    /**
     * 成功时返回
     * @param data 返回的data对象
     * @return {@link ResultVo}
     */
    public static ResultVo success(Object data) {
        ResultVo<Object> resultVO = new ResultVo<>();
        resultVO.setCode(0);
        resultVO.setMessage("success");
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 成功时返回
     * @return {@link ResultVo}
     */
    public static ResultVo success() {
        Map data = new HashMap();
        return success(data);
    }

    /**
     * 错误时返回
     * @param code 错误码
     * @param message 错误信息
     * @return {@link ResultVo}
     */
    public static ResultVo error(Integer code, String message) {
        ResultVo<Object> resultVO = new ResultVo<>();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }

    /**
     * 错误时返回
     * @param resultEnum 错误枚举类
     * @return {@link ResultVo}
     */
    public static ResultVo error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMessage());
    }

    /**
     * 错误时返回
     * @param resultEnum 错误枚举类
     * @param message 错误的信息
     * @return {@link ResultVo}
     */
    public static ResultVo error(ResultEnum resultEnum, String message) {
        return error(resultEnum.getCode(), message);
    }

}
