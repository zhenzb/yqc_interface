package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.QrySealData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 18:31
 * @version 
 */
public class QryPersonSealsResponse extends Response {
    private QrySealData data;

    public QrySealData getData() {
        return data;
    }

    public void setData(QrySealData data) {
        this.data = data;
    }
}
