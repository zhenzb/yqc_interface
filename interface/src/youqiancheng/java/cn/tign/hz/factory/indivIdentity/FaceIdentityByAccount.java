package cn.tign.hz.factory.indivIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.indivIdentity.FaceIdentityByAccountResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起个人刷脸实名认证-API
 * @author  澄泓
 * @date  2020/11/12 10:34
 * @version JDK1.7
 */
public class FaceIdentityByAccount extends Request<FaceIdentityByAccountResponse> {
    @JSONField(serialize = false)
    private String accountId;
    private String faceauthMode;
    private boolean repetition=true;
    private String callbackUrl;
    private String contextId;
    private String notifyUrl;

    private FaceIdentityByAccount(){};
    public FaceIdentityByAccount(String accountId, String faceauthMode, String callbackUrl,String notifyUrl,String contextId) {
        this.accountId = accountId;
        this.faceauthMode = faceauthMode;
        this.callbackUrl = callbackUrl;
        this.notifyUrl = notifyUrl;
        this.contextId = contextId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFaceauthMode() {
        return faceauthMode;
    }

    public void setFaceauthMode(String faceauthMode) {
        this.faceauthMode = faceauthMode;
    }

    public boolean getRepetition() {
        return repetition;
    }

    public void setRepetition(boolean repetition) {
        this.repetition = repetition;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

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

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/individual/"+accountId+"/face");
        super.setRequestType(RequestType.POST);
    }
}
