package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.bean.AvailableAuthTypes;
import cn.tign.hz.bean.ContextInfo;
import cn.tign.hz.bean.IndivInfo;
import cn.tign.hz.bean.PsnConfigParams;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.IndivAuthUrlResponse;

/**
 * 实名认证获取个人核身认证地址
 * @author  澄泓
 * @date  2020/11/12 10:25
 * @version JDK1.7
 */
public class IndivAuthUrl extends Request<IndivAuthUrlResponse> {
    private String authType;
    private AvailableAuthTypes availableAuthTypes;
    private String receiveUrlMobileNo;
    private ContextInfo contextInfo;
    private IndivInfo indivInfo;
    private PsnConfigParams configParams;

    public IndivAuthUrl(){};
    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public AvailableAuthTypes getAvailableAuthTypes() {
        return availableAuthTypes;
    }

    public void setAvailableAuthTypes(AvailableAuthTypes availableAuthTypes) {
        this.availableAuthTypes = availableAuthTypes;
    }

    public String getReceiveUrlMobileNo() {
        return receiveUrlMobileNo;
    }

    public void setReceiveUrlMobileNo(String receiveUrlMobileNo) {
        this.receiveUrlMobileNo = receiveUrlMobileNo;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    public IndivInfo getIndivInfo() {
        return indivInfo;
    }

    public void setIndivInfo(IndivInfo indivInfo) {
        this.indivInfo = indivInfo;
    }

    public PsnConfigParams getConfigParams() {
        return configParams;
    }

    public void setConfigParams(PsnConfigParams configParams) {
        this.configParams = configParams;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/web/indivAuthUrl");
        super.setRequestType(RequestType.POST);
    }
}
