package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.ability.SignacontractAbility;
import com.youqiancheng.form.client.CommentSaveForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.C10PublicityClientService;
import com.youqiancheng.util.BadWordsUtil;
import com.youqiancheng.vo.client.C05CommentClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class C10PublicityClientServiceImpl implements C10PublicityClientService {
    protected static final Log logger = LogFactory.getLog(C10PublicityClientServiceImpl.class);
    @Autowired
    private C10PublicityDao c10PublicityDao;
    @Autowired
    private C05CommentDao c05CommentDao;
    @Autowired
    private B01UserDao b01UserDao;

    @Autowired
    private C14ReplyCommentDao c14ReplyCommentDao;

    @Resource
    private C01GoodsDao goodsDao;

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
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
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

        List<String> badWordList = BadWordsUtil.searchBanWords(form.getContent());
        if (badWordList.size() != 0){
            logger.info("找到敏感词："+badWordList.get(0));
            throw new JsonException(ResultEnum.INSERT_FAIL,"评论中含有敏感词汇："+badWordList.get(0));
        }
        B01UserDO b01UserDO = b01UserDao.get(form.getUserId());
        if(b01UserDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户数据查询为空");
        }
        C10PublicityDO c10PublicityDO = c10PublicityDao.get(form.getGoodsId());
        C01GoodsDO c01GoodsDO = null;
        if(c10PublicityDO==null){
            c01GoodsDO = goodsDao.get(form.getGoodsId());
            if(c10PublicityDO ==null &&  c01GoodsDO == null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"宣传信息查询为空");
            }

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
        if(c10PublicityDO !=null){
            dto.setGoodsIcon(c10PublicityDO.getIcon());
            dto.setGoodsId(c10PublicityDO.getId());
            dto.setGoodsName(c10PublicityDO.getTitle());
        }else{
            dto.setGoodsIcon(c01GoodsDO.getIcon());
            dto.setGoodsId(c01GoodsDO.getId());
            dto.setGoodsName(c01GoodsDO.getIntroduction());
        }

        dto.setLevel(form.getLevel());
        return c05CommentDao.insert(dto);
    }



    @Override
    public List<C05CommentClientVO> allCommentAndReplyComment(Map<String, Object> map) {

        //通过宣传的goodsId获取对它的评论集合
        List<C05CommentClientVO> c05CommentDOS = c05CommentDao.allCommentAndReplyCommentHDPage(map);
        if(c05CommentDOS.isEmpty()){
              throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"没有相关数据");
        }
        for (C05CommentClientVO c05:c05CommentDOS
             ) {
            List<C14ReplyCommentDO> c14 = c14ReplyCommentDao.selectList
                    (new EntityWrapper<C14ReplyCommentDO>().eq("comment_id", c05.getId()));
            c05.setC14ReplyCommentDOS(c14);
            c05.setReplyComentNumber(c14.size());
        }
        return c05CommentDOS;
    }

 /*   @Override
    public List<C05CommentDO> allComment(Map<String, Object> map) {
        return c05CommentDao.listByGoodsIdHDPage(map);
    }*/
}
