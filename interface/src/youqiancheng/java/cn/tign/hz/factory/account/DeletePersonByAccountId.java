package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DeletePersonByAccountIdResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API注销个人账户（按照账号ID注销）
 * @author  澄泓
 * @date  2020/10/23 17:31
 * @version JDK1.7
 */
public class DeletePersonByAccountId extends Request<DeletePersonByAccountIdResponse> {
    @JSONField(serialize = false)
    private String accountId;

    //禁止构造无参对象
    private DeletePersonByAccountId(){}

    public DeletePersonByAccountId(String accountId) {
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
        super.setUrl("/v1/accounts/"+accountId);
        super.setRequestType(RequestType.DELETE);
    }
}
