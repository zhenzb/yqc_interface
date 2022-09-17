package cn.tign.hz.factory.response.other;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:47
 * @version 
 */
public class PdfSignature {
    private String modify;
    private String timeFrom;
    private String signDate;

    public String getModify() {
        return modify;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}
