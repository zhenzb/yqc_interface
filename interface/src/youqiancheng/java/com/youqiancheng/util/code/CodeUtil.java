package com.youqiancheng.util.code;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.youqiancheng.controller.client.ClientLoginController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述: <br>
 * 〈生成验证码工具类〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class CodeUtil {
    //map集合用于保存验证码实体
    private static Map<String, CodeEntity> codeMap = new ConcurrentHashMap<>();
    //过期时间
    private static final long expiredTime=1000*10*60;

    /**
     *生成验证码
      * @param codeEntity
     *@return String
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public static String createCode(CodeEntity codeEntity) {
        //清理验证码集合
        cleanEmailMap();
        //生成六位验证码
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        codeEntity.setCode(code);
        //创建时间
        codeEntity.setCreateTime(LocalDateTime.now());
        //放入集合key——手机号
        codeMap.put(codeEntity.getPhone(),codeEntity);
        return code;
    }

    //校验验证码
    public static boolean verifyCode(CodeEntity codeEntity) {
        //清理验证码集合
        cleanEmailMap();
        //如果集合内存在符合条件的记录，key=传入的手机号
        if(codeMap.containsKey(codeEntity.getPhone())){
            System.out.println(codeMap.get(codeEntity.getPhone()).getCode());
            System.out.println(codeEntity.getCode());
            //如果集合内符合条件的的验证码与传入的验证码一直，则返回true
           if(codeMap.get(codeEntity.getPhone()).getCode().equals(codeEntity.getCode())){
               return true;
           }
        }
        return false;
    }

    //清空超过有效期的验证码
    public  static void  cleanEmailMap() {
        //如果集合不为空
       if(!codeMap.isEmpty()){
           //循环集合
           for (Map.Entry<String, CodeEntity> codeEntry : codeMap.entrySet()) {
               //获取当前时间和创建时间的时间差
               Duration duration = Duration.between(codeEntry.getValue().getCreateTime(), LocalDateTime.now());
               //如果时间差大于有效时间—即当前时间-创建时间>有效时间，则验证码过期，从集合中删除
               if(duration.toMillis() > expiredTime){
                   codeMap.remove(codeEntry.getKey());
               }
           }
       }
    }

    public static void main(String[] args){
        CodeEntity codeEntity = new CodeEntity();
        codeEntity.setPhone("18518506652");
        //System.out.println("----生成验证码------:"+CodeUtil.createCode(codeEntity));

        codeEntity.setPhone("18518506652");
        codeEntity.setCode("935995");
        System.out.println("----校验验证码------:"+CodeUtil.verifyCode(codeEntity));
    }

    public static JSONObject buildSmsCode(){
        JSONObject json = new JSONObject();
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        ClientLoginController.SmsData smsData = new ClientLoginController.SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        json.put ("numeral",numeral);
        json.put("code",code);
        return json;
    }

}
