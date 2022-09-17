package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A15MessageDao;
import com.youqiancheng.mybatis.dao.A17MessageUserDao;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.mybatis.model.A17MessageUserDO;
import com.youqiancheng.service.client.service.A15MessageClientService;
import com.youqiancheng.vo.client.A15MessageClientVO;
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
public class A15MessageClientServiceImpl implements A15MessageClientService {
    @Autowired
    private A15MessageDao a15MessageDao;
    @Autowired
    private A17MessageUserDao a17MessageUserDao;

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
    public List<A15MessageClientVO> listHDPage(Map<String, Object> map) {
        return a15MessageDao.listByUserIdClientHDPage(map);
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
}
