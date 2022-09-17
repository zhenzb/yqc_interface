package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.OrgThreeFactorsData;

/**
 * 实名认证发起企业认证3要素校验响应
 * @author  澄泓
 * @date  2020/11/16 14:49
 * @version JDK1.7
 */
public class OrgThreeFactorsResponse extends Response {
    private OrgThreeFactorsData data;

    public OrgThreeFactorsData getData() {
        return data;
    }

    public void setData(OrgThreeFactorsData data) {
        this.data = data;
    }
}
