package com.youqiancheng.service.manager.serviceimpl.goodsmanagement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.youqiancheng.form.manager.Goods.GoodsDeleteForm;
import com.youqiancheng.form.manager.Goods.GoodsExamineSaveEditForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.util.BuildTree;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class GoodsListServiceImpl implements GoodsListService {
    @Resource
    private D04GoodsEvaluateDao d04GoodsEvaluateDao;
    @Resource
    private C01GoodsDao c01GoodsDao;
    @Resource
    private C02GoodsPicDao c02GoodsPicDao;
    @Resource
    private C03CategoryDao c03CategoryDao;
    @Resource
    private C05CommentDao c05CommentDao;
    @Resource
    private C04GoodsExamineSetDao c04GoodsExamineSetDao;
    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private A15MessageDao  a15MessageDao;
    @Resource
    private A17MessageUserDao  a17MessageUserDao;
    @Resource
    private F01ShopDao  f01ShopDao;
    public GoodsListServiceImpl() {
    }
    /**
    　* @Description: 分页查询列表
    　* @author shalongteng
    　* @date 2020/4/15 16:30
    　*/
    @Override
    public List<C01GoodsDO> listGoodsHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        List<C01GoodsDO> f01ShopDOList = c01GoodsDao.listGoodsHDPage(map);
        return f01ShopDOList;
    }
    /**
    　* @Description: 分页查询列表
    　* @author shalongteng
    　* @date 2020/4/15 16:30
    　*/
    @Override
    public List<C01GoodsDO> listHDPage(Map<String, Object> map) {
        List<C01GoodsDO> f01ShopDOList = c01GoodsDao.listHDPage(map);
        return f01ShopDOList;
    }
    /**
    　* @Description: 批量删除商品
    　* @author shalongteng
    　* @date 2020/4/16 10:05
    　*/
    @Override
    public Integer deleteGoods(List<GoodsDeleteForm> goodsDeleteFormList) {
        EntityWrapper<C01GoodsDO> ew = new EntityWrapper<>();
        ew.in("id",goodsDeleteFormList.stream().map(p->p.getId()).collect(Collectors.toList()));
        List<C01GoodsDO> c01GoodsDOList = c01GoodsDao.selectList(ew);
        if (c01GoodsDOList == null || c01GoodsDOList.size() == 0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        for (C01GoodsDO c01GoodsDO : c01GoodsDOList) {
            c01GoodsDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
            c01GoodsDO.setUpdateTime(LocalDateTime.now());
            c01GoodsDao.updateById(c01GoodsDO);
        }
        return 1;
    }
    /**
    　* @Description: 编辑商品回显接口
    　* @author shalongteng
    　* @date 2020/4/16 10:41
    　*/
    @Override
    public C01GoodsDO getEditGoods(Long id) {
        C01GoodsDO c01GoodsDO = c01GoodsDao.get(id);
        if(c01GoodsDO == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
        }
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("goodsId",id);
        List<C02GoodsPicDO> list = c02GoodsPicDao.list(map);
        List<String> collect = list.stream().map(C02GoodsPicDO::getPicUrl).collect(Collectors.toList());
        //添加商品图
        c01GoodsDO.setPicList(collect);
        //
        QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map1.put("goodsId",id);
        List<C09GoodsSkuDO> c09GoodsSkuDOS = c09GoodsSkuDao.list(map1);
        c01GoodsDO.setSkuList(c09GoodsSkuDOS);

        C03CategoryDO c03CategoryDO1 = c03CategoryDao.selectById(c01GoodsDO.getCategoryId());
        if(c03CategoryDO1!=null){
            c01GoodsDO.setCategoryIdName(c03CategoryDO1.getName());
        }
        C03CategoryDO c03CategoryDO2 = c03CategoryDao.selectById(c01GoodsDO.getSecondCategoryId());
        if(c03CategoryDO2!=null){
            c01GoodsDO.setSecondCategoryIdName(c03CategoryDO2.getName());
        }
        C03CategoryDO c03CategoryDO3 = c03CategoryDao.selectById(c01GoodsDO.getThirdCategoryId());
        if(c03CategoryDO3!=null){
            c01GoodsDO.setThirdCategoryIdName(c03CategoryDO3.getName());
        }
        return c01GoodsDO;
    }
    /**
    　* @Description: 批量通过拒绝
    　* @author shalongteng
    　* @date 2020/4/16 11:12
    　*/
    @Override
    public Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            C01GoodsDO c01GoodsDO = c01GoodsDao.selectById(shopPassRefuseForm.getId());
            if(c01GoodsDO == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            c01GoodsDO.setExamineStatus(shopPassRefuseForm.getStatus());
            c01GoodsDO.setReason(shopPassRefuseForm.getReason());
            c01GoodsDO.setUpdateTime(LocalDateTime.now());
            if(c01GoodsDO.getExamineStatus()==StatusConstant.ExamineStatus.adopt.getCode()){
                pushPassMessage(c01GoodsDO);
            }else{
                pushRefuseMessage(c01GoodsDO);
            }
            c01GoodsDao.updateById(c01GoodsDO);

        }
        return 1;
    }

    private void pushPassMessage(C01GoodsDO c01GoodsDO){
        F01ShopDO f01ShopDO = f01ShopDao.get(c01GoodsDO.getShopId());
        //根据商家订单创建消息体
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("商品审核通过");
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        messageDo.setUpdatePerson("admin");
        messageDo.setCreatePerson("admin");
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("审核提醒");
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息插入失败");
        }
        //查找消息接收人
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        //发送消息
        A17MessageUserDO dto=new A17MessageUserDO();
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
        dto.setType(TypeConstant.MessageReadType.business.getCode());
        dto.setMessageId(messageDo.getId());
        dto.setUserId(f01ShopDO.getUserId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息发送插入失败");
        }
        //消息推送——推送至商家对应申请入驻的用户
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家对应的申请用户不存在");
        }
        try {
            String alias = "yqc_" + b01UserDO1.getId();
            String aliastype = "yqc_type";
            String content = messageDo.getContent();
            String title = messageDo.getTitle();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, title,"", content, "1","1",null,null,null);  // IOS 单推
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, title, "", content, "1","1",null,null,null); // android 单推
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息推送失败"+e.getMessage());
        }
    }
    private void pushRefuseMessage(C01GoodsDO c01GoodsDO){
        F01ShopDO f01ShopDO = f01ShopDao.get(c01GoodsDO.getShopId());
        //根据商家订单创建消息体
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("商家审核拒绝，拒绝原因"+f01ShopDO.getReason());
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        messageDo.setUpdatePerson("admin");
        messageDo.setCreatePerson("admin");
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("审核提醒");
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息插入失败");
        }
        //查找消息接收人
        //发送消息
        A17MessageUserDO dto=new A17MessageUserDO();
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
        dto.setType(TypeConstant.MessageReadType.business.getCode());
        dto.setMessageId(messageDo.getId());
        dto.setUserId(f01ShopDO.getUserId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息发送插入失败");
        }
        //消息推送——推送至商家对应申请入驻的用户
        B01UserDO b01UserDO1 = b01UserDao.get(f01ShopDO.getUserId());
        if(b01UserDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家对应的申请用户不存在");
        }
        try {
            String alias = "yqc_" + b01UserDO1.getId();
            String aliastype = "yqc_type";
            String content = messageDo.getContent();
            String title = messageDo.getTitle();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, title,title, content, "1","1",null,null,null);  // IOS 单推
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, title, title, content, "1","1",null,null,null); // android 单推
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息推送失败"+e.getMessage());
        }
    }

    /**
    　* @Description: 批量上架下架
    　* @author shalongteng
    　* @date 2020/4/16 11:33
    　*/
    @Override
    public Integer batchUpDown(List<ShopPassRefuseForm> shopPassRefuseFormList) {
        for (ShopPassRefuseForm shopPassRefuseForm : shopPassRefuseFormList) {
            C01GoodsDO c01GoodsDO = c01GoodsDao.selectById(shopPassRefuseForm.getId());
            if(c01GoodsDO == null){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
            if(shopPassRefuseForm.getIsSale() == StatusConstant.SaleFlag.on_sale.getCode() ||
                    shopPassRefuseForm.getIsSale() == StatusConstant.SaleFlag.off_sale.getCode()){
                c01GoodsDO.setIsSale(shopPassRefuseForm.getIsSale());
                c01GoodsDO.setUpdateTime(LocalDateTime.now());
                c01GoodsDao.updateById(c01GoodsDO);
            }else{
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }

        }
        return 1;
    }
    /**
     * _ooOoo_
     * o8888888o
     * 88" . "88
     * (| -_- |)
     *  O\ = /O
     * ___/`---'\____
     * .   ' \\| |// `.
     * / \\||| : |||// \
     * / _||||| -:- |||||- \
     * | | \\\ - /// | |
     * | \_| ''\---/'' | |
     * \ .-\__ `-` ___/-. /
     * ___`. .' /--.--\ `. . __
     * ."" '< `.___\_<|>_/___.' >'"".
     * | | : `- \`.;`\ _ /`;.`/ - ` : | |
     * \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     * `=---='
     *          .............................................
     *           佛曰：bug泛滥，我已瘫痪！
     */

    @Override
    public List<C03CategoryDO> getC03CategoryDOList() {
        List<C03CategoryDO> c03CategoryDOList = c03CategoryDao.selectList(null);
        return null;
    }

    @Override
    public Tree<C03CategoryDO> getTree() {
        List<Tree<C03CategoryDO>> trees = new ArrayList<Tree<C03CategoryDO>>();
        List<C03CategoryDO> c03CategoryDOList = c03CategoryDao.selectList(null);
        for (C03CategoryDO categoryDO : c03CategoryDOList) {
            Tree<C03CategoryDO> tree = new Tree<>();
            tree.setId(categoryDO.getId()+"");
            tree.setParentId(categoryDO.getParentId()+"");
            tree.setText(categoryDO.getName());
            tree.setOrderNumber(categoryDO.getOrderNum());
            tree.setObject(categoryDO);

            trees.add(tree);
        }
        // 默认顶级菜单为0，根据数据库实际情况调整
        Tree<C03CategoryDO> t =new  BuildTree().build(trees);
        return t;
    }
    /**
    　* @Description: 新增商品分类
    　* @author shalongteng
    　* @date 2020/4/16 15:02
    　*/
    @Override
    public boolean insertC03CategoryDO(C03CategoryDO c03CategoryDO) {
        // 查询是否存在
        C03CategoryDO byName = c03CategoryDao.findC03CategoryDOByName(c03CategoryDO.getName());
        if (byName != null) {
            throw new JsonException(ResultEnum.DATA_REPEAT, "当前商品分类已存在");
        }

        return c03CategoryDao.insert(c03CategoryDO) == 1? true :false;
    }
    /**
    　* @Description: 修改商品分类
    　* @author shalongteng
    　* @date 2020/4/16 15:12
    　*/
    @Override
    public boolean updateC03CategoryDO(C03CategoryDO c03CategoryDO) {
        EntityWrapper<C03CategoryDO> ew = new EntityWrapper<>();
        ew.eq("id",c03CategoryDO.getId());
        return c03CategoryDao.update(c03CategoryDO,ew) == 1? true : false;
    }
    /**
    　* @Description: 删除商品分类
    　* @author shalongteng
    　* @date 2020/4/16 15:18
    　*/
    @Override
    public boolean deleteById(Integer id) {
        if(id == 0){
            return false;
        }
        EntityWrapper<C03CategoryDO> ew1 = new EntityWrapper<>();
        ew1.eq("parent_id",id);
        List<C03CategoryDO> c03CategoryDOList = c03CategoryDao.selectList(ew1);
        if(!CollectionUtils.isEmpty(c03CategoryDOList)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"存在子分类无法删除");
        }
        EntityWrapper<C03CategoryDO> ew = new EntityWrapper<>();
        ew.eq("id",id);
        c03CategoryDao.delete(ew);

        //菜单要级联删除
        //deleteByPid(id);

        return true;
    }



    //级联删除 菜单
    private void deleteByPid(Integer id) {
        EntityWrapper<C03CategoryDO> ew = new EntityWrapper<>();
        ew.eq("parent_id",id);
        List<C03CategoryDO> c03CategoryDOList = c03CategoryDao.selectList(ew);
        if(null == c03CategoryDOList || c03CategoryDOList.size() == 0){
            return;
        }else {
            for (C03CategoryDO c03CategoryDO : c03CategoryDOList) {
                EntityWrapper<C03CategoryDO> ew2 = new EntityWrapper<>();
                ew2.eq("id",c03CategoryDO.getId());
                c03CategoryDao.delete(ew2);
                deleteByPid(c03CategoryDO.getId());//递归删除 子菜单
            }
        }

    }

    /**
     * _ooOoo_
     * o8888888o
     * 88" . "88
     * (| -_- |)
     *  O\ = /O
     * ___/`---'\____
     * .   ' \\| |// `.
     * / \\||| : |||// \
     * / _||||| -:- |||||- \
     * | | \\\ - /// | |
     * | \_| ''\---/'' | |
     * \ .-\__ `-` ___/-. /
     * ___`. .' /--.--\ `. . __
     * ."" '< `.___\_<|>_/___.' >'"".
     * | | : `- \`.;`\ _ /`;.`/ - ` : | |
     * \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     * `=---='
     *          .............................................
     *           佛曰：bug泛滥，我已瘫痪！
     */

    /**
    　* @Description: 分页获取评论列表
    　* @author shalongteng
    　* @date 2020/4/16 15:34
    　*/
    @Override
    public List<D04GoodsEvaluateDO> listCommentHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        List<D04GoodsEvaluateDO> c05CommentDOList = d04GoodsEvaluateDao.listCommentHDPage(map);
        return c05CommentDOList;
    }
    /**
    　* @Description: 批量删除评论
    　* @author shalongteng
    　* @date 2020/4/16 15:38
    　*/
    @Override
    public Integer deleteComment(List<GoodsDeleteForm> goodsDeleteFormList) {
        List<C05CommentDO> c05CommentDOS = new ArrayList<>();
        EntityWrapper<D04GoodsEvaluateDO> ew = new EntityWrapper<>();
        ew.in("id",goodsDeleteFormList.stream().map(p->p.getId()).collect(Collectors.toList()));
        List<D04GoodsEvaluateDO> c05CommentDOList = d04GoodsEvaluateDao.selectList(ew);
        if (c05CommentDOList == null || c05CommentDOList.size() == 0){
            EntityWrapper<C05CommentDO> ewr = new EntityWrapper<>();
            ewr.in("id",goodsDeleteFormList.stream().map(p->p.getId()).collect(Collectors.toList()));
            c05CommentDOS = c05CommentDao.selectList(ewr);
            if(c05CommentDOS == null || c05CommentDOS.size() == 0){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            }
        }
        for (D04GoodsEvaluateDO c05CommentDO : c05CommentDOList) {
            c05CommentDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
            c05CommentDO.setUpdateTime(LocalDateTime.now());
            d04GoodsEvaluateDao.updateById(c05CommentDO);
        }
        for (C05CommentDO c05CommentDOs : c05CommentDOS) {
            c05CommentDOs.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
            c05CommentDOs.setUpdateTime(LocalDateTime.now());
            c05CommentDao.updateById(c05CommentDOs);
        }
        return 1;
    }
    /**
     * _ooOoo_
     * o8888888o
     * 88" . "88
     * (| -_- |)
     *  O\ = /O
     * ___/`---'\____
     * .   ' \\| |// `.
     * / \\||| : |||// \
     * / _||||| -:- |||||- \
     * | | \\\ - /// | |
     * | \_| ''\---/'' | |
     * \ .-\__ `-` ___/-. /
     * ___`. .' /--.--\ `. . __
     * ."" '< `.___\_<|>_/___.' >'"".
     * | | : `- \`.;`\ _ /`;.`/ - ` : | |
     * \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     * `=---='
     *          .............................................
     *           佛曰：bug泛滥，我已瘫痪！
     */
    /**
    　* @Description: 添加/修改审核设置
    　* @author shalongteng
    　* @date 2020/4/16 15:50
    　*/
    @Override
    public void addOrEditAudit(GoodsExamineSaveEditForm examineSaveEditForm) {
        c04GoodsExamineSetDao.delete(null);
        C04GoodsExamineSetDO c04GoodsExamineSetDO = new C04GoodsExamineSetDO();
        BeanUtils.copyProperties(examineSaveEditForm,c04GoodsExamineSetDO);

        c04GoodsExamineSetDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        c04GoodsExamineSetDao.insert(c04GoodsExamineSetDO);
    }
    /**
    　* @Description: 查询审核设置
    　* @author shalongteng
    　* @date 2020/4/16 15:52
    　*/
    @Override
    public C04GoodsExamineSetDO getAudit() {
        List<C04GoodsExamineSetDO> c04GoodsExamineSetDOList = c04GoodsExamineSetDao.selectList(null);
        if(c04GoodsExamineSetDOList== null || c04GoodsExamineSetDOList.size() == 0){
            return null;
        }
        return c04GoodsExamineSetDOList.get(0);
    }
    /**
    　* @Description: ${todo}
    　* @author shalongteng
    　* @date 2020/4/17 9:44
    　*/
    @Override
    public boolean forbiddenById(Integer id) {
        if(id == 0){
            return false;
        }
        C03CategoryDO c03CategoryDO = c03CategoryDao.selectById(id);
        if(c03CategoryDao == null){
            return false;
        }
        c03CategoryDO.setStatus(StatusConstant.disable.getCode());
        c03CategoryDO.setUpdateTime(LocalDateTime.now());
        c03CategoryDao.updateById(c03CategoryDO);
        //菜单要级联禁用
        forbiddenByPid(id);

        return true;
    }

    @Override
    public boolean start(Integer id) {
        if(id == 0){
            return false;
        }
        C03CategoryDO c03CategoryDO = c03CategoryDao.selectById(id);
        if(c03CategoryDao == null){
            return false;
        }
        c03CategoryDO.setStatus(StatusConstant.enable.getCode());
        c03CategoryDO.setUpdateTime(LocalDateTime.now());
        c03CategoryDao.updateById(c03CategoryDO);
        //菜单要级联禁用
//        forbiddenByPid(id);

        return true;
    }

    //级联
    private void forbiddenByPid(Integer id) {
        EntityWrapper<C03CategoryDO> ew = new EntityWrapper<>();
        ew.eq("parent_id",id);
        List<C03CategoryDO> c03CategoryDOList = c03CategoryDao.selectList(ew);
        if(null == c03CategoryDOList || c03CategoryDOList.size() == 0){
            return;
        }else {
            for (C03CategoryDO c03CategoryDO : c03CategoryDOList) {
                c03CategoryDO.setStatus(StatusConstant.disable.getCode());
                c03CategoryDao.updateById(c03CategoryDO);
                forbiddenByPid(c03CategoryDO.getId());//递归
            }
        }

    }
}
