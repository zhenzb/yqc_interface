package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.youqiancheng.form.client.C09GoodsSkuSearchForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C02GoodsPicDO;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.service.client.service.C01GoodsClientService;
import com.youqiancheng.vo.client.C01GoodsClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Service
@Transactional
public class C01GoodsClientServiceImpl implements C01GoodsClientService {
    @Autowired
    private C01GoodsDao c01GoodsDao;
    @Autowired
    private C02GoodsPicDao c02GoodsPicDao;
    @Autowired
    private C08SelectAttributeDao c08SelectAttributeDao;
    @Autowired
    private C09GoodsSkuDao c09GoodsSkuDao;
    @Autowired
    private D04GoodsEvaluateDao  d04GoodsEvaluateDao;
    @Override
    public C01GoodsClientVO get(Long id){
        C01GoodsDO c01GoodsDO =c01GoodsDao.get(id);
        if(c01GoodsDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到商品信息");
        }
        C01GoodsClientVO vo=new C01GoodsClientVO();
        BeanUtils.copyProperties(c01GoodsDO,vo);

        List<C02GoodsPicDO> c02GoodsPicDOS = c02GoodsPicDao.selectList(
                new EntityWrapper<C02GoodsPicDO>()
                        .eq("goods_id",id)
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
                        .eq("type", TypeConstant.CoodsPicType.goods.getCode())
        );
        if(c02GoodsPicDOS!=null&&c02GoodsPicDOS.size()>0){
            List<String> collect = c02GoodsPicDOS.stream().map(C02GoodsPicDO::getPicUrl).collect(Collectors.toList());
            vo.setPicList(collect);
        }
        //修改商品浏览量
        c01GoodsDO.setBrowseVolume(c01GoodsDO.getBrowseVolume()+1);
        c01GoodsDao.updateById(c01GoodsDO);
        //评论数
        Map<String,Object> map=new HashMap<>();
        map.put("goodsId",id);
        int count = d04GoodsEvaluateDao.count(map);
        vo.setEvaluateCount(count);
        return vo;
    }


    @Override
    public List<C01GoodsDO> listHDPageWithOrder(Map<String, Object> map) {
        return c01GoodsDao.listWithOrderHDPage(map);
    }

    @Override
    public List<C01GoodsDO> list(Map<String, Object> map) {
        return c01GoodsDao.list(map);
    }

  @Override
    public List<C01GoodsDO> listHDPage(Map<String, Object> map) {
        return c01GoodsDao.listHDPage(map);
    }


    @Override
    public int count(Map<String, Object> map){
        return c01GoodsDao.count(map);
    }


    @Override
    public int insert(C01GoodsDO c01Goods) {
        return c01GoodsDao.insert(c01Goods);
    }


    @Override
    public int insertBatch(List<C01GoodsDO> c01Goodss){
        return c01GoodsDao.insertBatch(c01Goodss);
    }


    @Override
    public int update(C01GoodsDO c01Goods) {
        return c01GoodsDao.updateById(c01Goods);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return c01GoodsDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return c01GoodsDao.updateStatus(param);
        }
    @Override
    public List<C08SelectAttributeDO> getSelectAttribute(Long id) {
        Map<String,Object> map =new HashMap<>();
        map.put("goodsId",id);
        List<C08SelectAttributeDO> list = c08SelectAttributeDao.list(map);
        return list;
    }

    @Override
    public C09GoodsSkuDO getSku(C09GoodsSkuSearchForm form) {
        //根据商品ID查询所有库存
        Map<String,Object> map =new HashMap<>();
        map.put("goodsId",form.getGoodsId());
        List<C09GoodsSkuDO> list = c09GoodsSkuDao.list(map);
        if(list==null||list.isEmpty()){
            return null;
        }
        //根据传入规格匹配库存
        if(!StringUtils.isBlank(form.getSpecifications())){
            //将传入规格拆分
            String[] speclist= form.getSpecifications().split(",");
            //循环库存：如果库存中对应的规格与出入规格匹配则返回库存记录
            for (C09GoodsSkuDO c09GoodsSkuDO : list) {
                String specifications = c09GoodsSkuDO.getSpecifications();
                List<String> split = Arrays.asList(specifications.split(","));
                if(split.size()!=speclist.length){
                    throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"规格与库存不匹配");
                }
                boolean flag=true;
                for(int j=0;j<speclist.length;j++){
                    if(!split.contains(speclist[j])){
                        flag=false;
                    }
                }
                if(flag){
                    return  c09GoodsSkuDO;
                }
            }
        }
        return null;
    }



    @Override
    public List<C01GoodsDO> getGoodsById(Map<String,Object> map) {
        //根据商家id查询商品的信息
        List<C01GoodsDO> c01GoodsDO =c01GoodsDao.getGoodsByIdHDPage(map);
        return c01GoodsDO;
    }
}
