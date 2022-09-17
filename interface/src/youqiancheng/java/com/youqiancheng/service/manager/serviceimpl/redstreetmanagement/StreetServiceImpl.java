package com.youqiancheng.service.manager.serviceimpl.redstreetmanagement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.manager.redstreet.RuleSaveEditForm;
import com.youqiancheng.form.manager.redstreet.StreetSaveForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.dao.C03CategoryDao;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.dao.E03RedenvelopesRuleDao;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.E03RedenvelopesRuleDO;
import com.youqiancheng.service.manager.service.redstreetmanagement.StreetService;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StreetServiceImpl implements StreetService {
    @Resource
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;
    @Resource
    private C03CategoryDao c03CategoryDao;
    @Resource
    private E03RedenvelopesRuleDao e03RedenvelopesRuleDao;


    public StreetServiceImpl() {
    }


    @Override
    public Map<String,List<E01RedenvelopesStreetDO>> getTree() {
        List<Tree<E01RedenvelopesStreetDO>> trees = new ArrayList<Tree<E01RedenvelopesStreetDO>>();
        List<E01RedenvelopesStreetDO> streetDOList = e01RedenvelopesStreetDao.selectList(null);
        Map<String,List<E01RedenvelopesStreetDO>> map = new HashMap();
        for (E01RedenvelopesStreetDO streetDO : streetDOList) {
            C03CategoryDO c03CategoryDO = c03CategoryDao.selectById(streetDO.getCategoryId());

            if(map.containsKey(c03CategoryDO.getName())){
                map.get(c03CategoryDO.getName()).add(streetDO);
            }else {
                map.put(c03CategoryDO.getName(),new ArrayList<>());
                map.get(c03CategoryDO.getName()).add(streetDO);
            }
        }
        return map;
    }

    @Override
    public void addStreet(StreetSaveForm streetSaveForm) {
        E01RedenvelopesStreetDO e01RedenvelopesStreetDO = new E01RedenvelopesStreetDO();
        if(streetSaveForm.getPackageNum() == null){
            streetSaveForm.setPackageNum(20);
        }
        if(streetSaveForm.getId() == null){
            if(streetSaveForm.getIsFree().equals(StatusConstant.IsFree.no.getCode()) ||
                    streetSaveForm.getIsFree().equals(StatusConstant.IsFree.yes.getCode())){
                BeanUtils.copyProperties(streetSaveForm,e01RedenvelopesStreetDO);
                e01RedenvelopesStreetDO.setStartTime(streetSaveForm.getStartTime());
                e01RedenvelopesStreetDO.setEndTime(streetSaveForm.getEndTime());
                e01RedenvelopesStreetDao.insert(e01RedenvelopesStreetDO);
            }
        }else {//编辑
            E01RedenvelopesStreetDO e01RedenvelopesStreetDO1 = e01RedenvelopesStreetDao.selectById(streetSaveForm.getId());
            BeanUtils.copyProperties(streetSaveForm,e01RedenvelopesStreetDO1);
            e01RedenvelopesStreetDO1.setUpdateTime(LocalDateTime.now());
            e01RedenvelopesStreetDao.updateById(e01RedenvelopesStreetDO1);
        }
    }

    @Override
    public Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            E01RedenvelopesStreetDO c10PublicityDO = e01RedenvelopesStreetDao.selectById(shopPassRefuseForm.getId());
            if(c10PublicityDO == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }

            if(shopPassRefuseForm.getStatus() != null){
                c10PublicityDO.setStatus(shopPassRefuseForm.getStatus());
            }
            if(shopPassRefuseForm.getDeleteFlag() == StatusConstant.DeleteFlag.delete.getCode()){
                c10PublicityDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
            }
            c10PublicityDO.setUpdateTime(LocalDateTime.now());
            e01RedenvelopesStreetDao.updateById(c10PublicityDO);

        }
        return 1;
    }

    @Override
    public void addOrEditAudit(RuleSaveEditForm ruleSaveEditForm) {
        e03RedenvelopesRuleDao.delete(null);
        E03RedenvelopesRuleDO e03RedenvelopesRuleDO = new E03RedenvelopesRuleDO();
        BeanUtils.copyProperties(ruleSaveEditForm,e03RedenvelopesRuleDO);

        e03RedenvelopesRuleDao.insert(e03RedenvelopesRuleDO);
    }

    @Override
    public E03RedenvelopesRuleDO getAudit() {
        List<E03RedenvelopesRuleDO> f04ShopExamineSetDOSList= e03RedenvelopesRuleDao.selectList(null);
        if(f04ShopExamineSetDOSList== null || f04ShopExamineSetDOSList.size() == 0){
            return null;
        }
        return f04ShopExamineSetDOSList.get(0);
    }

    @Override
    public List<C03CategoryDO> getCountryList() {
        EntityWrapper<C03CategoryDO> ew = new EntityWrapper<>();
        ew.eq("parent_id",0);
        List<C03CategoryDO> c03CategoryDOList = c03CategoryDao.selectList(ew);

        return c03CategoryDOList;
    }

    @Override
    public List<E01RedenvelopesStreetDO> getStreetList(Long id) {
        EntityWrapper<E01RedenvelopesStreetDO> ew = new EntityWrapper<>();
        ew.eq("category_id",id);
        ew.eq("delete_flag",1);
        List<E01RedenvelopesStreetDO> streetDOList = e01RedenvelopesStreetDao.selectList(ew);
        return streetDOList;
    }
    @Override
    public List<E01RedenvelopesStreetDO> getStreetList1(Map<String, Object>  map) {
        List<E01RedenvelopesStreetDO> streetDOList = e01RedenvelopesStreetDao.listHDPage(map);
        return streetDOList;
    }
}
