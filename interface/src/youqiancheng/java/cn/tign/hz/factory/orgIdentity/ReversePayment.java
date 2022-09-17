package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.ReversePaymentResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起企业反向打款认证
 * @author  澄泓
 * @date  2020/11/16 16:04
 * @version JDK1.7
 */
public class ReversePayment extends Request<ReversePaymentResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private ReversePayment(){};
    public ReversePayment(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/reversePayment");
        super.setRequestType(RequestType.POST);
    }
}
