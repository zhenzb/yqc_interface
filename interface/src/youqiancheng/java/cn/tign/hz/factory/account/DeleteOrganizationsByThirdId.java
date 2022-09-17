package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DeleteOrganizationsByThirdIdResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API注销机构账号（按照第三方机构ID注销）
 * @author  澄泓
 * @date  2020/10/23 20:19
 * @version JDK1.7
 */
public class DeleteOrganizationsByThirdId extends Request<DeleteOrganizationsByThirdIdResponse> {
    @JSONField(serialize = false)
    private String thirdPartyUserId;

    //禁止构造无参对象
    private DeleteOrganizationsByThirdId() {}

    public DeleteOrganizationsByThirdId(String thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }

    public String getThirdPartyUserId() {
        return thirdPartyUserId;
    }

    public void setThirdPartyUserId(String thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }

    @Override
    public void build() {
        super.setUrl("/v1/organizations/deleteByThirdId?thirdPartyUserId="+thirdPartyUserId);
        super.setRequestType(RequestType.DELETE);
    }
}
