package cn.tign.hz.factory.response.indivIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.FaceIdentityData;

/**
 * 实名认证
 * @author  澄泓
 * @date  2020/11/17 13:58
 * @version 
 */
public class FaceIdentityByAccountResponse extends Response {
    private FaceIdentityData data;

    public FaceIdentityData getData() {
        return data;
    }

    public void setData(FaceIdentityData data) {
        this.data = data;
    }
}
