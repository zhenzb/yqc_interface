package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface F06WithdrawalApplicationDao  extends BaseMapper<F06WithdrawalApplicationDO>{
     List<F06WithdrawalApplicationDO> listWithdrawalApplicationHDPage(Map<String, Object> map);

     F06WithdrawalApplicationDO get(Long id);

     List<F06WithdrawalApplicationDO> list(Map<String, Object> map);
     List<F06WithdrawalApplicationDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F06WithdrawalApplicationDO> f06WithdrawalApplications);



     int updateList(List<F06WithdrawalApplicationDO> f06WithdrawalApplications);

     int updateStatus(Map<String, Object> map);

     List<F06WithdrawalApplicationDO> listWithdrawalHDPage(Map<String, Object> map);

    List<F06WithdrawalApplicationDO> selectPageList(Page<F06WithdrawalApplicationDO> page,
                                                    @Param("ew") EntityWrapper<F06WithdrawalApplicationDO> ew);

    List<F06WithdrawalApplicationDO> selectUserWithdrawalNumber(Long accountId);

     F06WithdrawalApplicationDO selectF06Withdrawal(Long id);
}
