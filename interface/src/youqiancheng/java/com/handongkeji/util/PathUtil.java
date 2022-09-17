package com.handongkeji.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PathUtil {

	public static String getRealPath (HttpServletRequest request){
		//---------------------------
		String  prjName = request.getSession().getServletContext().getContextPath();		// 项目名称
		if ("".equals(prjName)) {
			prjName = File.separator+"ROOT"; // 默认为ROOT
		}
		String basePath = request.getSession().getServletContext().getRealPath("/");// 当前web应用的绝对路径 :
		try {
			basePath = basePath.substring(0, basePath.length() - prjName.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return basePath;
	}
	/**
	 * 
	* @Title: getPicWebPath
	* @Author victor 2016年6月8日 下午12:01:44
	* @Description: 
	* @param basePath //系统配置的app部署路径根路径，不含项目名
	* @param isWeb  获取路径的类型:ture 外网，false 本地
	* @return String    isWeb 为true 时 web外网访问路径，为空本地访问路，
	* @Site  http://www.handongkeji.com
	* @Copyrights 2016 handongkeji All rights reserved.
	 */
	public static String getPicWebPath (HttpServletRequest request,Boolean isWeb){
		//系统配置的app部署路径根路径，不含项目名
		String website=ResourceUtil.getConfigByName("svr.url");
		String filepath=getRealPath(request);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr= sdf.format(d);
		 filepath= filepath+ResourceUtil.getConfigByName("upload.basepath")
		 		 + File.separator
				 + ResourceUtil.getConfigByName("file.mon.foldername")
				 + File.separator
				 + datestr;
		 createFolder(filepath);
		//获取web路径
		if(isWeb){
			try { 
				website=  website
						+"/"+ResourceUtil.getConfigByName("upload.basepath")
						+"/"+ResourceUtil.getConfigByName("file.mon.foldername")
						+"/"+datestr;
				filepath=website.replaceAll("/", "\\\\");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return  filepath;
	}
	
	/**
	 * 
	* @Title: generatorFileName
	* @Author victor 2016年6月8日 下午12:16:29
	* @Description: 
	* @param extName 不带圆点的扩展名 如： png,jpg 
	* @return String     如：/14478763483648.png,/14478763483323.jpg
	* @Site  http://www.handongkeji.com
	* @Copyrights 2016 handongkeji All rights reserved.
	 */
	
	public static String generatorFileName (String extName){
		
		// 服务端根据时间戳重命名上传的文件
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int length = 2;
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		String filenameNoext =  File.separator+sb.toString() + System.currentTimeMillis();
		
		return filenameNoext+"."+extName;
		
	}
	/**
	 * 创建存放上传文件的文件夹，如果不存在则自动创建
	 * 
	 * @param realPath
	 * @return
	 */
	public static String createFolder(String folderpath) {
		File file = new File(folderpath);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		} else {
		}

		return folderpath;
	}
}
