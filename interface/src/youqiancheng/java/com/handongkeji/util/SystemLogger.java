package com.handongkeji.util;
/*********************************************************
 ****** @author [ CaiYong:271668825@QQ.COM ] *************
 ****** @date   2014-11-11[WILLIAM PROJECT ] *************
 *********************************************************/
public class SystemLogger{
	protected final static org.apache.commons.logging.Log logger=org.apache.commons.logging.LogFactory.getLog(SystemLogger.class);
	   public static final String urlStartTag="@=>>>";
	   public static final String urlEndTag="<<<=@";
	   public static void recordHttpReq(String argUrl){
		   logger.warn(urlStartTag+argUrl+urlEndTag);
	   }
	   public static void recordHttpReq(javax.servlet.http.HttpServletRequest req){
		   logger.warn(urlStartTag+req.getRequestURL().toString()+urlEndTag);
	   }
	   public static void recordHttpReq(javax.servlet.http.HttpServletRequest req,@SuppressWarnings("rawtypes") Class class_){
		   logger.warn(urlStartTag+req.getRequestURL().toString()+urlEndTag+class_);
	   }
	   public static void recordHttpReq(javax.servlet.http.HttpServletRequest req,java.util.Map<String,String> argMap){
		   StringBuffer args=new StringBuffer();
		   @SuppressWarnings("rawtypes")
		java.util.Iterator iter=argMap.keySet().iterator();
		   while(iter.hasNext()){
			   String key=iter.next().toString();
			   args.append(key).append("=").append(argMap.get(key)).append("&");
		   }
		   logger.warn(urlStartTag+req.getRequestURL().toString()+urlEndTag+args);
	   }
}
