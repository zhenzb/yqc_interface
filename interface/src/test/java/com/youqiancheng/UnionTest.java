//package com.youqiancheng;

//import com.Application;
//import com.youqiancheng.controller.client.ClientLoginController;
//import com.youqiancheng.mybatis.dao.F16ShopProfitFlowsDao;
//import com.youqiancheng.vo.result.ResultVo;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;


//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class UnionTest {

//    @Resource
//    F16ShopProfitFlowsDao f16ShopProfitFlowsDao;
//
//    @Resource
//    ClientLoginController clientLoginController;
//    @Test
//    public void testMappetUnion(){
//        Map<String,Object> map = new HashMap<>();
//        List shopList = Arrays.asList("187","226","134","182");
//        map.put("delFlag",1);
//        map.put("shopIds",shopList);
//        map.put("person","admin");
//
//        int i = f16ShopProfitFlowsDao.unionTest(map);
//        System.out.println("result: "+i);
//    }
//
//    @Test
//    public void testLogin(){
//        String PhoneNumbers = "13520289150";
//        String code = "4917";
//        ResultVo resultVo = clientLoginController.loginCodeVerifySms(PhoneNumbers, code);
//        System.out.println(resultVo);
//    }


//}
