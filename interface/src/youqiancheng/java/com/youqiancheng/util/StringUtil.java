package com.youqiancheng.util;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName StringUtil
 * @Description TODO
 * @Author zzb
 * @Date 2022/2/23 20:26
 * @Version 1.0
 **/
public class StringUtil {


    /**
     *替换身份证号码中间8位*
     * @param cardNo
     * @return
     */
    public static String replaceCardNo(String cardNo){
        if(!StringUtils.isEmpty(cardNo)){
            StringBuilder stringBuilder = new StringBuilder(cardNo);
            cardNo = stringBuilder.replace(6, 14, "********").toString();
        }
        return cardNo;
    }

    public static String replaceBankCarNo(String bankCardNo){
        if(!StringUtils.isEmpty(bankCardNo)){
            StringBuilder stringBuilder = new StringBuilder(bankCardNo);
            bankCardNo = stringBuilder.replace(6, 15, "********").toString();
        }
        return bankCardNo;
    }
}
