package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B05CollectionDao;
import com.youqiancheng.mybatis.model.B05CollectionDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.app.service.B05CollectionAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Service
@Transactional
public class B05CollectionAppServiceImpl implements B05CollectionAppService {
    @Autowired
    private B05CollectionDao b05CollectionDao;


    @Override
    public B05CollectionDO get(Long id){
        return b05CollectionDao.get(id);
    }


    @Override
    public List<B05CollectionDO> listHDPage(Map<String, Object> map) {
        return b05CollectionDao.listHDPage(map);
    }

    @Override
    public List<F01ShopDO> getCollectionShop(Map<String, Object> map) {
        return b05CollectionDao.getCollectionShopHDPage(map);
    }

    @Override
    public List<C10PublicityDO> getCollectionPublicity(Map<String, Object> map) {
        return b05CollectionDao.getCollectionPublicityHDPage(map);
    }

    @Override
    public List<C01GoodsDO> getCollectionGoods(Map<String, Object> map) {
        return b05CollectionDao.getCollectionGoodsHDPage(map);
    }


    @Override
    public List<B05CollectionDO> list(Map<String, Object> map) {
        return b05CollectionDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b05CollectionDao.count(map);
    }


    @Override
    public int insert(B05CollectionDO b05Collection) {
        return b05CollectionDao.insert(b05Collection);
    }


    @Override
    public int insertBatch(List<B05CollectionDO> b05Collections){
        return b05CollectionDao.insertBatch(b05Collections);
    }


    @Override
    public int update(B05CollectionDO b05Collection) {
        b05Collection.setUpdateTime(LocalDateTime.now());
        return b05CollectionDao.updateById(b05Collection);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b05CollectionDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b05CollectionDao.updateStatus(param);
        }

    @Override
    public int delete(B05CollectionDO b05Collection) {
        Integer delete = b05CollectionDao.delete(new EntityWrapper<B05CollectionDO>()
                .eq("relation_id", b05Collection.getRelationId())
                .eq("type", b05Collection.getType())
                .eq("user_id", b05Collection.getUserId())
        );
        return delete;
    }
}
