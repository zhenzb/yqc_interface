package com.youqiancheng.controller.client;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DateUtil;
import com.handongkeji.util.manager.JwtUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.tencentyun.TLSSigAPIv2;
import com.youqiancheng.form.client.B01UserSaveForm;
import com.youqiancheng.form.client.UserWechatForm;
import com.youqiancheng.initdata.UserEnity;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B11InvitationRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.app.service.B11InvitationRecordAppService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.util.code.CodeUtil;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.app.B01UserAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Api(tags = {"PC端-登录"})
@Slf4j
@RestController
@RequestMapping(value = "pc/login")
public class ClientLoginController {
    @Autowired
    private B01UserAppService b01UserService;
    @Autowired
    private B11InvitationRecordAppService b11InvitationRecordService;
    @Autowired
    private F01ShopClientService f01ShopClientService;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    //联系客服的应用 SDKAppID,可在即时通信 IM 控制台 的应用卡片中获取。
    @Value("${contactCustomerApp.sdkappid}")
    private Long sdkappid;
    //  联系客服密钥信息
    @Value("${contactCustomerApp.key}")
    private  String key;

    @Value("${pics.pic}")
    private String pic;
    @Data
    public static class SmsData{
        String code;

        public SmsData(String code) {
            this.code = code;
        }
    }
    List<String> phoneArray = Arrays.asList("167", "170", "171");
    /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——登录;参数——手机号")
    @GetMapping(value = "/sendSms")
    public ResultVo loginCodeSendSms(String PhoneNumbers, HttpServletRequest request){

        //手机号校验
        ResultVo resultVo = checkSendSmsCodeParams(PhoneNumbers);
        if(null != resultVo){
            return resultVo;
        }
        //随机生成验证码
        JSONObject jsonObject = CodeUtil.buildSmsCode();

        //发送验证码
        String information = sendSmsUtil.aliSendSms(PhoneNumbers,jsonObject.getString("code"), TypeConstant.ShortMessageType.loginCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        sendSmsUtil.saveMap(key,jsonObject.getString("numeral"));

//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);

        if(information.equals("ok")){
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }

    private ResultVo checkSendSmsCodeParams(String PhoneNumbers){
        if(StringUtils.isBlank(PhoneNumbers)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号不能为空");
        }
        String substring = PhoneNumbers.substring(0, 3);
        if(phoneArray.contains(substring)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号为虚拟手机号，不能注册登录");
        }
        return null;
    }

    /**
     * PC商城手机号、验证码登录方法
     * @param PhoneNumbers
     * @param code
     * @return
     */
    @ApiOperation(value = "登录；参数——手机号，验证码")
    @RequestMapping(value = "/loginCode/verifySms",method = RequestMethod.POST)
    public ResultVo loginCodeVerifySms(String PhoneNumbers, String code){
        //手机号校验
        ResultVo resultVo = checkSendSmsCodeParams(PhoneNumbers);
        if(null != resultVo){
            return resultVo;
        }

        if(StringUtils.isBlank(code)){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"验证码不能为空");
        }
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        boolean b = sendSmsUtil.verifyCode(key, code);
        if(b){
            B01UserDO b01UserDO=new B01UserDO();
            Map<String, Object> map = new HashMap<>();
            map.put("mobile",PhoneNumbers);
            List<B01UserDO> list = b01UserService.list(map);
            if(list==null||list.size()==0){
                // return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"用户不存在");
                buildUserDo(PhoneNumbers, b01UserDO);
                int num=b01UserService.insert(b01UserDO);
                if(num <=0){
                    ResultVOUtils.error(ResultEnum.INSERT_FAIL,"用户新增失败");
                }
            }else{
                b01UserDO= list.get(0);
                //判断用户的启用状态
                if( b01UserDO.getStatus()==StatusConstant.disable.getCode()){
                    return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户已经被禁用了");
                }
            }
            B01UserAppVO vo = buildUserAppVO(b01UserDO);
            return ResultVOUtils.success(vo);
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");

    }

    private B01UserAppVO buildUserAppVO(B01UserDO b01UserDO) {
        B01UserAppVO vo=new B01UserAppVO();
        BeanUtils.copyProperties(b01UserDO,vo);
        if(vo.getShopId()!=0){
            F01ShopDO f01ShopDO = f01ShopClientService.get(vo.getShopId());
            if(f01ShopDO!=null){
                vo.setShopType(f01ShopDO.getType());
            }
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("pc_user_id", vo.getId());
        String token = JwtUtils.createToken(claims, SecurityUtils.expiredTime);
        vo.setToken(token);
        vo.setTokenType(TypeConstant.PlatformType.pc.getCode());
        //获取用户的id,来生成UserSig
        String contactCustomer = getContactCustomer(b01UserDO.getId());
        vo.setUserSig(contactCustomer);
        vo.setTengXunImId("youqianchengjin_" +vo.getId());
        //
        UserEnity user=new UserEnity();
        user.setToken(token);
        user.setTime(LocalDateTime.now());
        SecurityUtils.setSession("pc_user_id_"+vo.getId(),user);
        return vo;
    }

    private void buildUserDo(String PhoneNumbers, B01UserDO b01UserDO) {
        b01UserDO.setCreatePerson(PhoneNumbers);
        b01UserDO.setNick(PhoneNumbers);
        b01UserDO.setMobile(PhoneNumbers);
        b01UserDO.setUpdatePerson(PhoneNumbers);
        b01UserDO.setCreateTime(LocalDateTime.now());
        b01UserDO.setUpdateTime(LocalDateTime.now());
        b01UserDO.setRegistTime(LocalDateTime.now());
        b01UserDO.setIsShop(StatusConstant.IsShop.no.getCode());
        b01UserDO.setCurrentLoginTime(LocalDateTime.now());
        b01UserDO.setStatus(StatusConstant.enable.getCode());
        b01UserDO.setIsAuthentication(StatusConstant.isAuthentication.un_authentication.getCode());
        b01UserDO.setAppType(TypeConstant.LoginType.phone.getCode());
        b01UserDO.setCurrentLoginTime(LocalDateTime.now());
        b01UserDO.setSex(TypeConstant.SexType.male.getCode());
        b01UserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        b01UserDO.setPic(pic);
    }

    /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——注册;参数——手机号")
    @GetMapping(value = "/registerCodeSendSms")
    public ResultVo registerCodeSendSms(String PhoneNumbers, HttpServletRequest request){
        //手机号校验
        ResultVo resultVo = checkSendSmsCodeParams(PhoneNumbers);
        if(null != resultVo){
            return resultVo;
        }
        //随机生成验证码
        JSONObject jsonObject = CodeUtil.buildSmsCode();
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, jsonObject.getString("code"), TypeConstant.ShortMessageType.userRegisterCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        sendSmsUtil.saveMap(key,jsonObject.getString("numeral"));
//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证注册
     * @param
     * @return
     */
    @ApiOperation(value = "注册；参数——手机号，验证码")
    @RequestMapping(value = "/registerCode/verifySms",method = RequestMethod.POST)
    public ResultVo registerCodeVerifySms(@RequestBody B01UserSaveForm form){

        String key=form.getMobile()+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        boolean b = sendSmsUtil.verifyCode(key, form.getCode());

        if(b){
            B01UserDO b01UserDO=new B01UserDO();
            Map<String, Object> map = new HashMap<>();
            map.put("mobile",form.getMobile());
            List<B01UserDO> list = b01UserService.list(map);
            if(list==null||list.size()==0){
                buildB01UserDO(form, b01UserDO);
                int num=b01UserService.insert(b01UserDO);
                if(num <=0){
                    ResultVOUtils.error(ResultEnum.INSERT_FAIL,"用户新增失败");
                }
            }else{
                b01UserDO= list.get(0);
                //判断用户的启用状态
                if( b01UserDO.getStatus()==StatusConstant.disable.getCode()){
                    return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户已经被禁用了");
                }
            }
            B01UserAppVO vo = buildB01UserAppVO(b01UserDO);
            return ResultVOUtils.success(vo);
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");

    }

    private B01UserAppVO buildB01UserAppVO(B01UserDO b01UserDO) {
        B01UserAppVO vo=new B01UserAppVO();
        BeanUtils.copyProperties(b01UserDO,vo);
        Map<String, Object> claims = new HashMap<>();
        claims.put("pc_user_id", vo.getId());
        String token = JwtUtils.createToken(claims, 60*60*24*3L);
        vo.setToken(token);
        vo.setTokenType(TypeConstant.PlatformType.pc.getCode());
        //获取用户的id,来生成UserSig
        String contactCustomer = getContactCustomer(b01UserDO.getId());
        vo.setUserSig(contactCustomer);
        vo.setTengXunImId("youqianchengjin_" +vo.getId());
        UserEnity user=new UserEnity();
        user.setToken(token);
        user.setTime(LocalDateTime.now());
        SecurityUtils.setSession("pc_user_id_"+vo.getId(),user);
        return vo;
    }

    private void buildB01UserDO(@RequestBody B01UserSaveForm form, B01UserDO b01UserDO) {
        BeanUtils.copyProperties(form,b01UserDO);
        // return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"用户不存在");
        b01UserDO.setCreatePerson(form.getMobile());
        b01UserDO.setUpdatePerson(form.getMobile());
        b01UserDO.setCreateTime(LocalDateTime.now());
        b01UserDO.setUpdateTime(LocalDateTime.now());
        b01UserDO.setRegistTime(LocalDateTime.now());
        b01UserDO.setIsShop(StatusConstant.IsShop.no.getCode());
        b01UserDO.setCurrentLoginTime(LocalDateTime.now());
        b01UserDO.setStatus(StatusConstant.enable.getCode());
        b01UserDO.setIsAuthentication(StatusConstant.isAuthentication.un_authentication.getCode());
        b01UserDO.setAppType(TypeConstant.LoginType.wechat.getCode());
        b01UserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        b01UserDO.setPic(pic);
        b01UserDO.setSex(TypeConstant.SexType.male.getCode());
    }


    //生成UserSig方法
    public String  getContactCustomer(Long id) {
        //用户的id
        String userId = String.valueOf(id);
        String  tengxunUser =  "youqianchengjin_" +userId;
        //过期时间
        long expire= 60*60*24*7;
        TLSSigAPIv2 api = new TLSSigAPIv2(sdkappid,key);
        System.out.print(api.genSig(tengxunUser, expire));
        return api.genSig(tengxunUser, expire);
    }

    //以下为新增微信登录保存用户信息方法

    /**
     * 保存微信小程序用户授权登录信息，
     * 1、先根据openid查询该用户是否已经授权保存过信息了，如果已经保存了则不在保存
     * 2、如果首次授权则保存用户授权信息
     * @param userWechatForm
     * @return
     */
    @PostMapping("/saveWechatUser")
    public ResultVo saveWechatUser(UserWechatForm userWechatForm){
        Map<String, Object> map = new HashMap<>();
        map.put("wechatOpenid",userWechatForm.getOpenid());
        List<B01UserDO> list = b01UserService.list(map);
        B01UserDO b01UserDO;
        if(list.isEmpty()){
             b01UserDO = buildWechatUserDo(userWechatForm);
            int num=b01UserService.insert(b01UserDO);
            if(num <=0){
                log.info("保存微信用户授权信息失败，openid：{}",userWechatForm.getOpenid());
               return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"用户新增失败");
            }
            log.info("保存微信用户授权信息成功，openid：{}",userWechatForm.getOpenid());
            //如果是被邀请进来的用户增加一条邀请关系
            String invitedUserId = userWechatForm.getInvitedUserId();
            if(invitedUserId !=null && !"".equals(invitedUserId)){
                log.info("被邀请进来小程序用户，邀请者id：{}",invitedUserId);
                B11InvitationRecordDO b11InvitationRecordDO = new B11InvitationRecordDO();
                b11InvitationRecordDO.setUserId(Long.valueOf(invitedUserId));
                b11InvitationRecordDO.setBeUserId(b01UserDO.getId());
                b11InvitationRecordDO.setCreateTime(LocalDateTime.now());
                b11InvitationRecordService.insert(b11InvitationRecordDO);
            }
        }else{
            b01UserDO = list.get(0);
        }
        return ResultVOUtils.success(b01UserDO);
    }

    /**
     * 通过openId查询用户信息
     * @param openId
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResultVo getUserInfoByOpenId(String openId){
        Map<String, Object> map = new HashMap<>();
        map.put("wechatOpenid",openId);
        List<B01UserDO> list = b01UserService.list(map);
        if(list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"无该用户");
        }else{
            return ResultVOUtils.success(list.get(0));
        }
    }


    /**
     * 通过openId更新用户手机号
     * @param openId
     * @return
     */
    @GetMapping("/updateUserInfoByOpenId")
    public ResultVo updateUserInfoByOpenId(String openId,String phone){
        Map<String, Object> map = new HashMap<>();
        map.put("wechatOpenid",openId);
        List<B01UserDO> list = b01UserService.list(map);
        if(list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"无该用户");
        }else{
            B01UserDO b01UserDO = list.get(0);
            b01UserDO.setMobile(phone);
            b01UserService.update(b01UserDO);
            return ResultVOUtils.success();
        }
    }
    /**
     * 构建微信用户信息DAO
     * @param userWechatForm
     * @return B01UserDO
     */
    public B01UserDO buildWechatUserDo(UserWechatForm userWechatForm){
        B01UserDO b01UserDO = new B01UserDO();
        LocalDateTime now = LocalDateTime.now();
        b01UserDO.setWechatOpenid(userWechatForm.getOpenid());
        b01UserDO.setNick(userWechatForm.getNikeName());
        b01UserDO.setPic(userWechatForm.getHeadimgurl());
        b01UserDO.setSex(userWechatForm.getSex());
        b01UserDO.setCreateTime(now);
        b01UserDO.setUpdateTime(now);
        b01UserDO.setDeleteFlag(1);
        return b01UserDO;
    }
}
