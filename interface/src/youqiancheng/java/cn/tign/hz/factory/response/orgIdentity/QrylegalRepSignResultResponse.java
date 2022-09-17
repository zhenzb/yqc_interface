package cn.tign.hz.factory.response.orgIdentity;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.QrylegalRepSignResultData;

/**
 * 实名认证查询授权书签署状态响应
 * @author  澄泓
 * @date  2020/11/16 15:18
 * @version JDK1.7
 */
public class QrylegalRepSignResultResponse extends Response {
    private QrylegalRepSignResultData data;

    public QrylegalRepSignResultData getData() {
        return data;
    }

    public void setData(QrylegalRepSignResultData data) {
        this.data = data;
    }
}
