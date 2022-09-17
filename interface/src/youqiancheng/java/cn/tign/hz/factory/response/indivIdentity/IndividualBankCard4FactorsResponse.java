package cn.tign.hz.factory.response.indivIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.IndividualBankCard4FactorsData;

/**
 * 实名认证发起银行4要素认证响应
 * @author  澄泓
 * @date  2020/11/16 10:05
 * @version JDK1.7
 */
public class IndividualBankCard4FactorsResponse extends Response {
    private IndividualBankCard4FactorsData data;

    public IndividualBankCard4FactorsData getData() {
        return data;
    }

    public void setData(IndividualBankCard4FactorsData data) {
        this.data = data;
    }
}
