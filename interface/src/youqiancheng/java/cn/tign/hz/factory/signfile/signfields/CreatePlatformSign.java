package cn.tign.hz.factory.signfile.signfields;

import cn.tign.hz.bean.Signfields;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.CreatePlatformSignResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API添加平台自动盖章签署区
 * @author  澄泓
 * @date  2020/10/28 15:33
 * @version JDK1.7
 */
public class CreatePlatformSign extends Request<CreatePlatformSignResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private Signfields signfields;

    private CreatePlatformSign(){};
    public CreatePlatformSign(String flowId, Signfields signfields) {
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
        super.setUrl("/v1/signflows/"+flowId+"/signfields/platformSign");
        super.setRequestType(RequestType.POST);
    }
}
