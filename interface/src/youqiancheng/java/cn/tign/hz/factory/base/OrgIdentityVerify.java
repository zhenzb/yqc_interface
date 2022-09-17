package cn.tign.hz.factory.base;

import cn.tign.hz.factory.orgIdentity.*;

/**
 * 实名认证企业实名认证功能类
 * @author  澄泓
 * @date  2020/11/17 9:40
 * @version JDK1.7
 */
public class OrgIdentityVerify {

    /**
     * 实名认证获取组织机构实名认证地址
     * @param accountId
     * @param agentAccountId
     * @return
     */
    public static OrgIdentityUrl orgIdentityUrl(String accountId, String agentAccountId){
        return new OrgIdentityUrl(accountId, agentAccountId);
    }

    /**
     * 获取组织机构核身地址
     * @return
     */
    public static OrgAuthUrl orgAuthUrl(){
        return new OrgAuthUrl();
    }

    /**
     * 实名认证发起企业核身认证4要素校验
     * @param name
     * @param orgCode
     * @param legalRepName
     * @param legalRepIdNo
     * @return
     */
    public static OrgFourFactors orgFourFactors(String name, String orgCode, String legalRepName, String legalRepIdNo){
        return new OrgFourFactors(name, orgCode, legalRepName, legalRepIdNo);
    }

    /**
     * 实名认证发起企业实名认证4要素校验
     * @param accountId
     * @param agentAccountId
     * @return
     */
    public static OrgFourFactorsByAccount orgFourFactorsByAccount(String accountId, String agentAccountId){
        return new OrgFourFactorsByAccount(accountId, agentAccountId);
    }

    /**
     * 发起企业实名认证3要素校验
     * @param accountId
     * @param agentAccountId
     * @return
     */
    public static OrgThreeFactorsByAccount orgThreeFactorsByAccount(String accountId, String agentAccountId){
        return new OrgThreeFactorsByAccount(accountId, agentAccountId);
    }

    /**
     * 发起企业核身认证3要素检验
     * @param name
     * @param orgCode
     * @param legalRepName
     * @return
     */
    public static OrgThreeFactors orgThreeFactors(String name, String orgCode, String legalRepName){
        return new OrgThreeFactors(name, orgCode, legalRepName);
    }

    /**
     * 发起授权签署实名认证
     * @param flowId
     * @param mobileNo
     * @return
     */
    public static OrglegalRepSignAuth orglegalRepSignAuth(String flowId, String mobileNo){
        return new OrglegalRepSignAuth(flowId, mobileNo);
    }

    /**
     * 发起授权签署核身认证
     * @param flowId
     * @param agentName
     * @param agentIdNo
     * @param mobileNo
     * @return
     */
    public static OrglegalRepSign orglegalRepSign(String flowId, String agentName, String agentIdNo, String mobileNo){
        return new OrglegalRepSign(flowId, agentName, agentIdNo, mobileNo);
    }

    /**
     * 查询授权书签署状态
     * @param flowId
     * @return
     */
    public static QrylegalRepSignResult qrylegalRepSignResult(String flowId){
        return new QrylegalRepSignResult(flowId);
    }

    /**
     * 实名认证获取授权签署链接
     * @param flowId
     * @return
     */
    public static GetAuthSignUrl getAuthSignUrl(String flowId){
        return new GetAuthSignUrl(flowId);
    }

    /**
     * 发起随机金额打款认证
     * @param flowId
     * @param bank
     * @param province
     * @param city
     * @param subbranch
     * @param cardNo
     * @param cnapsCode
     * @return
     */
    public static TransferRandomAmount transferRandomAmount(String flowId, String bank, String province, String city, String subbranch, String cardNo, String cnapsCode){
        return new TransferRandomAmount(flowId, bank, province, city, subbranch, cardNo, cnapsCode);
    }

    /**
     * 随机金额校验
     * @param flowId
     * @param amount
     * @return
     */
    public static VerifyRandomAmount verifyRandomAmount(String flowId, String amount){
        return new VerifyRandomAmount(flowId, amount);
    }

    /**
     * 查询随机金额打款进度
     * @param flowId
     * @return
     */
    public static QryTransferProcess qryTransferProcess(String flowId){
        return new QryTransferProcess(flowId);
    }

    /**
     * 发起企业反向打款认证
     * @param flowId
     * @return
     */
    public static ReversePayment reversePayment(String flowId){
        return new ReversePayment(flowId);
    }

    /**
     * 查询反向打款进度
     * @param flowId
     * @return
     */
    public static QryReversePaymentProcess qryReversePaymentProcess(String flowId){
        return new QryReversePaymentProcess(flowId);
    }

    /**
     * 查询打款银行信息
     * @param flowId
     * @param keyWord
     * @return
     */
    public static QrySubbranch qrySubbranch(String flowId, String keyWord){
        return new QrySubbranch(flowId, keyWord);
    }

}
