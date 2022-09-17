package com.youqiancheng.controller.app;

import com.google.gson.Gson;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.redis.CacheUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.JwtUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.tencentyun.TLSSigAPIv2;
import com.youqiancheng.ability.VerificationCodeCheckNumberAbility;
import com.youqiancheng.form.app.B01UserSaveForm;
import com.youqiancheng.form.app.UserInfoForm;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B11InvitationRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.app.service.B11InvitationRecordAppService;
import com.youqiancheng.service.app.service.F01ShopAppService;
import com.youqiancheng.util.AppleUtil;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.app.B01UserAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Api(tags = {"手机端-登录"})
@RestController
@RequestMapping(value = "app/login")
public class AppLoginController {

    @Autowired
    private B01UserAppService b01UserService;
    @Autowired
    private F01ShopAppService f01ShopAppService;
    @Autowired
    private B11InvitationRecordAppService b11InvitationRecordService;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private VerificationCodeCheckNumberAbility verificationCodeCheckNumberAbility;
    @Resource
    private AppleUtil appleUtil;
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
    @ApiOperation(value = "发送验证码——注册;参数——手机号")
    @GetMapping(value = "/registerCodeSendSms")
    public ResultVo registerCodeSendSms(String PhoneNumbers, HttpServletRequest request){
        if(StringUtils.isBlank(PhoneNumbers)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号不能为空");
        }
        String substring = PhoneNumbers.substring(0, 3);
        if(phoneArray.contains(substring)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号为虚拟手机号，不能注册登录");
        }
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
        String key=PhoneNumbers+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.app.getCode();
        sendSmsUtil.saveMap(key,numeral);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
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
        String substring = form.getMobile().substring(0, 3);
        if(phoneArray.contains(substring)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号为虚拟手机号，不能注册登录");
        }
        String key=form.getMobile()+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.app.getCode();
        boolean b = sendSmsUtil.verifyCode(key, form.getCode());
        //if(b){假如验证码不相符就不会走if里,所以 用true可以
            B01UserDO b01UserDO=new B01UserDO();
            if(b){
            //查询用户是否存在对应手机号信息,并且这个用户假如已经被删除了就不走着了.
            QueryMap map =new QueryMap(StatusConstant.CreatFlag.delete.getCode());
            map.put("mobile",form.getMobile());
            List<B01UserDO> list = b01UserService.list(map);
            //如果存在则根据appType类型更新对应的账号（微信或者苹果）
            if(!CollectionUtils.isEmpty(list)) {
                 b01UserDO = list.get(0);
                 if(form.getAppType()==2){
                     if(!StringUtils.isBlank(b01UserDO.getWechatOpenid())){
                         throw new JsonException(ResultEnum.DATA_NOT_EXIST,"该手机号已经与微信账号绑定");
                     }else{
                         //假如这次这个手机号没有绑定微信号,就把微信号给这次的手机号绑定上
                         b01UserDO.setWechatOpenid(form.getWechatOpenid());
                         b01UserDO.setAppType(form.getAppType());
                         b01UserService.update(b01UserDO);
                     }
                 }else{
                     if(!StringUtils.isBlank(b01UserDO.getAppleId())){
                         throw new JsonException(ResultEnum.DATA_NOT_EXIST,"该手机号已经与苹果账号绑定");
                     }else{
                         b01UserDO.setAppleId(form.getAppleId());
                         b01UserDO.setAppType(form.getAppType());
                         b01UserService.update(b01UserDO);
                     }
                 }
            }else{//否则进行新账号注册
                BeanUtils.copyProperties(form,b01UserDO);
                b01UserDO.setCreatePerson(form.getMobile());
                b01UserDO.setUpdatePerson(form.getMobile());
                b01UserDO.setCreateTime(LocalDateTime.now());
                b01UserDO.setUpdateTime(LocalDateTime.now());
                b01UserDO.setRegistTime(LocalDateTime.now());
                b01UserDO.setIsShop(StatusConstant.IsShop.no.getCode());
                b01UserDO.setCurrentLoginTime(LocalDateTime.now());
                b01UserDO.setStatus(StatusConstant.enable.getCode());
                b01UserDO.setIsAuthentication(StatusConstant.isAuthentication.un_authentication.getCode());
                b01UserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                b01UserDO.setPic(pic);
                b01UserDO.setSex(TypeConstant.SexType.male.getCode());
                int num=b01UserService.insert(b01UserDO);
                if(num <=0){
                    ResultVOUtils.error(ResultEnum.INSERT_FAIL,"用户新增失败");
                }
            }

            B01UserAppVO vo=new B01UserAppVO();
            BeanUtils.copyProperties(b01UserDO,vo);
            Map<String, Object> claims = new HashMap<>();
            claims.put("app_user_id", vo.getId());
            String token = JwtUtils.createToken(claims, null);//60*60*24*3L
            vo.setToken(token);
            vo.setTokenType(TypeConstant.PlatformType.app.getCode());
            //保存token 用于验证异地登录
            CacheUtils.set(vo.getId()+"token", token);
            //获取用户的id,来生成UserSig
            String contactCustomer = getContactCustomer(b01UserDO.getId());
            vo.setUserSig(contactCustomer);
            vo.setTengXunImId("youqianchengjin_"+b01UserDO.getId());
            return ResultVOUtils.success(vo);
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");

    }

  /*  @ApiOperation(value = "保存用户信息——参数用户实体")
    @PostMapping("/saveUser")
    ResultVo saveNewUser(@RequestBody B01UserSaveForm dto ) {

        B01UserDO b01User=new B01UserDO();
        BeanUtils.copyProperties(dto,b01User);
        QueryMap map =new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("mobile",dto.getMobile());
        List<B01UserDO> list = b01UserService.list(map);
        if(CollectionUtils.isEmpty(list)) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "该手机号已经注册");
        }
        b01User.setCreatePerson(dto.getMobile());
        b01User.setUpdatePerson(dto.getMobile());
        b01User.setCreateTime(LocalDateTime.now());
        b01User.setUpdateTime(LocalDateTime.now());
        b01User.setRegistTime(LocalDateTime.now());
        b01User.setIsShop(StatusConstant.IsShop.no.getCode());
        b01User.setCurrentLoginTime(LocalDateTime.now());
        b01User.setStatus(StatusConstant.enable.getCode());
        b01User.setIsAuthentication(StatusConstant.isAuthentication.un_authentication.getCode());
        b01User.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        b01User.setAppType(TypeConstant.LoginType.wechat.getCode());
        b01User.setSex(dto.getSex() ==0 ?TypeConstant.SexType.unknown.getCode():dto.getSex());
        int num=b01UserService.insert(b01User);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"插入用户信息失败");
        }
        return ResultVOUtils.success(num);
    }*/

    /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——登录;参数——手机号")
    @GetMapping(value = "/sendSms")
    public ResultVo loginCodeSendSms(String PhoneNumbers){
        if(StringUtils.isBlank(PhoneNumbers)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号不能为空");
        }
        String substring = PhoneNumbers.substring(0, 3);
        if(phoneArray.contains(substring)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号为虚拟手机号，不能注册登录");
        }
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
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginCode.getCode()+TypeConstant.PlatformType.app.getCode();
        sendSmsUtil.saveMap(key,numeral);
        if(information.equals("ok")){
            verificationCodeCheckNumberAbility.setCacheCode(PhoneNumbers);
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * @api {POST} /app/login/loginCode/verifySms 002用户手机号验证码登录接口
     * @apiGroup 003用户邀请模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 用户手机号验证码登录接口
     * @apiParam {String} PhoneNumbers 手机号[必填]
     * @apiParam {String} code 验证码[必填]
     * @apiParam {Long} parentUserId 邀请者ID[非必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "id": 570,
     *         "nick": "17520289155",
     *         "mobile": "17520289155",
     *         "pic": "http://client.youqiancheng.vip/files/100/78/1596792278791.png",
     *         "registTime": "2020-12-08 20:45:38",
     *         "sex": 1,
     *         "birthday": null,
     *         "wechatOpenid": null,
     *         "currentLoginTime": "2020-12-08 20:45:38",
     *         "appType": 1,
     *         "isShop": 1,
     *         "shopId": 0,
     *         "shopName": null,
     *         "status": 1,
     *         "createPerson": "17520289155",
     *         "createTime": "2020-12-08 20:45:38",
     *         "updatePerson": "17520289155",
     *         "updateTime": "2020-12-08 20:45:38",
     *         "deleteFlag": 1,
     *         "isAuthentication": 1,
     *         "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmbGciOjE2MDc0MzE3MTgzNzYsImFwcF91c2VyX2lkIjo1NzB9.qTpJ0dBMsUt-f7IhakZTHJjyj_pFmqn9xmPoR9hru0I",
     *         "tokenType": "APP",
     *         "shopType": 0,
     *         "userSig": "eJw9jl0LgjAYhf-Lbgvdmx9zQldREKUEeh*jTX0zN1MTJfrviUaX5zw8h-Mm6Tmx1FBjo0joUzegdD13vWpISDYWJUtuZSnqGiUJwaXU8YA7sBCUSneY4SyM5vVEoW*F0vkd9dVj-wHMJ96mh6jPdqbxexaj4Da7VGXRRomIk31QHQf7xM24ekButj*xw2r6Bj5lrgMM*OcLLiI2Xw__",
     *         "tengXunImId": "youqianchengjin_570"
     *     }
     * }
     */
    @ApiOperation(value = "登录：参数——手机号，验证码")
    @RequestMapping(value = "/loginCode/verifySms",method = RequestMethod.POST)
    public ResultVo<B01UserAppVO> loginCodeVerifySms(String PhoneNumbers, String code,String parentUserId){
        if(StringUtils.isBlank(PhoneNumbers)){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"手机号不能为空");
        }
        if(StringUtils.isBlank(code)){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"验证码不能为空");
        }
        String substring = PhoneNumbers.substring(0, 3);
        if(phoneArray.contains(substring)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号为虚拟手机号，不能注册登录");
        }
        String key=PhoneNumbers+TypeConstant.ShortMessageType.loginCode.getCode()+TypeConstant.PlatformType.app.getCode();
        boolean b = sendSmsUtil.verifyCode(key, code);
        //if(b){
        if(b){
            B01UserDO b01UserDO=new B01UserDO();
            Map<String, Object> map = new HashMap<>();
            map.put("mobile",PhoneNumbers);
            List<B01UserDO> list = b01UserService.list(map);
            if(list==null||list.size()==0){
                // 登录成功，插入一个用户
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
                int num=b01UserService.insert(b01UserDO);
                if(num <=0){
                    ResultVOUtils.error(ResultEnum.INSERT_FAIL,"用户新增失败");
                }else{
                    //如果是被邀请进来的用户增加一条邀请关系
                    if(parentUserId !=null && !"".equals(parentUserId)){
                        B11InvitationRecordDO b11InvitationRecordDO = new B11InvitationRecordDO();
                        b11InvitationRecordDO.setUserId(Long.valueOf(parentUserId));
                        b11InvitationRecordDO.setBeUserId(b01UserDO.getId());
                        b11InvitationRecordDO.setCreateTime(LocalDateTime.now());
                        b11InvitationRecordService.insert(b11InvitationRecordDO);
                    }
                }
            }else{
                b01UserDO= list.get(0);
                //判断用户的启用状态
                if( b01UserDO.getStatus()==StatusConstant.disable.getCode()){
                    return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户已经被禁用了");
                }

            }
            B01UserAppVO vo=new B01UserAppVO();
            BeanUtils.copyProperties(b01UserDO,vo);

            if(vo.getShopId()!=0){
                F01ShopDO f01ShopDO = f01ShopAppService.get(vo.getShopId());
                if(f01ShopDO!=null){
                    vo.setShopType(f01ShopDO.getType());
                }
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("app_user_id", vo.getId());
            String token = JwtUtils.createToken(claims, null);
            vo.setToken(token);
            vo.setTokenType(TypeConstant.PlatformType.app.getCode());
            //保存token 用于验证异地登录
            CacheUtils.set(vo.getId()+"token", token);
            //获取用户的id,来生成UserSig
            String contactCustomer = getContactCustomer(b01UserDO.getId());
            vo.setUserSig(contactCustomer);
            vo.setTengXunImId("youqianchengjin_"+b01UserDO.getId());
            return ResultVOUtils.success(vo);
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");

    }


//    /**
//     * 验证注册
//     * @param
//     * @return
//     */
//    @ApiOperation(value = "苹果注册；参数——手机号，验证码")
//    @RequestMapping(value = "/registerUserByApple",method = RequestMethod.POST)
//    public ResultVo registerUserByApple(@RequestBody B01UserSaveForm form){
//        String key=form.getMobile()+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.pc.getCode();
//        boolean b = sendSmsUtil.verifyCode(key, form.getCode());
//        if(b) {
//            QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//            map.put("mobile", form.getMobile());
//            List<B01UserDO> list = b01UserService.list(map);
//            if (CollectionUtils.isEmpty(list)) {
//                return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "该手机号已经注册");
//            }
//            B01UserDO b01UserDO = new B01UserDO();
//            BeanUtils.copyProperties(form, b01UserDO);
//            b01UserDO.setCreatePerson(form.getMobile());
//            b01UserDO.setUpdatePerson(form.getMobile());
//            b01UserDO.setCreateTime(LocalDateTime.now());
//            b01UserDO.setUpdateTime(LocalDateTime.now());
//            b01UserDO.setRegistTime(LocalDateTime.now());
//            b01UserDO.setIsShop(StatusConstant.IsShop.no.getCode());
//            b01UserDO.setCurrentLoginTime(LocalDateTime.now());
//            b01UserDO.setStatus(StatusConstant.enable.getCode());
//            b01UserDO.setIsAuthentication(StatusConstant.isAuthentication.un_authentication.getCode());
//            b01UserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
//            int num = b01UserService.insert(b01UserDO);
//            if (num <= 0) {
//                ResultVOUtils.error(ResultEnum.INSERT_FAIL, "用户新增失败");
//            }
//            B01UserAppVO vo = new B01UserAppVO();
//            BeanUtils.copyProperties(b01UserDO, vo);
//            Map<String, Object> claims = new HashMap<>();
//            claims.put("app_user_id", vo.getId());
//            String token = JwtUtils.createToken(claims, 60 * 60 * 24 * 3L);
//            vo.setToken(token);
//            vo.setTokenType(TypeConstant.PlatformType.app.getCode());
//            return ResultVOUtils.success(vo);
//        }
//        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");
//    }
//    /**
//     * 苹果登录校验
//     * @param
//     * @return
//     */
//    @ApiOperation(value = "苹果登录校验；参数——jwt格式的验证字符串")
//    @RequestMapping(value = "/verifyApple",method = RequestMethod.POST)
//    public ResultVo verifyApple(String  jwt){
//        String s = appleUtil.appleAuth(jwt);
//        if("SUCCESS".equals(s)){
//            String claim = new String(Base64.decodeBase64(jwt.split("\\.")[1]));
//            String sub = JSONObject.parseObject(claim).get("sub").toString();
//            QueryMap map =new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//            map.put("appType",TypeConstant.LoginType.apple.getCode());
//            map.put("weChatOpenId",sub);
//            List<B01UserDO> list = b01UserService.list(map);
//            if(!CollectionUtils.isEmpty(list)){
//                UserInfoForm form=new UserInfoForm();
//                B01UserDO b01UserDO = list.get(0);
//                B01UserAppVO vo=new B01UserAppVO();
//                BeanUtils.copyProperties(b01UserDO,vo);
//                Map<String, Object> claims = new HashMap<>();
//                claims.put("app_user_id", vo.getId());
//                String token = JwtUtils.createToken(claims, 60*60*24*3L);
//                vo.setToken(token);
//                vo.setTokenType(TypeConstant.PlatformType.app.getCode());
//                form.setEntity(vo);
//                form.setEntity(2);//已绑定手机号
//                return ResultVOUtils.success(form);
//            }else{
//                UserInfoForm form=new UserInfoForm();
//                form.setFlag(1);//未绑定手机号
//                return ResultVOUtils.success(form);
//            }
//        }
//        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证失败");
//    }
  /**
     * 苹果登录校验
     * @param
     * @return
     */
    @ApiOperation(value = "根据appleId查询用户信息")
    @PostMapping(value = "/getUserInfoByAppleId")
    public ResultVo getUserInfoByAppleId(String  appleId){
            QueryMap map =new QueryMap(StatusConstant.CreatFlag.delete.getCode());
            map.put("appleId",appleId);
            List<B01UserDO> list = b01UserService.list(map);
            if(!CollectionUtils.isEmpty(list)){
                UserInfoForm form=new UserInfoForm();
                B01UserDO b01UserDO = list.get(0);
                B01UserAppVO vo=new B01UserAppVO();
                BeanUtils.copyProperties(b01UserDO,vo);
                Map<String, Object> claims = new HashMap<>();
                claims.put("app_user_id", vo.getId());
                String token = JwtUtils.createToken(claims, null);
                vo.setToken(token);
                vo.setTokenType(TypeConstant.PlatformType.app.getCode());
                //保存token 用于验证异地登录
                CacheUtils.set(vo.getId()+"token", token);
                form.setEntity(vo);
                form.setFlag(2);//已绑定手机号
                String contactCustomer = getContactCustomer(b01UserDO.getId());
                form.setUserSig(contactCustomer);
                form.setTengXunImId("youqianchengjin_" +vo.getId());
                return ResultVOUtils.success(form);
            }else{
                UserInfoForm form=new UserInfoForm();
                form.setFlag(1);//未绑定手机号
                return ResultVOUtils.success(form);
            }
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
}
