package cn.tign.hz.factory.signfile.signflows;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.ArchiveSignFlowResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API签署流程归档
 * @author  澄泓
 * @date  2020/10/28 10:51
 * @version JDK1.7
 */
public class ArchiveSignFlow extends Request<ArchiveSignFlowResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private ArchiveSignFlow(){};
    public ArchiveSignFlow(String flowId) {
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
        super.setUrl("/v1/signflows/"+flowId+"/archive");
        super.setRequestType(RequestType.PUT);
    }
}
