package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.QryTransferProcessResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证查询随机金额打款进度
 * @author  澄泓
 * @date  2020/11/16 16:00
 * @version JDK1.7
 */
public class QryTransferProcess extends Request<QryTransferProcessResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private QryTransferProcess(){};
    public QryTransferProcess(String flowId) {
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
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/transferProcess");
        super.setRequestType(RequestType.GET);
    }
}
