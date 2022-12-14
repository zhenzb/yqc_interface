package com.youqiancheng.service.shop.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.dto.RouterDTO;
import com.handongkeji.util.Constants;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.ResetPasswordForm;
import com.youqiancheng.form.shop.system.user.ShopChangeForm;
import com.youqiancheng.form.shop.system.user.ShopResetPasswordForm;
import com.youqiancheng.form.shop.system.user.ShopUserSaveForm;
import com.youqiancheng.mybatis.dao.F01ShopDao;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F12PermissionDao;
import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F14RolePermissionDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.system.ShopRoleUserService;
import com.youqiancheng.service.shop.system.ShopUserService;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/7 9:53
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopUserServiceImpl implements ShopUserService {
    @Resource
    private F08ShopUserDao shopUserDao;
    @Resource
    private F14RolePermissionDao f14RolePermissionDao;
    @Resource
    private F12PermissionDao f12PermissionDao;
    @Resource
    private ShopRoleUserService shopRoleUserService;
    @Resource
    private F13RoleShopUserDao f13RoleShopUserDao;
    @Resource
    private F01ShopDao f01ShopDao;

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/7 9:55
     * @Param:
     * @return:
     * @Description: ??????
     */
    @Override
    public List<F08ShopUserDO> selectList(EntityWrapper<F08ShopUserDO> ew) {
        return shopUserDao.selectList(ew);
    }

    @Override
    public List<F08ShopUserDO> list(Map<String, Object> map) {
        List<F08ShopUserDO> list = shopUserDao.list(map);
        return list;
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/7 10:45
     * @Param: resetPasswordDto
     * @return:
     * @Description: ????????????
     */
    @Override
    public ResultVo ResetPassword(ShopResetPasswordForm resetPasswordForm) {
        if(resetPasswordForm == null){
            throw new JsonException(Constants.$Null, "?????????????????????????????????");
        }
        //?????????????????????

        EntityWrapper<F08ShopUserDO> ew = new EntityWrapper<F08ShopUserDO>();
        ew.eq("user_name",resetPasswordForm.getUsername());
        List<F08ShopUserDO> list = this.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            throw new JsonException(Constants.$Null, Constants.EXECUTION_EXIST_MESSAGE);
        }
        //????????????
        F08ShopUserDO shopUser = list.get(0);
        if(shopUser.getPwd().equals(PasswordUtils.authAdminPwd(resetPasswordForm.getNewpwd()))){
            throw new JsonException(Constants.$Failure, "???????????????????????????");
        }
        shopUser.setPwd(PasswordUtils.authAdminPwd(resetPasswordForm.getNewpwd()));
        shopUser.setUpdatePerson(shopUser.getUserName());
        shopUser.setUpdateTime(LocalDateTime.now());
        Integer rs = shopUserDao.updateById(shopUser);
        if (rs != 1){
            throw new JsonException(Constants.$Failure, "??????????????????");
        }
        return ResultVOUtils.success(shopUser.getUserName());
    }

    @Override
    public int ResetPasswordSetting(ResetPasswordForm form) {
        //????????????
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if(!shopUser.getPwd().equals(PasswordUtils.authAdminPwd(form.getOldPwd()))){
            throw new JsonException(Constants.$Failure, "??????????????????");
        }
        if(shopUser.getPwd().equals(PasswordUtils.authAdminPwd(form.getNewPwd()))){
            throw new JsonException(Constants.$Failure, "???????????????????????????");
        }
        shopUser.setPwd(PasswordUtils.authAdminPwd(form.getNewPwd()));
        shopUser.setUpdatePerson(shopUser.getUserName());
        shopUser.setUpdateTime(LocalDateTime.now());
        Integer rs = shopUserDao.updateById(shopUser);
        if (rs != 1){
            throw new JsonException(Constants.$Failure, "??????????????????");
        }
        return 1;
    }

    @Override
    public List<Long> getIdListByRolId(Long roleId) {
        EntityWrapper<F14RolePermissionDO> ew = new EntityWrapper<>();
        ew.eq("role_id",roleId);
        List<F14RolePermissionDO> f14RolePermissionList = f14RolePermissionDao.selectList(ew);
        List<Long> menuIds = f14RolePermissionList.stream().map(F14RolePermissionDO::getPermissionId).collect(Collectors.toList());
        // ??????roleId????????????,??????????????????????????????????????????????????????????????????????????????
        List<F12PermissionDO> menus = f12PermissionDao.selectList(new EntityWrapper<>());
        List<Long> temp= menuIds;
        for (F12PermissionDO menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        return menuIds;
    }

    @Override
    public List<RouterDTO> RouterByUserId(Long id) {
        //??????????????????
        F08ShopUserDO f08ShopUserDO = shopUserDao.get(id);
        F01ShopDO f01ShopDO =null;
        if(null !=f08ShopUserDO){
             f01ShopDO = f01ShopDao.get(f08ShopUserDO.getShopId());
        }else{
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"????????????????????????");
        }
        //????????????id ???????????????????????????
        List<F12PermissionDO> f12PermissionList = f12PermissionDao.findByUserId(id);
        if(CollectionUtils.isEmpty(f12PermissionList)){
           throw new JsonException(ResultEnum.DATA_NOT_EXIST,"?????????????????????");
        }
        List<F12PermissionDO> collect = new ArrayList<>();
        if(f01ShopDO.getMainProject() ==3 ){
             collect = f12PermissionList.stream().filter(item -> 710 != item.getMenuId() && 715 !=item.getMenuId()).collect(Collectors.toList());
        }else if(f01ShopDO.getMainProject() ==1){
            collect = f12PermissionList.stream().filter(item -> 722 != item.getMenuId()).collect(Collectors.toList());
        }else{
            collect =  f12PermissionList;
        }

        List<RouterDTO> routerDTOList = new ArrayList<>();
        for (F12PermissionDO f12Permission : collect) {
            RouterDTO routerDTO = new RouterDTO();
            routerDTO.setId(f12Permission.getMenuId());
            routerDTO.setParentId(f12Permission.getParentId());
            routerDTO.setChildren(new ArrayList<>());
            //type ??????   0?????????   1?????????   2?????????
            routerDTO.setLeaf(null != f12Permission.getType() && f12Permission.getType() == 1);
            routerDTO.setComponent(f12Permission.getComponent());
            routerDTO.setIconCls(f12Permission.getIcon());
            //????????????
            routerDTO.setOrderNum(f12Permission.getOrderNum());
            routerDTO.setPath(f12Permission.getUrl());
            routerDTO.setName(f12Permission.getName());
            routerDTO.setMenuShow(true);
            routerDTOList.add(routerDTO);
        }
        //????????? ?????????????????????
        return RouterDTO.buildList(routerDTOList,0l);
    }

    @Override
    public List<F08ShopUserDO> findByUserName(String userName) {
        if (StringUtils.isBlank(userName)){
            throw new JsonException(Constants.$Null, "????????????");
        }
        EntityWrapper<F08ShopUserDO> ew = new EntityWrapper<>();
        ew.eq("user_name",userName);
        return shopUserDao.selectList(ew);
    }

    @Override
    public int insertShopUser(F08ShopUserDO shopUser, ShopUserSaveForm shopUserSaveForm) {
        shopUser.setCreateTime(LocalDateTime.now());
        shopUser.setStatus(StatusConstant.enable.getCode());
        shopUser.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        shopUserDao.insert(shopUser);
        // ????????????
        if (!CollectionUtils.isEmpty(shopUserSaveForm.getRoleList())) {
            shopRoleUserService.insertRolesUserIdAll(shopUserSaveForm.getRoleList(), shopUser.getId());
        }
        return (int)shopUser.getId();
    }

    @Override
    public boolean updateUser(F08ShopUserDO shopUser) {
        shopUser.setUpdateTime(LocalDateTime.now());
        shopUserDao.updateById(shopUser);
        //?????????????????? ??????????????? ????????????
        shopRoleUserService.deleteByUserId(shopUser.getId());
        if (!CollectionUtils.isEmpty(shopUser.getRoleList())){
            List<F13RoleShopUserDO> addList = new ArrayList<>();
            for (Long id : shopUser.getRoleList()) {
                F13RoleShopUserDO roleShopUser = new F13RoleShopUserDO();
                roleShopUser.setUserId(roleShopUser.getId() );
                roleShopUser.setRoleId(id);
                addList.add(roleShopUser);
            }
            return f13RoleShopUserDao.insertBatch(addList) !=0?true:false;
        }
        return false;
    }
    @Override
    public boolean updateUserPwd(F08ShopUserDO shopUser) {
        shopUser.setUpdateTime(LocalDateTime.now());
        Integer integer = shopUserDao.updateById(shopUser);
        if(integer>0){
            return true;
        }
        return false;
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/13 10:23
     * @Param:
     * @return:
     * @Description: ???????????? ??????????????? ?????????????????????
     */
    @Override
    public boolean deleteUserById(Long id) {
        F08ShopUserDO shopUser = shopUserDao.selectById(id);
        if(shopUser == null){
            return false;
        }
        if(shopUser.getUserName().equals("admin")){
            throw new  JsonException(ResultEnum.NOT_ALLOW_CHANGE);
        }
        shopUserDao.deleteById(shopUser.getId());
        //???????????? ?????????????????????
        f13RoleShopUserDao.deleteByUserId(shopUser.getId());
        return true;
    }

    @Override
    public List<F08ShopUserDO> listUserHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return shopUserDao.listUserHDPage(map);
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/13 10:29
     * @Param:
     * @return:
     * @Description: ????????????????????????
     */
    @Override
    public List<String> changeStatus(List<ShopChangeForm> changeFormList) {
        if (CollectionUtils.isEmpty(changeFormList)){
            return null;
        }
        for (ShopChangeForm shopChangeForm : changeFormList) {
            F08ShopUserDO shopUser = new F08ShopUserDO();
            shopUser.setId(shopChangeForm.getId());
            shopUser  = shopUserDao.selectById(shopUser.getId());
            //????????????
            if(shopUser == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            //admin????????????????????????????????????
            if(shopUser.getUserName().equals("admin")){
                throw new JsonException(ResultEnum.NOT_ALLOW_CHANGE);
            }
            shopUser.setStatus(shopChangeForm.getStatus());
            shopUser.setUpdateTime(LocalDateTime.now());
            shopUserDao.updateById(shopUser);
        }
        return null;
    }
}


