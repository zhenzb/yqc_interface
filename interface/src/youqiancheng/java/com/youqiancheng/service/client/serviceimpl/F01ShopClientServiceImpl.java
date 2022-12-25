package com.youqiancheng.service.client.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.PasswordUtils;
import com.youqiancheng.ability.AuthenticationAbility;
import com.youqiancheng.form.client.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F11RoleDao;
import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.B07AuthenticationClientService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Slf4j
@Service
@Transactional
public class F01ShopClientServiceImpl implements F01ShopClientService {
    @Autowired
    private F01ShopDao f01ShopDao;
    @Autowired
    private F03MainProjectDao f03MainProjectDao;

    @Autowired
    private A15MessageDao a15MessageDao;
    @Autowired
    private A17MessageUserDao a17MessageUserDao;
    @Autowired
    private B01UserDao b01UserDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;
    @Resource
    private F16ShopProfitFlowsDao f16ShopProfitFlowsDao;
    @Resource
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private F04ShopExamineSetDao f04ShopExamineSetDao;
    @Autowired
    private F08ShopUserDao f08ShopUserDao;
    @Autowired
    private F11RoleDao f11RoleDao;
    @Autowired
    private F13RoleShopUserDao f13RoleShopUserDao;
    @Autowired
    private  B05CollectionDao b05CollectionDao;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private AuthenticationAbility authenticationAbility;

    @Override
    public F01ShopDO get(Long id){
        F01ShopDO f01ShopDO = f01ShopDao.get(id);
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家不存在");
        }
//        f01ShopDO.setBrowseVolume(f01ShopDO.getBrowseVolume()+1);
//        f01ShopDao.updateById(f01ShopDO);
        return f01ShopDO;
    }

    @Override
    public F01ShopDO getShop(Long id){
        F01ShopDO f01ShopDO = f01ShopDao.get(id);
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家不存在");
        }
        f01ShopDO.setBrowseVolume(f01ShopDO.getBrowseVolume()+1);
        f01ShopDao.updateById(f01ShopDO);
        return f01ShopDO;
    }


    @Override
    public List<F01ShopDO> listHDPage(Map<String, Object> map) {
        return f01ShopDao.listHDPage(map);
    }

    @Override
    public List<F03MainProjectDO> getShopProjectByType(String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type",type);
        map.put("deleteFlag",1);
        List<F03MainProjectDO> list = f03MainProjectDao.list(map);
        return list;
    }


    @Override
    public List<F01ShopDO> list(Map<String, Object> map) {
        return f01ShopDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return f01ShopDao.count(map);
    }


    @Override
    public int applyEnters(F01ShopAppSaveForm dto) {
        //字段copy保存店铺信息
        F01ShopDO f01Shop =new F01ShopDO();
        try {
            BeanUtils.copyProperties(f01Shop,dto);
        } catch (IllegalAccessException e) {
            throw new JsonException(ResultEnum.CHANGE_FAIL,e.getMessage());
        } catch (InvocationTargetException e) {
            throw new JsonException(ResultEnum.CHANGE_FAIL,e.getMessage());
        }
        f01Shop.setMainProject(dto.getMainProject());
        f01Shop.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f01Shop.setStatus(StatusConstant.enable.getCode());
        f01Shop.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());

        f01Shop.setCreatePerson(dto.getUserName());
        f01Shop.setUpdatePerson(dto.getUserName());
        f01Shop.setCreateTime(LocalDateTime.now());
        f01Shop.setUpdateTime(LocalDateTime.now());
        f01Shop.setHide(StatusConstant.Ishide.un_hide.getCode());
        f01Shop.setCountryId(1);
        int insert = f01ShopDao.insert(f01Shop);
        //修改实名认证
        authenticationAbility.updateAuthentication(dto);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"商家插入失败");
        }
        List<F04ShopExamineSetDO> list = f04ShopExamineSetDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
        if(!CollectionUtils.isEmpty(list)){
            F04ShopExamineSetDO f04ShopExamineSetDO = list.get(0);
            if(2==f04ShopExamineSetDO.getExamineFlag()){
                adoptShop(f01Shop.getId());
            }
        }

        //保存照片信息
        //营业执照信息
        authenticationAbility.addShopPic(f01Shop,dto);

        B01UserDO b01UserDO = b01UserDao.get(Long.valueOf(dto.getUserId()));
        if(b01UserDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户对应信息不存在");
        }
        b01UserDO.setShopId(f01Shop.getId());
        b01UserDO.setShopName(f01Shop.getName());
        b01UserDO.setIsShop(StatusConstant.IsShop.yes.getCode());
        b01UserDao.updateById(b01UserDO);
        //发送消息
        sendMsg(dto);
        return 1;
    }



    public void adoptShop(Long id) {
        F01ShopDO f01ShopDO = f01ShopDao.get(id);
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        //审核通过
        //获取商家 经纬度
        //String location = f01ShopDO.getProvince()+f01ShopDO.getCity()+f01ShopDO.getArea()+f01ShopDO.getAddress();
        //String latAndLogByName = MapUtil.getLatAndLogByName(location);
        //f01ShopDO.setLongitude(latAndLogByName.split(",")[0]);
        //f01ShopDO.setLatitude(latAndLogByName.split(",")[1]);
        f01ShopDO.setExamineStatus(StatusConstant.ExamineStatus.adopt.getCode());
        f01ShopDO.setUpdateTime(LocalDateTime.now());
        f01ShopDao.updateById(f01ShopDO);
        //审核通过需要建 一个商家账号 F08ShopUserDO
        //随机生成店铺登录密码
        String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
        JSONObject json = new JSONObject();
        json.put("name",f01ShopDO.getContacts());
        json.put("shop",f01ShopDO.getName());
        json.put("password",numeral);
        String information = sendSmsUtil.aliSendSmsTwo(b01UserDO1.getMobile(), json.toJSONString(), TypeConstant.ShortMessageType.checkbusinessCode.getCode());
        String key=f01ShopDO.getPhone()+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.pc.getCode();
        sendSmsUtil.saveMap(key,numeral);
//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);
        if(information.equals("ok")){
            log.info("店铺审核通过 短信已发送，请注意查收");
        }else{
            log.info("店铺审核通过 短信发送失败");
        }
        F08ShopUserDO f08ShopUserDO = buildF08ShopUserDO(f01ShopDO,numeral);
        f08ShopUserDao.insert(f08ShopUserDO);

        //根据商家类型确定角色类型
        int type=1;
        if(StatusConstant.NewShopType.product.getCode()==f01ShopDO.getType()){
//            if(f01ShopDO.getMainProject()==3){
//                type= TypeConstant.ShopRoleType.room_manager.getCode();
//            }else{
//                type= TypeConstant.ShopRoleType.goods_manager.getCode();
//            }
            type= TypeConstant.ShopRoleType.goods_manager.getCode();
        }else{
            type= TypeConstant.ShopRoleType.publicity_manager.getCode();
        }
        //查询对应类型角色信息
        List<F11RoleDO> f11RoleDOS = f11RoleDao.selectList(
                new EntityWrapper<F11RoleDO>()
                        .eq("type",type)
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
        );
        if(f11RoleDOS==null||f11RoleDOS.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到对应商家角色，请联系管理员");
        }
        F13RoleShopUserDO dto=new F13RoleShopUserDO();
        dto.setRoleId(f11RoleDOS.get(0).getId());
        dto.setUserId(f08ShopUserDO.getId());
        dto.setCreateTime(LocalDateTime.now());
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        Integer insert = f13RoleShopUserDao.insert(dto);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"关联商家用户和角色失败");
        }
        F05ShopAccountDO dto1=new F05ShopAccountDO();
        dto1.setShopId(f01ShopDO.getId());
        dto1.setAvailableBalance(BigDecimal.valueOf(0));
        dto1.setAccountBalance(BigDecimal.valueOf(0));
        dto1.setAvailableWithdrawMoney(BigDecimal.valueOf(0));
        dto1.setHasWithdrawMoney(BigDecimal.valueOf(0));
        dto1.setInWithdrawMoney(BigDecimal.valueOf(0));
        dto1.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f05ShopAccountDao.insert(dto1);
    }


    private F08ShopUserDO buildF08ShopUserDO(F01ShopDO f01ShopDO,String numeral){
        F08ShopUserDO f08ShopUserDO = new F08ShopUserDO();
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户信息不存在");
        }
        f08ShopUserDO.setUserName(b01UserDO1.getMobile());
        f08ShopUserDO.setStatus(StatusConstant.enable.getCode());
        f08ShopUserDO.setShopId(f01ShopDO.getId());
        f08ShopUserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f08ShopUserDO.setPwd(PasswordUtils.authAdminPwd(numeral));
        f08ShopUserDO.setShopName(f01ShopDO.getName());
        f08ShopUserDO.setUpdateTime(LocalDateTime.now());
        f08ShopUserDO.setCreateTime(LocalDateTime.now());
        f08ShopUserDO.setCreatePerson(b01UserDO1.getMobile());
        f08ShopUserDO.setUpdatePerson(b01UserDO1.getMobile());

        return f08ShopUserDO;
    }

    @Override
    public int isShopApplyEnters(Long userId) {
        B01UserDO b01UserDO = b01UserDao.get(userId);
        if(b01UserDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户不存在");
        }
        if(b01UserDO.getShopId()==0){
            return 1;
        }
        F01ShopDO f01ShopDO = f01ShopDao.get(b01UserDO.getShopId());
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家不存在");
        }
        if(StatusConstant.ExamineStatus.un_examine.getCode()==f01ShopDO.getExamineStatus()){
            return 2;
        }else if(StatusConstant.ExamineStatus.adopt.getCode()==f01ShopDO.getExamineStatus()){
            return 3;
        }else{
            return 1;
        }
    }

    public void sendMsg(F01ShopAppSaveForm dto){
        A15MessageDO  a15Message=new A15MessageDO();
        a15Message.setUpdateTime(LocalDateTime.now());
        a15Message.setCreateTime(LocalDateTime.now());
        a15Message.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        a15Message.setStatus(StatusConstant.ReleaseStatus.release.getCode());
        a15Message.setType(StatusConstant.MessageType.service_msg.getCode());
        a15Message.setTitle("入驻提示");
        a15Message.setContent("您申请入驻" +dto.getName()+"正在审核中");
        int insert = a15MessageDao.insert(a15Message);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"插入商家入驻审核中消息插入失败");
        }
        A17MessageUserDO a17MessageUserDO=new A17MessageUserDO();
        a17MessageUserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        a17MessageUserDO.setIsRead(StatusConstant.IsRead.read.getCode());
        a17MessageUserDO.setMessageId(insert);
        a17MessageUserDO.setUserId(dto.getUserId());
        int num =a17MessageUserDao.insert(a17MessageUserDO);
        if(num<=0){
            throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"插入用户消息(商家入驻审核中)失败");
        }
    }


    @Override
    public int insertBatch(List<F01ShopDO> f01Shops){
        return f01ShopDao.insertBatch(f01Shops);
    }


    @Override
    public int update(F01ShopDO f01Shop) {
        f01Shop.setUpdateTime(LocalDateTime.now());
        return f01ShopDao.updateById(f01Shop);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return f01ShopDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return f01ShopDao.updateStatus(param);
        }

    @Override
    public F05ShopAccountDO getProfit(Long id) {
        List<F05ShopAccountDO> f05ShopAccountDOS = f05ShopAccountDao.selectList(
                new EntityWrapper<F05ShopAccountDO>()
                .eq("shop_id",id)
        );
        F05ShopAccountDO vo=f05ShopAccountDOS.get(0);
        if(CollectionUtils.isEmpty(f05ShopAccountDOS)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家余额不存在");
        }
        List<F15ShopProfitDO> f15ShopProfitDOS = f15ShopProfitDao.selectList(
                new EntityWrapper<F15ShopProfitDO>()
                .eq("shop_id",id)
        );

//        if(CollectionUtils.isEmpty(f15ShopProfitDOS)){
//            //throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家收益不存在");
//
//           // vo.setDayProfit(BigDecimal.valueOf(0));
//           // vo.setTotalProfit(BigDecimal.valueOf(0));
//        }else{
//
//            //vo.setDayProfit(f15ShopProfitDOS.get(0).getDayIncome());
//           // vo.setTotalProfit(vo.getAvailableBalance());
//        }


        Map<String,Object> map=new HashMap<>();
        map.put("shopId",id);
        //昨日收益
        List<F16ShopProfitFlowsDO> profitByDay = f16ShopProfitFlowsDao.getProfitByDay(map);
        if(CollectionUtils.isEmpty(profitByDay)){
            //throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家昨日收益不存在");
            vo.setYesterdayProfit(BigDecimal.valueOf(0));
        }else{
            if(profitByDay.get(0)!=null){
                vo.setYesterdayProfit(profitByDay.get(0).getDayIncome() == null?BigDecimal.valueOf(0):profitByDay.get(0).getDayIncome());
            }else{
                vo.setYesterdayProfit(BigDecimal.valueOf(0));
            }
        }
        //今日收益
        List<F16ShopProfitFlowsDO> profitBytoday = f16ShopProfitFlowsDao.getProfitBytoday(map);
        if(CollectionUtils.isEmpty(profitBytoday)){
            vo.setDayProfit(BigDecimal.valueOf(0));
        }else {
            if(profitBytoday.get(0) !=null){
                vo.setDayProfit(profitBytoday.get(0).getDayIncome()== null?BigDecimal.valueOf(0):profitBytoday.get(0).getDayIncome());
            }else {
                vo.setDayProfit(BigDecimal.valueOf(0));
            }
        }
        //总收益
        List<F16ShopProfitFlowsDO> proToatlfitBytoday = f16ShopProfitFlowsDao.getTotalProfitBytoday(map);
        if(CollectionUtils.isEmpty(proToatlfitBytoday)){
            vo.setTotalProfit(BigDecimal.valueOf(0));
        }else {
            if(proToatlfitBytoday.get(0) !=null){
                vo.setTotalProfit(proToatlfitBytoday.get(0).getDayIncome()== null?BigDecimal.valueOf(0):proToatlfitBytoday.get(0).getDayIncome());
            }else{
                vo.setTotalProfit(BigDecimal.valueOf(0));
            }
        }
        return vo;
    }

    @Override
    public F01ShopDO getQRcodeimage(Long id) {
        //获取没删除的商家的微信支付宝二维码图片
        int  deleteFlag =  StatusConstant.DeleteFlag.un_delete.getCode();
        return f01ShopDao.getQRcodeimage(id,deleteFlag);
    }
}
