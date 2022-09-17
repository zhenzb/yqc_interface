package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 实名认证查询打款银行信息响应list
 * @author  澄泓
 * @date  2020/11/16 16:30
 * @version JDK1.7
 */
public class Lists extends ArrayList {
    @Override
    public List get(int index) {
        Object o = super.get(index);
        List antPush = JSON.parseObject(o.toString(), List.class);
        return antPush;
    }
}
