package cn.tign.hz.factory.signfile.signers;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.RushSignResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API流程签署人催签
 * @author  澄泓
 * @date  2020/10/28 16:41
 * @version JDK1.7
 */
public class RushSign extends Request<RushSignResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String accountId;
    private String noticeTypes;
    private String rushsignAccountId;

    private RushSign(){};
    public RushSign(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNoticeTypes() {
        return noticeTypes;
    }

    public void setNoticeTypes(String noticeTypes) {
        this.noticeTypes = noticeTypes;
    }

    public String getRushsignAccountId() {
        return rushsignAccountId;
    }

    public void setRushsignAccountId(String rushsignAccountId) {
        this.rushsignAccountId = rushsignAccountId;
    }

    @Override
    public void build() {
        super.setUrl("/v1/signflows/"+flowId+"/signers/rushsign");
        super.setRequestType(RequestType.PUT);
    }
}
