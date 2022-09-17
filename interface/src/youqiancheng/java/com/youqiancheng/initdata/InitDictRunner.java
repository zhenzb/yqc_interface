/**
 * Copyright (C), 2015-2019, 撼动科技有限公司
 * FileName: InitDictRunner
 * Author:   ytf
 * Date:     2019/7/8 18:33
 * Description: 初始化字典数据
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.initdata;

import com.youqiancheng.service.manager.service.InitDataService;
import com.youqiancheng.util.BadWordsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 功能：
 * 〈初始化字典数据〉
 *
 * @author ytf
 * @create 2019/7/8
 * @since 1.0.0
 */
@Component
public class InitDictRunner implements CommandLineRunner {

    @Autowired
    private InitDataService initDataService;


    @Override
    public void run(String... args) throws Exception {
       if( initDataService.initData()){
           System.out.println("初始化业务字典成功");
       }
       //初始化敏感词库
        BadWordsUtil.initbadWordsList();
        System.out.println("初始化敏感词库完成");

    }
}
