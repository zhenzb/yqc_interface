package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.shop.C09GoodsSkuSaveForm;
import com.youqiancheng.mybatis.dao.C09GoodsSkuDao;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.service.shop.ShopGoodsSkuService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/18 15:56
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopGoodsSkuServiceImpl implements ShopGoodsSkuService {
    @Resource
    private C09GoodsSkuDao goodsSkuDao;

    @Override
    public List<C09GoodsSkuDO> listGoodsSkuHDPage(Map<String, Object> map) {
        return goodsSkuDao.listHDPage(map);
    }

    @Override
    public C09GoodsSkuDO getGoodsSkuById(Long id) {
        return goodsSkuDao.selectById(id);
    }

    @Override
    public Integer saveOrUpdateGoodsSku(C09GoodsSkuDO goodsSku) {
        if (goodsSku == null){
            return 0;
        }
        goodsSku.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (goodsSku.getId() == null){//新增
            goodsSku.setCreateTime(LocalDateTime.now());
            return goodsSkuDao.insert(goodsSku);
        }
        //编辑
        goodsSku.setUpdateTime(LocalDateTime.now());
        return goodsSkuDao.updateById(goodsSku);
    }

    @Override
    public Integer batckUpdateGoodsSku(List<C09GoodsSkuDO> goodsSkus) {
        if (CollectionUtils.isEmpty(goodsSkus)){
            return 0;
        }
        return goodsSkuDao.updateList(goodsSkus);
    }

    @Override
    public Integer save(List<C09GoodsSkuSaveForm> list) {
        if(list==null||list.isEmpty()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        goodsSkuDao.delete(new EntityWrapper<C09GoodsSkuDO>().eq("goods_id",list.get(0).getGoodsId()));
        //插入新的关联关系
        List<C09GoodsSkuDO> c08SelectAttributes=new ArrayList<>();
        for (C09GoodsSkuSaveForm form : list) {
            C09GoodsSkuDO dto =new C09GoodsSkuDO();
            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            dto.setUpdateTime(LocalDateTime.now());
            dto.setCreateTime(LocalDateTime.now());
            dto.setGoodsId(form.getGoodsId());
            dto.setSpecifications(form.getSpecifications());
            dto.setGoodsPrice(form.getGoodsPrice());
            dto.setVersion(1);
            dto.setGoodsDesc(form.getGoodsDesc());
            dto.setNum(form.getNum());
            c08SelectAttributes.add(dto);
        }
        if(!c08SelectAttributes.isEmpty()){
            int i = goodsSkuDao.insertBatch(c08SelectAttributes);
            if(i<=0){
                throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"批量插入失败");
            }
        }

        return 1;
    }

    @Override
    public Integer delete(List<Long> ids) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("ids",ids);
        map.put("deleteFlag",StatusConstant.DeleteFlag.delete.getCode());
        goodsSkuDao.updateStatus(map);
        return null;
    }

    @Override
    public Integer edit(C09GoodsSkuDO dto) {
        dto.setUpdateTime(LocalDateTime.now());
        goodsSkuDao.updateById(dto);
        return null;
    }
}


