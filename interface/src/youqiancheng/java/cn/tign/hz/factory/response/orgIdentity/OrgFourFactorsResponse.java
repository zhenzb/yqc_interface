package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.OrgfourFactorsData;

/**
 * 实名认证发起企业实名认证4要素校验响应
 * @author  澄泓
 * @date  2020/11/16 14:31
 * @version JDK1.7
 */
public class OrgFourFactorsResponse extends Response {
    private OrgfourFactorsData data;

    public OrgfourFactorsData getData() {
        return data;
    }

    public void setData(OrgfourFactorsData data) {
        this.data = data;
    }
}
