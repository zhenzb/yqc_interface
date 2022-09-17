package cn.tign.hz.factory.response.infoverify;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.InfoVerifyData;

/**
 * 实名认证企业3要素信息比对响应
 * @author  澄泓
 * @date  2020/11/2 17:06
 * @version JDK1.7
 */
public class Bureau3FactorsResponse extends Response {
    private InfoVerifyData data;

    public InfoVerifyData getData() {
        return data;
    }

    public void setData(InfoVerifyData data) {
        this.data = data;
    }
}
