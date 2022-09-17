package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.dao.E04RedenvelopesGrantRecordDao;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.client.service.E01RedenvelopesStreetClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.client.E01RedenvelopesStreetClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Service
@Transactional
public class E01RedenvelopesStreetClientServiceImpl implements E01RedenvelopesStreetClientService {
    @Autowired
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;
    @Autowired
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;

    @Override
    public E01RedenvelopesStreetDO get(Long id){
        return e01RedenvelopesStreetDao.get(id);
    }

    @Override
    public List<E01RedenvelopesStreetClientVO> listHDPageWithShopList(Map<String, Object> map) {
        //查询街区列表
        List<E01RedenvelopesStreetDO> e01RedenvelopesStreetDOS = e01RedenvelopesStreetDao.listHDPage(map);
        if(e01RedenvelopesStreetDOS==null||e01RedenvelopesStreetDOS.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到红包街信息");
        }
        List<E01RedenvelopesStreetClientVO> voList=new ArrayList<>();
        //循环街区获取对应街区的12个商家
        for (E01RedenvelopesStreetDO e01RedenvelopesStreetDO : e01RedenvelopesStreetDOS) {
            E01RedenvelopesStreetClientVO vo=new E01RedenvelopesStreetClientVO();
            BeanUtils.copyProperties(e01RedenvelopesStreetDO,vo);
            //返回个数条件
            EntyPage page=new EntyPage();
            page.setCurrentPage(1);
            page.setPageSize(1000);
            QueryMap queryMap = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
            //街区ID
            queryMap.put("streetId",e01RedenvelopesStreetDO.getId());
            queryMap.put("endFlag",StatusConstant.EndFlag.un_end.getCode());
            List<F01ShopDO> shopListByRedEnvelopesHDPage = e04RedenvelopesGrantRecordDao.getShopListByRedEnvelopesHDPage(queryMap);


            vo.setShopList(shopListByRedEnvelopesHDPage);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<E01RedenvelopesStreetDO> listHDPage(Map<String, Object> map) {
        return e01RedenvelopesStreetDao.listHDPage(map);
    }


    @Override
    public List<E01RedenvelopesStreetDO> list(Map<String, Object> map) {
        return e01RedenvelopesStreetDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return e01RedenvelopesStreetDao.count(map);
    }


    @Override
    public int insert(E01RedenvelopesStreetDO e01RedenvelopesStreet) {
        return e01RedenvelopesStreetDao.insert(e01RedenvelopesStreet);
    }


    @Override
    public int insertBatch(List<E01RedenvelopesStreetDO> e01RedenvelopesStreets){
        return e01RedenvelopesStreetDao.insertBatch(e01RedenvelopesStreets);
    }


    @Override
    public int update(E01RedenvelopesStreetDO e01RedenvelopesStreet) {
        e01RedenvelopesStreet.setUpdateTime(LocalDateTime.now());
        return e01RedenvelopesStreetDao.updateById(e01RedenvelopesStreet);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return e01RedenvelopesStreetDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return e01RedenvelopesStreetDao.updateStatus(param);
        }
    }
