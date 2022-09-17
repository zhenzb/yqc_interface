package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.SetSignPwdResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API设置签署密码
 * @author  澄泓
 * @date  2020/10/23 18:00
 * @version JDK1.7
 */
public class SetSignPwd extends Request<SetSignPwdResponse> {
    @JSONField(serialize = false)
    private String accountId;

    private String password;

    //禁止构造无参对象
    private SetSignPwd() {}

    public SetSignPwd(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void build() {
        super.setUrl("/v1/accounts/"+accountId+"/setSignPwd");
        super.setRequestType(RequestType.POST);
    }
}
