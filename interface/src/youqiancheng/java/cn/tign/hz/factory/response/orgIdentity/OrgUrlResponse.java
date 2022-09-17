package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.OrgIdentityUrlData;

/**
 * 实名认证获取组织机构实名认证地址响应
 * @author  澄泓
 * @date  2020/11/16 11:04
 * @version JDK1.7
 */
public class OrgUrlResponse extends Response {
    private OrgIdentityUrlData data;

    public OrgIdentityUrlData getData() {
        return data;
    }

    public void setData(OrgIdentityUrlData data) {
        this.data = data;
    }
}
