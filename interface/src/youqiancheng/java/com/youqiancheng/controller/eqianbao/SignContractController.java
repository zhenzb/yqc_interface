package com.youqiancheng.controller.eqianbao;

import cn.tign.hz.bean.*;
import cn.tign.hz.comm.DeclareDetails;
import cn.tign.hz.comm.FileBean;
import cn.tign.hz.enums.HeaderConstant;
import cn.tign.hz.exception.DefineException;
import cn.tign.hz.factory.Factory;
import cn.tign.hz.factory.account.CreateOrganizationsByThirdPartyUserId;
import cn.tign.hz.factory.account.CreatePersonByThirdPartyUserId;
import cn.tign.hz.factory.base.Account;
import cn.tign.hz.factory.base.FileTemplate;
import cn.tign.hz.factory.base.SignFile;
import cn.tign.hz.factory.filetemplate.CreateFileByTemplate;
import cn.tign.hz.factory.filetemplate.GetFileUploadUrl;
import cn.tign.hz.factory.filetemplate.UploadFile;
import cn.tign.hz.factory.response.*;
import cn.tign.hz.factory.signfile.CreateFlowOneStep;
import cn.tign.hz.factory.signfile.signers.GetFileSignUrl;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.Constants;
import com.handongkeji.util.DateUtil;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.dao.B07AuthenticationDao;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.service.client.service.B07AuthenticationClientService;
import com.youqiancheng.vo.result.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SignContractController
 * @Description step3、进行签约
 * @Author zzb
 * @Date 2022/5/3 7:47
 * @Version 1.0
 **/
@RestController
@RequestMapping("/signContract")
public class SignContractController {
    Logger logger = LoggerFactory.getLogger(SignContractController.class);
    @Resource
    B07AuthenticationClientService b07AuthenticationClientService;
    @Value("${EQianBao.contractNotifyUrl}")
    private String notifyUrl;
    //正式环境 appid:5111733154   secret:7ebec3e399e7a168ede926147b1f49eb
    //测试环境：appid:7438913082   secret:4e9b7c40273e11e4525a0c0899b8b783
    private static String appId = "5111733154";//"5111733154"; //正式环境
    private static String secret = "7ebec3e399e7a168ede926147b1f49eb";//"7ebec3e399e7a168ede926147b1f49eb";

    static {
        //正式环境域名：https://openapi.esign.cn
        //模拟环境（沙箱环境）域名：https://smlopenapi.esign.cn
        String host="https://openapi.esign.cn"; //网关
        String project_id= appId;//应用id
        String project_secret= secret;//密钥
        Factory.init(host,project_id,project_secret);//初始化，传入请求网关，应用id以及密钥,全局一次
        Factory.setDebug(true);//开启日志，测试阶段建议开启，方便记录数据，日志保存在根目录的log.txt文件中
    }

    /**
     * 用户E签宝进行签约（椰云提现时需要用户签约文件）
     * @param userId
     * @return 返回签约地址，用户在浏览器打开地址进行签约
     */
    @RequestMapping("/userSign")
    public ResultVo userSign(Long userId){
        //签约地址
        String shortUrl = null;
        String sealId = "08e2979c-72e7-46d1-9b7c-ab161754924f"; //椰云公章
        //String sealId2 = "49ca06f8-fa05-4d70-a832-3c0b6fddb41e"; //有钱城公章
        //one 查询用户实名认证信息（组装调用E签宝签约接口参数使用）
        Map<String,Object> userAuthenticationMap = new HashMap<>();
        if(userId !=null &&  !"".equals(userId)){
            userAuthenticationMap.put("user_id",userId);
        }else{
            userAuthenticationMap.put("status",1);
        }
        List<B07AuthenticationDO> list = b07AuthenticationClientService.list(userAuthenticationMap);
        if(list.isEmpty()){
            throw new JsonException(Constants.$Null, "请先完成实名认证");
        }
        //签署人
        B07AuthenticationDO b07AuthenticationDO = list.get(0);
        try{
            ///String filePath="D:\\919yqc_doc\\承揽协议.pdf";//文件地址 D:\IDEAproject\PdfFile\dstPdf\qianshu.pdf
            //-----------------------个人账号信息用于创建个人账号接口传入-----------------------------
            String accountId = b07AuthenticationDO.getEAccountId();//"c10ae545183a47c4ab69879190310686";//"19786677a7314b7f9b864551cb8572c2";
            String orgId = "9cae26431b6f444bbf1cbb9c6ae0996c";// "9e40154d668f4227ac1ff7d61b925f37";
            if(accountId == null ){
                String thirdPartyUserIdPsn = b07AuthenticationDO.getCardNo();//thirdPartyUserId参数，用户唯一标识，自定义保持唯一即可
                String namePsn = b07AuthenticationDO.getName();//name参数，姓名
                String idTypePsn = "CRED_PSN_CH_IDCARD";//idType参数，证件类型
                String idNumberPsn = b07AuthenticationDO.getCardNo();//idNumber参数，证件号
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
            }

            if(orgId == null ){
                //------------------------企业账号信息用于创建机构账号接口传入----------------
                String thirdPartyUserIdOrg="20525907885f4fee9ba4b93c66902693";//thirdPartyUserId参数，用户唯一标识，自定义保持唯一即可
                String nameOrg="海南椰云众包科技有限公司";//name参数，机构名称
                String idTypeOrg="CRED_ORG_USCC";//idType参数，证件类型
                String idNumberOrg="91469036MA5TYKG8XF";//idNumber参数,机构证件号
                logger.info("------------------ 创建企业账号 start -----------------");
                CreateOrganizationsByThirdPartyUserId createOrg = Account.createOrganizationsByThirdPartyUserId(
                        thirdPartyUserIdOrg,
                        accountId,
                        nameOrg,
                        idTypeOrg,
                        idNumberOrg);
                CreateOrganizationsByThirdPartyUserIdResponse createOrgResp = createOrg.execute();//execute方法发起请求
                orgId = createOrgResp.getData().getOrgId();//生成的企业账号保存好，后续接口调用需要使用
                logger.info("------------------ 创建企业账号 end -----------------");
            }

            logger.info("------------------ 通过调用合同模板 start -----------------");
            //获取模板
            SimpleFormFields simpleFormFields = new SimpleFormFields();
            simpleFormFields.put("01",DateUtil.getStringNowDateTime());
            simpleFormFields.put("02",DateUtil.getCurrentDateTime("yyyy-MM-dd"));
            simpleFormFields.put("03",b07AuthenticationDO.getName());
            simpleFormFields.put("04",b07AuthenticationDO.getCardNo());
            CreateFileByTemplate fileByTemplate = FileTemplate.createFileByTemplate("《任务承揽协议》-灵工方VS椰云享", "06691fca6c244ca8b96123f4819bba02", simpleFormFields);
            CreateFileByTemplateResponse createFileByTemplateResponse = fileByTemplate.execute();
            String fileId = createFileByTemplateResponse.getData().getFileId();

            logger.info("------------------ 一步发起签署 start -----------------");
            //这里以一步发起签署为例，如果想查看分步发起签署代码示例，可以看b2bDemo
            //cn.tign.hz.factory.base.SignFile;
            //SignFile是文件模板功能类，所有文件模板相关的接口由SignFile发起
            Docs docs = new Docs();//入参是array格式时，先构造对象参数的array
            docs.add(new Doc().setFileId(fileId));//向array传入对象
            FlowConfigInfo flowConfigInfo = new FlowConfigInfo();
            flowConfigInfo.setNoticeDeveloperUrl(notifyUrl);
            flowConfigInfo.setRedirectUrl("https://www.youqiancheng.vip/#/");
            FlowInfo flowInfo = new FlowInfo().setBusinessScene("承揽协议合同签署")//flowInfo参数
                    .setAutoArchive(true)//启用自动归档
                    .setFlowConfigInfo(flowConfigInfo)
                    .setAutoInitiate(true).setInitiatorAccountId(accountId).setInitiatorAuthorizedAccountId(accountId);//启用自动开启流程

            Signfields psnSignfields = new Signfields();
            psnSignfields.add(new Signfield()
                    .setFileId(fileId)
                    .setSealType("1")
                    .setPosBean(new PosBean()
                            .setPosPage("7")
                            .setPosX(200)
                            .setPosY(300)).setSignDateBeanType(1));//构造个人signfields参数对象,用于后续入参使用,支持链式入参

            Signfields orgSignfields = new Signfields();
            orgSignfields.add(new Signfield()
                    .setFileId(fileId)
                    .setAutoExecute(true)
                    .setSealId(sealId)
                    .setActorIndentityType("2")//机构签署必传
                    .setPosBean(new PosBean()
                            .setPosPage("7")
                            .setPosX(500)
                            .setPosY(300)).setSignDateBeanType(1));//构造企业signfields参数对象,用于后续入参使用,支持链式入参

            Signers signers = new Signers();
            signers.add(new Signer()
                    .setSignerAccount(
                            new SignerAccount()
                                    .setSignerAccountId(accountId)
                    ).setSignFields(psnSignfields).setSignOrder(1));//传入个人signer信息
            signers.add(new Signer()
                    .setSignerAccount(
                            new SignerAccount()
                                    .setAuthorizedAccountId(orgId)//企业签署需要传入该参数
                    ).setSignFields(orgSignfields).setSignOrder(2));//传入企业signer信息
            CreateFlowOneStep flowOneStep = SignFile.createFlowOneStep(docs, flowInfo, signers);
            CreateFlowOneStepResponse flowOneStepResp = flowOneStep.execute();
            String flowId = flowOneStepResp.getData().getFlowId();//流程id保存好
            b07AuthenticationDO.setFlowId(flowId);
            b07AuthenticationClientService.update(b07AuthenticationDO);
            logger.info("------------------ 一步发起签署 end -----------------");

            //开启流程后会向个人实名手机号发送签署信息，会向企业签署经办人发送信息，也可以调用获取签署地址接口获取签署链接
            logger.info("------------------ 获取签署地址 start -----------------");
            GetFileSignUrl fileSignUrl = SignFile.getFileSignUrl(flowId, accountId);
            fileSignUrl.setOrganizeId(orgId);
            GetFileSignUrlResponse fileSignUrlResp = fileSignUrl.execute();
            shortUrl = fileSignUrlResp.getData().getShortUrl();//响应的签署链接，复制到浏览器访问即可打开签署页面
            logger.info("签署短连接,复制到浏览器打开: "+shortUrl);
            logger.info("------------------ 获取签署地址 end -----------------");

        }catch (DefineException e){
            e.printStackTrace();
            System.out.println(e.getMessage()+e.getCause());
        }
        logger.info("签署正常运行结束");
        return  ResultVOUtils.success(shortUrl);
    }
}
