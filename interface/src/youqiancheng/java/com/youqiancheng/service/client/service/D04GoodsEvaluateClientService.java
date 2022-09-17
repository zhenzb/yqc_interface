package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.D04GoodsEvaluateSaveForm;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;
import com.youqiancheng.vo.client.D04GoodsEvaluateClientVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
public interface D04GoodsEvaluateClientService {

     D04GoodsEvaluateClientVO get(Long id);

     List<D04GoodsEvaluateDO> listHDPage(Map<String, Object> map);

     List<D04GoodsEvaluateDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int save(D04GoodsEvaluateSaveForm d04GoodsEvaluate);



     int insertBatch(List<D04GoodsEvaluateDO> d04GoodsEvaluates);

     int update(D04GoodsEvaluateDO d04GoodsEvaluate);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
