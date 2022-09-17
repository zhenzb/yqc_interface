package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.SocialResponse;

/**
 * 实名认证非工商组织3要素信息比对
 * @author  澄泓
 * @date  2020/11/2 17:19
 * @version JDK1.7
 */
public class Social extends Request<SocialResponse> {
    private String name;
    private String codeUSC;
    private String legalRepName;

    private Social() {
    }
    public Social(String name, String codeUSC, String legalRepName) {
        this.name = name;
        this.codeUSC = codeUSC;
        this.legalRepName = legalRepName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeUSC() {
        return codeUSC;
    }

    public void setCodeUSC(String codeUSC) {
        this.codeUSC = codeUSC;
    }

    public String getLegalRepName() {
        return legalRepName;
    }

    public void setLegalRepName(String legalRepName) {
        this.legalRepName = legalRepName;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/organization/social");
        super.setRequestType(RequestType.POST);
    }
}