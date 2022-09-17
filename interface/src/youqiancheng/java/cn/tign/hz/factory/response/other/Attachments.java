package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:15
 * @version 
 */
public class Attachments extends ArrayList {
    @Override
    public Attachment get(int index) {
        Object o = super.get(index);
        Attachment attachment = JSON.parseObject(o.toString(), Attachment.class);
        return attachment;
    }
}
