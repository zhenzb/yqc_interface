package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.OrgVerifyResponse;

/**
 * 实名认证组织机构3要素信息比对
 * @author  澄泓
 * @date  2020/11/2 17:16
 * @version JDK1.7
 */
public class OrgVerify extends Request<OrgVerifyResponse> {
    private String name;
    private String orgCode;
    private String legalRepName;

    private OrgVerify(){};
    public OrgVerify(String name, String orgCode, String legalRepName) {
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
        super.setUrl("/v2/identity/verify/organization/verify");
        super.setRequestType(RequestType.POST);
    }
}
