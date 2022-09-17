package com.youqiancheng.controller.app;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.NearShopForm;
import com.youqiancheng.mybatis.model.A10CarouselPicDO;
import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.app.service.A10CarouselPicAppService;
import com.youqiancheng.service.app.service.A11AdvertisementPicAppService;
import com.youqiancheng.service.app.service.F01ShopAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-附近"})
@RestController
@RequestMapping(value = "app/nearby")
public class NearByAppController {
    @Autowired
    private A10CarouselPicAppService a10CarouselPicService;
    @Autowired
    private A11AdvertisementPicAppService a11AdvertisementPicService;
    @Autowired
    private F01ShopAppService  f01ShopAppService;

    @ApiOperation(value = "获取附近轮播图列表")
    @GetMapping("/getCarouselPic")
    ResultVo getCarouselPic() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.statusAndDelete.getCode());
        map.put("type", TypeConstant.CarouselPicType.nearby.getCode());
        map.put("status",StatusConstant.enable.getCode());
        List<A10CarouselPicDO> list = a10CarouselPicService.list(map);
        return ResultVOUtils.success(list);
    }



    @ApiOperation(value = "获取附近页广告图列表")
    @GetMapping("/getNearbyAd")
    ResultVo<PageSimpleVO<A11AdvertisementPicDO>> listByPage(@Valid EntyPage page ) {

        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        map.put("type",StatusConstant.AdvertisementType.nearby_page.getCode());
        List<A11AdvertisementPicDO> a11AdvertisementPicList = a11AdvertisementPicService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A11AdvertisementPicDO> a11AdvertisementPicSimpleVO = new PageSimpleVO<>();
        a11AdvertisementPicSimpleVO.setTotalNumber(page.getTotalNumber());
        a11AdvertisementPicSimpleVO.setList(a11AdvertisementPicList);

        return ResultVOUtils.success(a11AdvertisementPicSimpleVO);
    }

//    @ApiOperation(value = "获取附近实体店")
//    @GetMapping("/getNearShop")
//    ResultVo<PageSimpleVO<A11AdvertisementPicDO>> getNearShop(String coordinate,@Valid EntyPage page ) {
//
//        String[] split = coordinate.split(",");
//        if(split.length!=2){
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"经纬度参数格式错误");
//        }
//        String str1=split[0];
//        String str2=split[1];
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        map.put("type",StatusConstant.AdvertisementType.nearby_page.getCode());
//        List<A11AdvertisementPicDO> a11AdvertisementPicList = a11AdvertisementPicService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A11AdvertisementPicDO> a11AdvertisementPicSimpleVO = new PageSimpleVO<>();
//        a11AdvertisementPicSimpleVO.setTotalNumber(page.getTotalNumber());
//        a11AdvertisementPicSimpleVO.setList(a11AdvertisementPicList);
//
//        return ResultVOUtils.success(a11AdvertisementPicSimpleVO);
//    }
    @ApiOperation(value = "（已作废）获取附近实体店——不包含距离——;参数——距离KM（不传默认为10），经度，纬度")
    @GetMapping("/getNearShop")
     ResultVo getNearShop(NearShopForm form){
        double r = 6371;//地球半径千米
        //double dis =form.getRadii()==0?10:form.getRadii();
        double dis = 10 ;
        double lat = form.getLat();
        double lon = form.getLon();
        double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(lat*Math.PI/180));
        dlng = dlng*180/Math.PI;//角度转为弧度
        double dlat = dis/r;
        dlat = dlat*180/Math.PI;
        double minlat =lat-dlat;
        double maxlat = lat+dlat;
        double minlng = lon -dlng;
        double maxlng = lon + dlng;
        Map<String,Object> map=new HashMap<>();
        map.put("minlat", BigDecimal.valueOf(minlat));
        map.put("maxlat",BigDecimal.valueOf(maxlat));
        map.put("minlng",BigDecimal.valueOf(minlng));
        map.put("maxlng",BigDecimal.valueOf(maxlng));
        List<F01ShopDO> getvicinity = f01ShopAppService.getvicinity(map);
        return ResultVOUtils.success(getvicinity);
    }

    @ApiOperation(value = "获取附近实体店_分页，返回距离;参数——，经度，纬度")
    @GetMapping("/getNearShopByDistance")
     ResultVo getNearShopByDistance(NearShopForm form,@Valid EntyPage page){
        QueryMap map = new QueryMap(page);
        map.put("latitude", form.getLat());
        map.put("longitude",form.getLon());
        List<F01ShopDO> getvicinity = f01ShopAppService.getvicinityByDistanceHDPage(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> listp = new PageSimpleVO<>();
        listp.setTotalNumber(page.getTotalNumber());
        listp.setList(getvicinity);
        return ResultVOUtils.success(listp);
    }

}
