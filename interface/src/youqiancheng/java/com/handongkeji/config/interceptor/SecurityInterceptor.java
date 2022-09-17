package com.handongkeji.config.interceptor;

import com.handongkeji.util.Constants;
import com.handongkeji.util.DecryptToken;
import com.handongkeji.util.JsonBean;
import com.handongkeji.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    protected static final Log logger = LogFactory.getLog(SecurityInterceptor.class);

    @Autowired
    private DecryptToken decryptToken;

    private static SecurityInterceptor securityInterceptor;

    @PostConstruct // @PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行
    public void init() {
        securityInterceptor = this;
        securityInterceptor.decryptToken = this.decryptToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        logger.info("**admin** request url：" + req.getRequestURI());
        JsonBean result = new JsonBean();
//        String token = req.getParameter("token");
        String token = req.getHeader("token");// 从 http 请求头中取出 token
        if(null == token){
             token = req.getParameter("token");
        }
        Map<String, Object> decrypt_map = null;
        if (!StringUtil.isNullOrEmpty(token)) {
            decrypt_map = securityInterceptor.decryptToken.decryptToken(token);
            Long userId = null;
            String result_code = null;
            if (decrypt_map != null) {
                req.setAttribute("admin",decrypt_map.get("member"));
                req.setAttribute("shopUser",decrypt_map.get("shopUser"));
                result_code = (String) decrypt_map.get("code");
                if ("201".equals(result_code)) {
                    decrypt_map.put("member", null);
                    decrypt_map.put("shopUser", null);
                    result.setData(new HashMap<>());
                    result.setStatus(Constants.REPEAT_LOGIN_CODE);
                    result.setMessage(Constants.REPEAT_LOGIN_MSG);
                    writeResult(JSONObject.fromObject(result).toString(), res);
                    //writeResult(JSONArray.fromObject(result).toString(), res);
                    //res.getWriter().write(JSONArray.fromObject(result).toString());
                    return false;
                } else if ("200".equals(result_code)) {
                    userId = (Long) decrypt_map.get("uid");
                    req.setAttribute("auth_uid", userId);
                } else if ("404".equals(result_code) || "400".equals(result_code)) {
                    result.setStatus(Constants.TOKEN_INVALID_CODE);
                    //result.setMessage(Constants.TOKEN_INVALID_MSG);
                    result.setMessage("登录失效请重新登录");
                    writeResult(JSONObject.fromObject(result).toString(), res);
                    return false;
                }
                else if ("403".equals(result_code)) {
                    result.setStatus(900);
                    result.setMessage("你的账户已被下线！请联系客服");
                    writeResult(JSONObject.fromObject(result).toString(), res);
                    return false;
                }
            } else {
                result.setStatus(Constants.TOKEN_INVALID_CODE);
                //result.setMessage(Constants.TOKEN_INVALID_MSG);
                result.setMessage("登录失效请重新登录");
                writeResult(JSONObject.fromObject(result).toString(), res);
                return false;
            }
        } else {
            result.setStatus(Constants.$NULLPARAMETERS);
            result.setMessage(Constants.ERROR_TOKEN_MESSAGE);
            writeResult(JSONObject.fromObject(result).toString(), res);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {
    }

    /**
     * 将返回值写入页面
     *
     * @param str
     */
    public void  writeResult(String str, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
