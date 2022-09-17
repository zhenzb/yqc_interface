package com.youqiancheng.vo.result;

import lombok.Getter;

/**
 * 返回结果的枚举类
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "success"),
    NOT_NETWORK(-1, "系统繁忙，请稍后再试。"),
    LOGIN_VERIFY_FAIL(2, "登录信息已失效"),
    LOGIN_VERIFY_RIGHT(-2, "您的电脑已被禁止访问此网站"),
    PARAM_VERIFY_FAIL(3, "参数校验错误"),
    AUTH_FAILED(4, "权限验证失败"),
    DATA_NOT_EXIST(5, "没有相关数据"),
    DATA_CHANGE(6, "数据没有任何更改"),
    DATA_REPEAT(7, "数据已存在"),
    PRE_ADMIN(8, "请先删除对应的管理员"),
    NOT_RESOURCES(9,"资源路径不存在"),
    NOT_ALLOW_CHANGE(10,"admin不允许修改"),
    INSERT_FAIL(11,"插入失败"),
    UPDATE_FAIL(12,"修改失败"),
    DELETE_FAIL(13,"删除失败"),
    STOP_FAIL(14,"停用失败"),
    START_FAIL(15,"启用失败"),
    PRE_ROLE(16,"请先删除对应的角色"),
    CHANGE_FAIL(17,"参数转换失败"),
    PARAM_NULL(18,"参数为空"),
    FREEZE_FAIL(18,"冻结用户失败"),
    USER_NOT_EXIST(19,"用户不存在"),
    BATCH_INSERT_FAIL(20,"批量插入失败"),
    PUBLIC_FAIL(21,"发布失败"),
    NAME_FAIL(22,"商品分类名称不能重复"),
    ONFREEZE_FAIL(23,"商家账户已被冻结"),
    LOGIN_VERIFY_OTHER(24, "账户已在其他设备登录"),
    ACCOUNT_NOT_EXISTENT(25,"推广账户不存在"),


    TEST(100,"测试");
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
