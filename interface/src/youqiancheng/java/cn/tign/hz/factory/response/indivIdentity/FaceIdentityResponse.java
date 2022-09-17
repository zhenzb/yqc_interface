package cn.tign.hz.factory.response.indivIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.FaceIdentityData;

/**
 * 实名认证发起个人刷脸实名认证响应
 * @author  澄泓
 * @date  2020/11/12 10:39
 * @version JDK1.7
 */
public class FaceIdentityResponse extends Response {
    private FaceIdentityData data;

    public FaceIdentityData getData() {
        return data;
    }

    public void setData(FaceIdentityData data) {
        this.data = data;
    }
}
