package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.GetAuthSignUrlData;

/**
 * 实名认证获取授权签署链接响应
 * @author  澄泓
 * @date  2020/11/16 15:26
 * @version JDK1.7
 */
public class GetAuthSignUrlResponse extends Response {
    private GetAuthSignUrlData data;

    public GetAuthSignUrlData getData() {
        return data;
    }

    public void setData(GetAuthSignUrlData data) {
        this.data = data;
    }
}
