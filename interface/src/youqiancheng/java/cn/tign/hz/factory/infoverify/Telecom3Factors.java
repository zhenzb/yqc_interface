package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.Telecom3FactorsResponse;

/**
 * 实名认证个人运营商3要素信息比对
 * @author  澄泓
 * @date  2020/11/2 16:50
 * @version JDK1.7
 */
public class Telecom3Factors extends Request<Telecom3FactorsResponse> {
    private String idNo;
    private String name;
    private String mobileNo;

    private Telecom3Factors(){};
    public Telecom3Factors(String idNo, String name, String mobileNo) {
        this.idNo = idNo;
        this.name = name;
        this.mobileNo = mobileNo;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/organization/enterprise/base");
        super.setRequestType(RequestType.POST);
    }
}
