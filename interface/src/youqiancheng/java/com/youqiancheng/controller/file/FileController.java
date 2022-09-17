package com.youqiancheng.controller.file;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.OSSClientUtil;
import com.youqiancheng.ability.OcrAbility;
import com.youqiancheng.util.FileUtils;
import com.youqiancheng.util.R;
import com.youqiancheng.vo.result.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.implementation.bytecode.Throw;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-03-12 12:17:36
 */

@RestController
@RequestMapping("/fileUpload")
@Api(tags = "公共接口-文件上传")
public class FileController {
    @Value("${app.filePath}")
    String filePath;

    @Value("${app.pre}")
    String filePre;
    @Value("${app.ip}")
    String ip;
    @Value("${server.port}")
    String port;
    @Autowired
    private OcrAbility ocrAbility;


    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public R upload(MultipartFile file, String key,String type) {
        if(file==null){
            return R.error("上传文件不能为空");
        }
        if(StringUtils.isBlank(key)){
            return R.error("文件名不能为空");

        }

//        try {
//            ip = "http://"+ InetAddress.getLocalHost().getHostAddress()+":"+port;
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        //String nip = ip+":"+port;
        String nip = ip;
        try {
            String resPath = FileUtils.saveFile(file.getBytes(), filePath, key);
            //return R.ok().put("imgUrlPath",ip + filePre + "/"+resPath);
            return uploaOss(file, key, type);
            } catch (Exception e) {
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "文件上传失败");
        }
    }

    private R uploaOss(MultipartFile file, String key, String type) throws Exception {
        byte [] byteArr=file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        Map<String, Object> map = ossClientUtil.uploadFile(inputStream, key);
        if (map == null)
        {
            throw new Exception("返回结果map为null");
        }
        if(type ==null){
            return R.ok().put("imgUrlPath", map.get("url"));
        }
        return uploadOCR(type, map);
    }

    private R uploadOCR(String type, Map<String, Object> map) throws ParseException {
        JSONObject jSONObject = new JSONObject();
        try{
             jSONObject = ocrAbility.ocrIdCard(map.get("url").toString());
        }catch (Exception e){
            e.printStackTrace();
            return R.error( "身份证未识别！请重新上传");
        }
        if("1".equals(type)){
            if(jSONObject.has("valid_date")){
                return R.error("请上传身份证正面照");
            }
        }else{
            if(!jSONObject.has("valid_date")){
                return R.error("请上传身份证反面照");
            }
        }
        if(jSONObject.has("valid_date")){
            String valid_date = jSONObject.getString("valid_date");
            String[] split = valid_date.split("-");
            if(!split[1].equals("长期")){
                SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd");
                Date parse = format.parse(split[1]);
                Long timeStart = parse.getTime() / 1000;
                Long timeEnd = new Date().getTime() / 1000;
                if(timeStart<timeEnd){
                    return R.error("身份证已过期，请重新上传");
                }
            }
            return R.ok().put("imgUrlPath", map.get("url")).put("valid_date", jSONObject.getString("valid_date"));
        }else if (jSONObject.has("name")){
            return R.ok().put("imgUrlPath", map.get("url")).put("userName", jSONObject.getString("name")).put("idCardNumber", jSONObject.getString("id_card_number"));
        }else{
            return R.error( "身份证未识别！请重新上传");
        }
    }

    @PostMapping("/H5Upload")
    @ApiOperation(value = "H5文件上传")
    public String H5Upload(MultipartFile file) {
        if(file==null){
            return "上传文件不能为空";
        }
        String key = file.getOriginalFilename();
//        try {
//            ip = "http://"+ InetAddress.getLocalHost().getHostAddress()+":"+port;
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        //String nip = ip+":"+port;
        String nip = ip;
        try {
            //String resPath = FileUtils.saveFile(file.getBytes(), filePath, key);
            //return R.ok().put("imgUrlPath",ip + filePre + "/"+resPath);
            byte [] byteArr=file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            OSSClientUtil ossClientUtil = new OSSClientUtil();
            Map<String, Object> map = ossClientUtil.uploadFile(inputStream, key);
            if (map == null)
            {
                throw new Exception("返回结果map为null");
            }
            return map.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }
    @PostMapping("/download")
    public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename="2.xlsx";
        String filePath = "D:/download" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/x-msdownload;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

//    /**
//     * @function 视频上传
//     * @return
//     */
//    @PostMapping("/oss/uploadVideo")
//    @ApiOperation(value = "视频上传")
//    R uploadVideo(@RequestParam(value = "file", required = false) MultipartFile file) {
//        if(file == null){
//            return R.error( "内容为空");
//        }else{
//            return  R.ok(OssUtil.upload(file, OssUtil.FileDirType.GYS_VIDEO));
//        }
//
//    }
//
//    /**
//     * @function 图片上传
//     * @param file
//     * @return
//     */
//    @PostMapping("/oss/uploadImage")
//    @ApiOperation(value = "图片上传")
//    ResultVo uploadImages(@RequestParam(value = "file", required = false) MultipartFile file) {
//        if(file == null) {
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL);
//        }else {
//            return ResultVOUtils.success(OssUtil.uploadIMG(file, OssUtil.FileDirType.GYS_IMAGES));
//        }
//    }
//    /**
//     * @function 文件上传
//     * @param file
//     * @return
//     */
//    @PostMapping("/oss/uploadFile")
//    @ApiOperation(value = "文件上传")
//    R uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
//        if(file == null) {
//            return R.error("内容为空");
//        }else {
//            return R.ok(OssUtil.upload(file, OssUtil.FileDirType.GYS_REPORT));
//        }
//    }
//
//    /**
//     * 删除OSS对象
//     *
//     */
//    @PostMapping("/oss/deleteImage")
//    @ApiOperation(value = "删除OSS对象")
//   R deleteArtImg(@RequestBody List<String> strings) {
//        OssUtil.delete(strings);
//        return R.ok();
//    }


}
