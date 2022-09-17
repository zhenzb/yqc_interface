package com.youqiancheng.controller.client;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.gson.Gson;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.client.B01UserUpdateForm;
import com.youqiancheng.form.client.B08PaySetSaveForm;
import com.youqiancheng.form.client.B08PaySetSearchForm;
import com.youqiancheng.form.client.B08PaySetUpdateForm;
import com.youqiancheng.mybatis.dao.B02UserAccountDao;
import com.youqiancheng.mybatis.dao.F01ShopDao;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.dao.F06WithdrawalApplicationDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.B01UserClientService;
import com.youqiancheng.service.client.service.B08PaySetClientService;
import com.youqiancheng.service.client.service.B09BankCardClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.app.B01UserAppVO;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Api(tags = {"PC端-用户设置"})
@RestController
@RequestMapping(value = "pc/userSetting")
public class MySettingClientController {
    @Autowired
    private B01UserClientService b01UserService;
    @Autowired
    private B08PaySetClientService b08PaySetService;
    @Autowired
    private B09BankCardClientService b09BankCardService;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Resource
    private F01ShopDao f01ShopDao;
    @Resource
    private F06WithdrawalApplicationDao f06WithdrawalApplicationDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;

    @Data
    public static class SmsData{
        String code;

        public SmsData(String code) {
            this.code = code;
        }
    }



    @ApiOperation(value = "根据用户ID查询用户信息：参数——用户ID")
    @GetMapping("/getUserInfo")
    @AuthRuleAnnotation()
    ResultVo getUserInfo(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户iD不能为空");
        }
        B01UserDO b01UserDO = b01UserService.get(id);

        if(b01UserDO==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到用户信息");
        }
//        if(b01UserDO.getAuthenticationName() == null){
//
//            b01UserDO.setAuthenticationName("");
//        }
        if(b01UserDO.getShopId() !=0){
            //查询商家公司名称
            F01ShopDO f01ShopDO = f01ShopDao.get(b01UserDO.getShopId());
            b01UserDO.setAuthenticationName(f01ShopDO.getName());
            //查询商家账户id
            EntityWrapper<F05ShopAccountDO> f06 = new EntityWrapper<>();
            f06.eq("shop_id",b01UserDO.getShopId());
            List<F05ShopAccountDO> f05ShopAccountDOS = f05ShopAccountDao.selectList(f06);
            EntityWrapper<F06WithdrawalApplicationDO> ew = new EntityWrapper<>();
            ew.eq("account_id",f05ShopAccountDOS.get(0).getId());
            ew.orderBy("id",false);
            List<F06WithdrawalApplicationDO> f06WithdrawalApplicationDOS = f06WithdrawalApplicationDao.selectList(ew);
            if(f06WithdrawalApplicationDOS.size()!=0){
                b01UserDO.setAlipayAccount(f06WithdrawalApplicationDOS.get(0).getAccount());
            }
        }

        return ResultVOUtils.success(b01UserDO);
    }

    @ApiOperation(value = "修改用户信息记录;参数——用户修改实体")
    @PostMapping("/updateUser")
    @AuthRuleAnnotation()
    ResultVo updateUser(@RequestBody @Valid B01UserUpdateForm dto ) {
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
        if(num<=0){
          return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "查询用户是否存在支付密码；参数——用户ID")
    @GetMapping("/getPaySetting")
    @AuthRuleAnnotation()
    ResultVo getPaySetting(Long userId) {
        if(userId==null||userId==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数不能为空");
        }
        B01UserDO b01UserDO = b01UserService.get(userId);
        if(b01UserDO==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到用户信息");
        }
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("userId",userId);
        List<B08PaySetDO> list = b08PaySetService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.success(0);
        }
        B08PaySetDO b08PaySetDO = list.get(0);
        b08PaySetDO.setPhone(b01UserDO.getMobile());
        return ResultVOUtils.success(b08PaySetDO);
    }


    @ApiOperation(value = "保存支付设置记录；参数——支付配置保存实体：用户ID,密码，用户名")
    @PostMapping("/savePaySetting")
    @AuthRuleAnnotation()
    ResultVo savePaySetting(@RequestBody @Valid  B08PaySetSaveForm dto ) {

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


    @ApiOperation(value = "验证原密码；参数——支付配置ID,密码")
    @PostMapping("/verifyPwd")
    @AuthRuleAnnotation()
    ResultVo verifyPwd(@RequestBody @Valid B08PaySetSearchForm dto  ) {

        B08PaySetDO b08PaySetDO1 = b08PaySetService.get(dto.getId());
        if(b08PaySetDO1==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到支付密码信息");
        }
        if(!b08PaySetDO1.getPayPwd().equals(PasswordUtils.authAdminPwd(dto.getPayPwd()))){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"密码验证错误");
        }
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "修改支付密码记录；参数——支付配置主键ID，密码")
    @PostMapping("/updatePaySetting")
    @AuthRuleAnnotation()
    ResultVo updatePaySetting(@RequestBody @Valid B08PaySetUpdateForm form ) {

        B08PaySetDO b08PaySetDO = b08PaySetService.get(form.getId());
        if(b08PaySetDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付密码不存在");
        }
//        if(!b08PaySetDO.getPayPwd().equals(PasswordUtils.authAdminPwd(form.getOldPayPwd()))){
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"密码验证错误");
//        }
        b08PaySetDO.setPayPwd(PasswordUtils.authAdminPwd(form.getNewPayPwd()));
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
        if(StringUtils.isBlank(PhoneNumbers)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"手机号不可为空");
        }
        HttpSession session = request.getSession();
        //随机生成验证码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        SmsData smsData = new  SmsData(numeral);
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
     * 验证
     * @param PhoneNumbers
     * @param code
     * @param request
     * @return
     */
    @ApiOperation(value = "验证码校验；参数——手机号，验证码")
    @GetMapping(value = "/verifySms")
    public ResultVo<B01UserAppVO> verifySms(String PhoneNumbers, String code, HttpServletRequest request) {
        if (StringUtils.isBlank(PhoneNumbers)) {
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "手机号不能为空");
        }
        if (StringUtils.isBlank(code)) {
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "验证码不能为空");
        }
        String key = PhoneNumbers + TypeConstant.ShortMessageType.updatePwdCode.getCode() + TypeConstant.PlatformType.pc.getCode();
        boolean b = sendSmsUtil.verifyCode(key, code);
       // if(b){
        if(b){
            return  ResultVOUtils.success("验证码已通过");
        }
        return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"验证码错误");
    }
//
//    @ApiOperation(value = "获取银行卡列表（分页+过滤参数）")
//    @GetMapping("/getBankList")
//    @AuthRuleAnnotation()
//    ResultVo<PageSimpleVO<B09BankCardDO>> getBankList(@Valid @Valid B09BankCardSearchForm form , @Valid EntyPage page ) {
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
