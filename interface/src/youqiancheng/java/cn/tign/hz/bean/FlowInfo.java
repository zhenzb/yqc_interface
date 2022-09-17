package cn.tign.hz.bean;
/**
 * @description  轩辕API流程基本信息
 * @author  澄泓
 * @date  2020/10/27 14:07
 * @version JDK1.7
 */
public class FlowInfo {
    private boolean autoArchive;
    private boolean autoInitiate;
    private String businessScene;
    private Integer contractRemind;
    private Integer contractValidity;
    private FlowConfigInfo flowConfigInfo;
    private String initiatorAccountId;
    private String initiatorAuthorizedAccountId;
    private String remark;
    private String signValidity;

    public boolean isAutoArchive() {
        return autoArchive;
    }

    public FlowInfo setAutoArchive(boolean autoArchive) {
        this.autoArchive = autoArchive;
        return this;
    }

    public boolean isAutoInitiate() {
        return autoInitiate;
    }

    public FlowInfo setAutoInitiate(boolean autoInitiate) {
        this.autoInitiate = autoInitiate;
        return this;
    }

    public String getBusinessScene() {
        return businessScene;
    }

    public FlowInfo setBusinessScene(String businessScene) {
        this.businessScene = businessScene;
        return this;
    }

    public Integer getContractRemind() {
        return contractRemind;
    }

    public FlowInfo setContractRemind(Integer contractRemind) {
        this.contractRemind = contractRemind;
        return this;
    }

    public Integer getContractValidity() {
        return contractValidity;
    }

    public FlowInfo setContractValidity(Integer contractValidity) {
        this.contractValidity = contractValidity;
        return this;
    }

    public FlowConfigInfo getFlowConfigInfo() {
        return flowConfigInfo;
    }

    public FlowInfo setFlowConfigInfo(FlowConfigInfo flowConfigInfo) {
        this.flowConfigInfo = flowConfigInfo;
        return this;
    }

    public String getInitiatorAccountId() {
        return initiatorAccountId;
    }

    public FlowInfo setInitiatorAccountId(String initiatorAccountId) {
        this.initiatorAccountId = initiatorAccountId;
        return this;
    }

    public String getInitiatorAuthorizedAccountId() {
        return initiatorAuthorizedAccountId;
    }

    public FlowInfo setInitiatorAuthorizedAccountId(String initiatorAuthorizedAccountId) {
        this.initiatorAuthorizedAccountId = initiatorAuthorizedAccountId;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FlowInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getSignValidity() {
        return signValidity;
    }

    public FlowInfo setSignValidity(String signValidity) {
        this.signValidity = signValidity;
        return this;
    }
}
