package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A19ExpressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-06-15
 */
@Mapper
public interface A19ExpressDao extends BaseMapper<A19ExpressDO> {

     A19ExpressDO get(Long id);


     List<A19ExpressDO> listHDPage(Map<String, Object> map);

     List<A19ExpressDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A19ExpressDO> a19Expresss);



     int updateList(List<A19ExpressDO> a19Expresss);

     int updateStatus(Map<String, Object> map);
    //搜索快递公司
     List<A19ExpressDO> getCourierServicesCompanylist(Map<String, Object> map);
}
