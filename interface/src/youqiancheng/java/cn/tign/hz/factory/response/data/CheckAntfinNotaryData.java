package cn.tign.hz.factory.response.data;

import java.util.Date;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:54
 * @version 
 */
public class CheckAntfinNotaryData {
    private String docHash;
    private long blockHeight;
    private long errorCode;
    private String errorMessage;
    private Date notaryTime;
    private String notaryType;
    private String phase;
    private boolean result;
    private String antTransactionId;
    private String antTxHash;

    public String getDocHash() {
        return docHash;
    }

    public void setDocHash(String docHash) {
        this.docHash = docHash;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getNotaryTime() {
        return notaryTime;
    }

    public void setNotaryTime(Date notaryTime) {
        this.notaryTime = notaryTime;
    }

    public String getNotaryType() {
        return notaryType;
    }

    public void setNotaryType(String notaryType) {
        this.notaryType = notaryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getAntTransactionId() {
        return antTransactionId;
    }

    public void setAntTransactionId(String antTransactionId) {
        this.antTransactionId = antTransactionId;
    }

    public String getAntTxHash() {
        return antTxHash;
    }

    public void setAntTxHash(String antTxHash) {
        this.antTxHash = antTxHash;
    }
}
