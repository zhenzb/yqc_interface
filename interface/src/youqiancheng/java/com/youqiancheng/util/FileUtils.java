package com.youqiancheng.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传
 * @author wxt
 *
 *
 */
public class FileUtils {


    public static String saveFile(byte[] file, String filePath, String fileName) {

        int random = (int) (Math.random() * 100 + 1);
        int random1 = (int) (Math.random() * 100 + 1);
        filePath = filePath + random + File.separator + random1 + File.separator;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        String[] split = fileName.split("\\.");
        String newFileName=System.currentTimeMillis()+"."+split[1];
        try {
            fileOutputStream = new FileOutputStream(filePath + newFileName);
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer buf = ByteBuffer.wrap(file);
            while (fileChannel.write(buf) != 0) {
            }
        } catch (Exception e) {

        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //url
        return random + "/" + random1 + "/" + newFileName;
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 压缩图片上传图片 最大图片大小1M
     * @param file
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file, String filePath, String fileName)throws IOException{
        Long imgMaxSize = 1024L * 1024L;
        List<String> imgsType = new ArrayList<>(Arrays.asList("BMP","JPG","PNG"));
        int random = (int) (Math.random() * 100 + 1);
        int random1 = (int) (Math.random() * 100 + 1);
        filePath = filePath + random + File.separator + random1 + File.separator;
        Long fileSize = file.getSize();
        String fileType = file.getContentType().toUpperCase();
        InputStream inputStream = file.getInputStream();
        //如果上传的是图片，且超过指定大小，自动压缩输入流
        if(imgsType.contains(fileType)&&fileSize>imgMaxSize){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //压缩输入流，scale是压缩比例；quality是质量比例，0-1之间，越接近1,质量越高
            Thumbnails.of(inputStream).scale(0.7f).outputQuality(0.25d).toOutputStream(out);
            InputStream imgInputStream = new ByteArrayInputStream(out.toByteArray());
            inputStream = imgInputStream;
        }
        //保存文件到本地服务器
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File newFile = new File(filePath+fileName);
        newFile.createNewFile();
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(newFile);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return random + "/" + random1 + "/" + fileName;

    }



    public static void download(String filename, HttpServletResponse res) throws IOException {
        // 发送给客户端的数据
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File("./file/" + filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }



}
