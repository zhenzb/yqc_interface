package com.youqiancheng.ability;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DateUtil;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.manager.ResultVOUtils;

import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.util.HttpUtils;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.youqiancheng.controller.wechatpay.weixinpay.sign.SignUtil.buildSign;

@Slf4j
@Component
public class SignacontractAbility {

    protected static final Log logger = LogFactory.getLog(SignacontractAbility.class);

    @Autowired
    private F06WithdrawalApplicationDao f06WithdrawalApplicationDao;
    @Autowired
    private B02UserAccountDao b02UserAccountDao;
    @Autowired
    private B12PromotionAccountDao b12PromotionAccountDao;
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private UserAccountFlowAbility userAccountFlowAbility;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private A13SysVersionDao a13SysVersionDao;

//    测试环境：http://tvendor.cococc001.com
//    正式环境：https://vendor.cococc001.com
    private static final String yeYunUrl = "https://vendor.cococc888.com/realNameSign/signing";
    //private static final String yeYunOrderUrl = "https://vendor.cococc888.com/order/contractSubmit";
    private static final String yeYunOrderUrl = "https://vendor.cococc001.com/order/contractSubmit";
    public JSONObject signContract(Long userId,String userRealName,String idCard,String mobile,String bankCardNum) {

            //获取当前时间
            String nowDateTime = DateUtil.getNowDateTime();
            //组装参数
            Map<String, String> parameters = new HashMap<>();
            parameters.put("merchantId","2021041318332983");
            parameters.put("uid",userId.toString());
            parameters.put("name",userRealName);
            parameters.put("idCard",idCard);
            parameters.put("mobile",mobile);
            parameters.put("bankCardNum",bankCardNum);
            parameters.put("signingTime",nowDateTime);
            parameters.put("signingStatus","1");
            String sign = "";
            try {
                 sign = buildSign(parameters);
            }catch (Exception e){
                e.printStackTrace();
            }
            parameters.put("sign",sign);
            logger.info("sign: "+sign);

            JSONObject jsonObject = JSONObject.fromObject(parameters);
            logger.info("签约请求参数:"+jsonObject);

            //发送http post请求 json参数
            String str = HttpUtils.sendPost(jsonObject, yeYunUrl);
            //请求返回结果转json
            JSONObject JSONResult = JSONObject.fromObject(str);
            logger.info("签约返回结果："+JSONResult.getInt("code")+"========="+JSONResult.getString("msg"));
            return JSONResult;
        }

    public JSONObject signContractNew(B07AuthenticationDO b07AuthenticationDO) {
        //获取当前时间
        String nowDateTime = DateUtil.getNowDateTime();
        //身份证正面照
        String idUrl = convertFileToBase64(b07AuthenticationDO.getUrl());
        //身份证反面
        String idBackUrl = convertFileToBase64(b07AuthenticationDO.getBackUrl());
        //人脸识别照片
        if(b07AuthenticationDO.getFacePhotoUrl()==null || "".equals(b07AuthenticationDO.getFacePhotoUrl())){
            logger.info("提现失败：用户未完成人脸识别");
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"请先在实名认证时完成人脸识别");
        }
        String validityFacePhoto = convertFileToBase64(b07AuthenticationDO.getFacePhotoUrl());

        //签约合同文件
        if(b07AuthenticationDO.getContractUrl()==null || "".equals(b07AuthenticationDO.getContractUrl())){
            logger.info("提现失败：用户未签署任务承揽协议合同");
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"请先在实名认证后完成任务协议承揽协议合同签署");
        }
        String contractFile = convertFileToBase64(b07AuthenticationDO.getContractUrl());

        //组装参数
        Map<String, String> parameters = new HashMap<>();
        parameters.put("merchantId","2021041318332983");
        parameters.put("name",b07AuthenticationDO.getName());
        parameters.put("idCard",b07AuthenticationDO.getCardNo());
        parameters.put("mobile",b07AuthenticationDO.getCreatePerson());
        parameters.put("bankCardNum",b07AuthenticationDO.getBankCardNum());
        parameters.put("signingTime",nowDateTime);
        parameters.put("idPhotoVerificationStatus","1");
        parameters.put("positiveIDPhoto",idUrl);
        parameters.put("negativeIDPhoto",idBackUrl);
        parameters.put("contractFile",validityFacePhoto);
        parameters.put("facePhotoVerificationStatus","1");
        parameters.put("validityFacePhoto",contractFile);
        String sign = "";
        try {
            sign = buildSign(parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
        parameters.put("sign",sign);
        logger.info("sign: "+sign);

        JSONObject jsonObject = JSONObject.fromObject(parameters);
        logger.info("签约请求参数:"+jsonObject);

        //发送http post请求 json参数
        String str = HttpUtils.sendPost(jsonObject, yeYunUrl);
        //请求返回结果转json
        JSONObject JSONResult = JSONObject.fromObject(str);
        logger.info("签约返回结果："+JSONResult.getInt("code")+"========="+JSONResult.getString("msg"));
        return JSONResult;
    }


    public ResultVo yeYunbatchPassRefuse(ShopPassRefuseForm shopPassRefuseForm) {
        F06WithdrawalApplicationDO f06WithdrawalApplicationDO = f06WithdrawalApplicationDao.get(shopPassRefuseForm.getId());
        if(f06WithdrawalApplicationDO.getExamineStatus() ==1) {
            f06WithdrawalApplicationDO.setExamineTime(LocalDateTime.now());
            B02UserAccountDO b02UserAccountDO = b02UserAccountDao.get(f06WithdrawalApplicationDO.getAccountId());

            if (shopPassRefuseForm.getStatus() == 2) {

                List<B07AuthenticationDO> authenticationDO = null;
                if (b02UserAccountDO == null) {
                    B12PromotionAccountDO b12PromotionAccountDO = b12PromotionAccountDao.get(f06WithdrawalApplicationDO.getAccountId());
                    authenticationDO = b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", b12PromotionAccountDO.getUserId()));
                } else {
                    authenticationDO = b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", b02UserAccountDO.getUserId()));
                }
                B07AuthenticationDO b07AuthenticationDO = authenticationDO.get(0);
                ResultVo resultVo = yeYunPassRefuse(f06WithdrawalApplicationDO, b07AuthenticationDO.getName(), b07AuthenticationDO.getBankCardNum(), b07AuthenticationDO.getCardNo(), b07AuthenticationDO.getCreatePerson());
                if(resultVo.getMessage() !="success") {
                     //打款发生异常，退回资金
                   returnBack(shopPassRefuseForm,b02UserAccountDO,f06WithdrawalApplicationDO);
                   f06WithdrawalApplicationDO.setExamineStatus(3);
                   f06WithdrawalApplicationDao.updateById(f06WithdrawalApplicationDO);
                   //发送打款失败短信通知
                    sendSMS(b02UserAccountDO.getUpdatePerson(),"被拒绝",resultVo.getMessage());
               }else{
                   f06WithdrawalApplicationDO.setExamineStatus(shopPassRefuseForm.getStatus());
                   f06WithdrawalApplicationDao.updateById(f06WithdrawalApplicationDO);
               }
                   return resultVo;

               // return yeYunPassRefuse(f06WithdrawalApplicationDO, b07AuthenticationDO.getName(), b07AuthenticationDO.getBankCardNum(), b07AuthenticationDO.getCardNo(), b07AuthenticationDO.getCreatePerson());
            } else {
                //审核不通过资金原路退回账户
                returnBack(shopPassRefuseForm,b02UserAccountDO,f06WithdrawalApplicationDO);
                f06WithdrawalApplicationDO.setExamineStatus(shopPassRefuseForm.getStatus());
                f06WithdrawalApplicationDO.setReason(shopPassRefuseForm.getReason());
                f06WithdrawalApplicationDao.updateById(f06WithdrawalApplicationDO);
                //审核拒绝打款短信通知
                sendSMS(b02UserAccountDO.getUpdatePerson(),"被拒绝",shopPassRefuseForm.getReason());
                return ResultVOUtils.success();
            }
        }else{
           return ResultVOUtils.error(10000,"该订单已审核");
        }

    }

    private void sendSMS(String mobile,String type,String reason){
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("name",mobile);
        json.put("type",type);
        json.put("reason",reason);
        String information = sendSmsUtil.aliSendSmsTwo(mobile, json.toJSONString(), TypeConstant.ShortMessageType.withdrawalNoticeCode.getCode());
        if(information.equals("ok")){
            logger.info("审核短信已发送》》》》》》》");
        }
    }
    private void returnBack(ShopPassRefuseForm shopPassRefuseForm, B02UserAccountDO b02UserAccountDO,  F06WithdrawalApplicationDO f06WithdrawalApplicationDO){
        //审核不通过资金原路退回账户 1：店铺 2：红包 3：可视化
        if (shopPassRefuseForm.getType() == 2) {
            BigDecimal bigDecimal = DecimalUtil.addBigMall(b02UserAccountDO.getWithdrawalBalance(), f06WithdrawalApplicationDO.getActualWithdrawalMoney(), 2, 0);
            b02UserAccountDO.setWithdrawalBalance(bigDecimal);
            b02UserAccountDao.updateById(b02UserAccountDO);
        } else {
            B12PromotionAccountDO b12PromotionAccountDO = b12PromotionAccountDao.get(f06WithdrawalApplicationDO.getAccountId());
            BigDecimal bigDecimal = DecimalUtil.addBigMall(b12PromotionAccountDO.getAccountBalance(), f06WithdrawalApplicationDO.getActualWithdrawalMoney(), 2, 0);
            b12PromotionAccountDO.setAccountBalance(bigDecimal);
            BigDecimal revenue = DecimalUtil.subTionBigMal(b12PromotionAccountDO.getAccumulatedRevenue(), f06WithdrawalApplicationDO.getActualWithdrawalMoney(), 2, 0);
            b12PromotionAccountDO.setAccumulatedRevenue(revenue);
            b12PromotionAccountDao.updateById(b12PromotionAccountDO);
        }
        //增加用户账号流水
        userAccountFlowAbility.addUserAccountFlow(b02UserAccountDO,f06WithdrawalApplicationDO.getActualWithdrawalMoney(),TypeConstant.UserAccountType.refund_incone.getCode());
    }





    public ResultVo yeYunPassRefuse(F06WithdrawalApplicationDO f06WithdrawalApplicationDO,String name,String banckCardNum,String cardNo,String mobile){
        A13SysVersionDO a13SysVersionDO = a13SysVersionDao.get(10L);
        //获取当前时间
        String nowDateTime = DateUtil.getStringNowDateTime();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("merchantId","2021041318332983");
        parameters.put("orderNum",f06WithdrawalApplicationDO.getId().toString());
        if(a13SysVersionDO == null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"任务ID不存在");
        }else{
            parameters.put("taskNumber",a13SysVersionDO.getVersionExplain());
        }
        parameters.put("name",name);
        parameters.put("bankCardNum",banckCardNum);
        parameters.put("idCard",cardNo);
        parameters.put("mobile",mobile);
        parameters.put("requestTime",nowDateTime);
        parameters.put("expense",f06WithdrawalApplicationDO.getActualWithdrawalMoney().toString());
        parameters.put("remark","有钱城提现");
        String sign = "";
        try {
            sign = buildSign(parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
        parameters.put("sign",sign);
        logger.info("sign: "+sign);

        JSONObject jsonObject = JSONObject.fromObject(parameters);
        logger.info("jsonObject:"+jsonObject);

        String s = HttpUtils.sendPost(jsonObject, yeYunOrderUrl);

        JSONObject JSONResult = JSONObject.fromObject(s);
        System.out.println(JSONResult.getInt("code")+"------------"+JSONResult.getString("msg"));
        if(JSONResult.getInt("code")==1001){
            return ResultVOUtils.success();
        }else{
            //提现失败退还用户所提现的工资
            B02UserAccountDO b02UserAccountDO = b02UserAccountDao.get(f06WithdrawalApplicationDO.getAccountId());
            BigDecimal withdrawalBalance = b02UserAccountDO.getWithdrawalBalance();
            BigDecimal actualWithdrawalMoney = f06WithdrawalApplicationDO.getActualWithdrawalMoney();
            b02UserAccountDO.setWithdrawalBalance(withdrawalBalance.add(actualWithdrawalMoney));
            b02UserAccountDao.updateById(b02UserAccountDO);
            logger.info("用户提现失败，提现金额已退还到账号》》》》》");
        }
        return ResultVOUtils.error(JSONResult.getInt("code"),JSONResult.getString("msg"));
    }

    public static void main(String[] args) {
        //椰云签约测试
        //yeYunQianYueTest();
        //椰云下单测试
        yeYunOrderTest();
    }

    private static void yeYunOrderTest() {
        String nowDateTime = DateUtil.getStringNowDateTime();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("merchantId","2021041318332983");
        parameters.put("orderNum","202205142104");
        parameters.put("taskNumber","RW20220825224201833857");//测试环境：RW20220512124023656589  正式环境：RW20220508171912459288
        parameters.put("name","甄卓彬");
        parameters.put("bankCardNum","6212260200107585248");
        parameters.put("idCard","130125199002187036");
        parameters.put("mobile","13520289150");
        parameters.put("requestTime",nowDateTime);
        parameters.put("expense","2500");
        parameters.put("remark","有钱城提现");
        String sign = "";
        try {
            sign = buildSign(parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
        parameters.put("sign",sign);
        logger.info("sign: "+sign);

        JSONObject jsonObject = JSONObject.fromObject(parameters);
        logger.info("jsonObject:"+jsonObject);

        String s = HttpUtils.sendPost(jsonObject, yeYunOrderUrl);

        JSONObject JSONResult = JSONObject.fromObject(s);
        System.out.println(JSONResult.getInt("code")+"------------"+JSONResult.getString("msg"));
    }

    private static void yeYunQianYueTest() {
        //获取当前时间
        String nowDateTime = DateUtil.getNowDateTime();
        //身份证正面照
        String id1 = convertFileToBase64("https://p.youqiancheng.vip/picture/ee56e17f9c374774b7631602bc5bd0db微信图片_20220515172608.jpg");
        //身份证反面
        String id2 = convertFileToBase64("https://p.youqiancheng.vip/picture/59496593bea447c0aa9d8504e0544a42微信图片_20220515172615.jpg");
        //人脸识别照片
        String validityFacePhoto = convertFileToBase64("https://p.youqiancheng.vip/picture/d15de93d43dd407e916e6959fdc8eb89微信图片_20220515172910.jpg");
        //签约合同文件
        String contractFile = convertFileToBase64("https://p.youqiancheng.vip/picture/1fd1a7aa1dd349e4b41e896356797c3c《任务承揽协议》-灵工方VS椰云享.pdf");
        //组装参数
        Map<String, String> parameters = new HashMap<>();
        parameters.put("IDPhotoVerificationStatus","1");
        parameters.put("bankCardNum","6217994730027934490");
        parameters.put("contractFile","data:application/pdf;base64,"+contractFile);
        parameters.put("facePhotoVerificationStatus","1");
        parameters.put("idCard","372831197103270915");
        parameters.put("merchantId","2021041318332983"); //测试环境：2022041411045414 ,正式环境：2021041318332983
        parameters.put("mobile","13854962880");
        parameters.put("name","闫景刚");
        parameters.put("negativeIDPhoto","data:image/jpg;base64,"+id2);
        parameters.put("positiveIDPhoto","data:image/jpg;base64,"+id1);
        parameters.put("signingTime",nowDateTime);
        parameters.put("validityFacePhoto","data:image/jpg;base64,"+validityFacePhoto);
        String sign = "";
        try {
            sign = buildSign(parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
        parameters.put("sign",sign);
        logger.info("sign: "+sign);

        JSONObject jsonObject = JSONObject.fromObject(parameters);
        logger.info("签约请求参数:"+jsonObject);
        //http://tvendor.cococc001.com/realNameSign/signing  测试环境
        //https://vendor.cococc001.com  正式环境
        //http://tvendor.cococc001.com  测试环境
        String yeYunUrl_test = "https://vendor.cococc001.com/signature/elementSignature";
        //发送http post请求 json参数
        String str = HttpUtils.sendPost(jsonObject, yeYunUrl_test);
        //请求返回结果转json
        JSONObject JSONResult = JSONObject.fromObject(str);
        logger.info("签约返回结果："+JSONResult.getInt("code")+"========="+JSONResult.getString("msg"));
        //return JSONResult;
    }

    public static String convertFileToBase64(String imgURL) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "fail";//连接失败/链接失效/图片不存在
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while ((len = inStream.read(data)) != -1) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outPut.toByteArray());
    }
}
