package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.OrgFourFactorsResponse;

/**
 * 实名认证发起企业核身认证4要素校验
 * @author  澄泓
 * @date  2020/11/16 14:37
 * @version JDK1.7
 */
public class OrgFourFactors extends Request<OrgFourFactorsResponse> {
    private String name;
    private String orgCode;
    private String legalRepName;
    private String legalRepIdNo;
    private String contextId;
    private String notifyUrl;

    private OrgFourFactors(){};
    public OrgFourFactors(String name, String orgCode, String legalRepName, String legalRepIdNo) {
        this.name = name;
        this.orgCode = orgCode;
        this.legalRepName = legalRepName;
        this.legalRepIdNo = legalRepIdNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getLegalRepName() {
        return legalRepName;
    }

    public void setLegalRepName(String legalRepName) {
        this.legalRepName = legalRepName;
    }

    public String getLegalRepIdNo() {
        return legalRepIdNo;
    }

    public void setLegalRepIdNo(String legalRepIdNo) {
        this.legalRepIdNo = legalRepIdNo;
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
        super.setUrl("/v2/identity/auth/api/organization/enterprise/fourFactors");
        super.setRequestType(RequestType.POST);
    }
}
