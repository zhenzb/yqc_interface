package cn.tign.hz.factory.response.data;
/**
 * 实名认证发起个人刷脸认证data
 * @author  澄泓
 * @date  2020/11/12 10:40
 * @version JDK1.7
 */
public class FaceIdentityData {
    private String flowId;
    private String authUrl;
    private String originalUrl;
    private long expire;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
