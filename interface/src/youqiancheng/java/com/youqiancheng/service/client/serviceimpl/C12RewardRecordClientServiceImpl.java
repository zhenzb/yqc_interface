package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.youqiancheng.mybatis.dao.C12RewardRecordDao;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.dao.F07ShopAccountFlowDao;
import com.youqiancheng.mybatis.model.C12RewardRecordDO;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.mybatis.model.F07ShopAccountFlowDO;
import com.youqiancheng.service.client.service.C12RewardRecordClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
public class C12RewardRecordClientServiceImpl implements C12RewardRecordClientService {
    @Autowired
    private C12RewardRecordDao c12RewardRecordDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Autowired
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
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
        return c12RewardRecordDao.insert(c12RewardRecord);
    }


    @Override
    public int insertBatch(List<C12RewardRecordDO> c12RewardRecords){
        return c12RewardRecordDao.insertBatch(c12RewardRecords);
    }


    @Override
    public int update(C12RewardRecordDO c12RewardRecord) {
        return c12RewardRecordDao.updateById(c12RewardRecord);
    }
    @Override
    public int updateStatus(Long id,String no,int type) {
        //修改打赏状态
        C12RewardRecordDO c12RewardRecordDO = c12RewardRecordDao.get(id);
        c12RewardRecordDO.setStatus(StatusConstant.PayStatus.pay.getCode());
        c12RewardRecordDO.setPayType(type);
        c12RewardRecordDO.setPayNo(no);
      c12RewardRecordDao.updateById(c12RewardRecordDO);
        //修改商家账户
        QueryMap map =new QueryMap();
        map.put("shopId",c12RewardRecordDO.getShopId());
        List<F05ShopAccountDO> list = f05ShopAccountDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"打赏宣传店铺账户不存在");
        }
        F05ShopAccountDO f05ShopAccountDO = list.get(0);
        BigDecimal add = f05ShopAccountDO.getAccountBalance().add(c12RewardRecordDO.getMoney());
        BigDecimal add1 = f05ShopAccountDO.getAvailableWithdrawMoney().add(c12RewardRecordDO.getMoney());
        BigDecimal add2= f05ShopAccountDO.getAvailableBalance().add(c12RewardRecordDO.getMoney());

        //添加账户变动记录
        F07ShopAccountFlowDO dto=new F07ShopAccountFlowDO();
        dto.setAccountId(f05ShopAccountDO.getId());
        dto.setCreateTime(LocalDateTime.now());
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setFinalMoney(add);
        dto.setMoney(c12RewardRecordDO.getMoney());
        dto.setOriginalMoney(f05ShopAccountDO.getAccountBalance());
        dto.setUpdateTime(LocalDateTime.now());
        dto.setType(TypeConstant.ShopAccountType.reward_pay.getCode());
        dto.setSourceId(c12RewardRecordDO.getId());
        f07ShopAccountFlowDao.insert(dto);

        f05ShopAccountDO.setAccountBalance(add);
        f05ShopAccountDO.setAvailableWithdrawMoney(add1);
        f05ShopAccountDO.setAvailableBalance(add2);
        f05ShopAccountDao.updateById(f05ShopAccountDO);

      return 1;
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
