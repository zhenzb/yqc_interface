package cn.tign.hz.factory.infoverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.infoverify.EntBaseResponse;

/**
 * 实名认证企业2要素信息比对
 * @author  澄泓
 * @date  2020/11/2 17:02
 * @version JDK1.7
 */
public class EntBase extends Request<EntBaseResponse> {
    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业证件号,支持15位工商注册号或统一社会信用代码
     */
    private String orgCode;

    private EntBase(){};
    public EntBase(String name, String orgCode) {
        this.name = name;
        this.orgCode = orgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/verify/organization/enterprise/base");
        super.setRequestType(RequestType.POST);
    }
}
