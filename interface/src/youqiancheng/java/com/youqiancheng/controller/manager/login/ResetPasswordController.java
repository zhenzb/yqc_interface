package com.youqiancheng.controller.manager.login;

import com.google.gson.Gson;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.app.AppLoginController;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.manager.service.system.A01AdminService;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsYXN0TG9naW5UaW1lIjoxNTg3MTc1MTM4Njg0LCJhZG1pbl9pZCI6MTksImV4cCI6MTU4NzIxODMzOX0.OC9U7WqEowNmnqBgEXuGVys3OCXix_RquT6sacs-ga8
 　* @author shalongteng
 　* @date 2020/3/28 15:15
   SKU ：StockKeeping Unit的缩写，直译过来就是存货单元。
 　*/
@RestController
@RequestMapping("reset")
@Api(tags = "总管理后台-忘记密码")
public class ResetPasswordController {
    @Resource
    private A01AdminService a01AdminService;


    @Autowired
    private B01UserAppService b01UserService;
    @Autowired
    private SendSmsUtil sendSmsUtil;


    /**
    　* @Description: 发送验证码——总管理平台忘记密码验证码
    　* @author shalongteng
    　* @date 2020/4/23 9:16
    　*/
    @ApiOperation(value = "发送验证码")
    @GetMapping(value = "/sendSms")
    public ResultVo loginCodeSendSms(String PhoneNumbers, HttpServletRequest request){
        //校验手机号，是否是管理员
        boolean res =  a01AdminService.verifyPhoneNumbers(PhoneNumbers);
        if(!res){
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"不存在该管理员，请联系超级管理员");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        AppLoginController.SmsData smsData = new AppLoginController.SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.updatePwdCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        sendSmsUtil.saveMap(key,numeral);
//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);//3 分钟
        if(information.equals("ok")){
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
}


    /**
    　* @Description:设置新密码
    　* @author shalongteng
    　* @date 2020/4/23 9:19
    　*/
    @ApiOperation(value = "验证码是否正确")
    @RequestMapping(value = "/checkCode",method = RequestMethod.POST)
    public ResultVo checkCode(String PhoneNumbers, String code, HttpServletRequest request){
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        boolean b = sendSmsUtil.verifyCode(key, code);
        if(b){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");

    }
    /**
    　* @Description:设置新密码
    　* @author shalongteng
    　* @date 2020/4/23 9:19
    　*/
    @ApiOperation(value = "设置新密码")
    @RequestMapping(value = "/setNewPassword",method = RequestMethod.POST)
    public ResultVo setNewPassword(String PhoneNumbers, String password){
        boolean b = a01AdminService.setNewPassword(PhoneNumbers,password);
        if(b){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"设置新密码失败");
    }
}
