package com.youqiancheng.controller.eqianbao;

import cn.tign.hz.bean.ContextInfo;
import cn.tign.hz.comm.DeclareDetails;
import cn.tign.hz.factory.Factory;
import cn.tign.hz.factory.account.CreatePersonByThirdPartyUserId;
import cn.tign.hz.factory.base.Account;
import cn.tign.hz.factory.base.PsnIdentityVerify;
import cn.tign.hz.factory.indivIdentity.IndivAuthUrl;
import cn.tign.hz.factory.indivIdentity.IndivIdentityUrl;
import cn.tign.hz.factory.response.CreatePersonByThirdPartyUserIdResponse;
import cn.tign.hz.factory.response.indivIdentity.IndivAuthUrlResponse;
import cn.tign.hz.factory.response.indivIdentity.IndivIdentityUrlResponse;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.service.client.service.B07AuthenticationClientService;
import com.youqiancheng.vo.result.ResultVo;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PsnWebVerifyController
 * @Description step 1、用户点击页面实名认证按钮，调用该接口进行实名认证操作
 * @Author zzb
 * @Date 2022/5/3 11:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/psnWebVerify")
public class PsnWebVerifyController {
    Logger logger = LoggerFactory.getLogger(PsnWebVerifyController.class);
    @Resource
    B07AuthenticationClientService b07AuthenticationClientService;
    @Resource
    B01UserDao b01UserDao;
    static{
        DeclareDetails.showImportantMessage();//demo声明明细
//        正式环境域名：https://openapi.esign.cn
//        模拟环境（沙箱环境）域名：https://smlopenapi.esign.cn
        String host="https://smlopenapi.esign.cn"; //网关
        String project_id="7438913082";//应用id
        String project_scert="4e9b7c40273e11e4525a0c0899b8b783";//密钥
        Factory.init(host,project_id,project_scert);//初始化，传入请求网关和应用id以及密钥,全局运行一次
        Factory.setDebug(true);//开启日志，测试阶段建议开启，方便记录数据，日志保存在根目录的log.txt文件中
    }
    public ResultVo userVerify(String userId){
        String shortLink =null;
        try{
            //查询用户
            B01UserDO b01UserDO = b01UserDao.get(Long.valueOf(userId));
            //one 查询用户实名认证信息
            Map<String,Object> userAuthenticationMap = new HashMap<>();
            userAuthenticationMap.put("userId",userId);
            List<B07AuthenticationDO> list = b07AuthenticationClientService.list(userAuthenticationMap);
            if(list.isEmpty()){
                B07AuthenticationDO b07Authentication = new B07AuthenticationDO();
                b07Authentication.setCreateTime(LocalDateTime.now());
                b07Authentication.setUpdateTime(LocalDateTime.now());
                b07Authentication.setUpdatePerson(b01UserDO.getMobile());
                b07Authentication.setStatus(StatusConstant.ExamineStatus.un_examine.getCode());
                b07Authentication.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                b07Authentication.setName(b01UserDO.getNick());
                b07AuthenticationClientService.insert(b07Authentication);
                list = b07AuthenticationClientService.list(userAuthenticationMap);
            }
            //签署人
            B07AuthenticationDO b07AuthenticationDO = list.get(0);
            String thirdPartyUserIdPsn = b07AuthenticationDO.getCardNo();//thirdPartyUserId参数，用户唯一标识，自定义保持唯一即可
            String namePsn = b07AuthenticationDO.getName();//name参数，姓名
            String idTypePsn = "CRED_PSN_CH_IDCARD";//idType参数，证件类型
            String idNumberPsn = null;//idNumber参数，证件号
            String mobilePsn = null;//mobile参数，手机号
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
            String accountId = createPsnResp.getData().getAccountId();//生成的个人账号保存好，后续接口调用需要使用
            logger.info("------------------ 创建个人账号 end -----------------");
            //保存创建E签宝个人账号
            b07AuthenticationDO.setEAccountId(accountId);
            b07AuthenticationClientService.update(b07AuthenticationDO);

            logger.info("-----------------获取个人实名认证地址 start-----------------");
            IndivIdentityUrl indivIdentityUrl = PsnIdentityVerify.indivIdentityUrl(accountId);
            indivIdentityUrl.setRepeatIdentity(false);
            ContextInfo contextInfo = new ContextInfo();
            contextInfo.setNotifyUrl("https://client.youqiancheng.vip/eNotify/psnWebVerify");
            indivIdentityUrl.setContextInfo(contextInfo);
            IndivIdentityUrlResponse indivIdentityUrlResponse = indivIdentityUrl.execute();
            String flowId = indivIdentityUrlResponse.getData().getFlowId();
            shortLink = indivIdentityUrlResponse.getData().getShortLink();
            System.out.println("认证流程Id: "+flowId);
            System.out.println("认证链接: "+shortLink);
            logger.info("-----------------获取个人实名认证地址 end-----------------");

            logger.info("-----------------获取个人核身认证地址 start-----------------");
            IndivAuthUrl indivAuthUrl = PsnIdentityVerify.indivAuthUrl();
            IndivAuthUrlResponse indivAuthUrlResponse = indivAuthUrl.execute();
            System.out.println("个人核身认证地址 :"+indivAuthUrlResponse.getData().getShortLink());
            logger.info("-----------------获取个人核身认证地址 end-----------------");

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("运行结束");
        return ResultVOUtils.success(shortLink);
    }
}
