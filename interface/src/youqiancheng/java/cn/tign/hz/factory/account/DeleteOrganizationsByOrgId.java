package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DeleteOrganizationsByOrgIdResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API注销机构账号（按照账号ID注销）
 * @author  澄泓
 * @date  2020/10/23 20:09
 * @version JDK1.7
 */
public class DeleteOrganizationsByOrgId extends Request<DeleteOrganizationsByOrgIdResponse> {
    @JSONField(serialize = false)
    private String orgId;

    //禁止构造无参对象
    private DeleteOrganizationsByOrgId() {}

    public DeleteOrganizationsByOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public void build() {
        super.setUrl("/v1/organizations/"+orgId);
        super.setRequestType(RequestType.DELETE);
    }
}
