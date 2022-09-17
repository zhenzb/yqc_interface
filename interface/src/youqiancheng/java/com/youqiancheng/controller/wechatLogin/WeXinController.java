package com.youqiancheng.controller.wechatLogin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.config.redis.CacheUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.socket.WebSocket;
import com.handongkeji.util.manager.JwtUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.tencentyun.TLSSigAPIv2;
import com.youqiancheng.controller.app.AppLoginController;
import com.youqiancheng.controller.wechatpay.weixinpay.config.WexinPayConfig;
import com.youqiancheng.form.app.UserInfoForm;
import com.youqiancheng.form.client.UserWechatForm;
import com.youqiancheng.initdata.UserEnity;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.util.HttpClientUtils;
import com.youqiancheng.vo.app.B01UserAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第三方微信登录
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
@RestController
@Api(tags = {"微信登录"})
@RequestMapping("/wechatLogin")
public class WeXinController {
	//联系客服的应用 SDKAppID,可在即时通信 IM 控制台 的应用卡片中获取。
	@Value("${contactCustomerApp.sdkappid}")
	private Long sdkappid;
	//  联系客服密钥信息
	@Value("${contactCustomerApp.key}")
	private  String key;

	private static Map<String,UserInfoDto> map=new ConcurrentHashMap<>();
	//过期时间
	private static final long expiredTime=1000*10*60;
	//微信公众平台申请
	//应用唯一标识，在微信开放平台提交应用审核通过后获得 appID
	//应用密钥AppSecret，在微信开放平台提交应用审核通过后获得 appSecret
	//TpAccesstoken 用来保存微信返回的用户信息oppid等

	@Resource
	private B01UserDao b01UserDao;
	@Resource
	private WebSocket webSocket;

	@Autowired
    private AppLoginController appLoginController;

	@ApiOperation(value = "PC用户获取重定向地址——微信二维码")
	@GetMapping("/PC/Login")
	public ResultVo wxlogin(String state) {
		if(StringUtils.isBlank(state)){
			return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"state不能为空");
		}
		String appID = WexinLoginConfig.getWx_appid();
		String http = WexinLoginConfig.getWx_login_notify();
		// 第一步：用户同意授权，获取code
		String url = "https://open.weixin.qq.com/connect/qrconnect?" +
				"appid=" + appID +
				"&redirect_uri="+http+
				"&response_type=code" +
				"&scope=snsapi_login" +
				"&state="+state+"#wechat_redirect";
		return ResultVOUtils.success(url);
	}

	@ApiOperation(value = "PC二维码回调函数——返回用户信息")
	@GetMapping("/PC/wxcallback")
	public void wxcallback(String code, String state) throws IOException {
		//微信二维码扫描后回调地址
		String appID = WexinLoginConfig.getWx_appid();
		String appSecret = WexinLoginConfig.getWx_appsecret();
		//通过code换取网页授权access_token
		//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appID +
				"&secret=" + appSecret +
				"&code=" + code +
				"&grant_type=authorization_code";
		com.alibaba.fastjson.JSONObject jsonObject = HttpClientUtils.doGet(url);

		String openId = jsonObject.getString("openid");
		String access_Token = jsonObject.getString("access_token");

		System.out.println("access_Token"+access_Token);

		// 拉取用户信息(需scope为 snsapi_userinfo)
		UserWechatForm userInfo = getUserInfo(access_Token, openId);
		//查询微信对应用户是否存在，存在则更新用户信息然后返回带token的用户信息
		//否则返回微信用户信息
		List<B01UserDO> b01UserDOS = b01UserDao.selectList(
				new EntityWrapper<B01UserDO>()
				.eq("weChat_openId",openId)
				.eq("app_type", TypeConstant.LoginType.wechat.getCode())
		);
		if(!CollectionUtils.isEmpty(b01UserDOS)){
			B01UserDO b01UserDO = b01UserDOS.get(0);
			//更新用户信息
			b01UserDO.setNick(userInfo.getNikeName());
			b01UserDO.setPic(userInfo.getHeadimgurl());
			b01UserDO.setSex(userInfo.getSex());
			b01UserDO.setWechatOpenid(userInfo.getOpenid());
			b01UserDao.updateById(b01UserDO);
			//返回用户信息带授权信息
			B01UserAppVO vo=new B01UserAppVO();
			BeanUtils.copyProperties(b01UserDO,vo);
			Map<String, Object> claims = new HashMap<>();
			claims.put("pc_user_id", vo.getId());
			String token = JwtUtils.createToken(claims, null);
			vo.setToken(token);
			vo.setTokenType(TypeConstant.PlatformType.pc.getCode());
			//获取用户的id,来生成UserSig
			String contactCustomer = getContactCustomer(b01UserDO.getId());
			vo.setUserSig(contactCustomer);
			vo.setTengXunImId("youqianchengjin_" +vo.getId());
			UserEnity user=new UserEnity();
			user.setToken(token);
			user.setTime(LocalDateTime.now());
			SecurityUtils.setSession("pc_user_id_"+vo.getId(),user);
			// webSocket.sendObjMessage(state,vo);
			//webSocket.onClose();
			UserInfoDto dto=new UserInfoDto();
			dto.setEntity(vo);
			dto.setTime(LocalDateTime.now());
			dto.setFlag(2);//已注册
			map.put(state,dto);
			return;
			//return ResultVOUtils.success(vo);
		}else{
			UserInfoDto dto=new UserInfoDto();
			dto.setEntity(userInfo);
			dto.setTime(LocalDateTime.now());
			dto.setFlag(1);//未注册
			map.put(state,dto);
			return;
		}
		// 返回微信用户信息
		//webSocket.sendObjMessage(state,userInfo);
		//return ResultVOUtils.success(userInfo);
	}


	@ApiOperation(value = "PC轮询查找用户信息")
	@GetMapping("/PC/getUserByState")
	public ResultVo getUserByState(String state){
		cleanMap();
		if(map.containsKey(state)){
			return  ResultVOUtils.success(map.get(state));
		}else{
			return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"用户信息不存在或已过期");
		}
	}

//
//	/**
//	 * 获取accessToken,该步骤返回的accessToken期限为一个月
//	 *继续获取用户信息
//	 * @param code
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("all")
//	@ApiOperation(value = "APP查找用户信息")
//	@GetMapping("app/weixincallback")
//	public ResultVo getAccessToken(String code) throws Exception {
//		//app获取用户信息
//		//1、根据code 获取授权信息
//		JSONObject userInfo=null;
//		String appID = WexinPayConfig.getWx_appid();
//		String appSecret = WexinPayConfig.getWx_appsecret();
//		String accesstoken;
//		String openid = null;
//		String refreshtoken;
//		int expiresIn;
//		String unionid;//可通过获取用户基本信息中的unionid来区分用户的唯一性，因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号，
//		//用户的unionid是唯一的。换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。
//		if (code != null) {
//			System.out.println(code);
//		}
//		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appID+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
//		URI uri = URI.create(url);
//		org.apache.http.client.HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(uri);
//		HttpResponse response;
//		try {
//			response = client.execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				HttpEntity entity = response.getEntity();
//
//				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
//				StringBuilder sb = new StringBuilder();
//
//				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
//					sb.append(temp);
//				}
//				JSONObject object = new JSONObject(sb.toString().trim());
//				System.out.println("object:"+object);
//				accesstoken = object.getString("access_token");
//				System.out.println("accesstoken:"+accesstoken);
//				openid = object.getString("openid");
//				System.out.println("openid:"+openid);
//				refreshtoken = object.getString("refresh_token");
//				System.out.println("refreshtoken:"+refreshtoken);
//				expiresIn = (int) object.getLong("expires_in");
//				unionid = object.getString("unionid");
//				// 根据授权信息获取用户信息
//				UserWechatForm userInfo1 = getUserInfo(accesstoken, openid);
//				//根据微信openID查询用户是否存在；粗存在则更新用户信息，返回带token的用户信息
//				//否则返回微信用户信息
//				List<B01UserDO> b01UserDOS = b01UserDao.selectList(
//						new EntityWrapper<B01UserDO>()
//								.eq("weChat_openId",openid)
//				);
//				if(!CollectionUtils.isEmpty(b01UserDOS)){
//					B01UserDO b01UserDO = b01UserDOS.get(0);
//					//更新用户信息
//					b01UserDO.setNick(userInfo1.getNikeName());
//					b01UserDO.setPic(userInfo1.getHeadimgurl());
//					b01UserDO.setSex(userInfo1.getSex());
//					b01UserDO.setWechatOpenid(userInfo1.getOpenid());
//					b01UserDao.updateById(b01UserDO);
//					//设置登录token
//					B01UserAppVO vo=new B01UserAppVO();
//					BeanUtils.copyProperties(b01UserDO,vo);
//					Map<String, Object> claims = new HashMap<>();
//					claims.put("app_user_id", vo.getId());
//					String token = JwtUtils.createToken(claims, 60*60*24*3L);
//					vo.setToken(token);
//					vo.setTokenType(TypeConstant.PlatformType.app.getCode());
//					UserInfoForm form=new UserInfoForm();
//					form.setEntity(vo);
//					form.setFlag(2);//已经绑定
//					return ResultVOUtils.success(form);
//				}else{
//					UserInfoForm form=new UserInfoForm();
//					form.setEntity(userInfo1);
//					form.setFlag(1);//未绑定
//					return ResultVOUtils.success(form);
//				}
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"请求用户信息失败");
//		//return R.ok().put("openid", openid);
//	}
//

	/**
	 * 通过微信openIDh获取信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "APP通过OpenId查找用户信息：如果存在则返回用户信息，否则需要注册")
	@GetMapping("app/getUserByOpenId")
	public ResultVo getUserByOpenId(String openid) throws Exception {

		//根据微信openID查询用户是否存在；粗存在则返回带token的用户信息
		//否则返回未绑定标记
		List<B01UserDO> b01UserDOS = b01UserDao.selectList(
				new EntityWrapper<B01UserDO>()
						.eq("weChat_openId",openid)
						.eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode())
		);
		if(!CollectionUtils.isEmpty(b01UserDOS)){
			B01UserDO b01UserDO = b01UserDOS.get(0);
			//设置登录token
			B01UserAppVO vo=new B01UserAppVO();
			BeanUtils.copyProperties(b01UserDO,vo);
			Map<String, Object> claims = new HashMap<>();
			claims.put("app_user_id", vo.getId());
			String token = JwtUtils.createToken(claims, 60*60*24*3L);
			vo.setToken(token);
			vo.setTokenType(TypeConstant.PlatformType.app.getCode());
			//保存token 用于验证异地登录
			CacheUtils.set(vo.getId()+"token", token);
			UserInfoForm form=new UserInfoForm();
			form.setEntity(vo);
			form.setFlag(2);//已经绑定
			String contactCustomer = appLoginController.getContactCustomer(b01UserDO.getId());
			form.setUserSig(contactCustomer);
			form.setTengXunImId("youqianchengjin_"+b01UserDO.getId());
			return ResultVOUtils.success(form);
		}else{
			UserInfoForm form=new UserInfoForm();
			form.setEntity(null);
			form.setFlag(1);//未绑定
			return ResultVOUtils.success(form);
		}
	}
		/*
		 *
		 * 1 { 2 "access_token":"ACCESS_TOKEN", 3 "expires_in":7200, 4
		 * "refresh_token":"REFRESH_TOKEN", 5 "openid":"OPENID", 6 "scope":"SCOPE", 7
		 * "unionid":"o6_bmasdasdsad6_2sgVt7hMZOPfL" 8 } 复制代码 复制代码 参数 说明 access_token
		 * 接口调用凭证 expires_in access_token 接口调用凭证超时时间，单位（秒） refresh_token
		 * 用户刷新access_token openid 授权用户唯一标识 scope 用户授权的作用域，使用逗号（,）分隔 unionid
		 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
		 *
		 */


//	/**
//	 * 刷新token
//	 *
//	 * @param
//	 * @return
//	 */
//	@SuppressWarnings({ "unused", "resource" })
//	private void refreshAccessToken(String openid) {
//		String refreshtoken=null;
//		//TpAccesstoken tpAccesstoken=new TpAccesstoken();
//		String appID = WexinPayConfig.getWx_appid();
//		String appSecret = WexinPayConfig.getWx_appsecret();
////		TpAccesstokenExample example = new TpAccesstokenExample();
////		example.createCriteria().andOpenidEqualTo(openid);
////		List<TpAccesstoken> list = tpAccesstokenMapper.selectByExample(example);
////		if(list!=null&&list.size()>0) {
////			 tpAccesstoken = list.get(0);
////			 refreshtoken = tpAccesstoken.getRefreshtoken();
////		}
//
//		String uri = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appID+"&grant_type=refresh_token&refresh_token="+refreshtoken;
//		org.apache.http.client.HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(URI.create(uri));
//		try {
//			HttpResponse response = client.execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//				StringBuilder builder = new StringBuilder();
//				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
//					builder.append(temp);
//				}
//				JSONObject object = new JSONObject(builder.toString().trim());
//				String	accessToken = object.getString("access_token");
//				String    refreshToken = object.getString("refresh_token");
//				openid = object.getString("openid");
//				int   expires_in = (int) object.getLong("expires_in");
////				tpAccesstoken.setAccesstoken(accessToken);
////				tpAccesstoken.setExpiresIn(expires_in);
////				tpAccesstoken.setOpenid(openid);
////				tpAccesstoken.setRefreshtoken(refreshToken);
////				tpAccesstokenService.save(tpAccesstoken);
//			}
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
	/**
	 * 根据accessToken获取用户信息
	 *
	 * @param accessToken
	 * @param openID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "resource" })
	public UserWechatForm getUserInfo(String accessToken, String openID){
		//根据授权信息，返回用户信息
		String appID = WexinPayConfig.getWx_appid();
		String appSecret = WexinPayConfig.getWx_appsecret();
		String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openID;
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(URI.create(uri));
		UserWechatForm form=new UserWechatForm();
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					builder.append(temp);
				}
				JSONObject object = new JSONObject(builder.toString().trim());
				form.setCountry(object.getString("country"));
				form.setNikeName( object.getString("nickname"));
				form.setUnionid(object.getString("unionid"));
				form.setProvince(object.getString("province"));
				form.setCity(object.getString("city"));
				form.setOpenid(object.getString("openid"));
				form.setSex( object.getInt("sex"));
				form.setHeadimgurl( object.getString("headimgurl"));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return form;
	}
//
//	@RequestMapping("/isaccesstoken")
//	@SuppressWarnings({ "resource" })
//	private boolean isAccessTokenIsInvalid(String accessToken,String openID) {
//        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openID;
//        URI uri = URI.create(url);
//        org.apache.http.client.HttpClient client = new DefaultHttpClient();
//        HttpGet get = new HttpGet(uri);
//        HttpResponse response;
//        try {
//            response = client.execute(get);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                HttpEntity entity = response.getEntity();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
//                StringBuilder sb = new StringBuilder();
//
//                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
//                    sb.append(temp);
//                }
//                JSONObject object = new JSONObject(sb.toString().trim());
//             	 /* {
//                	"errcode":0,"errmsg":"ok"
//                	}
//                	错误的Json返回示例:
//                	{
//                	"errcode":40003,"errmsg":"invalid openid"
//                	}*/
//                int errorCode = object.getInt("errcode");
//                if (errorCode == 0) {
//                    return true;
//                }
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return false;
//
//    }

	//生成UserSig方法
	public String  getContactCustomer(Long id) {
		//用户的id
		String userId = String.valueOf(id);
		String  tengxunUser =  "youqianchengjin_" +userId;
		//过期时间
		long expire= 60*60*24*7;
		TLSSigAPIv2 api = new TLSSigAPIv2(sdkappid,key);
		System.out.print(api.genSig(tengxunUser, expire));
		return api.genSig(tengxunUser, expire);


	}

	/**
	 *
	 清理集合中过期的验证码
	 *@return ()
	 *@throws
	 *@author yutf
	 *@date 2020/5/23
	 *
	 */
	public static void cleanMap() {
		//如果集合不为空
		if(!map.isEmpty()){
			//循环集合——如果当前时间减去验证码创建时间大于过期时间则清除验证码
			for (Map.Entry<String, UserInfoDto> stringUserInfoDtoEntry : map.entrySet()) {
				Duration duration = java.time.Duration.between(stringUserInfoDtoEntry.getValue().getTime(), LocalDateTime.now() );
				if(duration.toMillis()>expiredTime){
					map.remove(stringUserInfoDtoEntry.getKey());
				}
			}
		}
	}
}
