package com.handongkeji.util;

public final class Parameter {

	// 错误返回�?
	public final static int Error = -2;
	// 参数为空返回�?
	public final static int $Null = -1;
	// 失败返回�?
	public final static int $Failure = 0;
	// 成功返回�?
	public final static int $Success = 1;
	// 已经送过返回�?

	public final static int Sent = -2;
	public final static int NOTEXTENT = -3;
	public final static int NOTEXIST = 404;
	public final static int $BadRequest = 400;
	public final static int $BadPARAMETERS = 401;
	public final static int $NULLPARAMETERS = 402;

	public static final int TOKEN_INVALID_CODE = 403;
	public static final int CODE_SENT_OUTIN = 201;
	public static final int CODE_COMMENT_NULL = 201;

	public static final int CODE_NO_UNREAD = 100;
	public final static String MSG_NO_UNREAD = "暂无未读消息!";
	public final static int CODE_SIGN_SIGNED = 210;
	public final static int CODE_SIGN_NOSIGNED = 211;
	public final static int $QUERY_FAILS = 403;

	public final static String ENCRYPT_TOKEN_FAILED = "加密Token失败!";
	public final static String QUERY_FAILS_MSG = "查询失败!";
	public final static String LOGIN_SUCCESS_MESSAGE = "登录成功!";
	public final static String LOGIN_FAILURE_MESSAGE = "密码错误!";
	public final static String LOGIN_NULL_MESSAGE = "手机号或者密码不能为空?!";
	public final static String EXECUTION_FAILURE_MESSAGE = "操作失败!";
	public final static String EXECUTION_SUCCESS_MESSAGE = "操作成功!";
	public final static String EXECUTION_PHONEERROR_MESSAGE = "该手机号已注册?!请更换手机号注册!";
	public final static String EXECUTION_EXIST_MESSAGE = "记录已存在?!";
	public final static String NULL_PARAMETERS_MESSAGE = "参数不能为空!";
	public final static String BAD_PARAMETERS_MESSAGE = "参数错误!";
	public final static String REQUEST_FAITURE_MESSAGE = "请求失败!";
	public final static String ERROR_TOKEN_MESSAGE = "Token解析失败!";
	public final static String ERROR_TOKENPARAMETER_MESSAGE = "Token解析失败或参数为空!";
	public final static String TYPE_ERROR_MESSAGE = "类型转换异常!";
	public final static String INSERT_ERROR_MESSAGE = "插入数据异常!";
	public final static String UPDATE_ERROR_MESSAGE = "更新数据异常!";

	// 权限常量
	public final static int $NOSUCH_JURISDICTION = 607;
	public final static String EXECUTION_NOSUCH_JURISDICTION = "无权操作!";

	// 验证码常�?
	public final static int $CODE_ERROR = 343;
	public final static String CODE_NULL_MESAGE = "验证码为空?!";
	public final static String CODE_ERROR_MESSAGE = "验证码不正确!";
	public final static String CODE_OUTTIME_MESSAGE = "验证码超时?!";

	// 返回空常�?
	public static final Integer $EMPTY_LIST = 60;
	public static final String EMPTY_LIST_MSG = "没有更多�?";

	// token
	public final static Integer $BAD_TOKEN = -999;
	public final static String PARSE_TOKEN_FAILED = "解析Token失败!";
	public final static String TOKEN_INVALID_MSG = "Token无效!";

	public final static int BENEFIT_USEOUT_CODE = 201;
	public final static int BENEFIT_LIMITONE_CODE = 202;

	// 上传文件
	public static final String UNSUPPORT_FILEFORMAT_MSG = "不支持的文件格式";
	public static final Integer UNSUPPORT_FILEFORMAT_CODE = 407;
	public static final int LARGE_WIDTH = 800;
	public static final int LARGE_HEIGHT = 800;
	public static final int MEDIUM_WIDTH = 500;
	public static final int MEDIUM_HEIGHT = 500;
	public static final int SMALL_WIDTH = 200;
	public static final int SMALL_HEIGHT = 200;

	public static final String PRODUCTION_MODE = "true";
	public static final String IOS_PRODUCTION_MODE = "true";
	public static final Integer REPEAT_LOGIN_CODE = 602;
	public static final String REPEAT_LOGIN_MSG = "账号已在其他设备登录�?";
	public static final String UMENG_IOS_SOUND = "tuisong.wav";

	// 仿真数据 order 范围
	public static final Long PRESETDATA_MIN = 0L;
	public static final Long PRESETDATA_MAX = 200L;
	public static final int PRESETDATA_DELAY_DAYS = 30;

}
