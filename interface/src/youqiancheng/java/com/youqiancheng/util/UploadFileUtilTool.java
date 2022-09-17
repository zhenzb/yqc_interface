//package com.pinyouma.util;
//
//import com.handongkeji.util.ResourceUtil;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//
//@Component
//public class UploadFileUtilTool {
//
//    protected static Log logger =LogFactory.getLog(UploadFileUtilTool.class) ;
////		private final static Lock aLock=new ReentrantLock();
//
//    public static Map<String,Object> upload(MultipartFile file,String prjName,
//            String basePath,String format,Integer type  ) throws Exception{
//
//        Map<String,Object> map=new HashMap<String,Object>();
//
//        String filepath = "";
//        String website= ResourceUtil.getConfigByName("svr.url");
//        //区别不同项目的文件夹
//        String currFolder = ResourceUtil.getConfigByName("upload.basepath") + File.separator;
//
//        StringBuffer uploadBasePath = new StringBuffer();
//        String panfu = ResourceUtil.getConfigByName("file.panfuurl");
//        uploadBasePath.append(panfu).append(currFolder);
//        int isAudioFile = 0;
//        //创建上传文件夹
//        createFolder(uploadBasePath.toString());
//        filepath = uploadBasePath + ResourceUtil.getConfigByName("file.comon.foldername");
//
//        String flag_b;
//        try {
//            flag_b = createFolder(filepath.toString());
//            logger.info("创建的路径文件夹:"+flag_b);
//        } catch (Exception e2) {
//            logger.info("创建的路径文件夹出错:"+e2.getMessage());
//        }
//
//        String datestr=getNameforDate();
//        filepath+=File.separator+datestr;
//        createFolder(filepath);
//        logger.info("filepath:" + filepath);
//        String[] strarr = null;
//        if (filepath != null) {
//            strarr = filepath.split(ResourceUtil.getConfigByName("upload.basepath"));
//        }
//        String preName = "";// 后缀、文件名
//        if (strarr != null) {
//            preName = strarr[1];
//        }
//        preName = filepath.substring(filepath.indexOf(ResourceUtil.getConfigByName("upload.basepath")));
//        String name = file.getOriginalFilename();
//        String extName = name.substring(name.lastIndexOf("."), name.length());
//        // 服务端根据时间戳重命名上传的文件
//        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        int length = 2;
//        Random random = new Random();
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(base.length());
//            sb.append(base.charAt(number));
//        }
//        String shotname =sb.toString() + System.currentTimeMillis();
//        String filenameNoext =  File.separator+shotname;
//
//        name = filenameNoext + extName;
//
//        //String vName = File.separator + preName + name;
//        String vName = File.separator + preName +filenameNoext+ extName;
//        int ischangeok = 0;
//        String realpath = null;
//        String shotfile=null;
//        String shotimg = null;
//        if (!file.isEmpty()) {
//            try {
//                final String vpath=filepath + File.separator + name;
//
//                //文件真实路径
//                realpath=vpath;
//                final File file_in= new File(vpath);
//                file.transferTo(file_in);
//                //返回截图路径
//                if(type ==1){
//                    String getshotimg=filenameNoext+1+".jpg";
//                    shotfile=VideoScreensShotUtil.fetchPic(file_in, getshotimg,filepath);
//                    shotimg=(File.separator + preName+shotfile).replaceAll("\\\\", "/");
//                    logger.info("截图文件名："+shotimg);
//                    map.put("pname", website+shotimg);
//                }
//            } catch (Exception e1) {
//                throw  e1;
//            }
//            //imgName = imgName.replaceAll("\\\\", "/");
//
//            vName=vName.replaceAll("\\\\", "/");
//
//
//        }
//        map.put("ischangeok", ischangeok);
//
//        map.put("vname", website+vName);
//        return map;
//
//    }
//
//    /**
//     * 创建存放上传文件的文件夹，如果不存在则自动创建
//     *
//     * @param folderpath
//     * @return
//     */
//    public static String createFolder(String folderpath) {
//        File file = new File(folderpath);
//        // 如果文件夹不存在则创建
//        if (!file.exists() && !file.isDirectory()) {
//            file.mkdir();
//        } else {
//        }
//
//        return folderpath;
//    }
//
//    /**
//     * 删除单个文件 //synchronized
//     *
//     * @param sPath
//     *            被删除文件的文件名
//     * @return 单个文件删除成功返回true，否则返回false
//     */
//    public static boolean deleteFile(String sPath) {
//        boolean flag = false;
//        File file = new File(sPath);
//        // 路径为文件且不为空则进行删除
//        if (file.isFile() && file.exists()) {
//            file.delete();
//            flag = true;
//        }
//        return flag;
//    }
//
//    public static String getNameforDate(){
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String fileName= sdf.format(d);
//        return fileName;
//    }
//}
//
