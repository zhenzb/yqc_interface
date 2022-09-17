package cn.tign.hz.factory.response.data;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 18:13
 * @version 
 */
public class CreateTemplateData {
    private String fileKey;
    private String sealId;
    private String url;
    private int height;
    private int width;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
