package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.OrglegalRepSignAuthResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起授权签署实名认证
 * @author  澄泓
 * @date  2020/11/16 15:08
 * @version JDK1.7
 */
public class OrglegalRepSignAuth extends Request<OrglegalRepSignAuthResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String mobileNo;
    private String legalRepIdNo;
    private String redirectUrl;

    private OrglegalRepSignAuth(){};
    public OrglegalRepSignAuth(String flowId, String mobileNo) {
        this.flowId = flowId;
        this.mobileNo = mobileNo;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
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
        super.setUrl("/v2/identity/auth/api/organization/"+flowId+"/legalRepSignAuth");
        super.setRequestType(RequestType.POST);
    }
}
