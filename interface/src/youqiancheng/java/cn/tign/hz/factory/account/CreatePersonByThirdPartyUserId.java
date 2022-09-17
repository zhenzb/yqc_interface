package cn.tign.hz.factory.account;


import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.CreatePersonByThirdPartyUserIdResponse;


/**
 * @description  轩辕API个人账户创建
 * @author  澄泓
 * @date  2020/10/23 10:35
 * @version JDK1.7
 */

public class CreatePersonByThirdPartyUserId extends Request<CreatePersonByThirdPartyUserIdResponse> {
    private String thirdPartyUserId;
    private String name;
    private String idType;
    private String idNumber;
    private String mobile;
    private String email;

    //禁止构造无参对象
    private CreatePersonByThirdPartyUserId(){};

    //构造方法传入必填参数
    public CreatePersonByThirdPartyUserId(String thirdPartyUserId, String name, String idType, String idNumber) {
        this.thirdPartyUserId = thirdPartyUserId;
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

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    /**
     * 传入手机号码
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 传入邮箱地址
     * @param email 邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void build() {
        super.setUrl("/v1/accounts/createByThirdPartyUserId");
        super.setRequestType(RequestType.POST);
    }
}
