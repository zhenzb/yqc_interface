package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.UpdateOrganizationsData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:50
 * @version 
 */
public class UpdateOrganizationsByThirdIdResponse extends Response {
    private UpdateOrganizationsData data;

    public UpdateOrganizationsData getData() {
        return data;
    }

    public void setData(UpdateOrganizationsData data) {
        this.data = data;
    }
}
