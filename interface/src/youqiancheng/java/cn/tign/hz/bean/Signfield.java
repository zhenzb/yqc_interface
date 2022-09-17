package cn.tign.hz.bean;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/27 14:14
 * @version JDK1.7
 */
public class Signfield {
    private boolean autoExecute;
    private String actorIndentityType;
    private String fileId;
    private String sealId;
    private String sealType;
    private Integer signType;
    private PosBean posBean;
    private Integer width;
    private Integer signDateBeanType;
    private SignDateBean signDateBean;
    private String authorizedAccountId;
    private String signerAccountId;
    private int order;


    public boolean isAutoExecute() {
        return autoExecute;
    }

    public Signfield setAutoExecute(boolean autoExecute) {
        this.autoExecute = autoExecute;
        return this;
    }

    public String getActorIndentityType() {
        return actorIndentityType;
    }

    public Signfield setActorIndentityType(String actorIndentityType) {
        this.actorIndentityType = actorIndentityType;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public Signfield setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getSealId() {
        return sealId;
    }

    public Signfield setSealId(String sealId) {
        this.sealId = sealId;
        return this;
    }

    public String getSealType() {
        return sealType;
    }

    public Signfield setSealType(String sealType) {
        this.sealType = sealType;
        return this;
    }

    public Integer getSignType() {
        return signType;
    }

    public Signfield setSignType(Integer signType) {
        this.signType = signType;
        return this;
    }

    public PosBean getPosBean() {
        return posBean;
    }

    public Signfield setPosBean(PosBean posBean) {
        this.posBean = posBean;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public Signfield setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getSignDateBeanType() {
        return signDateBeanType;
    }

    public Signfield setSignDateBeanType(Integer signDateBeanType) {
        this.signDateBeanType = signDateBeanType;
        return this;
    }

    public SignDateBean getSignDateBean() {
        return signDateBean;
    }

    public Signfield setSignDateBean(SignDateBean signDateBean) {
        this.signDateBean = signDateBean;
        return this;
    }

    public String getAuthorizedAccountId() {
        return authorizedAccountId;
    }

    public Signfield setAuthorizedAccountId(String authorizedAccountId) {
        this.authorizedAccountId = authorizedAccountId;
        return this;
    }

    public String getSignerAccountId() {
        return signerAccountId;
    }

    public Signfield setSignerAccountId(String signerAccountId) {
        this.signerAccountId = signerAccountId;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public Signfield setOrder(int order) {
        this.order = order;
        return this;
    }
}
