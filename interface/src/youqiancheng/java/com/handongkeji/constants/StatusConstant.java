package com.handongkeji.constants;

/**
 * @Classname StatusConstant
 * @Date 2019/7/8 11:56
 * @Created by Ytf
 * ____           _
 * / ___| __ ___   _(_)_ __
 * | |  _ / _` \ \ / / | '_ \
 * | |_| | (_| |\ V /| | | | |
 * \____|\__,_| \_/ |_|_| |_|
 */
public enum StatusConstant {

    enable(1,"启用"),
    disable(2,"停用");

    private int code;
    private String msg;

    private StatusConstant(int num, String msg){
        this.code =num;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsgByCode(int code){
        for (StatusConstant value : StatusConstant.values()) {
            if(code==value.getCode()){
                return value.getMsg();
            }
        }
        return null;
    }

    /**
     * 功能描述: <br>
     * 〈删除标记〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
   public enum  DeleteFlag{
        un_delete(1,"未删除"),
        delete(2,"删除");
        private int code;
        private String msg;

        private DeleteFlag(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


       public static String getMsgByCode(int code){
           for (DeleteFlag value : DeleteFlag.values()) {
               if(code==value.getCode()){
                   return value.getMsg();
               }
           }
           return null;
       }
    }

    /**
     * 功能描述: <br>
     * 〈查询条件自动注入MAP标记〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum  CreatFlag{
        delete(1,"删除"),
        status(2,"状态"),
        statusAndDelete(3,"删除和状态");
        private int code;
        private String msg;

        private CreatFlag(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈已读标记〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsRead{
        un_read(1,"未读"),
        read(2,"已读");
        private int code;
        private String msg;

        private IsRead(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈发布状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  ReleaseStatus{
        un_release(1,"未发布"),
        release(2,"已发布");
        private int code;
        private String msg;

        private ReleaseStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     *消息类型
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
    public enum  MessageType{
        platform_msg(1,"平台消息"),
        service_msg(2,"系统消息"),
        activity_msg(3,"活动通知");
        private int code;
        private String msg;

        private MessageType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     *是否免费
     *@throws
     *
     */
    public enum  FreeStatus{
        yes(1,"免费"),
        no(2,"非免费");
        private int code;
        private String msg;

        private FreeStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     *商家类型
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
    public enum  ShopType{
        product(1,"商品"),
        propaganda(2,"企业宣传"),
        physical(3,"实体店"),
        person_propaganda(4,"个人宣传");
        private int code;
        private String msg;

        private ShopType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     *商家类型
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
    public enum  NewShopType{
        product(1,"商品"),
        propaganda(2,"宣传");
        private int code;
        private String msg;

        private NewShopType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     *审核状态
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
    public enum  ExamineStatus{
        un_examine(1,"未审核"),
        adopt(2,"通过"),
        refuse(3,"拒绝");
        private int code;
        private String msg;

        private ExamineStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     *商家图片类型
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
    public enum  ShopPicType{
        license(1,"营业执照"),
        id_card(2,"身份证"),
        other(3,"其他");
        private int code;
        private String msg;

        private ShopPicType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     *收藏类型
     *@throws
     *@author yutf
     *@date 2020/4/9
     *
     */
    public enum  CollectionType{
        goods(1,"商品"),
        shop(2,"店铺"),
        publicity(3,"动态/文章");
        private int code;
        private String msg;

        private CollectionType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     * 功能描述: <br>
     * 〈上架〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  SaleFlag{
        un_sale(1,"未上架"),
        on_sale(2,"上架"),
        off_sale(3,"下架");
        private int code;
        private String msg;

        private SaleFlag(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈宣传类型〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  PublicityType{
        video(1,"视频"),
        audio(2,"音频"),
        pic(3,"图文");
        private int code;
        private String msg;

        private PublicityType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈评论是否存在图片标记〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  HasPicFlag{
        no(1,"无"),
        yes(2,"有");
        private int code;
        private String msg;

        private HasPicFlag(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈广告类型〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  AdvertisementType{
        boot_page(1,"引导页广告"),
        nearby_page(2,"附近页面广告");
        private int code;
        private String msg;

        private AdvertisementType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }

    /**
     * 功能描述: <br>
     * 〈发货状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  DeliveryStatus{
        un_shipped(1,"未发货"),
        shipped(2,"已发货");
        private int code;
        private String msg;

        private DeliveryStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈是否评价〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsEvaluate{
        un_evaluated(1,"未评价"),
        evaluated(2,"已评价");
        private int code;
        private String msg;

        private IsEvaluate(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈发货状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  OrderStatus{
        un_pay(1,"待支付"),
        pay(2,"已支付/待发货"),
        send(3,"已发货/待收货"),
        end(4,"已收货/已完成"),
        apply_refund(5,"退货待审核"),
        pass(6,"通过/待退款"),
        refuse(7,"拒绝"),
        refund(8,"已退款"),
        cancel(9,"已取消");
        private int code;
        private String msg;

        private OrderStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈是否默认〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsDefault{
        no(1,"非默认"),
        yes(2,"默认");
        private int code;
        private String msg;

        private IsDefault(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈退款状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  RefundStatus{
        un_refund(1,"未退款"),
        refund(2,"已退款");
        private int code;
        private String msg;

        private RefundStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈退款类型〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  RefundType{
        refund(1,"退货"),
        exchange (2,"换货");
        private int code;
        private String msg;

        private RefundType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈认证〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  isAuthentication{
        un_authentication(1,"未认证"),
        authentication(2,"已认证");
        private int code;
        private String msg;

        private isAuthentication(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: <br>
     * 〈是否需要退货〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsRefund{
        no(1,"不需要退货"),
        yes(2,"需要");
        private int code;
        private String msg;

        private IsRefund(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }/**
     * 功能描述: <br>
     * 〈是否为商家〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsShop{
        no(1,"否"),
        yes(2,"是");
        private int code;
        private String msg;

        private IsShop(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static String getMsgByCode(int code){
            for (DeleteFlag value : DeleteFlag.values()) {
                if(code==value.getCode()){
                    return value.getMsg();
                }
            }
            return null;
        }
    }
    /**
     * 功能描述: 红包街
     * 〈是否免费〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsFree{
        no(1,"否"),
        yes(2,"是");
        private int code;
        private String msg;

        private IsFree(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述: 支付方式
     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  payType{
        AliPay(1,"支付宝"),
        Wechat(2,"微信"),
        Balance(3,"余额");
        private int code;
        private String msg;

        private payType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述: 打款方式
     * 〈打款类型〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  withdrawType{
        MANUAL(1,"手动打款"),
        AUTO(2,"自动打款");
        private int code;
        private String msg;

        private withdrawType(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈是否结束〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  EndFlag{
        un_start(1,"未开始"),
        un_end(2,"未结束"),
        end(3,"已结束");
        private int code;
        private String msg;

        private EndFlag(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈是否领取〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  IsReceive{
        un_receive(1,"未领取"),
        receive(2,"已领取");
        private int code;
        private String msg;

        private IsReceive(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈支付状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  PayStatus{
        un_pay(1,"未支付"),
        pay(2,"已支付"),
        fail(3,"支付失败，已退回");
        private int code;
        private String msg;

        private PayStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈售后状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  CustomServiceStatus{
        un_examine(1,"待审核"),
        pass(2,"已通过"),
        refuse(3,"已拒绝"),
        refund(4,"已退款");
        private int code;
        private String msg;

        private CustomServiceStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈转账状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  TransferStatus{
        un_transfer(1,"未转账"),
        transfer(2,"已转账");
        private int code;
        private String msg;

        private TransferStatus(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈转账状态〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  FrozenAccount{
        un_frozenAccount(1,"未冻结"),
        on_frozenAccount(2,"已冻结");
        private int code;
        private String msg;

        private FrozenAccount(int code, String msg){
            this.code =code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 功能描述:
     * 〈交易类型〉

     * @return:
     * @since: 1.0.0
     * @Author:yutf
     * @Date:
     */
    public enum  Ishide {
        un_hide(1, "不隐藏"),
        hide(2, "隐藏");
        private int code;
        private String msg;

        private Ishide(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
