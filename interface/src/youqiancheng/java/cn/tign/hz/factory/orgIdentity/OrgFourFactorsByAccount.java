package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.OrgFourFactorsResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起企业实名认证4要素校验
 * @author  澄泓
 * @date  2020/11/16 14:26
 * @version JDK1.7
 */
public class OrgFourFactorsByAccount extends Request<OrgFourFactorsResponse> {
    @JSONField(serialize = false)
    private String accountId;
    private String agentAccountId;
    private boolean repetition=true;
    private String contextId;
    private String notifyUrl;
    private boolean frAuthEnable;

    private OrgFourFactorsByAccount(){};
    public OrgFourFactorsByAccount(String accountId, String agentAccountId) {
        this.accountId = accountId;
        this.agentAccountId = agentAccountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAgentAccountId() {
        return agentAccountId;
    }

    public void setAgentAccountId(String agentAccountId) {
        this.agentAccountId = agentAccountId;
    }

    public boolean getRepetition() {
        return repetition;
    }

    public void setRepetition(boolean repetition) {
        this.repetition = repetition;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public boolean getFrAuthEnable() {
        return frAuthEnable;
    }

    public void setFrAuthEnable(boolean frAuthEnable) {
        this.frAuthEnable = frAuthEnable;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/organization/enterprise/"+accountId+"/fourFactors");
        super.setRequestType(RequestType.POST);
    }
}
