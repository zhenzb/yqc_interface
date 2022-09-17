package cn.tign.hz.factory.response;

/**
 * @description  请求响应类
 * @author  澄泓
 * @date  2020/10/23 14:05
 * @version JDK1.7
 */
public class Response {
    private int status;
    private String code;
    private String message;
    private String body;
//    public Response(int status,String resCtx){
//        this.status=status;
//        this.body = resCtx;
//        JSONObject jsonObject = JSONObject.parseObject(resCtx);
//        this.code= jsonObject.getString("code");
//        this.message=jsonObject.getString("message");
//        this.data=jsonObject.getString("data");
//        jsonObject.getString("23");
//    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
