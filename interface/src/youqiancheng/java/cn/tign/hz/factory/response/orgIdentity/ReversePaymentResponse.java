package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.ReversePaymentData;

/**
 * 实名认证发起企业反向打款认证响应
 * @author  澄泓
 * @date  2020/11/16 16:06
 * @version JDK1.7
 */
public class ReversePaymentResponse extends Response {
    private ReversePaymentData data;

    public ReversePaymentData getData() {
        return data;
    }

    public void setData(ReversePaymentData data) {
        this.data = data;
    }
}
