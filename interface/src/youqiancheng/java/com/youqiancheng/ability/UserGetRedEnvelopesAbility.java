package com.youqiancheng.ability;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.app.ReceiveRecordSearchForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.vo.app.ReceiveRecordNumVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserGetRedEnvelopesAbility {

    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;
    @Autowired
    private B11InvitationRecordDao b11InvitationRecordDao;
    @Autowired
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;
    @Autowired
    private E05RedenvelopesReceiveRecordDao e05RedenvelopesReceiveRecordDao;


    /**
     * 查询计算用户是否具有可抢红包的参数，交个前端判断用户是否可抢红包
     * @param form
     * @return
     */
    public ReceiveRecordNumVO getReceiveRedPacketNum(ReceiveRecordSearchForm form) {

        synchronized(this) {
                ReceiveRecordNumVO vo = new ReceiveRecordNumVO();
                List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                        new EntityWrapper<D01OrderDO>()
                                //.eq("shop_id",form.getShopId())
                                .eq("user_id", form.getUserId())
                                .eq("order_status", 2)
//                        .ge("take_time", LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
//                        .lt("take_time ", LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                                .like("take_time", LocalDate.now().toString())
                );
                //查询总共可领取红包
                int totalNum = 0;
                vo.setIsFree(StatusConstant.IsFree.no.getCode());
                E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetDao.get(form.getStreetId());
                if (e01RedenvelopesStreetDO == null) {
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST, "未查询到有效街区");
                }
                if (StatusConstant.IsFree.yes.getCode() == e01RedenvelopesStreetDO.getIsFree()) {
                    totalNum = 1;
                    vo.setIsFree(StatusConstant.IsFree.yes.getCode());
//            if(e01RedenvelopesStreetDO.getStartTime()!=null&&e01RedenvelopesStreetDO.getEndTime()!=null){
//                int i = LocalDateTime.now().compareTo(e01RedenvelopesStreetDO.getStartTime());
//                int j = LocalDateTime.now().compareTo(e01RedenvelopesStreetDO.getEndTime());
//                if(i>=0&&j<=0){
//                    totalNum=1;
//                    vo.setIsFree(StatusConstant.IsFree.yes.getCode());
//
//                }
//            }else{
//                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"街区设置免费时间为空");
//            }
                } else {
                    if (d01OrderDOS != null && !d01OrderDOS.isEmpty()) {
                        for (D01OrderDO d01OrderDO : d01OrderDOS) {
                            totalNum += d01OrderDO.getOrderPrice().divideToIntegralValue(e01RedenvelopesStreetDO.getConsumeMoney()).intValue();
                            //判断是否有在定制街道购物订单 有的话 获得抢100元红街道包的权限
//                            if (0 != d01OrderDO.getStreetId()) {
//                                E01RedenvelopesStreetDO e01RedenvelopesStreetDO1 = e01RedenvelopesStreetDao.get(d01OrderDO.getStreetId());
//                                if (e01RedenvelopesStreetDO1.getName().contains("定制")) {
//                                    totalNum += 1;
//                                }
//                            }
                            //查询快条街抢红包的权限
                            if(null != d01OrderDO.getStreetId() && 0 != d01OrderDO.getStreetId()){
                                E01RedenvelopesStreetDO e01RedenvelopesStreetDO1 = e01RedenvelopesStreetDao.get(d01OrderDO.getStreetId());
                                if(e01RedenvelopesStreetDO1!=null){
                                    if (e01RedenvelopesStreetDO1.getName().contains("VIP")) {
                                        totalNum += 1;
                                    }
                                }

                            }
                        }
                    }
                    //查询邀请获得的抢100元红街道包的权限
                    Integer integer = b11InvitationRecordDao.selectCount(new EntityWrapper<B11InvitationRecordDO>().eq("user_id", form.getUserId())
                            .like("create_time", LocalDate.now().toString())
                    );
                    if (integer != 0 && 0 == 1000 - e01RedenvelopesStreetDO.getConsumeMoney().intValue()) {
                        totalNum += integer;
                    }


                }
                vo.setTotalNum(totalNum);
                //查询已经领取红包
                int receiveNum = 0;
                List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDOS =
                        e04RedenvelopesGrantRecordDao.selectList(
                                new EntityWrapper<E04RedenvelopesGrantRecordDO>()
                                        .eq("street_id ", form.getStreetId())
                                        .eq("shop_id", form.getShopId())
                                        .eq("end_flag", StatusConstant.EndFlag.un_end.getCode())
                        );
                if (e04RedenvelopesGrantRecordDOS == null || e04RedenvelopesGrantRecordDOS.isEmpty()) {
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST, "未查询到该街区下该商家的红包信息");
                }
                List<E05RedenvelopesReceiveRecordDO> e05RedenvelopesReceiveRecordDOS =
                        e05RedenvelopesReceiveRecordDao.selectList(
                                new EntityWrapper<E05RedenvelopesReceiveRecordDO>()
                                        .eq("grant_id", e04RedenvelopesGrantRecordDOS.get(0).getId())
                                        .eq("is_receive", StatusConstant.IsReceive.receive.getCode())
                                        .eq("user_id", form.getUserId())
                                        .like("update_time",LocalDate.now().toString())
                        );
                if (e05RedenvelopesReceiveRecordDOS != null && !e05RedenvelopesReceiveRecordDOS.isEmpty()) {
                    receiveNum = e05RedenvelopesReceiveRecordDOS.size();
                }
                vo.setReceiveNum(receiveNum);
                //待领取红包个数
                vo.setUnReceiveNum(vo.getTotalNum() - vo.getReceiveNum());

                return vo;
        }
    }
}
