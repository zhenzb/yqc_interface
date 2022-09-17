package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C12RewardRecordDao;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.model.C12RewardRecordDO;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.service.app.service.C12RewardRecordAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Service
@Transactional
public class C12RewardRecordAppServiceImpl implements C12RewardRecordAppService {
    @Autowired
    private C12RewardRecordDao c12RewardRecordDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;


    @Override
    public C12RewardRecordDO get(Long id){
        return c12RewardRecordDao.get(id);
    }


    @Override
    public List<C12RewardRecordDO> listHDPage(Map<String, Object> map) {
        return c12RewardRecordDao.listHDPage(map);
    }


    @Override
    public List<C12RewardRecordDO> list(Map<String, Object> map) {
        return c12RewardRecordDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return c12RewardRecordDao.count(map);
    }


    @Override
    public int insert(C12RewardRecordDO c12RewardRecord) {
        //保存打赏记录
        Integer insert = c12RewardRecordDao.insert(c12RewardRecord);
        QueryMap map =new QueryMap();
        map.put("shopId",c12RewardRecord.getShopId());
        List<F05ShopAccountDO> list = f05ShopAccountDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"打赏宣传店铺账户不存在");
        }
        //修改商家账户金额
//        F05ShopAccountDO f05ShopAccountDO = list.get(0);
//        BigDecimal add = f05ShopAccountDO.getAccountBalance().add(c12RewardRecord.getMoney());
//        f05ShopAccountDO.setAccountBalance(add);
//        BigDecimal add1 = f05ShopAccountDO.getAvailableWithdrawMoney().add(c12RewardRecord.getMoney());
//        f05ShopAccountDO.setAvailableWithdrawMoney(add1);
//        BigDecimal add2= f05ShopAccountDO.getAvailableBalance().add(c12RewardRecord.getMoney());
//        f05ShopAccountDO.setAvailableBalance(add2);
//        f05ShopAccountDao.updateById(f05ShopAccountDO);
        return insert;
    }


    @Override
    public int insertBatch(List<C12RewardRecordDO> c12RewardRecords){
        return c12RewardRecordDao.insertBatch(c12RewardRecords);
    }


    @Override
    public int update(C12RewardRecordDO c12RewardRecord) {
        c12RewardRecord.setUpdateTime(LocalDateTime.now());
        return c12RewardRecordDao.updateById(c12RewardRecord);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return c12RewardRecordDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return c12RewardRecordDao.updateStatus(param);
        }
    }
