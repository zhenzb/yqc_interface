package cn.tign.hz.factory.signfile.signflows;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.QrySignFlowResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API签署流程查询
 * @author  澄泓
 * @date  2020/10/28 10:10
 * @version JDK1.7
 */
public class QrySignFlow extends Request<QrySignFlowResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private QrySignFlow(){};

    public QrySignFlow(String flowId) {
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
        super.setUrl("/v1/signflows/"+flowId);
        super.setRequestType(RequestType.GET);
    }
}
