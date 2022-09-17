package com.youqiancheng.ability;

import com.handongkeji.util.EntyPage;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.vo.PageSimpleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShopAbility
 * @Description 店铺相关业务逻辑处理类
 * @Author zzb
 * @Date 2022/4/23 15:41
 * @Version 1.0
 **/
@Component
public class ShopAbility {

    @Resource
    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordClientService;
    @Resource
    private F01ShopClientService f01ShopClientService;
    @Resource
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;

    public PageSimpleVO<E04RedenvelopesGrantRecordDO> getE04RedenvelopesGrantRecordDOPageSimpleVO(@Valid EntyPage page, Map<String, Object> map) {
        List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDOS = e04RedenvelopesGrantRecordClientService.listHDPage(map);
        PageSimpleVO<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDO = new PageSimpleVO<>();
        e04RedenvelopesGrantRecordDO.setTotalNumber(page.getTotalNumber());
        e04RedenvelopesGrantRecordDO.setCurrentPage(page.getCurrentPage());
        //查询发放红包总金额
        BigDecimal bigDecimal = e04RedenvelopesGrantRecordClientService.TotalAmtHDPage(map);
        //封装到分页
        e04RedenvelopesGrantRecordDOS.stream().forEach(item ->{
            F01ShopDO f01ShopDO = f01ShopClientService.get(item.getShopId());
            item.setShopName(f01ShopDO.getName());
            E01RedenvelopesStreetDO e01RedenvelopesStreetDO1 = e01RedenvelopesStreetDao.get(item.getStreetId());
            if(e01RedenvelopesStreetDO1 !=null){
                item.setStreetName(e01RedenvelopesStreetDO1.getName());
            }
        });
        e04RedenvelopesGrantRecordDO.setList(e04RedenvelopesGrantRecordDOS);
        Map<String,Object> totalAmtMap = new HashMap<>();
        totalAmtMap.put("totalAmt",bigDecimal);
        e04RedenvelopesGrantRecordDO.setMap(totalAmtMap);
        return e04RedenvelopesGrantRecordDO;
    }
}
