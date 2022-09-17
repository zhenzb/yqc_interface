package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.QrySubbranchResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证查询打款银行信息
 * @author  澄泓
 * @date  2020/11/16 16:19
 * @version JDK1.7
 */
public class QrySubbranch extends Request<QrySubbranchResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String keyWord;

    private QrySubbranch(){};
    public QrySubbranch(String flowId, String keyWord) {
        this.flowId = flowId;
        this.keyWord = keyWord;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/subbranch");
        super.setRequestType(RequestType.GET);
    }
}
