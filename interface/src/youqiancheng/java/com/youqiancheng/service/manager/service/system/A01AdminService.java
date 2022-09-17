package com.youqiancheng.service.manager.service.system;


import com.handongkeji.dto.RouterDTO;
import com.youqiancheng.form.manager.AuthAdmin.A01AdminSaveForm;
import com.youqiancheng.form.manager.AuthAdmin.A01ChangeForm;
import com.youqiancheng.mybatis.model.A01Admin;

import java.util.List;
import java.util.Map;

public interface A01AdminService {
    A01Admin findByUserName(String username);

    void updateAuthAdmin(A01Admin au01AdminUp);

    List<RouterDTO> RouterByAdminId(Long id);

    int insertAuthAdmin(A01Admin a01Admin, A01AdminSaveForm authAdminSaveForm);

    List<A01Admin> listAdminHDPage(Map<String, Object> map);

    List<String> changeStatus(List<A01ChangeForm> changeForm);

    boolean updateAdmin(A01Admin a01Admin);

    boolean deleteAdminById(Long id);

    List<Long> getIdListByRolId(Long roleId);

    boolean verifyPhoneNumbers(String phoneNumbers);

    boolean setNewPassword(String phoneNumbers, String password);
}
