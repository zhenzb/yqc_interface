package com.youqiancheng.controller.sms;

import com.google.gson.Gson;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.VerificationCodeCheckNumberAbility;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
* @Description:    短信
* @Author:         yutf
* @CreateDate:     2020/3/21 16:31
*/
@RestController
@RequestMapping("/aliSms")
@Api(tags = "公共接口-短信验证")
public class SmsController {

    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private VerificationCodeCheckNumberAbility verificationCodeCheckNumberAbility;

    @Data
    public static class SmsData{
        String code;

        public SmsData(String code) {
            this.code = code;
        }
    }
//
//    /**
//     * 跳转到短信测试页面
//     * @return
//     */
//    @RequestMapping(value = "/toSms",method = RequestMethod.GET)
//    public String toSms(){
//        return "sms";
//    }
//
//    /**
//     * 发送验证码
//     * @param PhoneNumbers 手机号
//     * @return
//     */
//     @GetMapping(value = "/sendSms/{PhoneNumbers}")
//     @ApiOperation(value = "发送验证码" )
//    public String sendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        //随机生成验证码
//        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
//        SmsData smsData = new SmsData(numeral);
//        Gson gson = new Gson();
//        String code = gson.toJson(smsData);
//        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code);
//        session.setAttribute(PhoneNumbers,numeral);
//        session.setMaxInactiveInterval(3 * 60);
//        if(information.equals("ok")){
//            return "成功发送给"+PhoneNumbers+";验证码是"+numeral ;
//        }
//            return "失败了"+PhoneNumbers;
//    }

    /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——身份验证验证码")
    @GetMapping(value = "/idCardCode/sendSms/{PhoneNumbers}")
    public ResultVo idCardCodeSendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.idCardCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.idCardCode.getCode();
        session.setAttribute(key,numeral);
        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证——身份验证验证码")
    @RequestMapping(value = "/idCardCode/verifySms",method = RequestMethod.POST)
    public ResultVo idCardCodeVerifySms(String PhoneNumbers, String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String key=PhoneNumbers+TypeConstant.ShortMessageType.idCardCode.getCode();
        String ycode = (String) session.getAttribute(key);
        if(ycode.equals(code)){
            return ResultVOUtils.success("短信验证成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"短信验证失败");

    }
    /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——登录确认验证码")
    @GetMapping(value = "/loginCode/sendSms/{PhoneNumbers}")
    public ResultVo loginCodeSendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.loginCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginCode.getCode();
        session.setAttribute(key,numeral);
        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证——登录确认验证码")
    @RequestMapping(value = "/loginCode/verifySms",method = RequestMethod.POST)
    public ResultVo loginCodeVerifySms(String PhoneNumbers, String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginCode.getCode();
        String ycode = (String) session.getAttribute(key);
        if(ycode.equals(code)){
            return ResultVOUtils.success("短信验证成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"短信验证失败");

    }

 /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——登录异常验证码")
    @GetMapping(value = "/loginExceptionCode/sendSms/{PhoneNumbers}")
    public ResultVo loginExceptionCodeSendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.loginExceptionCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginExceptionCode.getCode();
        session.setAttribute(key,numeral);
        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证——登录异常验证码")
    @RequestMapping(value = "/loginExceptionCode/verifySms",method = RequestMethod.POST)
    public ResultVo loginExceptionCodeVerifySms(String PhoneNumbers, String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginExceptionCode.getCode();
        String ycode = (String) session.getAttribute(key);
        if(ycode.equals(code)){
            return ResultVOUtils.success("短信验证成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"短信验证失败");

    }

 /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——用户注册验证码")
    @GetMapping(value = "/userRegisterCode/sendSms/{PhoneNumbers}")
    public ResultVo userRegisterCodeSendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.userRegisterCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.userRegisterCode.getCode();
        session.setAttribute(key,numeral);
        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证——用户注册验证码")
    @RequestMapping(value = "/userRegisterCode/verifySms",method = RequestMethod.POST)
    public ResultVo userRegisterCodeVerifySms(String PhoneNumbers, String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String key=PhoneNumbers+TypeConstant.ShortMessageType.userRegisterCode.getCode();
        String ycode = (String) session.getAttribute(key);
        if(ycode.equals(code)){
            return ResultVOUtils.success("短信验证成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"短信验证失败");

    }

 /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——修改密码验证码")
    @GetMapping(value = "/updatePwdCode/sendSms/{PhoneNumbers}")
    public ResultVo updatePwdCodeSendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.updatePwdCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode();
        session.setAttribute(key,numeral);
        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证——修改密码验证码")
    @RequestMapping(value = "/updatePwdCode/verifySms",method = RequestMethod.POST)
    public ResultVo updatePwdCodeVerifySms(String PhoneNumbers, String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode();
        String ycode = (String) session.getAttribute(key);
        if(ycode.equals(code)){
            return ResultVOUtils.success("短信验证成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"短信验证失败");

    }

 /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——登录确认验证码")
    @GetMapping(value = "/changeInfoCode/sendSms/{PhoneNumbers}")
    public ResultVo changeInfoCodeSendSms(@PathVariable String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.changeInfoCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.changeInfoCode.getCode();
        session.setAttribute(key,numeral);
        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证——登录确认验证码")
    @RequestMapping(value = "/changeInfoCode/verifySms",method = RequestMethod.POST)
    public ResultVo changeInfoCodeVerifySms(String PhoneNumbers, String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String key=PhoneNumbers+TypeConstant.ShortMessageType.changeInfoCode.getCode();
        String ycode = (String) session.getAttribute(key);
        if(ycode.equals(code)){
            return ResultVOUtils.success("短信验证成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"短信验证失败");

    }


}

