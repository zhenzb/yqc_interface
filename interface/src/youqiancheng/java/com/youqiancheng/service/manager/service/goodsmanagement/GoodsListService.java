package com.youqiancheng.service.manager.service.goodsmanagement;


import com.youqiancheng.form.manager.Goods.GoodsDeleteForm;
import com.youqiancheng.form.manager.Goods.GoodsExamineSaveEditForm;
import com.youqiancheng.form.manager.shop.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.util.Tree;

import java.util.List;
import java.util.Map;

public interface GoodsListService {


    List<C01GoodsDO> listGoodsHDPage(Map<String, Object> map);
    List<C01GoodsDO> listHDPage(Map<String, Object> map);

    Integer deleteGoods(List<GoodsDeleteForm> goodsDeleteFormList);

    C01GoodsDO getEditGoods(Long id);

    Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList);

    Integer batchUpDown(List<ShopPassRefuseForm> shopPassRefuseFormList);

    List<C03CategoryDO> getC03CategoryDOList();

    Tree<C03CategoryDO> getTree();

    boolean insertC03CategoryDO(C03CategoryDO c03CategoryDO);

    boolean updateC03CategoryDO(C03CategoryDO c03CategoryDO);

    boolean deleteById(Integer id);

    List<D04GoodsEvaluateDO> listCommentHDPage(Map<String, Object> map);

    Integer deleteComment(List<GoodsDeleteForm> goodsDeleteFormList);

    void addOrEditAudit(GoodsExamineSaveEditForm examineSaveEditForm);

    C04GoodsExamineSetDO getAudit();

    boolean forbiddenById(Integer id);

    boolean start(Integer id);
}
