package com.youqiancheng.ability;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.client.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.dao.B07AuthenticationDao;
import com.youqiancheng.mybatis.dao.F02ShopPicDao;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F02ShopPicDO;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AuthenticationAbility {

    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private F02ShopPicDao f02ShopPicDao;
    /**
     * 修改实名认证
     * @param dto
     */
    public void updateAuthentication(F01ShopAppSaveForm dto){
        try {
            B07AuthenticationDO b07AuthenticationDO = new B07AuthenticationDO();
            b07AuthenticationDO.setUserId(dto.getUserId());
            b07AuthenticationDO.setName(dto.getContacts());
            b07AuthenticationDO.setStatus(1);
            b07AuthenticationDO.setDeleteFlag(1);
            b07AuthenticationDao.update(b07AuthenticationDO, new EntityWrapper<B07AuthenticationDO>().eq("user_id", dto.getUserId()));
        }catch (Exception e){
            log.info("实名认证修改失败......");
        }
    }

    /**
     * 保存照片信息
     * @param f01Shop
     * @param dto
     */
    public void  addShopPic(F01ShopDO f01Shop,F01ShopAppSaveForm dto){
        List<F02ShopPicDO> f02ShopPics=new ArrayList<>();
        //保存营业执照信息
        for (String str : dto.getLicenseList()) {
            F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
            f02ShopPicDO.setShopId(f01Shop.getId());
            f02ShopPicDO.setPicUrl(str);
            f02ShopPicDO.setType(StatusConstant.ShopPicType.license.getCode());
            //基础信息
            f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            f02ShopPicDO.setCreatePerson(dto.getUserName());
            f02ShopPicDO.setUpdatePerson(dto.getUserName());
            f02ShopPicDO.setCreateTime(LocalDateTime.now());
            f02ShopPicDO.setUpdateTime(LocalDateTime.now());

            f02ShopPics.add(f02ShopPicDO);
        }
        //身份证信息
        for (String str : dto.getIdCardList()) {
            F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
            f02ShopPicDO.setShopId(f01Shop.getId());
            f02ShopPicDO.setPicUrl(str);
            f02ShopPicDO.setType(StatusConstant.ShopPicType.id_card.getCode());
            //基础信息
            f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            f02ShopPicDO.setCreatePerson(dto.getUserName());
            f02ShopPicDO.setUpdatePerson(dto.getUserName());
            f02ShopPicDO.setCreateTime(LocalDateTime.now());
            f02ShopPicDO.setUpdateTime(LocalDateTime.now());

            f02ShopPics.add(f02ShopPicDO);
        }
        //其他信息
        for (String str : dto.getOtherInfoList()) {
            F02ShopPicDO f02ShopPicDO=new F02ShopPicDO();
            f02ShopPicDO.setShopId(f01Shop.getId());
            f02ShopPicDO.setPicUrl(str);
            f02ShopPicDO.setType(StatusConstant.ShopPicType.other.getCode());
            //基础信息
            f02ShopPicDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            f02ShopPicDO.setCreatePerson(dto.getUserName());
            f02ShopPicDO.setUpdatePerson(dto.getUserName());
            f02ShopPicDO.setCreateTime(LocalDateTime.now());
            f02ShopPicDO.setUpdateTime(LocalDateTime.now());

            f02ShopPics.add(f02ShopPicDO);
        }

        if(!CollectionUtils.isEmpty(f02ShopPics)){
            int i = f02ShopPicDao.insertBatch(f02ShopPics);
            if(i<=0){
                throw new JsonException(ResultEnum.INSERT_FAIL,"商家图片批量插入失败");
            }
        }
    }

}
