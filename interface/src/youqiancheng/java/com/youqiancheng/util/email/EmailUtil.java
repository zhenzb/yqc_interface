package com.youqiancheng.util.email;


import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述: <br>
 * 〈邮箱发送验证码〉
 * 业务要求可以修改发件邮箱，本人介绍的是参数存在数据库内的
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class EmailUtil {

    //为了保证参数没有存入数据库的也可以使用，此处并未删除参数变量
    //标题
    private static String TITLE="邮箱验证";
    //验证码
    private static String CONTENT="验证码";
    //发送邮箱的SMTP服务器
    //邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
    //设置邮箱的SMTP服务器，登录相对应的邮箱官网，去拿就行了
    private static String HOSTNAME="smtp.163.com";
    //发送信息的字符类型
    private static String CHARSET="utf-8";
    //发送邮箱
    private static String SENDEMAIL="oyangtengfei@163.com";
    //发送人用户名
    private static String USERNAME="撼动集团";
    //授权用户名和授权码
    private static String AUTHENTICATION_NAME="oyangtengfei@163.com";
    private static String USERNAME_PWD="xue520";
    //集合——保存验证码的集合
    private static Map<String, SendEmailDO> emailMap=new ConcurrentHashMap<>();
    //过期时间
    private static final long expiredTime=1000*10*60;

    /**
     *发送验证码
      * @param receiveEmail
     * @param dto
     *@return ()
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public static void sendEmail(String receiveEmail, EmailSettingDO dto) throws EmailException {

        //创建一个HtmlEmail实例对象
        HtmlEmail email=new HtmlEmail();

        //邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        //设置邮箱的SMTP服务器，登录相对应的邮箱官网，去拿就行了
       // email.setHostName(HOSTNAME);
        email.setHostName(dto.getHostName());

        //设置发送的字符类型
        email.setCharset(CHARSET);

        //设置收件人
        email.addTo(receiveEmail);

        //发送人的邮箱为自己的，用户名可以随便填
        //email.setFrom(SENDEMAIL,USERNAME);
        email.setFrom(dto.getSendMail(),dto.getUserName());

        //设置发送人的邮箱和用户名和授权码(授权码是自己设置的)
        //email.setAuthentication(AUTHENTICATION_NAME,USERNAME_PWD);
        email.setAuthentication(dto.getAuthenticationName(),dto.getUserNamePwd());

        //设置发送主题
        //email.setSubject(TITLE);
        email.setSubject(dto.getTitle());
        //生成验证码
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        //设置发送内容
        //email.setMsg(CONTENT+code);
        email.setMsg(dto.getContent()+":"+code);

        //进行发送
        email.send();

        //保存验证码
        SendEmailDO sendEmailDO=new SendEmailDO();
        sendEmailDO.setCode(code);
        sendEmailDO.setSendTime(System.currentTimeMillis());
        emailMap.put(receiveEmail,sendEmailDO);
    }

    /**
     * 功能描述: <br>
     * 〈验证验证码〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public static boolean verifyCode(String email,String code ){
        //清理集合中过期的验证码
        cleanEmailMap();
        //如果集合中存在符合条件的验证码则返回true,否则false
        if(emailMap.containsKey(email)){
           if(emailMap.get(email).getCode().equals(code)){
               return true;
           }
        }
        return false;
    }

    /**
     *清理验证码集合
      * @param
     *@return ()
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public static void cleanEmailMap() {
        //如果集合不为空
       if(!emailMap.isEmpty()){
           //循环集合；当前时间-验证码创建时间大于过期时间则清理验证码
           for (Map.Entry<String, SendEmailDO> stringSendEmailDOEntry : emailMap.entrySet()) {
               if((System.currentTimeMillis()-stringSendEmailDOEntry.getValue().getSendTime())>expiredTime){
                   emailMap.remove(stringSendEmailDOEntry.getKey());
               }
           }
       }
    }

    public static void main(String[] args) throws EmailException {
        String verifyCode = String
                .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        System.out.println(verifyCode);
        //sendEmail("183573612@qq.com","23443");
    }


}
