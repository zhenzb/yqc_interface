package cn.tign.hz.factory.response.indivIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.QryFaceStatusData;

/**
 * 实名认证查询个人刷脸状态响应
 * @author  澄泓
 * @date  2020/11/12 15:11
 * @version JDK1.7
 */
public class QryFaceStatusResponse extends Response {
    private QryFaceStatusData data;

    public QryFaceStatusData getData() {
        return data;
    }

    public void setData(QryFaceStatusData data) {
        this.data = data;
    }
}
