package cn.tign.hz.bean;
/**
 * 实名认证获取个人实名认证地址-indivInfo
 * @author  澄泓
 * @date  2020/11/12 10:17
 * @version JDK1.7
 */
public class IndivInfo {
    private String bankCardNo;
    private String certNo;
    private String certType;
    private String mobileNo;
    private String name;
    private String nationality;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
