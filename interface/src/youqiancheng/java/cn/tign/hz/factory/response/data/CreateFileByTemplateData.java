package cn.tign.hz.factory.response.data;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 16:45
 * @version 
 */
public class CreateFileByTemplateData {
    private String downloadUrl;
    private String fileId;
    private String fileName;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
