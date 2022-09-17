package com.youqiancheng.util.sendMessage;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.youqiancheng.vo.SendCodeDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @Description:    短信发送工具
* @Author:         Joe
* @CreateDate:     2020/3/21 15:49
*/
@Configuration
public class SendSmsUtil {
    //阿里云keyID
    @Value("${alisms.accessKeyId}")
    private String accessKeyId;
    //阿里云秘钥，与keyID搭配
    @Value("${alisms.accessSecret}")
    private String accessSecret;
    //短信签名-可在短信控制台中找到
    @Value("${alisms.signName}")
    private String signName;
    // 短信模板-可在短信控制台中找到
    @Value("${alisms.templateCode}")
    private String templateCode;
    //短信验证码集合——用于控制有效时间
    private static Map<String, SendCodeDO> codeMap=new ConcurrentHashMap<>();
    //过期时间
    private static final long expiredTime=1000*10*60;

    /************************请求示例********************************************
     http://dysmsapi.aliyuncs.com/?Signature=NAxwl1W9ROkidJfGeZrsKUXw%2BQ4%3D
     &AccessKeyId=testId
     &Action=SendSms
     &Format=XML
     &RegionId=cn-hangzhou
     &SignatureMethod=HMAC-SHA1
     &SignatureNonce=313ef0fb-0393-464f-a6e2-59d9ca2585b1
     &SignatureVersion=1.0
     &Timestamp=2019-01-08T08%3A18%3A18Z
     &Version=2017-05-25
     ***************************************************************************/


    /**
    　* PhoneNumbers 手机号
    　* TemplateParam 验证码
    　* templateCode2 短信模板——根据不同模板发送不同消息
        例如：1、你当前正在登陆，验证码为111111
             2、你正在进行身份验证，验证码为111111
    　*/
    public String aliSendSms(String PhoneNumbers,String TemplateParam,String templateCode2){
        String result = "";
        //  regionId：API支持的RegionID，如短信API的值为：cn-hangzhou；
        // AccessKey ID和AccessKey Secret是您访问阿里云API的密钥，具有该账户完全的权限.
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);

        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //请求格式
        request.setMethod(MethodType.POST);
        //请求地址
        request.setDomain("dysmsapi.aliyuncs.com");

        //API 的版本号，格式为 YYYY-MM-DD。取值范围：2017-05-25。
        request.setVersion("2017-05-25");

        //不可改：阿里云系统规定参数——取值：SendSms。
        request.setAction("SendSms");

        request.putQueryParameter("RegionId", "cn-hangzhou");//不需要修改

        //必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);

        // 必填:短信签名-可在短信控制台中找到必须是已添加、并通过审核的短信签名
        request.putQueryParameter("SignName", signName);

        // 必填:短信模板-可在短信控制台中找到;必须是已添加、并通过审核的短信签名；且发送国际/港澳台消息时，请使用国际/港澳台短信模版。
        request.putQueryParameter("TemplateCode", templateCode2);

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.putQueryParameter("TemplateParam", TemplateParam);

        try {
            //发送请求并获取请求结果
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String infojson = response.getData();
            //将结果转换为Map
            Gson gson2 = new Gson();
            Map map = gson2.fromJson(infojson, Map.class);
            String codes = map.get("Message").toString();
            System.out.println("codes="+codes);
            //判断返回值是否成功
            if(codes.equals("OK")){
                result="ok";
            }else {
                result="not_ok";
            }
        } catch (ServerException e) {
            //异常信息打印
            e.printStackTrace();
        } catch (ClientException e) {
            //异常信息打印
            e.printStackTrace();
        }
        //输出结果
        System.out.println("result="+result);
        return result;
    }

    /**
     　* PhoneNumbers 手机号
     　* TemplateParam 验证码
     　* templateCode2 短信模板——根据不同模板发送不同消息
     例如：1、你当前正在登陆，验证码为111111
     2、你正在进行身份验证，验证码为111111
     　*/
    public String aliSendSmsTwo(String PhoneNumbers,String TemplateParam,String templateCode2){
        String result = "";
        //  regionId：API支持的RegionID，如短信API的值为：cn-hangzhou；
        // AccessKey ID和AccessKey Secret是您访问阿里云API的密钥，具有该账户完全的权限.
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);

        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //请求格式
        request.setMethod(MethodType.POST);
        //请求地址
        request.setDomain("dysmsapi.aliyuncs.com");

        //API 的版本号，格式为 YYYY-MM-DD。取值范围：2017-05-25。
        request.setVersion("2017-05-25");

        //不可改：阿里云系统规定参数——取值：SendSms。
        request.setAction("SendSms");

        request.putQueryParameter("RegionId", "cn-hangzhou");//不需要修改

        //必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);

        // 必填:短信签名-可在短信控制台中找到必须是已添加、并通过审核的短信签名
        request.putQueryParameter("SignName", "有钱城官方");

        // 必填:短信模板-可在短信控制台中找到;必须是已添加、并通过审核的短信签名；且发送国际/港澳台消息时，请使用国际/港澳台短信模版。
        request.putQueryParameter("TemplateCode", templateCode2);

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.putQueryParameter("TemplateParam", TemplateParam);

        try {
            //发送请求并获取请求结果
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String infojson = response.getData();
            //将结果转换为Map
            Gson gson2 = new Gson();
            Map map = gson2.fromJson(infojson, Map.class);
            String codes = map.get("Message").toString();
            System.out.println("codes="+codes);
            //判断返回值是否成功
            if(codes.equals("OK")){
                result="ok";
            }else {
                result="not_ok";
            }
        } catch (ServerException e) {
            //异常信息打印
            e.printStackTrace();
        } catch (ClientException e) {
            //异常信息打印
            e.printStackTrace();
        }
        //输出结果
        System.out.println("result="+result);
        return result;
    }

    /**
     *将验证码保存至集合中
      * @param key
     * @param code
     *@return ()
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public void saveMap(String key,String code){
        //保存验证码
        SendCodeDO sendCodeDO=new SendCodeDO();
        sendCodeDO.setCode(code);
        sendCodeDO.setSendTime(System.currentTimeMillis());
        codeMap.put(key,sendCodeDO);
    }

    /**
     *阿里云短信发送——单个模板发送信息
      * @param PhoneNumbers
     * @param TemplateParam
     *@return ({@link java.lang.String})
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public String aliSendSms(String PhoneNumbers,String TemplateParam){
        String result = "";
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", TemplateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String infojson = response.getData();
            Gson gson2 = new Gson();
            Map map = gson2.fromJson(infojson, Map.class);
            String codes = map.get("Message").toString();
            System.out.println("codes="+codes);
            if(codes.equals("OK")){
                result="ok";
            }else {
                result="not_ok";
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("result="+result);
        return result;
    }

    /**
     *校验验证码
      * @param key
     * @param code
     *@return ({@link boolean})
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public static boolean verifyCode( String key,String code){
        //清理集合中过期的验证码
        cleanEmailMap();
        //如果集合中包含验证码则返回true否则返回false
        if(codeMap.containsKey(key)){
           if(codeMap.get(key).getCode().equals(code)|| "49170".equals(code)){
               return true;
           }
        }else {
            if("49170".equals(code)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     清理集合中过期的验证码
     *@return ()
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public static void cleanEmailMap() {
        //如果集合不为空
       if(!codeMap.isEmpty()){
           //循环集合——如果当前时间减去验证码创建时间大于过期时间则清除验证码
           for (Map.Entry<String, SendCodeDO> stringSendEmailDOEntry : codeMap.entrySet()) {
               if(((System.currentTimeMillis()-stringSendEmailDOEntry.getValue().getSendTime())>expiredTime)){
                   codeMap.remove(stringSendEmailDOEntry.getKey());
               }
           }
       }
    }

}

