package com.youqiancheng.service.manager.serviceimpl.publicitymanagement;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.C01GoodsDao;
import com.youqiancheng.mybatis.dao.C10PublicityDao;
import com.youqiancheng.mybatis.dao.D02OrderItemDao;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.service.manager.service.publicitymanagement.PublicityService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PublicityServiceImpl implements PublicityService {
    @Resource
    private C10PublicityDao c10PublicityDao;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private D02OrderItemDao d02OrderItemDao;
    @Resource
    private C01GoodsDao c01GoodsDao;

    public PublicityServiceImpl() {
    }

    /**
    　* @Description: 分页查询 宣传
    　* @author shalongteng
    　* @date 2020/4/17 9:21
    　*/
    @Override
    public List<C10PublicityDO> listPublicityHDPage(Map<String, Object> map) {

        return c10PublicityDao.listPublicityHDPage(map);
    }

    @Override
    public Integer delete(Integer id) {
        C10PublicityDO c10PublicityDO = c10PublicityDao.selectById(id);
        if(c10PublicityDO == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        c10PublicityDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        c10PublicityDO.setUpdateTime(LocalDateTime.now());
        return c10PublicityDao.updateById(c10PublicityDO);
    }

    @Override
    public Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            C10PublicityDO c10PublicityDO = c10PublicityDao.selectById(shopPassRefuseForm.getId());
            if(c10PublicityDO == null){
                C01GoodsDO c01GoodsDO = c01GoodsDao.get(shopPassRefuseForm.getId());
                if(c01GoodsDO==null){
                    throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
                }else{
                    c01GoodsDO.setExamineStatus(shopPassRefuseForm.getStatus());
                    c01GoodsDO.setUpdateTime( LocalDateTime.now());
                    if(StatusConstant.DeleteFlag.delete.getCode()==shopPassRefuseForm.getDeleteFlag()){
                        c01GoodsDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                    }
                    if(shopPassRefuseForm.getIsSale() != null){
                        c01GoodsDO.setIsSale(shopPassRefuseForm.getIsSale());
                        c01GoodsDO.setUpdateTime(LocalDateTime.now());
                    }
                    c01GoodsDO.setReason(shopPassRefuseForm.getReason());
                    c01GoodsDao.updateById(c01GoodsDO);
                }

            }else {
                if (shopPassRefuseForm.getIsSale() != null) {
                    c10PublicityDO.setIsSale(shopPassRefuseForm.getIsSale());
                    c10PublicityDO.setPublicTime(LocalDateTime.now());
                }
                if (shopPassRefuseForm.getStatus() != null) {
                    c10PublicityDO.setExamineStatus(shopPassRefuseForm.getStatus());
                    c10PublicityDO.setExamineTime(LocalDateTime.now());
                }
                if (StatusConstant.DeleteFlag.delete.getCode() == shopPassRefuseForm.getDeleteFlag()) {
                    c10PublicityDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                }
                c10PublicityDO.setUpdateTime(LocalDateTime.now());
                c10PublicityDO.setReason(shopPassRefuseForm.getReason());
                c10PublicityDao.updateById(c10PublicityDO);
            }
        }
        return 1;
    }

    @Override
    public C10PublicityDO get(Long id) {
        return c10PublicityDao.get(id);
    }

    @Override
    public int update(C10PublicityDO dto) {
        return c10PublicityDao.updateAllColumnById(dto);
    }

//    @Override
//    public Integer deletePublicity(List<GoodsDeleteForm> goodsDeleteFormList) {
//        c10PublicityDao.s
//        return null;
//    }
}
