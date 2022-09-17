package cn.tign.hz.factory.filetemplate;

import cn.tign.hz.comm.FileHelper;
import cn.tign.hz.comm.HttpHelper;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.exception.DefineException;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.UploadFileResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * @description  轩辕API文件流上传
 * @author  澄泓
 * @date  2020/10/26 15:30
 * @version JDK1.7
 */
public class UploadFile extends Request {
    private String filePath;
    private String contentType;
    private String url;
    public UploadFile(String url,String filePath, String contentType) {
        this.url=url;
        this.filePath=filePath;
        this.contentType=contentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    /**
     * 上传文件
     * @return
     * @throws DefineException
     * @throws IOException
     */
    public UploadFileResponse excute() throws DefineException {
        byte[] bytes = FileHelper.getBytes(filePath);
        String fileContentMd5 = FileHelper.getContentMD5(filePath);
        Map map = HttpHelper.doUploadHttp(RequestType.PUT, url, bytes, fileContentMd5, contentType);
        String resCtx = (String)map.get("resCtx");//响应体
        int status = (int) map.get("status");//响应状态码
        UploadFileResponse uploadFileResponse=new UploadFileResponse();
        try {
            uploadFileResponse = JSON.parseObject(resCtx, UploadFileResponse.class);
            uploadFileResponse.setStatus(status);
            uploadFileResponse.setBody(resCtx);
        }catch (JSONException e){
            uploadFileResponse.setBody(resCtx);
        }
        return uploadFileResponse;
    }

    @Override
    public void build() {
    }
}
