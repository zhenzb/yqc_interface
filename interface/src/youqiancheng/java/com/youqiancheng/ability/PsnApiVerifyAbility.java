package com.youqiancheng.ability;

import cn.tign.hz.comm.DeclareDetails;
import cn.tign.hz.exception.DefineException;
import cn.tign.hz.factory.Factory;
import cn.tign.hz.factory.account.CreatePersonByThirdPartyUserId;
import cn.tign.hz.factory.base.Account;
import cn.tign.hz.factory.base.PsnIdentityVerify;
import cn.tign.hz.factory.indivIdentity.FaceIdentityByAccount;
import cn.tign.hz.factory.response.CreatePersonByThirdPartyUserIdResponse;
import cn.tign.hz.factory.response.indivIdentity.FaceIdentityByAccountResponse;
import com.handongkeji.config.exception.JsonException;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.service.client.service.B07AuthenticationClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PsnApiVerifyAbility
 * @Description E签宝认证相关接口类
 * @Author zzb
 * @Date 2022/5/5 18:27
 * @Version 1.0
 **/
@Component
public class PsnApiVerifyAbility {
    Logger logger = LoggerFactory.getLogger(PsnApiVerifyAbility.class);
    @Resource
    B07AuthenticationClientService b07AuthenticationClientService;

    @Value("${EQianBao.notifyUrl}")
    private String notifyUrl;


    private static String appId = "5111733154";//"5111733154";  7438913082
    private static String secret = "7ebec3e399e7a168ede926147b1f49eb";//"7ebec3e399e7a168ede926147b1f49eb";

    static{
        DeclareDetails.showImportantMessage();//demo声明明细
//        正式环境域名：https://openapi.esign.cn
//        模拟环境（沙箱环境）域名：https://smlopenapi.esign.cn
        String host="https://openapi.esign.cn"; //网关
        String project_id = appId;//应用id
        String project_secret= secret;//密钥
        Factory.init(host,project_id,project_secret);//初始化，传入请求网关和应用id以及密钥,全局运行一次
        Factory.setDebug(true);//开启日志，测试阶段建议开启，方便记录数据，日志保存在根目录的log.txt文件中
    }

    /**
     * 唤起用户刷脸页面接口
      * @param userId
     */
    public String psnVerify(Long userId){
        String authUrl = null;
        try {
            //one 查询用户实名认证信息
            Map<String,Object> userAuthenticationMap = new HashMap<>();
            userAuthenticationMap.put("userId",userId);
            List<B07AuthenticationDO> list = b07AuthenticationClientService.list(userAuthenticationMap);
            if(list.isEmpty()){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户认证信息缺失");
            }
            B07AuthenticationDO b07AuthenticationDO = list.get(0);
            //创建E签宝个人账户
            String psnAccountId = createPsn(b07AuthenticationDO);
            //签署账号创建接口生成的account_Id
            String accountId=psnAccountId;//个人账号
            //认证完成以后前端需要跳转的页面地址  TENCENT  ESIGN
            String callbackUrl="https://www.youqiancheng.vip/#/signing";
            logger.info("-----------------发起个人刷脸实名认证 start-----------------");
            FaceIdentityByAccount faceIdentityByAccount = PsnIdentityVerify.faceIdentityByAccount(accountId,
                    "ZHIMACREDIT",callbackUrl,notifyUrl,String.valueOf(b07AuthenticationDO.getUserId()));
            FaceIdentityByAccountResponse faceByActResponse= faceIdentityByAccount.execute();
            authUrl = faceByActResponse.getData().getAuthUrl();
            logger.info("-----------------发起个人刷脸实名认证 end-----------------");
        }catch (Exception e){
            e.printStackTrace();
        }
        return authUrl;
    }
    /**
     * 创建E签宝个人账号
     * @param b07AuthenticationDO
     */
    public String createPsn( B07AuthenticationDO b07AuthenticationDO ){
        String accountId = null;
        try {
        String thirdPartyUserIdPsn = b07AuthenticationDO.getCardNo();//thirdPartyUserId参数，用户唯一标识，自定义保持唯一即可
        String namePsn = b07AuthenticationDO.getName();//name参数，姓名
        String idTypePsn = "CRED_PSN_CH_IDCARD";//idType参数，证件类型
        String idNumberPsn = thirdPartyUserIdPsn;//idNumber参数，证件号
        String mobilePsn = b07AuthenticationDO.getCreatePerson();//mobile参数，手机号
        //创建个人账号
        //cn.tign.hz.factory.base.Account;
        //Account类是账号相关的功能类，所有账号相关方法由Account发起
        logger.info("------------------ 创建个人账号 start -----------------");
        CreatePersonByThirdPartyUserId createPsn = Account.createPersonByThirdPartyUserId(
                thirdPartyUserIdPsn,
                namePsn,
                idTypePsn,
                idNumberPsn);
        createPsn.setMobile(mobilePsn);
            CreatePersonByThirdPartyUserIdResponse createPsnResp = createPsn.execute();//execute方法发起请求
            accountId = createPsnResp.getData().getAccountId();//生成的个人账号保存好，后续接口调用需要使用
            logger.info("------------------ 创建个人账号 end -----------------");
            //保存创建E签宝个人账号
            b07AuthenticationDO.setEAccountId(accountId);
            b07AuthenticationClientService.update(b07AuthenticationDO);
        } catch (DefineException e) {
            e.printStackTrace();
        }
       return accountId;
    }
}
