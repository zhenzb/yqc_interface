package com.youqiancheng.controller.client;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.client.C10PublicitySearchForm;
import com.youqiancheng.form.client.ShopOrderSearchForm;
import com.youqiancheng.form.shop.CustomerServiceUpdateForm;
import com.youqiancheng.form.shop.F06WithdrawalApplicationSaveForm;
import com.youqiancheng.mybatis.dao.F09WithdrawalSetDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.F08ShopAppService;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.service.shop.CustomerServiceService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.D01OrderDOVo;
import com.youqiancheng.vo.client.D02OrderItemClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import com.youqiancheng.vo.shop.WithdrawalParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Api(tags = {"PC端-我的店铺"})
@RestController
@RequestMapping(value = "pc/myShop")
public class MyShopController {
    @Autowired
    private F01ShopClientService   f01ShopClientService;
    @Autowired
    private F05ShopAccountService  f05ShopAccountService;
    @Autowired
    private D01OrderClientService  d01OrderClientService;
    @Autowired
    private C01GoodsClientService   c01GoodsClientService;
    @Autowired
    private D02OrderItemClientService d02OrderItemClientService;
    @Resource
    private CustomerServiceService customerServiceService;
    @Resource
    private F09WithdrawalSetDao f09WithdrawalSetDao;
    @Autowired
    private F08ShopAppService f08ShopAppService;
    @Autowired
    private C10PublicityClientService c10PublicityService;

    @ApiOperation(value = "根据商家获取商家的宣传图文信息列表——音频2，图文3；参数——商家ID")
    @GetMapping("/getPublicityByShopId")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<C10PublicityDO>> getPublicityByShopId(@Valid C10PublicitySearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        //map.put("type",StatusConstant.PublicityType.pic.getCode());
        List<C10PublicityDO> c10PublicityList = c10PublicityService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C10PublicityDO> c10PublicitySimpleVO = new PageSimpleVO<>();
        c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
        c10PublicitySimpleVO.setList(c10PublicityList);

        return ResultVOUtils.success(c10PublicitySimpleVO);
    }

    @ApiOperation(value = "根据商家获取商家的宣传视频信息;参数——商家ID")
    @GetMapping("/getVideoByShopId")
    @AuthRuleAnnotation()
    ResultVo getVideoByShopId(@Valid C10PublicitySearchForm form  ) {

        QueryMap map = new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        map.put("type",StatusConstant.PublicityType.video.getCode());
        List<C10PublicityDO> list = c10PublicityService.list(map);
        if(CollectionUtils.isEmpty(list)) {
            return  ResultVOUtils.success();
        }
        return ResultVOUtils.success(list.get(0));
    }

    @ApiOperation(value = "根据宣传ID获取宣传详情；参数——宣传ID")
    @GetMapping("/getInfoById")
    ResultVo getInfoById( Long id ){
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"宣传信息不能为空");
        }
        C10PublicityDO c10Publicity = c10PublicityService.get(id);
        return ResultVOUtils.success(c10Publicity);
    }


    @ApiOperation(value = "删除宣传")
    @PostMapping("/deletePublicity")
    @AuthRuleAnnotation()
    ResultVo deletePublicity(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"宣传ID不为空");
        }
        List<Long> list=new ArrayList<>();
        list.add(id);
        int stop = c10PublicityService.stop(list);
        //封装到分页
        return ResultVOUtils.success(stop);
    }

    @ApiOperation(value = "根据商家ID查询,商家余额信息,商家今日收益,昨日收益,总收益：参数——商家ID")
    @GetMapping("/getShopAccountInfoById")
    @AuthRuleAnnotation()
    ResultVo getShopAccountInfo(Long id) {
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家ID不能为空");
        }
        F05ShopAccountDO profit = f01ShopClientService.getProfit(id);
//        if(profit.getStatus() == 1){
//            return ResultVOUtils.error(Constants.$Failure,Constants.SHOPNAME_EXISTENCE_FROZEN);
//        }
        return  ResultVOUtils.success(profit);

    }
    @ApiOperation(value = "用户获取订单列表；参数——用户ID,订单状态，是否评价，分页参数")
    @GetMapping("/getOrderListByStatus")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<D01OrderDO>> getOrderListByStatus(@Valid ShopOrderSearchForm form, @Valid EntyPage page ) {

        //全部、待支付订单——(全部0)：1:待支付;
        if (form.getOrderStatus() < StatusConstant.OrderStatus.end.getCode()) {
            //通过商家id获取订单的信息===>信息有订单编号,发货状态,图片,商品名称,规格,价格,数量,总价
            //List<ShopOrderVO> shopOrderVO = d01OrderClientService.getOrderByShopId(id);
            QueryMap  map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
            List<D01OrderDOVo> list=  d01OrderClientService.getOrderByShopId(map);
            //封装到分页
            PageSimpleVO<D01OrderDOVo> d01OrderEvaluateSimpleVO = new PageSimpleVO<>();
            d01OrderEvaluateSimpleVO.setTotalNumber(page.getTotalNumber());
            d01OrderEvaluateSimpleVO.setList(list);
            return ResultVOUtils.success(d01OrderEvaluateSimpleVO);
        } else {
            if (StatusConstant.OrderStatus.end.getCode() == form.getOrderStatus()) {
                QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
                List<D02OrderItemClientVO> d02OrderItemVOs = d02OrderItemClientService.listHDPage(map);
                //封装到分页
                PageSimpleVO<D02OrderItemClientVO> d01OrderSimpleVO = new PageSimpleVO<>();
                d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
                d01OrderSimpleVO.setList(d02OrderItemVOs);

                return ResultVOUtils.success(d01OrderSimpleVO);
            }
            else {
                QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
                List<D02OrderItemClientVO> d02OrderItemVOs = d02OrderItemClientService.list5HDPage(map);
                //封装到分页
                PageSimpleVO<D02OrderItemClientVO> d01OrderSimpleVO = new PageSimpleVO<>();
                d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
                d01OrderSimpleVO.setList(d02OrderItemVOs);
                return ResultVOUtils.success(d01OrderSimpleVO);
            }
        }

    }

//    @ApiOperation(value = "根据商家ID查询,商家订单信息：参数——商家ID;1，待支付，2，待发货，3待收货，4 已完成")
//    @PostMapping("/getOrderById")
//    @AuthRuleAnnotation()
//    ResultVo<PageSimpleVO<D01OrderDO>>  getOrderById(@Valid ShopOrderSearchForm form, @Valid EntyPage page ) {
//
//        //通过商家id获取订单的信息===>信息有订单编号,发货状态,图片,商品名称,规格,价格,数量,总价
//        //List<ShopOrderVO> shopOrderVO = d01OrderClientService.getOrderByShopId(id);
//        QueryMap  map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
//        List<D01OrderDOVo> list=  d01OrderClientService.getOrderByShopId(map);
//        //封装到分页
//        PageSimpleVO<D01OrderDOVo> d01OrderEvaluateSimpleVO = new PageSimpleVO<>();
//        d01OrderEvaluateSimpleVO.setTotalNumber(page.getTotalNumber());
//        d01OrderEvaluateSimpleVO.setList(list);
//
//        return ResultVOUtils.success(d01OrderEvaluateSimpleVO);
//    }


    @ApiOperation(value = "根据商家ID查询,商家的商品：参数——商家ID")
    @GetMapping("/getGoodsInfoById")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<C01GoodsDO>> getGoodsInfo(Long id,@Valid EntyPage page) {

        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",id);
        //通过商家id获取这家商品的信息yId
        List<C01GoodsDO>  vo = c01GoodsClientService.getGoodsById(map);
        PageSimpleVO<C01GoodsDO> c01PageSimpleVO = new PageSimpleVO<>();
        c01PageSimpleVO.setTotalNumber(page.getTotalNumber());
        c01PageSimpleVO.setList(vo);
        return  ResultVOUtils.success(c01PageSimpleVO);

    }


    @ApiOperation(value = "发起提现申请", notes = "发起提现申请")
    @PostMapping(value = "/creatWithdrawalApplication")
    @AuthRuleAnnotation()
    public ResultVo creatWithdrawalApplication(@Valid @RequestBody F06WithdrawalApplicationSaveForm dto){

        if(dto == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        //通过提现申请的账号id查询账号信息
        F05ShopAccountDO f05ShopAccountDO = f05ShopAccountService.get(dto.getAccountId());
        QueryMap queryMap = new QueryMap();
        queryMap.put("shopId",f05ShopAccountDO.getShopId());
        queryMap.put("delete_flag",StatusConstant.CreatFlag.delete.getCode());
        //通过账号信息里的商家id去查商家账号的用户信息
        F08ShopUserDO f08ShopUserDO= f08ShopAppService.getf08ShopUserDO(queryMap);
        //判断有没有这商家用户
        if(f08ShopUserDO==null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.DATA_NOT_EXIST.getMessage());
        }
        //判断商家用户是否被冻结,假如冻结直接提示用户
        if(f08ShopUserDO.getStatus()==StatusConstant.disable.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.ONFREEZE_FAIL.getMessage());
        }
        if(dto.getWithdrawalMoney().compareTo(f05ShopAccountDO.getAvailableWithdrawMoney())>0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"提现金额不能大于可提现金额");
        }
        //计算实际提现到账金额
        //BigDecimal bigDecimal = DecimalUtil.multiplicationBigMal(String.valueOf(dto.getWithdrawalMoney()), String.valueOf(dto.getActualServiceRatio()), 2, null);
        BigDecimal bigDecimal =  DecimalUtil.subtractionBigMal(String.valueOf(dto.getWithdrawalMoney()),String.valueOf(dto.getServiceMoney()),0,0);
        F06WithdrawalApplicationDO entity=new F06WithdrawalApplicationDO();
        BeanUtils.copyProperties(dto,entity);
        entity.setActualWithdrawalMoney(bigDecimal);//实际提现金额
        entity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        entity.setApplyTime(LocalDateTime.now());
        return ResultVOUtils.success(f05ShopAccountService.save(entity));
    }

    @ApiOperation(value = "获取提现参数——账户ID")
    @PostMapping(value = "/getWithdrawalParam")
    @AuthRuleAnnotation()
    public ResultVo getWithdrawalParam(Long id){
        WithdrawalParamVO vo=f05ShopAccountService.getWithdrawalParam(id);
        return ResultVOUtils.success(vo);
    }

    @ApiOperation(value = "批量发货订单状态")
    @PostMapping(value = "/send")
    @AuthRuleAnnotation()
    public ResultVo send(Long id){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数未能为空");
        }
        List<Long> ids=new ArrayList<>();
        ids.add(id);
        int send = d01OrderClientService.send(ids);
        return ResultVOUtils.success(send);

    }

    @ApiOperation(value = "审核通过")
    @PostMapping(value = "/examineOrderPass")
    @AuthRuleAnnotation()
    public ResultVo examineOrderPass(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderPass(form);

        return ResultVOUtils.success();
    }
    @ApiOperation(value = "审核拒绝")
    @PostMapping(value = "/examineOrderRefuse")
    @AuthRuleAnnotation()
    public ResultVo examineOrderRefuse(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderRefuse(form);
        return ResultVOUtils.success();
    }
    @ApiOperation(value = "提现须知")
    @GetMapping(value = "/getWithdrawalNotice")
    @AuthRuleAnnotation()
    public ResultVo getWithdrawalNotice(){
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<F09WithdrawalSetDO> list = f09WithdrawalSetDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"数据不存在");
        }
        return ResultVOUtils.success(list.get(0));
    }

}
