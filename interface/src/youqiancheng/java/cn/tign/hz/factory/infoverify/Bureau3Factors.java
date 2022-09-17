package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.Bureau3FactorsResponse;

/**
 * 实名认证企业3要素信息比对
 * @author  澄泓
 * @date  2020/11/2 17:05
 * @version JDK1.7
 */
public class Bureau3Factors extends Request<Bureau3FactorsResponse> {
    private String name;
    private String orgCode;
    private String legalRepName;

    private Bureau3Factors(){};
    public Bureau3Factors(String name, String orgCode, String legalRepName) {
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

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/organization/enterprise/bureau3Factors");
        super.setRequestType(RequestType.POST);
    }
}
