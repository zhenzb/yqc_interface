package cn.tign.hz.bean;
/**
 * 实名认证获取个人实名认证地址-contextInfo
 * @author  澄泓
 * @date  2020/11/12 10:15
 * @version JDK1.7
 */
public class ContextInfo {
    private String contextId;
    private String notifyUrl;
    private String origin;
    private String redirectUrl;
    private boolean showResultPage;

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean getShowResultPage() {
        return showResultPage;
    }

    public void setShowResultPage(boolean showResultPage) {
        this.showResultPage = showResultPage;
    }
}
