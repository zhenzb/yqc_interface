package cn.tign.hz.factory.antfinsign;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.QrySignAntPushInfoResponse;

/**
 * @description  轩辕API查询签署文件上链信息
 * @author  澄泓
 * @date  2020/10/28 17:37
 * @version JDK1.7
 */
public class QrySignAntPushInfo extends Request<QrySignAntPushInfoResponse> {
    private String flowId;

    public QrySignAntPushInfo(String flowId) {
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
        super.setUrl("/v1/querySignAntPushInfo");
        super.setRequestType(RequestType.POST);
    }
}
