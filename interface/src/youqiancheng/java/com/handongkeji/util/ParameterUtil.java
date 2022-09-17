package com.handongkeji.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ParameterUtil {

    protected static final Log logger = LogFactory.getLog(ParameterUtil.class);

    // 参数排序
    public static String parameSort(String params) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.notNullOrEmpty(params)) {
            String[] prms = params.split("");
            Arrays.sort(prms);
            for (int i = 0; i < prms.length; i++) {
                sb.append(prms[i]);
            }
        }
        return sb.toString();
    }

    // 参数为空
    public static JsonBean parameterPms(String... args) {
        JsonBean result = new JsonBean();
        boolean flag = true;
        for (String str : args) {
            if (str == null || str.length() == 0 || "".equals(str)) {
                flag = false;
            }
        }
        if (!flag) {
            result.setStatus(Parameter.Error);
            result.setMessage(Parameter.ERROR_TOKENPARAMETER_MESSAGE);
        } else {
            result.setStatus(Parameter.$Success);
            result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        }
        return result;
    }

    // 判断
    public static JsonBean parameterPte(Integer status, String statutx) {
        JsonBean result = new JsonBean();
        result.setStatus(status);
        result.setMessage(statutx);
        return result;
    }

    // 成功参数封装
    public static JsonBean parameterSuccess(Object obj) {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.$Success);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        result.setData(obj);
        return result;
    }

    // 成功参数封装
    public static JsonBean parameterSuccess() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.$Success);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        result.setData(new HashMap<>());
        return result;
    }

    // 失败参数封装
    public static JsonBean parameterFailure() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.$Failure);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        result.setData(new HashMap<>());
        return result;
    }

    // 失败参数封装
    public static JsonBean parameterFailure(Object obj) {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.$Failure);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        result.setData(obj);
        return result;
    }

    // 失败参数封装
    public static JsonBean parameterFailures(String vul) {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.$Failure);
        result.setMessage(vul);
        return result;
    }
    // 开小差参数封装
    public static JsonBean parameterFailTimeOut() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setType(String.valueOf(Parameter.Error));
        result.setMessage("服务器开小差啦,请稍后再试");
        return result;
    }

    // 参数成功封装
    public static JsonBean parameterSuess(Object obj, JsonBean result) {
        result.setStatus(Parameter.$Success);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        result.setData(obj);
        return result;
    }

    // 失败参数封装
    public static JsonBean parameterFail() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setMessage(Parameter.EXECUTION_FAILURE_MESSAGE);
        return result;
    }
    // 失败参数封装
    public static JsonBean parameterFail(Object obj) {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setMessage(Parameter.EXECUTION_FAILURE_MESSAGE);
        result.setData(obj);
        return result;
    }
    // token失败封装
    public static JsonBean tokenFail(NumberFormatException e) {
        JsonBean result = new JsonBean();
        log.info("参数或token解析错误：" + e.getMessage());
        result.setStatus(Constants.Error);
        result.setMessage(Constants.BAD_PARAMETERS_MESSAGE);
        return result;
    }

    // 操作封装
    public static JsonBean operationResult(Integer status) {
        JsonBean result = new JsonBean();
        result.setStatus(status);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        return result;
    }


    // 参数
    public static JsonBean parameMap(HttpServletRequest request, String params, JsonBean result) {
        Map<String, String[]> map = request.getParameterMap();
        List<String> prams = EncapsulationListUtil.changeStringType(params);
        result.setStatus(Parameter.$Success);
        for (int k = 0; k < prams.size(); k++) {
            if (StringUtil.notNullOrEmpty(prams.get(k))) {
                if (map.get(prams.get(k)) != null) {

                } else {
                    result.setStatus(Parameter.Error);
                    result.setMessage(Parameter.ERROR_TOKENPARAMETER_MESSAGE);
                    break;
                }
            }
        }
        if (1 == result.getStatus()) {
            result.setStatus(Parameter.$Success);
            result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        }
        return result;
    }

    // 参数异常
    public static JsonBean parameterEpn() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setMessage(Parameter.ERROR_TOKENPARAMETER_MESSAGE);
        return result;
    }

    // 获取用户id
    public static JsonBean getUid(HttpServletRequest request, JsonBean result) {
        Long userid = null;
        try {
            userid = (Long) request.getAttribute("auth_uid");
            result = ParameterUtil.parameterSuccess(userid);
        } catch (Exception e) {
            logger.info("用户id解析失败" + e.getMessage());
            result.setStatus(Constants.$Failure);
            result.setMessage(Constants.ERROR_TOKEN_MESSAGE);
            result.setData(userid);
            return result;
        }
        return result;
    }
    // 失败参数封装
    public static JsonBean parameterFail(Integer status,String message,Object data) {
        JsonBean result = new JsonBean();
        result.setData(data);
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }
    // 失败参数封装
    public static JsonBean parameterFail(Integer status,String message) {
        JsonBean result = new JsonBean();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

    // 参数
    public static JsonBean parameterSfl() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.$Success);
        result.setMessage(Parameter.EXECUTION_SUCCESS_MESSAGE);
        return result;
    }

    // 类型转换异常
    public static JsonBean typeEpn() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setMessage(Parameter.TYPE_ERROR_MESSAGE);
        return result;
    }

    // 插入数据异常
    public static JsonBean insertEpn() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setMessage(Parameter.INSERT_ERROR_MESSAGE);
        return result;
    }

    // 更新数据异常
    public static JsonBean updateEpn() {
        JsonBean result = new JsonBean();
        result.setStatus(Parameter.Error);
        result.setMessage(Parameter.UPDATE_ERROR_MESSAGE);
        return result;
    }


}




