package cn.tign.hz.factory.response.data;
/**
 * 实名认证查询个人刷脸状态data
 * @author  澄泓
 * @date  2020/11/12 15:12
 * @version JDK1.7
 */
public class QryFaceStatusData {
    private String status;
    private String message;
    private String similarity;
    private String livingScore;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getLivingScore() {
        return livingScore;
    }

    public void setLivingScore(String livingScore) {
        this.livingScore = livingScore;
    }
}
