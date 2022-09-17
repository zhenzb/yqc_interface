package cn.tign.hz.bean;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/27 11:38
 * @version 
 */
public class Attachment {
    private String fileId;
    private String attachmentName;

    public String getFileId() {
        return fileId;
    }

    public Attachment setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public Attachment setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }
}
