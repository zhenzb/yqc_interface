package com.youqiancheng.service.manager.serviceimpl;

import com.youqiancheng.mybatis.dao.A16SysDictDao;
import com.youqiancheng.mybatis.model.A16SysDictDO;
import com.youqiancheng.service.manager.service.InitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2019-07-08
 */
@Service
@Transactional
public class InitDataServiceImpl implements InitDataService {
    public static Map<String,List> sysDictList = new HashMap<String, List>();
    @Autowired
    private A16SysDictDao sysDictDao;



    @Override
    public boolean initData() {
        //初始化业务字典
        List<A16SysDictDO> list = sysDictDao.list(new HashMap<>());
        if(list!=null && !list.isEmpty()){
            sysDictList.put("sysDict",list);
            return true;
        }
        return false;
    }

    @Override
    public List<A16SysDictDO>  getSysDictByType(String type) {
        List<A16SysDictDO> list=sysDictList.get("sysDict");
        return list.stream().filter(item->item.getType().equals(type)).collect(Collectors.toList());

    }

    @Override
    public boolean   refreshData() {
        //刷新业务字典
        List<A16SysDictDO> list = sysDictDao.list(new HashMap<>());
        if(list!=null && !list.isEmpty()){
            sysDictList.clear();
            sysDictList.put("sysDict",list);
            return true;
        }
        return false;
    }

}
