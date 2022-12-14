package com.youqiancheng.controller.wechatpay.weixinpay;

/**
 * @Author Captain Ren
 * @Description TODO
 * @Date 2019/4/16 0016 15:44
 * @Param
 * @return
 **/
public class RefundResData
{

    // 协议层
    private String return_code = "";
    private String return_msg = "";
    // 协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
    private String appid = "";
    private String mch_id = "";
    private String nonce_str = "";
    private String sign = "";
    private String result_code = "";
    private String transaction_id = "";
    private String out_trade_no = "";
    private String out_refund_no = "";
    private String refund_id = "";
    private String refund_channel = "";
    private String refund_fee = "";
    private String coupon_refund_fee = "";
    private String total_fee = "";
    private String cash_fee = "";
    private String coupon_refund_count = "";
    private String cash_refund_fee = "";
    private String err_code_des = "";

    public String getErr_code_des()
    {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des)
    {
        this.err_code_des = err_code_des;
    }

    public String getReturn_code()
    {
        return return_code;
    }

    public void setReturn_code(String return_code)
    {
        this.return_code = return_code;
    }

    public String getReturn_msg()
    {
        return return_msg;
    }

    public void setReturn_msg(String return_msg)
    {
        this.return_msg = return_msg;
    }

    public String getAppid()
    {
        return appid;
    }

    public void setAppid(String appid)
    {
        this.appid = appid;
    }

    public String getMch_id()
    {
        return mch_id;
    }

    public void setMch_id(String mch_id)
    {
        this.mch_id = mch_id;
    }

    public String getNonce_str()
    {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getResult_code()
    {
        return result_code;
    }

    public void setResult_code(String result_code)
    {
        this.result_code = result_code;
    }

    public String getTransaction_id()
    {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no()
    {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no)
    {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no()
    {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no)
    {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id()
    {
        return refund_id;
    }

    public void setRefund_id(String refund_id)
    {
        this.refund_id = refund_id;
    }

    public String getRefund_channel()
    {
        return refund_channel;
    }

    public void setRefund_channel(String refund_channel)
    {
        this.refund_channel = refund_channel;
    }

    public String getRefund_fee()
    {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee)
    {
        this.refund_fee = refund_fee;
    }

    public String getCoupon_refund_fee()
    {
        return coupon_refund_fee;
    }

    public void setCoupon_refund_fee(String coupon_refund_fee)
    {
        this.coupon_refund_fee = coupon_refund_fee;
    }

    public String getTotal_fee()
    {
        return total_fee;
    }

    public void setTotal_fee(String total_fee)
    {
        this.total_fee = total_fee;
    }

    public String getCash_fee()
    {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee)
    {
        this.cash_fee = cash_fee;
    }

    public String getCoupon_refund_count()
    {
        return coupon_refund_count;
    }

    public void setCoupon_refund_count(String coupon_refund_count)
    {
        this.coupon_refund_count = coupon_refund_count;
    }

    public String getCash_refund_fee()
    {
        return cash_refund_fee;
    }

    public void setCash_refund_fee(String cash_refund_fee)
    {
        this.cash_refund_fee = cash_refund_fee;
    }


}
