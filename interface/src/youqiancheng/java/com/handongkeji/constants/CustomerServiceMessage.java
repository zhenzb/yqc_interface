/**
 * Copyright (C), 2015-2019, 撼动科技有限公司
 * FileName: TypeConstant
 * Author:   ytf
 * Date:     2019/7/16 16:59
 * Description: 单据类型枚举
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.handongkeji.constants;

/**
 * 功能：
 * 〈客服消息类型枚举〉
 *
 * @author ytf
 * @create 2019/7/16
 * @since 1.0.0
 */
public enum CustomerServiceMessage {

    TIMTextElem("TXT","文本消息"),
    TIMFaceElem("BQ","表情消息"),
    TIMLocationElem("WZ","位置消息"),
    TIMSoundElem("YY","语音消息"),
    TIMImageElem("TX","图形消息"),
    TIMVideoFileElem("SP","视频消息"),
    TIMCustomElem("ZTY","自定义信息");

    private String code;
    private String msg;

    private CustomerServiceMessage(String code, String msg){
        this.code =code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 功能描述: <br>
     * 〈消息同步不同步的类型编码〉
     #  From_Account (1,"消息同步至发送方"),
     #  From_Account(2,"消息不同步至 From_Account");
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum SyncOtherMachineType {

        From_Account (1,"消息同步至发送方"),
        UNFrom_Account(2,"消息不同步至 From_Account");

        private Integer code;
        private String msg;

        private SyncOtherMachineType(Integer code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


    }
    /**
     * 功能描述: 在线还是离线<br>
     * 〈平台类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum OfflinePushInfoType {

        Desc("LX","离线推送消息"),
        UNDesc("no","不离线发送消息");
        private String code;
        private String msg;

        private OfflinePushInfoType(String code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


    }





}
