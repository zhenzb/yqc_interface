package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.GetAuthSignUrlResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证获取授权签署链接
 * @author  澄泓
 * @date  2020/11/16 15:23
 * @version JDK1.7
 */
public class GetAuthSignUrl extends Request<GetAuthSignUrlResponse> {
    @JSONField(serialize = false)
    private String flowId;

    private GetAuthSignUrl(){};
    public GetAuthSignUrl(String flowId) {
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
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/signUrl");
        super.setRequestType(RequestType.GET);
    }
}
