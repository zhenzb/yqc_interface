package cn.tign.hz.factory.response.other;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:22
 * @version 
 */
public class Cert {
    private String owner;
    private String serial;
    private String startDate;
    private String endDate;
    private String issuerCN;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIssuerCN() {
        return issuerCN;
    }

    public void setIssuerCN(String issuerCN) {
        this.issuerCN = issuerCN;
    }
}
