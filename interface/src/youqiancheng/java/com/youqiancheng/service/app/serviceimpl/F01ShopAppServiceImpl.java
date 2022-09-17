package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.PasswordUtils;
import com.youqiancheng.ability.AuthenticationAbility;
import com.youqiancheng.form.app.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F11RoleDao;
import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.F01ShopAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.app.ShopAppQtPhopo;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Slf4j
@Service
@Transactional
public class F01ShopAppServiceImpl implements F01ShopAppService {
    @Autowired
    private F01ShopDao f01ShopDao;
    @Autowired
    private F03MainProjectDao f03MainProjectDao;
    @Autowired
    private F02ShopPicDao f02ShopPicDao;
    @Autowired
    private A15MessageDao a15MessageDao;
    @Autowired
    private A17MessageUserDao a17MessageUserDao;
    @Autowired
    private B01UserDao b01UserDao;
    @Autowired
    private F04ShopExamineSetDao f04ShopExamineSetDao;
    @Autowired
    private F08ShopUserDao f08ShopUserDao;
    @Autowired
    private F11RoleDao f11RoleDao;
    @Autowired
    private F13RoleShopUserDao f13RoleShopUserDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;

    @Autowired
    private B05CollectionDao b05CollectionDao;
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private AuthenticationAbility authenticationAbility;

    @Override
    public F01ShopDO get(Long id){

        F01ShopDO f01ShopDO = f01ShopDao.get(id);
        if(f01ShopDO==null){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"此商家不存在");
        }
        //f01ShopDO.setBrowseVolume(f01ShopDO.getBrowseVolume()+1);
        //f01ShopDao.updateById(f01ShopDO);
        return f01ShopDO;
    }
    @Override
    public F01ShopDO getShop(Long id){

        F01ShopDO f01ShopDO = f01ShopDao.get(id);
        if(f01ShopDO==null){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"此商家不存在");
        }
        List<F02ShopPicDO> picList = f02ShopPicDao.selectList(new EntityWrapper<F02ShopPicDO>().eq("shop_id", id));
        f01ShopDO.setPicList(picList);
        f01ShopDO.setBrowseVolume(f01ShopDO.getBrowseVolume()+1);
        f01ShopDao.updateById(f01ShopDO);
        return f01ShopDO;
    }



    @Override
    public List<F01ShopDO> listHDPage(Map<String, Object> map) {
        return f01ShopDao.listHDPage(map);
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
            return 4;
        }
    }

    @Override
    public List<F03MainProjectDO> getShopProjectByType(String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type",type);
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
    public F01ShopDO applyEnters(F01ShopAppSaveForm dto) {
        //字段copy保存店铺信息
        F01ShopDO f01Shop =new F01ShopDO();
        try {
            BeanUtils.copyProperties(f01Shop,dto);
        } catch (IllegalAccessException e) {
            throw new JsonException(ResultEnum.CHANGE_FAIL,e.getMessage());
        } catch (InvocationTargetException e) {
            throw new JsonException(ResultEnum.CHANGE_FAIL,e.getMessage());
        }
        f01Shop.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f01Shop.setStatus(StatusConstant.enable.getCode());
        f01Shop.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());

        f01Shop.setCreatePerson(dto.getUserName());
        f01Shop.setUpdatePerson(dto.getUserName());
        f01Shop.setCreateTime(LocalDateTime.now());
        f01Shop.setUpdateTime(LocalDateTime.now());
        f01Shop.setUserId(dto.getUserId());
        f01Shop.setHide(StatusConstant.Ishide.un_hide.getCode());
        int insert = f01ShopDao.insert(f01Shop);
        try {
            B07AuthenticationDO b07AuthenticationDO = new B07AuthenticationDO();
            b07AuthenticationDO.setUserId(dto.getUserId());
            b07AuthenticationDO.setName(dto.getContacts());
            b07AuthenticationDao.update(b07AuthenticationDO, new EntityWrapper<B07AuthenticationDO>().eq("user_id", dto.getUserId()));
        }catch (Exception e){
            log.info("实名认证修改失败......");
        }
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
        List<F02ShopPicDO> f02ShopPics=new ArrayList<>();
        if(dto.getLicenseList()!=null &&!dto.getLicenseList().isEmpty()){
            for (String str : dto.getLicenseList()) {
                F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
                f02ShopPicDO.setShopId(f01Shop.getId());
                f02ShopPicDO.setPicUrl(str);
                f02ShopPicDO.setType(StatusConstant.ShopPicType.license.getCode());
                //基础信息
                f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                f02ShopPicDO.setCreatePerson(dto.getUserName());
                f02ShopPicDO.setUpdatePerson(dto.getUserName());
                f02ShopPicDO.setCreateTime(LocalDateTime.now());
                f02ShopPicDO.setUpdateTime(LocalDateTime.now());

                f02ShopPics.add(f02ShopPicDO);
            }
        }
    if(dto.getIdCardList()!=null &&!dto.getIdCardList().isEmpty()){
         //身份证信息
         for (String str : dto.getIdCardList()) {
             F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
             f02ShopPicDO.setShopId(f01Shop.getId());
             f02ShopPicDO.setPicUrl(str);
             f02ShopPicDO.setType(StatusConstant.ShopPicType.id_card.getCode());
             //基础信息
             f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
             f02ShopPicDO.setCreatePerson(dto.getUserName());
             f02ShopPicDO.setUpdatePerson(dto.getUserName());
             f02ShopPicDO.setCreateTime(LocalDateTime.now());
             f02ShopPicDO.setUpdateTime(LocalDateTime.now());

             f02ShopPics.add(f02ShopPicDO);
         }
     }
     if(dto.getOtherInfoList()!=null &&!dto.getOtherInfoList().isEmpty()){
         //其他信息
         for (String str : dto.getOtherInfoList()) {
             F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
             f02ShopPicDO.setShopId(f01Shop.getId());
             f02ShopPicDO.setPicUrl(str);
             f02ShopPicDO.setType(StatusConstant.ShopPicType.other.getCode());
             //基础信息
             f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
             f02ShopPicDO.setCreatePerson(dto.getUserName());
             f02ShopPicDO.setUpdatePerson(dto.getUserName());
             f02ShopPicDO.setCreateTime(LocalDateTime.now());
             f02ShopPicDO.setUpdateTime(LocalDateTime.now());

             f02ShopPics.add(f02ShopPicDO);
         }
        }


        if(!f02ShopPics.isEmpty()){
            int i = f02ShopPicDao.insertBatch(f02ShopPics);
            if(i<=0){
                throw new JsonException(ResultEnum.INSERT_FAIL,"商家图片批量插入失败");
            }
        }

        B01UserDO b01UserDO = b01UserDao.get(Long.valueOf(dto.getUserId()));
        if(b01UserDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户对应信息不存在");
        }
        b01UserDO.setIsShop(StatusConstant.IsShop.yes.getCode());
        b01UserDO.setShopId(f01Shop.getId());
        b01UserDO.setShopName(f01Shop.getName());
        b01UserDao.updateById(b01UserDO);
        //发送消息
        sendMsg(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("userId",dto.getUserId());
        List<F01ShopDO> list1 = f01ShopDao.list(map);
        f01ShopDao.selectList(new EntityWrapper<F01ShopDO>().eq("user_id",dto.getUserId()));
        if(list1.size()>0){
            return list1.get(0);
        }
        return null;
    }



    public void adoptShop(Long id) {
            F01ShopDO f01ShopDO = f01ShopDao.get(id);
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
            F08ShopUserDO f08ShopUserDO = buildF08ShopUserDO(f01ShopDO);
            f08ShopUserDao.insert(f08ShopUserDO);

            //根据商家类型确定角色类型
            int type=1;
            if(StatusConstant.NewShopType.product.getCode()==f01ShopDO.getType()){
           /*     if(f01ShopDO.getMainProject()==3){
                    type= TypeConstant.ShopRoleType.room_manager.getCode();
                }
                else{

                }*/
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


    private F08ShopUserDO buildF08ShopUserDO(F01ShopDO f01ShopDO){
        F08ShopUserDO f08ShopUserDO = new F08ShopUserDO();
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户信息不存在");
        }
        f08ShopUserDO.setUserName(b01UserDO1.getMobile());
        f08ShopUserDO.setStatus(StatusConstant.enable.getCode());
        f08ShopUserDO.setShopId(f01ShopDO.getId());
        f08ShopUserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f08ShopUserDO.setPwd(PasswordUtils.authAdminPwd("123456"));
        f08ShopUserDO.setShopName(f01ShopDO.getName());
        f08ShopUserDO.setUpdateTime(LocalDateTime.now());
        f08ShopUserDO.setCreateTime(LocalDateTime.now());
        f08ShopUserDO.setCreatePerson(b01UserDO1.getMobile());
        f08ShopUserDO.setUpdatePerson(b01UserDO1.getMobile());

        return f08ShopUserDO;
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
    public List<F01ShopDO> getvicinity(Map<String, Object> map) {
        List<F01ShopDO> getvicinity = f01ShopDao.getvicinity(map);
        return getvicinity;
    }
    @Override
    public List<F01ShopDO> getvicinityByDistanceHDPage(Map<String, Object> map) {
        List<F01ShopDO> getvicinity = f01ShopDao.getvicinityByDistanceHDPage(map);
        return getvicinity;
    }

    @Override
    public List<F01ShopDO> getShopInfoById(Long id) {
        return f01ShopDao.getShopInfoById(id);
    }

    @Override
    public F01ShopDO getQRcodeimage(Long id) {
        //获取没删除的商家的微信支付宝二维码图片
        int  deleteFlag =  StatusConstant.DeleteFlag.un_delete.getCode();
        F01ShopDO qRcodeimage = f01ShopDao.getQRcodeimage(id, deleteFlag);
        return  qRcodeimage;
    }

    @Override
    public int updateApplyEnters(com.youqiancheng.form.client.F01ShopAppSaveForm dto) {
        F01ShopDO f01Shop = f01ShopDao.get(dto.getId());
        try {
            BeanUtils.copyProperties(f01Shop,dto);
        } catch (IllegalAccessException e) {
            throw new JsonException(ResultEnum.CHANGE_FAIL,e.getMessage());
        } catch (InvocationTargetException e) {
            throw new JsonException(ResultEnum.CHANGE_FAIL,e.getMessage());
        }
        f01Shop.setUpdatePerson(dto.getUserName());
        f01Shop.setUpdateTime(LocalDateTime.now());
        f01ShopDao.updateById(f01Shop);
        //修改实名认证
        authenticationAbility.updateAuthentication(dto);
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
        return 1;
    }
}
