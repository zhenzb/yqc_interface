package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.vo.app.B01UserAppVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B01UserDao  extends BaseMapper<B01UserDO>{

     B01UserDO get(Long id);

     List<B01UserDO> list(Map<String, Object> map);
     List<B01UserDO> listHDPage(Map<String, Object> map);
     int count(Map<String, Object> map);
     int insertBatch(List<B01UserDO> b01Users);
     int updateList(List<B01UserDO> b01Users);

     int updateStatus(Map<String, Object> map);

    Long selectForDay();

     Long selectForMonth();

    Long selectForAll();

    List<B01UserDO> listUserHDPage(Map<String, Object> map);

    List<B01UserDO> listUserByUserIds(List<Long> ids);

}
