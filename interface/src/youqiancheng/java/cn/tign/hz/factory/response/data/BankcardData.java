package cn.tign.hz.factory.response.data;
/**
 * 实名认证银行卡OCRData
 * @author  澄泓
 * @date  2020/11/3 16:20
 * @version JDK1.7
 */
public class BankcardData {
    private String verifyId;
    private String bankCardNo;
    private String bankName;
    private String bankCardType;

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }
}
