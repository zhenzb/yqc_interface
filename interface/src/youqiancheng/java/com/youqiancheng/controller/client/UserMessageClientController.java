package com.youqiancheng.controller.client;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.client.A15MessageAppSearchForm;
import com.youqiancheng.mybatis.dao.B04ShoppingCartDao;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import com.youqiancheng.service.client.service.A15MessageClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.A15MessageClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"PC端-用户消息"})
@RestController
@RequestMapping(value = "pc/message")
public class UserMessageClientController {
    @Autowired
    private A15MessageClientService a15MessageService;
    @Autowired
    private B04ShoppingCartDao b04ShoppingCartDao;


    @ApiOperation(value = "根据用户ID获取消息列表；参数——用户ID,消息类型")
    @GetMapping("/getMessageListByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<A15MessageDO>> listByPage(@Valid A15MessageAppSearchForm form ,@Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        List<A15MessageClientVO> a15MessageList = a15MessageService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A15MessageClientVO> a15MessageSimpleVO = new PageSimpleVO<>();
            a15MessageSimpleVO.setTotalNumber(page.getTotalNumber());
            a15MessageSimpleVO.setList(a15MessageList);

        return ResultVOUtils.success(a15MessageSimpleVO);
    }

    @ApiOperation(value = "根据消息ID获取消息详情；参数——消息ID")
    @GetMapping("/getMessageInfoById")
    @AuthRuleAnnotation()
    ResultVo get( Long id ){
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"消息ID不能为空");
        }
        A15MessageDO a15Message = a15MessageService.get(id);
        return ResultVOUtils.success(a15Message);
    }



    @ApiOperation(value = "消息已读；参数——为用户查询消息列表中的relationId")
    @PutMapping("/readMessage")
    @AuthRuleAnnotation()
    ResultVo read( Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"消息ID不能为空");
        }
        a15MessageService.read(id);
        return ResultVOUtils.success();
    }


    @ApiOperation(value = "返回未读消息数量；参数——只要传递参数用户ID和类型即可")
    @GetMapping("/getMessageCountByUserId")
    @AuthRuleAnnotation()
    ResultVo  getMessageCountByUserId(@Valid A15MessageAppSearchForm form ,@Valid EntyPage page){
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isRead",StatusConstant.IsRead.un_read.getCode());
        map.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        List<A15MessageClientVO> a15MessageList = a15MessageService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A15MessageClientVO> a15MessageSimpleVO = new PageSimpleVO<>();
//        a15MessageSimpleVO.setTotalNumber(page.getTotalNumber());
//        a15MessageSimpleVO.setList(a15MessageList);
        List<B04ShoppingCartDO> shoppingCar = b04ShoppingCartDao.selectList(new EntityWrapper<B04ShoppingCartDO>().eq("user_id", form.getUserId()).ne("delete_flag",2));
        int goodsNumber = 0;
        for (B04ShoppingCartDO b04:shoppingCar) {
            goodsNumber += b04.getCommodityNumber();
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("messageSize",page.getTotalNumber());
        map1.put("shoppingCar",goodsNumber);
        return ResultVOUtils.success(map1);
    }

}
