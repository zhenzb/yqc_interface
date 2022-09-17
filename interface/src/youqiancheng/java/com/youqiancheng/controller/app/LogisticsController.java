package com.youqiancheng.controller.app;


import com.alibaba.fastjson.JSONObject;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.service.app.service.D01OrderAppService;
import com.youqiancheng.util.KdniaoTrackQueryAPI;
import com.youqiancheng.util.TianXingJianAPI;
import com.youqiancheng.vo.app.D01OrderVO;
import com.youqiancheng.vo.app.TracesInfoVO;
import com.youqiancheng.vo.app.TracesVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = {"物流"})
@RestController
@RequestMapping(value = "logistics")
public class LogisticsController {

    @Value("${logistics.EBusinessID}")
    private String EBusinessID;

    @Value("${logistics.AppKey}")
    private String AppKey;

    @Value("${logistics.ReqURL}")
    private String ReqURL;

    @Autowired
    private D01OrderAppService d01OrderAppService;

    Map<String,String> orderTraceMap = new HashMap<>();

    @ApiOperation(value = "物流即时查询,根据订单id去查订单的物流信息,参数订单----id")
    @PostMapping("/getOrderTraces")
    public ResultVo getBrowseVolume(Long id) {//前端要传订单编号或者订单id,通过编号去查该订单的物流运单号和物流公司编码

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "订单号不能是空的");
        }

        //得到订单对象
        D01OrderVO d01OrderVO = d01OrderAppService.get(id);
        try {
            String result = "";
            //要json数据
            JSONObject jsonObject = new JSONObject();
            //快递公司的编码要从数据库查出来；
            jsonObject.put("ShipperCode", d01OrderVO.getExpressCode());
            jsonObject.put("CustomerName", d01OrderVO.getExpressName());
            //快递公司的运单号要从数据库查出来；
            jsonObject.put("LogisticCode", d01OrderVO.getExpressNumber());
            //判断调用快递鸟还是天行健 （快递鸟：限500次/天;（即时查询 支持四家 申通、圆通、百世、极兔）;有效期半年;）
            if (!"YTO".equals(d01OrderVO.getExpressCode()) && !"STO".equals(d01OrderVO.getExpressCode())
                    && !"HTKY".equals(d01OrderVO.getExpressCode()) && !"JTSD".equals(d01OrderVO.getExpressCode())) {
                TracesVO tracesVO = getTianXinglogisticsTracesVO(d01OrderVO);
                return ResultVOUtils.success(tracesVO);
            } else {
                TracesVO tracesVO = getKuaiDiNiaoTracesVO(d01OrderVO, jsonObject);
                return ResultVOUtils.success(tracesVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK, "调用物流失败");
        }
    }

    private TracesVO getKuaiDiNiaoTracesVO(D01OrderVO d01OrderVO, JSONObject jsonObject) throws Exception {
        String result;
        String s = orderTraceMap.get(d01OrderVO.getExpressNumber());
        if(s!=null){
            result = s;
            log.info("K获取到物流Map"+s);
        }else{
            jsonObject.put("EBusinessID", EBusinessID);
            jsonObject.put("AppKey", AppKey);
            jsonObject.put("ReqURL", ReqURL);
            KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
            result = kdniaoTrackQueryAPI.getOrderTracesByJson(jsonObject);
            if(result !=null){
                orderTraceMap.put(d01OrderVO.getExpressNumber(), result);
                log.info("K获取到物流添加Map");
            }
            log.info("K获取到物流"+result);
        }
        TracesVO tracesVO = JSONObject.parseObject(result, TracesVO.class);
        tracesVO.setExpressName(d01OrderVO.getExpressName());
        List<TracesInfoVO> traces = tracesVO.getTraces();
        List<TracesInfoVO> nList = new ArrayList<>();
        if (traces.size() > 0) {
            for (int i = traces.size() - 1; i >= 0; i--) {
                nList.add(traces.get(i));
            }
        }
        tracesVO.setTraces(nList);
        return tracesVO;
    }

    private TracesVO getTianXinglogisticsTracesVO(D01OrderVO d01OrderVO) {
        String result;
        String s = orderTraceMap.get(d01OrderVO.getExpressNumber());
        if(s!=null){
            result = s;
            log.info("T获取到物流Map"+s);
        }else{
            TianXingJianAPI tianXingJianAPI = new TianXingJianAPI();
            // result = tianXingJianAPI.getOrderTracesByJson(d01OrderVO.getExpressNumber());
            result = "{\"code\":200,\"msg\":\"success\",\"newslist\":[{\"status\":4,\"updatetime\":\"2022-03-20 20:31:59\",\"kuaidiname\":\"韵达速递\",\"telephone\":\"95546\",\"list\":[{\"time\":\"2022-03-14 18:50:27\",\"content\":\"您的快件已被 皮村韵达门店 代签收，地址: 皮村中心街小熊烧烤海鲜大排档福兰德超市往东80米韵达门店（温馨提示您：戴口罩取快递，个人防护要牢记），如有疑问请电联快递员侯亚彬(13681580674) ，投诉电话:010-80909620\"},{\"time\":\"2022-03-14 16:38:42\",\"content\":\"北京主城区公司朝阳区金榆路服务部[010-86392185] 快递员 侯亚彬（13681580674） 正在为您派送。快件已消毒，小哥体温正常，将佩戴口罩为您派送，您也可联系小哥将快件放置指定代收点或快递柜（温馨提示您：戴口罩取快递，个人防护要牢记），【95121为韵达快递员外呼专属号码，请放心接听】\"},{\"time\":\"2022-03-14 16:05:30\",\"content\":\"已到达 北京主城区公司朝阳区金榆路服务部[010-86392185]\"},{\"time\":\"2022-03-14 09:45:11\",\"content\":\"已离开 北京分拨交付中心；发往 北京主城区公司朝阳区金榆路服务部\"},{\"time\":\"2022-03-14 09:31:21\",\"content\":\"已到达 北京分拨交付中心\"},{\"time\":\"2022-03-13 23:34:38\",\"content\":\"【盘锦市】已离开 辽宁盘锦分拨交付中心；发往 京北地区包\"},{\"time\":\"2022-03-13 23:01:27\",\"content\":\"【盘锦市】已到达 辽宁盘锦分拨交付中心\"},{\"time\":\"2022-03-13 16:19:36\",\"content\":\"【盘锦市】辽宁省盘锦分拨营销市场部韵海分部-张浩（15141268704） 已揽收\"}]}]}";
            if(result !=null){
                orderTraceMap.put(d01OrderVO.getExpressNumber(), result);
                log.info("T获取到物流添加Map");
            }
            log.info("T获取到物流"+result);
        }
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
        JSONArray newslist = json.getJSONArray("newslist");
        TracesVO tracesVO = new TracesVO();
        List<TracesInfoVO> nList = new ArrayList<>();
        for (int i = 0; i < newslist.size(); i++) {
            net.sf.json.JSONObject jsonObject2 = newslist.getJSONObject(i);
            JSONArray list = jsonObject2.getJSONArray("list");
            for (int n = 0; n < list.size(); n++) {
                net.sf.json.JSONObject jsonObject1 = list.getJSONObject(n);
                TracesInfoVO tracesInfoVO = new TracesInfoVO();
                tracesInfoVO.setAcceptTime(jsonObject1.getString("time"));
                tracesInfoVO.setAcceptStation(jsonObject1.getString("content"));
                nList.add(tracesInfoVO);
            }
        }
        tracesVO.setTraces(nList);
        tracesVO.setExpressName(d01OrderVO.getExpressName());
        tracesVO.setLogisticCode(d01OrderVO.getExpressNumber());
        return tracesVO;
    }

    public void clearMap(){
        orderTraceMap.clear();
        log.info("定时清空物流Map>>>>>>>>>>>>>>");
    }
    public static void test() {
        String result = "{\"code\":200,\"msg\":\"success\",\"newslist\":[{\"status\":4,\"updatetime\":\"2022-03-20 20:31:59\",\"kuaidiname\":\"韵达速递\",\"telephone\":\"95546\",\"list\":[{\"time\":\"2022-03-14 18:50:27\",\"content\":\"您的快件已被 皮村韵达门店 代签收，地址: 皮村中心街小熊烧烤海鲜大排档福兰德超市往东80米韵达门店（温馨提示您：戴口罩取快递，个人防护要牢记），如有疑问请电联快递员侯亚彬(13681580674) ，投诉电话:010-80909620\"},{\"time\":\"2022-03-14 16:38:42\",\"content\":\"北京主城区公司朝阳区金榆路服务部[010-86392185] 快递员 侯亚彬（13681580674） 正在为您派送。快件已消毒，小哥体温正常，将佩戴口罩为您派送，您也可联系小哥将快件放置指定代收点或快递柜（温馨提示您：戴口罩取快递，个人防护要牢记），【95121为韵达快递员外呼专属号码，请放心接听】\"},{\"time\":\"2022-03-14 16:05:30\",\"content\":\"已到达 北京主城区公司朝阳区金榆路服务部[010-86392185]\"},{\"time\":\"2022-03-14 09:45:11\",\"content\":\"已离开 北京分拨交付中心；发往 北京主城区公司朝阳区金榆路服务部\"},{\"time\":\"2022-03-14 09:31:21\",\"content\":\"已到达 北京分拨交付中心\"},{\"time\":\"2022-03-13 23:34:38\",\"content\":\"【盘锦市】已离开 辽宁盘锦分拨交付中心；发往 京北地区包\"},{\"time\":\"2022-03-13 23:01:27\",\"content\":\"【盘锦市】已到达 辽宁盘锦分拨交付中心\"},{\"time\":\"2022-03-13 16:19:36\",\"content\":\"【盘锦市】辽宁省盘锦分拨营销市场部韵海分部-张浩（15141268704） 已揽收\"}]}]}";
//        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
//        JSONArray newslist = json.getJSONArray("newslist");
//        for(int i=0;i<newslist.size();i++){
//            net.sf.json.JSONObject jsonObject = newslist.getJSONObject(i);
//            JSONArray list = jsonObject.getJSONArray("list");
//            for(int n=0;n<list.size();n++){
//                net.sf.json.JSONObject jsonObject1 = list.getJSONObject(n);
//                String time = jsonObject1.getString("time");
//                String content = jsonObject1.getString("content");
//                System.out.println(time +"    "+content);
//            }
//
//        }
    }

    public static void main(String[] args) {
        test();
//        try {
//            //要json数据
//            JSONObject jsonObject = new JSONObject();
//            //快递公司的编码要从数据库查出来；
//            jsonObject.put("ShipperCode","STO");
//            jsonObject.put("CustomerName","圆通快递");
//            //快递公司的运单号要从数据库查出来；
//            jsonObject.put("LogisticCode","YT8032725632637");
//            jsonObject.put("EBusinessID","1366652");
//            jsonObject.put("AppKey","bae933a5-2ebe-4683-9ae1-47c5dcfaf100");
//            jsonObject.put("ReqURL","http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx");
//
//            KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
//            String result  = kdniaoTrackQueryAPI.getOrderTracesByJson(jsonObject);
//            System.out.println("result："+result);
//            TracesVO tracesVO = JSONObject.parseObject(result, TracesVO.class);
//            //tracesVO.setExpressName(d01OrderVO.getExpressName());
//            List<TracesInfoVO> traces = tracesVO.getTraces();
//            List<TracesInfoVO> nList = new ArrayList<>();
//            if(traces.size()>0){
//                for(int i=traces.size()-1;i>=0;i--){
//                    nList.add(traces.get(i));
//                }
//            }
//            tracesVO.setTraces(nList);
//            System.out.println(nList.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}
