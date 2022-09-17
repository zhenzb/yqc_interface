package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.Tel3FactorsCodeVerifyResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证运营商短信验证码校验
 * @author  澄泓
 * @date  2020/11/16 9:58
 * @version JDK1.7
 */
public class Tel3FactorsCodeVerify extends Request<Tel3FactorsCodeVerifyResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String authcode;

    private Tel3FactorsCodeVerify(){};
    public Tel3FactorsCodeVerify(String flowId, String authcode) {
        this.flowId = flowId;
        this.authcode = authcode;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/pub/individual/"+flowId+"/telecom3Factors");
        super.setRequestType(RequestType.PUT);
    }
}
