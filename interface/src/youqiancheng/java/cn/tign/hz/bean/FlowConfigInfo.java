package cn.tign.hz.bean;
/**
 * @description  轩辕API任务配置信息
 * @author  澄泓
 * @date  2020/10/27 14:08
 * @version JDK1.7
 */
public class FlowConfigInfo {
    private String noticeDeveloperUrl;
    private String noticeType;
    private String redirectUrl;
    private String signPlatform;
    private Integer redirectDelayTime;

    public String getNoticeDeveloperUrl() {
        return noticeDeveloperUrl;
    }

    public FlowConfigInfo setNoticeDeveloperUrl(String noticeDeveloperUrl) {
        this.noticeDeveloperUrl = noticeDeveloperUrl;
        return this;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public FlowConfigInfo setNoticeType(String noticeType) {
        this.noticeType = noticeType;
        return this;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public FlowConfigInfo setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public String getSignPlatform() {
        return signPlatform;
    }

    public FlowConfigInfo setSignPlatform(String signPlatform) {
        this.signPlatform = signPlatform;
        return this;
    }

    public Integer getRedirectDelayTime() {
        return redirectDelayTime;
    }

    public FlowConfigInfo setRedirectDelayTime(Integer redirectDelayTime) {
        this.redirectDelayTime = redirectDelayTime;
        return this;
    }
}
