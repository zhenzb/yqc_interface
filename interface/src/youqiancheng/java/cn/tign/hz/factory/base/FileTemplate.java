package cn.tign.hz.factory.base;

import cn.tign.hz.bean.SimpleFormFields;
import cn.tign.hz.factory.filetemplate.CreateFileByTemplate;
import cn.tign.hz.factory.filetemplate.CreateTemplateByUploadUrl;
import cn.tign.hz.factory.filetemplate.GetFileUploadUrl;
import cn.tign.hz.factory.filetemplate.UploadFile;


/**
 * 轩辕API文件模板功能类
 * @description  轩辕API文件模板功能类
 * @author  澄泓
 * @date  2020/10/26 14:30
 * @version JDK1.7
 */
public class FileTemplate {

    /**
     * 轩辕API通过上传方式创建文件
     * @param contentMd5 文件md5值,demo提供了FileTransformation.getFileContentMD5(filePath)获取
     * @param contentType 目标文件的MIME类型，支持：
     *
     * （1）application/octet-stream
     *
     * （2）application/pdf
     * @param convert2Pdf 是否转换成pdf文档
     * @param fileName 文件名称
     * @param fileSize 文件大小，单位byte
     * @return
     */
    public static GetFileUploadUrl getFileUploadUrl(String contentMd5, String contentType, boolean convert2Pdf, String fileName, Integer fileSize){
        return new GetFileUploadUrl(contentMd5, contentType, convert2Pdf, fileName, fileSize);
    }

    /**
     * 上传文件(包括模板文件)
     *      * @param filePath 本地文件地址
     *      * @param contentType 目标文件的MIME类型需要与通过上传方式创建文件接口入参的contentType保持一致
     * @return
     */
    public static UploadFile uploadFile(String url,String filePath, String contentType){
       return new UploadFile(url, filePath, contentType);
    }

    /**
     * 轩辕API通过上传方式创建模板
     * @param contentMd5 模板文件md5值
     * @param contentType 目标文件的MIME类型
     * @param fileName 文件名称
     * @param convert2Pdf 是否需要转成pdf
     * @return
     */
    public static CreateTemplateByUploadUrl createTemplateByUploadUrl(String contentMd5, String contentType, String fileName, boolean convert2Pdf){
        return new CreateTemplateByUploadUrl(contentMd5,contentType,fileName,convert2Pdf);
    }


    /**
     *
     * @param name 文件名
     * @param templateId 模板id
     * @param simpleFormFields 输入项填充内容，key:value 传入
     * @return
     */
    public static CreateFileByTemplate createFileByTemplate(String name, String templateId, SimpleFormFields simpleFormFields){
        return new CreateFileByTemplate(name, templateId, simpleFormFields);
    }
}
