package com.youqiancheng.service.manager.serviceimpl.withdrawmanagement;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.alipay.RefundUtil;
import com.youqiancheng.controller.wechatpay.WeiXinRefundUtil;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.withdraw.WithdrawRuleForm;
import com.youqiancheng.form.manager.withdraw.WithdrawalSetForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.maile.service.MailService;
import com.youqiancheng.service.manager.service.withdrawmanagement.WithdrawDepositService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WithdrawDepositServiceImpl implements WithdrawDepositService {
    @Resource
    private F06WithdrawalApplicationDao withdrawalApplicationDao;
    @Resource
    private F09WithdrawalSetDao f09WithdrawalSetDao;
    @Resource
    private F10WithdrawalRuleDao f10WithdrawalRuleDao;
    @Resource
    private RefundUtil aliRefund;
    @Resource
    private WeiXinRefundUtil weiXinRefundUtil;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Autowired
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Resource
    private A15MessageDao  a15MessageDao;
    @Resource
    private A17MessageUserDao  a17MessageUserDao;
    @Resource
    private F01ShopDao  f01ShopDao;
    @Resource
    private B01UserDao  b01UserDao;
    @Resource
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private B01UserAppService b01UserService;

    @Autowired
    private MailService mailService;
    public WithdrawDepositServiceImpl() {
    }

    @Override
    public List<F06WithdrawalApplicationDO> listWithdrawalApplicationHDPage(Map<String, Object> map) {
        return withdrawalApplicationDao.listWithdrawalHDPage(map);
    }

    @Override
    public ResultVo batchPassRefuse(ShopPassRefuseForm shopPassRefuseForm,String ipAddr) {
            //????????????????????????????????????????????????
            F06WithdrawalApplicationDO f06WithdrawalApplicationDO = withdrawalApplicationDao.selectF06Withdrawal(shopPassRefuseForm.getId());
            if(f06WithdrawalApplicationDO == null){
                return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"?????????????????????");
            }
            if(shopPassRefuseForm.getStatus() != null){
                f06WithdrawalApplicationDO.setExamineStatus(shopPassRefuseForm.getStatus());
            }
            //?????????????????? ?????????????????????
            if(shopPassRefuseForm.getStatus() == 3){
                F05ShopAccountDO f05ShopAccountDO = f05ShopAccountDao.selectById(f06WithdrawalApplicationDO.getAccountId());
                BigDecimal add1 = f05ShopAccountDO.getAvailableWithdrawMoney().add(f06WithdrawalApplicationDO.getWithdrawalMoney());
                BigDecimal availableBalance = f05ShopAccountDO.getAvailableBalance().add(f06WithdrawalApplicationDO.getWithdrawalMoney());
                BigDecimal accountBalance = f05ShopAccountDO.getAccountBalance().add(f06WithdrawalApplicationDO.getWithdrawalMoney());
                f05ShopAccountDO.setAvailableWithdrawMoney(add1);
                f05ShopAccountDO.setAccountBalance(accountBalance);
                f05ShopAccountDO.setAvailableBalance(availableBalance);
                f05ShopAccountDao.updateById(f05ShopAccountDO);
                f06WithdrawalApplicationDO.setReason(shopPassRefuseForm.getReason());
                f06WithdrawalApplicationDO.setExamineTime(LocalDateTime.now());
                withdrawalApplicationDao.updateById(f06WithdrawalApplicationDO);

                //?????????????????????????????????
                F01ShopDO f01ShopDO = f01ShopDao.get(f05ShopAccountDO.getShopId());
                A15MessageDO messageDo=new A15MessageDO();
                messageDo.setContent("?????????????????????????????????????????????"+shopPassRefuseForm.getId()+", ??????: "+shopPassRefuseForm.getReason());
                messageDo.setUpdateTime(LocalDateTime.now());
                messageDo.setCreateTime(LocalDateTime.now());
//                messageDo.setUpdatePerson(d01OrderDO.getShopName());
//                messageDo.setSendId(d01OrderDO.getShopId());
//                messageDo.setCreatePerson(d01OrderDO.getShopName());
                messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
                messageDo.setTitle("??????????????????");
                //messageDo.setContent("???????????????????????????"+d01OrderDO.getOrderNo());
               // messageDo.setShopOrderId(d01OrderDO.getId());
                Integer insert = a15MessageDao.insert(messageDo);
                if(insert<=0){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????");
                }
                //?????????????????????
               // QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                //????????????
                A17MessageUserDO dto=new A17MessageUserDO();
                dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
                dto.setType(TypeConstant.MessageReadType.buyer.getCode());
                dto.setMessageId(messageDo.getId());
                dto.setUserId(f01ShopDO.getUserId());
                Integer insert1 = a17MessageUserDao.insert(dto);
                if(insert1<=0){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????");
                }
                return ResultVOUtils.success();
            }
            //????????????????????????
            f06WithdrawalApplicationDO.setReason(shopPassRefuseForm.getReason());
            f06WithdrawalApplicationDO.setExamineTime(LocalDateTime.now());
            Integer integer = withdrawalApplicationDao.updateById(f06WithdrawalApplicationDO);
            if(integer<=0){
                return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"????????????????????????");
            }
            if(shopPassRefuseForm.getDeleteFlag() == StatusConstant.DeleteFlag.delete.getCode()){
                withdrawalApplicationDao.deleteById(shopPassRefuseForm.getId());
            }
        f09WithdrawalSetDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
        //????????????
            if(StatusConstant.ExamineStatus.adopt.getCode()==f06WithdrawalApplicationDO.getExamineStatus()){
                //????????????????????????????????????
                //??????????????????
                F05ShopAccountDO f05ShopAccountDO = f05ShopAccountDao.selectById(f06WithdrawalApplicationDO.getAccountId());

                BigDecimal add = f05ShopAccountDO.getAccountBalance().subtract(f06WithdrawalApplicationDO.getWithdrawalMoney());
//                BigDecimal add1 = f05ShopAccountDO.getAvailableWithdrawMoney().subtract(f06WithdrawalApplicationDO.getWithdrawalMoney());
//                BigDecimal add2= f05ShopAccountDO.getAvailableBalance().subtract(f06WithdrawalApplicationDO.getWithdrawalMoney());

                //????????????????????????
                F07ShopAccountFlowDO dto=new F07ShopAccountFlowDO();
                dto.setAccountId(f05ShopAccountDO.getId());
                dto.setCreateTime(LocalDateTime.now());
                dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                dto.setFinalMoney(add);
                dto.setMoney(f06WithdrawalApplicationDO.getWithdrawalMoney());
                dto.setOriginalMoney(f05ShopAccountDO.getAccountBalance());
                dto.setUpdateTime(LocalDateTime.now());
                dto.setType(TypeConstant.ShopAccountType.withdrawal_pay.getCode());
                dto.setSourceId(f06WithdrawalApplicationDO.getId());
                f07ShopAccountFlowDao.insert(dto);

//                f05ShopAccountDO.setAccountBalance(add);
//                f05ShopAccountDO.setAvailableWithdrawMoney(add1);
//                f05ShopAccountDO.setAvailableBalance(add2);
                f05ShopAccountDao.updateById(f05ShopAccountDO);

                F01ShopDO f01ShopDO = f01ShopDao.get(f05ShopAccountDO.getShopId());
                pushPassMessage(f01ShopDO);
                //B01UserDO b01UserDO = b01UserDao.get(f01ShopDO.getUserId());
                Map<String,Object> map = new HashMap<>();
                map.put("userId",f01ShopDO.getUserId());
                List<B07AuthenticationDO> list = b07AuthenticationDao.list(map);
                String name = null;
                if(list.size()>0){
                     name = list.get(0).getName();
                }
                try{
                    mailService.sendMail(f01ShopDO.getName()+"????????????????????????","???????????????"+ f06WithdrawalApplicationDO.getWithdrawalMoney());
                }catch (Exception e){}
                //????????????????????????????????????????????????????????????????????????
                if(TypeConstant.WithdrawalType.zhifubao.getCode()==f06WithdrawalApplicationDO.getType()){
                    return aliRefund.transferAccounts(f06WithdrawalApplicationDO.getId(),name,f05ShopAccountDO);
                }else{//??????????????????
                    B01UserDO b01UserDO = b01UserService.get(f01ShopDO.getUserId());
                    return weiXinRefundUtil.weixinTransfer(f06WithdrawalApplicationDO.getId(),ipAddr,b01UserDO.getWechatOpenid());
                }

            }else{
                F05ShopAccountDO f05ShopAccountDO = f05ShopAccountDao.selectById(f06WithdrawalApplicationDO.getAccountId());
                F01ShopDO f01ShopDO = f01ShopDao.get(f05ShopAccountDO.getShopId());
                pushRefuseMessage(f01ShopDO, f06WithdrawalApplicationDO);
                return ResultVOUtils.success();
            }
    }
    private void pushPassMessage(F01ShopDO f01ShopDO){
        //?????????????????????????????????
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("??????????????????");
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        messageDo.setUpdatePerson("admin");
        messageDo.setCreatePerson("admin");
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("????????????");
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????");
        }
        //?????????????????????
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        //????????????
        A17MessageUserDO dto=new A17MessageUserDO();
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
        dto.setType(TypeConstant.MessageReadType.business.getCode());
        dto.setMessageId(messageDo.getId());
        dto.setUserId(f01ShopDO.getUserId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"????????????????????????");
        }
        //????????????????????????????????????????????????????????????
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"????????????????????????????????????");
        }
        try {
            String alias = "yqc_" + b01UserDO1.getId();
            String aliastype = "yqc_type";
            String content = messageDo.getContent();
            String title = messageDo.getTitle();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, title,"", content, "1","1",null,null,null);  // IOS ??????
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, title, "", content, "1","1",null,null,null); // android ??????
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????"+e.getMessage());
        }
    }
    private void pushRefuseMessage(F01ShopDO f01ShopDO,  F06WithdrawalApplicationDO f06WithdrawalApplicationDO){
        //?????????????????????????????????
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("?????????????????????????????????"+f06WithdrawalApplicationDO.getReason());
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        messageDo.setUpdatePerson("admin");
        messageDo.setCreatePerson("admin");
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("????????????");
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????");
        }
        //?????????????????????
        //????????????
        A17MessageUserDO dto=new A17MessageUserDO();
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
        dto.setType(TypeConstant.MessageReadType.business.getCode());
        dto.setMessageId(messageDo.getId());
        dto.setUserId(f01ShopDO.getUserId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"????????????????????????");
        }
        //????????????????????????????????????????????????????????????
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"????????????????????????????????????");
        }
        try {
            String alias = "yqc_" + b01UserDO1.getId();
            String aliastype = "yqc_type";
            String content = messageDo.getContent();
            String title = messageDo.getTitle();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, title,title, content, "1","1",null,null,null);  // IOS ??????
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, title, title, content, "1","1",null,null,null); // android ??????
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????"+e.getMessage());
        }
    }

    @Override
    public void addOrEditAudit(WithdrawalSetForm withdrawalSetForm) {
        f09WithdrawalSetDao.delete(null);
        F09WithdrawalSetDO f09WithdrawalSetDO = new F09WithdrawalSetDO();
        BeanUtils.copyProperties(withdrawalSetForm,f09WithdrawalSetDO);

        f09WithdrawalSetDao.insert(f09WithdrawalSetDO);
    }

    @Override
    public F09WithdrawalSetDO getAudit() {
        List<F09WithdrawalSetDO> f09WithdrawalSetDOList = f09WithdrawalSetDao.selectList(null);
        if(f09WithdrawalSetDOList== null || f09WithdrawalSetDOList.size() == 0){
            return null;
        }
        return f09WithdrawalSetDOList.get(0);
    }

    @Override
    public void addOrEditRule(WithdrawRuleForm withdrawRuleForm) {

        if(withdrawRuleForm.getId() == null){
            F10WithdrawalRuleDO f10WithdrawalRuleDO = new F10WithdrawalRuleDO();
            BeanUtils.copyProperties(withdrawRuleForm,f10WithdrawalRuleDO);

            f10WithdrawalRuleDao.insert(f10WithdrawalRuleDO);
            return;
        }else {
           if(StatusConstant.DeleteFlag.delete.getCode()==withdrawRuleForm.getDeleteFlag()){
               f10WithdrawalRuleDao.deleteById(withdrawRuleForm.getId());
               return;
            }
            F10WithdrawalRuleDO f10WithdrawalRuleDO = new F10WithdrawalRuleDO();
            BeanUtils.copyProperties(withdrawRuleForm,f10WithdrawalRuleDO);
            f10WithdrawalRuleDO.setUpdateTime(LocalDateTime.now());
            f10WithdrawalRuleDao.updateById(f10WithdrawalRuleDO);
        }
    }

    @Override
    public List<F10WithdrawalRuleDO> getRule() {
        List<F10WithdrawalRuleDO> f10WithdrawalRuleDOList = f10WithdrawalRuleDao.selectList(null);
        if(f10WithdrawalRuleDOList== null || f10WithdrawalRuleDOList.size() == 0){
            return null;
        }
        return f10WithdrawalRuleDOList;
    }



}
