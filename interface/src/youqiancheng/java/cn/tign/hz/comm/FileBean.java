package cn.tign.hz.comm;

import cn.tign.hz.exception.DefineException;

import java.io.File;

/**
 * @description  轩辕API文件基础信息类
 * @author  澄泓
 * @date  2020/10/26 14:54
 * @version JDK1.7
 */
public class FileBean {
    //文件名称
    private String fileName;
    //文件大小
    private int fileSize;
    //文件内容MD5
    private String fileContentMD5;

    public FileBean(String filePath) throws DefineException {
        this.fileContentMD5=FileTransformation.getFileContentMD5(filePath);
            File file = new File(filePath);
            if (!file.exists()) {
                DefineException ex = new DefineException("文件不存在");
                ex.initCause(ex);
                throw ex;
            }
            this.fileName=file.getName();
            this.fileSize= (int) file.length();

    }

    public String getFileName() {
        return fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFileContentMD5() {
        return fileContentMD5;
    }
}
