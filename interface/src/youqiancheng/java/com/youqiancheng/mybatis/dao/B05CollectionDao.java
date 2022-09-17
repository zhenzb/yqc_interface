package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B05CollectionDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Mapper
public interface B05CollectionDao extends BaseMapper<B05CollectionDO> {

     B05CollectionDO get(Long id);


     List<B05CollectionDO> listHDPage(Map<String, Object> map);

     List<F01ShopDO> getCollectionShopHDPage(Map<String, Object> map);
     List<C10PublicityDO> getCollectionPublicityHDPage(Map<String, Object> map);

     List<C01GoodsDO> getCollectionGoodsHDPage(Map<String, Object> map);

     List<B05CollectionDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B05CollectionDO> b05Collections);



     int updateList(List<B05CollectionDO> b05Collections);

     int updateStatus(Map<String, Object> map);
}
