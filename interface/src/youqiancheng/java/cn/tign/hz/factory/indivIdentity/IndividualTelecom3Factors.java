package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.IndividualTelecom3FactorsResponse;

/**
 * 实名认证发起运营商3要素核身认证
 * @author  澄泓
 * @date  2020/11/12 15:23
 * @version JDK1.7
 */
public class IndividualTelecom3Factors extends Request<IndividualTelecom3FactorsResponse> {
    private String name;
    private String idNo;
    private String mobileNo;
    private String contextId;
    private String notifyUrl;

    private IndividualTelecom3Factors(){};
    public IndividualTelecom3Factors(String name, String idNo, String mobileNo) {
        this.name = name;
        this.idNo = idNo;
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        super.setUrl("/v2/identity/auth/api/individual/telecom3Factors");
        super.setRequestType(RequestType.POST);
    }
}
