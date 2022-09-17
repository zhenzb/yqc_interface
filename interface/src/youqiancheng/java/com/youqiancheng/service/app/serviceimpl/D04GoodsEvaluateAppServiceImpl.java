package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.app.D04GoodsEvaluateSaveForm;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.dao.D04GoodsEvaluateDao;
import com.youqiancheng.mybatis.dao.D05EvaluatePicDao;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;
import com.youqiancheng.mybatis.model.D05EvaluatePicDO;
import com.youqiancheng.service.app.service.D04GoodsEvaluateAppService;
import com.youqiancheng.vo.app.D04GoodsEvaluateVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Service
@Transactional
public class D04GoodsEvaluateAppServiceImpl implements D04GoodsEvaluateAppService {
    @Autowired
    private D04GoodsEvaluateDao d04GoodsEvaluateDao;
    @Autowired
    private D05EvaluatePicDao d05EvaluatePicDao;
    @Autowired
    private D02OrderItemDao d02OrderItemDao;



    @Override
    public D04GoodsEvaluateVO get(Long id){
        //查询评论记录
        D04GoodsEvaluateVO vo =new D04GoodsEvaluateVO();
        D04GoodsEvaluateDO d04GoodsEvaluateDO = d04GoodsEvaluateDao.get(id);
        if(d04GoodsEvaluateDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到商品评价信息");
        }
        BeanUtils.copyProperties(d04GoodsEvaluateDO,vo);

        //查询评论图片
        Map<String, Object> map =new HashMap<>();
        map.put("evaluateId",id);
        List<D05EvaluatePicDO> list = d05EvaluatePicDao.list(map);
        List<String> collect = list.stream().map(D05EvaluatePicDO::getPicUrl).collect(Collectors.toList());
        vo.setPicList(collect);
        return vo;
    }


    @Override
    public List<D04GoodsEvaluateDO> listHDPage(Map<String, Object> map) {
        return d04GoodsEvaluateDao.listHDPage(map);
    }


    @Override
    public List<D04GoodsEvaluateDO> list(Map<String, Object> map) {
        return d04GoodsEvaluateDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return d04GoodsEvaluateDao.count(map);
    }


    @Override
    public int save(D04GoodsEvaluateSaveForm dto) {
        //保存评论记录
        D04GoodsEvaluateDO d04GoodsEvaluate=new D04GoodsEvaluateDO();
        BeanUtils.copyProperties(dto,d04GoodsEvaluate);
        d04GoodsEvaluate.setCreateTime(LocalDateTime.now());
        d04GoodsEvaluate.setUpdateTime(LocalDateTime.now());
        d04GoodsEvaluate.setUpdatePerson(dto.getCreatePerson());
        d04GoodsEvaluate.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int insert = d04GoodsEvaluateDao.insert(d04GoodsEvaluate);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"用户评论插入失败");
        }
        //保存评论图片
        if(StatusConstant.HasPicFlag.yes.getCode()==dto.getHasPic()){
            if(dto.getPicList()==null||dto.getPicList().size()==0){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"未发现评论图片");
            }
            List<D05EvaluatePicDO> d05EvaluatePics =new ArrayList<>();
            for (String str : dto.getPicList()) {
                D05EvaluatePicDO entity=new D05EvaluatePicDO();
                entity.setEvaluateId(insert);
                entity.setPicUrl(str);
                d05EvaluatePics.add(entity);
            }
            d05EvaluatePicDao.insertBatch(d05EvaluatePics);
        }
        //修改订单状态
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(dto.getOrderItemId());
        if(d02OrderItemDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单明细为空");
        }
        d02OrderItemDO.setIsEvaluate(StatusConstant.IsEvaluate.evaluated.getCode());
        d02OrderItemDao.updateById(d02OrderItemDO);
        return 1;
    }


    @Override
    public int insertBatch(List<D04GoodsEvaluateDO> d04GoodsEvaluates){
        return d04GoodsEvaluateDao.insertBatch(d04GoodsEvaluates);
    }


    @Override
    public int update(D04GoodsEvaluateDO d04GoodsEvaluate) {
        d04GoodsEvaluate.setUpdateTime(LocalDateTime.now());
        return d04GoodsEvaluateDao.updateById(d04GoodsEvaluate);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return d04GoodsEvaluateDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return d04GoodsEvaluateDao.updateStatus(param);
        }
    }
