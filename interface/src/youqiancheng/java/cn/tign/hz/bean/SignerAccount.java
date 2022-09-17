package cn.tign.hz.bean;
/**
 * @description  轩辕API签署方账号信息（平台方自动签署时，无需传入该参数）
 * @author  澄泓
 * @date  2020/10/27 14:13
 * @version JDK1.7
 */
public class SignerAccount {
    private String signerAccountId;
    private String authorizedAccountId;

    public String getSignerAccountId() {
        return signerAccountId;
    }

    public String getAuthorizedAccountId() {
        return authorizedAccountId;
    }

    public SignerAccount setAuthorizedAccountId(String authorizedAccountId) {
        this.authorizedAccountId = authorizedAccountId;
        return this;
    }

    public SignerAccount setSignerAccountId(String signerAccountId) {
        this.signerAccountId = signerAccountId;
        return this;
    }
}
