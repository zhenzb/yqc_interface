package cn.tign.hz.factory.orgIdentity;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.orgIdentity.TransferRandomAmountResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实名认证发起随机金额打款认证
 * @author  澄泓
 * @date  2020/11/16 15:43
 * @version JDK1.7
 */
public class TransferRandomAmount extends Request<TransferRandomAmountResponse> {
    @JSONField(serialize = false)
    private String flowId;
    private String bank;
    private String province;
    private String city;
    private String subbranch;
    private String cardNo;
    private String cnapsCode;
    private String contextId;
    private String notifyUrl;
    private String mobile;

    private TransferRandomAmount(){};
    public TransferRandomAmount(String flowId, String bank, String province, String city, String subbranch, String cardNo, String cnapsCode) {
        this.flowId = flowId;
        this.bank = bank;
        this.province = province;
        this.city = city;
        this.subbranch = subbranch;
        this.cardNo = cardNo;
        this.cnapsCode = cnapsCode;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCnapsCode() {
        return cnapsCode;
    }

    public void setCnapsCode(String cnapsCode) {
        this.cnapsCode = cnapsCode;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/pub/organization/"+flowId+"/transferRandomAmount");
        super.setRequestType(RequestType.PUT);
    }
}
