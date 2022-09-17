package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.vo.client.E01RedenvelopesStreetClientVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface E01RedenvelopesStreetClientService {

     E01RedenvelopesStreetDO get(Long id);

     List<E01RedenvelopesStreetDO> listHDPage(Map<String, Object> map);
     List<E01RedenvelopesStreetClientVO> listHDPageWithShopList(Map<String, Object> map);
     List<E01RedenvelopesStreetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(E01RedenvelopesStreetDO e01RedenvelopesStreet);

     int insertBatch(List<E01RedenvelopesStreetDO> e01RedenvelopesStreets);

     int update(E01RedenvelopesStreetDO e01RedenvelopesStreet);

     int stop(List<Long> ids);

     int start(List<Long> ids);
}
