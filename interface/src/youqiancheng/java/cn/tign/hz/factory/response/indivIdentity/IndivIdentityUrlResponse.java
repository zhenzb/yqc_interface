package cn.tign.hz.factory.response.indivIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.IndivIdentityUrlData;

/**
 * 实名认证
 * @author  澄泓
 * @date  2020/11/17 13:59
 * @version 
 */
public class IndivIdentityUrlResponse extends Response {
    private IndivIdentityUrlData data;

    public IndivIdentityUrlData getData() {
        return data;
    }

    public void setData(IndivIdentityUrlData data) {
        this.data = data;
    }
}
