package com.youqiancheng.service.client.serviceimpl.classification;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.classification.C01GoodsClientService;
import com.youqiancheng.util.QueryMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 17:42
 */
@Service
@Transactional
public class C01GoodsClientServiceImpl implements C01GoodsClientService {
    @Resource
    private C01GoodsDao c01GoodsDao;
    @Resource
    private C02GoodsPicDao c02GoodsPicDao;
    @Resource
    private C08SelectAttributeDao c08SelectAttributeDao;
    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;
    @Resource
    private C05CommentDao c05CommentDao;
    @Resource
    private D04GoodsEvaluateDao d04GoodsEvaluateDao;
    @Resource
    private D05EvaluatePicDao d05EvaluatePicDao;
    @Resource
    private B05CollectionDao b05CollectionDao;

    @Override
    @Transactional(readOnly = true)
    public List<C01GoodsDO> getGoodsListPage(QueryMap map){
        return c01GoodsDao.getGoodsListHDPage(map);
    }

    @Override
    @Transactional(readOnly = true)
    public C01GoodsDO getGoodsInfo(Long id){
        return c01GoodsDao.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<C02GoodsPicDO> getGoodsPic(Long id){
        EntityWrapper<C02GoodsPicDO> ew=new  EntityWrapper<C02GoodsPicDO>();
        ew.eq("goods_id",id);
        //ew.eq("type", StatusConstant.goodsPicType.getCode());
        ew.eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        return c02GoodsPicDao.selectList(ew);
    }

    @Override
    @Transactional(readOnly = true)
    public List<C08SelectAttributeDO> getGoodSelecttAttribute(Long id)
    {
        EntityWrapper<C08SelectAttributeDO> ew=new  EntityWrapper<C08SelectAttributeDO>();
        ew.eq("goods_id",id);
        ew.eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        return c08SelectAttributeDao.selectList(ew);
    }

    @Override
    @Transactional(readOnly = true)
    public List<C09GoodsSkuDO> getGoodsku(Long id, String specifications)
    {
        EntityWrapper<C09GoodsSkuDO> ew=new  EntityWrapper<C09GoodsSkuDO>();
        ew.eq("goods_id",id);
        ew.eq("specifications",specifications);
        ew.eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        return c09GoodsSkuDao.selectList(ew);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<D04GoodsEvaluateDO> getGoodsEvaluateList(Long id)
    {
        EntityWrapper<D04GoodsEvaluateDO> ew=new  EntityWrapper<D04GoodsEvaluateDO>();
        ew.eq("goods_id",id);
        ew.eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        List<D04GoodsEvaluateDO> d04GoodsEvaluateDOS=d04GoodsEvaluateDao.selectList(ew);
        for (int i = 0; i <d04GoodsEvaluateDOS.size() ; i++) {
            EntityWrapper<D05EvaluatePicDO> ews=new  EntityWrapper<D05EvaluatePicDO>();
            ews.eq("evaluate_id",d04GoodsEvaluateDOS.get(i).getId());
            List<D05EvaluatePicDO> d05EvaluatePicDOS=d05EvaluatePicDao.selectList(ews);
            d04GoodsEvaluateDOS.get(i).setD05EvaluatePicDO(d05EvaluatePicDOS);
        }
        return d04GoodsEvaluateDOS;
    }

    @Override
    @Transactional(readOnly = true)
    public int  saveCollection(Long id,Long userId){
        B05CollectionDO b05CollectionDO=new B05CollectionDO();
        //用户id未插入
        b05CollectionDO.setUserId(userId);
        //b05CollectionDO.setType(StatusConstant.goodscolType.getCode());
        b05CollectionDO.setRelationId(id);
        b05CollectionDO.setCreatePerson(userId+"");
        b05CollectionDO.setCreateTime(LocalDateTime.now());
        b05CollectionDO.setUpdatePerson(userId+"");
        b05CollectionDO.setUpdateTime(LocalDateTime.now());
        b05CollectionDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        return b05CollectionDao.insert(b05CollectionDO);
    }

}
