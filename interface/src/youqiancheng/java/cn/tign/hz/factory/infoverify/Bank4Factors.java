package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.Bank4FactorsResponse;

/**
 * 实名认证个人银行卡4要素信息比对
 * @author  澄泓
 * @date  2020/11/2 16:58
 * @version JDK1.7
 */
public class Bank4Factors extends Request<Bank4FactorsResponse> {
    private String idNo;
    private String name;
    private String cardNo;
    private String mobileNo;

    private Bank4Factors(){};
    public Bank4Factors(String idNo, String name, String cardNo, String mobileNo) {
        this.idNo = idNo;
        this.name = name;
        this.cardNo = cardNo;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/individual/bank4Factors");
        super.setRequestType(RequestType.POST);
    }
}
