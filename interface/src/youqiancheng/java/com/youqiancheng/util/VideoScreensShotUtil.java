//package com.pinyouma.util;
//
//import com.handongkeji.util.ResourceUtil;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//
///**
// *
// * @Description:截图工具
// * @author yws
// * @date 2018年12月17日
// *
// */
//public class VideoScreensShotUtil {
//
//	public static final String localpath= ResourceUtil.getConfigByName("localpath");
//	  /**
//     * 获取指定视频的帧并保存为图片至指定目录
//     * @param file  源视频文件
//     * @param framefile  截取帧的图片存放路径
//     * @throws Exception
//     */
//    public static String fetchPic(File file, String framefile,String filepath) throws Exception{
//        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
//        ff.start();
//        int lenght = ff.getLengthInFrames();
//
//        File targetFile = new File(filepath+framefile);
//        int i = 0;
//        Frame frame = null;
//        while (i < lenght) {
//            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
//            frame = ff.grabFrame();
//            if ((i > 5) && (frame.image != null)) {
//                break;
//            }
//            i++;
//        }
//        String imgSuffix = "jpg";
//        if(framefile.indexOf('.') != -1){
//            String[] arr = framefile.split("\\.");
//            if(arr.length>=2){
//                imgSuffix = arr[1];
//            }
//        }
//
//        Java2DFrameConverter converter =new Java2DFrameConverter();
//        BufferedImage srcBi =converter.getBufferedImage(frame);//BufferedImage(frame);
//        int owidth = srcBi.getWidth();
//        int oheight = srcBi.getHeight();
//        // 对截取的帧进行等比例缩放
//        int width = 800;
//        int height = (int) (((double) width / owidth) * oheight);
//        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//        bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
//        try {
//            ImageIO.write(bi, imgSuffix, targetFile);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        ff.stop();
//		return framefile;
//    }
//
//    /**
//     *
//     * Description: 抓取视频多张截图
//     * @param file
//     * @return
//     * @throws Exception
//     * @author yws
//     * @date 2018年12月17日
//     */
//    public static List<String> fetchPicLIist(File file) throws Exception{
//        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
//        ff.start();
//        int lenght = ff.getLengthInFrames();
//
//        Long l=getVideoTime(file);
//        System.out.println("该视频时间长" + l + "------------");
//        int mlen=l.intValue();
//        int cutp=lenght/mlen;//固定分割位置
//      //  int cutp=lenght/30;//固定分割位置
//        System.out.println(""+lenght+"----"+cutp);
//
//        List<String> piclist=new ArrayList<String>();
//
//        //截取5张图片.添加到列表
//        for (int j = 0; j < (mlen-1); j++) {
//
//            //生成文件名
//            long time=System.currentTimeMillis();
//        	String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//    		int length = 2;
//    		Random random = new Random();
//    		StringBuffer sb = new StringBuffer();
//    		for (int i = 0; i < length; i++) {
//    			int number = random.nextInt(base.length());
//    			sb.append(base.charAt(number));
//    		}
//            //创建临时文件夹
//            createFolder(localpath);
//            String framefile=localpath+sb.toString()+time+".jpg";
//
//            File targetFile = new File(framefile);
//            int i = 0;
//            Frame frame = null;
//            //固定截图分割位置
//
//            int a =cutp;
//
//            a=cutp*(j);
//
//           // System.out.println("6666666---"+lenght+"----"+a);
//            while (i < lenght) {
//                // 过滤前5帧，避免出现全黑的图片，依自己情况而定 random.nextInt(lenght)
//            	   if(i >a) {
//            		   frame = ff.grabFrame();
//                       if ((i >5)&& (frame.image != null)) {
//                           break;
//                       }
//            	   }
//            	   i++;
//            }
//            //System.out.println("6666666---"+lenght+"----"+a);
//
//            String imgSuffix = "jpg";
//            if(framefile.indexOf('.') != -1){
//                String[] arr = framefile.split("\\.");
//                if(arr.length>=2){
//                    imgSuffix = arr[1];
//                }
//            }
//
//            Java2DFrameConverter converter =new Java2DFrameConverter();
//            BufferedImage srcBi =converter.getBufferedImage(frame);//BufferedImage(frame);
//            int owidth = srcBi.getWidth();
//            int oheight = srcBi.getHeight();
//            // 对截取的帧进行等比例缩放
//            int width = 800;
//            int height = (int) (((double) width / owidth) * oheight);
//            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//            bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
//            try {
//                ImageIO.write(bi, imgSuffix, targetFile);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        	piclist.add(framefile);
//
//		}
//
//        ff.stop();
//		return piclist;
//    }
//
//    /**
//     * 获取视频时长，单位为秒
//     * @param file
//     * @return 时长（s）
//     */
//    public static Long getVideoTime(File file){
//        Long times = 0L;
//        try {
//            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
//            ff.start();
//            times = ff.getLengthInTime()/(1000*1000);
//            ff.stop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return times;
//    }
//
//    /**
//	 * 创建存放上传文件的文件夹，如果不存在则自动创建
//	 *
//	 * @param folderpath
//	 * @return
//	 */
//	public static String createFolder(String folderpath) {
//		File file = new File(folderpath);
//		// 如果文件夹不存在则创建
//		if (!file.exists() && !file.isDirectory()) {
//			file.mkdir();
//		} else {
//		}
//
//		return folderpath;
//	}
//
//	/**
//	 * 删除单个文件 //synchronized
//	 *
//	 * @param sPath
//	 *            被删除文件的文件名
//	 * @return 单个文件删除成功返回true，否则返回false
//	 */
//	public static boolean deleteFile(String sPath) {
//		boolean flag = false;
//		File file = new File(sPath);
//		// 路径为文件且不为空则进行删除
//		if (file.isFile() && file.exists()) {
//			file.delete();
//			flag = true;
//		}
//		return flag;
//	}
//
//	public static String getNameforDate(){
//		Date d = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String fileName= sdf.format(d);
//		return fileName;
//	}
//
//	public static void main(String[] args) {
//		try {
//
//			VideoScreensShotUtil.fetchPicLIist(new File("H://zimg3//8.34.mp4"));
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
