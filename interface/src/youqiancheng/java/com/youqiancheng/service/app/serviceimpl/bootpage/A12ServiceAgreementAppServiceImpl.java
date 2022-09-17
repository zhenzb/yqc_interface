package com.youqiancheng.service.app.serviceimpl.bootpage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A12ServiceAgreementDao;
import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;
import com.youqiancheng.service.app.service.bootpage.A12ServiceAgreementAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 10:37
 */
@Service
@Transactional
public class A12ServiceAgreementAppServiceImpl implements A12ServiceAgreementAppService {
    @Resource
    private A12ServiceAgreementDao a12ServiceAgreementDao;

    /**
    *
    * @author      nl
    * @return      获取启动页服务协议
    * @date        2020/4/3 10:45
    */
    @Override
    public A12ServiceAgreementDO getSlaInfo(){
        EntityWrapper<A12ServiceAgreementDO> ew=new EntityWrapper<A12ServiceAgreementDO>();
        List<A12ServiceAgreementDO> a12ServiceAgreementDOS=new ArrayList<>();
        ew.eq("delete_flag", StatusConstant.enable.getCode());
        a12ServiceAgreementDOS=a12ServiceAgreementDao.selectList(ew);
        A12ServiceAgreementDO a12ServiceAgreementDO=new A12ServiceAgreementDO();
        if(a12ServiceAgreementDOS!=null&&a12ServiceAgreementDOS.size()>0)
        {
            a12ServiceAgreementDO= a12ServiceAgreementDOS.get(0);
        }
        return a12ServiceAgreementDO;

    }

}
