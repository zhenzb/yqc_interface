package com.youqiancheng.service.app.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.B04ShoppingCartAddAndSubSaveForm;
import com.youqiancheng.mybatis.dao.B04ShoppingCartDao;
import com.youqiancheng.mybatis.dao.C01GoodsDao;
import com.youqiancheng.mybatis.dao.C09GoodsSkuDao;
import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.service.app.service.B04ShoppingCartAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.app.ShoppingCartVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Service
@Transactional
public class B04ShoppingCartAppServiceImpl implements B04ShoppingCartAppService {
    @Resource
    private B04ShoppingCartDao b04ShoppingCartDao;
    @Resource
    private C09GoodsSkuDao  c09GoodsSkuDao;
    @Resource
    private C01GoodsDao c01GoodsDao;

    @Override
    public B04ShoppingCartDO get(Long id){
        return b04ShoppingCartDao.get(id);
    }


    @Override
    public List<ShoppingCartVO>   listHDPage(Map<String, Object> map) {
        //获取这个用户购物车里的所有商品,以及商品的规格,价格,库存量
        List<B04ShoppingCartDO> b04ShoppingCartDOS = b04ShoppingCartDao.listHDPage(map);
        for (B04ShoppingCartDO b04ShoppingCartDO : b04ShoppingCartDOS) {
            if(null == b04ShoppingCartDO.getGoodPrice()){
                b04ShoppingCartDO.setPrice(b04ShoppingCartDO.getPrice());
                b04ShoppingCartDO.setTotalPrice(b04ShoppingCartDO.getPrice().multiply(BigDecimal.valueOf(b04ShoppingCartDO.getCommodityNumber())));
            }else{
                //你放入购物车,过了一会或者几天,这个商品价格调价了,所以要获取最新价格在赋值给单价
                b04ShoppingCartDO.setPrice(b04ShoppingCartDO.getGoodPrice());
                //把数量与最新的价格相乘赋值给总价
                b04ShoppingCartDO.setTotalPrice(b04ShoppingCartDO.getGoodPrice().multiply(BigDecimal.valueOf(b04ShoppingCartDO.getCommodityNumber())));
            }
        }
        //存储以商家ID为key，购物车记录数组为value的键值对
        Map<Long,List<B04ShoppingCartDO>> mapList=new HashMap<>();
        if(b04ShoppingCartDOS!=null&&!b04ShoppingCartDOS.isEmpty()){
            for (B04ShoppingCartDO b04ShoppingCartDO : b04ShoppingCartDOS) {
                //map为空，则放入第一个元素
                if(mapList.isEmpty()){
                    List<B04ShoppingCartDO> list=new ArrayList<>();
                    list.add(b04ShoppingCartDO);
                    //把在一个商家买的商品(商品是个集合),放在一个map里
                    mapList.put(b04ShoppingCartDO.getShopId(),list);
                }else{
                    //假如这个map不为空,就一这个key,作为键,来存值
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
        //更新库存和价格
        C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(b04ShoppingCart.getSkuId());
        C01GoodsDO c01GoodsDO=null;
        if(c09GoodsSkuDO==null){
            c01GoodsDO = c01GoodsDao.get(b04ShoppingCart.getGoodsId());
            if(c01GoodsDO !=null){
//                    int num = c01GoodsDO.getStock()-1; //库存数
//                    c01GoodsDO.setStock(num);
//                    c01GoodsDao.updateById(c01GoodsDO);
                if(c01GoodsDO.getStock()<b04ShoppingCart.getCommodityNumber()){
                    throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"库存不足");
                }
            }else {
                throw new JsonException(ResultEnum.DATA_NOT_EXIST, "未查询到规格库存信息");
            }
        }else{
            if(c09GoodsSkuDO.getNum()<b04ShoppingCart.getCommodityNumber()){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"库存不足");
            }else{
                int num = c09GoodsSkuDO.getNum()-b04ShoppingCart.getCommodityNumber(); //库存数
                c09GoodsSkuDO.setNum(num);
                c09GoodsSkuDao.updateById(c09GoodsSkuDO);
            }
        }

        //把库存规格对应的价格赋给购物车里的商品价格
        if(c09GoodsSkuDO!=null){
            b04ShoppingCart.setPrice(c09GoodsSkuDO.getGoodsPrice());
            b04ShoppingCart.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
        }else{
            b04ShoppingCart.setPrice(c01GoodsDO.getPrice());
            b04ShoppingCart.setTotalPrice(c01GoodsDO.getPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
        }

        //查询是否存在重复购物车记录存在则合并
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
            //以前购物车里有再加上现在又有一条数据
            int num=b04ShoppingCartDO.getCommodityNumber()+b04ShoppingCart.getCommodityNumber();
            b04ShoppingCartDO.setCommodityNumber(num);
            if(c09GoodsSkuDO !=null){
                b04ShoppingCartDO.setPrice(c09GoodsSkuDO.getGoodsPrice());
                b04ShoppingCartDO.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(num)));
            }else{
                b04ShoppingCartDO.setPrice(c01GoodsDO.getPrice());
                b04ShoppingCartDO.setTotalPrice(c01GoodsDO.getPrice().multiply(BigDecimal.valueOf(num)));
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
        //通过购物车的库存id去查库存
        C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(b04ShoppingCart.getSkuId());
        if(c09GoodsSkuDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到规格库存信息");
        }
        if(c09GoodsSkuDO.getNum()<b04ShoppingCart.getCommodityNumber()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"库存不足");
        }
        //库存足,允许修改,并且把对应的规格价格赋值给单价
        b04ShoppingCart.setPrice(c09GoodsSkuDO.getGoodsPrice());
        //改变总价
        b04ShoppingCart.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(b04ShoppingCart.getCommodityNumber())));
        b04ShoppingCart.setUpdateTime(LocalDateTime.now());
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


    //添加购物车商品
    @Override
    public int addCartGoods(B04ShoppingCartAddAndSubSaveForm form) {
        //通过购物车的id去查这个购物车的商品
        B04ShoppingCartDO b04ShoppingCartDO = b04ShoppingCartDao.get(form.getId());
        //在通过规格id去查对应的库存
        C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(b04ShoppingCartDO.getSkuId());
        if(c09GoodsSkuDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到规格库存信息");
        }
        if(c09GoodsSkuDO.getNum()<form.getCommodityNumber()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"库存不足");
        }
        //库存足,允许修改,并且把对应的规格价格赋值给单价
        b04ShoppingCartDO.setPrice(c09GoodsSkuDO.getGoodsPrice());
        //改变总价
        b04ShoppingCartDO.setTotalPrice(c09GoodsSkuDO.getGoodsPrice().multiply(BigDecimal.valueOf(form.getCommodityNumber())));
        b04ShoppingCartDO.setUpdateTime(LocalDateTime.now());
        //修改购物车的商品数量
        b04ShoppingCartDO.setCommodityNumber(form.getCommodityNumber());
        return b04ShoppingCartDao.updateById(b04ShoppingCartDO);

    }

}
