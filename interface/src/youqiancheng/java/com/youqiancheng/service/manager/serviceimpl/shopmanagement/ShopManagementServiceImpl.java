package com.youqiancheng.service.manager.serviceimpl.shopmanagement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.handongkeji.util.StringUtil;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.client.ClientLoginController;
import com.youqiancheng.form.manager.shop.*;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F11RoleDao;
import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.SpringContextUtil;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ShopManagementServiceImpl implements ShopManagementService {
    @Resource
    private F01ShopDao shopDao;
    @Resource
    private F03MainProjectDao f03MainProjectDao;
    @Resource
    private C01GoodsDao c01GoodsDao;
    @Resource
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private F04ShopExamineSetDao f04ShopExamineSetDao;
    @Resource
    private F08ShopUserDao f08ShopUserDao;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private F11RoleDao f11RoleDao;
    @Resource
    private F13RoleShopUserDao f13RoleShopUserDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;
    @Resource
    private F16ShopProfitFlowsDao  f16ShopProfitFlowsDao;
    @Resource
    private A15MessageDao  a15MessageDao;
    @Resource
    private A17MessageUserDao  a17MessageUserDao;
    @Resource
    private F02ShopPicDao  f02ShopPicDao;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;

    public ShopManagementServiceImpl() {
    }
    /**
    ???* @Description: ??????????????????
    ???* @author shalongteng
    ???* @date 2020/4/8 17:27
    ???*/
    @Override
    public List<F01ShopDO> listShopHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        List<F01ShopDO> f01ShopDOList = shopDao.listShopHDPage(map);
        //????????????,??????????????????
        for (F01ShopDO f01ShopDO : f01ShopDOList) {
            //F05ShopAccountDO f05ShopAccountDO = new F05ShopAccountDO();
            //f05ShopAccountDO.setShopId(f01ShopDO.getId());
           // F05ShopAccountDO f05ShopAccountDO1 = f05ShopAccountDao.selectOne(f05ShopAccountDO);
            BigDecimal shopTotalBalanceByShopId = f05ShopAccountDao.getShopTotalBalanceByShopId(f01ShopDO.getId());
            f01ShopDO.setAccountBalance(shopTotalBalanceByShopId);
            //??????????????????
            EntityWrapper<C01GoodsDO> ew = new EntityWrapper<>();
            ew.eq("shop_id",f01ShopDO.getId());
            List<C01GoodsDO> c01GoodsDOList = c01GoodsDao.selectList(ew);
            f01ShopDO.setSumGoods(c01GoodsDOList.size());
        }
        return f01ShopDOList;
    }
    /**
    ???* @Description: ????????????
    ???* @author shalongteng
    ???* @date 2020/4/13 14:31
     * @param shopDeleteFormList*/
    @Override
    public Integer deleteShop(List<ShopDeleteForm> shopDeleteFormList) {
        List<Long> list = new ArrayList<>();
        for(ShopDeleteForm shop:shopDeleteFormList){
            list.add(shop.getId());
        }
        return shopDao.deleteBatchIds(list);
    }
    /**
    ???* @Description: ${todo}
    ???* @author shalongteng
    ???* @date 2020/4/13 14:55
    ???*/
    @Override
    public Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            F01ShopDO f01ShopDO = shopDao.selectById(shopPassRefuseForm.getId());
            //????????????????????? ?????? ???????????? ??????
            if(f01ShopDO == null || f01ShopDO.getExamineStatus() != StatusConstant.ExamineStatus.un_examine.getCode()){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
            //????????????
            if(shopPassRefuseForm.getStatus()==StatusConstant.ExamineStatus.adopt.getCode()){
                //??????????????????????????????
                String numeral = String.valueOf(new Random().nextInt(899999) + 100000);
                //???????????? ?????????
                //String location = f01ShopDO.getProvince()+f01ShopDO.getCity()+f01ShopDO.getArea()+f01ShopDO.getAddress();
                //String latAndLogByName = MapUtil.getLatAndLogByName(location);
                //f01ShopDO.setLongitude(latAndLogByName.split(",")[0]);
                //f01ShopDO.setLatitude(latAndLogByName.split(",")[1]);
                f01ShopDO.setExamineStatus(shopPassRefuseForm.getStatus());
                f01ShopDO.setUpdateTime(LocalDateTime.now());
                shopDao.updateById(f01ShopDO);
                //????????????????????? ?????????????????? F08ShopUserDO
                F08ShopUserDO f08ShopUserDO = buildF08ShopUserDO(f01ShopDO,numeral);
                f08ShopUserDao.insert(f08ShopUserDO);

                //????????????????????????????????????
                int type=1;
                if(StatusConstant.NewShopType.product.getCode()==f01ShopDO.getType()){
//                    if(f01ShopDO.getMainProject()==3){
//                        type= TypeConstant.ShopRoleType.room_manager.getCode();
//                    }else{
//                        type= TypeConstant.ShopRoleType.goods_manager.getCode();
//                    }
                    type= TypeConstant.ShopRoleType.goods_manager.getCode();
                }else{
                    type= TypeConstant.ShopRoleType.publicity_manager.getCode();
                }
                //??????????????????????????????
                List<F11RoleDO> f11RoleDOS = f11RoleDao.selectList(
                        new EntityWrapper<F11RoleDO>()
                        .eq("type",type)
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
                );
                if(f11RoleDOS==null||f11RoleDOS.isEmpty()){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"???????????????????????????????????????????????????");
                }
                F13RoleShopUserDO dto=new F13RoleShopUserDO();
                dto.setRoleId(f11RoleDOS.get(0).getId());
                dto.setUserId(f08ShopUserDO.getId());
                dto.setCreateTime(LocalDateTime.now());
                dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                Integer insert = f13RoleShopUserDao.insert(dto);
                if(insert<=0){
                    throw new JsonException(ResultEnum.INSERT_FAIL,"?????????????????????????????????");
                }
                F05ShopAccountDO dto1=new F05ShopAccountDO();
                dto1.setShopId(f01ShopDO.getId());
                dto1.setAvailableBalance(BigDecimal.valueOf(0));
                dto1.setAccountBalance(BigDecimal.valueOf(0));
                dto1.setAvailableWithdrawMoney(BigDecimal.valueOf(0));
                dto1.setHasWithdrawMoney(BigDecimal.valueOf(0));
                dto1.setInWithdrawMoney(BigDecimal.valueOf(0));
                dto1.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                Integer insert1 = f05ShopAccountDao.insert(dto1);
                //????????????
                //pushPassMessage(f01ShopDO);
                //???????????? ?????????${name},????????????${shop}??????????????????,??????????????????:${password},???????????????????????????http://business.youqiancheng.vip ????????????!

                JSONObject json = new JSONObject();
                json.put("name",f01ShopDO.getContacts());
                json.put("shop","??????");//f01ShopDO.getName()
                json.put("password",numeral);
                String information = sendSmsUtil.aliSendSmsTwo(b01UserDO1.getMobile(), json.toJSONString(), TypeConstant.ShortMessageType.checkbusinessCode.getCode());
                String key=f01ShopDO.getPhone()+TypeConstant.ShortMessageType.userRegisterCode.getCode()+TypeConstant.PlatformType.pc.getCode();
                sendSmsUtil.saveMap(key,numeral);
//        session.setAttribute(key,numeral);
//        session.setMaxInactiveInterval(3 * 60);
                if(information.equals("ok")){
                    log.info("?????????????????? ?????????????????????????????????");
                }else{
                    log.info("?????????????????? ??????????????????");
                }

            }
            else{
                f01ShopDO.setExamineStatus(shopPassRefuseForm.getStatus());
                f01ShopDO.setReason(shopPassRefuseForm.getReason());
                f01ShopDO.setUpdateTime(LocalDateTime.now());
                shopDao.updateById(f01ShopDO);
                pushRefuseMessage(f01ShopDO);
                //?????????????????? ?????????${name},??????${shop}???????????????,??????????????????????????????!
                JSONObject json = new JSONObject();
                json.put("name",f01ShopDO.getContacts());
                json.put("shop","");//f01ShopDO.getName()
                String information = sendSmsUtil.aliSendSmsTwo(b01UserDO1.getMobile(), json.toJSONString(), TypeConstant.ShortMessageType.checkbusinessUnCode.getCode());
                if(information.equals("ok")){
                    log.info(b01UserDO1.getMobile()+"???????????????????????? ?????????????????????????????????");
                }else{
                    log.info(b01UserDO1.getMobile()+"????????????????????? ??????????????????");
                }
            }
        }
        return 1;
    }
    private void pushPassMessage(F01ShopDO f01ShopDO){
        //?????????????????????????????????
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("??????????????????:???????????????123456????????????????????????");
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
    private void pushRefuseMessage(F01ShopDO f01ShopDO){
        //?????????????????????????????????
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("?????????????????????????????????"+f01ShopDO.getReason());
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

    private F08ShopUserDO buildF08ShopUserDO(F01ShopDO f01ShopDO,String password){
        F08ShopUserDO f08ShopUserDO = new F08ShopUserDO();
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"?????????????????????");
        }
        f08ShopUserDO.setUserName(b01UserDO1.getMobile());
        f08ShopUserDO.setStatus(StatusConstant.enable.getCode());
        f08ShopUserDO.setShopId(f01ShopDO.getId());
        f08ShopUserDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f08ShopUserDO.setPwd(PasswordUtils.authAdminPwd(password));
        f08ShopUserDO.setShopName(f01ShopDO.getName());
        f08ShopUserDO.setUpdateTime(LocalDateTime.now());
        f08ShopUserDO.setCreateTime(LocalDateTime.now());
        f08ShopUserDO.setCreatePerson(b01UserDO1.getMobile());
        f08ShopUserDO.setUpdatePerson(b01UserDO1.getMobile());

        return f08ShopUserDO;
    }





    /**
    ???* @Description: ????????????????????????
    ???* @author shalongteng
    ???* @date 2020/4/9 9:14
    ???
     * @param map*/
    @Override
    public List<F03MainProjectDO> listMainProjectHDPage(Map<String, Object> map) {

        return f03MainProjectDao.listHDPage(map);
    }
    /**
    ???* @Description: ${todo}
    ???* @author shalongteng
    ???* @date 2020/4/9 9:47
    ???*/
    @Override
    public void addMainProject(MainProjectSaveForm mainProjectSaveForm) {
        F03MainProjectDO f03MainProjectDO = new F03MainProjectDO();
        //????????????????????????
        EntityWrapper<F03MainProjectDO> ew = new EntityWrapper<>();
        if(!StringUtil.isNullOrEmpty(mainProjectSaveForm.getName())){
            ew.eq("name",mainProjectSaveForm.getName());
        }
        List<F03MainProjectDO> mainProjectDOList = f03MainProjectDao.selectList(ew);
        if(mainProjectDOList.size() > 0){
            throw new JsonException(ResultEnum.NAME_FAIL);
        }
        BeanUtils.copyProperties(mainProjectSaveForm,f03MainProjectDO);
        f03MainProjectDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f03MainProjectDao.insert(f03MainProjectDO);
    }
    /**
    ???* @Description: ????????????
    ???* @author shalongteng
    ???* @date 2020/4/13 13:33
    ???*/
    @Override
    public void editMainProject(MainProjectEditForm mainProjectEditForm) {
        F03MainProjectDO f03MainProjectDO = f03MainProjectDao.selectById(mainProjectEditForm.getId());
        if(f03MainProjectDO == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        BeanUtils.copyProperties(mainProjectEditForm,f03MainProjectDO);
        f03MainProjectDO.setUpdateTime(LocalDateTime.now());
        f03MainProjectDao.updateById(f03MainProjectDO);
    }
    /**
    ???* @Description: ????????????????????????
    ???* @author shalongteng
    ???* @date 2020/4/13 15:21
    ???
     * @param mainProjectDeleteFormList*/
    @Override
    public Integer deleteMainProject(List<MainProjectDeleteForm> mainProjectDeleteFormList) {
        for (MainProjectDeleteForm mainProjectDeleteForm : mainProjectDeleteFormList) {
            F03MainProjectDO f03MainProjectDO = f03MainProjectDao.selectById(mainProjectDeleteForm.getId());
            if(f03MainProjectDO == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            f03MainProjectDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
            f03MainProjectDO.setUpdateTime(LocalDateTime.now());
            f03MainProjectDao.updateById(f03MainProjectDO);
        }
        return 1;
    }
    /**
    ???* @Description: ????????????
    ???* @author shalongteng
    ???* @date 2020/4/13 15:36
    ???*/
    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return f03MainProjectDao.updateStatus(param);
    }
    /**
    ???* @Description:????????????
    ???* @author shalongteng
    ???* @date 2020/4/13 15:36
    ???*/
    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return f03MainProjectDao.updateStatus(param);
    }

    @Override
    public void addOrEditAudit(ExamineSaveEditForm examineSaveEditForm) {
        f04ShopExamineSetDao.delete(null);
        F04ShopExamineSetDO f04ShopExamineSetDO = new F04ShopExamineSetDO();
        BeanUtils.copyProperties(examineSaveEditForm,f04ShopExamineSetDO);

        f04ShopExamineSetDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f04ShopExamineSetDO.setCreateTime(LocalDateTime.now());
        A01Admin admin = (A01Admin) SpringContextUtil.getHttpServletRequest().getAttribute("admin");
        f04ShopExamineSetDO.setCreatePerson(admin.getUserName());
        f04ShopExamineSetDao.insert(f04ShopExamineSetDO);
    }
    /**
    ???* @Description: ??????????????????
    ???* @author shalongteng
    ???* @date 2020/4/14 15:06
    ???*/
    @Override
    public F04ShopExamineSetDO getAudit() {
        List<F04ShopExamineSetDO> f04ShopExamineSetDOSList= f04ShopExamineSetDao.selectList(null);
        if(f04ShopExamineSetDOSList== null || f04ShopExamineSetDOSList.size() == 0){
            return null;
        }
        return f04ShopExamineSetDOSList.get(0);
    }


    /**
    ???* @Description: ????????????????????????
    ???* @author shalongteng
    ???* @date 2020/4/14 17:20
     * @param map
     * @return*/
    @Override
    public List<F08ShopUserDO> listShopAccountHDPage(Map<String, Object> map) {

        return f08ShopUserDao.listHDPage(map);
    }
    /**
    ???* @Description: ??????/??????????????????
    ???* @author shalongteng
    ???* @date 2020/4/15 15:26
    ???*/
    @Override
    public void saveOrupdateShopUser(ShopUsertSaveOrUpdateForm shopUsertSaveOrUpdateForm) {
        F08ShopUserDO f08ShopUserDO = f08ShopUserDao.selectById(shopUsertSaveOrUpdateForm.getId());
        if(f08ShopUserDO == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        BeanUtils.copyProperties(shopUsertSaveOrUpdateForm,f08ShopUserDO);
        f08ShopUserDO.setPwd(PasswordUtils.authAdminPwd(f08ShopUserDO.getPwd()));
        f08ShopUserDO.setUpdateTime(LocalDateTime.now());
        f08ShopUserDao.updateById(f08ShopUserDO);
    }

    @Override
    public Integer batchPassRefuseShopUser(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            F08ShopUserDO f08ShopUserDO = f08ShopUserDao.selectById(shopPassRefuseForm.getId());
            if(f08ShopUserDO == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            f08ShopUserDO.setStatus(shopPassRefuseForm.getStatus());
            f08ShopUserDO.setUpdateTime(LocalDateTime.now());
            F01ShopDO f01ShopDO = shopDao.get(f08ShopUserDO.getShopId());
            f01ShopDO.setStatus(shopPassRefuseForm.getStatus());
            shopDao.updateById(f01ShopDO);
            f08ShopUserDao.updateById(f08ShopUserDO);

        }
        return 1;
    }

    @Override
    public Integer saveFlow() {
        List<F15ShopProfitDO> list = f15ShopProfitDao.list(new HashMap<>());
        List<F16ShopProfitFlowsDO> flows=new ArrayList<>();
        for (F15ShopProfitDO f15ShopProfitDO : list) {
            F16ShopProfitFlowsDO dto=new F16ShopProfitFlowsDO();
            dto.setCreatePerson("admin");
            dto.setCreateTime(LocalDateTime.now());
            dto.setUpdatePerson("admin");
            dto.setUpdateTime(LocalDateTime.now());
            dto.setDayIncome(f15ShopProfitDO.getDayIncome());
            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            dto.setIncomeDay(LocalDateTime.now());
            dto.setShopId(f15ShopProfitDO.getShopId());
            flows.add(dto);
        }
        int i = f16ShopProfitFlowsDao.insertBatch(flows);
        return i;
    }

    @Override
    public F01ShopDO getInfo(Long id) {
        F01ShopDO f01ShopDO = shopDao.get(id);
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"???????????????");
        }
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",id);
        List<F02ShopPicDO> list = f02ShopPicDao.list(map);
        F02ShopPicDO f02ShopPicDO = new F02ShopPicDO();
        f02ShopPicDO.setCreateTime(f01ShopDO.getCreateTime());
        f02ShopPicDO.setPicUrl(f01ShopDO.getLogo());
        f02ShopPicDO.setType(99);
        f02ShopPicDO.setShopId(f01ShopDO.getId());
        list.add(f02ShopPicDO);
        f01ShopDO.setPicList(list);
        return f01ShopDO;
    }

    @Override
    public int update(F01ShopUpdateManageDO dto) {
        F01ShopDO f01ShopDO = shopDao.get(dto.getId());
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"???????????????");
        }
        BeanUtils.copyProperties(dto,f01ShopDO);
        Integer integer = shopDao.updateById(f01ShopDO);
        try {
            B07AuthenticationDO b07AuthenticationDO = new B07AuthenticationDO();
            b07AuthenticationDO.setUserId(f01ShopDO.getUserId());
            b07AuthenticationDO.setName(dto.getContacts());
            b07AuthenticationDao.update(b07AuthenticationDO, new EntityWrapper<B07AuthenticationDO>().eq("user_id", f01ShopDO.getUserId()));
        }catch (Exception e){
            log.info("????????????????????????......");
        }
        //??????????????????
        //???????????????
        List<F02ShopPicDO> f02ShopPics=new ArrayList<>();
        for (String str : dto.getIdCardList()) {
            F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
            f02ShopPicDO.setShopId(dto.getId());
            f02ShopPicDO.setPicUrl(str);
            f02ShopPicDO.setType(StatusConstant.ShopPicType.id_card.getCode());
            //????????????
            f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            f02ShopPicDO.setCreateTime(LocalDateTime.now());
            f02ShopPicDO.setUpdateTime(LocalDateTime.now());

            f02ShopPics.add(f02ShopPicDO);
        }
        //????????????
        for (String str : dto.getOtherInfoList()) {
            F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
            f02ShopPicDO.setShopId(dto.getId());
            f02ShopPicDO.setPicUrl(str);
            f02ShopPicDO.setType(StatusConstant.ShopPicType.other.getCode());
            //????????????
            f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            f02ShopPicDO.setCreateTime(LocalDateTime.now());
            f02ShopPicDO.setUpdateTime(LocalDateTime.now());

            f02ShopPics.add(f02ShopPicDO);
        }
        //??????????????????
        for (String str : dto.getLicenseList()) {
            F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
            f02ShopPicDO.setShopId(dto.getId());
            f02ShopPicDO.setPicUrl(str);
            f02ShopPicDO.setType(StatusConstant.ShopPicType.license.getCode());
            //????????????
            f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            f02ShopPicDO.setCreateTime(LocalDateTime.now());
            f02ShopPicDO.setUpdateTime(LocalDateTime.now());

            f02ShopPics.add(f02ShopPicDO);
        }
        if(!CollectionUtils.isEmpty(f02ShopPics)){
            f02ShopPicDao.delete(
                    new EntityWrapper<F02ShopPicDO>()
                    .eq("shop_id",dto.getId())
            );
            int i = f02ShopPicDao.insertBatch(f02ShopPics);
            if(i<=0){
                throw new JsonException(ResultEnum.INSERT_FAIL,"??????????????????????????????");
            }
        }
        return integer;
    }
}
