package com.youqiancheng.controller.shop;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.CustomerServicePageForm;
import com.youqiancheng.form.shop.CustomerServiceUpdateForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.shop.CustomerServiceService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:16
 * @Version: V1.0
 */
@Api(tags = "商家管理--售后管理")
@RestController
@RequestMapping("shop/customerService")
public class CustomerServiceController {
    @Resource
    private CustomerServiceService customerServiceService;


    @ApiOperation(value = "售后列表", notes = "售后列表")
    @PostMapping(value = "/pageOrders")
    public ResultVo<PageSimpleVO<D01OrderDO>> pageOrders( @Valid CustomerServicePageForm form, @Valid EntyPage page ){

        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",shopUser.getShopId());
        List<D03CustomerServiceDO> d03CustomerServiceDOS = customerServiceService.listHDPage(map);

        //封装到分页
        PageSimpleVO<D03CustomerServiceDO> simpleVO = new PageSimpleVO<>();
        simpleVO.setTotalNumber(page.getTotalNumber());
        simpleVO.setList(d03CustomerServiceDOS);
        return ResultVOUtils.success(simpleVO);
    }



    @ApiOperation(value = "审核通过")
    @PostMapping(value = "/examineOrderPass")
    public ResultVo examineOrderPass(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderPass(form);
        return ResultVOUtils.success();
    }


    @ApiOperation(value = "审核拒绝")
    @PostMapping(value = "/examineOrderRefuse")
    public ResultVo examineOrderRefuse(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderRefuse(form);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "退款：参数——退款ID")
    @PostMapping(value = "/refundOrder")
    public ResultVo refundOrder(Long  id){
      if(id==null||id==0){
          return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数不能为空");
      }
        ResultVo vo =   customerServiceService.refundOrder(id);
        return vo;
    }

}


