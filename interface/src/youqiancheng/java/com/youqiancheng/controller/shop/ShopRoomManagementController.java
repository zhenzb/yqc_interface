//package com.youqiancheng.controller.shop;
//
//import com.handongkeji.constants.StatusConstant;
//import com.handongkeji.util.EntyPage;
//import com.handongkeji.util.manager.ResultVOUtils;
//import com.youqiancheng.form.shop.otther.ShopCommonForm;
//import com.youqiancheng.form.shop.otther.ShopRoomPageForm;
//import com.youqiancheng.mybatis.model.C11RoomDO;
//import com.youqiancheng.service.shop.ShopRoomService;
//import com.youqiancheng.vo.PageSimpleVO;
//import com.youqiancheng.vo.result.ResultEnum;
//import com.youqiancheng.vo.result.ResultVo;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.thymeleaf.util.ArrayUtils;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description:
// * @Author: Mr.Deng
// * @Date: 2020/4/15 17:16
// * @Version: V1.0
// */
////@Api(tags = "商家管理--房间管理")
//@RestController
//@RequestMapping("shop/roomManagement")
//public class ShopRoomManagementController {
//    @Resource
//    private ShopRoomService shopRoomService;
//
//    @ApiOperation(value = "房间列表", notes = "房间列表")
//    @GetMapping(value = "/pageRoom")
//    public ResultVo<PageSimpleVO<C11RoomDO>> pageRoom(@Valid ShopRoomPageForm shopRoomPageForm, @Valid EntyPage page ){
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("shopRoomPageForm", shopRoomPageForm);
//        map.put("page", page);
//        List<C11RoomDO> roomlist = shopRoomService.listRoomHDPage(map);
//        //封装到分页
//        PageSimpleVO<C11RoomDO> shopRoomPageSimpleVO = new PageSimpleVO<>();
//        shopRoomPageSimpleVO.setTotalNumber(page.getTotalNumber());
//        shopRoomPageSimpleVO.setList(roomlist);
//        return ResultVOUtils.success(shopRoomPageSimpleVO);
//    }
//
//    @ApiOperation(value = "查看详情", notes = "查看详情")
//    @PostMapping(value = "/getRoomById")
//    public ResultVo getRoomById(@Valid ShopCommonForm shopCommonForm){
//        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        return ResultVOUtils.success(shopRoomService.getRoomById(shopCommonForm.getIds()[0]));
//    }
//
//    @ApiOperation(value = "编辑房间", notes = "编辑房间")
//    @PostMapping(value = "/editRoomById")
//    public ResultVo editRoomById(@Valid C11RoomDO room){
//        if(room == null){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        if (shopRoomService.getRoomById(room.getId()) == null){
//            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,ResultEnum.DATA_NOT_EXIST.getMessage());
//        }
//        return ResultVOUtils.success(shopRoomService.saveOrUpdateRoom(room));
//    }
//
//    @ApiOperation(value = "添加房间", notes = "添加房间")
//    @PostMapping(value = "/addRoom")
//    public ResultVo addRoom(@Valid C11RoomDO room){
//        if(room == null){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        if (shopRoomService.getRoomById(room.getId()) != null){
//            return ResultVOUtils.error(ResultEnum.DATA_REPEAT,ResultEnum.DATA_REPEAT.getMessage());
//        }
//        return ResultVOUtils.success(shopRoomService.saveOrUpdateRoom(room));
//    }
//
//    @ApiOperation(value = "删除房间", notes = "删除房间")
//    @PostMapping(value = "/delRoom")
//    public ResultVo delRoom(@Valid ShopCommonForm shopCommonForm){
//        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        List<C11RoomDO> rooms = new ArrayList<>();
//        for (long id:shopCommonForm.getIds()){
//            C11RoomDO room = shopRoomService.getRoomById(id);
//            if (room != null){
//                room.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
//                room.setUpdateTime(LocalDateTime.now());
//                rooms.add(room);
//            }
//        }
//        return ResultVOUtils.success(shopRoomService.batchUpdateRoom(rooms));
//    }
//}
//
