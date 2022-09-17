package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.Bureau4FactorsResponse;

/**
 * 实名认证企业4要素信息比对
 * @author  澄泓
 * @date  2020/11/2 17:08
 * @version JDK1.7
 */
public class Bureau4Factors extends Request<Bureau4FactorsResponse> {
    private String name;
    private String orgCode;
    private String legalRepName;
    private String legalRepCertNo;

    private Bureau4Factors(){};
    public Bureau4Factors(String name, String orgCode, String legalRepName, String legalRepCertNo) {
        this.name = name;
        this.orgCode = orgCode;
        this.legalRepName = legalRepName;
        this.legalRepCertNo = legalRepCertNo;
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

    public String getLegalRepCertNo() {
        return legalRepCertNo;
    }

    public void setLegalRepCertNo(String legalRepCertNo) {
        this.legalRepCertNo = legalRepCertNo;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/organization/enterprise/bureau4Factors");
        super.setRequestType(RequestType.POST);
    }
}
