package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSONObject;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 16:08
 * @version 
 */
public class AntPush {
    private String docHash;
    private String antTransactionId;
    private String antTxHash;
    private String pushTime;

    public String getDocHash() {
        return docHash;
    }

    public void setDocHash(String docHash) {
        this.docHash = docHash;
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

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
