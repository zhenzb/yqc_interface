package com.youqiancheng.service.client.serviceimpl.classification;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.classification.B01UserClientService;
import com.youqiancheng.util.QueryMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 17:42
 */
@Service
@Transactional
public class B01UserClientServiceImpl implements B01UserClientService {

    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private B02UserAccountDao b02UserAccountDao;
    @Resource
    private B03UserAccountFlowDao b03UserAccountFlowDao;
    @Resource
    private D01OrderDao d01OrderDao;
    @Resource
    private D02OrderItemDao d02OrderItemDao;
    @Override
    public B01UserDO getUserInfo(Long userId){
        return b01UserDao.get(userId);
    }
    @Override
    public B02UserAccountDO getUserAccountInfo(Long userId){
        B02UserAccountDO b02UserAccoun=new B02UserAccountDO();
        b02UserAccoun.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        b02UserAccoun.setUserId(userId);
        return b02UserAccountDao.selectOne(b02UserAccoun);
    }
    @Override
    public List<B03UserAccountFlowDO> getUserAccountFlowList(QueryMap map){
        return b03UserAccountFlowDao.getUserAccountFlowHDPage(map);
    }

    @Override
    public List<D01OrderDO>  getOrderList(QueryMap map){
        List<D01OrderDO>  d01OrderDOS= d01OrderDao.getOrderHDPage(map);
        for (int i = 0; i <d01OrderDOS.size() ; i++) {
            EntityWrapper<D02OrderItemDO> ew=new EntityWrapper<D02OrderItemDO>();
            ew.eq("order_id",d01OrderDOS.get(i).getId());
            List<D02OrderItemDO> d02OrderItemDOS=d02OrderItemDao.selectList(ew);
            d01OrderDOS.get(i).setOrderItem(d02OrderItemDOS);
        }
        return d01OrderDOS;
    }
}
