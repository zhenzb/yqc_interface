package com.youqiancheng.controller.app;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.ShopOrderSendForm;
import com.youqiancheng.mybatis.dao.A07HelpCenterDao;
import com.youqiancheng.mybatis.dao.F07ShopAccountFlowDao;
import com.youqiancheng.mybatis.dao.F09WithdrawalSetDao;
import com.youqiancheng.mybatis.model.A07HelpCenterDO;
import com.youqiancheng.mybatis.model.A19ExpressDO;
import com.youqiancheng.mybatis.model.F07ShopAccountFlowDO;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "提现须知")
@RestController
@RequestMapping("cashwithdrawal")
public class CashWithdrawalNotice {

   @Autowired
   private F09WithdrawalSetDao f09WithdrawalSetDao;
    @Autowired
    private A07HelpCenterDao a07HelpCenterDao;
    @Autowired
    private ShopOrderService shopOrderService;

    @ApiOperation(value = "获取提现须知")
    @GetMapping("/getWithdrawalContent")
    ResultVo getCashWithdrawalNotice(){
        QueryMap map = new QueryMap();
        map.put("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        //通过没被删除的去查提现须知
        List<F09WithdrawalSetDO> f09List=f09WithdrawalSetDao.list(map);
        return ResultVOUtils.success(f09List.get(0));

    }
    @ApiOperation(value = "隐私政策")
    @GetMapping("/privacyPolicy")
    ResultVo getprivacyPolicy(){
        QueryMap map = new QueryMap();
        map.put("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        //通过没被删除的去查隐私政策
        List<A07HelpCenterDO> f07list= a07HelpCenterDao.list(map);
        return ResultVOUtils.success(f07list.get(0));

    }

    @ApiOperation(value = "搜索——获取快递公司名称或者快递公司编号；参数——快递公司名称, 编号类型等")
    @GetMapping("/appsearchCourierServicesCompanyList")
    ResultVo<A19ExpressDO> searchShopList(@Valid A19ExpressDO a19ExpressDO ) {
        QueryMap map = new QueryMap(a19ExpressDO, StatusConstant.CreatFlag.delete.getCode());
        List<A19ExpressDO> a19ExpressDOList = shopOrderService.getCourierServicesCompanylist(map);
        return ResultVOUtils.success(a19ExpressDOList);
    }

    @ApiOperation(value = "订单发货")
    @PostMapping(value = "/appOrderStatusById")
    public ResultVo editOrderStatusById(@Valid ShopOrderSendForm form){
        return ResultVOUtils.success(shopOrderService.send(form));
    }

}
