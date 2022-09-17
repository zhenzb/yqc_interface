package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.vo.app.E01RedenvelopesStreetVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface E01RedenvelopesStreetAppService {

     E01RedenvelopesStreetDO get(Long id);
     List<E01RedenvelopesStreetDO> listHDPage(Map<String, Object> map);

     List<E01RedenvelopesStreetDO> listE01Redenve(Map<String, Object> map);

     List<E01RedenvelopesStreetVO> listHDPageWithShopList(Map<String, Object> map);

     List<E01RedenvelopesStreetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(E01RedenvelopesStreetDO e01RedenvelopesStreet);

     int insertBatch(List<E01RedenvelopesStreetDO> e01RedenvelopesStreets);

     int update(E01RedenvelopesStreetDO e01RedenvelopesStreet);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
