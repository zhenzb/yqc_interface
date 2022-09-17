package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.dao.E04RedenvelopesGrantRecordDao;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.app.service.E01RedenvelopesStreetAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.app.E01RedenvelopesStreetVO;
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
public class E01RedenvelopesStreetAppServiceImpl implements E01RedenvelopesStreetAppService {
    @Autowired
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;
    @Autowired
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;

    @Override
    public E01RedenvelopesStreetDO get(Long id){
        return e01RedenvelopesStreetDao.get(id);
    }


    @Override
    public List<E01RedenvelopesStreetDO> listHDPage(Map<String, Object> map) {
        //应该获取没有被禁用的红包街
        map.put("status",StatusConstant.enable.getCode());
        //e01RedenvelopesStreetDao.listHDPage(map);
       return  e01RedenvelopesStreetDao.listHDPage(map);
    }

    @Override
    public List<E01RedenvelopesStreetDO> listE01Redenve(Map<String, Object> map) {
        //应该获取没有被禁用的红包街
        map.put("status",StatusConstant.enable.getCode());
        return  e01RedenvelopesStreetDao.listE01Redenve(map);
    }
    @Override
    public List<E01RedenvelopesStreetVO> listHDPageWithShopList(Map<String, Object> map) {
        //查询街区列表
        //应该获取没有被禁用的红包街
        map.put("status",StatusConstant.enable.getCode());
        List<E01RedenvelopesStreetDO> e01RedenvelopesStreetDOS = e01RedenvelopesStreetDao.listHDPage(map);
        if(e01RedenvelopesStreetDOS==null||e01RedenvelopesStreetDOS.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到红包街信息");
        }
        List<E01RedenvelopesStreetVO> voList=new ArrayList<>();
        //循环街区获取对应街区的10个商家
        for (E01RedenvelopesStreetDO e01RedenvelopesStreetDO : e01RedenvelopesStreetDOS) {
            E01RedenvelopesStreetVO vo=new E01RedenvelopesStreetVO();
            BeanUtils.copyProperties(e01RedenvelopesStreetDO,vo);
            //返回个数条件
            EntyPage page=new EntyPage();
            page.setCurrentPage(1);
            page.setPageSize(1000);
            QueryMap queryMap = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
            //街区ID
            queryMap.put("streetId",e01RedenvelopesStreetDO.getId());
            queryMap.put("endFlag",StatusConstant.EndFlag.un_end.getCode());
            //通过红包街的id去查发放红包的商家,这是个关联查询,得到在这条街上发放红包的商家
            List<F01ShopDO> shopListByRedEnvelopesHDPage = e04RedenvelopesGrantRecordDao.getShopListByRedEnvelopesHDPage(queryMap);
            //每条街都有好多商家,所以弄个集合保存
            vo.setShopList(shopListByRedEnvelopesHDPage);
            //最好全部添加到提前定义的集合了做返回.

            voList.add(vo);
        }
        return voList;
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
