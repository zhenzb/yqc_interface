package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.QrySignersData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:55
 * @version 
 */
public class QrySignersResponse {
    private QrySignersData data;

    public QrySignersData getData() {
        return data;
    }

    public void setData(QrySignersData data) {
        this.data = data;
    }
}
