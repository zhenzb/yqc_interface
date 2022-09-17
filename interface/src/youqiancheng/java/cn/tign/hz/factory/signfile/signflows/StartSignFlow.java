package cn.tign.hz.factory.signfile.signflows;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.StartSignFlowResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API签署流程开启
 * @author  澄泓
 * @date  2020/10/27 17:34
 * @version JDK1.7
 */
public class StartSignFlow extends Request<StartSignFlowResponse> {
    @JSONField(serialize = false)
    private String flowId;

    //禁止构造无参对象
    private StartSignFlow(){}

    public StartSignFlow(String flowId) {
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
        super.setUrl("/v1/signflows/"+flowId+"/start");
        super.setRequestType(RequestType.PUT);
    }
}
