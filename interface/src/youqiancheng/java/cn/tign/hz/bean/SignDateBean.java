package cn.tign.hz.bean;
/**
 * @description  轩辕API签署日期信息
 * @author  澄泓
 * @date  2020/10/27 15:05
 * @version JDK1.7
 */
public class SignDateBean {
    private Integer fontSize;
    private String format;
    private Integer posPage;
    private float posX;
    private float posY;

    public Integer getFontSize() {
        return fontSize;
    }

    public SignDateBean setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public SignDateBean setFormat(String format) {
        this.format = format;
        return this;
    }

    public Integer getPosPage() {
        return posPage;
    }

    public SignDateBean setPosPage(Integer posPage) {
        this.posPage = posPage;
        return this;
    }

    public float getPosX() {
        return posX;
    }

    public SignDateBean setPosX(float posX) {
        this.posX = posX;
        return this;
    }

    public float getPosY() {
        return posY;
    }

    public SignDateBean setPosY(float posY) {
        this.posY = posY;
        return this;
    }
}
