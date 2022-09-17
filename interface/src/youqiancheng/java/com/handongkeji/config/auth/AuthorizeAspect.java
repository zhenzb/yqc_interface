package com.handongkeji.config.auth;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.config.redis.CacheUtils;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.StringUtil;
import com.handongkeji.util.manager.JwtUtils;
import com.youqiancheng.initdata.UserEnity;
import com.youqiancheng.vo.result.ResultEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证 AOP
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect {
//
//    @Resource
//    private AuthLoginService authLoginService;
    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* aopdemo.*.*(..))代表aopdemo包下所有类的所有方法都会被代理
     * @annotation(com.handongkeji.config.auth.AuthRuleAnnotation)代表使用注解@AuthRuleAnnotation的所有方法都会被代理
     */
    @Pointcut("@annotation(com.handongkeji.config.auth.AuthRuleAnnotation)")
    public void adminLoginVerify() {
    }
    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     *
     */
    @Pointcut("@annotation(com.handongkeji.config.auth.PublicPlatformAnnotation)")
    public void publicPlatformLoginVerify() {
    }

    /**
     * @param joinPoint
     * @return void
     * @Description 登录验证
     * @author Gavin
     * @date 2019/3/27 15:18
     *
     *  前置方法,在目标方法执行前执行
     * @param joinPoint 封装了代理方法信息的对象,若用不到则可以忽略不写
     *
     */
    @Before("adminLoginVerify()")
    public void doAdminAuthVerify(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new JsonException(ResultEnum.NOT_NETWORK);
        }
        HttpServletRequest request = attributes.getRequest();
        String type = request.getHeader("X-Type");
        if (StringUtil.isNullOrEmpty(type)) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
        }
        String id = request.getHeader("X-UserId");
        if (StringUtil.isNullOrEmpty(id)) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
        }


        String token = request.getHeader("X-Token");
        if (StringUtil.isNullOrEmpty(token)) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
        }
        Long adminId = Long.valueOf(id);

        // 验证 token
        Claims claims = JwtUtils.parse(token);
        if (claims == null) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
        }
        //app token检验
        if(type.equals(TypeConstant.PlatformType.app.getCode())){
            if(claims.get("app_user_id")==null){
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
            }
            Long jwtAdminId = Long.valueOf(claims.get("app_user_id").toString());
            if (adminId.compareTo(jwtAdminId) != 0) {
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
            }
            String cacheToken = (String)CacheUtils.get(jwtAdminId + "token");
            if(cacheToken !=null){
                if(!cacheToken.equals(token)){
                    throw new JsonException(ResultEnum.LOGIN_VERIFY_OTHER);
                }
            }

        //pc  token 校验
        }else if(type.equals(TypeConstant.PlatformType.pc.getCode())){
            if(claims.get("pc_user_id")==null){
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
            }
            Long jwtAdminId = Long.valueOf(claims.get("pc_user_id").toString());
            if (adminId.compareTo(jwtAdminId) != 0) {
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL);
            }
            UserEnity session = SecurityUtils.getSession("pc_user_id_" + adminId);
            if(session==null||!session.getToken().equals(token)){
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FAIL,"您的账号已在其他地方登录，请重新登录");
            }
        }




//        // 判断是否进行权限验证
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        //从切面中获取当前方法
//        Method method = signature.getMethod();
//        //得到了方,提取出他的注解
//        AuthRuleAnnotation action = method.getAnnotation(AuthRuleAnnotation.class);
//        // 进行权限验证
//       // authRuleVerify(action.value(), adminId);
    }

//    /**
//     * @param authRule
//     * @param adminId
//     * @return void
//     * @Description 权限验证
//     * @author Gavin
//     * @date 2019/3/27 15:19
//     */
//    private void authRuleVerify(String authRule, Long adminId) {
//        if (authRule != null && authRule.length() > 0) {
//            List<String> authRules = authLoginService.listRuleByAdminId(adminId);
//            // admin 为最高权限
//            for (String item : authRules) {
//                if (item.equals("admin") || item.equals(authRule)) {
//                    return;
//                }
//            }
//            throw new JsonException(ResultEnum.AUTH_FAILED);
//        }
//    }

}
