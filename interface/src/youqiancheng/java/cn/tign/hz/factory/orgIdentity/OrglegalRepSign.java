package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.OrglegalRepSignAuthResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起授权签署核身认证
 * @author  澄泓
 * @date  2020/11/16 15:13
 * @version JDK1.7
 */
public class OrglegalRepSign extends Request<OrglegalRepSignAuthResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String agentName;
    private String agentIdNo;
    private String mobileNo;
    private String legalRepIdNo;
    private String redirectUrl;

    private OrglegalRepSign(){};
    public OrglegalRepSign(String flowId, String agentName, String agentIdNo, String mobileNo) {
        this.flowId = flowId;
        this.agentName = agentName;
        this.agentIdNo = agentIdNo;
        this.mobileNo = mobileNo;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentIdNo() {
        return agentIdNo;
    }

    public void setAgentIdNo(String agentIdNo) {
        this.agentIdNo = agentIdNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLegalRepIdNo() {
        return legalRepIdNo;
    }

    public void setLegalRepIdNo(String legalRepIdNo) {
        this.legalRepIdNo = legalRepIdNo;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/organization/"+flowId+"/legalRepSign");
        super.setRequestType(RequestType.POST);
    }
}
