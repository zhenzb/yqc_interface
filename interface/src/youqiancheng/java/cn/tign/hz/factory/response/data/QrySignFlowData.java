package cn.tign.hz.factory.response.data;

import cn.tign.hz.factory.response.other.ConfigInfo;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:24
 * @version 
 */
public class QrySignFlowData {
    private String appId;
    private boolean autoArchive;
    private String businessScene;
    private ConfigInfo configInfo;
    private int contractValidity;
    private int contractRemind;
    private String flowId;
    private String flowStartTime;
    private String flowEndTime;
    private int flowStatus;
    private String flowDesc;
    private String initiatorAccountId;
    private String initiatorAuthorizedAccountId;
    private String signValidity;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isAutoArchive() {
        return autoArchive;
    }

    public void setAutoArchive(boolean autoArchive) {
        this.autoArchive = autoArchive;
    }

    public String getBusinessScene() {
        return businessScene;
    }

    public void setBusinessScene(String businessScene) {
        this.businessScene = businessScene;
    }

    public ConfigInfo getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    public int getContractValidity() {
        return contractValidity;
    }

    public void setContractValidity(int contractValidity) {
        this.contractValidity = contractValidity;
    }

    public int getContractRemind() {
        return contractRemind;
    }

    public void setContractRemind(int contractRemind) {
        this.contractRemind = contractRemind;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowStartTime() {
        return flowStartTime;
    }

    public void setFlowStartTime(String flowStartTime) {
        this.flowStartTime = flowStartTime;
    }

    public String getFlowEndTime() {
        return flowEndTime;
    }

    public void setFlowEndTime(String flowEndTime) {
        this.flowEndTime = flowEndTime;
    }

    public int getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowDesc() {
        return flowDesc;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }

    public String getInitiatorAccountId() {
        return initiatorAccountId;
    }

    public void setInitiatorAccountId(String initiatorAccountId) {
        this.initiatorAccountId = initiatorAccountId;
    }

    public String getInitiatorAuthorizedAccountId() {
        return initiatorAuthorizedAccountId;
    }

    public void setInitiatorAuthorizedAccountId(String initiatorAuthorizedAccountId) {
        this.initiatorAuthorizedAccountId = initiatorAuthorizedAccountId;
    }

    public String getSignValidity() {
        return signValidity;
    }

    public void setSignValidity(String signValidity) {
        this.signValidity = signValidity;
    }
}
