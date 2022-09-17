package cn.tign.hz.bean;
/**
 * 实名认证获取组织机构实名认证地址-组织机构基本信息orgEntity
 * @author  澄泓
 * @date  2020/11/16 11:01
 * @version JDK1.7
 */
public class OrgEntity {
    private String name;
    private String certNo;
    private String legalRepCertNo;
    private String legalRepName;
    private String agentName;
    private String agentIdNo;
    private String organizationType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getLegalRepCertNo() {
        return legalRepCertNo;
    }

    public void setLegalRepCertNo(String legalRepCertNo) {
        this.legalRepCertNo = legalRepCertNo;
    }

    public String getLegalRepName() {
        return legalRepName;
    }

    public void setLegalRepName(String legalRepName) {
        this.legalRepName = legalRepName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentIdNo() {
        return agentIdNo;
    }

    public void setAgentIdNo(String agentIdNo) {
        this.agentIdNo = agentIdNo;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }
}
