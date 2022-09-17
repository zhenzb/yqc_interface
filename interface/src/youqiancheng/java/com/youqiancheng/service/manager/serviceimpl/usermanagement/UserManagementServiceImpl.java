package com.youqiancheng.service.manager.serviceimpl.usermanagement;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.B01UserEnum;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.user.UserFreezeForm;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.B02UserAccountDao;
import com.youqiancheng.mybatis.dao.B03UserAccountFlowDao;
import com.youqiancheng.mybatis.dao.B07AuthenticationDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.service.manager.service.usermanagement.UserManagementService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {
    @Resource
    private B01UserDao userDao;
    @Resource
    private B02UserAccountDao b02UserAccountDao;
    @Resource
    private B03UserAccountFlowDao b03UserAccountFlowDao;
    @Resource
    private B07AuthenticationDao b07AuthenticationDao;
    /**
    　* @Description: 查询用户列表
    　* @author shalongteng
    　* @date 2020/4/8 9:53
    　*/
    @Override
    public List<B01UserDO> listUserHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return userDao.listUserHDPage(map);

    }
    /**
    　* @Description: 冻结用户
    　* @author shalongteng
    　* @date 2020/4/8 10:31
    　*/
    @Override
    public boolean freeze(UserFreezeForm userFreezeForm) {
        B01UserDO b01UserDO = userDao.selectById(userFreezeForm.getId());
        if(null ==  b01UserDO){
            throw new JsonException(ResultEnum.USER_NOT_EXIST);
        }
        // reqType 1.点击解冻 2.点击冻结
        b01UserDO.setStatus(userFreezeForm.getReqType()==1?B01UserEnum.NOT_FREEZE.getCode():B01UserEnum.IS_FREEZE.getCode());
        b01UserDO.setUpdateTime(LocalDateTime.now());
        userDao.updateById(b01UserDO);
        return true;
    }

    @Override
    public List<B02UserAccountDO> checkBalance(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return b02UserAccountDao.listB02UserAccountHDPage(map);
    }
    /**
    　* @Description: 分页获取账号流水
    　* @author shalongteng
    　* @date 2020/4/8 13:49
    　*/
    @Override
    public List<B03UserAccountFlowDO> checkBalanceDetail(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return b03UserAccountFlowDao.listB03UserAccountFlowHDPage(map);
    }
    /**
    　* @Description: 分页查询用户认证 列表
    　* @author shalongteng
    　* @date 2020/4/14 15:35
    　*/
    @Override
    public List<B07AuthenticationDO> listUserAuthHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }

        return b07AuthenticationDao.listUserAuthHDPage(map);
    }
    /**
    　* @Description: 批量通过拒绝
    　* @author shalongteng
    　* @date 2020/4/14 15:44
    　*/
    @Override
    public Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            B07AuthenticationDO b07AuthenticationDO = b07AuthenticationDao.selectById(shopPassRefuseForm.getId());
            if(b07AuthenticationDO == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            if(shopPassRefuseForm.getStatus()==1 || shopPassRefuseForm.getStatus()==2){
                b07AuthenticationDO.setStatus(shopPassRefuseForm.getStatus());
                b07AuthenticationDO.setUpdateTime(LocalDateTime.now());
                b07AuthenticationDao.updateById(b07AuthenticationDO);
                //修改用户状态
                B01UserDO b01UserDO = userDao.selectById(b07AuthenticationDO.getUserId());
                b01UserDO.setIsAuthentication(shopPassRefuseForm.getStatus());
                b01UserDO.setUpdateTime(LocalDateTime.now());
                userDao.updateById(b01UserDO);
            }
        }
        return 1;
    }
}
