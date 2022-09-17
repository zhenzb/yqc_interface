package com.youqiancheng.controller.shop.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.manager.JwtUtils;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.app.AppLoginController;
import com.youqiancheng.form.shop.system.user.ShopLoginForm;
import com.youqiancheng.form.shop.system.user.ShopResetPasswordForm;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.shop.ShopStoreService;
import com.youqiancheng.service.shop.system.ShopUserService;
import com.youqiancheng.util.code.CodeEntity;
import com.youqiancheng.util.code.CodeUtil;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.app.B01UserAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/7 9:32
 * @Version: V1.0
 */
@Api(tags = "商家管理--入口")
@RestController
@RequestMapping("shop")
public class ShopUserController {
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Resource
    private ShopUserService shopUserService;
    @Resource
    private ShopStoreService shopStoreService;
    @Resource
    private B01UserDao userDao;

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "login")
    public ResultVo Login(@RequestBody @Valid ShopLoginForm loginForm ,
                          HttpServletRequest request) {

        if (loginForm == null || StringUtils.isBlank(loginForm.getUsername()) || StringUtils.isBlank(loginForm.getPwd())){
            throw new JsonException(ResultEnum.NOT_NETWORK,Constants.LOGIN_NULL_MESSAGE);
        }
        EntityWrapper<F08ShopUserDO> ew = new EntityWrapper<F08ShopUserDO>();
        ew.eq("user_name",loginForm.getUsername());
        List<F08ShopUserDO> list = shopUserService.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "用户不存在");
        }
        ew.eq("pwd",PasswordUtils.authAdminPwd(loginForm.getPwd()));
        list = shopUserService.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK, Constants.LOGIN_FAILURE_MESSAGE);
        }
        F08ShopUserDO f08ShopUser = list.get(0);
        EntityWrapper<B01UserDO> us = new EntityWrapper<B01UserDO>();
        us.eq("nick",loginForm.getUsername());
        List<B01UserDO> list1 = userDao.selectList(us);
        boolean b = true;
        if(list1.size()>0){
            B01UserDO b01UserDO = list1.get(0);
            if(b01UserDO.getStatus() == 2){
                b = false;
            }
        }
        //账号被禁用
        if (f08ShopUser.getDeleteFlag() == 2 || f08ShopUser.getStatus()==0|| f08ShopUser.getStatus()==2 || b==false) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK, "账号已停用");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin_id", f08ShopUser.getId());
        String token = JwtUtils.createToken(claims, 43200L);
        f08ShopUser.setToken(token);
        F01ShopDO f01ShopDO = shopStoreService.get(f08ShopUser.getShopId());
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家信息不存在");
        }
        f08ShopUser.setShopType(f01ShopDO.getType());
        //返回用户信息 和用户权限信息
        return ResultVOUtils.success(f08ShopUser);
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @PostMapping(value = "/resetPassword")
    public ResultVo ResetPassword(@RequestBody @Valid ShopResetPasswordForm resetPasswordForm) {
        return shopUserService.ResetPassword(resetPasswordForm);
    }

    @ApiOperation(value = "注销退出", notes = "注销退出")
    @PostMapping("logout")
    public ResultVo LogOut() {
        return ResultVOUtils.success();
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/13 15:31
     * @Param:
     * @return:
     * @Description: 图片验证码生成/校验
     */
    @ApiOperation(value = "图片验证码生成/校验", notes = "图片验证码生成/校验")
    @PostMapping(value = "/imageCode")
    public ResultVo imageCode(@RequestBody CodeEntity codeEntity) {
        if (codeEntity == null || StringUtils.isBlank(codeEntity.getPhone())){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号为空");
        }
        if (StringUtils.isBlank(codeEntity.getCode())){
            return ResultVOUtils.success(CodeUtil.createCode(codeEntity));
        }
        return ResultVOUtils.success(CodeUtil.verifyCode(codeEntity));
    }

    @Data
    public static class SmsData{
        String code;

        public SmsData(String code) {
            this.code = code;
        }
    }
    /**
     * 发送验证码——身份验证验证码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——忘记密码")
    @GetMapping(value = "/sendSms")
    public ResultVo loginCodeSendSms(String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(StringUtils.isBlank(PhoneNumbers)){
            return ResultVOUtils.success("手机号不能为空");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        AppLoginController.SmsData smsData = new AppLoginController.SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.updatePwdCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode() ;
        sendSmsUtil.saveMap(key,numeral);
//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);//3 分钟
        if(information.equals("ok")){
            return ResultVOUtils.success("短信已发送，请注意查收");
        }
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"短信发送失败，请联系管理员");
    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param code
     * @return
     */
    @ApiOperation(value = "验证码校验并修改密码——忘记密码")
    @RequestMapping(value = "/loginCode/verifySms",method = RequestMethod.POST)
    public ResultVo<B01UserAppVO> loginCodeVerifySms(String PhoneNumbers, String code){
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode();
        boolean b = sendSmsUtil.verifyCode(key, code);
        if(b){
            return ResultVOUtils.success("校验成功");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");

    }


    /**
     * 验证
     * @param PhoneNumbers
     * @param request
     * @return
     */
    @ApiOperation(value = "保存密码——忘记密码")
    @RequestMapping(value = "/loginCode/save",method = RequestMethod.POST)
    public ResultVo<B01UserAppVO> loginCodeSave(String PhoneNumbers,String pwd,HttpServletRequest request){
            Map<String, Object> map = new HashMap<>();
            map.put("userName",PhoneNumbers);
            List<F08ShopUserDO> f08ShopUserDOS = shopUserService.list(map);
            if(f08ShopUserDOS==null||f08ShopUserDOS.size()<=0){
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到对应账号");
            }
            F08ShopUserDO shopUser=f08ShopUserDOS.get(0);
            shopUser.setPwd(PasswordUtils.authAdminPwd(pwd));
            shopUser.setUpdatePerson(PhoneNumbers);
            shopUser.setUpdateTime(LocalDateTime.now());
            boolean b1 = shopUserService.updateUserPwd(shopUser);
            if(b1){
                return ResultVOUtils.success();
            }else{
                return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"密码修改失败");
            }
    }

}


