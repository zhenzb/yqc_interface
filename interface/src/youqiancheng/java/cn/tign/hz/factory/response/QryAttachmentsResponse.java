package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.other.Attachments;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:14
 * @version 
 */
public class QryAttachmentsResponse extends Response{
    private Attachments attachments;

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }
}
