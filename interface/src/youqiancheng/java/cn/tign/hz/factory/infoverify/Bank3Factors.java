package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.Bank3FactorsResponse;

/**
 * 实名认证个人银行卡3要素信息比对
 * @author  澄泓
 * @date  2020/11/2 16:55
 * @version JDK1.7
 */
public class Bank3Factors extends Request<Bank3FactorsResponse> {
    private String idNo;
    private String name;
    private String cardNo;

    private Bank3Factors(){};
    public Bank3Factors(String idNo, String name, String cardNo) {
        this.idNo = idNo;
        this.name = name;
        this.cardNo = cardNo;
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

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/individual/bank3Factors");
        super.setRequestType(RequestType.POST);
    }
}
