package com.handongkeji.constants;
/**
　* @Description: 用户相关 枚举
　* @author shalongteng
　* @date 2020/4/8 10:47
　*/
public enum B01UserEnum {
    //是否冻结
    NOT_FREEZE(1,"未冻结"),
    IS_FREEZE(2,"已冻结");

    private int code;
    private String msg;

    private B01UserEnum(int code, String msg){
        this.code =code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
