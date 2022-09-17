package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C02GoodsPicDao;
import com.youqiancheng.mybatis.dao.F02ShopPicDao;
import com.youqiancheng.mybatis.model.C02GoodsPicDO;
import com.youqiancheng.service.shop.GoodsPicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/21 13:24
 * @Version: V1.0
 */
@Service
@Transactional
public class GoodsPicServiceImpl implements GoodsPicService {
    @Resource
    private C02GoodsPicDao goodsPicDao;

    @Override
    public List<C02GoodsPicDO> listGoodsPic(EntityWrapper<C02GoodsPicDO> ew) {
        return goodsPicDao.selectList(ew);
    }

    @Override
    public List<C02GoodsPicDO> listGoodsPicPage(Map<String, Object> map) {
        return goodsPicDao.listGoodsPicHDPage(map);
    }

    @Override
    public C02GoodsPicDO getGoodsPicById(long id) {
        return goodsPicDao.selectById(id);
    }

    @Override
    public Integer batchUpdateGoodsPicById(List<C02GoodsPicDO> goodsPics) {
        if (CollectionUtils.isEmpty(goodsPics)){
            return 0;
        }
        return goodsPicDao.updateList(goodsPics);
    }

    @Override
    public Integer saveOrUpdateGoodsPic(C02GoodsPicDO goodsPic) {
        if (goodsPic==null){
            return 0;
        }
        goodsPic.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (goodsPic.getId()==null){//添加
            goodsPic.setCreateTime(LocalDateTime.now());
            return goodsPicDao.insert(goodsPic);
        }
        goodsPic.setUpdateTime(LocalDateTime.now());
        return goodsPicDao.updateById(goodsPic);
    }
}


