package com.youqiancheng.controller.app;

import com.handongkeji.constants.TypeConstant;
import com.youqiancheng.ability.DelHTMLTag;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.B11InvitationRecordAppService;
import com.youqiancheng.service.app.service.C01GoodsAppService;
import com.youqiancheng.service.app.service.C10PublicityAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.C01GoodsAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author zzb
 * Date  2020-12-08
 */
@Api(tags = {"手机端-邀请记录接口"})
@RestController
@RequestMapping(value = "app/invitationRecord")
public class InvitationRecordAppController {
    @Autowired
    private B11InvitationRecordAppService b11InvitationRecordService;
    @Autowired
    private C10PublicityAppService c10PublicityService;
    @Autowired
    private C01GoodsAppService c01GoodsService;
    @Autowired
    private DelHTMLTag delHTMLTag;
    @Autowired
    private B01UserDao b01UserDao;

    /**
     * @api {GET} /app/invitationRecord/getInvitationRecord 003获取我的邀请记录接口
     * @apiGroup 003用户邀请模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取我的邀请记录接口
     * @apiParam {long} userId 用户Id[必填]
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize  每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *          "currentPage": null,
     *         "totalNumber": 2,
     *         "list": [
     *             {
     *                 "id": 2, 邀请记录Id
     *                 "userId": 409, 邀请人Id
     *                 "beUserId": 506, 被邀请人Id
     *                 "createTime": "2020-12-08 16:59:42", 邀请时间
     *                 "nick": "13520289156", 被邀请人昵称
     *                 "pic": "http://client.youqiancheng.vip/files/61/61/1606460130093.jpg" 被邀请人头像
     *             },
     *             {
     *                 "id": 1,
     *                 "userId": 409,
     *                 "beUserId": 435,
     *                 "createTime": "2020-12-08 16:59:17",
     *                 "nick": "13520289157",
     *                 "pic": "http://client.youqiancheng.vip/files/100/78/1596792278791.png"
     *             }
     *         ],
     *         "map": null
     *     }
     * }
     */
    @ApiOperation(value = "获取列表（分页+过滤参数）")
    @GetMapping(value = "getInvitationRecord")
     ResultVo<PageSimpleVO<B11InvitationRecordDO>> listByPage(@Valid EntyPage page, BindingResult bindingResult,Long userId) {
        //参数错误
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
        }
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        map.put("userId", userId);
        List<B11InvitationRecordDO> b11InvitationRecordList = b11InvitationRecordService.listHDPage(map);

        //查看是否开通店铺
        b11InvitationRecordList.stream().forEach(item -> {
            List userIdList = new ArrayList();
            userIdList.add(item.getBeUserId());
            List<B01UserDO> b01UserDOS = b01UserDao.listUserByUserIds(userIdList);
            b01UserDOS.stream().forEach(items ->{
                if(items.getIsShop()==2){
                    item.setShopId(items.getShopId());
                }
            });

        });
        //封装到分页
        PageSimpleVO<B11InvitationRecordDO> b11InvitationRecordSimpleVO = new PageSimpleVO<>();
            b11InvitationRecordSimpleVO.setTotalNumber(page.getTotalNumber());
            b11InvitationRecordSimpleVO.setList(b11InvitationRecordList);

        return ResultVOUtils.success(b11InvitationRecordSimpleVO);
    }

    @ApiOperation(value = "获取列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list(BindingResult bindingResult) {
        //参数错误
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
        }
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<B11InvitationRecordDO> list = b11InvitationRecordService.list(map);
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "根据ID获取记录")
    @GetMapping("{id}")
     ResultVo get(@PathVariable("id") Long id ){
        B11InvitationRecordDO b11InvitationRecord = b11InvitationRecordService.get(id);
        return ResultVOUtils.success(b11InvitationRecord);
    }

    @ApiOperation(value = "保存单条记录")
    @PostMapping()
    ResultVo insert(@RequestBody B11InvitationRecordDO b11InvitationRecord) {
        int num=b11InvitationRecordService.insert(b11InvitationRecord);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @PostMapping("/insertBatch")
    ResultVo insertBatch(@RequestBody List<B11InvitationRecordDO> b11InvitationRecords) {
        int num=b11InvitationRecordService.insertBatch(b11InvitationRecords);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改记录")
    @PutMapping
    ResultVo update(@RequestBody B11InvitationRecordDO b11InvitationRecord) {
        int num= b11InvitationRecordService.update(b11InvitationRecord);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "停用记录")
    @PutMapping("/stop")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= b11InvitationRecordService.stop(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "启用记录")
    @PutMapping("/start")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= b11InvitationRecordService.start(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    /**
     * @api {GET} /app/invitationRecord/getInvitationLink 001获取邀请码url接口
     * @apiGroup 003用户邀请模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取邀请码url接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": "http://www.baidu.com" 邀请码url
     * }
     */
    @GetMapping("/getInvitationLink")
    ResultVo getInvitationLink(){
        Map<String,Object> map = new HashMap<>();
        map.put("title","有钱城");
        map.put("description","带你逛世界，流量抢不停");
        //map.put("url","https://www.youqiancheng.vip/share/index.html");
        map.put("url","https://www.youqiancheng.vip/share/mobile_login/index.html");
        return ResultVOUtils.success(map);
    }

    /**
     * @api {GET} /app/invitationRecord/getPublicityShareLink 005获取宣传详情分享链接
     * @apiGroup 003用户邀请模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取邀请码url接口
     * @apiParam {Long} id 宣传ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "description": "要学会开好宣传店铺",
     *         "title": "怎样开宣传店铺",
     *         "url": "https://www.youqiancheng.vip/share/mobile_login/index.html"
     *     }
     * }
     */
    @GetMapping("/getPublicityShareLink")
    public ResultVo getPublicityShareLink(Long id){
        C10PublicityDO c10Publicity = c10PublicityService.get(id);
        Map<String,Object> map = new HashMap<>();
        if(c10Publicity !=null){
            map.put("title",c10Publicity.getTitle());
            String s = delHTMLTag.delHTMLTag(c10Publicity.getContent());
            if("".equals(s)||null == s){
                s="有钱城欢迎您";
            }
            map.put("description",s);
            map.put("icon",c10Publicity.getIcon());
            map.put("url","https://www.youqiancheng.vip/share/downpages/PropagandaPage.html");
        }
        return ResultVOUtils.success(map);
    }

    /**
     * @api {GET} /app/invitationRecord/getGoodsInfoShareLink 006获取商品详情分享链接
     * @apiGroup 003用户邀请模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取邀请码url接口
     * @apiParam {Long} id 商品ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "description": "<p>商品详情</p>",
     *         "title": "咪咪收音机",
     *         "url": "https://www.youqiancheng.vip/share/mobile_login/index.html"
     *     }
     * }
     */
    @GetMapping("/getGoodsInfoShareLink")
    public ResultVo getGoodsInfoShareLink(Long id){
        if (id == null || id == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "参数不能为空或者0");
        }
        C01GoodsAppVO c01Goods = c01GoodsService.get(id);
        if (c01Goods == null) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "未查询到商品信息");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("title",c01Goods.getName());
        String s = delHTMLTag.delHTMLTag(c01Goods.getGoodsDesc());
        if("".equals(s)||null == s){
            s="有钱城欢迎您";
        }
        map.put("description",s);
        map.put("icon",c01Goods.getIcon());
        map.put("url","https://www.youqiancheng.vip/share/downpages/GoodsDetails.html");
        return ResultVOUtils.success(map);
    }



}
