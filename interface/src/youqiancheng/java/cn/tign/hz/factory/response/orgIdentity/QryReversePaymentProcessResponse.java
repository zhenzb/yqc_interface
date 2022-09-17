package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.QryReversePaymentProcessData;

/**
 * 实名认证查询反向打款进度响应
 * @author  澄泓
 * @date  2020/11/16 16:11
 * @version JDK1.7
 */
public class QryReversePaymentProcessResponse extends Response {
    private QryReversePaymentProcessData data;

    public QryReversePaymentProcessData getData() {
        return data;
    }

    public void setData(QryReversePaymentProcessData data) {
        this.data = data;
    }
}
