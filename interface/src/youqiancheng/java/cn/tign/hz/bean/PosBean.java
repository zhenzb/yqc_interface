package cn.tign.hz.bean;
/**
 * @description  轩辕API签署区位置信息
 * @author  澄泓
 * @date  2020/10/27 14:16
 * @version JDK1.7
 */
public class PosBean {
    private String posPage;
    private float posX;
    private float posY;
    private boolean addSignTime;
    private String signTimeFormat;

    public String getPosPage() {
        return posPage;
    }

    public PosBean setPosPage(String posPage) {
        this.posPage = posPage;
        return this;
    }

    public float getPosX() {
        return posX;
    }

    public PosBean setPosX(float posX) {
        this.posX = posX;
        return this;
    }

    public float getPosY() {
        return posY;
    }

    public PosBean setPosY(float posY) {
        this.posY = posY;
        return this;
    }

    public boolean getAddSignTime() {
        return addSignTime;
    }

    public PosBean setAddSignTime(boolean addSignTime) {
        this.addSignTime = addSignTime;
        return this;
    }

    public String getSignTimeFormat() {
        return signTimeFormat;
    }

    public PosBean setSignTimeFormat(String signTimeFormat) {
        this.signTimeFormat = signTimeFormat;
        return this;
    }
}
