package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.QrylegalRepSignResultResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证查询授权书签署状态
 * @author  澄泓
 * @date  2020/11/16 15:16
 * @version JDK1.7
 */
public class QrylegalRepSignResult extends Request<QrylegalRepSignResultResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private QrylegalRepSignResult(){};
    public QrylegalRepSignResult(String flowId) {
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
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/legalRepSignResult");
        super.setRequestType(RequestType.GET);
    }
}
