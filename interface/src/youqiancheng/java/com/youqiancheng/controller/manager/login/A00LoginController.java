package com.youqiancheng.controller.manager.login;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.manager.IpUtils;
import com.handongkeji.util.manager.JwtUtils;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.AuthAdmin.LoginForm;
import com.youqiancheng.mybatis.model.A01Admin;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.manager.service.system.A01AdminService;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsYXN0TG9naW5UaW1lIjoxNTg3MTc1MTM4Njg0LCJhZG1pbl9pZCI6MTksImV4cCI6MTU4NzIxODMzOX0.OC9U7WqEowNmnqBgEXuGVys3OCXix_RquT6sacs-ga8
 　* @author shalongteng
 　* @date 2020/3/28 15:15
   SKU ：StockKeeping Unit的缩写，直译过来就是存货单元。
 　*/
@RestController
@RequestMapping("admin")
@Api(tags = "总管理后台-登录模块")
public class A00LoginController {
    @Resource
    private A01AdminService a01AdminService;


    @Autowired
    private B01UserAppService b01UserService;
    @Autowired
    private SendSmsUtil sendSmsUtil;
    /**
     　* @Description: 用户登录
     　* @author shalongteng
     　* @date 2020/3/28 15:21
     　*/
    @ApiOperation(value = "登录接口", notes = "登录接口")
    @PostMapping(value = "login")
    public ResultVo<A01Admin> index(@RequestBody @Valid LoginForm loginForm,
                          HttpServletRequest request) {

        //用户名或密码错误
        A01Admin a01Admin = a01AdminService.findByUserName(loginForm.getUsername());
        if (null == a01Admin) {
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "用户名或密码错误");
        }

        //用户名或密码错误
        if (!PasswordUtils.authAdminPwd(loginForm.getPwd()).equals(a01Admin.getPassword())) {
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "用户名或密码错误");
        }
        //账号被禁用
        if (a01Admin.getStatus() == 0) {
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "账号被禁用");
        }

        // 更新登录状态
        a01Admin.setLastLoginTime(new Date());
        a01Admin.setLastLoginIp(IpUtils.getIpAddr(request));
        a01AdminService.updateAuthAdmin(a01Admin);


        Map<String, Object> claims = new HashMap<>();
        claims.put("admin_id", a01Admin.getId());
        claims.put("lastLoginTime", a01Admin.getLastLoginTime());
        //生成token，半天后过期 1*24*3600*1000l
        String token = JwtUtils.createToken(claims, 60*1000l);
        a01Admin.setToken(token);

        //返回用户信息 和用户权限信息
        return ResultVOUtils.success(a01Admin);
    }

    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping("logout")
    public ResultVo out(String username) {
        return ResultVOUtils.success();
    }



}
