package cn.tign.hz.factory.signfile.signfields;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.QrySignFieldsResponse;

/**
 * @description  轩辕API查询签署区列表
 * @author  澄泓
 * @date  2020/10/28 15:15
 * @version JDK1.7
 */
public class QrySignFields extends Request<QrySignFieldsResponse> {
    private String flowId;
    private String accountId;
    private String signfieldIds;

    private QrySignFields(){};
    public QrySignFields(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSignfieldIds() {
        return signfieldIds;
    }

    public void setSignfieldIds(String signfieldIds) {
        this.signfieldIds = signfieldIds;
    }

    @Override
    public void build() {
        StringBuilder url=new StringBuilder("/v1/signflows/"+flowId+"/signfields?");
        if(accountId!=null){
            url.append("&accountId="+accountId);
        }
        if(signfieldIds!=null){
            url.append("&signfieldIds="+signfieldIds);
        }
        super.setUrl(url.toString());
        super.setRequestType(RequestType.GET);
    }
}
