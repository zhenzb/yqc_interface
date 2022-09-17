package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.app.CommentSaveForm;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.C02GoodsPicDao;
import com.youqiancheng.mybatis.dao.C05CommentDao;
import com.youqiancheng.mybatis.dao.C10PublicityDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.service.app.service.C10PublicityAppService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Service
@Transactional
public class C10PublicityAppServiceImpl implements C10PublicityAppService {
    @Autowired
    private C10PublicityDao c10PublicityDao;
    @Autowired
    private C02GoodsPicDao c02GoodsPicDao;
    @Autowired
    private C05CommentDao  c05CommentDao;
    @Autowired
    private B01UserDao b01UserDao;

    @Override
    public C10PublicityDO get(Long id){
        C10PublicityDO c10PublicityDO = c10PublicityDao.get(id);
        if(c10PublicityDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到宣传详情信息");
        }
        c10PublicityDO.setBrowseVolume(c10PublicityDO.getBrowseVolume()+1);
        c10PublicityDao.updateById(c10PublicityDO);
        return c10PublicityDO;
    }



    @Override
    public List<C10PublicityDO> listHDPage(Map<String, Object> map) {
        return c10PublicityDao.listHDPage(map);
    }

    @Override
    public List<C10PublicityDO> list(Map<String, Object> map) {
        return c10PublicityDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return c10PublicityDao.count(map);
    }


    @Override
    public int insert(C10PublicityDO c10Publicity) {
        return c10PublicityDao.insert(c10Publicity);
    }


    @Override
    public int insertBatch(List<C10PublicityDO> c10Publicitys){
        return c10PublicityDao.insertBatch(c10Publicitys);
    }


    @Override
    public int update(C10PublicityDO c10Publicity) {
        c10Publicity.setUpdateTime(LocalDateTime.now());
        return c10PublicityDao.updateById(c10Publicity);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return c10PublicityDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return c10PublicityDao.updateStatus(param);
        }

    @Override
    public int comment(CommentSaveForm form) {
        B01UserDO b01UserDO = b01UserDao.get(form.getUserId());
        if(b01UserDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户数据查询为空");
        }
        C10PublicityDO c10PublicityDO = c10PublicityDao.get(form.getGoodsId());
        if(c10PublicityDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"宣传信息查询为空");
        }
        C05CommentDO dto=new C05CommentDO();
        dto.setContent(form.getContent());
        dto.setCreatePerson(b01UserDO.getMobile());
        dto.setCreateTime(LocalDateTime.now());
        dto.setUpdatePerson(b01UserDO.getMobile());
        dto.setUpdateTime(LocalDateTime.now());
        dto.setUserId(b01UserDO.getId());
        dto.setUserName(b01UserDO.getNick());
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setGoodsIcon(c10PublicityDO.getIcon());
        dto.setGoodsId(c10PublicityDO.getId());
        dto.setGoodsName(c10PublicityDO.getTitle());
        dto.setLevel(form.getLevel());
        return c05CommentDao.insert(dto);
    }

    @Override
    public List<C05CommentDO> allComment(Map<String, Object> map) {
        List<C05CommentDO> c05CommentDOS = c05CommentDao.listByGoodsIdHDPage(map);
        return c05CommentDOS;
    }

    @Override
    public int PutOrOff(Long id) {
        C10PublicityDO c10PublicityDO = c10PublicityDao.get(id);
        if(c10PublicityDO.getDeleteFlag() !=2){
            if(c10PublicityDO.getIsSale() == 2){
                c10PublicityDO.setIsSale(1);
            }else{
                c10PublicityDO.setIsSale(2);
            }
            return c10PublicityDao.updateById(c10PublicityDO);
        }else{
           return  0;
        }
    }

    @Override
    public int delPublicity(Long id) {
        C10PublicityDO c10PublicityDO = c10PublicityDao.get(id);
        if (c10PublicityDO != null) {
            //删除音频,废弃了
            //  int i = shopC13PublicityAutio.delete(publicity.getId());
            c10PublicityDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
            return c10PublicityDao.updateById(c10PublicityDO);
        }else{
            return 0;
        }
    }


}
