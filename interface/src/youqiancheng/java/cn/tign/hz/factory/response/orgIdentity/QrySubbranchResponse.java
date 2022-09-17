package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.QrySubbranchData;

/**
 * 实名认证查询打款银行信息
 * @author  澄泓
 * @date  2020/11/16 16:27
 * @version JDK1.7
 */
public class QrySubbranchResponse extends Response {
    private QrySubbranchData data;

    public QrySubbranchData getData() {
        return data;
    }

    public void setData(QrySubbranchData data) {
        this.data = data;
    }
}
