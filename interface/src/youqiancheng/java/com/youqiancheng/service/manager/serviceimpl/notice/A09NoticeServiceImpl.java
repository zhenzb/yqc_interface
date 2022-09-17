package com.youqiancheng.service.manager.serviceimpl.notice;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.manager.notice.A09NoticeDeleteForm;
import com.youqiancheng.mybatis.dao.A09NoticeDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.manager.service.notice.A09NoticeService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class A09NoticeServiceImpl implements A09NoticeService {

    @Resource
    private A09NoticeDao a09NoticeDao;

    /**
    　* @Description: 获取公告列表
    　* @author shalongteng
    　* @date 2020/4/2 14:08
     * */
    @Override
    public List<A09NoticeDO> listNoticePage(Map<String, Object> map) {
        return a09NoticeDao.listNoticeHDPage(map);
    }

    @Override
    public A09NoticeDO getNoticeById(long id) {
        return a09NoticeDao.selectById(id);
    }

    /**
    　* @Description: 删除公告
    　* @author shalongteng
    　* @date 2020/4/2 16:28
    　*/
    @Override
    public boolean deleteNotice(A09NoticeDeleteForm a09NoticeDeleteForm) {
        A09NoticeDO a09NoticeDO = a09NoticeDao.selectById(a09NoticeDeleteForm.getId());
        a09NoticeDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        return a09NoticeDao.updateById(a09NoticeDO) == 1? true : false;
    }
    /**
     　* @Description: 批量删除
     　* @author yutf
     　* @date 2020/4/2 16:28
     　*/
    @Override
    public int deleteBatch(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return a09NoticeDao.updateStatus(param);
    }
    /**
     　* @Description: 批量删除
     　* @author yutf
     　* @date 2020/4/2 16:28
     　*/
    @Override
    public int save(A09NoticeDO a09NoticeDO) {
        return a09NoticeDao.insert(a09NoticeDO);
    }
    /**
     　* @Description: 批量删除
     　* @author yutf
     　* @date 2020/4/2 16:28
     　*/
    @Override
    public int update(A09NoticeDO a09NoticeDO) {
        return a09NoticeDao.updateById(a09NoticeDO);
    }

    /**
     * 功能描述: <br>
     * 〈发布〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    @Override
    public int release(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.ReleaseStatus.release.getCode());
        return a09NoticeDao.updateStatus(param);
    }

}
