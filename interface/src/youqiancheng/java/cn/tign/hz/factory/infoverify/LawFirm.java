package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.LawFirmResponse;

/**
 * 实名认证律所3要素信息比对
 * @author  澄泓
 * @date  2020/11/2 17:10
 * @version JDK1.7
 */
public class LawFirm extends Request<LawFirmResponse> {
    private String name;
    private String codeUSC;
    private String legalRepName;

    private LawFirm(){};
    public LawFirm(String name, String codeUSC, String legalRepName) {
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
        super.setUrl("/v2/identity/verify/organization/lawFirm");
        super.setRequestType(RequestType.POST);
    }
}
