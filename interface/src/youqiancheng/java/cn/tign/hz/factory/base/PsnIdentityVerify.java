package cn.tign.hz.factory.base;

import cn.tign.hz.factory.indivIdentity.*;

/**
 * 实名认证个人认证功能类
 * @author  澄泓
 * @date  2020/11/16 10:16
 * @version JDK1.7
 */
public class PsnIdentityVerify {
    /**
     * 获取个人实名认证地址
     * @param accountId
     * @return
     */
    public static IndivIdentityUrl indivIdentityUrl(String accountId){
        return new IndivIdentityUrl(accountId);
    }

    /**
     * 获取个人核身认证地址
     * @return
     */
    public static IndivAuthUrl indivAuthUrl(){
        return new IndivAuthUrl();
    }

    /**
     * 发起个人刷脸实名认证
     * @param accountId
     * @param faceauthMode
     * @param callbackUrl
     * @return
     */
    public static FaceIdentityByAccount faceIdentityByAccount(String accountId, String faceauthMode, String callbackUrl,
                                                              String notifyUrl,String contextId){
        return new FaceIdentityByAccount(accountId, faceauthMode, callbackUrl,notifyUrl,contextId);
    }

    /**
     * 发起个人刷脸核身认证
     * @param name
     * @param idNo
     * @param faceauthMode
     * @param callbackUrl
     * @return
     */
    public static FaceIdentity faceIdentity(String name, String idNo, String faceauthMode, String callbackUrl){
        return new FaceIdentity(name,idNo,faceauthMode,callbackUrl);
    }

    /**
     * 查询个人刷脸状态
     * @param flowId
     * @return
     */
    public static QryFaceStatus qryFaceStatus(String flowId){
        return new QryFaceStatus(flowId);
    }

    /**
     * 发起运营商3要素实名认证
     * @param accountId
     * @param mobileNo
     * @return
     */
    public static IndividualTelecom3FactorsByAccount individualTelecom3FactorsByAccount(String accountId, String mobileNo){
        return new IndividualTelecom3FactorsByAccount(accountId, mobileNo);
    }

    /**
     * 发起运营商3要素核身认证
     * @param name
     * @param idNo
     * @param mobileNo
     * @return
     */
    public static IndividualTelecom3Factors individualTelecom3Factors(String name, String idNo, String mobileNo){
        return new IndividualTelecom3Factors(name,idNo,mobileNo);
    }

    /**
     * 运营商短信验证码校验
     * @param flowId
     * @param authcode
     * @return
     */
    public static Tel3FactorsCodeVerify tel3FactorsCodeVerify(String flowId, String authcode){
        return new Tel3FactorsCodeVerify(flowId, authcode);
    }

    /**
     * 发起银行4要素实名认证
     * @param accountId
     * @param mobileNo
     * @param bankCardNo
     * @return
     */
    public static IndividualBankCard4FactorsByAccount individualBankCard4FactorsByAccount(String accountId, String mobileNo, String bankCardNo){
        return new IndividualBankCard4FactorsByAccount(accountId, mobileNo, bankCardNo);
    }

    /**
     * 发起银行4要素核身认证
     * @param name
     * @param certType
     * @param idNo
     * @param mobileNo
     * @param bankCardNo
     * @return
     */
    public static IndividualBankCard4Factors individualBankCard4Factors(String name, String certType, String idNo, String mobileNo, String bankCardNo){
        return new IndividualBankCard4Factors(name, certType, idNo, mobileNo, bankCardNo);
    }

    /**
     * 银行预留手机号验证码校验
     * @param flowId
     * @param authcode
     * @return
     */
    public static BankCard4FactorsCodeVerify bankCard4FactorsCodeVerify(String flowId, String authcode){
        return new BankCard4FactorsCodeVerify(flowId, authcode);
    }

}
