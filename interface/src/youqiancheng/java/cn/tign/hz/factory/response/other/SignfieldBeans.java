package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:03
 * @version 
 */
public class SignfieldBeans extends ArrayList {
    @Override
    public SignfieldBean get(int index) {
        Object o = super.get(index);
        SignfieldBean signfieldBean = JSON.parseObject(o.toString(), SignfieldBean.class);
        return signfieldBean;
    }
}
