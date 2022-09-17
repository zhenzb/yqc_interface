package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.shop.C07SelectProjectSaveForm;
import com.youqiancheng.mybatis.dao.C07SelectProjectDao;
import com.youqiancheng.mybatis.model.C07SelectProjectDO;
import com.youqiancheng.service.shop.ShopSelectProjectService;
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
 * @Date: 2020/4/20 10:48
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopSelectProjectServiceImpl implements ShopSelectProjectService {
    @Resource
    private C07SelectProjectDao selectProjectDao;

    @Override
    public C07SelectProjectDO getSelectProjectById(Long id) {
        return selectProjectDao.selectById(id);
    }

    @Override
    public Integer saveOrUpdateSelectProject(C07SelectProjectDO selectProjec) {
        if (selectProjec == null){
            return 0;
        }
        selectProjec.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (selectProjec.getId()==null){//添加
            selectProjec.setCreateTime(LocalDateTime.now());
            return selectProjectDao.insert(selectProjec);
        }
        //编辑
        selectProjec.setUpdateTime(LocalDateTime.now());
        return selectProjectDao.updateById(selectProjec);
    }

    @Override
    public Integer batchUpdateSelectProject(List<C07SelectProjectDO> selectProjects) {
        if (CollectionUtils.isEmpty(selectProjects)){
            return 0;
        }
        return selectProjectDao.updateList(selectProjects);
    }

    @Override
    public List<C07SelectProjectDO> listSelectProjectHDPage(Map<String, Object> map) {
        return selectProjectDao.listHDPage(map);
    }

    @Override
    public List<C07SelectProjectDO> listSelectProject(EntityWrapper<C07SelectProjectDO> ew) {
        return selectProjectDao.selectList(ew);
    }

    @Override
    public int save(C07SelectProjectSaveForm form) {
        if(form.getProjectIdList()==null||form.getProjectIdList().length<=0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"属性项数组不能为空");
        }
        //删除旧数据
       selectProjectDao.delete(new EntityWrapper<C07SelectProjectDO>()
               .eq("goods_id", form.getGoodsId()));

       //插入新的关联关系
        List<C07SelectProjectDO> c07SelectProjects=new ArrayList<>();
        for (Long aLong : form.getProjectIdList()) {
            C07SelectProjectDO dto =new C07SelectProjectDO();
            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            dto.setUpdateTime(LocalDateTime.now());
            dto.setCreateTime(LocalDateTime.now());
            dto.setGoodsId(form.getGoodsId());
            dto.setProjectId(aLong);
            c07SelectProjects.add(dto);
        }
        if(!c07SelectProjects.isEmpty()){
            int i = selectProjectDao.insertBatch(c07SelectProjects);
            if(i<=0){
                throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"批量插入失败");
            }
        }
        return 1;
    }

    @Override
    public List<C07SelectProjectDO>  listWithContent(Map<String, Object> map) {
        List<C07SelectProjectDO> c07SelectProjectDOS  = selectProjectDao.listWithContent(map);
        return c07SelectProjectDOS;
    }
}


