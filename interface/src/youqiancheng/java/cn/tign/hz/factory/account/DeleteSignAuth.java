package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DeleteSignAuthResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API撤销静默签署
 * @author  澄泓
 * @date  2020/10/23 20:22
 * @version JDK1.7
 */
public class DeleteSignAuth extends Request<DeleteSignAuthResponse> {
    @JSONField(serialize = false)
    private String accountId;

    //禁止构造无参对象
    private DeleteSignAuth() {}

    public DeleteSignAuth(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public void build() {
        super.setUrl("/v1/signAuth/"+accountId);
        super.setRequestType(RequestType.DELETE);
    }
}
