package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.IndividualBaseResponse;

/**
 * 实名认证个人2要素信息比对
 * @author  澄泓
 * @date  2020/11/2 16:46
 * @version JDK1.7
 */
public class IndividualBase extends Request<IndividualBaseResponse> {
    private String idNo;
    private String name;

    private IndividualBase(){};
    public IndividualBase(String idNo, String name) {
        this.idNo = idNo;
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/individual/base");
        super.setRequestType(RequestType.POST);
    }
}
