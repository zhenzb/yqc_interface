package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.QryOrganizationsData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:17
 * @version 
 */
public class QryOrganizationsByThirdIdResponse extends Response {
    private QryOrganizationsData data;

    public QryOrganizationsData getData() {
        return data;
    }

    public void setData(QryOrganizationsData data) {
        this.data = data;
    }
}
