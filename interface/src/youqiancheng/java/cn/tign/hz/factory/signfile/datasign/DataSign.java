package cn.tign.hz.factory.signfile.datasign;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DataSignResponse;

/**
 * @description  轩辕API平台方&平台用户文本签
 * @author  澄泓
 * @date  2020/10/28 16:54
 * @version JDK1.7
 */
public class DataSign extends Request<DataSignResponse> {
    private String accountId;
    private String data;
    private String type;

    private DataSign(){};
    public DataSign(String data, String type) {
        this.data = data;
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void build() {
        super.setUrl("/v1/dataSign");
        super.setRequestType(RequestType.POST);
    }
}
