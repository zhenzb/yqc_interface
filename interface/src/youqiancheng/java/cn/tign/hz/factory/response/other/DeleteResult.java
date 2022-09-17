package cn.tign.hz.factory.response.other;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:08
 * @version 
 */
public class DeleteResult {
    private String failedReason;
    private int optResult;
    private String signfieldId;

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public int getOptResult() {
        return optResult;
    }

    public void setOptResult(int optResult) {
        this.optResult = optResult;
    }

    public String getSignfieldId() {
        return signfieldId;
    }

    public void setSignfieldId(String signfieldId) {
        this.signfieldId = signfieldId;
    }
}
