package cn.tign.hz.factory.base;

import cn.tign.hz.bean.*;
import cn.tign.hz.factory.antfinsign.CheckAntfinNotary;
import cn.tign.hz.factory.antfinsign.QrySignAntPushInfo;
import cn.tign.hz.factory.other.SearchWordsPosition;
import cn.tign.hz.factory.signfile.CreateFlowOneStep;
import cn.tign.hz.factory.signfile.attachments.CreateAttachments;
import cn.tign.hz.factory.signfile.attachments.DeleteAttachments;
import cn.tign.hz.factory.signfile.attachments.QryAttachments;
import cn.tign.hz.factory.signfile.datasign.DataSign;
import cn.tign.hz.factory.signfile.datasign.DataVerify;
import cn.tign.hz.factory.signfile.documents.CreateDocuments;
import cn.tign.hz.factory.signfile.documents.DeleteDocuments;
import cn.tign.hz.factory.signfile.documents.DownDocuments;
import cn.tign.hz.factory.signfile.pdfverify.PdfVerify;
import cn.tign.hz.factory.signfile.signers.GetFileSignUrl;
import cn.tign.hz.factory.signfile.signers.QrySigners;
import cn.tign.hz.factory.signfile.signers.RushSign;
import cn.tign.hz.factory.signfile.signfields.*;
import cn.tign.hz.factory.signfile.signflows.*;

/**
 * 轩辕API签署服务功能类
 * @description  轩辕API签署服务功能类
 * @author  澄泓
 * @date  2020/10/27 11:32
 * @version JDK1.7
 */
public class SignFile {
    /**
     * 轩辕API一步发起签署
     * @param docs
     * @param flowInfo
     * @param signers
     * @return
     */
    public static CreateFlowOneStep createFlowOneStep(Docs docs, FlowInfo flowInfo, Signers signers){
        return new CreateFlowOneStep(docs, flowInfo, signers);
    }

    /**
     * 获取签署地址
     * @param flowId 流程id
     * @param accountId 签署人账号id
     * @return
     */
    public static GetFileSignUrl getFileSignUrl(String flowId, String accountId){
        return new GetFileSignUrl(flowId,accountId);
    }


    /**
     * 签署流程开启
     * @param flowId 流程id
     * @return
     */
    public static StartSignFlow startSignFlow(String flowId){
        return new StartSignFlow(flowId);
    }

    /**
     * 签署流程创建
     * @param businessScene 文件主题
     * @return
     */
    public static CreateSignFlow createSignFlow(String businessScene){
        return new CreateSignFlow(businessScene);
    }

    /**
     * 签署流程查询
     * @param flowId 流程id
     * @return
     */
    public static QrySignFlow qrySignFlow(String flowId){
        return new QrySignFlow(flowId);
    }

    /**
     * 签署流程撤销
     * @param flowId 流程id
     * @return
     */
    public static RevokeSignFlow revokeSignFlow(String flowId){
        return new RevokeSignFlow(flowId);
    }

    /**
     * 签署流程归档
     * @param flowId 流程id
     * @return
     */
    public static ArchiveSignFlow archiveSignFlow(String flowId){
        return new ArchiveSignFlow(flowId);
    }

    /**
     * 流程签署数据存储凭据
     * @param flowId
     * @return
     */
    public static GetVoucherSignFlow getVoucherSignFlow(String flowId){
        return new GetVoucherSignFlow(flowId);
    }

    /**
     * 流程文档添加
     * @param flowId 流程id
     * @param docs 文档列表数据
     * @return
     */
    public static CreateDocuments createDocuments(String flowId, Docs docs){
        return new CreateDocuments(flowId,docs);
    }

    /**
     * 流程文档下载
     * @param flowId 流程id
     * @return
     */
    public static DownDocuments downDocuments(String flowId){
        return new DownDocuments(flowId);
    }

    /**
     * 流程文档删除
     * @param flowId 流程id
     * @param fileIds 文档id列表,多个id使用“，”分隔
     * @return
     */
    public static DeleteDocuments deleteDocuments(String flowId, String fileIds){
        return new DeleteDocuments(flowId, fileIds);
    }

    /**
     * 流程附件添加
     * @param flowId 流程id
     * @param attachments 附件列表数据
     * @return
     */
    public static CreateAttachments createAttachments(String flowId, Attachments attachments){
        return new CreateAttachments(flowId, attachments);
    }

    /**
     * 流程附件列表
     * @param flowId 流程id
     * @return
     */
    public static QryAttachments qryAttachments(String flowId){
       return new QryAttachments(flowId);
    }

    /**
     * 流程附件删除
     * @param flowId 流程id
     * @param fileIds 文档id列表,多个id使用“，”分隔
     * @return
     */
    public static DeleteAttachments deleteAttachments(String flowId, String fileIds){
        return new DeleteAttachments(flowId, fileIds);
    }


    /**
     * 查询签署区列表
     * @param flowId 流程id
     * @return
     */
    public static QrySignFields qrySignFields(String flowId){
        return new QrySignFields(flowId);
    }

    /**
     * 添加平台自动盖章签署区
     * @param flowId 流程id
     * @param signfields 签署区列表数据
     * @return
     */
    public static CreatePlatformSign createPlatformSign(String flowId, Signfields signfields){
        return new CreatePlatformSign(flowId, signfields);
    }

    /**
     * 添加签署方自动盖章签署区
     * @param flowId 流程id
     * @param signfields 签署区列表数据
     * @return
     */
    public static CreateAutoSign createAutoSign(String flowId, Signfields signfields){
        return new CreateAutoSign(flowId, signfields);
    }


    /**
     * 添加手动盖章签署区
     * @param flowId 流程id
     * @param signfields 签署区列表数据
     * @return
     */
    public static CreateHandSign createHandSign(String flowId, Signfields signfields){
        return new CreateHandSign(flowId, signfields);
    }

    /**
     * 删除签署区
     * @param flowId 流程id
     * @param signfieldIds 签署区id列表，逗号分割
     * @return
     */
    public static DeleteSignFields deleteSignFields(String flowId, String signfieldIds){
        return new DeleteSignFields(flowId, signfieldIds);
    }

    /**
     * 流程签署人列表
     * @param flowId 流程id
     * @return
     */
    public static QrySigners qrySigners(String flowId){
        return new QrySigners(flowId);
    }

    /**
     * 流程签署人催签
     * @param flowId 流程id
     * @return
     */
    public static RushSign rushSign(String flowId){
        return new RushSign(flowId);
    }

    /**
     * 平台方&平台用户文本签
     * @param data 待签文本
     * @param type 待签文本类型：
     * @return
     */
    public static DataSign dataSign(String data, String type){
        return new DataSign(data, type);
    }

    /**
     *
     * @param data 文本签原文
     * @param signResult 文本签返回的签署结果
     * @return
     */
    public static DataVerify dataVerify(String data, String signResult){
        return new DataVerify(data, signResult);
    }

    /**
     * PDF文件验签
     * @param fileId 文件id
     * @return
     */
    public static PdfVerify pdfVerify(String fileId){
        return new PdfVerify(fileId);
    }

    /**
     * 查询签署文件上链信息
     * @param flowId 签署流程ID
     * @return
     */
    public static QrySignAntPushInfo qrySignAntPushInfo(String flowId){
        return new QrySignAntPushInfo(flowId);
    }

    /**
     * 核验签署文件上链信息
     * @param docHash 签署文件的哈希值
     * @param antTxHash 蚂蚁区块链统一证据编号
     * @return
     */
    public static CheckAntfinNotary checkAntfinNotary(String docHash, String antTxHash){
        return new CheckAntfinNotary(docHash, antTxHash);
    }

    /**
     * 搜索关键字坐标
     * @param fileId 文档id
     * @param keywords 关键字列表，逗号分割
     * @return
     */
    public static SearchWordsPosition searchWordsPosition(String fileId, String keywords){
        return new SearchWordsPosition(fileId, keywords);
    }
}
