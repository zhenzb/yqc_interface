package com.handongkeji.util;

import java.util.Random;

public class RandomStringMaker {

	public static String createSign(String stringSignTemp){
		
		String sign=MD5.MD5Encode(stringSignTemp).toUpperCase();
		return sign;
		
	}
	
	public static String nonce_str_out(){
		return   new Random().nextInt(90809009)+"";
	}
	
	public static String codeMaker(String base ,Integer length ){
		if(base==null){
		  base = "5386729014";
		}
		if(length==null||length<1){
			length = 2;
		}
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return    sb.toString();
	}
	
	public static String orderNoMaker(String base ,Integer length ){
		if(base==null){
		  base = "5386729014";
		}
		if(length==null||length<1){
			length = 2;
		}
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		String  stamp=System.currentTimeMillis()+"";
		return   stamp+sb.toString();
	}
	public static String orderNoMaker(){
		String base = "5386729014";
		int length = 2;
		return orderNoMaker(base,length);
	}
	public static String randomOrderNo(){
		//out_trade_no =
    	String base = "0E0A7B110JKUC22D3E3EF9GH9I5J1UH2J67K7L18M99N1FF08OPEQ4R2S1TU3VF1F6X6YZ72";
 		int length = 4;
 		Random random = new Random();
 		StringBuffer sb = new StringBuffer();
 		for (int i = 0; i < length; i++) {
 			int number = random.nextInt(base.length());
 			sb.append(base.charAt(number));
 		}
 		String  stamp=System.currentTimeMillis()+"";
 		int mark=8-random.nextInt(3);
 		return  stamp.substring(mark)+sb.toString()+stamp.substring(5,mark) ;
	}
    public static void main(String[] args) {
    	
    	
    	String key="appid=wxd4e1e2c091341fa9&body=微信APP支付"
    			+"&mch_id=1269726101&nonce_str=24272576&out_trade_no=001&spbill_create_ip=127.0.0.1&total_fee="
    			+"10&trade_type=APP&key=88888888888888888888888888888888";
    	//  appid , body, mch_id, nonce_str, out_trade_no , spbill_create_ip ,  total_fee, trade_type
    	//
    	
    	//** URL地址：https://api.mch.weixin.qq.com/pay/unifiedorder
    	
    	//appid=wxd4e1e2c091341fa9&body=微信APP支付&mch_id=1269726101&nonce_str=24272576&out_trade_no=001&spbill_create_ip=127.0.0.1&total_fee=10&trade_type=APP
    	String nonce_str ="";
        try {
        	
        	//7.随机字符串  --  nonce_str
        	 nonce_str= new Random().nextInt(90809009)+""; // 24272576  ,微信APP支付 ，订单号：001  total_fee：10
        	 //spbill_create_ip:127.0.0.1  trade_type= APP 
        	 
        	 //String stringA="";
        	 
        	 //String  stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d";
        	// 建议根据当前系统时间加随机序列来生成订单号
        	 
        
     		 
        	 //String sign=MD5.MD5Encode(stringSignTemp).toUpperCase();//="9A0A8659F005D6984697E2CA0A9CF3B7"
        	 
        	 System.out.println("生成 nonce_str ："+ nonce_str);
        	 
     		 System.out.println("生成的订单号："+randomOrderNo() );
        	 
        	 System.out.println("生成的SIGN签名："+createSign(key));

        	 
        	 System.out.println((int)(Math.random()*9000+1000));
        	 
        	 
        	 
        	 
            //--------------------------------------------------------------------
            //温馨提示，第一次使用该SDK时请到com.handongkeji.lvxingyongche.tencent.common.Configure类里面进行配置
            //--------------------------------------------------------------------



            //--------------------------------------------------------------------
            //PART One:基础组件测试
            //--------------------------------------------------------------------

            //1）https请求可用性测试
            //HTTPSPostRquestWithCert.test();

            //2）测试项目用到的XStream组件，本项目利用这个组件将Java对象转换成XML数据Post给API
            //XStreamTest.test();


            //--------------------------------------------------------------------
            //PART Two:基础服务测试
            //--------------------------------------------------------------------

            //1）测试被扫支付API
            //PayServiceTest.test();

            //2）测试被扫订单查询API
            //PayQueryServiceTest.test();

            //3）测试撤销API
            //温馨提示，测试支付API成功扣到钱之后，可以通过调用PayQueryServiceTest.test()，将支付成功返回的transaction_id和out_trade_no数据贴进去，完成撤销工作，把钱退回来 ^_^v
            //ReverseServiceTest.test();

            //4）测试退款申请API
            //RefundServiceTest.test();

            //5）测试退款查询API
            //RefundQueryServiceTest.test();

            //6）测试对账单API
            //DownloadBillServiceTest.test();


            //本地通过xml进行API数据模拟的时候，先按需手动修改xml各个节点的值，然后通过以下方法对这个新的xml数据进行签名得到一串合法的签名，最后把这串签名放到这个xml里面的sign字段里，这样进行模拟的时候就可以通过签名验证了
           // Util.log(Signature.getSignFromResponseString(Util.getLocalXMLString("/test/com/tencent/business/refundqueryserviceresponsedata/refundquerysuccess2.xml")));

            //Util.log(new Date().getTime());
            //Util.log(System.currentTimeMillis());

        } catch (Exception e){
            Util.log(e.getMessage());
        }

    }

}
