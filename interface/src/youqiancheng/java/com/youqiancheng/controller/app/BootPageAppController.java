package com.youqiancheng.controller.app;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;
import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;
import com.youqiancheng.service.app.service.A11AdvertisementPicAppService;
import com.youqiancheng.service.app.service.bootpage.A12ServiceAgreementAppService;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-引导页"})
@RestController
@RequestMapping(value = "app/bootPage")
public class BootPageAppController {
    @Autowired
    private A11AdvertisementPicAppService a11AdvertisementPicService;
    @Autowired
    private A12ServiceAgreementAppService a12ServiceAgreementService;


    /**
     * 获取广告页信息
     */
    @ApiOperation(value = "获取引导页广告图片")
    @GetMapping("/getBootAd")
    public ResultVo getAdPicList(){
        List<A11AdvertisementPicDO> adPicList=a11AdvertisementPicService.getAdPicList(StatusConstant.AdvertisementType.boot_page.getCode());
        return ResultVOUtils.success(adPicList);

    }

    /**
     *
     * @author      nl
     * @return      获取服务协议
     * @date        2020/4/3 10:41
     */
    @ApiOperation(value = "获取服务协议")
    @GetMapping("/getSlaInfo")
    public ResultVo getSlaInfo(){
        A12ServiceAgreementDO slaInfo=a12ServiceAgreementService.getSlaInfo();
        return ResultVOUtils.success(slaInfo);
    }
}
