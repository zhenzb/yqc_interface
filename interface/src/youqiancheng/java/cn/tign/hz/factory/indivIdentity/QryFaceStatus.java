package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.QryFaceStatusResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证查询个人刷脸状态
 * @author  澄泓
 * @date  2020/11/12 15:08
 * @version JDK1.7
 */
public class QryFaceStatus extends Request<QryFaceStatusResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private QryFaceStatus(){};
    public QryFaceStatus(String flowId) {
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
        super.setUrl("/v2/identity/auth/pub/individual/"+flowId+"/face");
        super.setRequestType(RequestType.GET);
    }
}
