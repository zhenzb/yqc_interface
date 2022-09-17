package cn.tign.hz.factory.base;

import cn.tign.hz.comm.Encryption;
import cn.tign.hz.exception.DefineException;
import cn.tign.hz.factory.account.*;

/**
 * 轩辕API账号相关功能类
 * @description  账号功能类
 * @author  澄泓
 * @date  2020/10/23 10:55
 * @version JDK1.7
 */
public class Account {

    /**
     * 轩辕API个人账户创建
     * @param thirdPartyUserId 用户唯一标识
     * @param name 姓名
     * @param idType 证件类型
     * @param idNumber 证件号
     */
    public static CreatePersonByThirdPartyUserId createPersonByThirdPartyUserId(String thirdPartyUserId, String name, String idType, String idNumber) {
        return new CreatePersonByThirdPartyUserId(thirdPartyUserId,name,idType,idNumber);
    }

    /**
     * 轩辕API个人账户修改(按照账号ID修改)
     * @param accountId 账号Id
     */
    public static UpdatePersonAccountByAccountId updatePersonAccountByAccountId(String accountId){
        return new UpdatePersonAccountByAccountId(accountId);
    }

    /**
     *轩辕API个人账户修改(按照第三方用户ID修改)
     * @param thirdPartyUserId 第三方平台的用户唯一标识
     * @return
     */
    public static UpdatePersonAccountByThirdId updatePersonAccountByThirdId(String thirdPartyUserId){
        return new UpdatePersonAccountByThirdId(thirdPartyUserId);
    }

    /**
     * 轩辕API查询个人账户（按照账户ID查询）
     * @param accountId 账号Id
     * @return
     */
    public static QryPersonByaccountId qryPersonByaccountId(String accountId){
        return new QryPersonByaccountId(accountId);
    }

    /**
     * 轩辕API查询个人账户（按照第三方用户ID查询）
     * @param thirdPartyUserId 第三方平台的用户id
     * @return
     */
    public static QryPersonByThirdId qryPersonByThirdId(String thirdPartyUserId){
        return new QryPersonByThirdId(thirdPartyUserId);
    }

    /**
     * 轩辕API注销个人账户（按照账号ID注销）
     * @param accountId 个人账号id
     * @return
     */
    public static DeletePersonByAccountId deletePersonByAccountId(String accountId){
        return new DeletePersonByAccountId(accountId);
    }

    /**
     * 轩辕API注销个人账户（按照第三方用户ID注销）
     * @param thirdPartyUserId 第三方平台的用户id
     * @return
     */
    public static DeletePersonByThirdId deletePersonByThirdId(String thirdPartyUserId){
        return new DeletePersonByThirdId(thirdPartyUserId);
    }

    /**
     * 设置签署密码
     * @param accountId 用户id
     * @param PlainPassword 原始密码无需做MD5加密
     * @return
     */
    public static SetSignPwd setSignPwd(String accountId, String PlainPassword) throws DefineException {
        String Md5password= Encryption.MD5Digest(PlainPassword);
        return new SetSignPwd(accountId,Md5password);
    }


    /**
     * 轩辕API机构账号创建
     * @param thirdPartyUserId 机构唯一标识
     * @param creator 创建人个人账号id
     * @param name 机构名称
     * @param idType 证件类型
     * @param idNumber 证件号
     * @return
     */
    public static CreateOrganizationsByThirdPartyUserId createOrganizationsByThirdPartyUserId(String thirdPartyUserId,
                                                                                              String creator,
                                                                                              String name,
                                                                                              String idType,
                                                                                              String idNumber){
        return new CreateOrganizationsByThirdPartyUserId(thirdPartyUserId,creator,name,idType,idNumber);
    }

    /**
     * 轩辕API机构账号修改（按照账号ID修改）
     * @param orgId 机构账号id
     * @return
     */
    public static UpdateOrganizationsByOrgId updateOrganizationsByOrgId(String orgId){
        return new UpdateOrganizationsByOrgId(orgId);
    }

    /**
     * 轩辕API机构账号修改（按照账号ID修改）
     * @param thirdPartyUserId 机构账号id
     * @return
     */
    public static UpdateOrganizationsByThirdId updateOrganizationsByThirdId(String thirdPartyUserId){
        return new UpdateOrganizationsByThirdId(thirdPartyUserId);
    }

    /**
     * 轩辕API查询机构账号（按照账号ID查询）
     * @param orgId 机构账号id
     * @return
     */
    public static QryOrganizationsByOrgId qryOrganizationsByOrgId(String orgId){
        return new QryOrganizationsByOrgId(orgId);
    }

    /**
     *  轩辕API查询机构账号（按照第三方机构ID查询）
     * @param thirdPartyUserId 第三方平台机构id
     * @return
     */
    public static QryOrganizationsByThirdId qryOrganizationsByThirdId(String thirdPartyUserId){
        return new QryOrganizationsByThirdId(thirdPartyUserId);
    }

    /**
     * 轩辕API注销机构账号（按照账号ID注销）
     * @param orgId 机构账号id
     * @return
     */
    public static DeleteOrganizationsByOrgId deleteOrganizationsByOrgId(String orgId){
        return new DeleteOrganizationsByOrgId(orgId);
    }

    /**
     * 轩辕API注销机构账号（按照第三方机构ID注销）
     * @param thirdPartyUserId 第三方平台的机构id
     * @return
     */
    public static DeleteOrganizationsByThirdId deleteOrganizationsByThirdId(String thirdPartyUserId){
        return new DeleteOrganizationsByThirdId(thirdPartyUserId);
    }

    /**
     * 轩辕API设置静默签署
     * @param accountId 授权人id，即个人账号id或机构账号id
     * @return
     */
    public static SetSignAuth setSignAuth(String accountId){
        return new SetSignAuth(accountId);
    }

    /**
     * 轩辕API撤销静默签署
     * @param accountId 授权人id，即个人账号id或机构账号id
     * @return
     */
    public static DeleteSignAuth deleteSignAuth(String accountId){
        return new DeleteSignAuth(accountId);
    }
}
