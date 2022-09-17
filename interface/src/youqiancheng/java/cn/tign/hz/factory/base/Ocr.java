package cn.tign.hz.factory.base;

import cn.tign.hz.factory.ocr.*;

/**
 * 实名认证OCR接口功能类
 * @author  澄泓
 * @date  2020/11/3 16:09
 * @version JDK1.7
 */
public class Ocr {

    /**
     * 身份证OCR
     * @param infoImg  身份证信息面图片BASE64字符串
     * @return
     */
    public static Idcard idcard(String infoImg){
        return new Idcard(infoImg);
    }

    /**
     * 银行卡OCR
     * @param img 银行卡正面照照片base64数据
     * @return
     */
    public static Bankcard bankcard(String img){
        return new Bankcard(img);
    }

    /**
     * 营业执照OCR
     * @param img 营业执照图片BASE64字符串
     * @return
     */
    public static License license(String img){
        return new License(img);
    }

    /**
     * 驾驶证OCR
     * @param image 驾驶证图片BASE64字符串
     * @return
     */
    public static DrivingLicence drivingLicence(String image){
        return new DrivingLicence(image);
    }

    /**
     * 行驶证OCR
     * @param image 行驶证图片BASE64字符串
     * @return
     */
    public static DrivingPermit drivingPermit(String image){
        return new DrivingPermit(image);
    }
}
