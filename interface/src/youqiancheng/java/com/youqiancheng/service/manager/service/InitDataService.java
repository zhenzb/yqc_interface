package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A16SysDictDO;

import java.util.List;

/**
 * Author tyf
 * Date  2019-07-08
 */
public interface InitDataService {


     boolean initData();

      List<A16SysDictDO>   getSysDictByType(String type);

     boolean refreshData();


}
