package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.CreateOrganizationsByThirdPartyUserIdData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 14:10
 * @version 
 */
public class CreateOrganizationsByThirdPartyUserIdResponse extends Response {
    private CreateOrganizationsByThirdPartyUserIdData data;

    public CreateOrganizationsByThirdPartyUserIdData getData() {
        return data;
    }

    public void setData(CreateOrganizationsByThirdPartyUserIdData data) {
        this.data = data;
    }
}
