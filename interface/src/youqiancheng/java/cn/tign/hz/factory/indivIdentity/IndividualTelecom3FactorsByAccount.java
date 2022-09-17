package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.IndividualTelecom3FactorsResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起运营商3要素实名认证
 * @author  澄泓
 * @date  2020/11/12 15:15
 * @version JDK1.7
 */
public class IndividualTelecom3FactorsByAccount extends Request<IndividualTelecom3FactorsResponse> {
    @JSONField(serialize = false)
    private String accountId;
    private String mobileNo;
    private boolean repetition=true;
    private String contextId;
    private String notifyUrl;

    private IndividualTelecom3FactorsByAccount(){};
    public IndividualTelecom3FactorsByAccount(String accountId, String mobileNo) {
        this.accountId = accountId;
        this.mobileNo = mobileNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/individual/"+accountId+"/telecom3Factors");
        super.setRequestType(RequestType.POST);
    }
}
