package com.handongkeji.tencent.common.report.service;

import com.handongkeji.tencent.common.Configure;
import com.handongkeji.tencent.common.HttpsRequest;
import com.handongkeji.tencent.common.Util;
import com.handongkeji.tencent.common.report.protocol.ReportReqData;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * User: rizenguo
 * Date: 2014/11/12
 * Time: 17:07
 */
public class ReportService {

    private ReportReqData reqData ;

    /**
     * 请求统计上报API
     * @param reportReqData 这个数据对象里面包含了API要求提交的各种数据字段
     */
    public ReportService(ReportReqData reportReqData){
        reqData = reportReqData;
    }

    public String request() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String responseString = new HttpsRequest().sendPost(Configure.REPORT_API, reqData);

        Util.log("   report返回的数据：" + responseString);

        return responseString;
    }

    /**
     * 请求统计上报API
     * @param reportReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static String request(ReportReqData reportReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = new HttpsRequest().sendPost(Configure.REPORT_API, reportReqData);

        Util.log("report返回的数据：" + responseString);

        return responseString;
    }

}
