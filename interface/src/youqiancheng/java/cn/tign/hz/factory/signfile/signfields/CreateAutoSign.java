package cn.tign.hz.factory.signfile.signfields;

import cn.tign.hz.bean.Signfields;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.CreateAutoSignResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API添加签署方自动盖章签署区
 * @author  澄泓
 * @date  2020/10/28 15:45
 * @version JDK1.7
 */
public class CreateAutoSign extends Request<CreateAutoSignResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private Signfields signfields;

    private CreateAutoSign(){};
    public CreateAutoSign(String flowId, Signfields signfields) {
        this.flowId = flowId;
        this.signfields = signfields;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Signfields getSignfields() {
        return signfields;
    }

    public void setSignfields(Signfields signfields) {
        this.signfields = signfields;
    }

    @Override
    public void build() {
        super.setUrl("/v1/signflows/"+flowId+"/signfields/autoSign");
        super.setRequestType(RequestType.POST);
    }
}
