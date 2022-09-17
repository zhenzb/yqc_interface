package cn.tign.hz.factory.response.indivIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.IndividualTelecom3FactorsData;

/**
 * 实名认证发起运营商3要素实名认证响应
 * @author  澄泓
 * @date  2020/11/12 15:17
 * @version JDK1.7
 */
public class IndividualTelecom3FactorsResponse extends Response {
    private IndividualTelecom3FactorsData data;

    public IndividualTelecom3FactorsData getData() {
        return data;
    }

    public void setData(IndividualTelecom3FactorsData data) {
        this.data = data;
    }
}
