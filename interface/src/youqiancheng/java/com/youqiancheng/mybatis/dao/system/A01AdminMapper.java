package com.youqiancheng.mybatis.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A01Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface A01AdminMapper extends BaseMapper<A01Admin> {

    int insertSelective(A01Admin record);

    A01Admin findByUserName(String username);

    void updateAuthAdmin(A01Admin au01AdminUp);

    List<A01Admin> listAdminHDPage(Map<String, Object> map);

}