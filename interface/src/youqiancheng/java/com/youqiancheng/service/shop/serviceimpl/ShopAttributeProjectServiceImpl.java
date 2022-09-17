package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.youqiancheng.mybatis.dao.C06AttributeProjectDao;
import com.youqiancheng.mybatis.model.C06AttributeProjectDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.shop.ShopAttributeProjectService;
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
 * @Date: 2020/4/18 17:27
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopAttributeProjectServiceImpl implements ShopAttributeProjectService {
    @Resource
    private C06AttributeProjectDao attributeProjectDao;

    @Override
    public C06AttributeProjectDO getAttributeProjectById(Long id) {
        return attributeProjectDao.selectById(id);
    }

    @Override
    public List<C06AttributeProjectDO> listAttributeProject(EntityWrapper<C06AttributeProjectDO> ew) {
        return attributeProjectDao.selectList(ew);
    }

    @Override
    public List<C06AttributeProjectDO> listAttributeProjectHDPage(Map<String, Object> map) {
        return attributeProjectDao.listAttributeProjectHDPage(map);
    }

    @Override
    public Integer saveOrUpdateAttributeProject(C06AttributeProjectDO attributeProject) {
        if (attributeProject == null){
            return 0;
        }
        attributeProject.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (attributeProject.getId() == null){//添加
            F08ShopUserDO shopUser = SecurityUtils.getShopUser();
            if(null ==attributeProject.getShopId()){
                if (shopUser == null){
                    throw new JsonException(Constants.$Null, "登录超时");
                }
                //验重
                EntityWrapper<C06AttributeProjectDO> ew = new EntityWrapper<>();
                ew.eq("shop_id",shopUser.getShopId()).eq("name",attributeProject.getName())
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
                List<C06AttributeProjectDO> list = this.listAttributeProject(ew);
                if (!CollectionUtils.isEmpty(list)){
                    throw new JsonException(Constants.Error, "属性项名称重复");
                }
                attributeProject.setShopId(shopUser.getShopId());
            }else{
                EntityWrapper<C06AttributeProjectDO> ew = new EntityWrapper<>();
                ew.eq("shop_id",attributeProject.getShopId()).eq("name",attributeProject.getName())
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
                List<C06AttributeProjectDO> list = this.listAttributeProject(ew);
                if (!CollectionUtils.isEmpty(list)){
                    throw new JsonException(Constants.Error, "属性项名称重复");
                }
                attributeProject.setShopId(attributeProject.getShopId());
            }
            attributeProject.setContent(attributeProject.getContent().replaceAll("，",","));
            attributeProject.setCreateTime(LocalDateTime.now());
            return attributeProjectDao.insert(attributeProject);
        }
        C06AttributeProjectDO attributeProjectOld = this.getAttributeProjectById(attributeProject.getId());
        if (attributeProjectOld != null && !attributeProjectOld.getName().equals(attributeProject.getName())){
            //验重
            EntityWrapper<C06AttributeProjectDO> ew = new EntityWrapper<>();
            ew.eq("shop_id",attributeProjectOld.getShopId()).eq("name",attributeProject.getName());
            List<C06AttributeProjectDO> list = this.listAttributeProject(ew);
            if (!CollectionUtils.isEmpty(list)){
                throw new JsonException(Constants.Error, "属性项名称重复");
            }
        }
        //编辑
        attributeProject.setUpdateTime(LocalDateTime.now());
        return attributeProjectDao.updateById(attributeProject);
    }

    @Override
    public Integer batchUpdateAttributeProject(List<C06AttributeProjectDO> attributeProjects) {
        return attributeProjectDao.updateList(attributeProjects);
    }
}


