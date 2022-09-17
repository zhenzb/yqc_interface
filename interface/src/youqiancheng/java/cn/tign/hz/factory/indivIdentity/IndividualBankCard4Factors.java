package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.IndividualBankCard4FactorsResponse;

/**
 * 实名认证发起银行4要素核身认证
 * @author  澄泓
 * @date  2020/11/16 10:03
 * @version JDK1.7
 */
public class IndividualBankCard4Factors extends Request<IndividualBankCard4FactorsResponse> {
    private String name;
    private String certType;
    private String idNo;
    private String mobileNo;
    private String bankCardNo;
    private String contextId;
    private String notifyUrl;

    private IndividualBankCard4Factors(){};
    public IndividualBankCard4Factors(String name, String certType, String idNo, String mobileNo, String bankCardNo) {
        this.name = name;
        this.certType = certType;
        this.idNo = idNo;
        this.mobileNo = mobileNo;
        this.bankCardNo = bankCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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
        super.setUrl("/v2/identity/auth/api/individual/bankCard4Factors");
        super.setRequestType(RequestType.POST);
    }
}
