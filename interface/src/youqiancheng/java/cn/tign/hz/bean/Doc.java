package cn.tign.hz.bean;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/27 11:56
 * @version JDK1.7
 */
public class Doc {
    private String fileId;
    private String fileName;
    private Integer encryption;
    private String filePassword;

    public String getFileId() {
        return fileId;
    }

    public Doc setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Doc setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Integer getEncryption() {
        return encryption;
    }

    public Doc setEncryption(Integer encryption) {
        this.encryption = encryption;
        return this;
    }

    public String getFilePassword() {
        return filePassword;
    }

    public Doc setFilePassword(String filePassword) {
        this.filePassword = filePassword;
        return this;
    }
}
