package cn.tign.hz.factory.response.data;
/**
 * 实名认证获取个人实名认证地址data
 * @author  澄泓
 * @date  2020/11/12 10:23
 * @version JDK1.7
 */
public class IndivIdentityUrlData {
    private String flowId;
    private String shortLink;
    private String url;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
