package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:59
 * @version 
 */
public class AntPushList extends ArrayList {
    @Override
    public AntPush get(int index) {
        Object o = super.get(index);
        AntPush antPush = JSON.parseObject(o.toString(), AntPush.class);
        return antPush;
    }
}
