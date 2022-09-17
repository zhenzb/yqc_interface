package com.youqiancheng.controller.app;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.service.app.service.C03CategoryAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
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
@Api(tags = {"手机端-分类"})
@RestController
@RequestMapping(value = "app/goodsCategory")
public class GoodsCategoryAppController {
    @Autowired
    private C03CategoryAppService c03CategoryAppService;





    /**
     * @api {GET} /app/goodsCategory/getFistLevelCategory 017获取商品一级分类接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取商品一级分类接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": [
     *         {
     *             "createPerson": null,
     *             "createTime": "2020-04-17 14:53:23",
     *             "updatePerson": "admin",
     *             "updateTime": "2020-11-12 13:12:37",
     *             "deleteFlag": 1,
     *             "id": 1, 分类ID
     *             "parentId": 0, 父级ID
     *             "name": "中国", 分类名称
     *             "url": "http://client.youqiancheng.vip/files/68/79/1596873628949.jpg", 分类图片
     *             "orderNum": 1,
     *             "status": 1
     *         }
     *     ]
     * }
     */
    @ApiOperation(value = "获取一级商品分类信息 ")
    @GetMapping("/getFistLevelCategory")
    public ResultVo<C03CategoryDO> getFistLevelCategory() {
        List<C03CategoryDO> list = c03CategoryAppService.listFirst();
        return ResultVOUtils.success(list);
    }


    /**
     * @api {GET} /app/goodsCategory/getCategoryList 018根据上级分类获取下一级分类接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 根据上级分类获取下一级分类
     * @apiParam {long} pid 上级类别Id [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": [
     *         {
     *             "createPerson": null,
     *             "createTime": "2020-04-17 14:53:23",
     *             "updatePerson": "admin",
     *             "updateTime": "2020-11-12 13:12:37",
     *             "deleteFlag": 1,
     *             "id": 1, 分类ID
     *             "parentId": 0, 父级ID
     *             "name": "中国", 分类名称
     *             "url": "http://client.youqiancheng.vip/files/68/79/1596873628949.jpg", 分类图片
     *             "orderNum": 1,
     *             "status": 1
     *         }
     *     ]
     * }
     */
    @ApiOperation(value = "根据上级分类获取下一级分类；参数——上级分类ID")
    @GetMapping("/getCategoryList")
    public ResultVo<C03CategoryDO> getCategoryList(Long pid) {
        if(pid==null||pid==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数不能为空或者0");
        }
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("parentId",pid);
        map.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        map.put("status",1);
        List<C03CategoryDO> list = c03CategoryAppService.getByParentId(map);
        return ResultVOUtils.success(list);
    }
}
