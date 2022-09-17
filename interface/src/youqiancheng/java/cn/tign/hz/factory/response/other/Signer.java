package cn.tign.hz.factory.response.other;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:58
 * @version 
 */
public class Signer {
    private int signOrder;
    private int signStatus;
    private String signerAccountId;
    private String signerName;
    private boolean signerRealName;
    private String signerAuthorizedAccountId;
    private String signerAuthorizedAccountName;
    private boolean signerAuthorizedAccountRealName;
    private int signerAuthorizedAccountType;
    private boolean thirdOrderNo;

    public int getSignOrder() {
        return signOrder;
    }

    public void setSignOrder(int signOrder) {
        this.signOrder = signOrder;
    }

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignerAccountId() {
        return signerAccountId;
    }

    public void setSignerAccountId(String signerAccountId) {
        this.signerAccountId = signerAccountId;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public boolean isSignerRealName() {
        return signerRealName;
    }

    public void setSignerRealName(boolean signerRealName) {
        this.signerRealName = signerRealName;
    }

    public String getSignerAuthorizedAccountId() {
        return signerAuthorizedAccountId;
    }

    public void setSignerAuthorizedAccountId(String signerAuthorizedAccountId) {
        this.signerAuthorizedAccountId = signerAuthorizedAccountId;
    }

    public String getSignerAuthorizedAccountName() {
        return signerAuthorizedAccountName;
    }

    public void setSignerAuthorizedAccountName(String signerAuthorizedAccountName) {
        this.signerAuthorizedAccountName = signerAuthorizedAccountName;
    }

    public boolean isSignerAuthorizedAccountRealName() {
        return signerAuthorizedAccountRealName;
    }

    public void setSignerAuthorizedAccountRealName(boolean signerAuthorizedAccountRealName) {
        this.signerAuthorizedAccountRealName = signerAuthorizedAccountRealName;
    }

    public int getSignerAuthorizedAccountType() {
        return signerAuthorizedAccountType;
    }

    public void setSignerAuthorizedAccountType(int signerAuthorizedAccountType) {
        this.signerAuthorizedAccountType = signerAuthorizedAccountType;
    }

    public boolean isThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(boolean thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }
}
