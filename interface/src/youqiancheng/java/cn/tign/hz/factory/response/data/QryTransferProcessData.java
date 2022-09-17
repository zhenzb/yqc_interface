package cn.tign.hz.factory.response.data;
/**
 * 实名认证查询随机金额打款进度data
 * @author  澄泓
 * @date  2020/11/16 16:03
 * @version JDK1.7
 */
public class QryTransferProcessData {
    private String process;
    private String message;
    private String foresee;
    private String cardNo;
    private String subbranch;
    private String remain;
    private String remaintimes;
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

    public String getForesee() {
        return foresee;
    }

    public void setForesee(String foresee) {
        this.foresee = foresee;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getRemaintimes() {
        return remaintimes;
    }

    public void setRemaintimes(String remaintimes) {
        this.remaintimes = remaintimes;
    }

    public String getBizFlowNo() {
        return bizFlowNo;
    }

    public void setBizFlowNo(String bizFlowNo) {
        this.bizFlowNo = bizFlowNo;
    }
}
