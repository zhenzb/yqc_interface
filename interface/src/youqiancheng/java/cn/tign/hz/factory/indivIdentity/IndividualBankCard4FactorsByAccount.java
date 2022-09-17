package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.IndividualBankCard4FactorsResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起银行4要素实名认证
 * @author  澄泓
 * @date  2020/11/16 10:08
 * @version JDK1.7
 */
public class IndividualBankCard4FactorsByAccount extends Request<IndividualBankCard4FactorsResponse> {
    @JSONField(serialize = false)
    private String accountId;
    private String mobileNo;
    private String bankCardNo;
    private boolean repetition=true;
    private String contextId;
    private String notifyUrl;

    private IndividualBankCard4FactorsByAccount(){};
    public IndividualBankCard4FactorsByAccount(String accountId, String mobileNo, String bankCardNo) {
        this.accountId = accountId;
        this.mobileNo = mobileNo;
        this.bankCardNo = bankCardNo;
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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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
        super.setUrl("/v2/identity/auth/api/individual/"+accountId+"/bankCard4Factors");
        super.setRequestType(RequestType.POST);
    }
}
