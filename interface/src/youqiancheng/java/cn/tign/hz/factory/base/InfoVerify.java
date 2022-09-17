package cn.tign.hz.factory.base;

import cn.tign.hz.factory.infoverify.*;

/**
 * 实名认证信息比对功能类
 * @author  澄泓
 * @date  2020/11/2 16:41
 * @version JDK1.7
 */
public class InfoVerify {

    /**
     * 个人2要素信息比对
     * @param idNo 身份证号（大陆二代身份证）
     * @param name 姓名
     * @return
     */
    public static IndividualBase individualBase(String idNo, String name){
        return new IndividualBase(idNo, name);
    }

    /**
     * 个人运营商3要素信息比对
     * @param idNo 身份证号（大陆二代身份证）
     * @param name 姓名
     * @param mobileNo 手机号（中国大陆3大运营商）
     * @return
     */
    public static Telecom3Factors telecom3Factors(String idNo, String name, String mobileNo){
        return new Telecom3Factors(idNo, name, mobileNo);
    }

    /**
     * 个人银行卡3要素信息比对
     * @param idNo 身份证号（大陆二代身份证）
     * @param name 姓名
     * @param cardNo 银行卡号（银联卡号）
     * @return
     */
    public static Bank3Factors bank3Factors(String idNo, String name, String cardNo){
        return new Bank3Factors(idNo, name, cardNo);
    }

    /**
     * 个人银行卡4要素信息比对
     * @param idNo 身份证号（大陆二代身份证）
     * @param name 姓名
     * @param cardNo 银行卡号（银联卡号）
     * @param mobileNo 银行预留手机号（非短信通知手机号）
     * @return
     */
    public static Bank4Factors bank4Factors(String idNo, String name, String cardNo, String mobileNo){
        return new Bank4Factors(idNo, name, cardNo, mobileNo);
    }

    /**
     * 企业2要素信息比对
     * @param name 企业名称
     * @param orgCode 企业证件号,支持15位工商注册号或统一社会信用代码
     * @return
     */
    public static EntBase entBase(String name, String orgCode){
        return new EntBase(name, orgCode);
    }

    /**
     * 企业3要素信息比对
     * @param name 企业名称
     * @param orgCode 企业证件号,支持15位工商注册号或统一社会信用代码
     * @param legalRepName 企业法定代表人姓名
     * @return
     */
    public static Bureau3Factors bureau3Factors(String name, String orgCode, String legalRepName){
        return new Bureau3Factors(name, orgCode, legalRepName);
    }

    /**
     * 企业4要素信息比对
     * @param name 企业名称
     * @param orgCode 企业统一社会信用代码或工商注册号
     * @param legalRepName 企业法定代表人姓名
     * @param legalRepCertNo 企业法定代表人身份证号
     * @return
     */
    public static Bureau4Factors bureau4Factors(String name, String orgCode, String legalRepName, String legalRepCertNo){
        return new Bureau4Factors(name, orgCode, legalRepName, legalRepCertNo);
    }


    /**
     * 律所3要素信息比对
     * @param name 律所名称
     * @param codeUSC 律所统一社会信用代码号
     * @param legalRepName 律所法定代表人姓名
     * @return
     */
    public static LawFirm lawFirm(String name, String codeUSC, String legalRepName){
        return new LawFirm(name, codeUSC, legalRepName);
    }

    /**
     * 组织机构3要素信息比对
     * @param name 组织机构名称
     * @param orgCode 组织证件号
     * @param legalRepName 组织法定代表人姓名
     * @return
     */
    public static OrgVerify orgVerify(String name, String orgCode, String legalRepName){
        return new OrgVerify(name, orgCode, legalRepName);
    }

    /**
     * 非工商组织3要素信息比对
     * @param name 组织机构名称
     * @param codeUSC 社会组织统一社会信用代码证
     * @param legalRepName 社会组织法定代表人名称
     * @return
     */
    public static Social social(String name, String codeUSC, String legalRepName){
        return new Social(name, codeUSC, legalRepName);
    }
}
