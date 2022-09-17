package cn.tign.hz.factory.signfile.datasign;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DataVerifyResponse;

/**
 * @description  轩辕API文本签验签
 * @author  澄泓
 * @date  2020/10/28 17:06
 * @version JDK1.7
 */
public class DataVerify extends Request<DataVerifyResponse> {
    private String data;
    private String signResult;

    private DataVerify(){};
    public DataVerify(String data, String signResult) {
        this.data = data;
        this.signResult = signResult;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignResult() {
        return signResult;
    }

    public void setSignResult(String signResult) {
        this.signResult = signResult;
    }

    @Override
    public void build() {
        super.setUrl("/v1/dataSign/verify");
        super.setRequestType(RequestType.POST);
    }
}
