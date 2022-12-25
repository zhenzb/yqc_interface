package com.youqiancheng.controller.client;

import com.alipay.api.domain.MachineInfo;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.interceptor.PageInterceptor;
import com.handongkeji.constants.DictTypeConstants;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.client.E01RedenvelopesStreetSearchForm;
import com.youqiancheng.form.client.E04RedenvelopesGrantRecordSearchForm;
import com.youqiancheng.form.client.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.dao.A07HelpCenterDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.service.manager.service.A13SysVersionService;
import com.youqiancheng.service.manager.service.InitDataService;
import com.youqiancheng.util.EntCoordSyncJob;
import com.youqiancheng.util.QrCodeUtil;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.E01RedenvelopesStreetClientVO;
import com.youqiancheng.vo.client.NoticeVo;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Api(tags = {"PC端-首页"})
@RestController
@RequestMapping(value = "pc/homePage")
public class HomePageClientController {

    protected static final Log logger = LogFactory.getLog(PageInterceptor.class);

    @Autowired
    private A08ContactUsClientService a08ContactUsService;
    @Autowired
    private A10CarouselPicClientService a10CarouselPicService;
    @Autowired
    private A09NoticeClientService a09NoticeService;
    @Autowired
    private F01ShopClientService f01ShopService;
    @Autowired
    private C03CategoryClientService c03CategoryClientService;
    @Autowired
    private E01RedenvelopesStreetClientService e01RedenvelopesStreetService;
    @Autowired
    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordService;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private A13SysVersionService a13SysVersionService;
    @Autowired
    private A07HelpCenterDao a07HelpCenterDao;

    @ApiOperation(value = "返回最新版本安卓app下载地址二维码")
    @GetMapping("/getQrCodeOfApp")
    ResultVo getQrCodeOfApp(HttpServletResponse response) {
        try {
            QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
            List<A13SysVersionDO> list = a13SysVersionService.list(map);
            if(CollectionUtils.isEmpty(list)){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"下载连接不存在");
            }
            A13SysVersionDO a13SysVersionDO = list.get(0);
            if (!StringUtils.isBlank(a13SysVersionDO.getUrl())) {
                ServletOutputStream stream = null;
                try {
                    stream = response.getOutputStream();
                    //使用工具类生成二维码
                    QrCodeUtil.encode(a13SysVersionDO.getUrl(), stream);
                } finally {
                    if (stream != null) {
                        stream.flush();
                        stream.close();
                    }
                }
            } else {
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"获取二维码失败！地址不存在");
            }
        } catch (Exception e) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"获取二维码失败！"+e.getMessage());
        }
        return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"获取二维码失败！");
    }


    @ApiOperation(value = "获取首页轮播图列表")
    @GetMapping("/getCarouselPic")
    ResultVo getCarouselPic() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("type", TypeConstant.CarouselPicType.home_page.getCode());
        map.put("status", StatusConstant.enable.getCode());
        List<A10CarouselPicDO> list = a10CarouselPicService.list(map);
        return ResultVOUtils.success(list);
    }


    /**
     　* @Description: 获取公告信息
     　* @author yutf
     　* @date 2020/4/2 13:54
     　*/
    @ApiOperation(value = "获取公告信息")
    @GetMapping("/getNoticeList")
    public ResultVo<PageSimpleVO<A09NoticeDO>> getNoticeList(@Valid EntyPage page) {
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        List<A09NoticeDO> a09NoticeDOList = a09NoticeService.listNoticePage(map);
        List<NoticeVo> noticeVoList = a09NoticeDOList.stream().map(item -> {
                    NoticeVo noticeVo = new NoticeVo();
                    BeanUtils.copyProperties(item, noticeVo);
                    return noticeVo;
                }
        ).collect(Collectors.toList());

        //封装到分页
        PageSimpleVO<NoticeVo> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(noticeVoList);
        return ResultVOUtils.success(authAdminPageSimpleVO);
    }

    /**
     　* @Description: 获取商品分类信息
     　* @author yutf
     　* @date 2020/4/2 13:54
     　*/
    /**
     * @api {GET} /pc/homePage/getCategoryList 001商品街道分类接口
     * @apiGroup 005街道分类模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 商品街道分类接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": [
     *       {
     *             "createPerson": null,
     *             "createTime": "2020-04-17 14:53:23",
     *             "updatePerson": "admin",
     *             "updateTime": "2021-02-06 23:55:36",
     *             "deleteFlag": 1,
     *             "id": 1, 分类id 获取该分类下的街道时的 categoryId 为此值
     *             "parentId": 0,
     *             "name": "中国", 分类名称
     *             "url": "http://client.youqiancheng.vip/files/68/79/1596873628949.jpg",
     *             "orderNum": 1,
     *             "status": 1
     *         }
     *   ]
     * }
     */
    @ApiOperation(value = "获取一级商品分类信息（一级商品分类即为国家）")
    @GetMapping("/getCategoryList")
    public ResultVo<C03CategoryDO> getCategoryList() {
        List<C03CategoryDO> list = c03CategoryClientService.listFirst();
        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "获取红包街列表（分页+过滤参数）——自动返回10家商家；参数——一级分类ID")
    @GetMapping("/getStreet")
    ResultVo<PageSimpleVO<E01RedenvelopesStreetDO>> getStreet(@Valid E01RedenvelopesStreetSearchForm form, @Valid EntyPage page ) {
        page.setPageSize(50);
        long startTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.statusAndDelete.getCode());
        List<E01RedenvelopesStreetClientVO> e01RedenvelopesStreetClientVOS = e01RedenvelopesStreetService.listHDPageWithShopList(map);
        //封装到分页
        PageSimpleVO<E01RedenvelopesStreetClientVO> e01RedenvelopesStreetSimpleVO = new PageSimpleVO<>();
        e01RedenvelopesStreetSimpleVO.setTotalNumber(page.getTotalNumber());
        e01RedenvelopesStreetSimpleVO.setList(e01RedenvelopesStreetClientVOS);
        long endTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long l = (endTime - startTime);
        logger.info("getStreet()接口耗时:"+l+"秒");
        return ResultVOUtils.success(e01RedenvelopesStreetSimpleVO);
    }

    @ApiOperation(value = "获取红包街——商家列表;参数——APP——红包发放记录查询实体")
    @GetMapping("/getShopListByStreetId")
    ResultVo<PageSimpleVO<E04RedenvelopesGrantRecordDO>> getShopListByStreetId(@Valid E04RedenvelopesGrantRecordSearchForm form, @Valid EntyPage page   ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("endFlag",StatusConstant.EndFlag.un_end.getCode());
        List<F01ShopDO> shopListByRedEnvelopes = e04RedenvelopesGrantRecordService.getShopListByRedEnvelopes(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> e04RedenvelopesGrantRecordSimpleVO = new PageSimpleVO<>();
        e04RedenvelopesGrantRecordSimpleVO.setTotalNumber(page.getTotalNumber());
        e04RedenvelopesGrantRecordSimpleVO.setList(shopListByRedEnvelopes);

        return ResultVOUtils.success(e04RedenvelopesGrantRecordSimpleVO);
    }


    @ApiOperation(value = "商家入驻申请；参数——商家保存实体")
    @PostMapping("/shopApplyEnters")
    //@AuthRuleAnnotation()
    ResultVo applyEnters(@RequestBody @Valid F01ShopAppSaveForm f01Shop ) {

        int shopApplyEnters = f01ShopService.isShopApplyEnters(f01Shop.getUserId());
        if(shopApplyEnters==1){
            f01ShopService.applyEnters(f01Shop);
            return ResultVOUtils.success();
        }else{
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户已经入驻");
        }


    }



    @ApiOperation(value = "是否可以商家入驻——返回结果:1:无商家可以申请入驻，2已入驻待审核，3已入驻审核通过，4已入驻审核拒绝；参数——用户ID")
    @PostMapping("/isShopApplyEnters")
    //@AuthRuleAnnotation()
    ResultVo isShopApplyEnters(Long userId) {
        if(userId==null||userId==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID不能为空");
        }
        return ResultVOUtils.success(f01ShopService.isShopApplyEnters(userId));
    }


   @ApiOperation(value = "根据地理位置获取经纬度——商家入驻申请；参数——地理位置")
    @PostMapping("/getCoordinate")
    //@AuthRuleAnnotation()
    ResultVo getCoordinate(String address) {
        if(StringUtils.isBlank(address)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"地址不能为空");
        }
       String coordinate = EntCoordSyncJob.getCoordinate(address);
       return ResultVOUtils.success(coordinate);
    }

    @ApiOperation(value = "返回商家类型——申请入住时使用")
    @GetMapping("/getShopType")
    ResultVo getShopType() {
        List<A16SysDictDO> sysDictByType = initDataService.getSysDictByType(DictTypeConstants.SHOP_TYPE);
        return ResultVOUtils.success(sysDictByType);
    }

    @ApiOperation(value = "根据商家类型返回商家主营项目——申请入住时使用")
    @GetMapping("/getMainProjectByType")
    ResultVo getShopProjectByType(String type ){
        if(StringUtils.isBlank(type)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家类型不能为空");
        }
        List<F03MainProjectDO> list = f01ShopService.getShopProjectByType(type);
        return ResultVOUtils.success(list);
    }


//    /**
//     　* @Description: 浏览次数加1
//     　* @author yutf
//     　* @date 2020/4/2 13:54
//     　*/
//    @ApiOperation(value = "浏览次数加1")
//    @PutMapping("/addBrowseVolume")
//    ResultVo addBrowseVolume() {
//        a08ContactUsService.addBrowseVolume();
//        return ResultVOUtils.success();
//    }
//    /**
//     　* @Description: 获取浏览次数
//     　* @author yutf
//     　* @date 2020/4/2 13:54
//     　*/
//    @ApiOperation(value = "获取浏览次数")
//    @GetMapping("/getBrowseVolume")
//    ResultVo getBrowseVolume() {
//        int num = a08ContactUsService.addBrowseVolume();
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "获取用户隐私政策")
    @GetMapping("/getPrivacyPolicy")
    ResultVo getPrivacyPolicy() {
        List<A07HelpCenterDO> list = a07HelpCenterDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
        if(CollectionUtils.isEmpty(list)){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"隐私政策数据不存在");
        }
        return ResultVOUtils.success(list.get(0));
    }
}
