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
 * 〈单据类型枚举〉
 *
 * @author ytf
 * @create 2019/7/16
 * @since 1.0.0
 */
public enum TypeConstant {

    Order("DD","订单"),
    order_shop("DDS","商家_订单"),
    withdrawal_application("WA","提现申请"),
    face_pay("MM","在线支付"),
    CustomerService("SH","售后");
    private String code;
    private String msg;

    private TypeConstant(String code, String msg){
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
     * 〈短息你验证码类型编码〉
     #  idCardCode: SMS_187595674   #身份验证验证码
     #  LoginCode: SMS_187595673    #登录确认验证码
     #  LoginExceptionCode: SMS_187595672   #登录异常验证码
     #  userRegisterCode: SMS_187595671   #用户注册验证码
     #  updatePwdCode: SMS_187595670   #修改密码验证码
     #  changeInfoCode: SMS_187595669   #信息变更验证码
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum ShortMessageType {

        idCardCode("SMS_187595674","身份验证验证码"),
        loginCode("SMS_187595673","登录确认验证码"),
        loginExceptionCode("SMS_187595672","登录异常验证码"),
        userRegisterCode("SMS_187595671","用户注册验证码"),
        updatePwdCode("SMS_187595670","修改密码验证码"),
        changeInfoCode("SMS_187595669","信息变更验证码"),
        checkbusinessUnCode("SMS_197896065","店铺审核未通过通知"),
        confirmReceiveUnCode("SMS_227743593","确认收货短信通知"),
        checkbusinessCode("SMS_197891801","店铺审核通过通知"),
        withdrawalNoticeCode("SMS_222335227","提现审核通知");
        private String code;
        private String msg;

        private ShortMessageType(String code, String msg){
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
    /**
     * 功能描述: <br>
     * 〈平台类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum PlatformType {

        pc("PC","商城"),
        app("APP","移动端");
        private String code;
        private String msg;

        private PlatformType(String code, String msg){
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
    /**
     * 功能描述: <br>
     * 〈登录类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum LoginType {
        phone(1,"手机登录"),
        wechat(2,"微信登录"),
        apple(3,"苹果登录");
        private int code;
        private String msg;

        private LoginType(int code, String msg){
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
     * 功能描述: <br>
     * 〈登录类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum SexType {

        male(1,"男"),
        female(2,"女"),
        unknown(3,"未知");
        private int code;
        private String msg;

        private SexType(int code, String msg){
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
     * 功能描述: <br>
     * 〈轮播图类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum CarouselPicType {

        home_page(1,"首页"),
        nearby(2,"附近");
        private int code;
        private String msg;

        private CarouselPicType(int code, String msg){
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
     * 功能描述: <br>
     * 〈商品轮播图对应商品类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum CoodsPicType {

        goods(1,"商品"),
        publicity_video(2,"宣传_视频"),
        publicity_audio(3,"宣传_音频");
        private int code;
        private String msg;

        private CoodsPicType(int code, String msg){
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
     * 功能描述: <br>
     * 〈商家账户流水类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum ShopAccountType {

        sale_income(1,"销售收入"),
        withdrawal_pay(2,"提现支出"),
        reward_pay(3,"打赏收入");
        private int code;
        private String msg;

        private ShopAccountType(int code, String msg){
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
     * 功能描述: <br>
     * 〈商家角色类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum ShopRoleType {

        admin(1,"超级管理员"),
        goods_manager(2,"商品管理员"),
        publicity_manager(3,"动态管理员"),
        room_manager(4,"房间管理员");
        private int code;
        private String msg;

        private ShopRoleType(int code, String msg){
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
     * 功能描述: <br>
     * 〈商家账户流水类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum UserAccountType {

        redPacket_income(1,"红包收入"),
        buy_pay(2,"购物支出"),
        refund_incone(3,"退款收入"),
        reward_flow_add(4,"打赏收入"),
        reward_flow_sub(5,"打赏支出");
        private int code;
        private String msg;

        private UserAccountType(int code, String msg){
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
     * 功能描述: <br>
     * 〈消息读取类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum MessageReadType {

        buyer(1,"买家"),
        business(2,"商家");
        private int code;
        private String msg;

        private MessageReadType(int code, String msg){
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
     * 功能描述: <br>
     * 〈是否为商家〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum isShop {

        no(1,"否"),
        yes(2,"是");
        private int code;
        private String msg;

        private isShop(int code, String msg){
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
     * 功能描述: <br>
     * 〈是否展示〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum isShow {

        no(1,"否"),
        yes(2,"是");
        private int code;
        private String msg;

        private isShow(int code, String msg){
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
     * 功能描述: <br>
     * 〈支付回调类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum NotifyType {

        ali(1,"支付宝回调"),
        wechat(2,"微信回调");
        private int code;
        private String msg;

        private NotifyType(int code, String msg){
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
     * 功能描述: <br>
     * 〈错误类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum ErrorType {

        add_inventory(1,"库存归还"),
        update_pay(2,"支付状态修改");
        private int code;
        private String msg;

        private ErrorType(int code, String msg){
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
     * 功能描述: <br>
     * 〈支付对象类型〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum PayObjectType {

        user(1,"用户支付——下订单"),
        shop(2,"卖家家支付——发红包"),
        reward(3,"用户支付——打赏");
        private int code;
        private String msg;

        private PayObjectType(int code, String msg){
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
     * 功能描述: <br>
     * 〈提现方式〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum WithdrawalType {

        zhifubao(1,"支付宝"),
        wechat(2,"微信");
        private int code;
        private String msg;

        private WithdrawalType(int code, String msg){
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
     * 功能描述: <br>
     * 〈国家〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    public enum country {

        china(1,"中国");
        private int code;
        private String msg;

        private country(int code, String msg){
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
}
