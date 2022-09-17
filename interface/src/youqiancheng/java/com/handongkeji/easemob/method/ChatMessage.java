/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ChatMessage
 * Author:   Mr.Chen
 * Date:     2019/3/19 10:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.handongkeji.easemob.method;

import com.handongkeji.easemob.api.impl.EasemobChatMessage;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Mr.Chen
 * @create 2019/3/19
 * @since 1.0.0
 */
public class ChatMessage {
    private EasemobChatMessage easemobChatMessage=new EasemobChatMessage();
    public void  chatMessage(){
//        //方法 一
//       Long a= System.currentTimeMillis();
//        //方法 二
//        Long b= Calendar.getInstance().getTimeInMillis();
//        //方法 三
//        Long c=new Date().getTime();
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
       Object o= easemobChatMessage.exportChatMessages(100L,"dasda","ql=select * where timestamp>1553077740211");
        System.out.println(o.toString());
    }

}
