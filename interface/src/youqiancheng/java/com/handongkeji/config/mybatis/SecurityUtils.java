package com.handongkeji.config.mybatis;

import com.youqiancheng.initdata.UserEnity;
import com.youqiancheng.mybatis.dao.system.A01AdminMapper;
import com.youqiancheng.mybatis.model.A01Admin;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring Security.工具
 */
public final class SecurityUtils {
	@Autowired
	private A01AdminMapper a01AdminMapper;
	private static Map<String, UserEnity> session=new ConcurrentHashMap<>();
	public  static Long  expiredTime=60*60*24*3L;
	private SecurityUtils() {
	}
	public static  void setSession(String key , UserEnity entity){
		clearSession();
		session.put(key,entity);
	}
	public  static void clearSession(){
		//如果集合不为空
		if(!session.isEmpty()){
			//循环集合
			for (Map.Entry<String, UserEnity> codeEntry : session.entrySet()) {
				//获取当前时间和创建时间的时间差
				Duration duration = Duration.between(codeEntry.getValue().getTime(), LocalDateTime.now());
				//如果时间差大于有效时间—即当前时间-创建时间>有效时间，则验证码过期，从集合中删除
				if(duration.toMillis() > expiredTime*1000){
					session.remove(codeEntry.getKey());
				}
			}
		}
	}

	public static UserEnity getSession(String key){
		clearSession();
		return session.get(key);
	}


	/**
	 * 获取当前用户
	 *
	 */
	public static A01Admin getCurrentUser() {
		A01Admin a01Admin= new A01Admin();
		try {
			HttpServletRequest request = SpringContextUtil.getHttpServletRequest();
			 a01Admin = (A01Admin) request.getAttribute("admin");
			if(a01Admin == null){
				return new A01Admin();
			}
		}catch (Exception e){

		}
		return  a01Admin;
	}

	/**
	 * @Date: 2020/4/17 18:24
	 * @Param:
	 * @return:
	 * @Description: 获取商家当前用户
	 */
	public static F08ShopUserDO getShopUser() {
		HttpServletRequest request = SpringContextUtil.getHttpServletRequest();
		F08ShopUserDO shopUser = (F08ShopUserDO) request.getAttribute("shopUser");
		if(shopUser == null){
			return null;
		}
		return  shopUser;
	}
	/**
	 * @Date: 2020/4/17 18:24
	 * @Param:
	 * @return:
	 * @Description: 获取session
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = SpringContextUtil.getHttpServletRequest();
		return   request.getSession();
	}

//	/**
//	 * 删除用户用户登录Session
//	 */
//	public void resetUserSession(String username){
//		final FindByIndexNameSessionRepository<? extends Session> sessionRepository = SpringContextUtil.getBean(FindByIndexNameSessionRepository.class);
//		// 查询用户的 Session 信息，返回值 key 为 sessionId
//		Map<String, ? extends Session> userSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
//		if(userSessions !=null) {
//			// 移除用户的 session 信息
//			List<String> sessionIds = new ArrayList<>(userSessions.keySet());
//			if (!sessionIds.isEmpty() && sessionIds.size()>0){
//				sessionIds.forEach(session -> {
//					sessionRepository.deleteById(session);
//				});
//			}
//		}
//	}
//
//	/**
//	 * 删除用户用户登录Session
//	 */
//	public static void deleteUserSession(String username){
//		final FindByIndexNameSessionRepository<? extends Session> sessionRepository = SpringContextUtil.getBean(FindByIndexNameSessionRepository.class);
//		// 查询用户的 Session 信息，返回值 key 为 sessionId
//		Map<String, ? extends Session> userSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
//		if(userSessions !=null) {
//			// 移除用户的 session 信息
//			List<String> sessionIds = new ArrayList<>(userSessions.keySet());
//			if (!sessionIds.isEmpty() && sessionIds.size()>0){
//				sessionIds.forEach(session -> {
//					sessionRepository.deleteById(session);
//				});
//			}
//		}
//	}

}
