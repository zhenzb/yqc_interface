package cn.tign.hz.bean;
/**
 * @description  轩辕API签署方信息
 * @author  澄泓
 * @date  2020/10/27 15:10
 * @version JDK1.7
 */
public class Signer {
    private boolean platformSign;
    private Integer signOrder;
    private SignerAccount signerAccount;
    private Signfields signfields;
    private String thirdOrderNo;

    public boolean isPlatformSign() {
        return platformSign;
    }

    public Signer setPlatformSign(boolean platformSign) {
        this.platformSign = platformSign;
        return this;
    }

    public Integer getSignOrder() {
        return signOrder;
    }

    public Signer setSignOrder(Integer signOrder) {
        this.signOrder = signOrder;
        return this;
    }

    public SignerAccount getSignerAccount() {
        return signerAccount;
    }

    public Signer setSignerAccount(SignerAccount signerAccount) {
        this.signerAccount = signerAccount;
        return this;
    }

    public Signfields getSignfields() {
        return signfields;
    }

    public Signer setSignFields(Signfields signfields) {
        this.signfields = signfields;
        return this;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public Signer setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
        return this;
    }
}
