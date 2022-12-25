package com.youqiancheng.controller.client;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.youqiancheng.ability.OcrAbility;
import com.youqiancheng.ability.PsnApiVerifyAbility;
import com.youqiancheng.form.app.E04GrantRecordSearchForm;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.ShopSendRedEnvelopesAbility;
import com.youqiancheng.form.client.*;
import com.youqiancheng.mybatis.dao.B07AuthenticationDao;
import com.youqiancheng.mybatis.dao.E03RedenvelopesRuleDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.bootpage.A12ServiceAgreementAppService;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.StringUtil;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.UserWithShopVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Api(tags = {"PC端-我的"})
@RestController
@RequestMapping(value = "pc/my")
public class MyClientController {
    @Autowired
    private A08ContactUsClientService a08ContactUsService;
    @Autowired
    private A14UserKnowClientService a14UserKnowService;
    @Autowired
    private B01UserClientService  b01UserClientService;
    @Autowired
    private B02UserAccountClientService b02UserAccountService;
    @Autowired
    private B03UserAccountFlowClientService b03UserAccountFlowAppService;
    @Autowired
    private B06UserAddressClientService b06UserAddressService;
    @Autowired
    private B07AuthenticationClientService b07AuthenticationService;
    @Autowired
    private B05CollectionClientService b05CollectionService;
    @Autowired
    private C04GoodsExamineSetClientService c04GoodsExamineSetService;
    @Autowired
    private F04ShopExamineSetClientService f04ShopExamineSetService;
    @Autowired
    private F01ShopClientService f01ShopClientService;
    @Autowired
    private E01RedenvelopesStreetClientService e01RedenvelopesStreetService;
    @Autowired
    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordClientService;
    @Autowired
    private A12ServiceAgreementAppService a12ServiceAgreementService;
    @Autowired
    private E03RedenvelopesRuleDao e03RedenvelopesRuleDao;

    @Autowired
    private  F08ShopClienService f08ShopClienService;
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private ShopSendRedEnvelopesAbility shopSendRedEnvelopesAbility;
    @Autowired
    private OcrAbility ocrAbility;

    @Resource
    private PsnApiVerifyAbility psnApiVerifyAbility;

    @ApiOperation(value = "根据当前用户ID获取用户信息")
    @GetMapping("/getUerInfoByUserId")
    //@AuthRuleAnnotation()
    ResultVo getUerInfoByUserId(Long id){
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"参数不能为空");
        }
        B01UserDO b01UserDO = b01UserClientService.get(id);
        UserWithShopVO vo=new UserWithShopVO();
        vo.setMobile(b01UserDO.getMobile());
        vo.setNick(b01UserDO.getNick());
        vo.setPic(b01UserDO.getPic());
        vo.setUserId(b01UserDO.getId());
        vo.setIsShop(b01UserDO.getIsShop());
        vo.setShopId(b01UserDO.getShopId());
        if(TypeConstant.isShop.yes.getCode()==b01UserDO.getIsShop() ){
            F01ShopDO f01ShopDO = f01ShopClientService.get(b01UserDO.getShopId());
            if(f01ShopDO==null){
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"商家信息为空");
            }
            if(StatusConstant.ExamineStatus.adopt.getCode()==f01ShopDO.getExamineStatus()){
                vo.setShopId(f01ShopDO.getId());
                vo.setShopType(f01ShopDO.getType());
                vo.setShopName(f01ShopDO.getName());
                vo.setIsShow(TypeConstant.isShow.yes.getCode());
                vo.setShopState(f01ShopDO.getStatus());
            }else{
                vo.setIsShow(TypeConstant.isShow.no.getCode());
            }
        }
        //封装到分页
        return ResultVOUtils.success(vo);
    }

    /****************************我要上街***********************************/
    @ApiOperation(value = "获取街区列表——我要上街")
    @GetMapping("/getStreetList")
    //@AuthRuleAnnotation()
    ResultVo getStreetList(@Valid E01RedenvelopesStreetSearchForm form){
        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        map.put("status",1);
        List<E01RedenvelopesStreetDO> list = e01RedenvelopesStreetService.list(map);
        //封装到分页
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "保存红包发放记录——我要上街")
    @PostMapping("/saveGrantInfo")
    //@AuthRuleAnnotation()
    ResultVo getStreetList(@Valid E04RedenvelopesGrantRecordSaveForm form ){
       //在上街之前先判断下这个商家是否被冻结
        QueryMap map = new QueryMap();
        map.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        map.put("shopId",form.getShopId());
        F08ShopUserDO f08ShopUserDO = f08ShopClienService.getf08ShopUserDO(map);
        if(f08ShopUserDO==null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.DATA_NOT_EXIST.getMessage());
        }
        //判断商家用户是否被冻结,假如冻结直接提示用户
        if(f08ShopUserDO.getStatus()==StatusConstant.disable.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.ONFREEZE_FAIL.getMessage());
        }
       // long id = e04RedenvelopesGrantRecordClientService.insert(form);
        E04GrantRecordSearchForm form1 = new E04GrantRecordSearchForm();
        BeanUtils.copyProperties(form,form1);
        long id = shopSendRedEnvelopesAbility.saveShopSendRedEnvelopesRecord(form1);
        //封装到分页
        return ResultVOUtils.success(id);
    }

    @ApiOperation(value = "获取红包街规则")
    @GetMapping("/getRedenvelopesRule")
    ResultVo  getredenvelopesRule() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<E03RedenvelopesRuleDO> list = e03RedenvelopesRuleDao.list(map);
        return ResultVOUtils.success(list.get(0));
    }
  /**************************账户余额 **************************************/

    @ApiOperation(value = "获取用户账户列表；参数——用户ID，分页参数")
    @GetMapping("/getAccountListByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B02UserAccountDO>> getUserAccountByUserId(@Valid B02UserAccountSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B02UserAccountDO> b02UserAccountList = b02UserAccountService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B02UserAccountDO> b02UserAccountSimpleVO = new PageSimpleVO<>();
        b02UserAccountSimpleVO.setTotalNumber(page.getTotalNumber());
        b02UserAccountSimpleVO.setList(b02UserAccountList);

        return ResultVOUtils.success(b02UserAccountSimpleVO);
    }

    @ApiOperation(value = "获取账户流水；参数——账户ID")
    @GetMapping("/getAccountFlowByAccountId")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B02UserAccountDO>> getAccountFlowByAccountId(@Valid B03UserAccountFlowSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B03UserAccountFlowDO> b03UserAccountFlowDOS = b03UserAccountFlowAppService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B03UserAccountFlowDO> b02UserAccountSimpleVO = new PageSimpleVO<>();
        b02UserAccountSimpleVO.setTotalNumber(page.getTotalNumber());
        b02UserAccountSimpleVO.setList(b03UserAccountFlowDOS);

        return ResultVOUtils.success(b02UserAccountSimpleVO);
    }

    /**************************用户地址 **************************************/

    @ApiOperation(value = "根据用户ID获取用户地址列表；参数——用户ID,分页参数")
    @GetMapping("/getUserAddressByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B06UserAddressDO>> getUserAddressByUserId(@Valid B06UserAddressSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B06UserAddressDO> b06UserAddressList = b06UserAddressService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B06UserAddressDO> b06UserAddressSimpleVO = new PageSimpleVO<>();
        b06UserAddressSimpleVO.setTotalNumber(page.getTotalNumber());
        b06UserAddressSimpleVO.setList(b06UserAddressList);

        return ResultVOUtils.success(b06UserAddressSimpleVO);
    }

    @ApiOperation(value = "保存用户地址表信息；参数——用户地址保存实体")
    @PostMapping("/saveUserAddress")
    @AuthRuleAnnotation()
    ResultVo saveUserAddress(@RequestBody @Valid B06UserAddressSaveForm dto ) {

        B06UserAddressDO b06UserAddress=new B06UserAddressDO();
        BeanUtils.copyProperties(dto,b06UserAddress);
        b06UserAddress.setCreateTime(LocalDateTime.now());
        b06UserAddress.setUpdateTime(LocalDateTime.now());
        b06UserAddress.setUpdatePerson(dto.getCreatePerson());
        b06UserAddress.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b06UserAddressService.insert(b06UserAddress);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "修改用户地址信息；参数——用户地址修改实体")
    @PutMapping("/updateUserAddress")
    //@AuthRuleAnnotation()
    ResultVo updateUserAddress(@RequestBody @Valid B06UserAddressUpdateForm form ) {

        B06UserAddressDO b06UserAddressDO = b06UserAddressService.get(form.getId());
        if(b06UserAddressDO==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"地址信息不存在");
        }
        if(!StringUtils.isBlank(form.getAddress())){
            b06UserAddressDO.setAddress(form.getAddress());
        }
        if(!StringUtils.isBlank(form.getArea())){
            b06UserAddressDO.setArea(form.getArea());
        }
        if(!StringUtils.isBlank(form.getAreaCode())){
            b06UserAddressDO.setAreaCode(form.getAreaCode());
        }
        if(!StringUtils.isBlank(form.getCityCode())){
            b06UserAddressDO.setCityCode(form.getCityCode());
        }
        if(!StringUtils.isBlank(form.getCity())){
            b06UserAddressDO.setCity(form.getCity());
        }
        if(!StringUtils.isBlank(form.getProvince())){
            b06UserAddressDO.setProvince(form.getProvince());
        }
        if(!StringUtils.isBlank(form.getProvinceCode())){
            b06UserAddressDO.setProvinceCode(form.getProvinceCode());
        }
        if(!StringUtils.isBlank(form.getPhone())){
            b06UserAddressDO.setPhone(form.getPhone());
        }
        if(!StringUtils.isBlank(form.getReceiver())){
            b06UserAddressDO.setReceiver(form.getReceiver());
        }
        if(form.getIsDefault()!=0){
            b06UserAddressDO.setIsDefault(form.getIsDefault());
        }
        b06UserAddressDO.setUpdateTime(LocalDateTime.now());
        int num= b06UserAddressService.update(b06UserAddressDO);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "删除用户地址信息：参数——地址ID")
    @GetMapping("/deleteUserAddress")
    ResultVo deleteUserAddress(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"地址ID不能为空");
        }
        List<Long> list=new ArrayList<>();
        list.add(id);
        int num= b06UserAddressService.stop(list);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    /**************************用户收藏 **************************************/

    @ApiOperation(value = "获取收藏商家列表;参数——用户ID；分页参数")
    @GetMapping("/getShopList")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B05CollectionDO>> getShopList(@Valid B05CollectionSearchForm form ,@Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        List<F01ShopDO> collectionShop = b05CollectionService.getCollectionShop(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> b05CollectionSimpleVO = new PageSimpleVO<>();
        b05CollectionSimpleVO.setTotalNumber(page.getTotalNumber());
        b05CollectionSimpleVO.setList(collectionShop);

        return ResultVOUtils.success(b05CollectionSimpleVO);
    }
    @ApiOperation(value = "获取收藏动态列表;参数——用户ID；分页参数")
    @GetMapping("/getPublicityList")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B05CollectionDO>> getPublicityList(@Valid B05CollectionSearchForm form ,@Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.publicity.getCode());
        List<F01ShopDO> collectionShop = b05CollectionService.getCollectionShop(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> b05CollectionSimpleVO = new PageSimpleVO<>();
        b05CollectionSimpleVO.setTotalNumber(page.getTotalNumber());
        b05CollectionSimpleVO.setList(collectionShop);

        return ResultVOUtils.success(b05CollectionSimpleVO);
    }



    @ApiOperation(value = "获取收藏商品列表;参数——用户ID；分页参数")
    @GetMapping("/getGoodsList")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B05CollectionDO>> getGoodsList(@Valid B05CollectionSearchForm form ,@Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.goods.getCode());
        List<C01GoodsDO> collectionGoods = b05CollectionService.getCollectionGoods(map);
        //封装到分页
        PageSimpleVO<C01GoodsDO> b05CollectionSimpleVO = new PageSimpleVO<>();
        b05CollectionSimpleVO.setTotalNumber(page.getTotalNumber());
        b05CollectionSimpleVO.setList(collectionGoods);

        return ResultVOUtils.success(b05CollectionSimpleVO);
    }


    /**************************实名认证 **************************************/


    @ApiOperation(value = "申请实名认证；参数——认证保存实体")
    @PostMapping("/applyAuthentication")
    @AuthRuleAnnotation()
    ResultVo applyAuthentication(@RequestBody @Valid B07AuthenticationSaveForm dto ) {

        List<B07AuthenticationDO> userList= b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", dto.getUserId()));
        if(userList.size()==0 || userList.get(0).getStatus()==1) {
            if(userList.size() != 0){
                B07AuthenticationDO b07AuthenticationDO = userList.get(0);
                    b07AuthenticationDao.delete(new EntityWrapper<B07AuthenticationDO>().eq("id", b07AuthenticationDO.getId()));
            }

//            String idCardNum = ocrAbility.ocrIdCard(dto.getUrl());
//            if(!idCardNum.equals(dto.getCardNo())){
//                return ResultVOUtils.error(Constants.$Failure,"输入的身份证号与身份证不符！请重新输入");
//            }
            B07AuthenticationDO b07Authentication = new B07AuthenticationDO();
            BeanUtils.copyProperties(dto, b07Authentication);
            b07Authentication.setCreateTime(LocalDateTime.now());
            b07Authentication.setUpdateTime(LocalDateTime.now());
            b07Authentication.setUpdatePerson(dto.getCreatePerson());
            b07Authentication.setStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            b07Authentication.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            b07Authentication.setBankCardNum(dto.getBankCardNum());
            int num = b07AuthenticationService.insert(b07Authentication);
            if (num <= 0) {
                return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
            }
            //调用E签宝接口获得人脸识别地址
            String authUrl = psnApiVerifyAbility.psnVerify(dto.getUserId());
            return ResultVOUtils.success(authUrl);
        }else {
            return ResultVOUtils.error(Constants.$Failure,"该账号已经实名认证");
        }
    }

    @ApiOperation(value = "获取实名认证信息；参数——用户ID；结果——若存在则返回认证信息，不存在返回空")
    @GetMapping("/getAuthenticationByUserId")
    @AuthRuleAnnotation()
    ResultVo getAuthenticationByUserId(@Valid AuthenticationSearchForm dto ) {

//        QueryMap map=new QueryMap(dto,StatusConstant.CreatFlag.delete.getCode());
//        List<B07AuthenticationDO> list = b07AuthenticationService.list(map);
        List<B07AuthenticationDO> list= b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", dto.getUserId()).eq("status",2));
        if(list==null||list.isEmpty()){
            return ResultVOUtils.success();
        }
        B07AuthenticationDO b07AuthenticationDO = list.get(0);
        String cardNo = StringUtil.replaceCardNo(b07AuthenticationDO.getCardNo());
        b07AuthenticationDO.setCardNo(cardNo);
        String bankCardNo = StringUtil.replaceBankCarNo(b07AuthenticationDO.getBankCardNum());
        b07AuthenticationDO.setBankCardNum(bankCardNo);
        return ResultVOUtils.success(b07AuthenticationDO);
    }

    @ApiOperation(value = "修改实名认证；参数——认证修改实体:名字，身份证号，正反面照片")
    @PutMapping("/updateAuthentication")
    @AuthRuleAnnotation()
    ResultVo updateAuthentication(@RequestBody @Valid B07AuthenticationUpdateForm form ) {

        B07AuthenticationDO b07AuthenticationDO = b07AuthenticationService.get(form.getId());
        if(b07AuthenticationDO==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"认证信息不存在");
        }
        if(!StringUtils.isBlank(form.getBackUrl())){
            b07AuthenticationDO.setBackUrl(form.getBackUrl());
        }
        if(!StringUtils.isBlank(form.getUrl())){
            b07AuthenticationDO.setUrl(form.getUrl());
        }
        if(!StringUtils.isBlank(form.getCardNo())){
            b07AuthenticationDO.setCardNo(form.getCardNo());
        }
        if(!StringUtils.isBlank(form.getName())){
            b07AuthenticationDO.setName(form.getName());
        }
        b07AuthenticationDO.setUpdateTime(LocalDateTime.now());
        int num= b07AuthenticationService.update(b07AuthenticationDO);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    /**************************联系我们 **************************************/

    @ApiOperation(value = "获取联系我们")
    @GetMapping("/getContactUs")
    ResultVo getContactUs( ) {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<A08ContactUsDO> list = a08ContactUsService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到联系我们信息");
        }
        return ResultVOUtils.success(list.get(0));
    }


    /**************************用户须知 **************************************/

    @ApiOperation(value = "获取用户须知")
    @GetMapping("/getUserNeedKnow")
    ResultVo getUserNeedKnow() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<A14UserKnowDO> list = a14UserKnowService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到用户须知信息");
        }
        return ResultVOUtils.success(list.get(0));
    }

    /**************************购物须知 **************************************/
    @ApiOperation(value = "获取购物须知")
    @GetMapping("/getBuyNeedKnow")
    ResultVo getBuyNeedKnow() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<C04GoodsExamineSetDO> list = c04GoodsExamineSetService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到购物须知信息");
        }
        return ResultVOUtils.success(list.get(0));
    }


    /**************************入住须知 **************************************/

    @ApiOperation(value = "获取入住须知")
    @GetMapping("/getSettleInNeedKnow")
    ResultVo getSettleInNeedKnow() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<F04ShopExamineSetDO> list = f04ShopExamineSetService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到入驻须知信息");
        }
        return ResultVOUtils.success(list.get(0));
    }
    /**
     *
     * @author      nl
     * @return      获取服务协议
     * @date        2020/4/3 10:41
     */
    @ApiOperation(value = "获取服务协议")
    @GetMapping("/getClientSlaInfo")
    public ResultVo getSlaInfo(){
        A12ServiceAgreementDO slaInfo=a12ServiceAgreementService.getSlaInfo();
        return ResultVOUtils.success(slaInfo);
    }

}
