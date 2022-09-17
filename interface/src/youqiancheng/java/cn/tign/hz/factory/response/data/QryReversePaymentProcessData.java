package cn.tign.hz.factory.response.data;
/**
 * 实名认证查询反向打款进度data
 * @author  澄泓
 * @date  2020/11/16 16:12
 * @version JDK1.7
 */
public class QryReversePaymentProcessData {
    private String process;
    private String message;
    private String subAccount;
    private String subAccountChannelName;
    private String mainAccountName;
    private String oppAccountNo;
    private String oppAccountName;
    private String oppBankName;
    private String payAmount;
    private String tradeTime;
    private String createTime;
    private String modifyTime;
    private String bizFlowNo;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getSubAccountChannelName() {
        return subAccountChannelName;
    }

    public void setSubAccountChannelName(String subAccountChannelName) {
        this.subAccountChannelName = subAccountChannelName;
    }

    public String getMainAccountName() {
        return mainAccountName;
    }

    public void setMainAccountName(String mainAccountName) {
        this.mainAccountName = mainAccountName;
    }

    public String getOppAccountNo() {
        return oppAccountNo;
    }

    public void setOppAccountNo(String oppAccountNo) {
        this.oppAccountNo = oppAccountNo;
    }

    public String getOppAccountName() {
        return oppAccountName;
    }

    public void setOppAccountName(String oppAccountName) {
        this.oppAccountName = oppAccountName;
    }

    public String getOppBankName() {
        return oppBankName;
    }

    public void setOppBankName(String oppBankName) {
        this.oppBankName = oppBankName;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBizFlowNo() {
        return bizFlowNo;
    }

    public void setBizFlowNo(String bizFlowNo) {
        this.bizFlowNo = bizFlowNo;
    }
}
