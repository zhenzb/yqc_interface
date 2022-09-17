package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.OrgThreeFactorsResponse;

/**
 * 实名认证发起企业核身认证3要素检验
 * @author  澄泓
 * @date  2020/11/16 14:55
 * @version JDK1.7
 */
public class OrgThreeFactors extends Request<OrgThreeFactorsResponse> {
    private String name;
    private String orgCode;
    private String legalRepName;
    private String contextId;
    private String notifyUrl;

    private OrgThreeFactors(){};
    public OrgThreeFactors(String name, String orgCode, String legalRepName) {
        this.name = name;
        this.orgCode = orgCode;
        this.legalRepName = legalRepName;
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
        super.setUrl("/v2/identity/auth/api/organization/threeFactors");
        super.setRequestType(RequestType.POST);
    }
}
