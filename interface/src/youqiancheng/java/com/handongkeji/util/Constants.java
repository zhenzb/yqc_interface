package com.handongkeji.util;

import java.text.DecimalFormat;

public final class Constants {
	// 错误返回值
	public final static int Error = -2;
	// 参数为空返回值
	public final static int $Null = -1;
	// 失败返回值
	public final static int $Failure = -4;
	// 成功返回值
	public final static int $Success = 1;

	public final static int NOTEXTENT = -3;

	public final static int NOTEXIST = 404;

	public final static int $BadRequest = 400;

	public final static int $BadPARAMETERS = 401;

	public final static int $NULLPARAMETERS = 402;

	// 主页

	public static final int TOKEN_INVALID_CODE = 403;

	public static final int CODE_SENT_OUTIN = 201;

	public static final int CODE_COMMENT_NULL = 201;

	// 消息推送
	public static final int CODE_NO_UNREAD = 100;

	public final static String MSG_NO_UNREAD = "暂无未读消息!";

	public final static int CODE_SIGN_SIGNED = 210;
	public final static int CODE_SIGN_NOSIGNED = 211;

	// 主页

	public final static int $QUERY_FAILS = 403;

	// 店铺
	public final static String INSERT_REVIEW_FAILED = "添加评论失败！";

	public final static String COMMENT_NULL_MSG = "帖子暂无评论!";

	public final static String COUPON_OUTIN_MSG = "优惠券已派完!";

	public final static String QUERY_FAILS_MSG = "查询失败!";

	public final static String LOGIN_SUCCESS_MESSAGE = "登录成功!";

	public final static String LOGIN_FAILURE_MESSAGE = "密码错误!";

	public final static String LOGIN_NULL_MESSAGE = "手机号或者密码不能为空!";

	public final static String EXECUTION_FAILURE_MESSAGE = "操作失败!";

	public final static String EXECUTION_SUCCESS_MESSAGE = "操作成功!";

	public final static String EXECUTION_EXIST_MESSAGE = "记录已存在!";

	public final static String EXECUTION_GET_MESAGE = "数据获取失败!";

	public final static String ERROR_PARAMETERS_MESSAGE = "数据格式错误!";

	public final static String ERROR_SUBMIT_MESSAGE = "提交方式不正确!";

	public final static String NULL_PARAMETERS_MESSAGE = "参数不能为空!";

	public final static String MOBILE_ERROR_MESSAGE = "手机号输入错误!";

	public final static String PASS_ERROR_MESSAGE = "密码错误!";

	public final static String OLDPASS_ERROR_MESSAGE = "原始密码错误!";

	public final static String PASS_NULL_MESSAGE = "密码为空!";

	public final static String BAD_PARAMETERS_MESSAGE = "参数错误!";

	public final static String REQUEST_FAITURE_MESSAGE = "请求失败!";

	public final static String MOBILE_EXISTENCE_MESSAGE = "手机号已存在!";

	public final static String USERNAMEISHAS = "用户名已存在!";

	public final static String OPENID_EXISTENCE_MESSAGE = "第三方帐号已存在!";

	public final static String MOBILE_NULL_MESSAGE = "此号码未注册";

	public final static String REGIST_SUSSESS_MESAGE = "注册成功!";

	public final static String REGIST_ERROR_MESSAGE = "注册失败!";

	public final static String ERROR_CODE_MESSAGE = "手机号或验证码错误!";

	public final static String ERROR_TOKEN_MESSAGE = "Token解析失败!";

	public final static String OVERTIME_TOKEN_MESSAGE = "登录信息已失效 请重新登录";

	public final static String SHOPNAME_EXISTENCE_MESSAGE = "店铺名称已存在!";
	public final static String SHOPNAME_EXISTENCE_FROZEN = "店铺/推广已被冻结!";


	public final static String PRODUCTNAME_EXISTENCE_MESSAGE = "商品名称已存在!";

	public final static String COUPONNO_EXISTENCE_MESSAGE = "优惠券编号不存在!";

	public final static String GROUPON_NULL_MESSAGE = "尚未领取优惠券!";

	public final static String ERROR_LOGIN_MESSAGE = "用户名或密码错误!";

	public final static String EXECUTION_SIGN_SIGNED = "今日已签到!";

	public final static String EXECUTION_SIGN_NOSIGNED = "今日未签到!";

	public final static String USERADDRSDUO = "用户地址不能超过10条!";

	public final static String MBNAVSIMPLEHAS = "该功能已添加!";

	// 权限常量
	public final static int $NOSUCH_JURISDICTION = 607;
	public final static String EXECUTION_NOSUCH_JURISDICTION = "无权操作!";

	// 验证码常量
	public final static int $CODE_ERROR = 343;
	public final static String CODE_NULL_MESAGE = "验证码为空!";
	public final static String CODE_ERROR_MESSAGE = "验证码错误!";
	public final static String CODE_OUTTIME_MESSAGE = "验证码超时!";
	// 环信常量
	// API_HTTP_SCHEMA
		public static String API_HTTP_SCHEMA = "https";
	//	// API_SERVER_HOST
		public static String API_SERVER_HOST = PropertiesUtils.getProperties().getProperty("API_SERVER_HOST");
	//	// APPKEY
		public static String APPKEY = PropertiesUtils.getProperties().getProperty("APPKEY");
	//	// APP_CLIENT_ID
		public static String APP_CLIENT_ID = PropertiesUtils.getProperties().getProperty("APP_CLIENT_ID");
	//	// APP_CLIENT_SECRET
		public static String APP_CLIENT_SECRET = PropertiesUtils.getProperties().getProperty("APP_CLIENT_SECRET");
	//	// DEFAULT_PASSWORD
   //	public static String DEFAULT_PASSWORD = "123456";
	// 环信常量结束

	// 返回空常量
	public static final Integer $EMPTY_LIST = 60;
	public static final String EMPTY_LIST_MSG = "没有更多！";

	// 好友常量
	public static final Integer $ERROR_FRIEND = 65;
	public static final Integer $NOT_EMPTY_LIST= 66;
	public static final String ERROR_FRIEND_MSG = "无法获取好友列表！";
	public static final String NOT_MYSELF = "不能够关注自己";
	public static final String ME_NOT_ATTENTION  = "自己没有关注该用户";
	public static final String IS_ATTENTION = "已经关注过该用户";
	public static final String NOT_ATTENTION = "您还没有关注任何人快去发现感兴趣的人吧";
	public static final String NOT_ONE_SOCIA ="暂无动态";
	// token
	public final static Integer $BAD_TOKEN = -999;
	public final static String PARSE_TOKEN_FAILED = "解析Token失败!";
	public final static String TOKEN_INVALID_MSG = "Token无效!";

	// 帖子常量
	public static final String POST_NOTEXIST_MSG = "帖子不存在！";
	public static Integer $POST_NOTEXIST_CODE = 401;
	public static String SYSCONFIG__ERROR_MSG ="Properties属性配置错误";
	// 订单
	public static final String ORDER_NOTDEL = "该订单无法删除";
	public static final String ORDER_ISDEL = "订单已经被删除";
	public static final String ORDER_NOTEXIST_MSG = "订单不存在！";

	// 实惠
	public static final String BENEFIT_USEOUT_MSG = "实惠已经抢完！";
	public static final String BENEFIT_LIMITONE_MSG = "今天已经领取！";

	public final static int BENEFIT_USEOUT_CODE = 201;
	public final static int BENEFIT_LIMITONE_CODE = 202;
	// 发布
	public static final String NEED_NOTDEL = "该发布无法删除";
	public static final String NEED_NOTEXIST_MSG = "发布不存在！";
	public static final String NEED_ISDEL = "发布已经被删除";

	// 酬金表
	public static final String DATETIME_ERROR_MSG = "时间参数格式错误！";
	public final static int DATETIME_ERROR_CODE = 201;
	//优惠券
	public static final String NODATA_MSG = "无可用优惠券！";
	public final static int NODATA_CODE = 405;

	//活动
	public static final String REGIST_ISEXIST = "已经报名";
	public static final String REGIST_NOTEXIST = "未报名";
	public static final String COLLECTION_ISEXIST = "已经收藏";
	public static final String NOT_COLLECTION = "未收藏";
	public static final String ZAMBIA_ISEXIST = "已经点赞";
	public static final String NOT_ZAMBIA = "未点赞";
	public static final String ACTIVITY_END = "活动报名已截止";
	//上传文件
	public static final String UNSUPPORT_FILEFORMAT_MSG = "不支持的文件格式";
	public static final Integer UNSUPPORT_FILEFORMAT_CODE = 407;
	public static final int LARGE_WIDTH = 800;
	public static final int LARGE_HEIGHT = 800;
	public static final int MEDIUM_WIDTH = 500;
	public static final int MEDIUM_HEIGHT =500;
	public static final int SMALL_WIDTH = 200;
	public static final int SMALL_HEIGHT =200;

	//友盟推送

		public static final String ALIAS_TYPE = "jjyl_type";
		public static final String ALIAS_PREFIX =  "jjyl";
		public static final String ALIAS_TICKER = "小康生活";
		public static final String ALIAS_TITLE = "小康生活消息";
		public static final String PRODUCTION_MODE = "true";
		public static final String IOS_PRODUCTION_MODE = "false";
		public static final Integer REPEAT_LOGIN_CODE = 602;
		public static final String REPEAT_LOGIN_MSG = "账号已在其他设备登录！";
		public static final String UMENG_IOS_SOUND = "tuisong.wav";
		//仿真数据 order 范围
		public static final Long PRESETDATA_MIN = 0L;
		public static final Long PRESETDATA_MAX = 200L;
		public static final int PRESETDATA_DELAY_DAYS = 30;

		public static final DecimalFormat    decimalFormat = new DecimalFormat("######0.00");
		public static final Integer TRAIN_EXIST_CODE = 501;
		public static final String TRAIN_EXIST_MSG = "机构名称已存在";

		public static final String IS_SOCIA = "有新动态";
		public static final String NOT_SOCIA = "没有有新动态";
		public static final String INVITATION_SOURCE_FACTORY = "1QAZXSW23EDCVFR45TGBNHY67UJMKI89OLP0";

		//问诊
		public static final String ASK_IS_APPORT = "问题已有专家解答";
		public static final String USERNAMEORPWD_ERROR_MESSAGE = "用户名或密码错误";
		public static final String CERT_FAILURE_MESSAGE = "认证不通过，无权进行此操作";
		public static final Integer CERT_FAILURE_CODE = 501;
		public static final String TIPS_PARAMS_MESSAGE = " One or more parameters is null.Please check parameter(s): ";
		public static final String DEFAULT_PASSWORD = "123456";
		public static final Integer CANNOT_ADD_YOUSELF = 203;
		public static final String CANNOT_ADD_YOUSELF_MESSAGE = "不能添加自己为好友";
		public static final Integer SYSCONFIG__ERROR_CODE = 1001;

}
