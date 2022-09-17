package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.NoUtil;
import com.youqiancheng.form.app.D03CustomerServiceSaveForm;
import com.youqiancheng.mybatis.dao.D01OrderDao;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.dao.D03CustomerServiceDao;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.service.app.service.D03CustomerServiceAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Service
@Transactional
public class D03CustomerServiceAppServiceImpl implements D03CustomerServiceAppService {
    @Autowired
    private D03CustomerServiceDao d03CustomerServiceDao;
    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private D02OrderItemDao d02OrderItemDao;


    @Override
    public D03CustomerServiceDO get(Long id){
        return d03CustomerServiceDao.get(id);
    }

    @Override
    public D03CustomerServiceDO getCustomerServiceByOrderId(Long id) {
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("orderItemId",id);
        List<D03CustomerServiceDO> list = d03CustomerServiceDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到售后信息");
        }
        return list.get(0);
    }


    @Override
    public List<D03CustomerServiceDO> listHDPage(Map<String, Object> map) {
        return d03CustomerServiceDao.listHDPage(map);
    }


    @Override
    public List<D03CustomerServiceDO> list(Map<String, Object> map) {
        return d03CustomerServiceDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return d03CustomerServiceDao.count(map);
    }


    @Override
    public int save(D03CustomerServiceSaveForm dto) {
        D03CustomerServiceDO d03CustomerService=new D03CustomerServiceDO();
        BeanUtils.copyProperties(dto,d03CustomerService);
        d03CustomerService.setUpdatePerson(dto.getCreatePerson());
        d03CustomerService.setCreateTime(LocalDateTime.now());
        d03CustomerService.setUpdateTime(LocalDateTime.now());
        d03CustomerService.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        d03CustomerService.setStatus(StatusConstant.CustomServiceStatus.un_examine.getCode());
        d03CustomerService.setTime(LocalDateTime.now());
        d03CustomerService.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        d03CustomerService.setType(StatusConstant.RefundType.refund.getCode());
        d03CustomerService.setServiceNo(NoUtil.createNo(dto.getUserId(), TypeConstant.CustomerService.getCode()));
        D02OrderItemDO d02OrderItemDO1 = d02OrderItemDao.get(dto.getOrderItemId());
        if(d02OrderItemDO1==null){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"数据不存在");
        }
        D01OrderDO d01OrderDO = d01OrderDao.get(d02OrderItemDO1.getOrderId());
        if(d01OrderDO==null){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"数据不存在");
        }
        if(StatusConstant.DeliveryStatus.un_shipped.getCode()==d01OrderDO.getDeliveryStatus()){
            d03CustomerService.setIsReturnGoods(StatusConstant.IsRefund.no.getCode());
        }else{
            d03CustomerService.setIsReturnGoods(StatusConstant.IsRefund.yes.getCode());
        }
        d03CustomerServiceDao.insert(d03CustomerService);
        //修改订单明细状态
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(d03CustomerService.getOrderItemId());
        if(d02OrderItemDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后关联订单明细不存在");
        }
        if(StatusConstant.OrderStatus.pay.getCode()!=d02OrderItemDO.getOrderStatus()
                &&StatusConstant.OrderStatus.end.getCode()!=d02OrderItemDO.getOrderStatus()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后关联订单明细状态不正确：只有待发货或者收货的商品才能退货");
        }
        d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.apply_refund.getCode());
        d02OrderItemDao.updateById(d02OrderItemDO);

        return 1;
    }


    @Override
    public int insertBatch(List<D03CustomerServiceDO> d03CustomerServices){
        return d03CustomerServiceDao.insertBatch(d03CustomerServices);
    }


    @Override
    public int update(D03CustomerServiceDO d03CustomerService) {
        d03CustomerService.setUpdateTime(LocalDateTime.now());
        return d03CustomerServiceDao.updateById(d03CustomerService);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return d03CustomerServiceDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return d03CustomerServiceDao.updateStatus(param);
        }
    }
