package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A06BaseInfoDao;
import com.youqiancheng.mybatis.dao.A15MessageDao;
import com.youqiancheng.mybatis.dao.A17MessageUserDao;
import com.youqiancheng.mybatis.model.A06BaseInfoDO;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.mybatis.model.A17MessageUserDO;
import com.youqiancheng.service.app.service.A15MessageAppService;
import com.youqiancheng.vo.app.A15MessageAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Service
@Transactional
public class A15MessageAppServiceImpl implements A15MessageAppService {
    @Autowired
    private A15MessageDao a15MessageDao;
    @Autowired
    private A17MessageUserDao a17MessageUserDao;
    @Autowired
    private A06BaseInfoDao a06BaseInfoDao;

    @Override
    public A15MessageDO get(Long id){
        return a15MessageDao.get(id);
    }


    /**
     * 功能描述: <br>
     * 〈根据条件查询消息信息——分页〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    @Override
    public List<A15MessageAppVO> listHDPage(Map<String, Object> map) {
        return a15MessageDao.listByUserIdAppHDPage(map);
    }

    @Override
    public Integer count(Map<String, Object> map) {
        return a15MessageDao.countByUserIdApp(map);
    }

    /**
     * 已读
      * @param id
     *@return ({@link int})
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
     @Override
    public int read(Long id){
        A17MessageUserDO dto =a17MessageUserDao.get(id);
        if(dto==null){
            throw new JsonException(ResultEnum.UPDATE_FAIL,"消息关联用户记录不存在");
        }
        dto.setIsRead(StatusConstant.IsRead.read.getCode());
        dto.setReadTime(LocalDateTime.now());
        int num= a17MessageUserDao.updateById(dto);
        if(num<=0){
            throw new JsonException(ResultEnum.UPDATE_FAIL,"修改已读状态失败");
        }
        return 1;
    }

    @Override
    public A06BaseInfoDO getA06BaseInfo() {
        EntityWrapper<A06BaseInfoDO> ew = new EntityWrapper<>();
        List<A06BaseInfoDO> a06BaseInfoDOList = a06BaseInfoDao.selectList(null);
        if(a06BaseInfoDOList != null && a06BaseInfoDOList.size() > 0){
            return a06BaseInfoDOList.get(0);
        }
        return null;
    }

    @Override
    public A06BaseInfoDO addA06BaseInfo() {
        EntityWrapper<A06BaseInfoDO> ew = new EntityWrapper<>();
        List<A06BaseInfoDO> a06BaseInfoDOList = a06BaseInfoDao.selectList(null);
        if(a06BaseInfoDOList != null && a06BaseInfoDOList.size() > 0){
            A06BaseInfoDO a06BaseInfoDO = a06BaseInfoDOList.get(0);
            a06BaseInfoDO.setBrowseVolume(a06BaseInfoDO.getBrowseVolume()+1);
            a06BaseInfoDO.setUpdateTime(LocalDateTime.now());
            a06BaseInfoDao.updateById(a06BaseInfoDO);
        }
        return null;
    }
}
