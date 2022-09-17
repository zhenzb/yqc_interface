package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.QrySignAntPushInfoData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:57
 * @version 
 */
public class QrySignAntPushInfoResponse extends Response {
    private QrySignAntPushInfoData data;

    public QrySignAntPushInfoData getData() {
        return data;
    }

    public void setData(QrySignAntPushInfoData data) {
        this.data = data;
    }
}
