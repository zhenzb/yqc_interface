package cn.tign.hz.factory.signfile.signflows;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.GetVoucherSignFlowResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API流程签署数据存储凭据
 * @author  澄泓
 * @date  2020/10/28 10:58
 * @version JDK1.7
 */
public class GetVoucherSignFlow extends Request<GetVoucherSignFlowResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private GetVoucherSignFlow(){};
    public GetVoucherSignFlow(String flowId) {
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
        super.setUrl("/api/v2/signflows/"+flowId+"/getVoucher");
        super.setRequestType(RequestType.GET);
    }
}
