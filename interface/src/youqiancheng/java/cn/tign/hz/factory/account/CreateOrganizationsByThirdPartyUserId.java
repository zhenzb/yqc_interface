package cn.tign.hz.factory.account;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.CreateOrganizationsByThirdPartyUserIdResponse;

/**
 * @description  轩辕API机构账号创建
 * @author  澄泓
 * @date  2020/10/23 18:26
 * @version JDK1.7
 */
public class CreateOrganizationsByThirdPartyUserId extends Request<CreateOrganizationsByThirdPartyUserIdResponse> {
    private String thirdPartyUserId;
    private String creator;
    private String name;
    private String idType;
    private String idNumber;
    private String orgLegalIdNumber;
    private String orgLegalName;

    //禁止构造无参对象
    private CreateOrganizationsByThirdPartyUserId() {}

    public CreateOrganizationsByThirdPartyUserId(String thirdPartyUserId, String creator, String name, String idType, String idNumber) {
        this.thirdPartyUserId = thirdPartyUserId;
        this.creator = creator;
        this.name = name;
        this.idType = idType;
        this.idNumber = idNumber;
    }

    public String getThirdPartyUserId() {
        return thirdPartyUserId;
    }

    public void setThirdPartyUserId(String thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getOrgLegalIdNumber() {
        return orgLegalIdNumber;
    }

    public void setOrgLegalIdNumber(String orgLegalIdNumber) {
        this.orgLegalIdNumber = orgLegalIdNumber;
    }

    public String getOrgLegalName() {
        return orgLegalName;
    }

    public void setOrgLegalName(String orgLegalName) {
        this.orgLegalName = orgLegalName;
    }

    @Override
    public void build() {
        super.setUrl("/v1/organizations/createByThirdPartyUserId");
        super.setRequestType(RequestType.POST);
    }
}
