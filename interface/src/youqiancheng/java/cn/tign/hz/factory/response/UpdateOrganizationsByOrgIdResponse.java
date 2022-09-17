package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.UpdateOrganizationsData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 12:00
 * @version 
 */
public class UpdateOrganizationsByOrgIdResponse extends Response{
    private UpdateOrganizationsData data;

    public UpdateOrganizationsData getData() {
        return data;
    }

    public void setData(UpdateOrganizationsData data) {
        this.data = data;
    }
}
