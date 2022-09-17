package com.youqiancheng.controller.app;

import com.google.gson.Gson;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.VerificationCodeCheckNumberAbility;
import com.youqiancheng.form.app.B01UserUpdateForm;
import com.youqiancheng.form.app.B08PaySetSaveForm;
import com.youqiancheng.form.app.B08PaySetSearchForm;
import com.youqiancheng.form.app.B08PaySetUpdateForm;
import com.youqiancheng.mybatis.model.A13SysVersionDO;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.B08PaySetDO;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.app.service.B02UserAccountAppService;
import com.youqiancheng.service.app.service.B08PaySetAppService;
import com.youqiancheng.service.app.service.B09BankCardAppService;
import com.youqiancheng.service.manager.service.A13SysVersionService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.app.B01UserAppVO;
import com.youqiancheng.vo.app.B01UserAppVVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-个人设置"})
@RestController
@RequestMapping(value = "app/userSetting")
public class MySettingAppController {
    @Autowired
    private B01UserAppService b01UserService;
    @Autowired
    private B08PaySetAppService b08PaySetService;
    @Autowired
    private B09BankCardAppService b09BankCardService;
    @Autowired
    private SendSmsUtil sendSmsUtil;
   @Autowired
   private B02UserAccountAppService b02UserAccountAppService;
    @Autowired
    private A13SysVersionService a13SysVersionService;
    @Autowired
    private VerificationCodeCheckNumberAbility verificationCodeCheckNumberAbility;
    @Data
    public static class SmsData{
        String code;

        public SmsData(String code) {
            this.code = code;
        }
    }



    @ApiOperation(value = "查询是否存在支付密码——前端判断展示修改密码还是添加密码的依据")
    @GetMapping("/getPaySetting")
    @AuthRuleAnnotation()
    ResultVo getPaySetting(Long userId) {
        if(userId==null||userId==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID不能为空");
        }
      QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("userId",userId);
        List<B08PaySetDO> list = b08PaySetService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.success(0);
        }
        return ResultVOUtils.success(list.get(0).getId());
    }

    @ApiOperation(value = "保存支付密码；参数——支付设置保存实体")
    @PostMapping("/savePaySetting")
    @AuthRuleAnnotation()
    ResultVo savePaySetting(@RequestBody B08PaySetSaveForm dto) {
        B08PaySetDO b08PaySet =new B08PaySetDO();
        b08PaySet.setPayPwd(PasswordUtils.authAdminPwd(dto.getPayPwd()));
        b08PaySet.setUserId(dto.getUserId());
        b08PaySet.setCreatePerson(dto.getCreatePerson());
        b08PaySet.setUpdatePerson(dto.getCreatePerson());
        b08PaySet.setCreateTime(LocalDateTime.now());
        b08PaySet.setUpdateTime(LocalDateTime.now());
        b08PaySet.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b08PaySetService.insert(b08PaySet);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "验证原密码；参数——支付设置记录主键ID，密码")
    @PostMapping("/verifyPwd")
    @AuthRuleAnnotation()
    ResultVo verifyPwd(@RequestBody B08PaySetSearchForm dto ){
        B08PaySetDO b08PaySetDO1 = b08PaySetService.get(dto.getId());
        if(b08PaySetDO1==null ){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到支付密码信息");
        }
        if(!b08PaySetDO1.getPayPwd().equals(PasswordUtils.authAdminPwd(dto.getPayPwd()))){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"密码验证错误");
        }
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "修改支付密码记录；参数——支付设置记录主键ID，密码")
    @PostMapping("/updatePaySetting")
    @AuthRuleAnnotation()
    ResultVo updatePaySetting(@RequestBody B08PaySetUpdateForm form) {
        B08PaySetDO b08PaySetDO = b08PaySetService.get(form.getId());
        if(b08PaySetDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付密码不存在");
        }
        b08PaySetDO.setPayPwd(PasswordUtils.authAdminPwd(form.getPayPwd()) );
        int num= b08PaySetService.update(b08PaySetDO);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }



    /**
     * 发送验证码——修改支付密码
     * @param PhoneNumbers 手机号
     * @return
     */
    @ApiOperation(value = "发送验证码——修改支付密码功能；参数——手机号")
    @GetMapping(value = "/sendSms")
    public ResultVo loginCodeSendSms(String PhoneNumbers, HttpServletRequest request){
        HttpSession session = request.getSession();
        Boolean aBoolean = verificationCodeCheckNumberAbility.checkCode(PhoneNumbers);
        if(!aBoolean){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"今日发送验证码已达上限");
        }
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        AppLoginController.SmsData smsData = new AppLoginController.SmsData(numeral);
        Gson gson = new Gson();
        String code = gson.toJson(smsData);
        String information = sendSmsUtil.aliSendSms(PhoneNumbers, code, TypeConstant.ShortMessageType.updatePwdCode.getCode());
        String key=PhoneNumbers+TypeConstant.ShortMessageType.updatePwdCode.getCode()+TypeConstant.PlatformType.app.getCode();
        sendSmsUtil.saveMap(key,numeral);
//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);//3 分钟
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
    @ApiOperation(value = "验证码校验；参数——手机号，验证码")
    @GetMapping(value = "/verifySms")
    public ResultVo<B01UserAppVO> verifySms(String PhoneNumbers, String code,HttpServletRequest request) {
        if (StringUtils.isBlank(PhoneNumbers)) {
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "手机号不能为空");
        }
        if (StringUtils.isBlank(code)) {
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "验证码不能为空");
        }
        String key = PhoneNumbers + TypeConstant.ShortMessageType.updatePwdCode.getCode() + TypeConstant.PlatformType.app.getCode();
        boolean b = sendSmsUtil.verifyCode(key, code);
        if(b){
            return  ResultVOUtils.success("验证码已通过");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");
    }

    @ApiOperation(value = "修改用户信息记录；参数——用户信息修改实体")
    @PostMapping("/updateUserInfo")
    @AuthRuleAnnotation()
    ResultVo updateUserInfo(@RequestBody B01UserUpdateForm dto) {
        B01UserDO b01UserDO = b01UserService.get(dto.getId());
        if(b01UserDO==null){
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"用户信息不存在");
        }
        if(!StringUtils.isBlank(dto.getMobile())){
            b01UserDO.setMobile(dto.getMobile());
        }
        if(!StringUtils.isBlank(dto.getNick())){
            b01UserDO.setNick(dto.getNick());
        }
        if(!StringUtils.isBlank(dto.getPic())){
            b01UserDO.setPic(dto.getPic());
        }
        if( dto.getBirthday()!=null){
            b01UserDO.setBirthday(dto.getBirthday().toLocalDate());
        }
        if(dto.getSex()!=0){
            b01UserDO.setSex(dto.getSex());
        }
        int num = b01UserService.update(b01UserDO);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "根据用户ID查询用户信息；参数——用户ID")
    @GetMapping("/getUserInfo")
    @AuthRuleAnnotation()
    ResultVo getUserInfo(Long id) {
        B01UserAppVVO   b01UserAppVVO =new B01UserAppVVO();
        B01UserDO b01UserDO = b01UserService.get(id);
        if (b01UserDO == null) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "未查询到用户信息");
        }
        BeanUtils.copyProperties(b01UserDO, b01UserAppVVO);
        LocalDate birthday = b01UserDO.getBirthday();
        if(birthday!=null){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
            String date = birthday.format(fmt);
            b01UserAppVVO.setBirthday(date);
        }
        Map<String, Object> map = new HashMap<>();
        QueryMap param=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        param.put("userId",b01UserDO.getId());
        param.put("countryId",1);
        List<B02UserAccountDO> list = b02UserAccountAppService.list(param);
        if(CollectionUtils.isEmpty(list)){
            b01UserAppVVO.setBalance(BigDecimal.valueOf(0));
        }else{
            b01UserAppVVO.setBalance(list.get(0).getAccountBalance());
        }
        b01UserAppVVO.setAuthenticationName("联系人");
        return ResultVOUtils.success(b01UserAppVVO);
    }

    @ApiOperation(value = "APP版本更新")
    @GetMapping("/getSysVersion")
    ResultVo getSysVersion() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<A13SysVersionDO> list = a13SysVersionService.list(map);
        if(list.size()>0){
            return ResultVOUtils.success(list.get(0));
        }else {
            return ResultVOUtils.success();
        }
    }


    //
//    @ApiOperation(value = "获取银行卡列表（分页+过滤参数）")
//    @GetMapping("/getBankListByUserId")
//    @AuthRuleAnnotation()
//    ResultVo<PageSimpleVO<B09BankCardDO>> getBankListByUserId(@Valid B09BankCardSearchForm form , @Valid EntyPage page ) {
//
//        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
//        List<B09BankCardDO> b09BankCardList = b09BankCardService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<B09BankCardDO> b09BankCardSimpleVO = new PageSimpleVO<>();
//        b09BankCardSimpleVO.setTotalNumber(page.getTotalNumber());
//        b09BankCardSimpleVO.setList(b09BankCardList);
//
//        return ResultVOUtils.success(b09BankCardSimpleVO);
//    }
//
//    @ApiOperation(value = "保存单条银行卡记录")
//    @PostMapping("/saveBank")
//    @AuthRuleAnnotation()
//    ResultVo saveBank(@RequestBody B09BankCardSaveForm dto) {
//        B09BankCardDO b09BankCard=new B09BankCardDO();
//        b09BankCard.setCardNo(dto.getCardNo());
//        b09BankCard.setCreatePerson(dto.getCreatePerson());
//        b09BankCard.setUpdatePerson(dto.getCreatePerson());
//        b09BankCard.setUserId(dto.getUserId());
//        b09BankCard.setType(dto.getType());
//        b09BankCard.setCreateTime(LocalDateTime.now());
//        b09BankCard.setUpdateTime(LocalDateTime.now());
//        b09BankCard.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
//        int num=b09BankCardService.insert(b09BankCard);
//        if(num<=0){
//            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//
//
//    @ApiOperation(value = "停用银行卡记录")
//    @PostMapping("/deleteBank")
//    @AuthRuleAnnotation()
//    ResultVo deleteBank(@RequestBody List<Long> idList) {
//        int num= b09BankCardService.stop(idList);
//        if(num<=0){
//            return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

}
