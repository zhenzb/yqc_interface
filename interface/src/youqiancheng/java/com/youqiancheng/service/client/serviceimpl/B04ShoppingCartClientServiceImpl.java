package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B04ShoppingCartDao;
import com.youqiancheng.mybatis.dao.C01GoodsDao;
import com.youqiancheng.mybatis.dao.C09GoodsSkuDao;
import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.service.client.service.B04ShoppingCartClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.client.ShoppingCartVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Service
@Transactional
public class B04ShoppingCartClientServiceImpl implements B04ShoppingCartClientService {
    @Autowired
    private B04ShoppingCartDao b04ShoppingCartDao;
    @Autowired
    private C09GoodsSkuDao c09GoodsSkuDao;
    @Autowired
    private C01GoodsDao c01GoodsDao;

    @Override
    public B04ShoppingCartDO get(Long id){
        return b04ShoppingCartDao.get(id);
    }


    @Override
    public  List<ShoppingCartVO> listHDPage(Map<String, Object> map) {
        List<B04ShoppingCartDO> b04ShoppingCartDOS = b04ShoppingCartDao.listHDPage(map);
        for (B04ShoppingCartDO b04ShoppingCartDO : b04ShoppingCartDOS) {
            if(null == b04ShoppingCartDO.getGoodPrice()){
                b04ShoppingCartDO.setPrice(b04ShoppingCartDO.getPrice());
                b04ShoppingCartDO.setTotalPrice(b04ShoppingCartDO.getPrice().multiply(BigDecimal.valueOf(b04ShoppingCartDO.getCommodityNumber())));
            }else{
                b04ShoppingCartDO.setPrice(b04ShoppingCartDO.getGoodPrice());
                b04ShoppingCartDO.setTotalPrice(b04ShoppingCartDO.getGoodPrice().multiply(BigDecimal.valueOf(b04ShoppingCartDO.getCommodityNumber())));
            }

        }
        Map<Long,List<B04ShoppingCartDO>> mapList=new LinkedHashMap<>();
        if(b04ShoppingCartDOS!=null&&!b04ShoppingCartDOS.isEmpty()){
            for (B04ShoppingCartDO b04ShoppingCartDO : b04ShoppingCartDOS) {
                if(mapList.isEmpty()){
                    List<B04ShoppingCartDO> list=new ArrayList<>();
                    list.add(b04ShoppingCartDO);
                    mapList.put(b04ShoppingCartDO.getShopId(),list);
                }else{
                    if(mapList.keySet().contains(b04ShoppingCartDO.getShopId())){
                        mapList.get(b04ShoppingCartDO.getShopId()).add(b04ShoppingCartDO);
                    }else{
                        List<B04ShoppingCartDO> list=new ArrayList<>();
                        list.add(b04ShoppingCartDO);
                        mapList.put(b04ShoppingCartDO.getShopId(),list);
                    }
                }

            }
        }
        List<ShoppingCartVO> list =new ArrayList<>();
        if(!mapList.values().isEmpty()){
            for (List<B04ShoppingCartDO> value : mapList.values()) {
                 ShoppingCartVO vo=new ShoppingCartVO();
                vo.setCartList(value);
                vo.setShopId(value.get(0).getShopId());
                vo.setShopName(value.get(0).getShopName());
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public List<B04ShoppingCartDO> list(Map<String, Object> map) {
        return b04ShoppingCartDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b04ShoppingCartDao.count(map);
    }


    @Override
    public int insert(B04ShoppingCartDO b04ShoppingCart) {
        //?????????????????????
        C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(b04ShoppingCart.getSkuId());
        if(c09GoodsSkuDO==null){
            //?????? ???????????????????????????
            C01GoodsDO c01GoodsDO = c01GoodsDao.get(b04ShoppingCart.getGoodsId());
            if(c01GoodsDO.getStock()<b04ShoppingCart.getCommodityNumber()){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"????????????");
            }
        }else{
            if(c09GoodsSkuDO.getNum()<b04ShoppingCart.getCommodityNumber()){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"????????????");
            }else{
                int num = c09GoodsSkuDO.getNum()-b04ShoppingCart.getCommodityNumber(); //?????????
                c09GoodsSkuDO.setNum(num);
                c09GoodsSkuDao.updateById(c09GoodsSkuDO);
            }
        }
        if(c09GoodsSkuDO !=null){
            b04ShoppingCart.setPrice(c09GoodsSkuDO.getGoodsPrice());
            b04ShoppingCart.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
        }else{
            b04ShoppingCart.setPrice(b04ShoppingCart.getPrice());
            b04ShoppingCart.setTotalPrice(b04ShoppingCart.getPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
        }
        //??????????????????????????????????????????????????????
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        if(0 == b04ShoppingCart.getSkuId()){
            map.put("goodsId",b04ShoppingCart.getGoodsId());
        }else{
            map.put("skuId",b04ShoppingCart.getSkuId());
        }
        map.put("userId",b04ShoppingCart.getUserId());
        List<B04ShoppingCartDO> list = b04ShoppingCartDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            return b04ShoppingCartDao.insert(b04ShoppingCart);
        }else{
            B04ShoppingCartDO b04ShoppingCartDO = list.get(0);
            int num=b04ShoppingCartDO.getCommodityNumber()+b04ShoppingCart.getCommodityNumber();
            b04ShoppingCartDO.setCommodityNumber(num);
            if(c09GoodsSkuDO == null){
                b04ShoppingCart.setPrice(b04ShoppingCart.getPrice());
                b04ShoppingCart.setTotalPrice(b04ShoppingCart.getPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
            }else{
                b04ShoppingCartDO.setPrice(c09GoodsSkuDO.getGoodsPrice());
                b04ShoppingCartDO.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(num)));
            }
            return b04ShoppingCartDao.updateById(b04ShoppingCartDO);
        }
    }


    @Override
    public int insertBatch(List<B04ShoppingCartDO> b04ShoppingCarts){
        return b04ShoppingCartDao.insertBatch(b04ShoppingCarts);
    }


    @Override
    public int update(B04ShoppingCartDO b04ShoppingCart) {
        C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(b04ShoppingCart.getSkuId());
        if(c09GoodsSkuDO==null){
            //?????? ???????????????????????????
            C01GoodsDO c01GoodsDO = c01GoodsDao.get(b04ShoppingCart.getGoodsId());
            if(c01GoodsDO.getStock()<b04ShoppingCart.getCommodityNumber()){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"????????????");
            }
            b04ShoppingCart.setPrice(c01GoodsDO.getPrice());
            b04ShoppingCart.setTotalPrice(c01GoodsDO.getPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
            b04ShoppingCart.setUpdateTime(LocalDateTime.now());
        }else{
            if(c09GoodsSkuDO.getNum()<b04ShoppingCart.getCommodityNumber()){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"????????????");
            }
            b04ShoppingCart.setPrice(c09GoodsSkuDO.getGoodsPrice());
            b04ShoppingCart.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
            b04ShoppingCart.setUpdateTime(LocalDateTime.now());
        }

        return b04ShoppingCartDao.updateById(b04ShoppingCart);
    }


    @Override
    public int delete(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return b04ShoppingCartDao.deleteShoppingCart(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b04ShoppingCartDao.updateStatus(param);
        }


}
