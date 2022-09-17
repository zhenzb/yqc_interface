package com.handongkeji.constants;
/**
　* @Description: 账户流水相关 枚举
　* @author shalongteng
　* @date 2020/4/8 15:47
　*/
public enum B03UserAccountFlowEnum {
    //是否冻结
    INCOME(1,"红包收入"),//流量值
    EXPEND(2,"购物支出");//置换

    private int code;
    private String msg;

    private B03UserAccountFlowEnum(int code, String msg){
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
