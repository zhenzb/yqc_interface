package com.youqiancheng.service.shop.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.dto.RouterDTO;
import com.youqiancheng.form.shop.ResetPasswordForm;
import com.youqiancheng.form.shop.system.user.ShopChangeForm;
import com.youqiancheng.form.shop.system.user.ShopResetPasswordForm;
import com.youqiancheng.form.shop.system.user.ShopUserSaveForm;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.vo.result.ResultVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/7 9:52
 * @Version: V1.0
 */
public interface ShopUserService {
    List<F08ShopUserDO> selectList(EntityWrapper<F08ShopUserDO> ew);
    List<F08ShopUserDO> list(Map<String,Object> map);

    ResultVo ResetPassword(ShopResetPasswordForm resetPasswordForm);

    int ResetPasswordSetting(ResetPasswordForm form);

    List<Long> getIdListByRolId(Long roleId);

    List<RouterDTO> RouterByUserId(Long id);

    List<F08ShopUserDO> findByUserName(String userName);

    int insertShopUser(F08ShopUserDO shopUser, ShopUserSaveForm shopUserSaveForm);

    boolean updateUser(F08ShopUserDO shopUser);
    boolean updateUserPwd(F08ShopUserDO shopUser);

    boolean deleteUserById(Long id);

    List<F08ShopUserDO> listUserHDPage(Map<String, Object> map);

    List<String> changeStatus(List<ShopChangeForm> changeFormList);
}
