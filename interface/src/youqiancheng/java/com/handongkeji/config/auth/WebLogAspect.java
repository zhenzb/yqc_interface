package com.handongkeji.config.auth;

import com.youqiancheng.controller.wechatpay.weixinpay.util.IpAddressUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution( * com.youqiancheng.controller.app.*.*(..)) || " +
            "execution(* com.youqiancheng.controller.client.*.*(..)) ||" +
            "execution(* com.youqiancheng.controller.shop.*.*(..)) ||" +
            "execution(* com.youqiancheng.controller.manager.*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        if(request.getRequestURL() !=null && !request.getRequestURL().toString().contains("getPeopleNumber")){
            logger.info("请求地址 : " +request.getRequestURL().toString());
            logger.info("HTTP METHOD : " + request.getMethod());
            // 获取真实的ip地址
            logger.info("IP : " + IpAddressUtil.getIpAddr(request));
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName());
            logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
        }

    }
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
        logger.debug("返回值 : " + ret);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        Signature signature = pjp.getSignature();
        if(null !=signature){
            String name = signature.getName();
            if(!"getPeopleNumber".equals(name)){
                logger.info("耗时 : "  + name + "==" + (System.currentTimeMillis() - startTime));
            }
        }
        return ob;
    }
}
