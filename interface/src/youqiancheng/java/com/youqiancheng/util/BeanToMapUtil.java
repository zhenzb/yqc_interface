/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: BeanToMapUtil
 * Author:   ytf
 * Date:     2020/4/1 16:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.util;

import com.youqiancheng.form.manager.AuthAdmin.A01AdminEditForm;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/4/1
 * @since 1.0.0
 */
public class BeanToMapUtil {
    //BeanUtils.describe(person1)

        /**
         *          *
         *
         * Map转换层Bean，使用泛型免去了类型转换的麻烦。
         * String null 转换List 报异常
         * @param <T>
         * @param map
         * @param class1
         * @return
         */
        public static <T> T map2Bean(Map<String, String> map, Class<T> class1) {
                T bean = null;
                try {
                bean = class1.newInstance();
                BeanUtils.populate(bean, map);
                } catch (InstantiationException e) {
                e.printStackTrace();
                } catch (IllegalAccessException e) {
                e.printStackTrace();
                } catch (InvocationTargetException e) {
                e.printStackTrace();
                }
                return bean;
        }


        public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
                A01AdminEditForm form =new A01AdminEditForm();
                form.setRealName("1232132");
                Map describe = BeanUtils.describe(form);
                System.out.println(describe.get("realName"));
                A01AdminEditForm o = map2Bean(describe,  A01AdminEditForm.class);
                System.out.println(o.getRealName());

        }
}
