package cn.tign.hz.factory.response.data;
/**
 * 实名认证发起企业反向打款认证
 * @author  澄泓
 * @date  2020/11/16 16:07
 * @version JDK1.7
 */
public class ReversePaymentData {
    private String subAccountNo;
    private String mainAccountName;
    private String mainAccountBank;
    private String paymentAmount;

    public String getSubAccountNo() {
        return subAccountNo;
    }

    public void setSubAccountNo(String subAccountNo) {
        this.subAccountNo = subAccountNo;
    }

    public String getMainAccountName() {
        return mainAccountName;
    }

    public void setMainAccountName(String mainAccountName) {
        this.mainAccountName = mainAccountName;
    }

    public String getMainAccountBank() {
        return mainAccountBank;
    }

    public void setMainAccountBank(String mainAccountBank) {
        this.mainAccountBank = mainAccountBank;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
