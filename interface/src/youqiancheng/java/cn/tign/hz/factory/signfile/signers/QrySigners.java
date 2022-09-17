package cn.tign.hz.factory.signfile.signers;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.QrySignersResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API流程签署人列表
 * @author  澄泓
 * @date  2020/10/28 16:31
 * @version JDK1.7
 */
public class QrySigners extends Request<QrySignersResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private QrySigners(){};
    public QrySigners(String flowId) {
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
        super.setUrl("/v1/signflows/"+flowId+"/signers");
        super.setRequestType(RequestType.GET);
    }
}
