package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.QryPersonData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:41
 * @version 
 */
public class QryPersonByaccountIdResponse extends Response {
    private QryPersonData data;

    public QryPersonData getData() {
        return data;
    }

    public void setData(QryPersonData data) {
        this.data = data;
    }
}
