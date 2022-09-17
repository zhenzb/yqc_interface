package com.youqiancheng.service.shop.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C05CommentDao;
import com.youqiancheng.mybatis.dao.D04GoodsEvaluateDao;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;
import com.youqiancheng.service.shop.ShopEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:26
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopEvaluateServiceImpl implements ShopEvaluateService {
    @Resource
    private D04GoodsEvaluateDao goodsEvaluateDao;
    @Resource
    private C05CommentDao c05CommentDao;

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/15 18:28
     * @Param:
     * @return:
     * @Description: 商品评论记录 分页
     */
    @Override
    public List<C05CommentDO> listCommentHDPage(Map<String, Object> map) {
        return c05CommentDao.listHDPage(map);
    }

    @Override
    public C05CommentDO getCommentById(Long id) {
        return c05CommentDao.get(id);
    }

    @Override
    public int updateCommentById(C05CommentDO dto) {
        return c05CommentDao.updateById(dto);
    }

    @Override
    public int updateEvaluateById(D04GoodsEvaluateDO dto) {
        return goodsEvaluateDao.updateById(dto);
    }

    @Override
    public Integer batckUpdateComment(List<C05CommentDO> commentDO) {
       return c05CommentDao.updateList(commentDO);
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/15 18:28
     * @Param:
     * @return:
     * @Description: 商品评论记录 分页
     */
    @Override
    public List<D04GoodsEvaluateDO> listEvaluateHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return goodsEvaluateDao.listGoodsEvaluateHDPage(map);
    }

    @Override
    public D04GoodsEvaluateDO getEvaluateById(long id) {
        return goodsEvaluateDao.selectById(id);
    }

    @Override
    public Integer saveOrUpdateEvaluate(D04GoodsEvaluateDO goodsEvaluate) {
        if (goodsEvaluate == null){
            return 0;
        }
        goodsEvaluate.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (goodsEvaluate.getId() == null){//添加
            goodsEvaluate.setCreateTime(LocalDateTime.now());
            return goodsEvaluateDao.insert(goodsEvaluate);
        }
        //编辑
        goodsEvaluate.setUpdateTime(LocalDateTime.now());
        return goodsEvaluateDao.updateById(goodsEvaluate);
    }

    @Override
    public Integer batckUpdateEvaluate(List<D04GoodsEvaluateDO> goodsEvaluates) {
        if (CollectionUtils.isEmpty(goodsEvaluates)){
            return 0;
        }
        return goodsEvaluateDao.updateList(goodsEvaluates);
    }
}


