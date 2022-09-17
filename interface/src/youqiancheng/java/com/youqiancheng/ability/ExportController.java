package com.youqiancheng.ability;


import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.PoiUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.otther.ShopOrderPageForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.util.BuildOrderAddressUtil;
import com.youqiancheng.vo.result.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "pc/export")
public class ExportController {

    @Resource
    private ShopOrderService shopOrderService;
    @Autowired
    private BuildOrderAddressUtil buildOrderAddressUtil;

    @RequestMapping("/exportOrder")
    public ResponseEntity<byte[]> exportOrder(String sid,String nameOrNo,String startTime,String endTime,String orderStatus){
        ShopOrderPageForm shopOrderPageForm = new ShopOrderPageForm();
        shopOrderPageForm.setNameOrNo(nameOrNo);
        shopOrderPageForm.setStartTime(startTime);
        shopOrderPageForm.setEndTime(endTime);
        if(!"".equals(orderStatus)){
            shopOrderPageForm.setOrderStatus(Integer.valueOf(orderStatus));
        }
        String[] title = new String[]{"订单号","订单状态","收货人姓名","联系电话","收货地址","单价","数量"};
        Map<String,Object> map = new HashMap<>();
//        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
//        if (shopUser == null){
//            throw new JsonException(Constants.$Null, "登录超时");
//        }
        EntyPage page = new EntyPage();
        page.setCurrentPage(0);
        page.setPageSize(2000);

        map.put("shopId",sid);
        map.put("shopOrderPageForm", shopOrderPageForm);
        map.put("page", page);
        List<D01OrderDO> orderlist = shopOrderService.listOrderHDPage(map);
        buildOrderAddressUtil.buildAddress(orderlist);
        return PoiUtils.exportUser2Excel(title,orderlist);

    }



}
