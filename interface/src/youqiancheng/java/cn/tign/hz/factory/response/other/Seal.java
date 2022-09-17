package cn.tign.hz.factory.response.other;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 18:22
 * @version 
 */
public class Seal {
    private String alias;
    private int createDate;
    private boolean defaultFlag;
    private String fileKey;
    private int height;
    private int width;
    private String sealId;
    private int sealType;
    private String url;
    private String sealBizType;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public boolean isDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
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

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public int getSealType() {
        return sealType;
    }

    public void setSealType(int sealType) {
        this.sealType = sealType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSealBizType() {
        return sealBizType;
    }

    public void setSealBizType(String sealBizType) {
        this.sealBizType = sealBizType;
    }
}
