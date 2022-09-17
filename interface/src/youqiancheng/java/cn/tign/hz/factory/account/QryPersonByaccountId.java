package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.QryPersonByaccountIdResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API查询个人账户（按照账户ID查询）
 * @author  澄泓
 * @date  2020/10/23 17:03
 * @version JDK1.7
 */
public class QryPersonByaccountId extends Request<QryPersonByaccountIdResponse> {
    @JSONField(serialize = false)
    private String accountId;

    //禁止构造无参对象
    private QryPersonByaccountId(){};

    public QryPersonByaccountId(String accountId) {
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
        super.setRequestType(RequestType.GET);
    }
}
