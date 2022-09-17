package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.shop.C08SelectAttributeSaveForm;
import com.youqiancheng.mybatis.dao.C08SelectAttributeDao;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;
import com.youqiancheng.service.shop.ShopSelectAttributeService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/20 10:59
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopSelectAttributeServiceImpl implements ShopSelectAttributeService {
    @Resource
    private C08SelectAttributeDao selectAttributeDao;

    @Override
    public C08SelectAttributeDO getSelectAttributeById(Long id) {
        return selectAttributeDao.selectById(id);
    }

    @Override
    public Integer saveOrUpdateSelectAttribute(C08SelectAttributeDO selectAttribute) {
        if (selectAttribute == null){
            return 0;
        }
        selectAttribute.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (selectAttribute.getId() == null){//添加
            selectAttribute.setCreateTime(LocalDateTime.now());
            return selectAttributeDao.insert(selectAttribute);
        }
        //编辑
        selectAttribute.setUpdateTime(LocalDateTime.now());
        return selectAttributeDao.updateById(selectAttribute);
    }

    @Override
    public Integer batchUpdateSelectAttribute(List<C08SelectAttributeDO> selectAttributes) {
        if (CollectionUtils.isEmpty(selectAttributes)){
            return 0;
        }
        return selectAttributeDao.updateList(selectAttributes);
    }

    @Override
    public List<C08SelectAttributeDO> listSelectAttributeHDPage(Map<String, Object> map) {
        return selectAttributeDao.listHDPage(map);
    }

    @Override
    public List<C08SelectAttributeDO> listSelectAttribute(EntityWrapper<C08SelectAttributeDO> ew) {
        return selectAttributeDao.selectList(ew);
    }

    @Override
    public int save(List<C08SelectAttributeSaveForm> list) {
        if(list==null||list.isEmpty()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        //删除旧数据
        selectAttributeDao.delete(new EntityWrapper<C08SelectAttributeDO>()
                .eq("goods_id", list.get(0).getGoodsId()));

        //插入新的关联关系
        List<C08SelectAttributeDO> c08SelectAttributes=new ArrayList<>();
        for (C08SelectAttributeSaveForm form : list) {
            C08SelectAttributeDO dto =new C08SelectAttributeDO();
            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            dto.setUpdateTime(LocalDateTime.now());
            dto.setCreateTime(LocalDateTime.now());
            dto.setGoodsId(form.getGoodsId());
            dto.setProjectId(form.getProjectId());
            dto.setContent(form.getContent1());
            dto.setProjectName(form.getProjectName());
            c08SelectAttributes.add(dto);
        }
        if(!c08SelectAttributes.isEmpty()){
            int i = selectAttributeDao.insertBatch(c08SelectAttributes);
            if(i<=0){
                throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"批量插入失败");
            }
        }
        return 1;
    }
}


