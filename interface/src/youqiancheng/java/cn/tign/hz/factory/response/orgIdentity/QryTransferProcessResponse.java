package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.QryTransferProcessData;

/**
 * 实名认证查询随机金额打款进度响应
 * @author  澄泓
 * @date  2020/11/16 16:02
 * @version JDK1.7
 */
public class QryTransferProcessResponse extends Response {
    private QryTransferProcessData data;

    public QryTransferProcessData getData() {
        return data;
    }

    public void setData(QryTransferProcessData data) {
        this.data = data;
    }
}
