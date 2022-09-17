package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.bean.AvailableAuthTypes;
import cn.tign.hz.bean.ContextInfo;
import cn.tign.hz.bean.OrgConfigParams;
import cn.tign.hz.bean.OrgEntity;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.OrgUrlResponse;

/**
 * 实名认证获取组织机构核身地址
 * @author  澄泓
 * @date  2020/11/16 14:16
 * @version JDK1.7
 */
public class OrgAuthUrl extends Request<OrgUrlResponse> {
    private String authType;
    private AvailableAuthTypes availableAuthTypes;
    private String receiveUrlMobileNo;
    private ContextInfo contextInfo;
    private OrgEntity orgEntity;
    private OrgConfigParams configParams;
    private boolean repeatIdentity=true;


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

    public OrgEntity getOrgEntity() {
        return orgEntity;
    }

    public void setOrgEntity(OrgEntity orgEntity) {
        this.orgEntity = orgEntity;
    }

    public OrgConfigParams getConfigParams() {
        return configParams;
    }

    public void setConfigParams(OrgConfigParams configParams) {
        this.configParams = configParams;
    }

    public boolean getRepeatIdentity() {
        return repeatIdentity;
    }

    public void setRepeatIdentity(boolean repeatIdentity) {
        this.repeatIdentity = repeatIdentity;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/web/orgAuthUrl");
        super.setRequestType(RequestType.POST);
    }
}
