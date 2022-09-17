package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.VerifyRandomAmountResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证随机金额校验
 * @author  澄泓
 * @date  2020/11/16 15:57
 * @version JDK1.7
 */
public class VerifyRandomAmount extends Request<VerifyRandomAmountResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String amount;

    private VerifyRandomAmount(){};
    public VerifyRandomAmount(String flowId, String amount) {
        this.flowId = flowId;
        this.amount = amount;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/verifyRandomAmount");
        super.setRequestType(RequestType.PUT);
    }
}
