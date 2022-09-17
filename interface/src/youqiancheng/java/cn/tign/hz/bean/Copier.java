package cn.tign.hz.bean;
/**
 * @description  轩辕API抄送人人列表
 * @author  澄泓
 * @date  2020/10/27 11:48
 * @version JDK1.7
 */
public class Copier {
    private String copierAccountId;
    private Integer copierIdentityAccountType;
    private String copierIdentityAccountId;

    public String getCopierAccountId() {
        return copierAccountId;
    }

    public Copier setCopierAccountId(String copierAccountId) {
        this.copierAccountId = copierAccountId;
        return this;
    }

    public Integer getCopierIdentityAccountType() {
        return copierIdentityAccountType;
    }

    public Copier setCopierIdentityAccountType(Integer copierIdentityAccountType) {
        this.copierIdentityAccountType = copierIdentityAccountType;
        return this;
    }

    public String getCopierIdentityAccountId() {
        return copierIdentityAccountId;
    }

    public Copier setCopierIdentityAccountId(String copierIdentityAccountId) {
        this.copierIdentityAccountId = copierIdentityAccountId;
        return this;
    }
}
