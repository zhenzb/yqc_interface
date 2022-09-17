package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.SetSignAuthResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API设置静默签署
 * @author  澄泓
 * @date  2020/10/23 20:23
 * @version JDK1.7
 */
public class SetSignAuth extends Request<SetSignAuthResponse> {
    @JSONField(serialize = false)
    private String accountId;

    private String deadline;

    //禁止构造无参对象
    private SetSignAuth() {}

    public SetSignAuth(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public void build() {
        super.setUrl("/v1/signAuth/"+accountId);
        super.setRequestType(RequestType.POST);
    }
}
