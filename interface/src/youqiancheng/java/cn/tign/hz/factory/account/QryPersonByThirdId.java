package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.QryPersonByThirdIdResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API查询个人账户（按照第三方用户ID查询）
 * @author  澄泓
 * @date  2020/10/23 17:21
 * @version JDK1.7
 */
public class QryPersonByThirdId extends Request<QryPersonByThirdIdResponse> {
    //非body入参不参与签名，不做序列化
    @JSONField(serialize = false)
    private String thirdPartyUserId;

    //禁止构造无参对象
    private QryPersonByThirdId() {}

    public QryPersonByThirdId(String thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }

    public String getThirdPartyUserId() {
        return thirdPartyUserId;
    }

    public void setThirdPartyUserId(String thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }

    @Override
    public void build() {
        super.setUrl("/v1/accounts/getByThirdId?thirdPartyUserId="+thirdPartyUserId);
        super.setRequestType(RequestType.GET);
    }
}
