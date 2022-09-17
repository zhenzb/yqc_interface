package com.youqiancheng.controller.app;//package com.youqiancheng.controller.app;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.A15MessageAppSearchAllForm;
import com.youqiancheng.form.app.A15MessageAppSearchForm;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.service.app.service.A15MessageAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.A15MessageAppCountlVO;
import com.youqiancheng.vo.app.A15MessageAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-消息页面"})
@RestController
@RequestMapping(value = "app/message")
public class MessageAppController {
    @Autowired
    private A15MessageAppService a15MessageService;

    @ApiOperation(value = "根据用户ID获取消息列表（分页）；参数——用户ID,类型")
    @GetMapping("/getMessageListByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<A15MessageDO>> getMessageListByUserId(@Valid A15MessageAppSearchForm form  ,@Valid EntyPage page) {
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        List<A15MessageAppVO> a15MessageList = a15MessageService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A15MessageAppVO> a15MessageSimpleVO = new PageSimpleVO<>();
        a15MessageSimpleVO.setTotalNumber(page.getTotalNumber());
        a15MessageSimpleVO.setList(a15MessageList);

        return ResultVOUtils.success(a15MessageSimpleVO);
    }

    @ApiOperation(value = "根据消息ID获取消息记录；参数——消息ID")
    @GetMapping("/getMessageInfoById")
    @AuthRuleAnnotation()
    ResultVo getMessageInfoById( Long id ){
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"消息ID不能为空或者0");
        }
        A15MessageDO a15Message = a15MessageService.get(id);
        if(a15Message==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到消息信息");
        }
        return ResultVOUtils.success(a15Message);
    }



    @ApiOperation(value = "消息已读——参数为用户查询消息列表中的relationId")
    @GetMapping("/readMessage")
    @AuthRuleAnnotation()
    ResultVo readMessage( Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数（参数为用户查询消息列表中的relationId）不能为空或者0");
        }
        a15MessageService.read(id);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "返回未读消息数量——只要传递参数用户ID和类型即可")
    @GetMapping("/getMessageCountByUserId")
    @AuthRuleAnnotation()
    ResultVo  getMessageCountByUserId(@Valid A15MessageAppSearchForm form , @Valid EntyPage page ) {
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isRead",StatusConstant.IsRead.un_read.getCode());
        map.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        List<A15MessageAppVO> a15MessageAppVOS = a15MessageService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A15MessageClientVO> a15MessageSimpleVO = new PageSimpleVO<>();
//        a15MessageSimpleVO.setTotalNumber(page.getTotalNumber());
//        a15MessageSimpleVO.setList(a15MessageList);

        return ResultVOUtils.success(page.getTotalNumber());
    }

    @ApiOperation(value = "按消息类型返回消息数量——只要传递参数用户ID")
    @GetMapping("/getAllCountByUserId")
    @AuthRuleAnnotation()
    ResultVo  getAllCountByUserId(@Valid A15MessageAppSearchAllForm form  ) {

        QueryMap map = new QueryMap(form);
        map.put("isRead",StatusConstant.IsRead.un_read.getCode());
        map.put("type",StatusConstant.MessageType.platform_msg.getCode());
        map.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        Integer count = a15MessageService.count(map);
        QueryMap map1 = new QueryMap(form);
        map1.put("isRead",StatusConstant.IsRead.un_read.getCode());
        map1.put("type",StatusConstant.MessageType.service_msg.getCode());
        map1.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        Integer count1 = a15MessageService.count(map1);
        QueryMap map2 = new QueryMap(form);
        map2.put("isRead",StatusConstant.IsRead.un_read.getCode());
        map2.put("type",StatusConstant.MessageType.activity_msg.getCode());
        map2.put("userType", TypeConstant.MessageReadType.buyer.getCode());
        Integer count2 = a15MessageService.count(map2);
//        //封装到分页
//        PageSimpleVO<A15MessageClientVO> a15MessageSimpleVO = new PageSimpleVO<>();
//        a15MessageSimpleVO.setTotalNumber(page.getTotalNumber());
//        a15MessageSimpleVO.setList(a15MessageList);
        A15MessageAppCountlVO vo =new A15MessageAppCountlVO();
        vo.setPlatformMessageCount(count);
        vo.setSystemMessagesCount(count1);
        vo.setActivityMessageCount(count2);
        vo.setPlatformMessageType(StatusConstant.MessageType.platform_msg.getCode());
        vo.setSystemMessagesType(StatusConstant.MessageType.service_msg.getCode());
        vo.setActivityMessageType(StatusConstant.MessageType.activity_msg.getCode());
        return ResultVOUtils.success(vo);
    }


}
