package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.B09BankCardDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface B09BankCardAppService {

     B09BankCardDO get(Long id);

     List<B09BankCardDO> listHDPage(Map<String, Object> map);

     List<B09BankCardDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B09BankCardDO b09BankCard);

     int insertBatch(List<B09BankCardDO> b09BankCards);

     int update(B09BankCardDO b09BankCard);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
