package com.handongkeji.config.mybatis;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.model.A01Admin;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * Mybatis plus 自动填充公共字段 处理类
 * 　@Description: Mybatis plus MetaObjectHandler 配置
 * 　@author shalongteng
 * 　@date 2020/4/1 9:31
 */
@Component
public class MybatisMetaObjectHandler extends MetaObjectHandler {
    /**
     * 在数据库插入时设置createPerson（创建人），createTime（创建时间），updateTime（最后修改时间）
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTimeValue = getFieldValByName("createTime", metaObject);
        //自动填充创建时间
        if (createTimeValue == null) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }

        A01Admin currUser = SecurityUtils.getCurrentUser();
        //自动填充 创建人
        if (currUser != null) {
            Object createPersonByValue = getFieldValByName("createPerson", metaObject);
            if (createPersonByValue == null) {
                setFieldValByName("createPerson", currUser.getUserName(), metaObject);
            }
        }
        //自动填充 未删除
        Object deleteFlag = getFieldValByName("deleteFlag", metaObject);
        if (deleteFlag == null) {
            setFieldValByName("deleteFlag", StatusConstant.DeleteFlag.un_delete.getCode(), metaObject);
        }
        //自动填充 启用停用
        Object status = getFieldValByName("status", metaObject);
        if (deleteFlag == null) {
            setFieldValByName("status", StatusConstant.enable.getCode(), metaObject);
        }
    }

    /**
     * 在数据库更新时设置updatePerson（最后修改人），updateTime（最后修改时间）
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            if (metaObject.hasSetter("updateTime")) {
                setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        }
        Object updatePerson = getFieldValByName("updatePerson", metaObject);
        if(updatePerson == null){
            A01Admin currUser = SecurityUtils.getCurrentUser();
            if (currUser != null) {
                if (metaObject.hasSetter("updatePerson")) {
                    setFieldValByName("updatePerson", currUser.getUserName(), metaObject);
                }
            }
        }
    }
}
