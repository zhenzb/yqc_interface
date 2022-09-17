package com.youqiancheng.controller.eqianbao;

import cn.tign.hz.comm.HttpHelper;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.exception.DefineException;
import cn.tign.hz.factory.base.SignFile;
import cn.tign.hz.factory.response.DownDocumentsResponse;
import cn.tign.hz.factory.signfile.documents.DownDocuments;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.DateUtil;
import com.handongkeji.util.OSSClientUtil;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.service.client.service.B07AuthenticationClientService;
import com.youqiancheng.util.R;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ENotifyController
 * @Description step2：用户页面上点击实名认证后E签宝异步通知调用该接口，
 * 接口逻辑为收到E签宝的用户实名认证后异步通知后，异步主动调用查询认证接口获取用户
 * 认证的信息（包括：姓名、身份证号，银行卡号、刷脸时的随机照片）进行存储本地
 * @Author zzb
 * @Date 2022/5/3 11:55
 * @Version 1.0
 **/
@RestController
@RequestMapping("eNotify")
public class ENotifyController {
    protected static final Log logger = LogFactory.getLog(ENotifyController.class);

    @Resource
    B07AuthenticationClientService b07AuthenticationClientService;

    //认证刷脸回调
    @RequestMapping("/psnWebVerify")
    public void psnWebVerify(HttpServletRequest request){
        logger.info("psnWebVerify...");
        String postStr = null;
        postStr = getFormatRequest(request, postStr);
        JSONObject jsonObject = JSONObject.fromObject(postStr);
        String flowId = jsonObject.getString("flowId");
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            //查询认证详情
                            queryVerifyDetail(flowId);
                        }
                    }
            ).start();
    }

    //合同签署完成回调
    @RequestMapping("/contractVerify")
    public void contractVerify(HttpServletRequest request){
        logger.info("contractVerify...");
        String postStr = null;
        postStr = getFormatRequest(request, postStr);
        JSONObject jsonObject = JSONObject.fromObject(postStr);
        String action = jsonObject.getString("action");
        String flowId = jsonObject.getString("flowId");
        if("SIGN_FLOW_FINISH".equals(action)){
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            getDownloadUrl(flowId);
                        }
                    }
            ).start();
        }else {
            logger.info("非合同结束回调");
        }
    }

    private void getDownloadUrl(String flowId) {
        try {
            //签署结束调用E签宝下载合同接口
            logger.info("------------------ 签署文档下载 start -----------------");
            DownDocuments downDocuments = SignFile.downDocuments(flowId);
            DownDocumentsResponse execute = downDocuments.execute();
            cn.tign.hz.factory.response.other.Docs docs1 = execute.getData().getDocs();
            cn.tign.hz.factory.response.other.Doc doc = docs1.get(0);
            String fileUrl = doc.getFileUrl();
            String fileId = doc.getFileId();
            logger.info("文档下载地址："+fileUrl);
            logger.info("------------------ 签署文档下载 end -----------------");
            boolean b = saveContract(fileUrl,fileId,flowId);
            if(b){
                logger.info("签署合同保存成功");
            }else{
                logger.info("签署合同保存失败");
            }
        } catch (DefineException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        ENotifyController eNotifyController = new ENotifyController();
//        eNotifyController.saveContract("https://esignoss.esign.cn/1111563786/f509787b-095f-4f48-98a5-de807c9acd91/%E3%80%8A%E4%BB%BB%E5%8A%A1%E6%89%BF%E6%8F%BD%E5%8D%8F%E8%AE%AE%E3%80%8B-%E7%81%B5%E5%B7%A5%E6%96%B9VS%E6%A4%B0%E4%BA%91%E4%BA%AB.pdf?Expires=1651999636&OSSAccessKeyId=LTAI4G23YViiKnxTC28ygQzF&Signature=zDJLbWkcwCnT1lenyJGfx3t4FRQ%3D","cswd.pdf","123");
//    }
    /**
     * 保存签署完成的合同
     * @param downloadUrl
     */
    private boolean saveContract(String downloadUrl,String fileId,String flowId){
        logger.info("下载链接开始时间 = " + DateUtil.getStringNowDateTime());
        boolean ret = false;
        Integer timeout = 1000000;

        try {
            logger.info("文件下载操作");
            // 构造URL
            URL url = new URL(downloadUrl);
            // 打开连接
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            if (timeout != null) {
                con.setConnectTimeout(timeout);
                con.setReadTimeout(timeout);
            }
            con.connect();
            int contentLength = con.getContentLength();
            logger.info("打印文件的长度" + contentLength);
            // 输入流
            InputStream inputStream = con.getInputStream();
            OSSClientUtil ossClientUtil = new OSSClientUtil();
            String fileName = "《任务承揽协议》-灵工方VS椰云享.pdf";
            Map<String, Object> map = ossClientUtil.uploadFile(inputStream, fileName);
            if (map == null)
            {
                throw new Exception("返回结果map为null");
            }
            String contractUrl = (String)map.get("url");
            logger.info("用户完成签约yqcContractUrl:"+contractUrl);
            Map<String,Object> userAuthenticationMap = new HashMap<>();
            userAuthenticationMap.put("flowId",flowId);
            List<B07AuthenticationDO> list = b07AuthenticationClientService.list(userAuthenticationMap);
            if(list.isEmpty()){
                throw new JsonException(Constants.$Null, "用户不存在");
            }
            B07AuthenticationDO b07AuthenticationDO = list.get(0);
            b07AuthenticationDO.setContractUrl(contractUrl);
            b07AuthenticationDO.setStatus(StatusConstant.ExamineStatus.adopt.getCode());
            //更新用户合同
            b07AuthenticationClientService.update(b07AuthenticationDO);
            // 完毕，关闭所有链接
            inputStream.close();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;
        }finally {
            logger.info("下载链接结束时间 = " + DateUtil.getStringNowDateTime());
            return ret;
        }
    }

    @RequestMapping("/testUpload")
    public String testUpload(){
        logger.info("下载链接开始时间 = " + DateUtil.getStringNowDateTime());
        String ret = "";
        Integer timeout = 1000000;
        String downloadUrl = "https://oss.esign.cn/1111563786/8853e144-efbe-4992-b05c-ca42a00868c1/%E3%80%8A%E4%BB%BB%E5%8A%A1%E6%89%BF%E6%8F%BD%E5%8D%8F%E8%AE%AE%E3%80%8B-%E7%81%B5%E5%B7%A5%E6%96%B9VS%E6%A4%B0%E4%BA%91%E4%BA%AB.pdf?Expires=1653580537&OSSAccessKeyId=LTAI4G5MrNNRU1tH6qtY4Moi&Signature=tEUbqjBVz%2BOMcgyb%2B15d9Q9odwY%3D";
        try {
            logger.info("文件下载操作");
            // 构造URL
            URL url = new URL(downloadUrl);
            // 打开连接
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            if (timeout != null) {
                con.setConnectTimeout(timeout);
                con.setReadTimeout(timeout);
            }
            con.connect();
            int contentLength = con.getContentLength();
            logger.info("打印文件的长度" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();

            OSSClientUtil ossClientUtil = new OSSClientUtil();
            String fileId = "《任务承揽协议》-灵工方VS椰云享";
            String fileName = fileId+".pdf";
            Map<String, Object> map = ossClientUtil.uploadFile(is, fileName);
            if (map == null)
            {
                throw new Exception("返回结果map为null");
            }
            String contractUrl = (String)map.get("url");
            logger.info("用户完成签约yqcContractUrl:"+contractUrl);
            // 完毕，关闭所有链接
            is.close();
            ret = contractUrl;
        } catch (IOException e) {
            e.printStackTrace();
            ret = "";
        }finally {
            logger.info("下载链接结束时间 = " + DateUtil.getStringNowDateTime());
            return ret;
        }
    }

    //主动查询认证结果
    private void queryVerifyDetail(String flowId){
        //String flowId = "2295318941125839270";
        String url = "/v2/identity/auth/api/common/"+flowId+"/detail";
        try {
            Map map = HttpHelper.doCommHttp(RequestType.GET, url, flowId);
            String resCtx = (String)map.get("resCtx");//响应体
            int status = (int) map.get("status");//响应状态码
            if(status==200){
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resCtx);
                com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
                com.alibaba.fastjson.JSONObject indivInfo = data.getJSONObject("indivInfo");
                String accountId = indivInfo.getString("accountId");
                //姓名
                String name = indivInfo.getString("name");
                //证件号
                String certNo = indivInfo.getString("certNo");
                //证件类型 INDIVIDUAL_CH_IDCARD  中国大陆居民身份证
                //INDIVIDUAL_CH_TWCARD  台湾来往大陆通行证
                //INDIVIDUAL_CH_HONGKONG_MACAO  港澳来往大陆通行证
                //INDIVIDUAL_PASSPORT  护照
                String certType = indivInfo.getString("certType");
                //手机号
                String mobileNo = indivInfo.getString("mobileNo");
                //银行卡
                String bankCardNo = indivInfo.getString("bankCardNo");
                //刷脸时的随机照片 base64编码照片图片数据
                String facePhotoUrl = indivInfo.getString("facePhotoUrl");
                logger.info("用户刷脸照片："+facePhotoUrl);
                //one 查询实名认证用户的 accountId
                int num = updateAuthentication(accountId, name, certNo, mobileNo, bankCardNo,facePhotoUrl,certType);
                if(num ==1){
                    logger.info("用户实名认证存储成功");
                }else{
                    logger.error("用户实名认证失败");
                }
            }
        } catch (DefineException e) {
            e.printStackTrace();
        }
    }

    private int updateAuthentication(String accountId, String name, String certNo, String mobileNo,
                                     String bankCardNo,String facePhotoUrl,String certType) {
        Map<String,Object> userAuthenticationMap = new HashMap<>();
        userAuthenticationMap.put("EAccountId",accountId);
        List<B07AuthenticationDO> list = b07AuthenticationClientService.list(userAuthenticationMap);
        if(list.isEmpty()){
            throw new JsonException(Constants.$Null, "用户不存在");
        }
        B07AuthenticationDO b07AuthenticationDO = list.get(0);
        b07AuthenticationDO.setUpdateTime(LocalDateTime.now());
        b07AuthenticationDO.setUpdatePerson(mobileNo);
        b07AuthenticationDO.setBankCardNum(bankCardNo);
        b07AuthenticationDO.setCertType(certType);
        b07AuthenticationDO.setName(name);
        b07AuthenticationDO.setCardNo(certNo);
        b07AuthenticationDO.setFacePhotoUrl(facePhotoUrl);
        return b07AuthenticationClientService.update(b07AuthenticationDO);
    }

    private String getFormatRequest(HttpServletRequest request, String postStr) {
        try {
            InputStream sin = new BufferedInputStream(request.getInputStream());
            ByteArrayOutputStream sout = new ByteArrayOutputStream();
            int len = 0;
            while ((len = sin.read()) != -1) {
                sout.write(len);
            }
            byte[] temp = sout.toByteArray();
            postStr = new String(temp, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return postStr;
    }
}
