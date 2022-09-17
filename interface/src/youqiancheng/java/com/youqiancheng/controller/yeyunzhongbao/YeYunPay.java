package com.youqiancheng.controller.yeyunzhongbao;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.controller.alipay.PayController;
import com.youqiancheng.mybatis.dao.F06WithdrawalApplicationDao;
import com.youqiancheng.mybatis.model.A18SysMessageDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@Api(tags = {"椰云众包支付"})
@RequestMapping(value = "/yeYunPay")
public class YeYunPay {

    protected static final Log logger = LogFactory.getLog(PayController.class);

    @Resource
    private F06WithdrawalApplicationDao withdrawalApplicationDao;
    /**
     * 椰云支付回调方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/YeYunNotifyalipay")
    @ResponseBody
    public void payNotifyalipay(HttpServletRequest request){
        logger.info("YeYunNotifyalipay...");
        //获取椰云POST过来反馈信息

        //获取椰云支付的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        String postStr = null;
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
        JSONObject jsonObject = JSONObject.fromObject(postStr);
        //商户号
        String merchantId = jsonObject.getString("merchantId");
        logger.info("order " + merchantId + " yeyun callback begin");

        //椰云交易号
        String orderId = jsonObject.getString("orderId");
        logger.info("orderId " + orderId );

        //交易状态
        String orderStatus = jsonObject.getString("orderStatus");
        logger.info(" :orderStatus = " + orderStatus);

        //交易金额
        String performanceFee = jsonObject.getString("performanceFee");
        logger.info(" :performanceFee = " + performanceFee);

        //总服务费
        String totalServiceFee = jsonObject.getString("totalServiceFee");
        logger.info(" :totalServiceFee = " + totalServiceFee);

        //支付通知消息
        String msg = jsonObject.getString("msg");
        logger.info(" :支付回调结果消息 = " + msg);

        //商户订单号
        String orderNum = jsonObject.getString("orderNum");
        logger.info("orderNum " + orderNum );

        if("0".equals(orderStatus) && "支付成功".equals(msg)){

            F06WithdrawalApplicationDO entity = withdrawalApplicationDao.get(Long.valueOf(orderNum));
            entity.setOrderNo(orderId);
            entity.setStatus(StatusConstant.PayStatus.pay.getCode());
            withdrawalApplicationDao.updateById(entity);
        }else{
            F06WithdrawalApplicationDO entity=new F06WithdrawalApplicationDO();
            entity.setId(Long.valueOf(orderNum));
            entity.setStatus(StatusConstant.PayStatus.fail.getCode());
            entity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            withdrawalApplicationDao.updateById(entity);
        }

    }
}
